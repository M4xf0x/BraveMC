package de.m4xf0x.blf;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class FangenSystem extends JavaPlugin {

	public FangenMsg fangenMsg;
	public FangenCMD fangenCmd;
	public FangenEvents fangenEvents;

	public void onEnable() {

		this.fangenCmd = new FangenCMD(this);
		this.fangenMsg = new FangenMsg();
		this.fangenEvents = new FangenEvents(this);

		final PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(fangenEvents, this);

		getCommand("fangen").setExecutor(fangenCmd);

		armorTimer();

	}

	public ArrayList<Player> playerIngame = new ArrayList<>();

	public Player catcher;

	public FangenMsg getFangenMsg() {
		return fangenMsg;
	}

	public FangenCMD getFangenCmd() {
		return fangenCmd;
	}

	public FangenEvents getFangenEvents() {
		return fangenEvents;
	}

	public HashMap<Player, BukkitRunnable> effect = new HashMap<>();

	public HashMap<Player, ItemStack[]> inventorys = new HashMap<>();

	public void joinGame(Player p) {

		inventorys.put(p, p.getInventory().getContents());

		for (int i = 0; i <= 35; i++) {

			p.getInventory().setItem(i, null);

		}

		ItemStack is = new ItemStack(Material.SLIME_BALL, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("§8» §a§lVerlassen");
		is.setItemMeta(im);

		p.getInventory().setItem(8, is);

		if (!playerIngame.contains(p)) {

			playerIngame.add(p);

		}

		if (!effect.containsKey(p)) {

			effect.put(p, new BukkitRunnable() {

				@Override
				public void run() {
					final Location loc = p.getLocation().add(0, 2.45, 0);
					if (p != null)
						p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 5);

				}
			});
			effect.get(p).runTaskTimer(this, 1, 1);
		}

		if (p.getInventory().getBoots() != null) {
			if (p.getInventory().getBoots().getItemMeta().getDisplayName().contains("Speed")
					|| p.getInventory().getBoots().getItemMeta().getDisplayName().contains("Jump")
					|| p.getInventory().getBoots().getItemMeta().getDisplayName().contains("JetPack")) {

				p.getInventory().setBoots(null);
			}
		}
	}

	public void leaveGame(Player p) {

		p.getInventory().setItem(8, null);

		try {

			ItemStack[] ia = inventorys.get(p);

			for (int i = 0; i < ia.length; i++) {

				if (ia[i] != null) {

					ItemStack temp = ia[i];

					p.getInventory().setItem(i, temp);

				}
			}

		} catch (NullPointerException ex) {

		}

		if (effect.containsKey(p)) {

			effect.get(p).cancel();
			effect.remove(p);

		}

		if (playerIngame.contains(p)) {

			playerIngame.remove(p);

		}

		if (catcher == p) {

			searchNewCatcher();
			p.getInventory().setChestplate(null);

		}
	}

	float color = 0.0f;

	public void armorTimer() {

		Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {

			@Override
			public void run() {
				if (catcher != null) {
					final Player player = catcher;

					if (player != null && player.isOnline()) {

						color = handleColor(color, 0.005f);

						setArmor(color, 0.02f);

						sendActionbar(player, "§7Du bist §3Fänger");

					}
				}

			}
		}, 1, 1);

	}

	private float handleColor(float hue, float speed) {
		hue += speed;
		if (hue > 1.0f)
			hue = 0.0f;
		return hue;
	}

	private Color getRGB(float hue) {
		return Color.getHSBColor(hue, 1f, 1f);
	}

	private void setArmor(float hue, float gradientSpeed) {

		final ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);

		org.bukkit.Color chestplateColor = org.bukkit.Color.fromBGR(getRGB(hue).getRed(), getRGB(hue).getGreen(),
				getRGB(hue).getBlue());
		hue = handleColor(hue, gradientSpeed);

		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
		chestplateMeta.setColor(chestplateColor);
		chestplate.setItemMeta(chestplateMeta);

		catcher.getInventory().setChestplate(chestplate);

	}

	public void searchNewCatcher() {
		catcher = null;

		final Random random = new Random();
		if (playerIngame.size() > 0) {
			final int i = random.nextInt((playerIngame.size() >= 0 ? playerIngame.size() : 0));
			catcher = playerIngame.get(i);
			catcher.sendMessage(getFangenMsg().Als_Neuer_Faenger_Teilnehmen());
			catcher.playSound(catcher.getLocation(), Sound.LEVEL_UP, 1, 2);

		} else {
			catcher = null;
		}
	}

	public void sendActionbar(Player p, String message) {
		IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer
				.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
		PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
	}

}

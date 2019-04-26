package de.brave.fangen;

import java.awt.Color;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class FangCMD implements CommandExecutor, Listener {

	private final FangSystem fsys;

	public FangCMD(final FangSystem fsys) {
		this.fsys = fsys;
		mainLoop().runTaskTimer(fsys, 1, 1);
	}

	@EventHandler
	public void on(PlayerQuitEvent e) {

		this.stopplayereffect(e.getPlayer());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (cmd.getName().equals("fangsys")) {
				if (fsys.list.contains((Player) sender)) {
					sender.sendMessage(fsys.getFangEditor().Du_nimmst_nichtmehr_teil());
					fsys.list.remove((Player) sender);
					this.stopplayereffect((Player) sender);

					hue.remove(((Player) sender).getUniqueId());
					((Player) sender).getInventory().setArmorContents(null);

					return false;
				}
				fsys.faengerAdd((Player) sender);
				sender.sendMessage(fsys.getFangEditor().Du_nimmst_teil());
				fsys.list.add((Player) sender);
				this.startplayermitspielen((Player) sender);
				hue.put(((Player) sender).getUniqueId(), 0.0f);

				for (Player all : Bukkit.getOnlinePlayers()) {
					if (fsys.list.contains(all) && (sender != all)) {
						all.sendMessage(fsys.getFangEditor().xxx_nimmt_nun_teil(sender.getName()));
					}
				}
				return false;
			}
		}
		return false;
	}

	final HashMap<UUID, BukkitRunnable> run = new HashMap<>();

	public void startplayermitspielen(final Player p) {

		if (!run.containsKey(p.getUniqueId())) {
			run.put(p.getUniqueId(), new BukkitRunnable() {

				@Override
				public void run() {
					final Location loc = p.getLocation().add(0, 2.5, 0);
					if (p != null)
						p.getWorld().playEffect(loc, Effect.COLOURED_DUST, 5);

				}
			});
			run.get(p.getUniqueId()).runTaskTimer(fsys, 1, 1);
		}

	}

	public void stopplayereffect(final Player p) {

		if (run.containsKey(p.getUniqueId())) {
			run.get(p.getUniqueId()).cancel();
			run.remove(p.getUniqueId());
		}

	}

	private void setArmor(Player player, float hue, float gradientSpeed) {

		final ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);

		org.bukkit.Color chestplateColor = org.bukkit.Color.fromBGR(getRGB(hue).getRed(), getRGB(hue).getGreen(),
				getRGB(hue).getBlue());
		hue = handleColor(hue, gradientSpeed);

		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
		chestplateMeta.setColor(chestplateColor);
		chestplate.setItemMeta(chestplateMeta);

		player.getInventory().setChestplate(chestplate);

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

	public HashMap<UUID, Float> hue = new HashMap<>();

	public BukkitRunnable mainLoop() {
		return new BukkitRunnable() {
			@Override
			public void run() {

				hue.forEach((uuid, h) -> {

					final Player player = Bukkit.getPlayer(fsys.faenger.getUniqueId());

					if (player != null && player.isOnline()) {

						h = handleColor(h, 0.005f);

						setArmor(player, h, 0.02f);

						hue.put(uuid, h);

					}

				});

			}
		};
	}
}

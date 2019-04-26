package de.brave.fangen;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FangListener implements Listener {
	private final FangSystem fsys;

	public FangListener(final FangSystem fsys) {
		this.fsys = fsys;
	}

	@EventHandler
	public void on(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			final Player player = (Player) e.getEntity();
			try {
				final Player damager = (Player) e.getDamager();
				if (fsys.list.contains(player) && fsys.list.contains(damager)) {
					if (damager.equals(fsys.faenger)) {
						fsys.faenger.sendMessage(fsys.getFangEditor().Du_hast_xxx_gefangen(player.getName()));
						fsys.faenger.playSound(fsys.faenger.getLocation(), Sound.LEVEL_UP, 1, 2);
						fsys.faenger.getInventory().setChestplate(null);
						fsys.faenger = player;
						fsys.faenger.sendMessage(fsys.getFangEditor().Du_wurdest_gefangen());
						fsys.faenger.playSound(fsys.faenger.getLocation(), Sound.LEVEL_UP, 1, 2);

					}
				}
			} catch (NullPointerException exeption) {
			}
		}
	}

	@EventHandler
	public void onStopEffect(PlayerQuitEvent e) {
		e.getPlayer().getInventory().setChestplate(null);
		fsys.getFangCMD().run.get(e.getPlayer().getUniqueId()).cancel();
		fsys.getFangCMD().run.remove(e.getPlayer().getUniqueId());
	}

	@EventHandler
	public void on(PlayerQuitEvent e) {

		if (this.fsys.list.contains(e.getPlayer())) {
			if (fsys.faenger.equals(e.getPlayer())) {
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (fsys.list.contains(all)) {
						all.sendMessage(fsys.getFangEditor().Hat_das_Spiel_verlassen(e.getPlayer().getName()));
					}
				}
				this.searchnewfaenger();

			}
		}

	}

	public void searchnewfaenger() {
		fsys.resetPlayer();
		final Random random = new Random();
		final int i = random.nextInt((fsys.list.size() >= 0 ? fsys.list.size() : 0));
		if (fsys.list.size() > 0) {
			fsys.faenger = fsys.list.get(i);
			fsys.faenger.sendMessage(fsys.getFangEditor().Als_Neuer_Faenger_Teilnehmen());
			fsys.faenger.playSound(fsys.faenger.getLocation(), Sound.LEVEL_UP, 1, 2);
		}
	}

}

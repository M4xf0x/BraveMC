package de.m4xf0x.blf;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FangenEvents implements Listener {

	Fangen fangen;

	public FangenEvents(Fangen fangen) {
		this.fangen = fangen;
	}

	Player lastTagger;
	long lastTaggedTime;

	@EventHandler
	public void onHitLeft(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player taggedPlayer = (Player) e.getEntity();
			Player tagger = (Player) e.getDamager();

			if (fangen.playerIngame.contains(taggedPlayer) && fangen.playerIngame.contains(tagger)) {
				
				e.setDamage(0);
				
				if (tagger == fangen.catcher) {

					if (lastTagger != taggedPlayer || System.currentTimeMillis() - lastTaggedTime >= 5000) {

						tagger.playSound(tagger.getLocation(), Sound.LEVEL_UP, 1, 2);
						tagger.sendMessage(fangen.getFangenMsg().Du_hast_xxx_gefangen(taggedPlayer.getName()));

						fangen.catcher = taggedPlayer;

						fangen.sendActionbar(tagger, "");
						tagger.getInventory().setChestplate(null);

						taggedPlayer.sendMessage(fangen.getFangenMsg().Du_wurdest_gefangen());
						taggedPlayer.playSound(taggedPlayer.getLocation(), Sound.LEVEL_UP, 1, 2);

						lastTagger = tagger;
						lastTaggedTime = System.currentTimeMillis();

					} else {
						tagger.sendMessage(fangen.getFangenMsg().waitForTagging());
						
					}
				}
			} else {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onHitRight(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			Player taggedPlayer = (Player) e.getRightClicked();
			Player tagger = (Player) e.getPlayer();

			if (fangen.playerIngame.contains(taggedPlayer) && fangen.playerIngame.contains(tagger)) {

				if (tagger == fangen.catcher) {

					if (lastTagger != taggedPlayer || System.currentTimeMillis() - lastTaggedTime >= 5000) {

						tagger.playSound(tagger.getLocation(), Sound.LEVEL_UP, 1, 2);
						tagger.sendMessage(fangen.getFangenMsg().Du_hast_xxx_gefangen(taggedPlayer.getName()));

						fangen.catcher = taggedPlayer;
						
						fangen.sendActionbar(tagger, "");
						tagger.getInventory().setChestplate(null);

						taggedPlayer.sendMessage(fangen.getFangenMsg().Du_wurdest_gefangen());
						taggedPlayer.playSound(taggedPlayer.getLocation(), Sound.LEVEL_UP, 1, 2);
						
						lastTagger = tagger;
						lastTaggedTime = System.currentTimeMillis();

					} else {
						tagger.sendMessage(fangen.getFangenMsg().waitForTagging());
						
					}

				}

			}
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		fangen.leaveGame(p);

		if (p == fangen.catcher) {

			fangen.searchNewCatcher();
			p.getInventory().setChestplate(null);
		}
		
	}
	
	@EventHandler
	public void onJoinSetActionbar(PlayerJoinEvent e) {
		
		fangen.sendActionbar(e.getPlayer(), "");
		
	}

}

package de.m4xf0x.blf;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FangenCMD implements CommandExecutor {

	Fangen fangen;

	public FangenCMD(Fangen fangen) {
		this.fangen = fangen;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (fangen.playerIngame.contains((Player) sender)) {
				sender.sendMessage(fangen.getFangenMsg().Du_nimmst_nichtmehr_teil());

				Player p = (Player) sender;

				fangen.leaveGame(p);

				if (p == fangen.catcher) {

					fangen.searchNewCatcher();
					p.getInventory().setChestplate(null);
				}

			} else {

				sender.sendMessage(fangen.getFangenMsg().Du_nimmst_teil());

				Player p = (Player) sender;

				fangen.joinGame(p);
				
				if (fangen.catcher == null) {
					
					fangen.searchNewCatcher();
					
				}

				for (Player all : Bukkit.getOnlinePlayers()) {
					if (fangen.playerIngame.contains(all) && (sender != all)) {
						all.sendMessage(fangen.getFangenMsg().xxx_nimmt_nun_teil(sender.getName()));
					}
				}

			}

		}

		return false;

	}

}

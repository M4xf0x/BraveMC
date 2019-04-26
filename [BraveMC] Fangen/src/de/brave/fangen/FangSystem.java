package de.brave.fangen;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FangSystem extends JavaPlugin {

	public FangSystem fsys;
	public FangCMD fcmd;
	public FangEditor feditor;
	public FangListener flistener;

	public ArrayList<Player> list;

	public Player faenger;

	public String Prefix() {
		final String i = "§eLobbySystem §8»§7 ";
		return i;
	}

	public void resetPlayer() {
		final Player i = null;
		this.faenger = i;
	}

	public void onEnable() {
		this.fsys = this;
		this.i();
	}

	public FangCMD getFangCMD() {
		return this.fcmd;
	}

	public FangEditor getFangEditor() {
		return this.feditor;
	}

	public FangListener getFangListener() {
		return flistener;
	}

	public void i() {

		final PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new FangListener(this), this);
		pm.registerEvents(new FangCMD(this), this);

		getCommand("fangsys").setExecutor(new FangCMD(this));
		this.list = new ArrayList<>();
		this.fcmd = new FangCMD(this);
		this.feditor = new FangEditor(this);
		this.flistener = new FangListener(this);
	}

	public void faengerAdd(final Player player) {
		if (list.size() == 0) {
			faenger = player;
			faenger.sendMessage(this.getFangEditor().Als_Faenger_Teil_nehmen());
			return;
		}
	}

}

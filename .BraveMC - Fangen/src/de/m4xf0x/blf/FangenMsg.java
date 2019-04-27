package de.m4xf0x.blf;

public class FangenMsg {

	public String Prefix() {
		final String i = "§eLobbySystem §8»§7 ";
		return i;
	}
	
	public String Du_nimmst_teil() {
		final String i = this.Prefix() + "§7Du nimmst am §3Fangen §7Spiel teil.";
		return i;
	}

	public String Du_nimmst_nichtmehr_teil() {
		final String i = Prefix() + "§7Du hast das §3Fangen §7Spiel verlassen.";
		return i;
	}

	public String Als_Faenger_Teil_nehmen() {
		final String i = Prefix() + "§7Du nimmst als §3Fänger§7 teil.";
		return i;
	}

	public String Als_Neuer_Faenger_Teilnehmen() {
		final String i = Prefix() + "§7Du wurdest als neuer §3Fänger §7ausgewählt.";
		return i;
	}

	public String Hat_das_Spiel_verlassen(final String e) {
		final String i = Prefix() + "§7Der Fänger §3" + e + "§7 hat den Server verlassen.";
		return i;
	}

	public String Du_wurdest_gefangen() {
		final String i = Prefix() + "§7Du wurdest §3gefangen§7! Jetzt bist du der §3Fänger§7!";
		return i;
	}

	public String Du_hast_xxx_gefangen(final String e) {
		final String i = Prefix() + "§7Du hast §3" + e + "§7 gefangen!";
		return i;
	}

	public String xxx_nimmt_nun_teil(final String i) {
		final String e = Prefix() + "§7Der Spieler §3" + i + "§7 nimmt nun Teil!";
		return e;
	}
	
	public String waitForTagging() {
		final String e = Prefix() + "§7Bitte warte einen §3Moment§7, bis du ihn wieder fangen darfst.";
		return e;
	}
	
}

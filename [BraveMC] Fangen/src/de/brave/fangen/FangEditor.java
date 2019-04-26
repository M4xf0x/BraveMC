package de.brave.fangen;

public class FangEditor {
	private final FangSystem fsys;

	public FangEditor(final FangSystem fsys) {
		this.fsys = fsys;
	}

	public String Du_nimmst_teil() {
		final String i = fsys.Prefix() + "�7Du nimmst am �3Fangen �7Spiel teil.";
		return i;
	}

	public String Du_nimmst_nichtmehr_teil() {
		final String i = fsys.Prefix() + "�7Du hast das �3Fangen �7Spiel verlassen.";
		return i;
	}

	public String Als_Faenger_Teil_nehmen() {
		final String i = fsys.Prefix() + "�7Du nimmst als �3F�nger�7 teil.";
		return i;
	}

	public String Als_Neuer_Faenger_Teilnehmen() {
		final String i = fsys.Prefix() + "�7Du wurdest als neuer �3F�nger �7ausgew�hlt.";
		return i;
	}

	public String Hat_das_Spiel_verlassen(final String e) {
		final String i = fsys.Prefix() + "�7Der F�nger �3" + e + "�7 hat den Server verlassen.";
		return i;
	}

	public String Du_wurdest_gefangen() {
		final String i = fsys.Prefix() + "�7Du wurdest �3gefangen�7! Jetzt bist du der �3F�nger�7!";
		return i;
	}

	public String Du_hast_xxx_gefangen(final String e) {
		final String i = fsys.Prefix() + "�7Du hast �3" + e + "�7 gefangen!";
		return i;
	}

	public String xxx_nimmt_nun_teil(final String i) {
		final String e = fsys.Prefix() + "�7Der Spieler �3" + i + "�7 nimmt nun Teil!";
		return e;
	}
}

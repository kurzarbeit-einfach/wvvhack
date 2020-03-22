package de.udo.pdf;

public class TarifvertragKenndaten {

	private String bezeichnung;
	private String normaleArbeitszeit;
	private String hatKurzarbeitKlausel;

	private TarifvertragKenndaten(Builder builder) {
		this.bezeichnung = builder.bezeichnung;
		this.normaleArbeitszeit = builder.normaleArbeitszeit;
		this.hatKurzarbeitKlausel = builder.hatKurzarbeitKlausel;
	}

	public TarifvertragKenndaten() {
		super();
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getNormaleArbeitszeit() {
		return normaleArbeitszeit;
	}

	public void setNormaleArbeitszeit(String normaleArbeitszeit) {
		this.normaleArbeitszeit = normaleArbeitszeit;
	}

	public String getHatKurzarbeitKlausel() {
		return hatKurzarbeitKlausel;
	}

	public void setHatKurzarbeitKlausel(String hatKurzarbeitKlausel) {
		this.hatKurzarbeitKlausel = hatKurzarbeitKlausel;
	}

	// --- BUILDER ONLY

	public static class Builder {

		private String bezeichnung;
		private String normaleArbeitszeit;
		private String hatKurzarbeitKlausel;

		public Builder() {
		}

		Builder(String bezeichnung, String normaleArbeitszeit, String hatKurzarbeitKlausel) {
			this.bezeichnung = bezeichnung;
			this.normaleArbeitszeit = normaleArbeitszeit;
			this.hatKurzarbeitKlausel = hatKurzarbeitKlausel;
		}

		public Builder bezeichnung(String bezeichnung) {
			this.bezeichnung = bezeichnung;
			return Builder.this;
		}

		public Builder normaleArbeitszeit(String normaleArbeitszeit) {
			this.normaleArbeitszeit = normaleArbeitszeit;
			return Builder.this;
		}

		public Builder hatKurzarbeitKlausel(String hatKurzarbeitKlausel) {
			this.hatKurzarbeitKlausel = hatKurzarbeitKlausel;
			return Builder.this;
		}

		public TarifvertragKenndaten build() {

			return new TarifvertragKenndaten(this);
		}
	}

}

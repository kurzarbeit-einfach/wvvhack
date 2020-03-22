package de.udo.pdf;

public class MonatJahr {
	private String monat;
	private String jahr;

	public MonatJahr() {
		super();
	}

	private MonatJahr(Builder builder) {
		this.monat = builder.monat;
		this.jahr = builder.jahr;
	}

	public String getMonat() {
		return monat;
	}

	public String getJahr() {
		return jahr;
	}

	@Override
	public String toString() {
		return "MonatJahr [monat=" + monat + ", jahr=" + jahr + "]";
	}

	// --- BUILDER ONLY

	public static class Builder {

		private String monat;
		private String jahr;

		public Builder() {
		}

		Builder(String monat, String jahr) {
			this.monat = monat;
			this.jahr = jahr;
		}

		public Builder monat(String monat) {
			this.monat = monat;
			return Builder.this;
		}

		public Builder jahr(String jahr) {
			this.jahr = jahr;
			return Builder.this;
		}

		public MonatJahr build() {

			return new MonatJahr(this);
		}
	}

}

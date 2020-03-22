package de.udo.pdf;

public class KontaktDaten {

	private String nameAnschrift;
	private String zusaetzlicheKontaktinfos;

	private KontaktDaten(Builder builder) {
		this.nameAnschrift = builder.nameAnschrift;
		this.zusaetzlicheKontaktinfos = builder.zusaetzlicheKontaktinfos;
	}

	public KontaktDaten() {
		super();
	}

	public String getNameAnschrift() {
		return nameAnschrift;
	}

	public void setNameAnschrift(String nameAnschrift) {
		this.nameAnschrift = nameAnschrift;
	}

	public String getZusaetzlicheKontaktinfos() {
		return zusaetzlicheKontaktinfos;
	}

	public void setZusaetzlicheKontaktinfos(String zusaetzlicheKontaktinfos) {
		this.zusaetzlicheKontaktinfos = zusaetzlicheKontaktinfos;
	}

	public static class Builder {

		private String nameAnschrift;
		private String zusaetzlicheKontaktinfos;

		public Builder() {
		}

		Builder(String nameAnschrift, String zusaetzlicheKontaktinfos) {
			this.nameAnschrift = nameAnschrift;
			this.zusaetzlicheKontaktinfos = zusaetzlicheKontaktinfos;
		}

		public Builder nameAnschrift(String nameAnschrift) {
			this.nameAnschrift = nameAnschrift;
			return Builder.this;
		}

		public Builder zusaetzlicheKontaktinfos(String zusaetzlicheKontaktinfos) {
			this.zusaetzlicheKontaktinfos = zusaetzlicheKontaktinfos;
			return Builder.this;
		}

		public KontaktDaten build() {
			return new KontaktDaten(this);
		}
	}

}
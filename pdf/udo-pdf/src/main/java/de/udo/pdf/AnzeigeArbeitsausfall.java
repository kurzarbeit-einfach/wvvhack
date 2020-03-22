package de.udo.pdf;

public class AnzeigeArbeitsausfall {

	private String agenturFuerArbeitAnschrift;
	// Die 9-stellige Stamm-Nr. Kug besteht 1) aus dem vorbelegten Buchstaben K und
	// 2) aus dem 8-stelligen Ausfüllfeld. Bitte Stamm-Nr. Kug eingeben
	private String stammNrKug;
	// 4-stellige Ableitungs-Nr. (soweit bekannt)
	private String abteilungsNr;
	// 8-stellige Betriebsnummer
	private String betriebsNr;

	private KontaktDaten betriebsanschrift;
	private KontaktDaten betriebsanschriftAnsprechpartner;
	private KontaktDaten lohnabrechnungsstelle;
	private KontaktDaten lohnabrechnungsstelleAnsprechpartner;

	private String branche;

	private MonatJahr zeitraumVon;
	private MonatJahr zeitraumBis;

	// value "null" bedeutet "den Gesamtbetrieb" (d.h. keine Einschränkung).
	private String abteilungsBeschraenkung;

	private String vollarbeitArbeitszeit;
	private String kurzarbeitArbeitszeit;

	private boolean unternehmenAelterAlsEinJahr;
	private String unternehmenGruendungsdatum;
	private TarifvertragKenndaten arbeiterTarifvertrag;
	private TarifvertragKenndaten angestellteTarifvertrag;
	private boolean tarifvertragHatAnkuendigungsfrist;
	private String tarifvertragAnkuendigungZ1;
	private String tarifvertragAnkuendigungZ2;
	private boolean betriebNichtTarifgebunden;

	// Neue Felder
	private boolean betriebsratVorhanden;
	private boolean kurzarbeitVereinbartDurchBetriebsvereinbarung;
	private boolean kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern;
	private boolean kurzarbeitVereinbartDurchAenderungskuendigung;
	private String kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm;
	private String kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu;
	private boolean kurzarbeitVereinbartDurchSonstiges;
	private String kurzarbeitVereinbartAnmerkungen;

	private String anzahlArbeitnehmerInBetroffenerAbteilung;
	private String anzahlLeiharbeiterInBetroffenerAbteilung;
	private String anzahlArbeitnehmerMitEntgeltAusfall;

	private String angabenArbeitsausfall;
	private String angabenArbeitsausfallAnlage;
	private boolean auchUeblicheUrsachenFuerAusfall;

	private String ortDatum;

	// Neue Felder

	public AnzeigeArbeitsausfall() {
		super();
	}

	public String getAgenturFuerArbeitAnschrift() {
		return agenturFuerArbeitAnschrift;
	}

	public void setAgenturFuerArbeitAnschrift(String agenturFuerArbeitAnschrift) {
		this.agenturFuerArbeitAnschrift = agenturFuerArbeitAnschrift;
	}

	public String getStammNrKug() {
		return stammNrKug;
	}

	public void setStammNrKug(String stammNrKug) {
		this.stammNrKug = stammNrKug;
	}

	public String getAbteilungsNr() {
		return abteilungsNr;
	}

	public void setAbteilungsNr(String abteilungsNr) {
		this.abteilungsNr = abteilungsNr;
	}

	public String getBetriebsNr() {
		return betriebsNr;
	}

	public void setBetriebsNr(String betriebsNr) {
		this.betriebsNr = betriebsNr;
	}

	public KontaktDaten getBetriebsanschrift() {
		return betriebsanschrift;
	}

	public void setBetriebsanschrift(KontaktDaten betriebsanschrift) {
		this.betriebsanschrift = betriebsanschrift;
	}

	public KontaktDaten getBetriebsanschriftAnsprechpartner() {
		return betriebsanschriftAnsprechpartner;
	}

	public void setBetriebsanschriftAnsprechpartner(KontaktDaten betriebsanschriftAnsprechpartner) {
		this.betriebsanschriftAnsprechpartner = betriebsanschriftAnsprechpartner;
	}

	public KontaktDaten getLohnabrechnungsstelle() {
		return lohnabrechnungsstelle;
	}

	public void setLohnabrechnungsstelle(KontaktDaten lohnabrechnungsstelle) {
		this.lohnabrechnungsstelle = lohnabrechnungsstelle;
	}

	public KontaktDaten getLohnabrechnungsstelleAnsprechpartner() {
		return lohnabrechnungsstelleAnsprechpartner;
	}

	public void setLohnabrechnungsstelleAnsprechpartner(KontaktDaten lohnabrechnungsstelleAnsprechpartner) {
		this.lohnabrechnungsstelleAnsprechpartner = lohnabrechnungsstelleAnsprechpartner;
	}

	public String getBranche() {
		return branche;
	}

	public void setBranche(String branche) {
		this.branche = branche;
	}

	public MonatJahr getZeitraumVon() {
		return zeitraumVon;
	}

	public void setZeitraumVon(MonatJahr zeitraumVon) {
		this.zeitraumVon = zeitraumVon;
	}

	public MonatJahr getZeitraumBis() {
		return zeitraumBis;
	}

	public void setZeitraumBis(MonatJahr zeitraumBis) {
		this.zeitraumBis = zeitraumBis;
	}

	public String getAbteilungsBeschraenkung() {
		return abteilungsBeschraenkung;
	}

	public void setAbteilungsBeschraenkung(String abteilungsBeschraenkung) {
		this.abteilungsBeschraenkung = abteilungsBeschraenkung;
	}

	public String getVollarbeitArbeitszeit() {
		return vollarbeitArbeitszeit;
	}

	public void setVollarbeitArbeitszeit(String vollarbeitArbeitszeit) {
		this.vollarbeitArbeitszeit = vollarbeitArbeitszeit;
	}

	public String getKurzarbeitArbeitszeit() {
		return kurzarbeitArbeitszeit;
	}

	public void setKurzarbeitArbeitszeit(String kurzarbeitArbeitszeit) {
		this.kurzarbeitArbeitszeit = kurzarbeitArbeitszeit;
	}

	public boolean isUnternehmenAelterAlsEinJahr() {
		return unternehmenAelterAlsEinJahr;
	}

	public void setUnternehmenAelterAlsEinJahr(boolean unternehmenAelterAlsEinJahr) {
		this.unternehmenAelterAlsEinJahr = unternehmenAelterAlsEinJahr;
	}

	public String getUnternehmenGruendungsdatum() {
		return unternehmenGruendungsdatum;
	}

	public void setUnternehmenGruendungsdatum(String unternehmenGruendungsdatum) {
		this.unternehmenGruendungsdatum = unternehmenGruendungsdatum;
	}

	public TarifvertragKenndaten getArbeiterTarifvertrag() {
		return arbeiterTarifvertrag;
	}

	public void setArbeiterTarifvertrag(TarifvertragKenndaten arbeiterTarifvertrag) {
		this.arbeiterTarifvertrag = arbeiterTarifvertrag;
	}

	public TarifvertragKenndaten getAngestellteTarifvertrag() {
		return angestellteTarifvertrag;
	}

	public void setAngestellteTarifvertrag(TarifvertragKenndaten angestellteTarifvertrag) {
		this.angestellteTarifvertrag = angestellteTarifvertrag;
	}

	public boolean isTarifvertragHatAnkuendigungsfrist() {
		return tarifvertragHatAnkuendigungsfrist;
	}

	public void setTarifvertragHatAnkuendigungsfrist(boolean tarifvertragHatAnkuendigungsfrist) {
		this.tarifvertragHatAnkuendigungsfrist = tarifvertragHatAnkuendigungsfrist;
	}

	public String getTarifvertragAnkuendigungZ1() {
		return tarifvertragAnkuendigungZ1;
	}

	public void setTarifvertragAnkuendigungZ1(String tarifvertragAnkuendigungZ1) {
		this.tarifvertragAnkuendigungZ1 = tarifvertragAnkuendigungZ1;
	}

	public String getTarifvertragAnkuendigungZ2() {
		return tarifvertragAnkuendigungZ2;
	}

	public void setTarifvertragAnkuendigungZ2(String tarifvertragAnkuendigungZ2) {
		this.tarifvertragAnkuendigungZ2 = tarifvertragAnkuendigungZ2;
	}

	public boolean isBetriebNichtTarifgebunden() {
		return betriebNichtTarifgebunden;
	}

	public void setBetriebNichtTarifgebunden(boolean betriebNichtTarifgebunden) {
		this.betriebNichtTarifgebunden = betriebNichtTarifgebunden;
	}

	public boolean isBetriebsratVorhanden() {
		return betriebsratVorhanden;
	}

	public void setBetriebsratVorhanden(boolean betriebsratVorhanden) {
		this.betriebsratVorhanden = betriebsratVorhanden;
	}

	public boolean isKurzarbeitVereinbartDurchBetriebsvereinbarung() {
		return kurzarbeitVereinbartDurchBetriebsvereinbarung;
	}

	public void setKurzarbeitVereinbartDurchBetriebsvereinbarung(
			boolean kurzarbeitVereinbartDurchBetriebsvereinbarung) {
		this.kurzarbeitVereinbartDurchBetriebsvereinbarung = kurzarbeitVereinbartDurchBetriebsvereinbarung;
	}

	public boolean isKurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern() {
		return kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern;
	}

	public void setKurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern(
			boolean kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern) {
		this.kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern = kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern;
	}

	public boolean isKurzarbeitVereinbartDurchAenderungskuendigung() {
		return kurzarbeitVereinbartDurchAenderungskuendigung;
	}

	public void setKurzarbeitVereinbartDurchAenderungskuendigung(
			boolean kurzarbeitVereinbartDurchAenderungskuendigung) {
		this.kurzarbeitVereinbartDurchAenderungskuendigung = kurzarbeitVereinbartDurchAenderungskuendigung;
	}

	public String getKurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm() {
		return kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm;
	}

	public void setKurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm(
			String kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm) {
		this.kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm = kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm;
	}

	public String getKurzarbeitVereinbartDurchAenderungskuendigungWirksamZu() {
		return kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu;
	}

	public void setKurzarbeitVereinbartDurchAenderungskuendigungWirksamZu(
			String kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu) {
		this.kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu = kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu;
	}

	public boolean isKurzarbeitVereinbartDurchSonstiges() {
		return kurzarbeitVereinbartDurchSonstiges;
	}

	public void setKurzarbeitVereinbartDurchSonstiges(boolean kurzarbeitVereinbartDurchSonstiges) {
		this.kurzarbeitVereinbartDurchSonstiges = kurzarbeitVereinbartDurchSonstiges;
	}

	public String getKurzarbeitVereinbartAnmerkungen() {
		return kurzarbeitVereinbartAnmerkungen;
	}

	public void setKurzarbeitVereinbartAnmerkungen(String kurzarbeitVereinbartAnmerkungen) {
		this.kurzarbeitVereinbartAnmerkungen = kurzarbeitVereinbartAnmerkungen;
	}

	public String getAnzahlArbeitnehmerInBetroffenerAbteilung() {
		return anzahlArbeitnehmerInBetroffenerAbteilung;
	}

	public void setAnzahlArbeitnehmerInBetroffenerAbteilung(String anzahlArbeitnehmerInBetroffenerAbteilung) {
		this.anzahlArbeitnehmerInBetroffenerAbteilung = anzahlArbeitnehmerInBetroffenerAbteilung;
	}

	public String getAnzahlLeiharbeiterInBetroffenerAbteilung() {
		return anzahlLeiharbeiterInBetroffenerAbteilung;
	}

	public void setAnzahlLeiharbeiterInBetroffenerAbteilung(String anzahlLeiharbeiterInBetroffenerAbteilung) {
		this.anzahlLeiharbeiterInBetroffenerAbteilung = anzahlLeiharbeiterInBetroffenerAbteilung;
	}

	public String getAnzahlArbeitnehmerMitEntgeltAusfall() {
		return anzahlArbeitnehmerMitEntgeltAusfall;
	}

	public void setAnzahlArbeitnehmerMitEntgeltAusfall(String anzahlArbeitnehmerMitEntgeltAusfall) {
		this.anzahlArbeitnehmerMitEntgeltAusfall = anzahlArbeitnehmerMitEntgeltAusfall;
	}

	public String getAngabenArbeitsausfall() {
		return angabenArbeitsausfall;
	}

	public void setAngabenArbeitsausfall(String angabenArbeitsausfall) {
		this.angabenArbeitsausfall = angabenArbeitsausfall;
	}

	public String getAngabenArbeitsausfallAnlage() {
		return angabenArbeitsausfallAnlage;
	}

	public void setAngabenArbeitsausfallAnlage(String angabenArbeitsausfallAnlage) {
		this.angabenArbeitsausfallAnlage = angabenArbeitsausfallAnlage;
	}

	public boolean isAuchUeblicheUrsachenFuerAusfall() {
		return auchUeblicheUrsachenFuerAusfall;
	}

	public void setAuchUeblicheUrsachenFuerAusfall(boolean auchUeblicheUrsachenFuerAusfall) {
		this.auchUeblicheUrsachenFuerAusfall = auchUeblicheUrsachenFuerAusfall;
	}

	public String getOrtDatum() {
		return ortDatum;
	}

	public void setOrtDatum(String ortDatum) {
		this.ortDatum = ortDatum;
	}

	@Override
	public String toString() {
		return "AnzeigeArbeitsausfall [betriebsanschrift=" + betriebsanschrift + ", zeitraumVon=" + zeitraumVon
				+ ", zeitraumBis=" + zeitraumBis + "]";
	}

	// generated by https://riversun.github.io/java-builder/
	public static class Builder {

		private String agenturFuerArbeitAnschrift;
		private String stammNrKug;
		private String abteilungsNr;
		private String betriebsNr;
		private KontaktDaten betriebsanschrift;
		private KontaktDaten betriebsanschriftAnsprechpartner;
		private KontaktDaten lohnabrechnungsstelle;
		private KontaktDaten lohnabrechnungsstelleAnsprechpartner;
		private String branche;
		private MonatJahr zeitraumVon;
		private MonatJahr zeitraumBis;
		private String abteilungsBeschraenkung;
		private String vollarbeitArbeitszeit;
		private String kurzarbeitArbeitszeit;
		private boolean unternehmenAelterAlsEinJahr;
		private String unternehmenGruendungsdatum;
		private TarifvertragKenndaten arbeiterTarifvertrag;
		private TarifvertragKenndaten angestellteTarifvertrag;
		private boolean tarifvertragHatAnkuendigungsfrist;
		private String tarifvertragAnkuendigungZ1;
		private String tarifvertragAnkuendigungZ2;
		private boolean betriebNichtTarifgebunden;
		private boolean betriebsratVorhanden;
		private boolean kurzarbeitVereinbartDurchBetriebsvereinbarung;
		private boolean kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern;
		private boolean kurzarbeitVereinbartDurchAenderungskuendigung;
		private String kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm;
		private String kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu;
		private boolean kurzarbeitVereinbartDurchSonstiges;
		private String kurzarbeitVereinbartAnmerkungen;
		private String anzahlArbeitnehmerInBetroffenerAbteilung;
		private String anzahlLeiharbeiterInBetroffenerAbteilung;
		private String anzahlArbeitnehmerMitEntgeltAusfall;
		private String angabenArbeitsausfall;
		private boolean auchUeblicheUrsachenFuerAusfall;
		private String angabenArbeitsausfallAnlage;
		private String ortDatum;

		public Builder() {
		}

		public Builder agenturFuerArbeitAnschrift(String agenturFuerArbeitAnschrift) {
			this.agenturFuerArbeitAnschrift = agenturFuerArbeitAnschrift;
			return Builder.this;
		}

		public Builder stammNrKug(String stammNrKug) {
			this.stammNrKug = stammNrKug;
			return Builder.this;
		}

		public Builder abteilungsNr(String abteilungsNr) {
			this.abteilungsNr = abteilungsNr;
			return Builder.this;
		}

		public Builder betriebsNr(String betriebsNr) {
			this.betriebsNr = betriebsNr;
			return Builder.this;
		}

		public Builder betriebsanschrift(KontaktDaten betriebsanschrift) {
			this.betriebsanschrift = betriebsanschrift;
			return Builder.this;
		}

		public Builder betriebsanschriftAnsprechpartner(KontaktDaten betriebsanschriftAnsprechpartner) {
			this.betriebsanschriftAnsprechpartner = betriebsanschriftAnsprechpartner;
			return Builder.this;
		}

		public Builder lohnabrechnungsstelle(KontaktDaten lohnabrechnungsstelle) {
			this.lohnabrechnungsstelle = lohnabrechnungsstelle;
			return Builder.this;
		}

		public Builder lohnabrechnungsstelleAnsprechpartner(KontaktDaten lohnabrechnungsstelleAnsprechpartner) {
			this.lohnabrechnungsstelleAnsprechpartner = lohnabrechnungsstelleAnsprechpartner;
			return Builder.this;
		}

		public Builder branche(String branche) {
			this.branche = branche;
			return Builder.this;
		}

		public Builder zeitraumVon(MonatJahr zeitraumVon) {
			this.zeitraumVon = zeitraumVon;
			return Builder.this;
		}

		public Builder zeitraumBis(MonatJahr zeitraumBis) {
			this.zeitraumBis = zeitraumBis;
			return Builder.this;
		}

		public Builder abteilungsBeschraenkung(String abteilungsBeschraenkung) {
			this.abteilungsBeschraenkung = abteilungsBeschraenkung;
			return Builder.this;
		}

		public Builder vollarbeitArbeitszeit(String vollarbeitArbeitszeit) {
			this.vollarbeitArbeitszeit = vollarbeitArbeitszeit;
			return Builder.this;
		}

		public Builder kurzarbeitArbeitszeit(String kurzarbeitArbeitszeit) {
			this.kurzarbeitArbeitszeit = kurzarbeitArbeitszeit;
			return Builder.this;
		}

		public Builder unternehmenAelterAlsEinJahr(boolean unternehmenAelterAlsEinJahr) {
			this.unternehmenAelterAlsEinJahr = unternehmenAelterAlsEinJahr;
			return Builder.this;
		}

		public Builder unternehmenGruendungsdatum(String unternehmenGruendungsdatum) {
			this.unternehmenGruendungsdatum = unternehmenGruendungsdatum;
			return Builder.this;
		}

		public Builder arbeiterTarifvertrag(TarifvertragKenndaten arbeiterTarifvertrag) {
			this.arbeiterTarifvertrag = arbeiterTarifvertrag;
			return Builder.this;
		}

		public Builder angestellteTarifvertrag(TarifvertragKenndaten angestellteTarifvertrag) {
			this.angestellteTarifvertrag = angestellteTarifvertrag;
			return Builder.this;
		}

		public Builder tarifvertragHatAnkuendigungsfrist(boolean tarifvertragHatAnkuendigungsfrist) {
			this.tarifvertragHatAnkuendigungsfrist = tarifvertragHatAnkuendigungsfrist;
			return Builder.this;
		}

		public Builder tarifvertragAnkuendigungZ1(String tarifvertragAnkuendigungZ1) {
			this.tarifvertragAnkuendigungZ1 = tarifvertragAnkuendigungZ1;
			return Builder.this;
		}

		public Builder tarifvertragAnkuendigungZ2(String tarifvertragAnkuendigungZ2) {
			this.tarifvertragAnkuendigungZ2 = tarifvertragAnkuendigungZ2;
			return Builder.this;
		}

		public Builder betriebNichtTarifgebunden(boolean betriebNichtTarifgebunden) {
			this.betriebNichtTarifgebunden = betriebNichtTarifgebunden;
			return Builder.this;
		}

		public Builder betriebsratVorhanden(boolean betriebsratVorhanden) {
			this.betriebsratVorhanden = betriebsratVorhanden;
			return Builder.this;
		}

		public Builder kurzarbeitVereinbartDurchBetriebsvereinbarung(
				boolean kurzarbeitVereinbartDurchBetriebsvereinbarung) {
			this.kurzarbeitVereinbartDurchBetriebsvereinbarung = kurzarbeitVereinbartDurchBetriebsvereinbarung;
			return Builder.this;
		}

		public Builder kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern(
				boolean kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern) {
			this.kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern = kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern;
			return Builder.this;
		}

		public Builder kurzarbeitVereinbartDurchAenderungskuendigung(
				boolean kurzarbeitVereinbartDurchAenderungskuendigung) {
			this.kurzarbeitVereinbartDurchAenderungskuendigung = kurzarbeitVereinbartDurchAenderungskuendigung;
			return Builder.this;
		}

		public Builder kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm(
				String kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm) {
			this.kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm = kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm;
			return Builder.this;
		}

		public Builder kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu(
				String kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu) {
			this.kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu = kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu;
			return Builder.this;
		}

		public Builder kurzarbeitVereinbartDurchSonstiges(boolean kurzarbeitVereinbartDurchSonstiges) {
			this.kurzarbeitVereinbartDurchSonstiges = kurzarbeitVereinbartDurchSonstiges;
			return Builder.this;
		}

		public Builder kurzarbeitVereinbartAnmerkungen(String kurzarbeitVereinbartAnmerkungen) {
			this.kurzarbeitVereinbartAnmerkungen = kurzarbeitVereinbartAnmerkungen;
			return Builder.this;
		}

		public Builder anzahlArbeitnehmerInBetroffenerAbteilung(String anzahlArbeitnehmerInBetroffenerAbteilung) {
			this.anzahlArbeitnehmerInBetroffenerAbteilung = anzahlArbeitnehmerInBetroffenerAbteilung;
			return Builder.this;
		}

		public Builder anzahlLeiharbeiterInBetroffenerAbteilung(String anzahlLeiharbeiterInBetroffenerAbteilung) {
			this.anzahlLeiharbeiterInBetroffenerAbteilung = anzahlLeiharbeiterInBetroffenerAbteilung;
			return Builder.this;
		}

		public Builder anzahlArbeitnehmerMitEntgeltAusfall(String anzahlArbeitnehmerMitEntgeltAusfall) {
			this.anzahlArbeitnehmerMitEntgeltAusfall = anzahlArbeitnehmerMitEntgeltAusfall;
			return Builder.this;
		}

		public Builder angabenArbeitsausfall(String angabenArbeitsausfall) {
			this.angabenArbeitsausfall = angabenArbeitsausfall;
			return Builder.this;
		}

		public Builder auchUeblicheUrsachenFuerAusfall(boolean auchUeblicheUrsachenFuerAusfall) {
			this.auchUeblicheUrsachenFuerAusfall = auchUeblicheUrsachenFuerAusfall;
			return Builder.this;
		}

		public Builder angabenArbeitsausfallAnlage(String angabenArbeitsausfallAnlage) {
			this.angabenArbeitsausfallAnlage = angabenArbeitsausfallAnlage;
			return Builder.this;
		}

		public Builder ortDatum(String ortDatum) {
			this.ortDatum = ortDatum;
			return Builder.this;
		}

		public AnzeigeArbeitsausfall build() {
			return new AnzeigeArbeitsausfall(this);
		}
	}

	private AnzeigeArbeitsausfall(Builder builder) {
		this.agenturFuerArbeitAnschrift = builder.agenturFuerArbeitAnschrift;
		this.stammNrKug = builder.stammNrKug;
		this.abteilungsNr = builder.abteilungsNr;
		this.betriebsNr = builder.betriebsNr;
		this.betriebsanschrift = builder.betriebsanschrift;
		this.betriebsanschriftAnsprechpartner = builder.betriebsanschriftAnsprechpartner;
		this.lohnabrechnungsstelle = builder.lohnabrechnungsstelle;
		this.lohnabrechnungsstelleAnsprechpartner = builder.lohnabrechnungsstelleAnsprechpartner;
		this.branche = builder.branche;
		this.zeitraumVon = builder.zeitraumVon;
		this.zeitraumBis = builder.zeitraumBis;
		this.abteilungsBeschraenkung = builder.abteilungsBeschraenkung;
		this.vollarbeitArbeitszeit = builder.vollarbeitArbeitszeit;
		this.kurzarbeitArbeitszeit = builder.kurzarbeitArbeitszeit;
		this.unternehmenAelterAlsEinJahr = builder.unternehmenAelterAlsEinJahr;
		this.unternehmenGruendungsdatum = builder.unternehmenGruendungsdatum;
		this.arbeiterTarifvertrag = builder.arbeiterTarifvertrag;
		this.angestellteTarifvertrag = builder.angestellteTarifvertrag;
		this.tarifvertragHatAnkuendigungsfrist = builder.tarifvertragHatAnkuendigungsfrist;
		this.tarifvertragAnkuendigungZ1 = builder.tarifvertragAnkuendigungZ1;
		this.tarifvertragAnkuendigungZ2 = builder.tarifvertragAnkuendigungZ2;
		this.betriebNichtTarifgebunden = builder.betriebNichtTarifgebunden;
		this.betriebsratVorhanden = builder.betriebsratVorhanden;
		this.kurzarbeitVereinbartDurchBetriebsvereinbarung = builder.kurzarbeitVereinbartDurchBetriebsvereinbarung;
		this.kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern = builder.kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern;
		this.kurzarbeitVereinbartDurchAenderungskuendigung = builder.kurzarbeitVereinbartDurchAenderungskuendigung;
		this.kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm = builder.kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm;
		this.kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu = builder.kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu;
		this.kurzarbeitVereinbartDurchSonstiges = builder.kurzarbeitVereinbartDurchSonstiges;
		this.kurzarbeitVereinbartAnmerkungen = builder.kurzarbeitVereinbartAnmerkungen;
		this.anzahlArbeitnehmerInBetroffenerAbteilung = builder.anzahlArbeitnehmerInBetroffenerAbteilung;
		this.anzahlLeiharbeiterInBetroffenerAbteilung = builder.anzahlLeiharbeiterInBetroffenerAbteilung;
		this.anzahlArbeitnehmerMitEntgeltAusfall = builder.anzahlArbeitnehmerMitEntgeltAusfall;
		this.angabenArbeitsausfall = builder.angabenArbeitsausfall;
		this.auchUeblicheUrsachenFuerAusfall = builder.auchUeblicheUrsachenFuerAusfall;
		this.angabenArbeitsausfallAnlage = builder.angabenArbeitsausfallAnlage;
		this.ortDatum = builder.ortDatum;
	}
}

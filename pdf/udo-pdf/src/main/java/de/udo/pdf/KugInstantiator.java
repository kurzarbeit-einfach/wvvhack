package de.udo.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDComboBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;

public class KugInstantiator {

	public static final KugInstantiator INSTANCE = new KugInstantiator();
	private PDDocument kugDocument;
	private PDDocumentCatalog kugDocumentCatalog;
	private PDAcroForm kugAcroForm;

	private KugInstantiator() {
		try {
			this.load();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void load() throws FileNotFoundException, IOException {
		kugDocument = PDDocument.load(KugUtils.INSTANCE.getTemplateResource());
		kugDocumentCatalog = kugDocument.getDocumentCatalog();
		kugAcroForm = kugDocumentCatalog.getAcroForm();
	}

	public File saveInstance(AnzeigeArbeitsausfall anzeige, String fileName) {
		File retVal = new File(fileName);
		try (FileOutputStream fileOutputStream = new FileOutputStream(retVal);) {
			ByteArrayOutputStream createInstance = this.createInstance(anzeige);
			createInstance.writeTo(fileOutputStream);
			return retVal;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// FIXME: This cannot be thread safe!
	public ByteArrayOutputStream createInstance(AnzeigeArbeitsausfall anzeige) throws IOException {
		ByteArrayOutputStream retVal = new ByteArrayOutputStream();

		// Header
		this.setTextValue("postanschrift", anzeige.getAgenturFuerArbeitAnschrift());
		this.setTextValue("stammnr", anzeige.getStammNrKug());
		this.setTextValue("ableitungsnr", anzeige.getAbteilungsNr());
		this.setTextValue("betriebsnummer", anzeige.getBetriebsNr());

		// A. Anschrift des Betriebes
		this.fillAdresseUndKontaktInfos("betriebsanschrift", "betriebsanschrift_kontakt",
				anzeige.getBetriebsanschrift());
		this.fillAdresseUndKontaktInfos("betrieb_ansprechpartner", "betrieb_ansprechpartner_kontakt",
				anzeige.getBetriebsanschriftAnsprechpartner());
		this.fillAdresseUndKontaktInfos("lohnabrechnungsanschrift", "lohnabrechnungsanschrift_kontakt",
				anzeige.getLohnabrechnungsstelle());
		this.fillAdresseUndKontaktInfos("la_ansprechpartner", "la_ansprechpartner_kontakt",
				anzeige.getLohnabrechnungsstelleAnsprechpartner());

		this.setTextValue("branche", anzeige.getBranche());

		// B. Zeitraum der geplanten Arbeitszeitreduzierung
		this.setTextValue("von_monat", anzeige.getZeitraumVon().getMonat());
		this.setTextValue("von_jahr", anzeige.getZeitraumVon().getJahr());
		this.setTextValue("bin_monat", anzeige.getZeitraumBis().getMonat());
		this.setTextValue("bis_jahr", anzeige.getZeitraumBis().getJahr());
		this.fillAbteilungsBeschraenkung(anzeige.getAbteilungsBeschraenkung());

		// C. Angaben zur Arbeitszeit
		this.setTextValue("wochenstunden_normal", anzeige.getVollarbeitArbeitszeit());
		this.setTextValue("wochenstunden_kurz", anzeige.getKurzarbeitArbeitszeit());

		// D. Angaben zum Betrieb
		this.fillUnternehmensAlter(anzeige);
		this.fillTarifvertrag("tv_arbeiter_bezeichnung", "tv_arbeiter_std", "tv_arbeiter_klausel",
				anzeige.getArbeiterTarifvertrag());
		this.fillTarifvertrag("tv_angestellte_bezeichnung", "tv_angestellte_std", "tv_angestellte_klausel",
				anzeige.getAngestellteTarifvertrag());

		this.setCheckboxValue("tv_ankuendigung_ja",anzeige.isTarifvertragHatAnkuendigungsfrist());
		this.setCheckboxValue("tv_ankuendigung_nein",!anzeige.isTarifvertragHatAnkuendigungsfrist());
		this.setTextValue("tv_ankuendigung_1", anzeige.getTarifvertragAnkuendigungZ1());
		this.setTextValue("tv_ankuendigung_2", anzeige.getTarifvertragAnkuendigungZ2());
		this.setCheckboxValue("tv_betrieb_nicht_tarifgebunden", anzeige.isBetriebNichtTarifgebunden());

		this.setCheckboxValue("betriebsrat_ja", anzeige.isBetriebsratVorhanden());
		this.setCheckboxValue("betriebsrat_nein", !anzeige.isBetriebsratVorhanden());
		this.setCheckboxValue("vereinbart_betriebsvereinbarung",
				anzeige.isKurzarbeitVereinbartDurchBetriebsvereinbarung());
		this.setCheckboxValue("vereinbart_vereinbarung",
				anzeige.isKurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern());
		this.setCheckboxValue("vereinbart_aenderungskuendigung",
				anzeige.isKurzarbeitVereinbartDurchAenderungskuendigung());

		this.setTextValue("vereinbart_am", anzeige.getKurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm());
		this.setTextValue("vereinbart_wirkung_am", anzeige.getKurzarbeitVereinbartDurchAenderungskuendigungWirksamZu());
		this.setCheckboxValue("vereinbart_sonstiges", anzeige.isKurzarbeitVereinbartDurchSonstiges());
		this.setTextValue("vereinbart_sonstiges_text", anzeige.getKurzarbeitVereinbartAnmerkungen());

		this.setTextValue("anzahl_arbeitnehmer_insg", anzeige.getAnzahlArbeitnehmerInBetroffenerAbteilung());
		this.setTextValue("anzahl_arbeitnehmer_insg_leiharbeiter",
				anzeige.getAnzahlLeiharbeiterInBetroffenerAbteilung());
		this.setTextValue("anzahl_arbeitnehmer_betroffen_kurzarbeit", anzeige.getAnzahlArbeitnehmerMitEntgeltAusfall());

		// E. Angaben zum Arbeitsausfall
		this.setTextValue("begruendung", anzeige.getAngabenArbeitsausfall());
		this.setCheckboxValue("branchenueblich_ja", anzeige.isAuchUeblicheUrsachenFuerAusfall());
		this.setCheckboxValue("branchenueblich_nein", !anzeige.isAuchUeblicheUrsachenFuerAusfall());
		this.setTextValue("begruendung_anlage", anzeige.getAngabenArbeitsausfallAnlage());

		// Footer
		this.setTextValue("unterschrift_ort_datum", anzeige.getOrtDatum());

		kugDocument.save(retVal);
		return retVal;
	}

	private void fillAdresseUndKontaktInfos(String adressenFeld, String kontaktDatenFeld, KontaktDaten kontaktDaten)
			throws IOException {
		this.setTextValue(adressenFeld, kontaktDaten.getNameAnschrift());
		this.setTextValue(kontaktDatenFeld, kontaktDaten.getZusaetzlicheKontaktinfos());
	}

	private void fillAbteilungsBeschraenkung(String abteilungsBeschraenkung) throws IOException {
		if (StringUtils.isBlank(abteilungsBeschraenkung)) {
			this.setCheckboxValue("betrifft_gesamtbetrieb", true);
			this.setCheckboxValue("betrifft_abteilung", false);
		} else {
			this.setCheckboxValue("betrifft_gesamtbetrieb", false);
			this.setCheckboxValue("betrifft_abteilung", true);
			this.setTextValue("abteilungsname", abteilungsBeschraenkung);
		}
	}

	private void fillUnternehmensAlter(AnzeigeArbeitsausfall anzeige) throws IOException {
		if (anzeige.isUnternehmenAelterAlsEinJahr()) {
			this.setCheckboxValue("unternehmen_ex_laenger_1_jahr", true);
			this.setTextValue("unternehmen_ex_seit", "");
		} else {
			this.setTextValue("unternehmen_ex_seit", anzeige.getUnternehmenGruendungsdatum());
		}
	}

	private void fillTarifvertrag(String bezeichnungKey, String arbeitszeitKey, String klauselKey,
			TarifvertragKenndaten tarifvertrag) throws IOException {
		this.setTextValue(bezeichnungKey, tarifvertrag.getBezeichnung());
		this.setTextValue(arbeitszeitKey, tarifvertrag.getNormaleArbeitszeit());
		this.setTextValue(klauselKey, tarifvertrag.getHatKurzarbeitKlausel());
	}

	private void setTextValue(String key, String value) throws IOException {
		PDField field = kugAcroForm.getField(key);
		if (field == null)
			new Object();
		field.setValue(value);
	}

	@SuppressWarnings("unused")
	private void setComboValue(String key, String value) throws IOException {
		PDComboBox field = (PDComboBox) kugAcroForm.getField(key);
		if (!field.getOptions().contains(value))
			throw new RuntimeException(
					String.format("Cannot set value '%s' on element '%s'; only values allowed are %s.", value, key,
							field.getOptions().toString()));
		field.setValue(value);
	}

	@SuppressWarnings("unused")
	private void setRadioValue(String key, String value) throws IOException {
		PDRadioButton field = (PDRadioButton) kugAcroForm.getField(key);
		if (!field.getOnValues().contains(value))
			throw new RuntimeException(
					String.format("Cannot set value '%s' on element '%s'; only values allowed are %s.", value, key,
							field.getOnValues().toString()));
		field.setValue(value);
	}

	private void setCheckboxValue(String key, boolean value) throws IOException {
		PDCheckBox field = (PDCheckBox) kugAcroForm.getField(key);
		if (field.getOnValues().size() != 1)
			throw new RuntimeException("Did not expect this strange case...");
		String onValue = field.getOnValues().iterator().next();
		field.setValue(value ? onValue : "Off");
	}

}

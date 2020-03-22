package de.udo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.udo.pdf.AnzeigeArbeitsausfall;
import de.udo.pdf.KontaktDaten;
import de.udo.pdf.KugInstantiator;
import de.udo.pdf.KugUtils;
import de.udo.pdf.MonatJahr;
import de.udo.pdf.TarifvertragKenndaten;

public class AppTest {
	private static final boolean DELETE_AFTER_TEST = false;
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
			.withZone(ZoneId.systemDefault());

	
	private String fileName;
	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void makeFilename() {
		this.fileName = FORMATTER.format(Instant.now()) + ".pdf";
		deleteFileIfExists(this.fileName);
	}

	@After
	public void deleteTestFile() {
		if (DELETE_AFTER_TEST)
			deleteFileIfExists(this.fileName);
	}

	private void deleteFileIfExists(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			boolean deleted = file.delete();
			if (!deleted)
				throw new RuntimeException(String.format("Could not delete file %s.", this.fileName));
		}
	}

	@Test
	@Ignore
	public void createMapping() throws FileNotFoundException, IOException {
		KugUtils.INSTANCE.createMapping("./tmp/mapping_template_udo");
	}
	
	
	@Test
	public void case1() throws IOException {
		AnzeigeArbeitsausfall anzeige = createBaseAnzeigeArbeitsausfall();
		this.writeJsonString("./sample/case1.json", anzeige);
		KugInstantiator.INSTANCE.saveInstance(anzeige , "tmp/" + this.fileName);
		Assert.assertTrue(new File("tmp/" + this.fileName).exists());
	}
	
	@Test
	public void case2() throws IOException {
		AnzeigeArbeitsausfall anzeige = createBaseAnzeigeArbeitsausfall();
		//Dann muss auch die Checkbox anders aussehen!
		anzeige.setAbteilungsBeschraenkung(null);
		anzeige.setUnternehmenAelterAlsEinJahr(true);
		anzeige.setUnternehmenGruendungsdatum(null);
		anzeige.setBetriebsratVorhanden(false);
		anzeige.setKurzarbeitVereinbartDurchBetriebsvereinbarung(false);
		anzeige.setAuchUeblicheUrsachenFuerAusfall(false);
		anzeige.setKurzarbeitVereinbartDurchAenderungskuendigung(false);
		anzeige.setKurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm(null);
		anzeige.setKurzarbeitVereinbartDurchAenderungskuendigungWirksamZu(null);
		this.writeJsonString("./sample/case2.json", anzeige);
		KugInstantiator.INSTANCE.saveInstance(anzeige , "tmp/" + this.fileName);
		Assert.assertTrue(new File("tmp/" + this.fileName).exists());
	}

	
	private void writeJsonString(String fileName, AnzeigeArbeitsausfall anzeige) throws IOException {
		String anzeigeAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(anzeige);
		try (FileOutputStream writer = new FileOutputStream(fileName);
				OutputStreamWriter outputWriter = new OutputStreamWriter(writer, StandardCharsets.UTF_8)) {
			outputWriter.write(anzeigeAsString);
		}
	}
	
	private AnzeigeArbeitsausfall createBaseAnzeigeArbeitsausfall() {
		KontaktDaten betriebsanschrift = new KontaktDaten.Builder()
				.nameAnschrift("Die WirVsVirus Hackathon GmbH + Co. KG\n"
						+ "Allee der widerstandsfähigen Rachenflora 42a\n"
						+ "12321 Porta Resistenzia")
				.zusaetzlicheKontaktinfos("T: 024 123456\n"
						+ "info@wirvsvirushackathon.org")
				.build();
		KontaktDaten betriebsanschriftAnsprechpartner = new KontaktDaten.Builder()
				.nameAnschrift("Fr. Martina Mustermann")
				.zusaetzlicheKontaktinfos("T: 024 123456-999\n"
						+ "martina.marx@wirvsvirushackathon.org")
				.build();
		
		KontaktDaten lohnabrechnungsstelle = new KontaktDaten.Builder()
				.nameAnschrift("Lohnabrechnung des beständigen Immunsystems e.V.\n"
						+ "Bazillengasse 69\n"
						+ "98789 Sankt Aspirin")
				.zusaetzlicheKontaktinfos("T: 07898 654321\n"
						+ "mail@lar-immun.com")
				.build();
		KontaktDaten lohnabrechnungsstelleAnsprechpartner = new KontaktDaten.Builder()
				.nameAnschrift("Hr. Jean-Luc de la Santé")
				.zusaetzlicheKontaktinfos("T: 07898 654321-555\n"
						+ "jean-luc.delasante@lar-immun.com")
				.build();
		
		AnzeigeArbeitsausfall anzeige = new AnzeigeArbeitsausfall.Builder()
			// Header
			.agenturFuerArbeitAnschrift("Anschrift der Agentur fuer Arbeit")
			.stammNrKug("123456789")
			.abteilungsNr("9876")
			.betriebsNr("abcd")
			
			// A. Anschrift des Betriebes
			.betriebsanschrift(betriebsanschrift)
			.betriebsanschriftAnsprechpartner(betriebsanschriftAnsprechpartner)
			.lohnabrechnungsstelle(lohnabrechnungsstelle)
			.lohnabrechnungsstelleAnsprechpartner(lohnabrechnungsstelleAnsprechpartner)
			.branche("Die Mega-Branche")

			// B. Zeitraum der geplanten Arbeitszeitreduzierung
			.zeitraumVon(new MonatJahr.Builder().monat("April").jahr("2020").build())
			.zeitraumBis(new MonatJahr.Builder().monat("Januar").jahr("2021").build())
			.abteilungsBeschraenkung("Reinigung")
			
			// C. Angaben zur Arbeitszeit
			.vollarbeitArbeitszeit("38,5")
			.kurzarbeitArbeitszeit("20")
			
			// D. Angaben zum Betrieb
			.unternehmenAelterAlsEinJahr(false)
			.unternehmenGruendungsdatum("01.01.2020")
			.arbeiterTarifvertrag(new TarifvertragKenndaten.Builder().bezeichnung("TV der fleißigen Arbeiter").normaleArbeitszeit("35,6").hatKurzarbeitKlausel("Ja").build())
			.angestellteTarifvertrag(new TarifvertragKenndaten.Builder().bezeichnung("TV der langsamen Angestellten").normaleArbeitszeit("24").hatKurzarbeitKlausel("Nein").build())
			.tarifvertragHatAnkuendigungsfrist(true)
			.tarifvertragAnkuendigungZ1("tarifvertragAnkuendigungZ1")
			.tarifvertragAnkuendigungZ2("tarifvertragAnkuendigungZ2")
			.betriebNichtTarifgebunden(true)
			
			.betriebsratVorhanden(true)
			.kurzarbeitVereinbartDurchBetriebsvereinbarung(true)
			.kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern(true)
			.kurzarbeitVereinbartDurchAenderungskuendigung(true)
			.kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm("01.03.2020")
			.kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu("01.04.2020")
			.kurzarbeitVereinbartDurchSonstiges(true)
			.kurzarbeitVereinbartAnmerkungen("durch konsequentes Beleidigen")
			.anzahlArbeitnehmerInBetroffenerAbteilung("32")
			.anzahlLeiharbeiterInBetroffenerAbteilung("16")
			.anzahlArbeitnehmerMitEntgeltAusfall("24")
			
			// E. Angaben zum Arbeitsausfall
			.angabenArbeitsausfall("Bitte entnehmen Sie die Angaben zum Arbeitsausfall der Anlage.")
			.auchUeblicheUrsachenFuerAusfall(true)
			.angabenArbeitsausfallAnlage("Es ist ja alles so furchtbar.\nIch bekomme keine Gummibäume mehr...\n...und mein Schaukelpferd ist jetzt auch in Quarantäne.")
			
			// Footer
			.ortDatum("Wuhan, 16.11.2019")
			
			.build();
		return anzeige;
	}
	
}

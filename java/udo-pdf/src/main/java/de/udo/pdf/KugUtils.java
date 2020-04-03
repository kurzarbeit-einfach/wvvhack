package de.udo.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDComboBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;

public class KugUtils {

//	public static final String TEMPLATE_FILENAME = "template_udo.pdf";
	public static final String TEMPLATE_ODB_FILENAME = "template_udo_oDB.pdf";

	public static final KugUtils INSTANCE = new KugUtils();

	private KugUtils() {

	}

	public InputStream getTemplateResource() {
		InputStream resourceAsStream = this.getClass().getClassLoader()
				.getResourceAsStream(KugUtils.TEMPLATE_ODB_FILENAME);
		return resourceAsStream;
	}

	public void createMapping(String filePrefix) throws FileNotFoundException, IOException {

		PDDocument kugDocument;
		PDDocumentCatalog kugDocumentCatalog;
		PDAcroForm kugAcroForm;

		byte[] template = IOUtils.toByteArray(getTemplateResource());
		kugDocument = PDDocument.load(new ByteArrayInputStream(template));
		kugDocumentCatalog = kugDocument.getDocumentCatalog();
		kugAcroForm = kugDocumentCatalog.getAcroForm();

		StringBuffer csvString = new StringBuffer();

		try (FileOutputStream fileOutputStream = new FileOutputStream(new File(filePrefix + ".pdf"));
				FileWriter csvWriter = new FileWriter(filePrefix + ".csv");
				ByteArrayOutputStream retVal = new ByteArrayOutputStream();) {
			int i = 0;
			int j = 0;
			Iterator<PDField> fieldIterator = kugAcroForm.getFieldIterator();
			csvString.append(String.format("%s;%s;%s;%s;%s%n", "ownId", "getFullyQualifiedName()", "getPartialName()",
					"getAlternateFieldName()", "getFieldType()"));
			while (fieldIterator.hasNext()) {
				if (i % 26 == 0)
					j++;
				PDField next = fieldIterator.next();
				char c = (char) ((int) 'a' + (i % 26));
				String ownId = String.valueOf(j) + String.valueOf(c);
				if (next instanceof PDComboBox) {
					PDComboBox cb = (PDComboBox) next;
					@SuppressWarnings("unused")
					List<String> options = cb.getOptions();
				}
				if (next instanceof PDRadioButton) {
					PDRadioButton rb = (PDRadioButton) next;
					rb.getValueAsString();
					new Object();

				}
				if (ownId.equals("1s"))
					new Object();
				if (next.getFieldType() != null && next.getFieldType().equals("Tx"))
					next.setValue(ownId);
				csvString.append(String.format("%s;\"'%s'\";%s;%s;%s%n", ownId, next.getFullyQualifiedName(),
						next.getPartialName(),
						(next.getAlternateFieldName() != null ? next.getAlternateFieldName().replace("\r", " ") : ""),
						next.getFieldType()));

				i++;

			}
			kugDocument.save(retVal);
			retVal.writeTo(fileOutputStream);
			csvWriter.append(csvString.toString());
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}

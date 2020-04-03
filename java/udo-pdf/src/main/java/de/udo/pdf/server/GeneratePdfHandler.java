package de.udo.pdf.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.udo.pdf.AnzeigeArbeitsausfall;
import de.udo.pdf.KugInstantiator;

public class GeneratePdfHandler extends AbstractHandler {

	private static final Logger LOG = Log.getLogger(GeneratePdfHandler.class);

	private ObjectMapper mapper = new ObjectMapper();

	public GeneratePdfHandler() {
		mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try {
			request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			LOG.info(String.format("Received request; parsing AnzeigeArbeitsausfall from JSON"));
			AnzeigeArbeitsausfall anzeige = mapper.readValue(reader, AnzeigeArbeitsausfall.class);

			response.setContentType("application/pdf; charset=binary");
			response.setStatus(HttpServletResponse.SC_OK);
			LOG.info(String.format("Creating PDF."));
			try (ByteArrayOutputStream createInstance = KugInstantiator.INSTANCE.createInstance(anzeige);) {
				LOG.info(String.format("Sending PDF."));
				createInstance.writeTo(response.getOutputStream());
				baseRequest.setHandled(true);
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LOG.warn(sw.toString(), e);
			String stacktrace = ExceptionUtils.getStackTrace(e);
			response.setContentType("application/json");
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR_500, stacktrace);
		}
	}
}

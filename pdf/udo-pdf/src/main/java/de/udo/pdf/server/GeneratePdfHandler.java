package de.udo.pdf.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.udo.pdf.AnzeigeArbeitsausfall;
import de.udo.pdf.KugInstantiator;

public class GeneratePdfHandler extends AbstractHandler {

	private static final Logger LOG = Log.getLogger(GeneratePdfHandler.class); 
	
	private ObjectMapper mapper = new ObjectMapper();

	public GeneratePdfHandler() {
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
			ByteArrayOutputStream createInstance = KugInstantiator.INSTANCE.createInstance(anzeige);
			LOG.info(String.format("Sending PDF."));
			createInstance.writeTo(response.getOutputStream());
			baseRequest.setHandled(true);
		} catch (Exception e) {
			LOG.warn(e);
			e.printStackTrace();
			String stacktrace = ExceptionUtils.getStackTrace(e);
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR_500, stacktrace);
		}
	}
}

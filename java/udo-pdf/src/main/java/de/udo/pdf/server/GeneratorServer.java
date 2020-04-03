package de.udo.pdf.server;

import java.io.InputStream;
import java.util.logging.LogManager;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ErrorHandler;

public class GeneratorServer {

	final static LogManager logManager = LogManager.getLogManager();

	public static Server createServer(int port) {
		Server server = new Server(port);
		return server;
	}

	public static void main(String[] args) throws Exception {

		try (final InputStream is = GeneratorServer.class.getClassLoader().getResourceAsStream("logging.properties")) {
			if (is == null)
				throw new RuntimeException("Cannot load logging.properties, which should be contained in jar.");
			logManager.readConfiguration(is);
			java.util.logging.Logger.getLogger(GeneratorServer.class.getCanonicalName())
					.info("Starting GeneratorServer...");
		}

		int port = 8080;
		Server server = createServer(port);
		server.setHandler(new GeneratePdfHandler());

		ErrorHandler errorHandler = new ErrorHandler();
		errorHandler.setShowStacks(true);
		server.addBean(errorHandler);

		server.start();
		server.join();
	}
}

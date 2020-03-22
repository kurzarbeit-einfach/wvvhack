package de.udo.pdf.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ErrorHandler;

public class GeneratorServer {

	public static Server createServer(int port) {
		Server server = new Server(port);
		return server;
	}

	public static void main(String[] args) throws Exception {
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

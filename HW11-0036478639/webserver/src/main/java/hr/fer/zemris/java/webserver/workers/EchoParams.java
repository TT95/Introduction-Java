package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker class which process request given from server.
 * Returns to client parameters given in request.
 * @author Teo Toplak
 *
 */
public class EchoParams implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) {
		Set<String> set = context.getParameterNames();
		String str="";
		for(String s : set) {
			str+=s+"\n";
		}
		try {
			context.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

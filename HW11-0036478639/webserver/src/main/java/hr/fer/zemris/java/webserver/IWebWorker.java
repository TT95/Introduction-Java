package hr.fer.zemris.java.webserver;

/**
 * Represents worker for server accepting clients.
 * Worker is able to procces request with context given.
 * @author Teo Toplak
 *
 */
public interface IWebWorker {

	/**
	 * Method which is used by worker for processing request
	 * from client.
	 * @param context conext 
	 */
	public void processRequest(RequestContext context);
}

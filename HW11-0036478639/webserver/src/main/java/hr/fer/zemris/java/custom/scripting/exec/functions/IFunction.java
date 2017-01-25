package hr.fer.zemris.java.custom.scripting.exec.functions;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class representing smart script function.
 * @author Teo Toplak
 *
 */
public interface IFunction {

	/**
	 * Method executing smart script function.
	 * @param multistack multistack
	 * @param stack stack
	 * @param requestContext context 
	 */
	public void exec(ObjectMultistack multistack, Stack<Object> stack, RequestContext requestContext);
}

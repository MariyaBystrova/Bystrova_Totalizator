package by.tr.totalizator.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Represents Command interface implementation of which provides appropriate realization of <code>execute</code> method.
 * 
 * @author Mariya Bystrova
 *
 */
public interface Command {
	/**
	 * Executes the request that is sent to the servlet and prepares the response object to be returned to the client.
	 * 
	 * @param request the {@link javax.servlet.http.HttpServletRequest} object that is sent to the servlet.
	 * @param response the {@link javax.servlet.http.HttpServletResponse} object that the servlet uses to return the headers to the client.
	 * @return a String value which represents the page or URL(URI).
	 * @see HttpServletRequest
	 * @see HttpServletResponse
	 */
	String execute (HttpServletRequest request, HttpServletResponse response);
}

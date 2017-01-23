package by.tr.totalizator.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;

/**
 * Implements {@link by.tr.totalizator.command.Command} to change local in the
 * session.
 * 
 * @author Mariya Bystrova
 */
public class LocalizeCommand implements Command {
	private final static String CURRENT_URL = "currentUrl";
	private final static String LOCAL = "local";
	private final static String LANG = "lang";
	private final static String LOCALHOST = "index.jsp";

	/**
	 * Provides the change language service that is available for every user.
	 * 
	 * @return an index.jsp page.
	 * 
	 * @see by.tr.totalizator.command.Command
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return LOCALHOST;
		}
		request.getSession(false).setAttribute(LOCAL, request.getParameter(LANG));

		if (request.getSession(false).getAttribute(CURRENT_URL) != null) {
			return request.getSession(false).getAttribute(CURRENT_URL).toString();
		} else {
			return LOCALHOST;
		}

	}

}

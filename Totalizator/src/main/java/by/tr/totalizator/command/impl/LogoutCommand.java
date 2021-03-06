package by.tr.totalizator.command.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;

/**
 * Implements {@link by.tr.totalizator.command.Command} to log out the system.
 * 
 * @author Mariya Bystrova
 */
public class LogoutCommand implements Command {
	private final static String URL_INDEX = "index.jsp";
	private final static String LOCAL = "local";

	/**
	 * Provides the log out service. Invalidates the current session, saving
	 * local details to cookie before this.
	 * 
	 * @return an index.jsp page.
	 * 
	 * @see by.tr.totalizator.command.Command
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) != null) {
			if (request.getSession(false).getAttribute(LOCAL) != null) {
				response.addCookie(new Cookie(LOCAL, request.getSession(false).getAttribute(LOCAL).toString()));
			}
			request.getSession(false).invalidate();
		}

		return URL_INDEX;
	}
}

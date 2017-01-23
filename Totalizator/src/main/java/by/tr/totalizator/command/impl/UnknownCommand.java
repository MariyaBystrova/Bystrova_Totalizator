package by.tr.totalizator.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;

/**
 * Implements {@link by.tr.totalizator.command.Command} for unknown commands.
 * 
 * @author Mariya Bystrova
 */
public class UnknownCommand implements Command {
	private final static String CURRENT_URL_ATTR = "currentUrl";
	private final static String CURRENT_URL = "Controller?command=unknown_command";

	/**
	 * Returns the ERROR page.
	 * 
	 * @return {@link by.tr.totalizator.controller.PageName.ERROR_PAGE}.
	 * 
	 * @see by.tr.totalizator.command.Command
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		if (request.getSession(false) != null) {
			request.getSession(false).setAttribute(CURRENT_URL_ATTR, CURRENT_URL);
		}
		return PageName.ERROR_PAGE;

	}

}

package by.tr.totalizator.command.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;

/**
 * Implements {@link by.tr.totalizator.command.Command} for a command to go to
 * the page for user's registration.
 * 
 * @author Mariya Bystrova
 */
public class GoToRegistrationCommand implements Command {
	private final static String CURRENT_URL_ATTR = "currentUrl";
	private final static String CURRENT_URL = "Controller?command=go-to-registration";

	/**
	 * Returns a registration page.
	 * 
	 * @return {@link by.tr.totalizator.controller.PageName.REGISTRATION_PAGE}.
	 * 
	 * @see by.tr.totalizator.command.Command
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		if (request.getSession(false) != null) {
			request.getSession(false).setAttribute(CURRENT_URL_ATTR, CURRENT_URL);
		}
		return PageName.REGISTRATION_PAGE;
	}
}

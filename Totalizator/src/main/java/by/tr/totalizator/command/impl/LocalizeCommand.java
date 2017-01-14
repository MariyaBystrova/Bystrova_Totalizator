package by.tr.totalizator.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;

public class LocalizeCommand implements Command {

	private final static String CURRENT_URL = "currentUrl";
	private final static String LOCAL = "local";
	private final static String LANG = "lang";
	private final static String LOCALHOST = "http://localhost:8080/Totalizator/index.jsp";

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

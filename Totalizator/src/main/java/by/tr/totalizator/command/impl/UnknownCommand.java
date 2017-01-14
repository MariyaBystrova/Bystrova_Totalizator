package by.tr.totalizator.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;

public class UnknownCommand implements Command {

	private final static String CURRENT_URL_ATTR = "currentUrl";
	private final static String CURRENT_URL = "http://localhost:8080/Totalizator/Controller?command=unknown_command";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		if (request.getSession(false) != null) {
			request.getSession(false).setAttribute(CURRENT_URL_ATTR, CURRENT_URL);
		}
		return PageName.ERROR_PAGE;

	}

}

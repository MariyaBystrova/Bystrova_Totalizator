package by.tr.totalizator.command.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;

public class LogoutCommand implements Command {

	private final static String URL_INDEX = "http://localhost:8080/Totalizator/";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false).getAttribute("local") != null) {
			response.addCookie(new Cookie("local", request.getSession(false).getAttribute("local").toString()));
		}
		request.getSession(false).invalidate();

		return URL_INDEX;
	}
}

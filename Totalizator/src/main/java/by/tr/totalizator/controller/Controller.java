package by.tr.totalizator.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.command.CommandProvider;

public final class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String COMMAND_NAME = "command";
	private final CommandProvider commandHelper = new CommandProvider();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String page = null;
		String commandName = null;
		Command command = null;

		commandName = request.getParameter(COMMAND_NAME);
		command = commandHelper.getCommand(commandName);

		page = command.execute(request, response);

		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String page = null;
		String commandName = null;
		Command command = null;

		commandName = request.getParameter(COMMAND_NAME);
		command = commandHelper.getCommand(commandName);

		page = command.execute(request, response);

		response.sendRedirect(page);

	}

}

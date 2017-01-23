package by.tr.totalizator.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.command.CommandProvider;

/**
 * This class represents a controller for web application. Overrides doGet and
 * doPost methods for HTTP request.
 * 
 * @author Mariya Bystrova
 *
 */
public final class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String COMMAND_NAME = "command";
	private final CommandProvider commandHelper = new CommandProvider();

	public Controller() {
		super();
	}

	/**
	 * Called by the server to allow a servlet(Controller) to handle a GET
	 * request.
	 * <p>
	 * GET request executes the specified command, after which it forwards to
	 * the page.
	 * </p>
	 * 
	 * @see {@link javax.servlet.http.HttpServlet}
	 */
	@Override
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

	/**
	 * Called by the server to allow a servlet(Controller) to handle a POST
	 * request.
	 * <p>
	 * POST request executes the specified command, after which it sends
	 * redirect to the client.
	 * </p>
	 * 
	 * @see {@link javax.servlet.http.HttpServlet}
	 */
	@Override
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

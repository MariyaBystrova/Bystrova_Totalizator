package by.tr.totalizator.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.CommandName;
import by.tr.totalizator.controller.PageName;

/**
 * Represents a filter to process the request before it is passed to the servlet
 * and to check matching the command and available handling method.
 * <p>
 * Blocks the further going of the request-response objects in case of
 * mismatching of comand's actual method and the method declared in the system.
 * </p>
 * <p>
 * In case of mismatching request forwards to the ERROR page.
 * </p>
 * 
 * @author Mariya Bystrova
 *
 */
public class CommandFilter implements Filter {
	private final static Logger logger = LogManager.getLogger(CommandFilter.class.getName());
	private final static String COMMAND = "command";

	public CommandFilter() {
	}

	public void destroy() {
	}

	/**
	 * Performs pre-processing of the request before it is passed to the
	 * servlet.
	 * <p>
	 * Blocks the further going of the request-response objects in case of
	 * mismatching of comand's actual method and the method declared in the
	 * system.
	 * </p>
	 * <p>
	 * In case of mismatching request forwards to the ERROR page.
	 * </p>
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String requestMethod = CommandName.getMethod(request.getParameter(COMMAND));

		if (requestMethod != null) {
			requestMethod = requestMethod.toUpperCase();
		}

		String realMethod = ((HttpServletRequest) request).getMethod();

		if (requestMethod != null && requestMethod.equals(realMethod)) {
			logger.info("CommandFilter checked the method.");
			chain.doFilter(request, response);
		} else {
			logger.info("Invalid command method.");
			RequestDispatcher rd = request.getRequestDispatcher(PageName.ERROR_PAGE);
			rd.forward(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}

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

public class CommandFilter implements Filter {
	private final static Logger logger = LogManager.getLogger(CommandFilter.class.getName());
	private final static String COMMAND = "command";
	
    public CommandFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		String requestMethod = CommandName.getMethod(request.getParameter(COMMAND));
		
		if(requestMethod != null){
			requestMethod = requestMethod.toUpperCase();
		}
		
		String realMethod = ((HttpServletRequest) request).getMethod();
		
		if(requestMethod!=null && requestMethod.equals(realMethod)){
			logger.info("CommandFilter checked the method.");
			try {
				chain.doFilter(request, response);
			} catch (IOException | ServletException e) {
				logger.error(e);
			}
			
		} else{
			logger.info("Invalid command method.");
			RequestDispatcher rd = request.getRequestDispatcher(PageName.ERROR_PAGE);
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e) {
				logger.error("Can't forward.", e);
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}

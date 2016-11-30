package by.tr.totalizator.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EncodingFilter implements Filter {
	private final static Logger logger = LogManager.getLogger(EncodingFilter.class.getName());
	private final static String CHARSET_ENCODING = "charsetEncoding";
	
	private String encoding;
	private ServletContext servletContext;

    public EncodingFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		((HttpServletRequest) request).setCharacterEncoding(encoding);
		((HttpServletRequest) request).setCharacterEncoding(encoding);
		servletContext.log("Charset was set.");
		logger.info("Filter was done: charset encoding was set.");
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter(CHARSET_ENCODING);
		servletContext = fConfig.getServletContext();
		logger.info("Filter was initialized.");
	}

}
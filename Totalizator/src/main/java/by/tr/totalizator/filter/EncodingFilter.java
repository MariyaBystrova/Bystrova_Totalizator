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
import javax.servlet.http.HttpServletResponse;

/**
 * Represents a filter to set encoding to the request and response before it is
 * passed to the servlet.
 * 
 * @author Mariya Bystrova
 *
 */
public class EncodingFilter implements Filter {
	private final static String CHARSET_ENCODING = "charsetEncoding";

	private String encoding;
	private ServletContext servletContext;

	public EncodingFilter() {
	}

	public void destroy() {
	}

	/**
	 * Sets the encoding to the request and response.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		((HttpServletRequest) request).setCharacterEncoding(encoding);
		((HttpServletResponse) response).setCharacterEncoding(encoding);
		servletContext.log("Charset was set.");
		chain.doFilter(request, response);
	}

	/**
	 * Initializes and configures the filter objects such as encoding and
	 * servlet context.
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter(CHARSET_ENCODING);
		servletContext = fConfig.getServletContext();
		servletContext.log("Filter was initialized.");
	}

}

package by.tr.totalizator.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import by.tr.totalizator.command.CommandName;

public class MessageListener implements ServletRequestListener {
	private final static String COMMAND = "command";
	private final static String METHOD = "GET";
	private final static String RESULT_ADD = "resultAdd";
	private final static String RESULT_EDIT = "resultEdit";
	private final static String RESULT_FINISHED_MATCHES = "resultFinishedMatches";
	private final static String RESULT_CLOSE_COUPON = "resultCloseCoupon";

	public MessageListener() {
	}

	public void requestDestroyed(ServletRequestEvent arg0) {
		HttpServletRequest request = (HttpServletRequest) arg0.getServletRequest();
		String requestMethod = CommandName.getMethod(request.getParameter(COMMAND));
		if (METHOD.equals(requestMethod)) {
			if (request.getSession(false) != null) {
				if (request.getSession(false).getAttribute(RESULT_EDIT) != null) {
					request.getSession(false).removeAttribute(RESULT_EDIT);
				}
				if (request.getSession(false).getAttribute(RESULT_ADD) != null) {
					request.getSession(false).removeAttribute(RESULT_ADD);
				}
				if (request.getSession(false).getAttribute(RESULT_FINISHED_MATCHES) != null) {
					request.getSession(false).removeAttribute(RESULT_FINISHED_MATCHES);
				}
				if (request.getSession(false).getAttribute(RESULT_CLOSE_COUPON) != null) {
					request.getSession(false).removeAttribute(RESULT_CLOSE_COUPON);
				}
			}
		}
	}

	public void requestInitialized(ServletRequestEvent arg0) {
	}

}

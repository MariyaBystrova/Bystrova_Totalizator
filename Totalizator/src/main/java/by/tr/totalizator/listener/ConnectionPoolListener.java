package by.tr.totalizator.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.tr.totalizator.command.SourceInitCommand;

/**
 * Receives notification events about ServletContext lifecycle changes.
 * 
 * @author Mariya Bystrova
 *
 */
public class ConnectionPoolListener implements ServletContextListener {

	public ConnectionPoolListener() {
	}

	/**
	 * Receives notification that the ServletContext is about to be shut down.
	 * <p>
	 * Destroys the connection to the data source.
	 * </p>
	 * <p>
	 * All servlets and filters will have been destroyed before the
	 * ServletContextListener is notified of context destruction.
	 * </p>
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		SourceInitCommand sourceInit = SourceInitCommand.getInstance();
		sourceInit.destroy();
	}

	/**
	 * Receives notification that the web application initialization process is
	 * starting.
	 * <p>
	 * Initializes the data source in the system.
	 * </p>
	 * <p>
	 * The ServletContextListener is notified of context initialization before
	 * any filters or servlets in the web application are initialized.
	 * </p>
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		SourceInitCommand sourceInit = SourceInitCommand.getInstance();
		sourceInit.init();
	}

}

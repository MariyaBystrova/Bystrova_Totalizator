package by.tr.totalizator.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.tr.totalizator.command.SourceInitCommand;

public class ConnectionPoolListener implements ServletContextListener {

	public ConnectionPoolListener() {
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		SourceInitCommand sourceInit = SourceInitCommand.getInstance();
		sourceInit.destroy();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		SourceInitCommand sourceInit = SourceInitCommand.getInstance();
		sourceInit.init();
	}

}

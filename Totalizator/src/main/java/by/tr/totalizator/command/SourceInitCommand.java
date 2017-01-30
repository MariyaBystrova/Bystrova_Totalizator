package by.tr.totalizator.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.service.SourceInitService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

/**
 * Represents the command which provides the source initialization, done by
 * singleton.
 * 
 * @author Mariya Bystrova
 *
 */
public class SourceInitCommand {
	private final static Logger logger = LogManager.getLogger(SourceInitCommand.class.getName());
	private static final SourceInitCommand instance = new SourceInitCommand();

	private SourceInitCommand() {
	}

	public static SourceInitCommand getInstance() {
		return instance;
	}

	/**
	 * Initializes the data source.
	 */
	public void init() {
		ServiceFactory factory = ServiceFactory.getInstance();
		SourceInitService sourceInit = factory.getSourceInitService();
		try {
			sourceInit.init();
			logger.info("Source was initialized.");
		} catch (ServiceException e) {
			logger.error(e);
			throw new RuntimeException("Init data source command failed.");
		}
	}

	/**
	 * Provides destruction of the connection to the data source.
	 */
	public void destroy() {
		ServiceFactory factory = ServiceFactory.getInstance();
		SourceInitService sourceInit = factory.getSourceInitService();
		sourceInit.destroy();
		logger.info("Source was disposed.");
	}

}

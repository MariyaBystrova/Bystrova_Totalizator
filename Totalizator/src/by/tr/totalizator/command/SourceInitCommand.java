package by.tr.totalizator.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.service.SourceInitService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

public class SourceInitCommand {
	private final static Logger logger = LogManager.getLogger(SourceInitCommand.class.getName());
	private static final SourceInitCommand instance = new SourceInitCommand();
	
	private SourceInitCommand(){
	}
	
	public static SourceInitCommand getInstance() {
		return instance;
	}
	
	public void init() {
		ServiceFactory factory = ServiceFactory.getInstance();
		SourceInitService sourceInit = factory.getSourceInitService();
		try {
			sourceInit.init();
			logger.info("Source was initialized.");
		} catch (ServiceException e) {
			logger.error(e);
		}
	}

	public void destroy() {
		ServiceFactory factory = ServiceFactory.getInstance();
		SourceInitService sourceInit = factory.getSourceInitService();
		sourceInit.destroy();
		logger.info("Source was disposed.");
	}

}

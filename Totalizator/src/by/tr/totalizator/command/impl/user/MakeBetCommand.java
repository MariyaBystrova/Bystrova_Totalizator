package by.tr.totalizator.command.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;
import by.tr.totalizator.entity.User;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

public class MakeBetCommand implements Command {
	private final static Logger logger = LogManager.getLogger(MakeBetCommand.class.getName());
	private final static String USER = "user";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response){
		String page="";
		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(USER)) {
			
		
		ServiceFactory sf = ServiceFactory.getInstance();
		TotalizatorService totoService = sf.getTotaliztorService();
		
		
		
		
		//////
		
		
		/////
		 try {
			totoService.registerBet(request.getParameterMap());
		} catch (ServiceException e) {
			logger.error(e);
		}

		 //page =
		} else {
			page = PageName.INDEX_PAGE;
		}
		return page;
	}
}

package by.tr.totalizator.dao.factory;

import by.tr.totalizator.dao.SourceInitDAO;
import by.tr.totalizator.dao.TotalizatorOperationDAO;
import by.tr.totalizator.dao.UserOperationDAO;
import by.tr.totalizator.dao.impl.SQLInit;
import by.tr.totalizator.dao.impl.SQLTotalizatorOperationDAO;
import by.tr.totalizator.dao.impl.SQLUserOperationDAO;

public class DAOFactory {
	private static final DAOFactory INSTANCE = new DAOFactory();

	private TotalizatorOperationDAO totalizatorOperationDAO = new SQLTotalizatorOperationDAO();
	private UserOperationDAO userOperationDAO = new SQLUserOperationDAO();
	////
	private SourceInitDAO sourceInitDAO = new SQLInit();

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return INSTANCE;
	}

	public TotalizatorOperationDAO getTotalizatorOperationDAO() {
		return totalizatorOperationDAO;
	}

	public UserOperationDAO getUserOperationDAO() {
		return userOperationDAO;
	}

	////
	public SourceInitDAO getSourceInitDAO(){
		return sourceInitDAO;
	}
	
}

package by.tr.totalizator.dao;

import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.entity.User;

public interface UserOperationDAO {
	boolean createUser(User user, String login, String password) throws DAOException;
	User authentication(String login, String password) throws DAOException;		
}

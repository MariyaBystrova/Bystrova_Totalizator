package by.tr.totalizator.service;

import by.tr.totalizator.bean.UserBean;
import by.tr.totalizator.entity.User;
import by.tr.totalizator.service.exception.ServiceException;

public interface UserService {

	boolean registerUser(UserBean userBean) throws ServiceException;
	User editUserPersonalInfo(UserBean user) throws ServiceException;
	boolean editUserAccountInfo(byte[] password, byte[] rpassword, int id) throws ServiceException;
	User authentication(String login, byte[] password) throws ServiceException;
}

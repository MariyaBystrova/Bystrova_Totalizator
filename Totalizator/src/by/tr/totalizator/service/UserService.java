package by.tr.totalizator.service;

import by.tr.totalizator.bean.UserBean;
import by.tr.totalizator.entity.User;
import by.tr.totalizator.service.exception.ServiceException;

public interface UserService {

	boolean registerUser(UserBean userBean) throws ServiceException;

	User authentication(String login, byte[] password) throws ServiceException;
}

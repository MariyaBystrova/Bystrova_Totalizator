package by.tr.totalizator.service;

import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.entity.dto.UserDTO;
import by.tr.totalizator.service.exception.ServiceException;

public interface UserService {

	boolean registerUser(UserDTO userBean) throws ServiceException;
	User editUserPersonalInfo(UserDTO user) throws ServiceException;
	boolean editUserAccountInfo(byte[] password, byte[] rpassword, int id) throws ServiceException;
	User authentication(String login, byte[] password) throws ServiceException;
}

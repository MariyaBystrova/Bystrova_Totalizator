package by.tr.totalizator.service;

import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.entity.dto.UserDTO;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;

/**
 * Represents an interface SourceInitService, implementation of which provides a
 * proper work with user's services.
 * 
 * @author Mariya Bystrova
 *
 */
public interface UserService {
	/**
	 * 
	 * @param userBean
	 *            a DTO value of {@link by.tr.totalizator.entity.dto.UserDTO}
	 *            object to be created.
	 * @return a boolean value <code>true</code>, if user is created and
	 *         <code>false</code>, if registration failed.
	 * @throws ServiceException if some problems with data has occur. 
	 * @throws ServiceDataException 
	 */
	boolean registerUser(UserDTO userBean) throws ServiceException, ServiceDataException;

	User editUserPersonalInfo(UserDTO user) throws ServiceException, ServiceDataException;

	boolean editUserAccountInfo(byte[] password, byte[] rpassword, int id) throws ServiceException, ServiceDataException;

	User authentication(String login, byte[] password) throws ServiceException, ServiceDataException;
}

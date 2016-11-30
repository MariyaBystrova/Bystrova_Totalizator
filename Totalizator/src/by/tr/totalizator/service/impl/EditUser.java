package by.tr.totalizator.service.impl;

import java.util.Arrays;

import by.tr.totalizator.bean.UserBean;
import by.tr.totalizator.dao.UserOperationDAO;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.factory.DAOFactory;
import by.tr.totalizator.entity.User;
import by.tr.totalizator.service.UserService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.impl.util.HashMd5Coder;
import by.tr.totalizator.service.impl.util.Validator;

public class EditUser implements UserService {

	@Override
	public boolean registerUser(UserBean userBean) throws ServiceException {

		if (!Validator.registrationValidator(userBean)) {
			throw new ServiceException("Invalid date.");
		}

		// city and address are unnecessary for fulfilling
		if (userBean.getCity().isEmpty()) {
			userBean.setCity(null);
		}
		if (userBean.getAddress().isEmpty()) {
			userBean.setAddress(null);
		}

		User user = new User(userBean.getFirstName(), userBean.getLastName(), userBean.getSex(), userBean.getEmail(),
				userBean.getCountry(), userBean.getCity(), userBean.getAddress(), userBean.getRole());

		String passwordHash = HashMd5Coder.hashMd5(userBean.getPassword());
		
		userBean.setPassword((byte) 0);
		userBean.setRepPassword((byte) 0);

		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userDAO = factory.getUserOperationDAO();
		try {
			userDAO.createUser(user, userBean.getLogin(), passwordHash);
		} catch (DAOException e) {
			throw new ServiceException("Create user failed.", e);
		}
		return true;
	}

	@Override
	public User authentication(String login, byte[] password) throws ServiceException {
		User user = null;
		if (!Validator.authenticationValidator(login, password)) {
			throw new ServiceException("Invalid login or password.");
		}
		String passwordHash = HashMd5Coder.hashMd5(password);
		Arrays.fill(password, (byte) 0);

		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userOpDao = factory.getUserOperationDAO();
		try {
			user = userOpDao.authentication(login, passwordHash);
		} catch (DAOException e) {
			throw new ServiceException("Autentication failed. ", e);
		}
		return user;
	}
}

package by.tr.totalizator.service.impl;

import java.util.Arrays;

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
	public boolean registerUser(String fName, String lName, String sex, String email, String country, String city,
			String address, String role, String login, byte[] password, byte[] passwordRep) throws ServiceException {

		if (!Validator.registrationValidator(fName, lName, sex, email, country, city, address, role, login, password,
				passwordRep)) {
			throw new ServiceException("Invalid date.");
		}

		city = city.isEmpty() ? null : city; // city and address are unnecessary for fulfilling
		address = address.isEmpty() ? null : address;
		User user = new User(fName, lName, sex, email, country, city, address, role);
		
		String passwordHash = HashMd5Coder.hashMd5(password);
		Arrays.fill(password, (byte)0);
		Arrays.fill(passwordRep, (byte)0);
		
		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userDAO = factory.getUserOperationDAO();
		try {
			userDAO.createUser(user, login, passwordHash);
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
		Arrays.fill(password, (byte)0);
		
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

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
			throw new ServiceException("Invalid data.");
		}

		// city and address are unnecessary for fulfilling
		if (userBean.getCity().isEmpty()) {
			userBean.setCity(null);
		}
		if (userBean.getAddress().isEmpty()) {
			userBean.setAddress(null);
		}

		User user = new User(userBean.getFirstName(), userBean.getLastName(), userBean.getSex(), userBean.getEmail(),
				userBean.getCountry(), userBean.getCity(), userBean.getAddress(), userBean.getLogin(), userBean.getRole());

		String passwordHash = HashMd5Coder.hashMd5(userBean.getPassword());
		userBean.setPassword((byte) 0);
		userBean.setRepPassword((byte) 0);

		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userDAO = factory.getUserOperationDAO();
		try {
			userDAO.createUser(user, passwordHash);
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

	@Override
	public User editUserPersonalInfo(UserBean userBean) throws ServiceException {
		if(!Validator.userPersonalInfoValidator(userBean)){
			throw new ServiceException("Invalid data.");
		}
		User user = null;

		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userOpDao = factory.getUserOperationDAO();
		try {
			user = new User(userBean.getFirstName(), userBean.getLastName(), userBean.getSex(), userBean.getEmail(),
					userBean.getCountry(), userBean.getCity(), userBean.getAddress());
			user.setId(Integer.parseInt(userBean.getId()));
			user = userOpDao.editUserPersonalInfo(user);
		} catch (DAOException e) {
			throw new ServiceException("Edit user personal information failed. ", e);
		}
		return user;
	}

	@Override
	public boolean editUserAccountInfo(byte[] password, byte[] rpassword, int id) throws ServiceException {
		if(!Validator.userAccountInfoValidator(password, rpassword, id)){
			throw new ServiceException("Invalid data.");
		}
		String passwordHash = HashMd5Coder.hashMd5(password);
		for(int i=0; i<password.length; i++){
			password[i]=0;
			rpassword[i]=0;
		}
		
		boolean result = false;
		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userOpDao = factory.getUserOperationDAO();
		
		try {
			result = userOpDao.editUserAccountInfo(id, passwordHash);
		} catch (DAOException e) {
			throw new ServiceException("Edit user account information failed. ", e);
		}
		
		return result;
	}
}

package by.tr.totalizator.dao.impl;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import by.tr.totalizator.command.SourceInitCommand;
import by.tr.totalizator.dao.UserOperationDAO;
import by.tr.totalizator.dao.connectionpool.ConnectionPool;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.factory.DAOFactory;
import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.service.impl.util.HashMd5Coder;

@Ignore
public class SQLUserOperationDAOTest {

	private static java.sql.Connection con = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SourceInitCommand sourceInit = SourceInitCommand.getInstance();
		sourceInit.init();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SourceInitCommand sourceInit = SourceInitCommand.getInstance();
		sourceInit.destroy();
	}

	@Before
	public void setUp() throws Exception {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		con = connectionPool.takeConnection();
	}

	@After
	public void tearDown() throws Exception {
		con.close();
	}

	@Test
	public void createUserTest() {

		int countBefore = getUserCount();

		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userDAO = factory.getUserOperationDAO();
		User user = new User("Kristine", "Brown", "F", "kristina_brown@gmail.com", "GB", "Liverpool", "Pools, 122",
				"kristina_brown", "user");
		String password = HashMd5Coder.hashMd5("9090");
		try {
			userDAO.createUser(user, password);
		} catch (DAOException e) {
			fail("DAOException has occure: " + e);
		}

		int countAfter = getUserCount();

		int id = -1;
		User userDB = null;
		String passwordDB = null;
		try (Statement st = con.createStatement()) {
			try (ResultSet rs = st.executeQuery(
					"SELECT `first_name`, `last_name`, `login`, `password`, `sex`, `e-mail`, `country`, `city`, `address`, `role`, `user_id` FROM `user` ORDER BY user_id DESC LIMIT 1;")) {
				rs.next();
				userDB = new User();
				userDB.setFirstName(rs.getString(1));
				userDB.setLastName(rs.getString(2));
				userDB.setLogin(rs.getString(3));
				passwordDB = rs.getString(4);
				userDB.setSex(rs.getString(5));
				userDB.setEmail(rs.getString(6));
				userDB.setCountry(rs.getString(7));
				userDB.setCity(rs.getString(8));
				userDB.setAddress(rs.getString(9));
				userDB.setRole(rs.getString(10));
				id = rs.getInt(11);
			} catch (SQLException e1) {
				fail("SQLException has occure: " + e1);
			}
		} catch (SQLException e) {
			fail("SQLException has occure: " + e);
		}

		deleteUserById(id);

		assertEquals("Not proper number of rows added.", countBefore + 1, countAfter);
		assertEquals("User is  not equals", user, userDB);
		assertEquals("Password is not equals", password, passwordDB);
	}

	@Test
	public void editUserPersonalInfoTest() {
		User userBefore = new User("Kristine", "Brown", "F", "kristina_brown@gmail.com", "GB", "Liverpool",
				"Pools, 122", "kristina_brown", "user");
		String password = HashMd5Coder.hashMd5("9090");
		insertUser(userBefore, password);

		int id = getLastUserId();

		User user = userBefore.clone();
		user.setFirstName("Kristine");
		user.setLastName("Brown");
		user.setCountry("GB");
		user.setCity("Liverpool");
		user.setAddress("Pools, 122");
		user.setSex("F");
		user.setEmail("kristina_brown@gmail.com");
		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userDAO = factory.getUserOperationDAO();
		try {
			userDAO.editUserPersonalInfo(user);
		} catch (DAOException e) {
			fail("SQLException has occure: " + e);
		}

		User userAfterDB = getUser(id);
		deleteUserById(id);

		assertEquals("User personal info must be the same.", user, userAfterDB);
	}

	@Test
	public void editUserAccountInfoTest() {
		User userBefore = new User("Kristine", "Brown", "F", "kristina_brown@gmail.com", "GB", "Liverpool",
				"Pools, 122", "kristina_brown", "user");
		String passwordBefore = HashMd5Coder.hashMd5("9090");
		insertUser(userBefore, passwordBefore);

		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userDAO = factory.getUserOperationDAO();
		int id = getLastUserId();
		String password = HashMd5Coder.hashMd5("asdfgh");
		try {
			userDAO.editUserAccountInfo(id, password);
		} catch (DAOException e2) {
			fail("DAOxception has occure: " + e2);
		}

		User userDB = null;
		String passwordDB = null;
		try (PreparedStatement st = con.prepareStatement(
				"SELECT `first_name`, `last_name`, `login`, `password`, `sex`, `e-mail`, `country`, `city`, `address`, `role` FROM `user` WHERE user_id = ?;")) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery()) {
				rs.next();
				userDB = new User();
				userDB.setFirstName(rs.getString(1));
				userDB.setLastName(rs.getString(2));
				userDB.setLogin(rs.getString(3));
				passwordDB = rs.getString(4);
				userDB.setSex(rs.getString(5));
				userDB.setEmail(rs.getString(6));
				userDB.setCountry(rs.getString(7));
				userDB.setCity(rs.getString(8));
				userDB.setAddress(rs.getString(9));
				userDB.setRole(rs.getString(10));
			} catch (SQLException e1) {
				fail("SQLException has occure: " + e1);
			}
		} catch (SQLException e) {
			fail("SQLException has occure: " + e);
		}

		deleteUserById(id);

		assertEquals("User personal info must be the same.", userBefore, userDB);
		assertEquals("User passwords must be the same.", password, passwordDB);
	}

	@Test
	public void authenticationTest() {
		User userBefore = new User("Kristine", "Brown", "F", "kristina_brown@gmail.com", "GB", "Liverpool",
				"Pools, 122", "kristina_brown", "user");
		String passwordBefore = HashMd5Coder.hashMd5("9090");
		insertUser(userBefore, passwordBefore);
		int id = getLastUserId();

		String login = "kristina_brown";
		String password = HashMd5Coder.hashMd5("9090");
		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userDAO = factory.getUserOperationDAO();
		User userDB = null;
		try {
			userDB = userDAO.authentication(login, password);
		} catch (DAOException e) {
			fail("DAOException has occure: " + e);
		}

		User user = null;
		try (PreparedStatement ps = con.prepareStatement(
				"SELECT `user_id`,`first_name`, `last_name`, `sex`, `e-mail`, `country`, `city`, `address`, `login`, `role` "
						+ "FROM `user`  WHERE `login`=? AND `password`=?;")) {
			ps.setString(1, login);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery()) {
				rs.next();
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
			} catch (SQLException e1) {
				fail("SQLException has occure: " + e1);
			}
		} catch (SQLException e) {
			fail("SQLException has occure: " + e);
		}

		deleteUserById(id);

		assertEquals("User info must be the same.", user, userDB);
	}

	private int getUserCount() {
		int count = -1;
		try (Statement st = con.createStatement()) {
			try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM user;")) {
				rs.next();
				count = rs.getInt(1);
				return count;
			} catch (SQLException e1) {
				fail("SQLException has occure: " + e1);
			}
		} catch (SQLException e) {
			fail("SQLException has occure: " + e);
		}
		return count;
	}

	private void deleteUserById(int id) {
		try (PreparedStatement ps = con.prepareStatement("DELETE FROM `user` WHERE user_id=?;")) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			fail("SQLException has occure: " + e);
		}
	}

	private int getLastUserId() {
		int id = -1;
		try (Statement st = con.createStatement()) {
			try (ResultSet rs = st.executeQuery("SELECT MAX(user_id) FROM user;")) {
				rs.next();
				id = rs.getInt(1);
			} catch (SQLException e1) {
				fail("SQLException has occure: " + e1);
			}
		} catch (SQLException e) {
			fail("SQLException has occure: " + e);
		}
		return id;
	}

	private User getUser(int id) {
		User user = null;
		try (PreparedStatement ps = con.prepareStatement(
				"SELECT `first_name`, `last_name`, `login`, `sex`, `e-mail`, `country`, `city`, `address`, `role` FROM `user` WHERE user_id=?")) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				rs.next();
				user = new User();
				user.setFirstName(rs.getString(1));
				user.setLastName(rs.getString(2));
				user.setLogin(rs.getString(3));
				user.setSex(rs.getString(4));
				user.setEmail(rs.getString(5));
				user.setCountry(rs.getString(6));
				user.setCity(rs.getString(7));
				user.setAddress(rs.getString(8));
				user.setRole(rs.getString(9));
			} catch (SQLException e1) {
				fail("SQLException has occure: " + e1);
			}
		} catch (SQLException e) {
			fail("SQLException has occure: " + e);
		}
		return user;
	}

	private void insertUser(User user, String password) {
		try (PreparedStatement ps = con.prepareStatement(
				"INSERT INTO `user` (`first_name`,`last_name`,`login`,`password`,`sex`,`e-mail`,`country`,`city`,`address`,`role`)VALUES (?,?,?,?,?,?,?,?,?,?);")) {
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getLogin());
			ps.setString(4, password);
			ps.setString(5, user.getSex());
			ps.setString(6, user.getEmail());
			ps.setString(7, user.getCountry());
			ps.setString(8, user.getCity());
			ps.setString(9, user.getAddress());
			ps.setString(10, user.getRole());
			ps.executeUpdate();
		} catch (SQLException e) {
			fail("SQLException has occure: " + e);
		}
	}
}

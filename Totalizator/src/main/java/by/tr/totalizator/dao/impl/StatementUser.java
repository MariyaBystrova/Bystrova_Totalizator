package by.tr.totalizator.dao.impl;

/**
 * Represents options of queries to the database working with user.
 * 
 * @author Mariya Bystrova
 *
 */
public final class StatementUser {

	private StatementUser() {
	}

	public final static String SELECT_USER_WHERE_LOGIN_PASSWORD = "SELECT `user_id`,`first_name`, `last_name`, `sex`, `e-mail`, `country`, `city`, `address`, `login`, `role` "
																+ "FROM `user`  WHERE `login`=? AND `password`=?;";

	public final static String INSERT_INTO_USER = "INSERT INTO `user` (`first_name`,`last_name`,`login`,`password`,`sex`,`e-mail`,`country`,`city`,`address`,`role`)VALUES (?,?,?,?,?,?,?,?,?,?);";

	public final static String UPDATE_USER_PERSONAL_DATA = "UPDATE `user` SET `first_name` = ?,`last_name` = ?,`sex` = ?,`e-mail` = ?,`country` = ?,`city` = ?,`address` = ? "
															+ "WHERE `user_id` = ?;";

	public final static String UPDATE_USER_ACCOUNT_DATA = "UPDATE `user` SET `password` = ? WHERE `user_id` = ?;";

}

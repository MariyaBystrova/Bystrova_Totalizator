package by.tr.totalizator.service.impl.util;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.tr.totalizator.entity.dto.CouponDTO;
import by.tr.totalizator.entity.dto.MatchDTO;
import by.tr.totalizator.entity.dto.UserDTO;

/**
 * Represents an utility class for data validation according to the rules
 * specified by web application.
 * 
 * @author Mariya Bystrova
 *
 */
public final class Validator {

	private final static String LOGIN_PATTERN = "\\w+";
	private final static String NAME_PATTERN = "[a-zA-Z]+";
	private final static String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z0-9-]+\\.[a-z]{2,}";
	private final static String DATE_PATTERN = "([1-9]\\d{3})-([1-9]|([0-1][0-2])|(0[1-9]))-([1-9]|([0-3]\\d)) (\\d{2}):(\\d{2}):(\\d{2})(.\\d)*";
	private final static String TEXT_PATTERN = "[\\w\\s-\\.:]+";
	private final static String GREATER_THEN_ZERO_PATTERN = "[1-9][0-9]*";
	private final static String ZERO_OR_GREATER_PATTERN = "[0-9]+";
	private final static String CREDIT_CARD_NUMBER_PATTERN = "[0-9]{16}";

	private Validator() {
	}

	/**
	 * Validates login and password.
	 * <p>
	 * <code>login</code> must be not empty and match the "\\w+" pattern.
	 * </p>
	 * <p>
	 * <code>password</code> as an array of byte must have length at least 1.
	 * </p>
	 * 
	 * @param login
	 *            a value of login to be validated.
	 * @param password
	 *            a value of password to be validated.
	 * @return <code>true</code>, if validation passes, and <code>false</code>
	 *         otherwise.
	 */
	public static boolean authenticationValidator(String login, byte[] password) {
		if (login.isEmpty() || !matcher(LOGIN_PATTERN, login)) {
			return false;
		}
		if (password.length < 1) {
			return false;
		}
		return true;
	}

	/**
	 * Validates user's DTO according to the following rules:
	 * <p>
	 * 1. Password must have at least one symbol and must be equals to the
	 * second time entered one.
	 * </p>
	 * <p>
	 * 2. Login must be not empty and match the <code>"\\w+"</code> pattern.
	 * </p>
	 * <p>
	 * 3. User's first name and last name must be not empty and match the
	 * <code>"[a-zA-Z]+"</code> pattern.
	 * </p>
	 * <p>
	 * 4. Sex must be not empty.
	 * </p>
	 * <p>
	 * 5. Email address must be not empty and match the
	 * <code>"[a-zA-Z0-9._-]+@[a-z0-9-]+\\.[a-z]{2,}"</code> pattern.
	 * </p>
	 * <p>
	 * 6. Country must be not empty and match the <code>"[a-zA-Z]+"</code>
	 * pattern.
	 * </p>
	 * <p>
	 * 7. The role must be set.
	 * </p>
	 * 
	 * @param user
	 *            a value of DTO user's object to be validated.
	 * @return <code>true</code>, if validation passes, and <code>false</code>
	 *         otherwise.
	 */
	public static boolean registrationValidator(UserDTO user) {

		if (!Arrays.equals(user.getPassword(), user.getRepPassword()) || (user.getPassword().length < 1)) {
			return false;
		}

		if (user.getLogin().isEmpty() || !matcher(LOGIN_PATTERN, user.getLogin())) {
			return false;
		}

		if (user.getFirstName().isEmpty() || !matcher(NAME_PATTERN, user.getFirstName())) {
			return false;
		}
		if (user.getLastName().isEmpty() || !matcher(NAME_PATTERN, user.getLastName())) {
			return false;
		}
		if (user.getSex().isEmpty()) {
			return false;
		}
		if (user.getEmail().isEmpty() || !matcher(EMAIL_PATTERN, user.getEmail())) {
			return false;
		}
		if (user.getCountry().isEmpty() || !matcher(NAME_PATTERN, user.getCountry())) {
			return false;
		}
		if (user.getRole().isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Validates user's DTO according to the following rules:
	 * <p>
	 * 1. User's first name and last name must be not empty and match the
	 * <code>"[a-zA-Z]+"</code> pattern.
	 * </p>
	 * <p>
	 * 2. Sex must be not empty.
	 * </p>
	 * <p>
	 * 3. Email address must be not empty and match the
	 * <code>"[a-zA-Z0-9._-]+@[a-z0-9-]+\\.[a-z]{2,}"</code> pattern.
	 * </p>
	 * <p>
	 * 4. Country must be not empty and match the <code>"[a-zA-Z]+"</code>
	 * pattern.
	 * </p>
	 * 
	 * @param user
	 *            a value of DTO user's object to be validated.
	 * @return <code>true</code>, if validation passes, and <code>false</code>
	 *         otherwise.
	 */
	public static boolean userPersonalInfoValidator(UserDTO user) {
		if (user.getFirstName().isEmpty() || !matcher(NAME_PATTERN, user.getFirstName())) {
			return false;
		}
		if (user.getLastName().isEmpty() || !matcher(NAME_PATTERN, user.getLastName())) {
			return false;
		}
		if (user.getSex().isEmpty()) {
			return false;
		}
		if (user.getEmail().isEmpty() || !matcher(EMAIL_PATTERN, user.getEmail())) {
			return false;
		}
		if (user.getCountry().isEmpty() || !matcher(NAME_PATTERN, user.getCountry())) {
			return false;
		}
		return true;
	}

	/**
	 * Validates passwords and id according to the following rules:
	 * <p>
	 * 1. Password must have at least one symbol and must be equals to the
	 * second time entered one.
	 * </p>
	 * <p>
	 * 2. id(unique identifier) must be greater then zero.
	 * </p>
	 * 
	 * @param password
	 *            a value of password an array of byte must have length at least
	 *            0.
	 * @param rpassword
	 *            a repeated value of password.
	 * @param id
	 *            a value of unique identifier.
	 * @return <code>true</code>, if validation passes, and <code>false</code>
	 *         otherwise.
	 */
	public static boolean userAccountInfoValidator(byte[] password, byte[] rpassword, int id) {
		if (!Arrays.equals(password, rpassword) || (password.length < 1)) {
			return false;
		}
		if (id <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * Validates coupon's start date, end date and minimal bet money amount
	 * according to the following rules:
	 * <p>
	 * 1. Start/end date and time must match the following pattern:
	 * <code>"yyyy-[m]m-[d]d hh:mm:ss[.f...]"</code>.
	 * </p>
	 * <p>
	 * 2. The minimal bet money amount must be greater then 0;
	 * </p>
	 * 
	 * @param startDate
	 *            a String value of start date and time.
	 * @param endDate
	 *            a String value of end date and time.
	 * @param minBetAmount
	 *            a value of minimal bet money amount.
	 * @return <code>true</code>, if validation passes, and <code>false</code>
	 *         otherwise.
	 */
	public static boolean validateCoupon(String startDate, String endDate, int minBetAmount) {
		if (!validateDate(startDate)) {
			return false;
		}
		if (!validateDate(endDate)) {
			return false;
		}
		if (minBetAmount <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * Validates coupon's DTO according to the following rules:
	 * <p>
	 * 1. Start/end date and time must match the following pattern:
	 * <code>"yyyy-[m]m-[d]d hh:mm:ss[.f...]"</code>.
	 * </p>
	 * <p>
	 * 2. The String value of minimal bet money amount must be numeric and
	 * greater then 0.
	 * </p>
	 * <p>
	 * 3. The String value of coupon's pool and jackPot must be numeric.
	 * </p>
	 * <p>
	 * 4. The String value of coupon's status must be "1", "3", "4" or "6".
	 * </p>
	 * <p>
	 * 5. The String value of coupon's id must be numeric and greater then 0.
	 * </p>
	 * 
	 * @param coupon
	 *            a value of coupon's DTO to be validated.
	 * @return <code>true</code>, if validation passes, and <code>false</code>
	 *         otherwise.
	 */
	public static boolean validateCoupon(CouponDTO coupon) {
		if (!validateDate(coupon.getStartDate())) {
			return false;
		}
		if (!validateDate(coupon.getEndDate())) {
			return false;
		}
		if (!matcher(GREATER_THEN_ZERO_PATTERN, coupon.getMinBetAmount())) {
			return false;
		}
		if (!matcher(ZERO_OR_GREATER_PATTERN, coupon.getJackpot())) {
			return false;
		}
		if (!matcher(ZERO_OR_GREATER_PATTERN, coupon.getPull())) {
			return false;
		}
		if (!matcher("[1346]{1}", coupon.getStatus())) {
			return false;
		}
		if (!validateCouponId(coupon.getId())) {
			return false;
		}
		return true;
	}

	/**
	 * Validates coupon's id to numeric and greater then 0.
	 * 
	 * @param couponId
	 *            a String value of unique identifier to be validated.
	 * @return <code>true</code>, if validation passes, and <code>false</code>
	 *         otherwise.
	 */
	public static boolean validateCouponId(String couponId) {
		if (!matcher(GREATER_THEN_ZERO_PATTERN, couponId)) {
			return false;
		}
		return true;
	}

	/**
	 * Validates match's DTO according to the following rules:
	 * <p>
	 * 1. Match's name, first and second team's names must be not empty and
	 * match the following pattern: <code>"[\\w\\s-\\.:]+"</code>.
	 * </p>
	 * <p>
	 * 2. First team's name must be not equals to the second team's name.
	 * </p>
	 * <p>
	 * 3. Start/end date and time must match the following pattern:
	 * <code>"yyyy-[m]m-[d]d hh:mm:ss[.f...]"</code>.
	 * </p>
	 * 
	 * @param match
	 *            a value of match's DTO to be validated.
	 * @return <code>true</code>, if validation passes, and <code>false</code>
	 *         otherwise.
	 */
	public static boolean validateMatch(MatchDTO match) {
		if (match.getCouponId() != null) {
			if (!validateCouponId(match.getCouponId())) {
				return false;
			}
		}
		if (match.getName().isEmpty() || !matcher(TEXT_PATTERN, match.getName())) {
			return false;
		}
		if (match.getTeamOne().isEmpty() || !matcher(TEXT_PATTERN, match.getTeamOne())) {
			return false;
		}
		if (match.getTeamTwo().isEmpty() || !matcher(TEXT_PATTERN, match.getTeamTwo())) {
			return false;
		}
		if (match.getTeamOne().equals(match.getTeamTwo())) {
			return false;
		}
		if (!validateDate(match.getStartDate())) {
			return false;
		}
		if (!validateDate(match.getEndDate())) {
			return false;
		}
		return true;
	}

	/**
	 * Validates match's DTO object according the following rules:
	 * <p>
	 * 1. Match's unique identifier must be greater then 0.
	 * </p>
	 * <p>
	 * 2. Start/end date and time must match the following pattern:
	 * <code>"yyyy-[m]m-[d]d hh:mm:ss[.f...]"</code>.
	 * </p>
	 * <p>
	 * 3. The String value of match's status must be "2", "4" or "5".
	 * </p>
	 * <p>
	 * 4. The String value of match's result must be "1", "2", "x" or "NULL".
	 * </p>
	 * 
	 * @param match
	 *            a value of match's DTO to be validated.
	 * @return <code>true</code>, if validation passes, and <code>false</code>
	 *         otherwise.
	 */
	public static boolean validateMatchDatesResultStatus(MatchDTO match) {
		if (match.getId() != null) {
			if (!matcher(GREATER_THEN_ZERO_PATTERN, match.getId())) {
				return false;
			}
		}
		if (!validateDate(match.getStartDate())) {
			return false;
		}
		if (!validateDate(match.getEndDate())) {
			return false;
		}
		if (match.getStatus() != null) {
			if (!matcher("[245]{1}", match.getStatus())) {
				return false;
			}
		}
		if (!matcher("[12x]|(NULL)", match.getResult())) {
			return false;
		}
		return true;
	}

	/**
	 * Validates input parameters according to the following rules:
	 * <p>
	 * 1. A number of elements in the <code>params</code> must be equals or
	 * greater then 15.
	 * </p>
	 * <p>
	 * 2. A key values in the Map <code>params</code> must have values "1", "2"
	 * or "x".
	 * </p>
	 * <p>
	 * 3. Amount, userId must be greater the 0.
	 * </p>
	 * <p>
	 * 4. Credit card number must be numeric and contain 16 numbers.
	 * </p>
	 * <p>
	 * 5. A string value of coupon's id must have an integer value greater then
	 * 0.
	 * </p>
	 * 
	 * @param params
	 *            a Map representing bet's results to be validated.
	 * @param amount
	 *            a money amount to be validated.
	 * @param creditCardNumber
	 *            a credit card number to be validated.
	 * @param userId
	 *            a value of user's unique identifier to be validated.
	 * @param couponId
	 *            a value of coupon's unique identifier to be validated.
	 * @return <code>true</code>, if validation passes, and <code>false</code>
	 *         otherwise.
	 */
	public static boolean validateBet(Map<String, String> params, int amount, String creditCardNumber, int userId,
			String couponId) {
		if (params.size() < 15) {
			return false;
		}
		for (int i = 0; i < params.size(); i++) {
			String param = params.get("result" + new Integer(i + 1).toString());

			if (!matcher("[12X]{1}", param)) {
				return false;
			}
		}
		if (amount <= 0) {
			return false;
		}
		if (!matcher(CREDIT_CARD_NUMBER_PATTERN, creditCardNumber)) {
			return false;
		}
		if (userId <= 0) {
			return false;
		}
		if (!matcher(GREATER_THEN_ZERO_PATTERN, couponId)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the result of matching an input String and the pattern set by
	 * regex.
	 * 
	 * @param regex
	 *            the expression of pattern to be matched.
	 * @param input
	 *            a value to be checked.
	 * @return the result of matching an input String and the pattern set by
	 *         regex.
	 */
	private static boolean matcher(String regex, String input) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the result of matching the String start date and time value to
	 * the <code>"yyyy-[m]m-[d]d hh:mm:ss[.f...]"</code> format.
	 * 
	 * @param startDate
	 *            a value of date and time to check.
	 * @return the result of matching the String start date and time value to
	 *         the <code>"yyyy-[m]m-[d]d hh:mm:ss[.f...]"</code> format.
	 */
	private static boolean validateDate(String startDate) {
		Pattern p = Pattern.compile(DATE_PATTERN);
		Matcher m = p.matcher(startDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
}

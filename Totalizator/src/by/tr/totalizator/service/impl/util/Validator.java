package by.tr.totalizator.service.impl.util;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.tr.totalizator.bean.MatchBean;

public final class Validator {
	private final static String LOGIN_PATTERN = "\\w+";
	private final static String NAME_PATTERN = "[a-zA-Z]+";
	private final static String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z0-9-]+\\.[a-z]{2,}";
	private final static String DATE_PATTERN = "([1-9]\\d{3})-([1-9]|([0-1][0-2])|(0[1-9]))-([1-9]|([0-3]\\d)) (\\d{2}):(\\d{2}):(\\d{2})(.\\d)*";

	private Validator() {

	}

	public static boolean authenticationValidator(String login, byte[] password) {
		if (login.isEmpty() || !matcher(LOGIN_PATTERN, login)) {
			return false;
		}
		if (password.length < 1) {
			return false;
		}
		return true;
	}

	/*public static boolean authenticationValidator(String login, String password) {
		if (login.isEmpty() || !matcher("\\w+", login)) {
			return false;
		}
		if (password.isEmpty() || !matcher("\\w+", password)) {
			return false;
		}
		return true;
	}*/

	public static boolean registrationValidator(String fName, String lName, String sex, String email, String country,
			String city, String address, String role, String login, byte[] password, byte[] passwordRep) {
		
		if (!Arrays.equals(password, passwordRep) || (password.length < 1)) {
			return false;
		}

		if (login.isEmpty() || !matcher(LOGIN_PATTERN, login)) {
			return false;
		}

		if (fName.isEmpty() || !matcher(NAME_PATTERN, fName)) {
			return false;
		}
		if (lName.isEmpty() || !matcher(NAME_PATTERN, lName)) {
			return false;
		}
		if (sex.isEmpty()) {
			return false;
		}
		if (email.isEmpty() || !matcher(EMAIL_PATTERN, email)) {
			return false;
		}
		if (country.isEmpty() || !matcher(NAME_PATTERN, country)) {
			return false;
		}
		if (role.isEmpty()) {
			return false;
		}

		return true;
	}

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

	//// поля на pattern

	public static boolean validateMatch(MatchBean match) {
		if (match.getCouponId() != null) {
			if (Integer.parseInt(match.getCouponId()) <= 0) {
				return false;
			}
		}
		if (match.getName().isEmpty()) { // как еще?
			return false;
		}
		if (match.getTeamOne().isEmpty()) {
			return false;
		}
		if (match.getTeamTwo().isEmpty()) {
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

	///////////////////
	public static boolean validateParams(Map<String, String[]> params) {
		if (params.size() <= 15) {
			return false;
		}
		for (int i = 0; i < params.size(); i++) {
			String[] lineParams = params.get("result" + new Integer(i + 1).toString());
			for (int j = 0; j < lineParams.length; j++) {
				if (!matcher("[12X]{1}", lineParams[j])) {
					return false;
				}
			}
		}
		return true;
	}
	///////////

	private static boolean matcher(String regex, String input) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	private static boolean validateDate(String startDate) { // yyyy-[m]m-[d]d
															// hh:mm:ss[.f...]
		Pattern p = Pattern.compile(DATE_PATTERN);
		Matcher m = p.matcher(startDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
}

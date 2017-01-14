package by.tr.totalizator.service.impl.util;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.tr.totalizator.bean.CouponBean;
import by.tr.totalizator.bean.MatchBean;
import by.tr.totalizator.bean.UserBean;

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

	public static boolean registrationValidator(UserBean user) {

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

	public static boolean userPersonalInfoValidator(UserBean user) {
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

	public static boolean userAccountInfoValidator(byte[] password, byte[] rpassword, int id) {
		if (!Arrays.equals(password, rpassword) || (password.length < 1)) {
			return false;
		}

		if (id <= 0) {
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
	public static boolean validateCoupon(CouponBean coupon) {
		if (!validateDate(coupon.getStartDate())) {
			System.out.println(coupon.getStartDate());
			return false;
		}
		if (!validateDate(coupon.getEndDate())) {
			return false;
		}
		if (!matcher("[1-9][0-9]*", coupon.getMinBetAmount())) {
			return false;
		}
		if (!matcher("[0-9]+", coupon.getJackpot())) {
			return false;
		}
		if (!matcher("[0-9]+", coupon.getPull())) {
			return false;
		}
		if (!matcher("[1346]{1}", coupon.getStatus())) {
			return false;
		}
		if(!validateCouponId(coupon.getId())){
			return false;
		}
		return true;
	}
	public static boolean validateCouponId(String couponId) {
		if (!matcher("[1-9][0-9]*", couponId)) {
			return false;
		}
		return true;
	}

	//// поля на pattern

	public static boolean validateMatch(MatchBean match) {
		if (match.getCouponId() != null) { 								 //??????
			if (Integer.parseInt(match.getCouponId()) <= 0) {
				return false;
			}
		}
		if (match.getName().isEmpty()) {
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

	public static boolean validateMatchDatesResultStatus(MatchBean match){
		if (match.getId() != null) {
			if (Integer.parseInt(match.getId()) <= 0) {
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
			if (Integer.parseInt(match.getStatus()) <= 0) {
				return false;
			}
		}
		if (!matcher("[12x]|(NULL)", match.getResult())){
			return false;
		}
		return true;
	}
	///////////////////

	// не дописано
	public static boolean validateBet(Map<String, String> params, int amount, String creditCardNumber, int userId, String couponId) {
		if (params.size() < 15) {
			return false;
		}
		for (int i = 0; i < params.size(); i++) {
			String param = params.get("result" + new Integer(i + 1).toString());

			if (!matcher("[12X]{1}", param)) {
				return false;
			}
		}
		if(amount<=0){
			return false;
		}
		if(!matcher("[0-9]{16}", creditCardNumber)){
			return false;
		}
		if(userId<=0){
			return false;
		}
		if(!matcher("[1-9][0-9]*",couponId)){
			return false;
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

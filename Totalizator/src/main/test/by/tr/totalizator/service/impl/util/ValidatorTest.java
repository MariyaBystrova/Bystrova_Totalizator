package by.tr.totalizator.service.impl.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import by.tr.totalizator.entity.dto.CouponDTO;
import by.tr.totalizator.entity.dto.MatchDTO;
import by.tr.totalizator.entity.dto.UserDTO;

public class ValidatorTest {

	@Test
	public void authenticationValidatorTest() {
		assertTrue(Validator.authenticationValidator("ddosoosoaoaooasooa", "scsdcsd".getBytes()));
		assertFalse(Validator.authenticationValidator("ddosoosoa oaooasooa", "scsdcsd".getBytes()));
		assertFalse(Validator.authenticationValidator("ddosoosoaoa ooasooa", "".getBytes()));
		assertFalse(Validator.authenticationValidator("", "scsdcsd".getBytes()));
		assertFalse(Validator.authenticationValidator("лтоыслтвлст", "scsdcsd".getBytes()));
	}

	@Test
	public void registrationValidatorTest() {
		UserDTO user = new UserDTO("nskjncksndkjnds", "kjnsakjnckjs", "F", "nsbcb@gmail.com", "kwndsocjos", "jdkskcj",
				"knsckni, jaskj 12", "user", "jdckjbds", "dsdsvisndkjv".getBytes(), "dsdsvisndkjv".getBytes());
		assertTrue(Validator.registrationValidator(user));

		user.setFirstName("sndcjkn sdk-bk");
		user.setLastName("sndcjkn sdk-bk");
		assertTrue(Validator.registrationValidator(user));

		user.setFirstName("ывоис лфвы-лытс");
		user.setLastName("лотыс-ысл ыф");
		assertTrue(Validator.registrationValidator(user));

		user.setRepPassword("ds".getBytes());
		assertFalse(Validator.registrationValidator(user));

		user.setPassword("".getBytes());
		user.setRepPassword("".getBytes());
		assertFalse(Validator.registrationValidator(user));

		user.setPassword("sdhbcjs".getBytes());
		user.setRepPassword("sdhbcjs".getBytes());
		user.setSex("");
		assertFalse(Validator.registrationValidator(user));

		user.setSex("M");
		user.setCountry("skjc sdbsds");
		assertTrue(Validator.registrationValidator(user));

		user.setCountry("влоыис-сывсг форыи");
		assertTrue(Validator.registrationValidator(user));

		user.setLogin("вылотслы");
		assertFalse(Validator.registrationValidator(user));

		user.setLogin("sdjkckj kdsjn");
		assertFalse(Validator.registrationValidator(user));

		user.setLogin("");
		assertFalse(Validator.registrationValidator(user));

		user.setLogin("sjkkjdc");
		user.setRole("");
		assertFalse(Validator.registrationValidator(user));

		user.setRole("admin");
		user.setEmail("bdcjshbd@mail.ru");
		assertTrue(Validator.registrationValidator(user));

		user.setEmail("офиыфыоисрф@mail.ru");
		assertFalse(Validator.registrationValidator(user));

		user.setEmail("bdcjshbd@mail");
		assertFalse(Validator.registrationValidator(user));

	}

	@Test
	public void userPersonalInfoValidatorTest() {
		UserDTO user = new UserDTO("nskjncksndkjnds", "kjnsakjnckjs", "F", "nsbcb@gmail.com", "kwndsocjos", "jdkskcj",
				"knsckni, jaskj 12", "user", "jdckjbds", "dsdsvisndkjv".getBytes(), "dsdsvisndkjv".getBytes());
		assertTrue(Validator.userPersonalInfoValidator(user));

		user.setFirstName("sndcjkn sdk-bk");
		user.setLastName("sndcjkn sdk-bk");
		assertTrue(Validator.userPersonalInfoValidator(user));

		user.setFirstName("ывоис лфвы-лытс");
		user.setLastName("лотыс-ысл ыф");
		assertTrue(Validator.userPersonalInfoValidator(user));

		user.setSex("");
		assertFalse(Validator.userPersonalInfoValidator(user));

		user.setSex("M");
		user.setEmail("bdcjshbd@mail.ru");
		assertTrue(Validator.registrationValidator(user));

		user.setEmail("офиыфыоисрф@mail.ru");
		assertFalse(Validator.registrationValidator(user));

		user.setEmail("bdcjshbd@mail");
		assertFalse(Validator.registrationValidator(user));

		user.setEmail("bdcjshbd@mail.ru");
		user.setCountry("skjc sdbsds");
		assertTrue(Validator.registrationValidator(user));

		user.setCountry("влоыис-сывсг форыи");
		assertTrue(Validator.registrationValidator(user));
	}

	@Test
	public void userAccountInfoValidatorTest() {
		assertTrue(Validator.userAccountInfoValidator("dsdsvisndkjv".getBytes(), "dsdsvisndkjv".getBytes(), 1));

		assertFalse(Validator.userAccountInfoValidator("dsdsvisndkjv".getBytes(), "ds".getBytes(), 1));

		assertFalse(Validator.userAccountInfoValidator("".getBytes(), "".getBytes(), 1));

		assertFalse(Validator.userAccountInfoValidator("dsdsvisndkjv".getBytes(), "dsdsvisndkjv".getBytes(), -21));

		assertFalse(Validator.userAccountInfoValidator("dsdsvisndkjv".getBytes(), "dsdsvisndkjv".getBytes(), 0));
	}

	@Test
	public void validateCouponTest() {
		assertTrue(Validator.validateCoupon("2017-12-12 12:00:00", "2017-12-22 01:00:00", 10));
		assertTrue(Validator.validateCoupon("2017-1-12 12:00:00", "2017-12-22 01:00:00", 10));
		assertFalse(Validator.validateCoupon("2017-12-12 12:00:00", "2017-12-22 1:00:00", 10));
		assertFalse(Validator.validateCoupon("2017-1-12 12:00:00", "2017-12-22", 10));
		assertFalse(Validator.validateCoupon("2017-12-12 12:00:00", "2017-12-22 01:00:00", 0));
		assertFalse(Validator.validateCoupon("2017-12-12 12:00:00", "2017-12-22 01:00:00", -20));
	}

	@Test
	public void validateCouponDTOTest() {
		CouponDTO coupon = new CouponDTO("11", "2017-12-12 12:00:00", "2017-12-22 01:00:00", "323", "345", "342", "1");
		assertTrue(Validator.validateCoupon(coupon));
		coupon.setEndDate("2017-12-22 1:00:00");
		assertFalse(Validator.validateCoupon(coupon));
		coupon.setEndDate("2017-1-22 12:00:00");
		assertTrue(Validator.validateCoupon(coupon));
		coupon.setEndDate("2017-1-22");
		assertFalse(Validator.validateCoupon(coupon));
		coupon.setEndDate("2017-1-22 12:00:00");
		coupon.setMinBetAmount("0");
		assertFalse(Validator.validateCoupon(coupon));
		coupon.setMinBetAmount("-2");
		assertFalse(Validator.validateCoupon(coupon));
		coupon.setMinBetAmount("skdhufks");
		assertFalse(Validator.validateCoupon(coupon));
		coupon.setMinBetAmount("12");
		coupon.setJackpot("0");
		coupon.setPull("0");
		assertTrue(Validator.validateCoupon(coupon));
		coupon.setJackpot("-1");
		coupon.setPull("1");
		assertFalse(Validator.validateCoupon(coupon));
		coupon.setJackpot("sdkn");
		assertFalse(Validator.validateCoupon(coupon));
		coupon.setJackpot("123");
		coupon.setPull("-12");
		assertFalse(Validator.validateCoupon(coupon));
		coupon.setPull("oiwjeofije");
		assertFalse(Validator.validateCoupon(coupon));
		coupon.setPull("1346");
		coupon.setStatus("223");
		assertFalse(Validator.validateCoupon(coupon));
		coupon.setStatus("3");
		coupon.setId("aksndhkl");
		assertFalse(Validator.validateCoupon(coupon));
		coupon.setId("-1");
		assertFalse(Validator.validateCoupon(coupon));
		coupon.setId("0");
		assertFalse(Validator.validateCoupon(coupon));
	}

	@Test
	public void validateCouponIdTest() {
		assertTrue(Validator.validateCouponId("1223"));
		assertFalse(Validator.validateCouponId("-32"));
		assertFalse(Validator.validateCouponId("0"));
		assertFalse(Validator.validateCouponId("wuhsdfiuh"));
	}

	@Test
	public void validateMatchTest() {
		MatchDTO m = new MatchDTO("skdkhsn", "123", "khskd|ashd(sad)", "kuhsdiauhs. HGdskcn", "2017-12-12 12:00:00",
				"2017-12-22 01:00:00");
		assertTrue(Validator.validateMatch(m));
		m.setCouponId("0");
		assertFalse(Validator.validateMatch(m));
		m.setCouponId("-1");
		assertFalse(Validator.validateMatch(m));
		m.setCouponId("bcaisbnc");
		assertFalse(Validator.validateMatch(m));
		m.setCouponId("12271");
		m.setTeamOne(m.getTeamTwo());
		assertFalse(Validator.validateMatch(m));
		m.setTeamOne("akskajc b");
		m.setEndDate("2017-12-22 1:00:00");
		assertFalse(Validator.validateMatch(m));
		m.setEndDate("2017-12-22");
		assertFalse(Validator.validateMatch(m));
		m.setEndDate("2017-1-12 12:00:00");
		assertTrue(Validator.validateMatch(m));
		m.setName("");
		assertFalse(Validator.validateMatch(m));
		m.setName("ueadhbsin");
		m.setTeamOne("");
		assertFalse(Validator.validateMatch(m));
		m.setName("ueadhbsin");
		m.setTeamOne("kabsch sjhdga");
		m.setTeamTwo("");
		assertFalse(Validator.validateMatch(m));

	}

	@Test
	public void validateMatchDatesResultStatusTest() {
		MatchDTO m = new MatchDTO("skdkhsn", "123", "khskd|ashd(sad)", "kuhsdiauhs. HGdskcn", "2017-12-12 12:00:00",
				"2017-12-22 01:00:00");
		m.setResult("1");
		m.setStatus("2");
		assertTrue(Validator.validateMatchDatesResultStatus(m));
		m.setEndDate("2017-12-22 1:00:00");
		assertFalse(Validator.validateMatchDatesResultStatus(m));
		m.setEndDate("2017-12-22");
		assertFalse(Validator.validateMatchDatesResultStatus(m));
		m.setResult("x");
		m.setEndDate("2017-1-12 12:00:00");
		assertTrue(Validator.validateMatchDatesResultStatus(m));
		m.setStatus("123");
		assertFalse(Validator.validateMatchDatesResultStatus(m));
		m.setStatus("aksjcka");
		assertFalse(Validator.validateMatchDatesResultStatus(m));
		m.setStatus("4");
		m.setResult("dscnkjnc");
		assertFalse(Validator.validateMatchDatesResultStatus(m));
		m.setResult("2");
		m.setId("12");
		assertTrue(Validator.validateMatchDatesResultStatus(m));
		m.setId("-32");
		assertFalse(Validator.validateMatchDatesResultStatus(m));
		m.setId("0");
		assertFalse(Validator.validateMatchDatesResultStatus(m));
		m.setId("jhsb");
		assertFalse(Validator.validateMatchDatesResultStatus(m));
	}
	
	@Test
	public void validateBetTest(){
		Map<String, String> params = new HashMap<>();
		for(int i=0; i<12; i++){
			params.put("result"+ new Integer(i+1).toString(), "2");
		}
		for(int i=12; i<14; i++){
			params.put("result"+ new Integer(i+1).toString(), "1");
		}
		params.put("result"+ new Integer(15).toString(), "X");
		
		assertTrue(Validator.validateBet(params, 134, "1111222233334444", 12, "12345"));
		assertFalse(Validator.validateBet(params, -134, "1111222233334444", 12, "12345"));
		assertFalse(Validator.validateBet(params, 0, "1111222233334444", 12, "12345"));
		assertFalse(Validator.validateBet(params, 134, "1111222233334444", 0, "12345"));
		assertFalse(Validator.validateBet(params, 134, "111122233334444", 12, "12345"));
		assertFalse(Validator.validateBet(params, 134, "11112222333334444", 12, "12345"));
		assertFalse(Validator.validateBet(params, 134, "1111222233334444", 12, "123d45"));
		assertFalse(Validator.validateBet(params, 134, "1111222233334444", 12, "-12345"));
		assertFalse(Validator.validateBet(params, 134, "1111222233334444", 12, "0"));
		assertFalse(Validator.validateBet(params, 134, "khusdk", 12, "12345"));
		
		params.put("result"+ new Integer(16).toString(), "x");
		assertFalse(Validator.validateBet(params, 134, "1111222233334444", 12, "12345"));
		
		params.remove("result"+ new Integer(15).toString());
		params.remove("result"+ new Integer(16).toString());
		assertFalse(Validator.validateBet(params, 134, "1111222233334444", 12, "12345"));
	}

}

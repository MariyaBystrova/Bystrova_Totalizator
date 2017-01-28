//Глобальные переменные
var FILL_FIEAD = "*Обязательно для заполнения", LATIN_KIRIL_SPACE_DASH = "*Можно использовать латиницу/кириллицу, пробел и -", AGE_VALIDATION = "Возраст должен быть в пределах 7-120 лет", NOT_LESS_FIVE_SYMBOLS = "*Должен содержать не менее 5 символов", NOT_BEGIN_WITH_DIGIT = "*Не должен начинаться с цифры", LATIN_DIGITS_UNDERLINE = "*Можно использовать латиницу, цифры, _", WEAK_PASSWORD = "*Слишком слабый пароль: введите не менее 6 символов", UPPER_LOWER_CASE_DIGITS = "*Нужно использовать верхний и нижний регистр, цифры", DIFFERENT_PASSWORDS = "*Разные пароли!", EMAIL_FORMAT = "Не верный формат: xxxxx@xxxx.xx";

function validate() {
	var result = true, res1 = true, res2 = true, res3 = true, res4 = true, res5 = true, res6 = true, res7 = true, res8 = true;

	res1 = validateFirstName();
	if (!res1) {
		result = res1;
	}
	res2 = validateLastName();
	if (!res2) {
		result = res2;
	}
	res4 = validateLogin();
	if (!res4) {
		result = res4;
	}
	res5 = validatePassword();
	if (!res5) {
		result = res5;
	}
	res6 = validateRPassword();
	if (!res6) {
		result = res6;
	}
	res7 = validateEmail();
	if (!res7) {
		result = res7;
	}
	res8 = validateCountry();
	if (!res8) {
		result = res8;
	}
	console.log("js");
	return result;
}

function validateFirstName() {
	var result = true, errFirstName = document.getElementById("err-first-name");

	errFirstName.innerHTML = "";
	document.getElementById("first-name").style.cssText = "border: 1px solid #ccc;";

	var regExpNameRuEn = new RegExp(/^(([а-яё \-]+)|([a-z \-]+))$/i), firstName = document
			.getElementById("first-name").value;

	if (!firstName) {
		errFirstName.innerHTML = FILL_FIEAD;
		result = false;
	} else {
		if (!regExpNameRuEn.test(firstName)) {
			errFirstName.innerHTML = LATIN_KIRIL_SPACE_DASH;
			result = false;
		}
	}
	if (!result) {
		document.getElementById("first-name").style.cssText = "border: 1px solid red;";
	}
	return result;
}

function validateLastName() {
	var result = true, errLastName = document.getElementById("err-last-name");

	errLastName.innerHTML = "";
	document.getElementById("last-name").style.cssText = "border: 1px solid #ccc;";

	var regExpNameRuEn = new RegExp(/^(([а-яё \-]+)|([a-z \-]+))$/i), lastName = document
			.getElementById("last-name").value;

	if (!lastName) {
		errLastName.innerHTML = FILL_FIEAD;
		result = false;
	} else {
		if (!regExpNameRuEn.test(lastName)) {
			errLastName.innerHTML = LATIN_KIRIL_SPACE_DASH;
			result = false;
		}
	}
	if (!result) {
		document.getElementById("last-name").style.cssText = "border: 1px solid red;";
	}
	return result;
}
//
// function validateAge() {
// var result = true,
// errAge = document.getElementById("err-age");
//    
// errAge.innerHTML = "";
//   
// var age = document.getElementById("age").value;
// document.getElementById("age").style.cssText = "border: 1px solid #ccc;";
//
// if (!age) {
// errAge.innerHTML = FILL_FIEAD;
// result = false;
// } else {
// if (age < 7 || age > 120) {
// errAge.innerHTML = AGE_VALIDATION;
// result = false;
// }
// }
// if (!result) {
// document.getElementById("age").style.cssText = "border: 1px solid red;";
// }
// return result;
// }

function validateLogin() {
	var result = true, errLogin = document.getElementById("err-login");

	errLogin.innerHTML = "";
	document.getElementById("login").style.cssText = "border: 1px solid #ccc;";

	var regExpLoginStart = new RegExp("^[^0-9]"), regExpLoginEn = new RegExp(
			/^[a-z][a-z_0-9]+$/i);
	var login = document.getElementById("login").value;

	if (!login) {
		errLogin.innerHTML = FILL_FIEAD;
		result = false;
	} else {
		if (login.length < 5) {
			errLogin.innerHTML = NOT_LESS_FIVE_SYMBOLS;
			result = false;
		} else {
			if (!regExpLoginStart.test(login)) {
				errLogin.innerHTML = NOT_BEGIN_WITH_DIGIT;
				result = false;
			} else {
				if (!regExpLoginEn.test(login)) {
					errLogin.innerHTML = LATIN_DIGITS_UNDERLINE;
					result = false;
				}
			}
		}
	}
	if (!result) {
		document.getElementById("login").style.cssText = "border: 1px solid red;";
	}
	return result;
}

function validatePassword() {
	var result = true, errPassword = document.getElementById("err-password");

	errPassword.innerHTML = "";
	document.getElementById("password").style.cssText = "border: 1px solid #ccc;";

	var regExpPasswordEn = new RegExp(
			"^([a-z]+[A-Z]+[0-9]+)|([a-z]+[0-9]+[A-Z]+)|([A-Z]+[a-z]+[0-9]+)|([A-Z]+[0-9]+[a-z]+)|([0-9]+[a-z]+[A-Z]+)|([0-9]+[A-Z]+[a-z]+)$");

	var password = document.getElementById("password").value;

	if (!password) {
		errPassword.innerHTML = FILL_FIEAD;
		result = false;
	} else {
		if (password.length < 6) {
			errPassword.innerHTML = WEAK_PASSWORD;
			result = false;
		} else {
			if (!regExpPasswordEn.test(password)) {
				errPassword.innerHTML = UPPER_LOWER_CASE_DIGITS;
				result = false;
			}
		}
	}
	if (!result) {
		document.getElementById("password").style.cssText = "border: 1px solid red;";
	}
	return result;
}

function validateRPassword() {
	var result = true, errRPassword = document.getElementById("err-rpassword");

	errRPassword.innerHTML = "";
	document.getElementById("rpassword").style.cssText = "border: 1px solid #ccc;";

	var password = document.getElementById("password").value, rpassword = document
			.getElementById("rpassword").value;

	if (!rpassword) {
		errRPassword.innerHTML = FILL_FIEAD;
		result = false;
	} else {
		if (password && password !== rpassword) {
			errRPassword.innerHTML = DIFFERENT_PASSWORDS;
			result = false;
		}
	}
	if (!result) {
		document.getElementById("rpassword").style.cssText = "border: 1px solid red;";
	}
	return result;
}

function validateEmail() {
    var result = true,
        errEmail = document.getElementById("err-email");
    
    errEmail.innerHTML = "";
    document.getElementById("email").style.cssText = "border: 1px solid #ccc;";
    
    var regExpEmail = new RegExp(/^[a-z_\.\-0-9]{1,}@[a-z_]+\.[a-z]{2,4}$/i); 
    var email = document.getElementById("email").value;
    
    if (!email) {
        errEmail.innerHTML = FILL_FIEAD;
        result = false;
    } else {
        if (!regExpEmail.test(email)) {
            errEmail.innerHTML = EMAIL_FORMAT;
            result = false;
        }
    }
    if (!result) {
        document.getElementById("email").style.cssText = "border: 1px solid red;";
    }
    return result;
}

function validateCountry() {
	var result = true, errCountry = document.getElementById("err-country");

	errCountry.innerHTML = "";
	document.getElementById("country").style.cssText = "border: 1px solid #ccc;";

	var regExpRuEn = new RegExp(/^(([а-яё \-]+)|([a-z \-]+))$/i);
	var country = document.getElementById("country").value;

	if (!country) {
		errCountry.innerHTML = FILL_FIEAD;
		result = false;
	} else {
		if (!regExpRuEn.test(country)) {
			errCountry.innerHTML = LATIN_KIRIL_SPACE_DASH;
			result = false;
		}
	}
	if (!result) {
		document.getElementById("country").style.cssText = "border: 1px solid red;";
	}
	return result;
}

function visibility() {
    document.getElementById("list-matches").style.visibility = "hidden";
    return true;
}
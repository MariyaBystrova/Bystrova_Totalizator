package by.tr.totalizator.command;

/**
 * A CommandName represents options of command names having it's own declared
 * access methods to the appropriate resources.
 * 
 * @author Mariya Bystrova
 *
 */
public enum CommandName {
	/**
	 * Command name to log in the system specified by post method. 
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.LoginCommand}
	 */
	LOGINATION(Method.POST),
	/**
	 * Command name to log out the system specified by post method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.LogoutCommand}
	 */
	LOGOUT(Method.POST),
	/**
	 * Command name to register user specified by post method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.user.RegisterUserCommand}
	 */
	REGISTRATION(Method.POST),
	/**
	 * Command name to go to the registration page specified by get method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.user.GoToRegistrationCommand}
	 */
	GO_TO_REGISTRATION(Method.GET),
	/**
	 * Command name to change language specified by post method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.LocalizeCommand}
	 */
	CHANGE_LANGUAGE(Method.POST),
	/**
	 * Command name to go to page which shows the current coupon specified by
	 * get method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.user.ShowCurrentCouponCommand}
	 */
	SHOW_CURRENT_COUPON(Method.GET),
	/**
	 * Command name to go to the page with specified coupon specified by get
	 * method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.admin.ShowCouponMatchesCommand}
	 */
	SHOW_COUPON_MATCHES(Method.GET),
	/**
	 * Command name to go to the general user's page specified by get method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.user.GoToGeneralCommand}
	 */
	GO_TO_GENERAL(Method.GET),
	/**
	 * Command name to go to the forming coupon page specified by get method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.admin.GoToFormCouponCommand}
	 */
	ADMIN_GO_TO_FORM_COUPON(Method.GET),
	/**
	 * Command name to go to the page with all coupons information specified by
	 * get method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.admin.GoToShowAllCouponsCommand}
	 */
	ADMIN_GO_TO_SHOW_ALL_COUPONS(Method.GET),
	/**
	 * Command name to go to the forming matches to the coupon page specified by
	 * get method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.admin.GoToFormMatchesCommand}
	 */
	ADMIN_GO_TO_FORM_MATCHES(Method.GET),
	/**
	 * Command name to go to the edit current or past coupon page specified by
	 * get method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.admin.GoToEditCurrentCouponCommand}
	 */
	ADMIN_GO_TO_EDIT_CURRENT_COUPON(Method.GET),
	/**
	 * Command name to edit match details specified by post method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.admin.EditMatchCommand}
	 */
	EDIT_MATCH(Method.POST),
	/**
	 * Command name to edit match result specified by post method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.admin.EditMatchResultCommand}
	 */
	EDIT_MATCH_RESULT(Method.POST),
	/**
	 * Command name to edit coupon information specified by post method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.admin.EditCouponInfoCommand}
	 */
	EDIT_COUPON_INFO(Method.POST),
	/**
	 * Command name to go to the general administrator's page specified by get
	 * method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.admin.AdminGoToGeneralCommand}
	 */
	ADMIN_GO_TO_GENERAL(Method.GET),
	/**
	 * Command name to go to the page for editing coupon information specified
	 * by get method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.admin.GoToEditCouponInfoCommand}
	 */
	ADMIN_GO_TO_EDIT_COUPON_INFO(Method.GET),
	/**
	 * Command name to register match specified by post method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.admin.CreateMatchCommand}
	 */
	REGISTER_MATCH(Method.POST),
	/**
	 * Command name to register coupon specified by post method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.admin.RegisterCouponCommand}
	 */
	REGISTER_COUPON(Method.POST),
	/**
	 * Command name to go to the page for making a bet specified by get method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.user.GoToMakeBetCommand}
	 */
	GO_TO_MAKE_BET(Method.GET),
	/**
	 * Command name to make bet specified by post method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.user.MakeBetCommand}
	 */
	MAKE_BET(Method.POST),
	/**
	 * Command name to go to the page for editing user's profile specified by
	 * get method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.user.GoToEditProfileCommand}
	 */
	GO_TO_EDIT_PROFILE(Method.GET),
	/**
	 * Command name to edit user's profile details specified by post method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.user.EditProfileCommand}
	 */
	EDIT_PROFILE(Method.POST),
	/**
	 * Command name to edit user's account details specified by post method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.user.EditAccountCommand}
	 */
	EDIT_ACCOUNT(Method.POST),
	/**
	 * Command name to close coupon specified by post method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.admin.CloseCouponCommand}
	 */
	CLOSE_COUPON(Method.POST),
	/**
	 * Any other command name not declared in the system specified by get
	 * method.
	 * 
	 * Represents {@link by.tr.totalizator.command.impl.UnknownCommand}
	 */
	UNKNOWN_COMMAND(Method.GET);

	private Method method;
	private final static String DASH = "-";
	private final static String UNDERLINE = "_";

	/**
	 * @param method
	 *            a value of {@link by.tr.totalizator.command.Method} enumeration.
	 */
	private CommandName(Method method) {
		this.method = method;
	}
	
	/**
	 * Returns a String value of access method by the String value of command name.
	 * 
	 * @param commandName a String value of command name.
	 * @return a String value of access method to the appropriate resources.
	 */
	public static String getMethod(String commandName) {
		CommandName command = null;
		try {
			command = CommandName.valueOf(commandName.replace(DASH, UNDERLINE).toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			command = CommandName.UNKNOWN_COMMAND;
		}
		return command.method.toString();
	}
}

/**
 * A Method enumeration represents options of access methods to the appropriate resources.
 * 
 * @author Mariya Bystrova
 *
 */
enum Method {
	POST, GET
}
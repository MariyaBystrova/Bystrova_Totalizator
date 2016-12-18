package by.tr.totalizator.command;

public enum CommandName {
	LOGINATION(Method.POST), 
	LOGOUT(Method.POST), 					
	REGISTRATION(Method.POST), 
	GO_TO_REGISTRATION(Method.GET), 
	CHANGE_LANGUAGE(Method.POST), 
	SHOW_CURRENT_COUPON(Method.GET), 
	SHOW_COUPON_MATCHES(Method.GET), 
	GO_TO_GENERAL(Method.GET), 
	ADMIN_GO_TO_FORM_COUPON(Method.GET), 
	ADMIN_GO_TO_FORM_MATCHES(Method.GET), 
	ADMIN_GO_TO_EDIT_CURRENT_COUPON(Method.GET),
	EDIT_MATCH(Method.POST),
	ADMIN_GO_TO_GENERAL(Method.GET), 
	ADMIN_GO_TO_CREATE_MATCH(Method.GET), 
	REGISTER_MATCH(Method.POST), 
	REGISTER_COUPON(Method.POST), 
	GO_TO_MAKE_BET(Method.GET),
	MAKE_BET(Method.POST),
	GO_TO_EDIT_PROFILE(Method.GET),
	EDIT_PROFILE(Method.POST),
	EDIT_ACCOUNT(Method.POST),
	UNKNOWN_COMMAND(Method.GET);

	private Method method;
	private final static String DASH = "-";
	private final static String UNDERLINE = "_";

	private CommandName(Method method) {
		this.method = method;
	}	

	public static String getMethod(String commandName){
		CommandName command = null;
		try {
			command = CommandName.valueOf(commandName.replace(DASH, UNDERLINE).toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			command = CommandName.UNKNOWN_COMMAND;
		}
		return command.method.toString();		
	}
}

enum Method {
	POST, GET
}
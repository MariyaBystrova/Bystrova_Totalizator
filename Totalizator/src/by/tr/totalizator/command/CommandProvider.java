package by.tr.totalizator.command;

import java.util.HashMap;
import java.util.Map;

import by.tr.totalizator.command.impl.LocalizeCommand;
import by.tr.totalizator.command.impl.LoginCommand;
import by.tr.totalizator.command.impl.LogoutCommand;
import by.tr.totalizator.command.impl.UnknownCommand;
import by.tr.totalizator.command.impl.admin.AdminGoToGeneralCommand;
import by.tr.totalizator.command.impl.admin.CreateMatchCommand;
import by.tr.totalizator.command.impl.admin.GoToCreateMatchCommand;
import by.tr.totalizator.command.impl.admin.GoToEditCurrentCouponCommand;
import by.tr.totalizator.command.impl.admin.EditMatchCommand;
import by.tr.totalizator.command.impl.admin.GoToFormCouponCommand;
import by.tr.totalizator.command.impl.admin.GoToFormMatchesCommand;
import by.tr.totalizator.command.impl.admin.RegisterCouponCommand;
import by.tr.totalizator.command.impl.admin.ShowCouponMatchesCommand;
import by.tr.totalizator.command.impl.user.EditAccountCommand;
import by.tr.totalizator.command.impl.user.EditProfileCommand;
import by.tr.totalizator.command.impl.user.GoToEditProfileCommand;
import by.tr.totalizator.command.impl.user.GoToGeneralCommand;
import by.tr.totalizator.command.impl.user.GoToMakeBetCommand;
import by.tr.totalizator.command.impl.user.GoToRegistrationCommand;
import by.tr.totalizator.command.impl.user.MakeBetCommand;
import by.tr.totalizator.command.impl.user.RegisterUserCommand;
import by.tr.totalizator.command.impl.user.ShowCurrentCouponCommand;

public final class CommandProvider {
	private final static String DASH = "-";
	private final static String UNDERLINE = "_";
	
	private Map<CommandName, Command> commands = new HashMap<>();

	public CommandProvider() {
		commands.put(CommandName.LOGINATION, new LoginCommand());											//general
		commands.put(CommandName.LOGOUT, new LogoutCommand());												//general
		commands.put(CommandName.GO_TO_REGISTRATION, new GoToRegistrationCommand());				//user
		commands.put(CommandName.REGISTRATION, new RegisterUserCommand());							//user
		commands.put(CommandName.CHANGE_LANGUAGE, new LocalizeCommand());									//general
		commands.put(CommandName.SHOW_CURRENT_COUPON, new ShowCurrentCouponCommand());				//user
		commands.put(CommandName.SHOW_COUPON_MATCHES, new ShowCouponMatchesCommand());		//admin
		commands.put(CommandName.GO_TO_GENERAL, new GoToGeneralCommand());							//user
		commands.put(CommandName.ADMIN_GO_TO_GENERAL, new AdminGoToGeneralCommand());		//admin
		commands.put(CommandName.ADMIN_GO_TO_FORM_COUPON, new GoToFormCouponCommand());		//admin
		commands.put(CommandName.REGISTER_COUPON, new RegisterCouponCommand());				//admin
		commands.put(CommandName.ADMIN_GO_TO_FORM_MATCHES, new GoToFormMatchesCommand());	//admin
		commands.put(CommandName.ADMIN_GO_TO_CREATE_MATCH, new GoToCreateMatchCommand());	//admin
		commands.put(CommandName.ADMIN_GO_TO_EDIT_CURRENT_COUPON, new GoToEditCurrentCouponCommand());	//admin
		commands.put(CommandName.EDIT_MATCH, new EditMatchCommand());				//admin
		commands.put(CommandName.REGISTER_MATCH, new CreateMatchCommand());					//admin
		commands.put(CommandName.MAKE_BET, new MakeBetCommand());									//user
		commands.put(CommandName.GO_TO_MAKE_BET, new GoToMakeBetCommand());							//user
		commands.put(CommandName.GO_TO_EDIT_PROFILE, new GoToEditProfileCommand());					//user
		commands.put(CommandName.EDIT_PROFILE, new EditProfileCommand());							//user
		commands.put(CommandName.EDIT_ACCOUNT, new EditAccountCommand());							//user
		commands.put(CommandName.UNKNOWN_COMMAND, new UnknownCommand());									//general
	}	

	public Command getCommand(String commandName) {

		Command command = null;
		CommandName key = null;

		commandName = commandName.replace(DASH, UNDERLINE).toUpperCase();
		try {
			key = CommandName.valueOf(commandName);
		} catch (IllegalArgumentException e) {
			key = CommandName.UNKNOWN_COMMAND;
		}
		command = commands.get(key);
		return command;
	}
	
	
	
	
	
	

	// public Command getCommand(String commandName) {
	// Command command = null;
	// CommandName key = CommandName.UNKNOWN_COMMAND;
	// commandName = commandName.replace('-', '_').toUpperCase();
	// CommandName[] cn = CommandName.values();
	//
	// for (CommandName c : cn) {
	// if (commandName.equals(c.toString())) {
	// key = CommandName.valueOf(commandName);
	// break;
	// }
	// }
	// command = commands.get(key);
	//
	// return command;
	// }

}

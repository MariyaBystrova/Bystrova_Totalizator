package by.tr.totalizator.command;

import java.util.HashMap;
import java.util.Map;

import by.tr.totalizator.command.impl.LocalizeCommand;
import by.tr.totalizator.command.impl.LoginCommand;
import by.tr.totalizator.command.impl.LogoutCommand;
import by.tr.totalizator.command.impl.UnknownCommand;
import by.tr.totalizator.command.impl.admin.AdminGoToGeneralCommand;
import by.tr.totalizator.command.impl.admin.CloseCouponCommand;
import by.tr.totalizator.command.impl.admin.CreateMatchCommand;
import by.tr.totalizator.command.impl.admin.DeleteCouponCommand;
import by.tr.totalizator.command.impl.admin.EditCouponInfoCommand;
import by.tr.totalizator.command.impl.admin.GoToEditCouponInfoCommand;
import by.tr.totalizator.command.impl.admin.GoToEditCurrentCouponCommand;
import by.tr.totalizator.command.impl.admin.EditMatchCommand;
import by.tr.totalizator.command.impl.admin.EditMatchResultCommand;
import by.tr.totalizator.command.impl.admin.GoToFormCouponCommand;
import by.tr.totalizator.command.impl.admin.GoToFormMatchesCommand;
import by.tr.totalizator.command.impl.admin.GoToShowAllCouponsCommand;
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
/**
 * Represents the class which provides matching of command names and implementations of {@link by.tr.totalizator.command.Command}.
 * 
 * @author Mariya Bystrova
 *
 */
public final class CommandProvider {
	private final static String DASH = "-";
	private final static String UNDERLINE = "_";

	private Map<CommandName, Command> commands = new HashMap<>();

	public CommandProvider() {
		// user
		commands.put(CommandName.GO_TO_REGISTRATION, new GoToRegistrationCommand());
		commands.put(CommandName.REGISTRATION, new RegisterUserCommand());
		commands.put(CommandName.SHOW_CURRENT_COUPON, new ShowCurrentCouponCommand());
		commands.put(CommandName.GO_TO_GENERAL, new GoToGeneralCommand());
		commands.put(CommandName.MAKE_BET, new MakeBetCommand());
		commands.put(CommandName.GO_TO_MAKE_BET, new GoToMakeBetCommand());
		commands.put(CommandName.GO_TO_EDIT_PROFILE, new GoToEditProfileCommand());
		commands.put(CommandName.EDIT_PROFILE, new EditProfileCommand());
		commands.put(CommandName.EDIT_ACCOUNT, new EditAccountCommand());

		// admin
		commands.put(CommandName.SHOW_COUPON_MATCHES, new ShowCouponMatchesCommand());
		commands.put(CommandName.ADMIN_GO_TO_GENERAL, new AdminGoToGeneralCommand());
		commands.put(CommandName.ADMIN_GO_TO_EDIT_COUPON_INFO, new GoToEditCouponInfoCommand());
		commands.put(CommandName.ADMIN_GO_TO_FORM_COUPON, new GoToFormCouponCommand());
		commands.put(CommandName.REGISTER_COUPON, new RegisterCouponCommand());
		commands.put(CommandName.ADMIN_GO_TO_FORM_MATCHES, new GoToFormMatchesCommand());
		commands.put(CommandName.ADMIN_GO_TO_EDIT_CURRENT_COUPON, new GoToEditCurrentCouponCommand());
		commands.put(CommandName.REGISTER_MATCH, new CreateMatchCommand());
		commands.put(CommandName.ADMIN_GO_TO_SHOW_ALL_COUPONS, new GoToShowAllCouponsCommand());
		commands.put(CommandName.EDIT_COUPON_INFO, new EditCouponInfoCommand());
		commands.put(CommandName.EDIT_MATCH, new EditMatchCommand());
		commands.put(CommandName.EDIT_MATCH_RESULT, new EditMatchResultCommand());
		commands.put(CommandName.DELETE_COUPON, new DeleteCouponCommand());
		commands.put(CommandName.CLOSE_COUPON, new CloseCouponCommand());

		// general
		commands.put(CommandName.LOGINATION, new LoginCommand());
		commands.put(CommandName.LOGOUT, new LogoutCommand());
		commands.put(CommandName.CHANGE_LANGUAGE, new LocalizeCommand());
		commands.put(CommandName.UNKNOWN_COMMAND, new UnknownCommand());
	}

	/**
	 * Returns a Command object associated with the command name.
	 * 
	 * @param commandName
	 *            a String value of command name.
	 * @return a Command object associated with the command name or
	 *         {@link by.tr.totalizator.command.impl.UnknownCommand} in case of
	 *         no match.
	 */
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
}

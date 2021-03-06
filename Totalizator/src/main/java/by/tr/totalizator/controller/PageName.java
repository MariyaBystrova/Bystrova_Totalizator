package by.tr.totalizator.controller;

/**
 * Represents options of pages available in a system.
 * 
 * @author Mariya Bystriva
 *
 */
public final class PageName {

	private PageName() {
	}

	public static final String INDEX_PAGE = "index.jsp";
	public static final String REGISTRATION_PAGE = "/WEB-INF/jsp/registration.jsp";
	public static final String ERROR_PAGE = "/WEB-INF/jsp/error.jsp";

	public static final String USER_PAGE_GENERAL = "/WEB-INF/jsp/user_welcome_page.jsp";
	public static final String USER_PAGE_TOTO = "/WEB-INF/jsp/user_toto_menu.jsp";
	public static final String USER_PAGE_EDIT_PROFILE = "/WEB-INF/jsp/user_edit_profile.jsp";
	public static final String USER_PAGE_MAKE_BET = "/WEB-INF/jsp/user_make_bet.jsp";

	public static final String ADMIN_PAGE_GENERAL = "/WEB-INF/jsp/admin_welcome_page.jsp";
	public static final String ADMIN_FORM_MATCHES_MENU_PAGE = "/WEB-INF/jsp/admin_form_matches_menu.jsp";
	public static final String ADMIN_FROM_COUPON_MENU_PAGE = "/WEB-INF/jsp/admin_form_coupon_menu.jsp";
	public static final String ADMIN_EDIT_CURRENT_COUPON = "/WEB-INF/jsp/admin_edit_current_coupon_menu.jsp";
	public static final String ADMIN_SHOW_ALL_COUPONS_PAGE = "/WEB-INF/jsp/admin_show_all_coupons_menu.jsp";
	public static final String ADMIN_EDIT_COUPON_INFO = "/WEB-INF/jsp/admin_edit_coupon_info.jsp";

}

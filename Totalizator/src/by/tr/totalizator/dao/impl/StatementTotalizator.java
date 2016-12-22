package by.tr.totalizator.dao.impl;

public final class StatementTotalizator {

	private StatementTotalizator(){
	}
	
	public final static String SELECT_MATCHES_WHERE_CUPONID = "SELECT m.`match_id`, m.`match_name`, m.`cupon_id`, m.`team_one`, m.`team_two`, m.`start_date`, m.`end_date`, m.`real_result`, m.`status_id` "
															+ "FROM `match` AS m "
															+ "WHERE m.`cupon_id`=?;";
	
	//private final static String SELECT_CURRENT_COUPON_MATCHES = "SELECT m.`match_id`, m.`match_name`, m.`cupon_id`, m.`team_one`, m.`team_two`, m.`start_date`, m.`end_date` FROM `match` AS m JOIN `cupon` AS c ON c.`cupon_id`=m.`cupon_id`WHERE c.`start_date`<= NOW() AND  c.`status_id` = 1 ORDER BY m.`match_id`;";

	public final static String SELECT_CURRENT_COUPON_MATCHES = "SELECT m.`match_id`, m.`match_name`, m.`cupon_id`, m.`team_one`, m.`team_two`, m.`start_date`, m.`end_date`, m.`real_result`, m.`status_id` "
															+ "FROM `match` AS m "
															+ "JOIN `cupon` AS c ON c.`cupon_id`=m.`cupon_id` "
															+ "WHERE c.`start_date`<= NOW() AND c.`end_date`> NOW() AND c.`status_id` = 1;";
	
	public final static String SELECT_MIN_BET_AMOUNT_BY_COUPONID = "SELECT `min_bet_amount` FROM `cupon` WHERE `cupon_id`=?;";
	
	public final static String SELECT_FREE_VALID_COUPONS = "SELECT c.`cupon_id`,c.`start_date`,c.`end_date`, c.`min_bet_amount`, c.`cupon_pull`, c.`jackpot`, c.`status_id` "
														+ "FROM `cupon` AS c "
														+ "WHERE c.`start_date`> NOW() AND c.`status_id` = 6;";
	
	public final static String INSERT_INTO_COUPON = "INSERT INTO `cupon`(`start_date`,`end_date`,`min_bet_amount`,`cupon_pull`,`jackpot`,`status_id`)VALUES(?,?,?,0,0,6);";
	
	public final static String INSERT_INTO_MATCH = "INSERT INTO `match`(`match_name`,`cupon_id`,`team_one`,`team_two`,`start_date`,`end_date`,`real_result`,`status_id`) "
													+ "SELECT * FROM (SELECT ? as match_name, ? as cupon_id, ? as team_one, ? as team_two, ? as start_date, ? as end_date, NULL, 2) AS tmp "
															+ "WHERE EXISTS (SELECT c.cupon_id "
																		+ "FROM `cupon` AS c "
																		+ "WHERE DATE_ADD(c.end_date, INTERVAL 2 DAY) > tmp.start_date AND c.end_date < tmp.start_date AND c.status_id=6 ) LIMIT 1;";
	
	public final static String UPDATE_MATCH = "UPDATE `totalizator`.`match` as m "
											+ "JOIN (SELECT tmp.match_id, tmp.match_name, tmp.team_one, tmp.team_two, tmp.start_date, tmp.end_date "
													+ "FROM (SELECT ? as match_name, ? as match_id, ? as team_one, ? as team_two, ? as start_date, ? as end_date) AS tmp "
													+ "JOIN `match` AS ma ON ma.match_id=tmp.match_id "
													+ "WHERE EXISTS (SELECT c.cupon_id "
																	+ "FROM `cupon` AS c "
																	+ "WHERE c.cupon_id=ma.cupon_id AND c.status_id=6 AND  DATE_ADD(c.end_date, INTERVAL 2 DAY) > tmp.start_date AND c.end_date < tmp.start_date) "
													+ "LIMIT 1) AS q ON q.match_id=m.match_id "
											+ "SET m.`match_name` = q.match_name, m.`team_one` = q.team_one, m.`team_two` = q.team_two, m.`start_date` = q.start_date, m.`end_date` = q.end_date;";
	
	//public final static String INSERT_INTO_BET = "INSERT INTO `bet` (`user_id`,`cupon_id`,`bet_amount`,`transaction_date`,`creditcard_number`,`win_match_count`,`win_bet_amount`) VALUES(?,?,?,NOW(),?,NULL,NULL);";
	
	public final static String INSERT_INTO_BET = "INSERT INTO `bet` (`user_id`,`cupon_id`,`bet_amount`,`transaction_date`,`creditcard_number`,`win_match_count`,`win_bet_amount`) "
												+"SELECT * FROM (SELECT ? as user_id, ? as cupon_id, ? as bet_amount, NOW() as transaction_date, ? as creditcard_number, NULL as win_match_count, NULL as win_bet_amount) AS tmp "
												+"WHERE EXISTS (SELECT c.cupon_id FROM `cupon` as c WHERE c.min_bet_amount<=tmp.bet_amount);";
	
	public final static String LAST_INSERTED_ID = "SELECT LAST_INSERT_ID();";
	
	public final static String INSERT_INTO_USER_BET_DETAIL = "INSERT INTO `user_bet_detail`(`bet_id`,`match_id`,`result`,`win_flag`) VALUES (?,?,?,NULL);";
	
	public final static String SELECT_NOT_CLOSED_COUPONS = "SELECT c.`cupon_id`,c.`start_date`,c.`end_date`, c.`min_bet_amount`, c.`cupon_pull`, c.`jackpot`, c.`status_id` "
															+ "FROM `cupon` AS c "
															+ "WHERE c.`status_id` in (1,6);";
	
	public final static String UPDATE_MATCH_DATES_RESULT_STATUS = 	"UPDATE `totalizator`.`match` as m "
																	+"JOIN 	(SELECT tmp.match_id, tmp.start_date, tmp.end_date, tmp.status_id, tmp.real_result "
																			+"FROM (SELECT ? as match_id, ? as start_date, ? as end_date, ? as status_id, ? as real_result) AS tmp "
																			+"JOIN `match` AS ma ON ma.match_id=tmp.match_id "
																			+"WHERE EXISTS 	(SELECT c.cupon_id 	"
																							+"FROM `cupon` AS c "
																							+" WHERE c.cupon_id=ma.cupon_id AND DATE_ADD(c.end_date, INTERVAL 2 DAY) > tmp.start_date "
																							+" AND c.end_date < tmp.start_date "
																							+"AND ((NOW()>tmp.end_date AND tmp.status_id = 5 AND tmp.real_result in ('1', '2', 'x'))"
																							+" OR "
																							+"(NOW()<tmp.end_date AND tmp.status_id in (2,4) AND tmp.real_result is NULL)) LIMIT 1)"
																							+ " OR "
																							+"(NOW()>tmp.end_date AND tmp.status_id = 4 AND tmp.real_result is NULL))  AS q "
																	+"ON q.match_id=m.match_id "
																	+"SET m.`start_date` = q.start_date, m.`end_date` = q.end_date, m.`status_id`=q.status_id, m.`real_result`=q.real_result;";
	
	public final static String CLOSE_COUPON_PROCEDURE = "{call results_processing_procedure (?)}"; 
}

package by.tr.totalizator.service.impl;

import java.sql.Timestamp;
import java.util.List;

import by.tr.totalizator.bean.CouponBean;
import by.tr.totalizator.bean.MatchBean;
import by.tr.totalizator.bean.RegisterBetBean;
import by.tr.totalizator.dao.TotalizatorOperationDAO;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.factory.DAOFactory;
import by.tr.totalizator.entity.Coupon;
import by.tr.totalizator.entity.Match;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.impl.util.Validator;

public class EditTotalizator implements TotalizatorService {

	@Override
	public List<Match> getCuponMatches(int cuponId) throws ServiceException {
		if (cuponId <= 0) {
			throw new ServiceException("Invalid data: cupon_id must be greater then 0.");
		}

		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();

		List<Match> list = null;
		try {
			list = totoDAO.getCuponMatches(cuponId);
		} catch (DAOException e) {
			throw new ServiceException("Get coupon matches failed.", e);
		}
		return list;
	}

	@Override
	public List<Match> getCurrentCupon() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();

		List<Match> list = null;
		try {
			list = totoDAO.getCurrentCoupon();
		} catch (DAOException e) {
			throw new ServiceException("Get coupon matches failed.", e);
		}
		return list;
	}
	
	@Override
	public int getMinBetAmount(int couponId) throws ServiceException {
		if(couponId<=0){
			throw new ServiceException("CouponId must be greater then 0.");
		}
		
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		
		try {
			return totoDAO.getMinBetAmount(couponId);
		} catch (DAOException e) {
			throw new ServiceException("Get min bet amount failed.", e);
		}
	}

	@Override
	public List<Coupon> getEmptyValidCoupons() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();

		List<Coupon> list = null;
		try {
			list = totoDAO.getEmptyValidCoupons();
		} catch (DAOException e) {
			throw new ServiceException("Get empty valid coupons failed.", e);
		}
		return list;
	}

	@Override
	public boolean registerCoupon(String startDate, String endDate, int minBetAmount) throws ServiceException {
		Timestamp start;
		Timestamp end;
		
		startDate = formatDate(startDate);
		endDate = formatDate(endDate);

		if (Validator.validateCoupon(startDate, endDate, minBetAmount)) {
			start = Timestamp.valueOf(startDate);
			end = Timestamp.valueOf(endDate);
		} else {
			throw new ServiceException("Invalid data.");
		}

		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		boolean result = false;

		if (start.before(end)) {
			try {
				result = totoDAO.registerCoupon(start, end, minBetAmount);
			} catch (DAOException e) {
				throw new ServiceException("Register coupon failed.", e);
			}
		}

		return result;
	}

	@Override
	public boolean registerBet(RegisterBetBean bean) throws ServiceException {
		
		boolean result = Validator.validateBet(bean.getMap(), bean.getAmount(), bean.getCreditCardNumber(), bean.getUserId(), bean.getCouponId());
		if (!result) {
			throw new ServiceException("Invalid data.");
		}
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		try {
			result = totoDAO.registerBet(bean.getMap(), bean.getAmount(), bean.getCreditCardNumber(), bean.getUserId(), Integer.parseInt(bean.getCouponId()));
		} catch (DAOException e) {
			throw new ServiceException("Register bet failed.", e);
		}
		return result;
	}

	@Override
	public boolean registerMatch(MatchBean match) throws ServiceException {
		Timestamp start;
		Timestamp end;

		match.setStartDate(formatDate(match.getStartDate()));
		match.setEndDate(formatDate(match.getEndDate()));

		if (Validator.validateMatch(match)) {
			start = Timestamp.valueOf(match.getStartDate());
			end = Timestamp.valueOf(match.getEndDate());
		} else {
			throw new ServiceException("Invalid data.");
		}

		Match matchEntity = new Match(match.getName(), match.getTeamOne(), match.getTeamTwo(), start, end);

		if (match.getCouponId() != null) {
			matchEntity.setCouponId(Integer.parseInt(match.getCouponId()));
		}
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		boolean result = false;
		if (start.before(end)) {
			try {
				result = totoDAO.registerMatch(matchEntity);
			} catch (DAOException e) {
				throw new ServiceException("Register match failed.", e);
			}
		}

		return result;
	}

	@Override
	public boolean editMatch(MatchBean match) throws ServiceException {
		Timestamp start;
		Timestamp end;

		match.setStartDate(formatDate(match.getStartDate()));
		match.setEndDate(formatDate(match.getEndDate()));

		if (Validator.validateMatch(match)) {
			start = Timestamp.valueOf(match.getStartDate());
			end = Timestamp.valueOf(match.getEndDate());
		} else {
			throw new ServiceException("Invalid data.");
		}

		Match matchEntity = new Match(match.getName(), match.getTeamOne(), match.getTeamTwo(), start, end);

		if (match.getId() != null) {
			matchEntity.setId(Integer.parseInt(match.getId()));
		}
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		boolean result = false;
		if (start.before(end)) {
			try {
				result = totoDAO.editMatch(matchEntity);
			} catch (DAOException e) {
				throw new ServiceException("Register match failed.", e);
			}
		}
		return result;
	}

	@Override
	public List<Coupon> getCurrentCoupons() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		List<Coupon> list;
		try {
			list = totoDAO.getCurrentCoupons();
		} catch (DAOException e) {
			throw new ServiceException("Get current coupons failed.", e);
		}
		return list;
	}

	@Override
	public boolean editMatchResStatus(MatchBean match) throws ServiceException {
		Timestamp start;
		Timestamp end;

		match.setStartDate(formatDate(match.getStartDate()));
		match.setEndDate(formatDate(match.getEndDate()));

		if (Validator.validateMatchDatesResultStatus(match)) {
			start = Timestamp.valueOf(match.getStartDate());
			end = Timestamp.valueOf(match.getEndDate());
		} else {
			throw new ServiceException("Invalid data.");
		}
		
		Match matchEntity = new Match();
		matchEntity.setStartDate(start);
		matchEntity.setEndDate(end);
		if(!match.getResult().equals("NULL")){
			matchEntity.setResult(match.getResult());
		}
		matchEntity.setStatus(Integer.parseInt(match.getStatus()));
		if (match.getId() != null) {
			matchEntity.setId(Integer.parseInt(match.getId()));
		}
		
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		boolean result = false;
		if (start.before(end)) {
			try {
				result = totoDAO.editMatchResult(matchEntity);
			} catch (DAOException e) {
				throw new ServiceException("Register match failed.", e);
			}
		}
		return result;
	}

	private String formatDate(String date) {
		date = date.replace('T', ' ');
		date = date.replaceAll("%3A", ":");
		date = date.concat(":00");
		return date;
	}

	@Override
	public boolean closeCoupon(String couponId) throws ServiceException {
		boolean result = Validator.validateCouponId(couponId);
		if (!result) {
			throw new ServiceException("Invalid data.");
		}
		
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		try {
			result = totoDAO.closeCoupon(Integer.parseInt(couponId));
		} catch (DAOException e) {
			throw new ServiceException("Close coupon failed.", e);
		}
		return result;
	}

	
	@Override
	public List<Coupon> getAllCoupons() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();

		List<Coupon> list = null;
		try {
			list = totoDAO.getAllCoupons();
		} catch (DAOException e) {
			throw new ServiceException("Get all coupons failed.", e);
		}
		return list;
	}

	@Override
	public Coupon getCouponById(String id) throws ServiceException {
		if(!Validator.validateCouponId(id)){
			throw new ServiceException("Invalid data.");
		}
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		try {
			Coupon coupon = totoDAO.getCouponById(Integer.parseInt(id));
			return coupon;
		} catch (DAOException e) {
			throw new ServiceException("Get coupon failed.", e);
		}
		
	}

	@Override
	public boolean editCouponInfo(CouponBean coupon) throws ServiceException {
		coupon.setStartDate(formatDate(coupon.getStartDate()));
		coupon.setEndDate(formatDate(coupon.getEndDate()));
		
		if(!Validator.validateCoupon(coupon)){
			throw new ServiceException("Invalid data.");
		}
		
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		try {
			Coupon couponEntity = new Coupon();
			couponEntity.setId(Integer.parseInt(coupon.getId()));
			couponEntity.setPull(Integer.parseInt(coupon.getPull()));
			couponEntity.setJackpot(Integer.parseInt(coupon.getJackpot()));
			couponEntity.setMinBetAmount(Integer.parseInt(coupon.getMinBetAmount()));
			couponEntity.setStatus(Integer.parseInt(coupon.getStatus()));
			couponEntity.setStartDate(Timestamp.valueOf(coupon.getStartDate()));
			couponEntity.setEndDate(Timestamp.valueOf(coupon.getEndDate()));
			
			boolean result = totoDAO.editCouponInfo(couponEntity);
			return result;
		} catch (DAOException e) {
			throw new ServiceException("Edit coupon failed.", e);
		}
	}

}
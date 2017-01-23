package by.tr.totalizator.service.impl;

import java.sql.Timestamp;
import java.util.List;

import by.tr.totalizator.dao.TotalizatorOperationDAO;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.exception.NotAllFinishedMatchesDAOException;
import by.tr.totalizator.dao.factory.DAOFactory;
import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.entity.bean.Match;
import by.tr.totalizator.entity.dto.CouponDTO;
import by.tr.totalizator.entity.dto.MatchDTO;
import by.tr.totalizator.entity.dto.RegisterBetDTO;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.NotAllFinishedMatchesServiceException;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.impl.util.Validator;

public class EditTotalizator implements TotalizatorService {

	@Override
	public List<Match> getCuponMatches(int cuponId) throws ServiceException, ServiceDataException {
		if (cuponId <= 0) {
			throw new ServiceDataException("Invalid data: cupon_id must be greater then 0.");
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
			list = totoDAO.getCurrentCouponMatches();
		} catch (DAOException e) {
			throw new ServiceException("Get coupon matches failed.", e);
		}
		return list;
	}

	@Override
	public int getMinBetAmount(int couponId) throws ServiceException, ServiceDataException {
		if (couponId <= 0) {
			throw new ServiceDataException("CouponId must be greater then 0.");
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
	public boolean registerCoupon(String startDate, String endDate, int minBetAmount) throws ServiceException, ServiceDataException {
		Timestamp start;
		Timestamp end;

		startDate = formatDate(startDate);
		endDate = formatDate(endDate);

		if (Validator.validateCoupon(startDate, endDate, minBetAmount)) {
			start = Timestamp.valueOf(startDate);
			end = Timestamp.valueOf(endDate);
		} else {
			throw new ServiceDataException("Invalid data.");
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
	public boolean registerBet(RegisterBetDTO bean) throws ServiceException, ServiceDataException {

		boolean result = Validator.validateBet(bean.getMap(), bean.getAmount(), bean.getCreditCardNumber(),
				bean.getUserId(), bean.getCouponId());
		if (!result) {
			throw new ServiceDataException("Invalid data.");
		}
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		try {
			result = totoDAO.registerBet(bean.getMap(), bean.getAmount(), bean.getCreditCardNumber(), bean.getUserId(),
					Integer.parseInt(bean.getCouponId()));
		} catch (DAOException e) {
			throw new ServiceException("Register bet failed.", e);
		}
		return result;
	}

	@Override
	public boolean registerMatch(MatchDTO match) throws ServiceException, ServiceDataException {
		Timestamp start;
		Timestamp end;

		match.setStartDate(formatDate(match.getStartDate()));
		match.setEndDate(formatDate(match.getEndDate()));

		if (Validator.validateMatch(match)) {
			start = Timestamp.valueOf(match.getStartDate());
			end = Timestamp.valueOf(match.getEndDate());
		} else {
			throw new ServiceDataException("Invalid data.");
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
	public boolean editMatch(MatchDTO match) throws ServiceException, ServiceDataException {
		Timestamp start;
		Timestamp end;

		match.setStartDate(formatDate(match.getStartDate()));
		match.setEndDate(formatDate(match.getEndDate()));

		if (Validator.validateMatch(match)) {
			start = Timestamp.valueOf(match.getStartDate());
			end = Timestamp.valueOf(match.getEndDate());
		} else {
			throw new ServiceDataException("Invalid data.");
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
	public boolean editMatchResStatus(MatchDTO match) throws ServiceException, ServiceDataException {
		Timestamp start;
		Timestamp end;

		match.setStartDate(formatDate(match.getStartDate()));
		match.setEndDate(formatDate(match.getEndDate()));

		if (Validator.validateMatchDatesResultStatus(match)) {
			start = Timestamp.valueOf(match.getStartDate());
			end = Timestamp.valueOf(match.getEndDate());
		} else {
			throw new ServiceDataException("Invalid data.");
		}

		Match matchEntity = new Match();
		matchEntity.setStartDate(start);
		matchEntity.setEndDate(end);
		if (!match.getResult().equals("NULL")) {
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

	@Override
	public boolean closeCoupon(String couponId) throws NotAllFinishedMatchesServiceException, ServiceException, ServiceDataException {
		boolean result = Validator.validateCouponId(couponId);
		if (!result) {
			throw new ServiceDataException("Invalid data.");
		}

		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();

		try {
			result = totoDAO.closeCoupon(Integer.parseInt(couponId));
		} catch (NotAllFinishedMatchesDAOException e) {
			throw new NotAllFinishedMatchesServiceException("Close coupon failed.", e);
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
	public Coupon getCouponById(String id) throws ServiceException, ServiceDataException {
		if (!Validator.validateCouponId(id)) {
			throw new ServiceDataException("Invalid data.");
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
	public boolean editCouponInfo(CouponDTO coupon) throws ServiceException, ServiceDataException {
		coupon.setStartDate(formatDate(coupon.getStartDate()));
		coupon.setEndDate(formatDate(coupon.getEndDate()));

		if (!Validator.validateCoupon(coupon)) {
			throw new ServiceDataException("Invalid data.");
		}

		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		
		Timestamp start = Timestamp.valueOf(coupon.getStartDate());
		Timestamp end = Timestamp.valueOf(coupon.getEndDate());
		if(start.after(end)){
			throw new ServiceDataException("Invalid dates: start date is after end date");
		}
		try {
			Coupon couponEntity = new Coupon();
			couponEntity.setId(Integer.parseInt(coupon.getId()));
			couponEntity.setPull(Integer.parseInt(coupon.getPull()));
			couponEntity.setJackpot(Integer.parseInt(coupon.getJackpot()));
			couponEntity.setMinBetAmount(Integer.parseInt(coupon.getMinBetAmount()));
			couponEntity.setStatus(Integer.parseInt(coupon.getStatus()));
			couponEntity.setStartDate(start);
			couponEntity.setEndDate(end);

			boolean result = totoDAO.editCouponInfo(couponEntity);
			return result;
		} catch (DAOException e) {
			throw new ServiceException("Edit coupon failed.", e);
		}
	}
	

	private String formatDate(String date) {
		date = date.replace('T', ' ');
		date = date.replaceAll("%3A", ":");
		date = date.concat(":00");
		return date;
	}


}

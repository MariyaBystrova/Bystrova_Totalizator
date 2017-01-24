package by.tr.totalizator.tag.bean;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.entity.bean.Match;

/**
 * Represents a UseBean component for list with iterator.
 * 
 * @author Mariya Bystrova
 *
 */
public class JSPListBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<? extends Object> list;
	private Iterator<? extends Object> iterator;

	public JSPListBean() {
	}

	public JSPListBean(List<? extends Object> list) {
		this.list = list;
	}

	/**
	 * Returns true if this list contains no elements.
	 * 
	 * @return true if this list contains no elements.
	 */
	public boolean isEmpty() {
		boolean result = false;
		if (list != null) {
			result = list.isEmpty();
		}
		return result;
	}

	/**
	 * Initializes an iterator over the elements in this list and returns the
	 * number of elements in this list in String format.
	 * 
	 * @return the number of elements in this list in String format.
	 */
	public String getSize() {
		iterator = list.iterator();
		return Integer.toString(list.size());
	}

	/**
	 * Returns the next element(Match) in n the iteration.
	 * 
	 * @return the next element(Match) in n the iteration.
	 */
	public Match getMatchElement() {
		return (Match) iterator.next();
	}

	/**
	 * Returns the next element(Coupon) in n the iteration.
	 * 
	 * @return the next element(Coupon) in n the iteration.
	 */
	public Coupon getCouponElement() {
		return (Coupon) iterator.next();
	}

	public List<? extends Object> getList() {
		return list;
	}

	public void setList(List<? extends Object> list) {
		this.list = list;
	}

}

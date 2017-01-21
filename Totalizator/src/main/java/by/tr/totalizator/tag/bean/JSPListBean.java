package by.tr.totalizator.tag.bean;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.entity.bean.Match;

public class JSPListBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<? extends Object> list;
	private Iterator<? extends Object> iterator;

	public JSPListBean() {
	}

	public JSPListBean(List<? extends Object> list) {
		this.list = list;
	}

	public boolean isEmpty() {
		boolean result = false;
		if (list != null) {
			result = list.isEmpty();
		}
		return result;
	}

	public String getSize() {
		iterator = list.iterator();
		return Integer.toString(list.size());
	}

	public Match getMatchElement() {
		return (Match) iterator.next();
	}

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

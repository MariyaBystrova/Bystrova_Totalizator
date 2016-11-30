package by.tr.totalizator.tag.bean;

import java.util.Iterator;
import java.util.List;

import by.tr.totalizator.entity.Coupon;
import by.tr.totalizator.entity.Match;

public class JSPListBean {
	private List<? extends Object> list;
	private Iterator<? extends Object> iterator;
	
	public JSPListBean(){
	}
	
	public JSPListBean(List<? extends Object> list){
		this.list = list;
	}
	
	public String getSize(){
		iterator = list.iterator();
		return Integer.toString(list.size());
	}
	public Match getMatchElement(){
		return (Match)iterator.next();
	}
	public Coupon getCouponElement(){
		return (Coupon)iterator.next();
	}
}

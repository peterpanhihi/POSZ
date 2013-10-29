package com.pos.sale;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.pos.posz.Item;


public class Sale {
	private Calendar date; // date of the sale
	private List<SalesLineItem> items; // items in the sale
	private boolean isComplete;
	private Payment payment;

	/** create a new sale with a given customer 
	 * @param customer the purchaser 
	 */
	public Sale() {
		items = new ArrayList<SalesLineItem>( );
		date = Calendar.getInstance(); // maybe updated when the sale ends?
		isComplete = false;
	}

	public boolean isComplete(){
		return isComplete;
	}
	
	public void becomeComplete(){
		isComplete = true;
	}
	
	public void makePayment( ){
		payment = new Payment();
	}
	
	public double getTotal(){
		double total = 0.0;
		for( SalesLineItem item : items ) {
			total += item.getSubtotal();
		}
		return total;
	}

	public double getBalance(){
		return payment.getAmount() - getTotal();
	}

	/** add an item to the current sale.
	 * @param pd is the description of the item to add
	 * @param quantity is the quantity of the item to add
	 * @return true if item added successfully to sale.
	 */
	public boolean addLineItem( Item item , int quantity) {
		boolean isAdd = false;
		for(int i = 0 ; i<items.size();i++){
			if(items.get(i).getProductId().equals(item.getProductid())){
				items.get(i).setQuantity(items.get(i).getQuantity()+quantity);
				isAdd = true;
				return true;
			}
		}
		if(!isAdd){
			SalesLineItem lineitem = new SalesLineItem(item,quantity);
			items.add(lineitem);
			return true;
		}
		
		return false;
	}
	
	public void removeLineItem(String id){
		for(int i = 0; i<items.size();i++)
			if(items.get(i).getProductId().equals(id)) items.remove(i);
	}

	/** 
	 * Create and return an iterator of the sale items.
	 * Note that if the contents of the sale change after creating
	 * an iterator that the Iterator will probably be invalid and
	 * throw exception if it is used again. 
	 * @return an Iterator of items in this sale
	 */
	public Iterator<SalesLineItem> iterator() {
		return items.iterator();
	}

	/** Return the number of items in the sale.
	 * @return number of items in the sale.
	 */
	public int size() {
		return items.size();
	}

	/**
	 * Get the k-th item in the list of sale items
	 * @param k is index of item to get
	 * @return the k-th item in this Sale
	 */
	public SalesLineItem getLineItem(int k) {
		if ( k >= 0 && k < items.size() ) return items.get(k);
		else return null;
	}

	/**
	 * Get the date that the sale was made.  This is the date that the
	 * sale object was created.
	 * @return the date of this sale
	 */
	public Calendar getDate() {
		return date;
	}
	
	public List<SalesLineItem> getList(){
		return items;
	}
	
	public boolean setQnty(String id,int qnty){
		for(int i = 0; i<items.size();i++)
			if(items.get(i).getProductId().equals(id)) {items.get(i).setQuantity(qnty);return true;}
		return false;
	}
}

package com.pos.sale;

import java.util.List;

import com.pos.posz.InventoryCollection;
import com.pos.posz.Item;


public class Register {
	private static Register register = null;
	
	/** Need the product catalog for finding items by id */
	private InventoryCollection invent;

	/** the current sale, if any */
	private Sale sale;

	
	public static Register getInstance(){
		if(register == null)register = new Register();
		return register;
	}

	public void startSale (){
		if (sale != null) throw new IllegalStateException("Sale already started. End sale first");
		sale = new Sale();
	}
	
	public void setInvent(InventoryCollection invent){
		this.invent = invent;
	}

	/**
	 * Add an item to the sale.
	 * @param id Product id of item to add
	 * @param quantity the quantity of product to add
	 * @return reference to the Product for this item id
	 * @throws IllegalArgumentException if parameters are invalid
	 */
	public boolean addItem(String id, int quantity) {
		if (sale == null) startSale();
		Item item = invent.findById(id);
		if (item == null) return false;
		sale.addLineItem( item, quantity );
		return true;
	}
	
	public void removeItem(String id){
		sale.removeLineItem(id);
	}

	/**
	 * Get the total price of sale.
	 */
	public double getTotal() {
		if (sale == null) return 0.0;
		double total = sale.getTotal();
		return total;
	}
	
	public List<SalesLineItem> getList(){
		return sale.getList();
	}
	
	public boolean setQnty(String id,int qnty){
		return sale.setQnty(id,qnty);
	}

	/**
	 * End a sale and record it.
	 */
	public void endSale() {
		//TODO save the sale in SalesLedger
		sale = null;
	}

	public double getTax() {
		// not implemented yet
		return 0.0;
	}

}

package com.pos.posz;

import java.util.List;

public class InventoryCollection {
	private List<Item> items;
	private DBcontroller myDb;
	
	public InventoryCollection(DBcontroller myDb){
		this.myDb = myDb;
	}

	public List<Item> getTotalItems(DBcontroller myDb){
		return myDb.selectAllData();
	}

	public Item getItem(String productId){
		
		Item item = myDb.selectData(productId);
		return item;
	}
	
	/**
	 * Get a product by product id number.
	 * @param id the product id
	 * @return product with matching id or null if id not found
	 */
	public Item findById(String productId) {
		items = getTotalItems(myDb);
		for(Item p: items) if (productId.equals(p.getProductid())) return p;
		return null;
	}
	
	public InventoryCollection getInventCol(){
		return this;
	}
	
}

package com.pos.posz;

import com.pos.sale.ItemDescription;

public class Item {

	int quantity;
	String productId;
	ItemDescription itemDescription;

	public Item(String productId, String name, double cost, double price, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
		itemDescription = new ItemDescription(productId, name, cost, price);
	}

//	public void setName(String name) {
//		itemDescription.setName(name);
//	}
//
//	public void setCost(double cost) {
//		itemDescription.setCost(cost);
//	}
//
//	public void setPrice(double price) {
//		itemDescription.setPrice(price);
//	}

	public void setProductid(String productId) {
		this.productId = productId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

//	public String getName() {
//		return itemDescription.getName();
//	}
//
//	public double getCost() {
//		return itemDescription.getCost();
//	}
//
//	public double getPrice() {
//		return itemDescription.getPrice();
//	}

	public String getProductid() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public ItemDescription getItemDescription(){
		return itemDescription;
	}

}

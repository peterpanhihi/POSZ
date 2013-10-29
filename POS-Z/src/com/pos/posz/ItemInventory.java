package com.pos.posz;

public class ItemInventory {

	int productId, cost, price;
	String name;

	public void setName(String name) {
		this.name = name;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setProductid(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}

	public int getPrice() {
		return price;
	}

	public int getProductid() {
		return productId;
	}

	public String toString() {
		return productId + " " + name + " " + price + " " + cost;
	}
}

package com.pos.sale;

public class ItemDescription {
	private String id;
	private String name;
	private double cost;
	private double price;
	private String description;

	public ItemDescription(String id, String name, double cost, double price) {
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.price = price;
//		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setDescription(String description){
		this.description = description;
	}


	public String getName() {
		return name;
	}

	public double getCost() {
		return cost;
	}

	public double getPrice() {
		return price;
	}
	
	public String getDescription(){
		return description;
	}

}

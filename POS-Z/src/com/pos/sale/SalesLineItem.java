package com.pos.sale;

import com.pos.posz.Item;


public class SalesLineItem {
	// cannot modify product after creating the line item
	private final Item item;
	private int quantity;

	/**
	 * Create a new LineItem with given product id number and quantity 1
	 */
	public SalesLineItem(Item item) {
		this.item = item;
		this.quantity = 1;
	}

	public double getSubtotal()
	{
		ItemDescription itemdes = item.getItemDescription();
		return itemdes.getPrice() * quantity;
	}
	/**
	 * Create a new LineItem with a given product and quantity;
	 * 
	 * @param product
	 *            the product being represented by this lineitem
	 * @param quantity
	 *            the quantity of this product.
	 */
	public SalesLineItem(Item item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}

	/**
	 * Get the item id.
	 * @return product id of the product for this line item
	 */
	public String getProductId() {
		return item.getProductid();
	}

	public Item getProduct() {
		return item;
	}

	/**
	 * Get the quantity of product in this line item.
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Set the quantity of product in this line item.
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Increase (or decrease) the quantity of product.
	 * @param amount
	 *            is amount to add (amount&gt;0) or subtract (amount&lt;0) from
	 *            quantity
	 */
	public void addQuantity(int amount) {
		this.quantity += amount;
	}
}

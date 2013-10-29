package com.pos.posz;


public class Inventory {

	private static Inventory inventory;

	public static Inventory getInstance() {
		if (inventory == null) {
			inventory = new Inventory();
		}

		return inventory;
	}

	public long addItem(Item item, DBcontroller myDb) {

		String productId = item.getProductid() + "";
		String name = item.getItemDescription().getName();
		String price = item.getItemDescription().getPrice() + "";
		String cost = item.getItemDescription().getCost() + "";
		String quantity = item.getQuantity() + "";

		long saveStatus = myDb.insertData(productId, name, price, cost,
				quantity);
		return saveStatus;
	}

	public long removeItem(String productId, DBcontroller myDb) {
		return myDb.deleteData(productId);
	}

	public long updateItem(Item item, DBcontroller myDb) {

		String productId = item.getProductid() + "";
		String name = item.getItemDescription().getName();
		String price = item.getItemDescription().getPrice() + "";
		String cost = item.getItemDescription().getCost() + "";
		String quantity = item.getQuantity() + "";

		return myDb.updateData(productId, name, price, cost, quantity);

	}
}

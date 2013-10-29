package com.pos.posz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pos.sale.Register;
import com.pos.sale.Sale;
import com.pos.sale.Store;

import android.content.Context;

public class InventoryController {

	private Inventory inventory;
	private DBcontroller myDb;
	private InventoryCollection inventcollection;
	private Context context;

	public InventoryController(Context context) {
		this.context = context;
		myDb = new DBcontroller(context);
		inventory = Inventory.getInstance();
		inventcollection = new InventoryCollection(myDb);

	}

	public boolean isExsits(String productId) {
		Item item = myDb.selectData(productId);
		return item != null;
	}

	public boolean add(String productId, String name, String price,
			String cost, String qunatity) {

		double pce = Double.parseDouble(price);
		double cst = Double.parseDouble(cost);
		int quan = Integer.parseInt(qunatity);

		Item item = new Item(productId, name, cst, pce, quan);

		return inventory.addItem(item, myDb) != -1;
	}

	public void delete(String productId) {
		inventory.removeItem(productId, myDb);

	}

	public List<Item> selectAll() {

		return inventcollection.getTotalItems(myDb);

	}

	public Item getItem(String productId) {
		return inventcollection.getItem(productId);
	}

	public boolean update(String productId, String name, String price,
			String cost, String qunatity) {

		double pce = Double.parseDouble(price);
		double cst = Double.parseDouble(cost);
		int quan = Integer.parseInt(qunatity);

		Item item = new Item(productId, name, cst, pce, quan);

		return inventory.updateItem(item, myDb) != -1;

	}

	public List<HashMap<String, String>> getListMapString(List<Item> itemlist) {

		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;

		for (Item item : itemlist) {

			map = new HashMap<String, String>();
			String name = item.getItemDescription().getName();
			String productId = item.getProductid();
			double price = item.getItemDescription().getPrice();
			double cost = item.getItemDescription().getCost();
			int qunatity = item.getQuantity();

			map.put("productID", productId);
			map.put("price", price + "");
			map.put("cost", cost + "");
			map.put("name", name);
			map.put("quantity", qunatity + "");

			list.add(map);

		}

		return list;
	}

}

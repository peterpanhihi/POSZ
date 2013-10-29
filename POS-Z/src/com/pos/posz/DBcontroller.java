package com.pos.posz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBcontroller extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "poszDatabase";

	private static final String TABLE_INVENTORY = "Inventory";

	private final Context context;

	public DBcontroller(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_INVENTORY
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " productId TEXT(14), name TEXT(100),"
				+ " cost DOUBLE , price DOUBLE , quantity INTEGER);");

		Log.d("CREATE TABLE", "Create Table Successfully.");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("USE TABLE IF EXISTS " + TABLE_INVENTORY);
		onCreate(db);

	}

	// INSERT DATA TO DATABASE
	public long insertData(String productId, String name, String price,
			String cost, String quantity) {

		try {
			Item item = selectData(productId);
			SQLiteDatabase db;
			db = this.getWritableDatabase();

			if (item == null) {

				ContentValues val = new ContentValues();
				val.put("productId", productId);
				val.put("name", name);
				val.put("cost", cost);
				val.put("price", price);
				val.put("quantity", quantity);

				long rows = db.insert(TABLE_INVENTORY, null, val);
				Toast.makeText(context, "SuccessFul", Toast.LENGTH_SHORT)
						.show();
				db.close();
				return rows;
			} else {
				db.close();
				return -2;
			}
		} catch (Exception e) {
			return -1;
		}
	}

	public Item selectData(String productId) {
		try {
			// List<String> data = new ArrayList<String>();
			SQLiteDatabase db;
			db = this.getReadableDatabase();
			Item item = null;

			Cursor cursor = db.query(TABLE_INVENTORY, new String[] { "*" },
					"productId=?", new String[] { String.valueOf(productId) },
					null, null, null, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {

					item = new Item(cursor.getString(1), cursor.getString(2),
							Double.parseDouble(cursor.getString(3)),
							Double.parseDouble(cursor.getString(4)),
							Integer.parseInt(cursor.getString(5)));
				}

			}

			cursor.close();
			db.close();
			Toast.makeText(context, "Select SuccessFully ", Toast.LENGTH_SHORT)
					.show();
			return item;

		} catch (Exception e) {
			return null;
		}
	}

	public List<Item> selectAllData() {

		try {
			List<Item> list = new ArrayList<Item>();
			Item item;

			SQLiteDatabase db;
			db = this.getReadableDatabase();

			String Sql = "SELECT * FROM " + TABLE_INVENTORY;
			Cursor cursor = db.rawQuery(Sql, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						String productId = cursor.getString(1);
						String name = cursor.getString(2);
						double cost = Double.parseDouble(cursor.getString(3));
						double price = Double.parseDouble(cursor.getString(4));
						int quantity = Integer.parseInt(cursor.getString(5));

						item = new Item(productId, name, cost, price, quantity);
						list.add(item);
					} while (cursor.moveToNext());
				}
			}
			cursor.close();
			db.close();
			return list;
		} catch (Exception e) {
			return null;
		}

	}

	public long deleteData(String productId) {

		try {
			SQLiteDatabase db;
			db = this.getWritableDatabase();

			long rows = db.delete(TABLE_INVENTORY, "productId = ?",
					new String[] { String.valueOf(productId) });
			db.close();
			Toast.makeText(context, "Delete Successfully!!!",
					Toast.LENGTH_SHORT).show();
			return rows;
		} catch (Exception e) {
			return -1;
		}
	}

	public long updateData(String productId, String name, String price,
			String cost, String quantity) {
		try {

			SQLiteDatabase db;
			db = this.getWritableDatabase();

			ContentValues val = new ContentValues();
			val.put("name", name);
			val.put("cost", cost);
			val.put("price", price);
			val.put("quantity", quantity);

			long rows = db.update(TABLE_INVENTORY, val, " productId = ?",
					new String[] { String.valueOf(productId) });
			db.close();
			return rows;

		} catch (Exception e) {
			return -1;
		}
	}
}

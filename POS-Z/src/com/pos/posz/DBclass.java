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

public class DBclass extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "poszDatabase";

	private static final String TABLE_INVENTORY = "Inventory";

	private final Context context;

	public DBclass(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_INVENTORY
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " productId INTEGER(6), name TEXT(100),"
				+ " cost INTEGER(6) , price INTEGER(6));");

		Log.d("CREATE TABLE", "Create Table Successfully.");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("USE TABLE IF EXISTS " + TABLE_INVENTORY);

	}

	// INSERT DATA TO DATABASE
	public long insertData(int productId, String name, int cost, int price) {

		try {
			String[] data = SelectData(productId);

			if (data == null) {

				SQLiteDatabase db;
				db = this.getWritableDatabase();

				ContentValues Val = new ContentValues();
				Val.put("productId", productId);
				Val.put("name", name);
				Val.put("cost", cost);
				Val.put("price", price);

				long rows = db.insert(TABLE_INVENTORY, null, Val);
				Toast.makeText(context, "SuccessFul", Toast.LENGTH_SHORT)
						.show();
				db.close();
				return rows;
			} else {
				return -2;
			}
		} catch (Exception e) {
			return -1;
		}
	}

	public String[] SelectData(int productId) {
		try {
			// List<String> data = new ArrayList<String>();
			String[] data = null;
			SQLiteDatabase db;
			db = this.getReadableDatabase();

			Cursor cursor = db.query(TABLE_INVENTORY, new String[] { "*" },
					"productId=?", new String[] { String.valueOf(productId) },
					null, null, null, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					data = new String[cursor.getColumnCount()];

					data[0] = cursor.getString(1);
					data[1] = cursor.getString(2);
					data[2] = cursor.getString(3);
					data[3] = cursor.getString(4);

				}

			}

			cursor.close();
			db.close();
			Toast.makeText(context, "Select SuccessFully ", Toast.LENGTH_SHORT)
					.show();
			return data;

		} catch (Exception e) {
			return null;
		}
	}

	public List<HashMap<String, String>> SelectAllData() {
		try {
			List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			SQLiteDatabase db;
			db = this.getReadableDatabase();

			String Sql = "SELECT * FROM " + TABLE_INVENTORY;
			Cursor cursor = db.rawQuery(Sql, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						map = new HashMap<String, String>();
						map.put("ID", cursor.getString(0));
						map.put("productID", cursor.getString(1));
						map.put("name", cursor.getString(2));
						map.put("cost", cursor.getString(3));
						map.put("price", cursor.getString(4));
						list.add(map);
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

	public long DeleteData(int productId) {

		try {
			SQLiteDatabase db;
			db = this.getWritableDatabase();

			long rows = db.delete(TABLE_INVENTORY, "productId = ?",
					new String[] { String.valueOf(productId) });
			db.close();
			Toast.makeText(context, "Delete Successfully!!!", Toast.LENGTH_SHORT).show();
			return rows;
		} catch (Exception e) {
			return -1;
		}
	}
}

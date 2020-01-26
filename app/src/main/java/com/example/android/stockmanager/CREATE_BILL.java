package com.example.android.stockmanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.android.stockmanager.Data.StockDbHelper;
import com.example.android.stockmanager.Data.Stock_contract;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CREATE_BILL extends AppCompatActivity {
    StockDbHelper mDbHelper;
    EditText Customer_name;
    EditText Customer_number;

    EditText Item_ID;
    EditText Balance;
    EditText Item_Weight;
    EditText Date_of_purchase;
    EditText Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__bill);
        mDbHelper = new StockDbHelper(this);
    }




        private int insertCustomer(String CUSTOMERNAME, String CUSTOMERCONTACT ) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        Cursor cursor = db.rawQuery("SELECT "+Stock_contract.CustomerDetails.CUSTOMER_ID+" FROM  " + Stock_contract.CustomerDetails.TABLE_NAME+ " where "
                + Stock_contract.CustomerDetails.CUSTOMER_NAME + " = \"" +CUSTOMERNAME+ "\" AND " + Stock_contract.CustomerDetails.CUSTOMER_CONTACT + "=\"" + CUSTOMERCONTACT+ "\"", null);

        if (cursor.getCount() == 0) {
            contentValues.put(Stock_contract.CustomerDetails.CUSTOMER_NAME, CUSTOMERNAME);
            contentValues.put(Stock_contract.CustomerDetails.CUSTOMER_CONTACT, CUSTOMERCONTACT);
            database.insert(Stock_contract.CustomerDetails.TABLE_NAME, null, contentValues);


        }
        

        Cursor cursor2 = db.rawQuery("SELECT "+Stock_contract.CustomerDetails.CUSTOMER_ID +" FROM  " + Stock_contract.CustomerDetails.TABLE_NAME+ " where "
                + Stock_contract.CustomerDetails.CUSTOMER_NAME + " = \"" + CUSTOMERNAME + "\" AND " + Stock_contract.CustomerDetails.CUSTOMER_CONTACT + "=\"" + CUSTOMERCONTACT+ "\"", null);
       int id = 0;
        while (cursor2.moveToNext())
        {
            id = cursor2.getInt(cursor2.getColumnIndex(Stock_contract.CustomerDetails.CUSTOMER_ID));

        }
cursor2.close();

        return id;
    }

    public void generate_invoice(View view) throws ParseException {
        String itemtype;
        int PurchasePrice = 0;
        float itemWeight;
        int dealerid = 0;


        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        Customer_name = (EditText) findViewById(R.id.customer_name);
        Customer_number = (EditText) findViewById(R.id.customer_contact);
        Item_ID = (EditText) findViewById(R.id.Item_ID);
        Price = (EditText) findViewById(R.id.Item_Price);
        Balance = (EditText) findViewById(R.id.Item_BALANCE) ;


//        Item_Weight = (EditText) findViewById(R.id.Item_Weight);
//        Date_of_purchase = (EditText) findViewById(R.id.Purchase_date);


        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");

        String cname = Customer_name.getText().toString().toLowerCase().trim();
        String cnumber = Customer_number.getText().toString();
        int item_id = Integer.parseInt(Item_ID.getText().toString());
        int sellingprice = Integer.parseInt(Price.getText().toString());
        int balance = Integer.parseInt(Balance.getText().toString());



        ContentValues contentValues = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM  " + Stock_contract.ItemsDetails.TABLE_NAME + " where "
                + Stock_contract.ItemsDetails.ITEM_ID + " = " + item_id , null);
        Log.i("CURSOR COUNT", Integer.toString(cursor.getCount()));

       while (cursor.moveToNext()) {
          itemtype = cursor.getString(cursor.getColumnIndex(Stock_contract.ItemsDetails.ITEM_TYPE));
          PurchasePrice = cursor.getInt(cursor.getColumnIndex(Stock_contract.ItemsDetails.PURCHASE_PRICE));
          itemWeight = cursor.getFloat(cursor.getColumnIndex(Stock_contract.ItemsDetails.WEIGHT));
          dealerid = cursor.getInt(cursor.getColumnIndex(Stock_contract.ItemsDetails.DEALER_ID));

           Log.i("ITEM DETAILS", itemtype + "," + PurchasePrice + ","+itemWeight);
           }

           int profit  = sellingprice - PurchasePrice;
           int cid  = insertCustomer(cname, cnumber);
        Log.i("Customer generate " , cid +"Hello");
           ContentValues contentValues1 = new ContentValues();


       // INSERT EVERYTHING IN THE SALES TABLE
        contentValues1.put(Stock_contract.SalesDetails.ITEM_ID, item_id);
        contentValues1.put(Stock_contract.SalesDetails.CUSTOMER_ID, cid);
        contentValues1.put(Stock_contract.SalesDetails.DEALER_ID, dealerid);
        contentValues1.put(Stock_contract.SalesDetails.PROFIT, profit);
        contentValues1.put(Stock_contract.SalesDetails.BALANCE, balance);
        contentValues1.put(Stock_contract.SalesDetails.SELL_PRICE,sellingprice);

        database.insert(Stock_contract.SalesDetails.TABLE_NAME, null, contentValues1);

        cursor = db.rawQuery("SELECT * FROM " + Stock_contract.SalesDetails.TABLE_NAME, null);

         Log.i("Total sales" , Integer.toString(cursor.getCount()));
         cursor = db.rawQuery("SELECT * FROM  " + Stock_contract.CustomerDetails.TABLE_NAME, null);
        Log.i("count" , Integer.toString(cursor.getCount()));
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            try {

                Log.i("CUSTOMER", cursor.getString(cursor.getColumnIndex(Stock_contract.CustomerDetails.CUSTOMER_NAME)));
               // displayView.append(cursor5.getString(cursor5.getColumnIndex(Stock_contract.CustomerDetails.CUSTOMER_NAME + "\n")));

            } catch (Exception e) {
                Log.i("Exception Found ", "Exception Found");

            }
        }
    }
}

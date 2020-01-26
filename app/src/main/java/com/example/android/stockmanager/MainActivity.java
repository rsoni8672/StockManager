package com.example.android.stockmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.stockmanager.Data.StockDbHelper;
import com.example.android.stockmanager.Data.Stock_contract;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private     StockDbHelper mDbHelper;
    EditText Customer_name;
    EditText Customer_number;
    EditText Item_type;
    EditText Item_Weight;
    EditText Date_of_purchase;
    EditText Price;
    EditText ItemID;

    public  void  find_cust(View view)
    {
      Intent intent  =new Intent( MainActivity.this, Main2Activity.class);
      startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new StockDbHelper(this);

        //insertCustomer();
        //displatDatabaseInfo();

    }
    public void generate_invoice(View view) throws ParseException {

        Intent intent = new Intent(MainActivity.this, CREATE_BILL.class);
        startActivity(intent);
    }



    private int  insertDealer(String DEALERNAME, String DEALERCONTACT ) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        Cursor cursor = db.rawQuery("SELECT "+Stock_contract.DealerDetails.DEALER_ID +" FROM  " + Stock_contract.DealerDetails.TABLE_NAME + " where "
                + Stock_contract.DealerDetails.DEALER_NAME + " = \"" +DEALERNAME+ "\" AND " + Stock_contract.DealerDetails.DEALER_CONTACT + "=\"" + DEALERCONTACT+ "\"", null);

        if (cursor.getCount() == 0) {
            contentValues.put(Stock_contract.DealerDetails.DEALER_NAME, DEALERNAME);
            contentValues.put(Stock_contract.DealerDetails.DEALER_CONTACT, DEALERCONTACT);
            database.insert(Stock_contract.DealerDetails.TABLE_NAME, null, contentValues);


        }

        Cursor cursor2 = db.rawQuery("SELECT "+Stock_contract.DealerDetails.DEALER_ID +" FROM  " + Stock_contract.DealerDetails.TABLE_NAME + " where "
                + Stock_contract.DealerDetails.DEALER_NAME + " = \"" +DEALERNAME+ "\" AND " + Stock_contract.DealerDetails.DEALER_CONTACT + "=\"" + DEALERCONTACT+ "\"", null);

        while (cursor2.moveToNext())
        {
            int id = cursor2.getInt(cursor2.getColumnIndex(Stock_contract.DealerDetails.DEALER_ID));
            return id;
        }

        return 0;
    }



    public void Additem(View view) throws ParseException {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        SQLiteDatabase database = mDbHelper.getWritableDatabase();


        Customer_name = (EditText) findViewById(R.id.customer_name);
        Customer_number = (EditText) findViewById(R.id.customer_contact);
        Item_type = (EditText) findViewById(R.id.Item_category);
        Item_Weight = (EditText) findViewById(R.id.Item_Weight);
        Date_of_purchase = (EditText) findViewById(R.id.Purchase_date);
        Price = (EditText) findViewById(R.id.Item_Price);
        ItemID = (EditText) findViewById(R.id.item_id);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");

        String cname = Customer_name.getText().toString().toLowerCase().trim();
        String cnumber = Customer_number.getText().toString();
        String item_type = Item_type.getText().toString().toLowerCase().trim();
        int item_id  = Integer.parseInt(ItemID.getText().toString());
        double weight = Double.parseDouble(Item_Weight.getText().toString());
        java.util.Date date = df.parse(Date_of_purchase.getText().toString());

//        Date date1 = java.sql.Date.valueOf(Date_of_purchase.getText().toString());

        int price = Integer.parseInt(Price.getText().toString());
        Log.i("Todays date", date.toString());

        int id  =  insertDealer(cname, cnumber);

        Log.i("DEALER ID  =  " , Integer.toString(id));
        ContentValues contentValues = new ContentValues();
        contentValues.put(Stock_contract.ItemsDetails.ITEM_ID, item_id);
        contentValues.put(Stock_contract.ItemsDetails.ITEM_TYPE,item_type);
        //contentValues.put(Stock_contract.ItemsDetails.DATE_OF_PURCHASE,date1);
        contentValues.put(Stock_contract.ItemsDetails.DEALER_ID, id);
        contentValues.put(Stock_contract.ItemsDetails.WEIGHT, weight);
        contentValues.put(Stock_contract.ItemsDetails.PURCHASE_PRICE, price);

        database.insert(Stock_contract.ItemsDetails.TABLE_NAME, null, contentValues);

        //Add all the remaining attributes to  items table
        // INsert into items Table values( " Dealer id ", "Weight", "Type", "PURCHASE PRICE", "Date of purchase,)

    }







    public void displatDatabaseInfo()

    { SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM  " + Stock_contract.CustomerDetails.TABLE_NAME, null);

        try {
            TextView displayView = (TextView) findViewById(R.id.text_trial);
            displayView.setText("Number of rows in pets database table: " + cursor.getCount());
            }
        catch(Exception e)
        {
            Log.i("Exception FOund ", "Exception Found");

        }finally {
            cursor.close();
       }

        }

    }



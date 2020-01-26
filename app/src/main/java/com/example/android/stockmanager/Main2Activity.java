package com.example.android.stockmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.stockmanager.Data.StockDbHelper;
import com.example.android.stockmanager.Data.Stock_contract;


public class Main2Activity extends AppCompatActivity {

    StockDbHelper stockDbHelper;
    EditText editText;
    TextView displayView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        stockDbHelper = new StockDbHelper(this);



    }
public void  find_customer(View view)
{
    SQLiteDatabase db = stockDbHelper.getReadableDatabase();
    SQLiteDatabase database = stockDbHelper.getWritableDatabase();
    editText = (EditText) findViewById(R.id.query_customer);
    String name = editText.getText().toString().toLowerCase().trim();

    int id = 0;
    Cursor cursor = db.rawQuery("SELECT * FROM " + Stock_contract.CustomerDetails.TABLE_NAME + " where  " +
            Stock_contract.CustomerDetails.CUSTOMER_NAME +" = \""  +name+ "\"",null);


    while(cursor.moveToNext()) {
         id = cursor.getInt(cursor.getColumnIndex(Stock_contract.CustomerDetails.CUSTOMER_ID));
         }
    if(id != 0) {
        String final_string, item_details;
        //Cursor item_cursor = db.rawQuery("SELECT * FROM " + Stock_contract.CustomerDetails.TABLE_NAME + " where " + Stock_contract.CustomerDetails.CUSTOMER_ID + " = " + id, null);
        Cursor cursor2 = db.rawQuery("SELECT * FROM " + Stock_contract.SalesDetails.TABLE_NAME +  " where " + Stock_contract.SalesDetails.CUSTOMER_ID + " = " + id, null);
        cursor.moveToFirst();
        Cursor item_cursor;
        while (cursor2.moveToNext()) {


            int item_id = cursor2.getInt(cursor2.getColumnIndex(Stock_contract.SalesDetails.ITEM_ID));
            item_cursor = db.rawQuery("SELECT * FROM " + Stock_contract.ItemsDetails.TABLE_NAME +" where " + Stock_contract.ItemsDetails.ITEM_ID + " = " + item_id , null);


                while(item_cursor.moveToNext())
                {
                Log.i("ITEM DETAILS : ", item_cursor.getString(item_cursor.getColumnIndex(Stock_contract.ItemsDetails.ITEM_TYPE)));
            }


                final_string = "NAME : " + cursor.getString(cursor.getColumnIndex(Stock_contract.CustomerDetails.CUSTOMER_NAME)) + "\n" +
                        "CONTACT : " + cursor.getString(cursor.getColumnIndex(Stock_contract.CustomerDetails.CUSTOMER_CONTACT)) + "\n" +
                        "SELLING PPRICE : " + cursor2.getInt(cursor2.getColumnIndex(Stock_contract.SalesDetails.SELL_PRICE)) + "\n" +
                        "BALANCE : " + cursor2.getInt(cursor2.getColumnIndex(Stock_contract.SalesDetails.BALANCE)) + "\n" +
                        "\n" + "*************************************\n";


                displayView = (TextView) findViewById(R.id.data);
                displayView.append(final_string);




        }

        cursor2.close();
        cursor.close();
    }
    else {
        Toast.makeText(Main2Activity.this, "No Customers found" , Toast.LENGTH_SHORT).show();
    }
}



}

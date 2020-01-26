package com.example.android.stockmanager.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StockDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME  = "stocks_and_sales";
    public static final int DATABASE_VERSION = 1;

 public StockDbHelper(Context context)
 {
     super(context,DATABASE_NAME,null,DATABASE_VERSION);
 }
    @Override
    public void onCreate(SQLiteDatabase db) {





String CREATE_CUSTOMER_TABLE  =


        "create table " + Stock_contract.CustomerDetails.TABLE_NAME + "(" +
        Stock_contract.CustomerDetails.CUSTOMER_ID + " integer primary key AUTOINCREMENT, " +
        Stock_contract.CustomerDetails.CUSTOMER_NAME +" varchar(40) not null , "
        +Stock_contract.CustomerDetails.CUSTOMER_CONTACT + " varchar(10) " +
        ")";



String CREATE_DEALER_TABLE  = "create table " + Stock_contract.DealerDetails.TABLE_NAME +"(" +
        Stock_contract.DealerDetails.DEALER_ID +" integer primary key Autoincrement , " +
        Stock_contract.DealerDetails.DEALER_NAME +" varchar(40) not null , "+
                Stock_contract.DealerDetails.DEALER_CONTACT + " varchar(10) "+
         ")";

String CREATE_ITEMS_TABLE = "create table " + Stock_contract.ItemsDetails.TABLE_NAME +"(" +
        Stock_contract.ItemsDetails.ITEM_ID+" integer primary key ," +
        Stock_contract.ItemsDetails.ITEM_TYPE + " varchar (20) , " +
        Stock_contract.ItemsDetails.WEIGHT + " float(10,3) not null ,  "+
        Stock_contract.ItemsDetails.DATE_OF_PURCHASE + " date  , "+
        Stock_contract.ItemsDetails.DEALER_ID + " int , " +
        Stock_contract.ItemsDetails.PURCHASE_PRICE + " int , "+
        " Foreign key( " + Stock_contract.ItemsDetails.DEALER_ID +
        ") references " + Stock_contract.DealerDetails.TABLE_NAME + "(" + Stock_contract.DealerDetails.DEALER_ID  +
        " ))";




String CREATE_SALES_TABLE  =
        "create table " +
                Stock_contract.SalesDetails.TABLE_NAME + "(" +
                Stock_contract.SalesDetails.CUSTOMER_ID + "  int not null , " +
                Stock_contract.SalesDetails.ITEM_ID + "  int not null , "+
                Stock_contract.SalesDetails.DEALER_ID + "  int not null , "+
                Stock_contract.SalesDetails.SELL_PRICE + "  int not null , "+
                Stock_contract.SalesDetails.SELL_DATE + "  date , " +
                Stock_contract.SalesDetails.SELL_MONTH + " int ,  " +
                Stock_contract.SalesDetails.BALANCE + " int Default 0 , " +
                Stock_contract.SalesDetails.PROFIT + " int Default 0 , " +"Primary key("+
                Stock_contract.SalesDetails.ITEM_ID +"), "+
                "Foreign key(" + Stock_contract.SalesDetails.CUSTOMER_ID +") references " +
                Stock_contract.CustomerDetails.TABLE_NAME+ "(" + Stock_contract.CustomerDetails.CUSTOMER_ID + "),"+
                "Foreign key(" + Stock_contract.SalesDetails.ITEM_ID +") references " +
                Stock_contract.ItemsDetails.TABLE_NAME+ " ( " + Stock_contract.ItemsDetails.ITEM_ID + " ), " +
                "Foreign key(" + Stock_contract.SalesDetails.DEALER_ID + " ) references " +
                Stock_contract.DealerDetails.TABLE_NAME+ "(" + Stock_contract.DealerDetails.DEALER_ID + "))";

  db.execSQL(CREATE_CUSTOMER_TABLE);
  db.execSQL(CREATE_DEALER_TABLE);
  db.execSQL(CREATE_ITEMS_TABLE);
  db.execSQL(CREATE_SALES_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

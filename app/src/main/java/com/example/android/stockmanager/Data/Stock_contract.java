package com.example.android.stockmanager.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class Stock_contract {



    public static class CustomerDetails  implements  BaseColumns
    {   public static final String TABLE_NAME = "CUSTOMER_DETAILS";
        public static final String CUSTOMER_ID  = "CUSTOMER_ID";
        public  static  final  String CUSTOMER_NAME  = "CUSTOMER_NAME";
        public static final String  CUSTOMER_CONTACT = "CUSTOMER_CONTACT";
    }

    public static class DealerDetails implements  BaseColumns
    {public static final String TABLE_NAME = "DEALER_DETAILS";
        public static final String DEALER_ID = "DEALER_ID";
        public static final String  DEALER_NAME= "DEALER_NAME";
        public static final String DEALER_CONTACT = "DEALER_CONTACT";
    }

    public  static  class ItemsDetails implements BaseColumns
    {
        public static final String TABLE_NAME = "ITEM_DETAILS";
        public static final String ITEM_ID = "ITEM_ID";
        public static final String ITEM_TYPE  = "ITEM_TYPE";
        public static final String WEIGHT = "WEIGHT";
        public static final String DATE_OF_PURCHASE = "DATE_OF_PURCHASE";
        public static final String DEALER_ID = "DEALER_ID";
        public static final String PURCHASE_PRICE = "PURCHASE_PRICE";

    }

    public static  class  SalesDetails implements BaseColumns
    {
        public static final String TABLE_NAME = "SALES_DETAILS";
        public static final String CUSTOMER_ID  = "CUSTOMER_ID";
        public static final String DEALER_ID = "DEALER_ID";
        public static final String ITEM_ID = "ITEM_ID";
        public static final String SELL_PRICE  = "SELL_PRICE";
        public static final String SELL_DATE  = "SELL_DATE";
        public static final String SELL_MONTH  = "SELL_MONTH";
        public static final String BALANCE = "BALANCE";
        public static final String PROFIT  = "PROFIT";


    }
}

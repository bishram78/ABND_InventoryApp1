package com.bishram.nano.degree.inventory.app1.data;

import android.provider.BaseColumns;

/**
 * Created by Bishram Munda;
 * Created on Fri, 07/12/2018 03:50H;
 */
public final class InventoryContract {

    private InventoryContract() {
        // Private Constructor (the necessary constructor).
    }

    public static final class InventoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "inventory";

        /* COLUMNS OF THE TABLE */
        // THE ID OF THE TABLE
        public static final String _ID = BaseColumns._ID;

        // Name of the product Mandatory Column Name of table
        public static final String COLUMN_PRODUCT_NAME = "name";

        // Price of the product Mandatory Column Name of table
        public static final String COLUMN_PRICE = "price";

        // Quantity of the product Mandatory Column Name of table
        public static final String COLUMN_QUANTITY = "quantity";

        // Supplier Name Mandatory Column Name of table
        public static final String COLUMN_SUPPLIER = "supplier";

        // Supplier Mobile Number Mandatory Column Name of table
        public static final String COLUMN_SUPPLIER_CONTACT = "ph_number";
    }
}

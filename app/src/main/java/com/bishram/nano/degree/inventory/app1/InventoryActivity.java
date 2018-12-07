package com.bishram.nano.degree.inventory.app1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bishram.nano.degree.inventory.app1.data.InventoryContract.InventoryEntry;
import com.bishram.nano.degree.inventory.app1.data.InventoryDBHelper;

public class InventoryActivity extends AppCompatActivity {

    private InventoryDBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // Initialize and setup toolbar
        Toolbar toolbar = findViewById(R.id.inventory_toolbar);
        toolbar.setTitle(R.string.inventory);
        setSupportActionBar(toolbar);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InventoryActivity.this, InventoryEditor.class));
            }
        });

        mDBHelper = new InventoryDBHelper(this);

        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void insertData() {
        // Get the data repository in write mode
        SQLiteDatabase database = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, "Product Name");
        values.put(InventoryEntry.COLUMN_PRICE, 5000.50);
        values.put(InventoryEntry.COLUMN_QUANTITY, 17);
        values.put(InventoryEntry.COLUMN_SUPPLIER, "Supplier Name");
        values.put(InventoryEntry.COLUMN_SUPPLIER_CONTACT, 773535);

        long id = database.insert(InventoryEntry.TABLE_NAME, null, values);

        if (id != -1) {
            Toast.makeText(getApplicationContext(), getString(R.string.text_successfully_added), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.text_error_occured), Toast.LENGTH_SHORT).show();
        }

        Log.v("InventoryActivity", "Row ID" + id);
    }

    private void displayDatabaseInfo() {

        SQLiteDatabase database = mDBHelper.getReadableDatabase();

        String[] projection = {
          InventoryEntry._ID,
          InventoryEntry.COLUMN_PRODUCT_NAME,
          InventoryEntry.COLUMN_PRICE,
          InventoryEntry.COLUMN_QUANTITY,
          InventoryEntry.COLUMN_SUPPLIER,
          InventoryEntry.COLUMN_SUPPLIER_CONTACT
        };

        Cursor cursor = database.query(
                InventoryEntry.TABLE_NAME,      // The table to be queried.
                projection,                     // Columns that are interested to show.
                null,                   // The columns for the WHERE clause
                null,                // The values for the WHERE clause
                null,                   // Don't group the rows
                null,                    // Don't filter by row groups
                null,                   // The sort order
                null                      //The limit
        );

        TextView displayTexts = findViewById(R.id.display_database_info);

        try {
            displayTexts.setText(String.format("Number of rows = %d\n\n", cursor.getCount()));
            displayTexts.append(
                    InventoryEntry._ID + " - " +
                    InventoryEntry.COLUMN_PRODUCT_NAME + " - " +
                    InventoryEntry.COLUMN_PRICE + " - " +
                    InventoryEntry.COLUMN_QUANTITY + " - " +
                    InventoryEntry.COLUMN_SUPPLIER + " - " +
                    InventoryEntry.COLUMN_SUPPLIER_CONTACT
            );

            int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER);
            int supplierContactColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER_CONTACT);

            while (cursor.moveToNext()) {
                int currentRowId = cursor.getInt(idColumnIndex);
                String currentProductName = cursor.getString(nameColumnIndex);
                double currentProductPrice = cursor.getDouble(priceColumnIndex);
                int currentProductQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                long currentSupplierContact = cursor.getLong(supplierContactColumnIndex);

                displayTexts.append("\n\n" + currentRowId + ". " +
                        currentProductName + ", Rs. " +
                        currentProductPrice + ", " +
                        currentProductQuantity + "Ps, Supplied by " +
                        currentSupplierName + ", Contact: " +
                        currentSupplierContact);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     *
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     *
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     *
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/main_menu.xml file;
        // This adds new items to the app bar;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* User clicked on a new option in the app bar overFlow new. */
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertData();
                displayDatabaseInfo();
                break;

            case R.id.action_quit:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

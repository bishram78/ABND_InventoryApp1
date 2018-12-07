package com.bishram.nano.degree.inventory.app1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.bishram.nano.degree.inventory.app1.data.InventoryContract;
import com.bishram.nano.degree.inventory.app1.data.InventoryDBHelper;

public class InventoryEditor extends AppCompatActivity {

    private EditText mProductNameField;
    private EditText mPriceField;
    private EditText mQuantityField;
    private EditText mSupplierNameField;
    private EditText mSupplierContactField;

    private InventoryDBHelper mDBHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_editor);

        // Initialize and Setup Toolbar
        Toolbar toolbar = findViewById(R.id.editor_toolbar);
        setTitle(R.string.add_to_store);
        setSupportActionBar(toolbar);

        initializeViews();

        mDBHelper = new InventoryDBHelper(this);
    }

    private void initializeViews() {
        mProductNameField = findViewById(R.id.editor_product_name);
        mPriceField = findViewById(R.id.editor_product_price);
        mQuantityField = findViewById(R.id.editor_product_quantity);
        mSupplierNameField = findViewById(R.id.editor_supplier_name);
        mSupplierContactField = findViewById(R.id.editor_supplier_contact);
    }
    private void addData2Store() {
        String mProductNameString = mProductNameField.getText().toString().trim();
        String mProductPriceString = mPriceField.getText().toString().trim();
        String mProductQuantityString = mQuantityField.getText().toString().trim();
        String mSupplierNameString = mSupplierNameField.getText().toString().trim();
        String mSupplierContactString = mSupplierContactField.getText().toString().trim();

        SQLiteDatabase database = mDBHelper.getReadableDatabase();

        ContentValues values = new ContentValues();

        if (mProductNameString.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Name field must be filled.",
                    Toast.LENGTH_SHORT).show();
        } else {
            if (mProductPriceString.isEmpty()) {
                Toast.makeText(getApplicationContext(),
                        "Price field must be filled.",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (mSupplierNameString.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Supplier name field must be filled.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (mSupplierContactString.isEmpty()) {
                        Toast.makeText(getApplicationContext(),
                                "Supplier contact field must be filled.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        if (mSupplierContactString.length() != 10) {
                            Toast.makeText(getApplicationContext(),
                                    "Supplier contact field must be of 10 digit.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            double mProductPriceDouble = Double.parseDouble(mProductPriceString);
                            int mProductQuantityInt;
                            long mSupplierContactLong = Long.parseLong(mSupplierContactString);

                            if (mProductQuantityString.isEmpty()) {
                                mProductQuantityInt = 0;
                            } else {

                                mProductQuantityInt = Integer.parseInt(mProductQuantityString);
                            }

                            values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                                    mProductNameString);
                            values.put(InventoryContract.InventoryEntry.COLUMN_PRICE,
                                    mProductPriceDouble);
                            values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY,
                                    mProductQuantityInt);
                            values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER,
                                    mSupplierNameString);
                            values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_CONTACT,
                                    mSupplierContactLong);

                            long id = database.insert(InventoryContract.InventoryEntry.TABLE_NAME,
                                    null, values);

                            if (id != -1) {
                                Toast.makeText(getApplicationContext(),
                                        "Successfully added.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Error occurred while adding.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            Log.v("InventoryActivity", "Row ID" + id);

                            // Exit the current activity.
                            finish();
                        }
                    }
                }
            }
        }
    }

    private void clearTexts() {
        mProductNameField.setText("");
        mPriceField.setText("");
        mQuantityField.setText("");
        mSupplierNameField.setText("");
        mSupplierContactField.setText("");
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
        getMenuInflater().inflate(R.menu.editor_menu, menu);
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
        switch (item.getItemId()) {
            case R.id.action_save_editor:
                addData2Store();
                break;

            case R.id.action_clear_text_editor:
                clearTexts();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}


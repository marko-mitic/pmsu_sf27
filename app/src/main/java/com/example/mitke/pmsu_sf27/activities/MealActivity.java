package com.example.mitke.pmsu_sf27.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mitke.pmsu_sf27.R;
import com.example.mitke.pmsu_sf27.adapters.DrawerListAdapter;
import com.example.mitke.pmsu_sf27.model.Meal;
import com.example.mitke.pmsu_sf27.model.NavItem;
import com.example.mitke.pmsu_sf27.model.Restourant;
import com.example.mitke.pmsu_sf27.pagers.RestourantPager;
import com.example.mitke.pmsu_sf27.services.OrderService;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.example.mitke.pmsu_sf27.R.id.rest_tab_layout;

public class MealActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout mDrawerPane;
    private CharSequence mTitle;

    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    public Meal mMeal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_meal);
        prepareMenu(mNavItems);
        mMeal = Meal.getById(getPos());
        this.setTitle(mMeal.getName());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_meal);
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerLayout.setDrawerShadow(R.mipmap.drawer_shadow, GravityCompat.START);
        mDrawerList.setOnItemClickListener(new MealActivity.DrawerItemClickListener());
        mDrawerList.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_meal);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setIcon(R.drawable.background_splash);
            actionBar.setHomeButtonEnabled(true);
        }
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
//                getActionBar().setTitle(mTitle);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
//                getActionBar().setTitle(mDrawerTitle);
                getSupportActionBar().setTitle("SF27");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        //populate meals
        TextView meal_name_text = (TextView) findViewById(R.id.name_text);
        ImageView meal_image = (ImageView) findViewById(R.id.meal_image);
        TextView meal_desc_text = (TextView) findViewById(R.id.description_text);

        meal_name_text.setText(mMeal.getName());
        meal_image.setImageResource(mMeal.getImagePath());
        meal_desc_text.setText(mMeal.getDescription());

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
    public long getPos(){
        return getIntent().getLongExtra("position", 0);
    }

    public void order(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        startService(new Intent(getBaseContext(), OrderService.class));
                        Toast.makeText(MealActivity.this, "Your order is being processed", Toast.LENGTH_SHORT).show();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Order "+mMeal.getName()+"?").setPositiveButton("Order", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    private void selectItemFromDrawer(int position) {
        mDrawerList.setItemChecked(position, true);

        if(position == 0){
            //..
            Intent intent = new Intent(MealActivity.this, SettingsActivity.class);
            startActivity(intent);
        }else if(position == 1){
            //..
            this.finishAffinity();
        }
        mDrawerLayout.closeDrawer(mDrawerPane);
    }
    private void prepareMenu(ArrayList<NavItem> mNavItems) {
        this.mNavItems.add(new NavItem(getString(R.string.settings), getString(R.string.settings_long), R.drawable.ic_build_black_24dp ));
        this.mNavItems.add(new NavItem(getString(R.string.exit), getString(R.string.exit_long), R.drawable.ic_settings_power_black_24dp ));

    }
}

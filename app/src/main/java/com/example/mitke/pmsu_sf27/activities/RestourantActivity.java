package com.example.mitke.pmsu_sf27.activities;

import android.app.backup.RestoreObserver;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mitke.pmsu_sf27.R;
import com.example.mitke.pmsu_sf27.adapters.DrawerListAdapter;
import com.example.mitke.pmsu_sf27.model.NavItem;
import com.example.mitke.pmsu_sf27.model.Restourant;
import com.example.mitke.pmsu_sf27.pagers.MainPager;
import com.example.mitke.pmsu_sf27.pagers.RestourantPager;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.example.mitke.pmsu_sf27.R.id.mainTabLayout;
import static com.example.mitke.pmsu_sf27.R.id.rest_tab_layout;

public class RestourantActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout mDrawerPane;
    private CharSequence mTitle;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    public Restourant mRestourant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restourant);
        prepareMenu(mNavItems);
        mRestourant = Restourant.getById(getPos());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.rest_drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerLayout.setDrawerShadow(R.mipmap.drawer_shadow, GravityCompat.START);
        mDrawerList.setOnItemClickListener(new RestourantActivity.DrawerItemClickListener());
        mDrawerList.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.rest_toolbar);
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
                getSupportActionBar().setTitle("sf27");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        //
        //tabs7uy6
        mTabLayout = (TabLayout) findViewById(rest_tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.info));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.meals));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPager = (ViewPager) findViewById(R.id.rest_pager);
        RestourantPager mPager = new RestourantPager(getSupportFragmentManager(), mTabLayout.getTabCount(), getPos());
        mViewPager.setAdapter(mPager);
        mTabLayout.setOnTabSelectedListener(this);

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
    public int getPos(){
        return getIntent().getIntExtra("position", 0);
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    private void prepareMenu(ArrayList<NavItem> mNavItems) {
        this.mNavItems.add(new NavItem(getString(R.string.settings), getString(R.string.settings_long), R.drawable.ic_build_black_24dp ));
        this.mNavItems.add(new NavItem(getString(R.string.exit), getString(R.string.exit_long), R.drawable.ic_settings_power_black_24dp ));


    }

    public void call(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+mRestourant.getPhone()));
        startActivity(callIntent);
    }
    public void sms(View view){
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+mRestourant.getPhone()));
        startActivity(smsIntent);
    }
    public void webpage(View view){
        Intent webIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(mRestourant.getSiteString()));
        startActivity(webIntent);
    }

    public void email(View view) {
        Intent eMailIntent = new Intent();
        eMailIntent.setAction(Intent.ACTION_SEND);
        eMailIntent.setType("message/rfc822");
        eMailIntent.putExtra(android.content.Intent.EXTRA_TEXT, new String[]{mRestourant.getEmail()});
        eMailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        eMailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "" );
        try {
            startActivity(Intent.createChooser(eMailIntent, getResources().getText(R.string.send_to)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(RestourantActivity.this, "There are no email applications installed.", Toast.LENGTH_SHORT).show();
        }

    }

    public void share(View view) {
        //Share text:
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out "+mRestourant.getName()+" on "+mRestourant.getSiteString());
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);
        }
    }
    private void selectItemFromDrawer(int position) {
        mDrawerList.setItemChecked(position, true);

        if(position == 0){
            //..
            Intent intent = new Intent(RestourantActivity.this, SettingsActivity.class);
            startActivity(intent);
        }else if(position == 1){
            //..
            this.finishAffinity();
        }
        mDrawerLayout.closeDrawer(mDrawerPane);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}

package com.wind.frosty;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    BottomNavigationView bottomNavigation;
    List<Fragment> fragmentList;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置toolbar为应用栏
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        pager=findViewById(R.id.pager_view);
        bottomNavigation=findViewById(R.id.navigation_view);
        initFragments();
        initPager();
        initBotNavView();
    }

    public void initToolbar(){
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    /**
     * create menus/创建菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        //menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.settings));
        return true;
    }


    /**
     * menu selected event/菜单选中事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                return true;
            case R.id.menu_about:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("关于我们(About us)")
                        .setMessage("小小开发者")
                        .setNegativeButton("确定", null)
                        .create();
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * init fragments
     */
    private void initFragments(){
        fragmentList= new ArrayList<>(3);
        fragmentList.add(new PickFragment());
        fragmentList.add(new CreationFragment());
        fragmentList.add(new MineFragment());
    }

    /**
     * custom pager adapter to fit view pager with fragment
     */
    private class CusPagerAdapter extends FragmentPagerAdapter{
        public CusPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


    /**
     * set view pager listener/设置viewpager的监听器，监听页面事件
     */
    private void initPager(){
        pager.setAdapter(new CusPagerAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //set bottom navigation to correctly respond to view pager
                bottomNavigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    /**
     * navigation listener,to fit with view pager/设置view pager响应navigation
     */
    void initBotNavView(){
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navi_pick:
                        pager.setCurrentItem(0);
                        return true;
                    case R.id.navi_create:
                        pager.setCurrentItem(1);
                        return true;
                    case R.id.navi_mine:
                        pager.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        });
    }
}

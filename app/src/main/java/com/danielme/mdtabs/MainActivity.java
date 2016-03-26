package com.danielme.mdtabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

  private static final int ALPHA_SELECTED = 255;
  private static final int ALPHA_UNSELECTED = 128;
  private static final int NUM_TABS = 4;

  private TabLayout tabLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
    viewPager.setAdapter(new MainPageAdapter());

    tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setTabMode(TabLayout.MODE_FIXED);
    tabLayout.setupWithViewPager(viewPager);
    tabLayout.getTabAt(0).setIcon(R.mipmap.tab1);
    tabLayout.getTabAt(1).setIcon(R.mipmap.tab2);
    tabLayout.getTabAt(2).setIcon(R.mipmap.tab3);
    tabLayout.getTabAt(3).setIcon(R.mipmap.tab4);

    selectIcon(0);
    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //nothing here
      }

      @Override
      public void onPageSelected(int position) {
        selectIcon(position);
      }

      @Override
      public void onPageScrollStateChanged(int state) {
        //nothing here
      }
    });

  }

  private void selectIcon(int position) {
    for (int i = 0; i < NUM_TABS; i++) {
      if (i == position) {
        tabLayout.getTabAt(i).getIcon().setAlpha(ALPHA_SELECTED);
      } else {
        tabLayout.getTabAt(i).getIcon().setAlpha(ALPHA_UNSELECTED);
      }
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_activity_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_about:
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://danielme" +
            ".com/2016/03/26/diseno-android-tutorial-pestanas-con-material-design")));
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }


  class MainPageAdapter extends PagerAdapter {

    private LinearLayout page1;
    private LinearLayout page2;
    private ListView page3;
    private LinearLayout page4;
    private final int[] titles = {R.string.page1, R.string.page2, R.string.page3, R.string.page4};

    @Override
    public int getCount() {
      return NUM_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return getString(titles[position]);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
      View page;
      switch (position) {
        case 0:
          if (page1 == null) {
            page1 = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout
                .page_one, collection, false);
          }
          page = page1;
          break;
        case 1:
          if (page2 == null) {
            page2 = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout
                .page_two, collection, false);
          }
          page = page2;
          break;
        case 2:
          if (page3 == null) {
            page3 = (ListView) LayoutInflater.from(MainActivity.this).inflate(R.layout
                .page_three, collection, false);
            initListView();
          }
          page = page3;
          break;
        default:
          if (page4 == null) {
            page4 = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout
                .page_four, collection, false);
          }
          page = page4;
          break;
      }

      collection.addView(page, 0);

      return page;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
      collection.removeView((View) view);
    }

    private void initListView() {
      String[] items = new String[50];
      for (int i = 0; i < 50; i++) {
        items[i] = "Item " + i;
      }
      page3.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.textview, items));
      page3.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          Toast.makeText(MainActivity.this, (String) parent.getItemAtPosition(position), Toast
              .LENGTH_SHORT).show();
        }
      });

    }
  }

}

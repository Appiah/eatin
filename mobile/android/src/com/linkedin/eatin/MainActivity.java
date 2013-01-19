package com.linkedin.eatin;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkedin.eatin.model.Constants;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(
					actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		private Context context;

		public SectionsPagerAdapter(FragmentManager fm, Context ctx) {
			super(fm);
			this.context = ctx;
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new MenuSectionFragment(position, context);
			Bundle args = new Bundle();
			args.putInt(Constants.ARG_DAY, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase();
			case 1:
				return getString(R.string.title_section2).toUpperCase();
			case 2:
				return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class MenuSectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		
		public class Category {
			public int catId;
			public String name;
			public String description;
			public int imageId;
			public int mainColor;
			public int secColor;
			
			
			public Category(int catId, String name, String description,
					int imageId, String mainColor, String secColor) {
				super();
				this.catId = catId;
				this.name = name;
				this.description = description;
				this.imageId = imageId;
				this.mainColor = Color.parseColor(mainColor);
				this.secColor = Color.parseColor(secColor);
			}
		}
		
		public class CategoryClickHandler implements OnClickListener {
			private int catId;
			private int day;
			
			public CategoryClickHandler(int day, int catId) {
				super();
				this.catId = catId;
			}

			@Override
			public void onClick(View v) {
				Bundle args = new Bundle();
				args.putInt(Constants.ARG_DAY, day);
				args.putInt(Constants.ARG_CAT_ID, catId);
				//Intent intent = new Intent(context, .class)
			}			
		}
		
		private LinearLayout catListView;
		private List<Category> catInfoList;
		private Context context;
		private int day;

		public MenuSectionFragment() {}
		
		public MenuSectionFragment(int day, Context ctx) {
			catInfoList = new ArrayList<Category>();
			catInfoList.add(new Category(Constants.CAT_CATER, "Daily Catering", "<Caterer Name>", R.drawable.cater_logo, "#cb2525", "#f1a2a2"));
			catInfoList.add(new Category(Constants.CAT_INDIAN, "Indian Cuisine", "<Caterer Name>", R.drawable.indian_logo, "#ada932", "#d6cb81"));
			catInfoList.add(new Category(Constants.CAT_VEGGIE, "Vegetarian", "<Caterer Name>", R.drawable.vege_logo, "#5dad32", "#a1d681"));
			
			//day = getArguments().getInt(Constants.ARG_DAY);
			this.day = day;
			this.context = ctx;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			View view = inflater.inflate(R.layout.main_menu_layout, null);
			catListView = (LinearLayout) view.findViewById(R.id.categoryList);
			
			catListView.addView(buildCategoryView(inflater, 0));
			catListView.addView(buildCategoryView(inflater, 1));
			catListView.addView(buildCategoryView(inflater, 2));
			
			((TextView) view.findViewById(R.id.day)).setText(Constants.DAY_NAMES[day % 5]);
			
			return view;
		}
		
		private View buildCategoryView(LayoutInflater inflater, int cat) {
			Category cate = catInfoList.get(cat);
			View view = inflater.inflate(R.layout.category_list_item, null);
			TextView category = ((TextView) view.findViewById(R.id.catName));
			TextView caterer = ((TextView) view.findViewById(R.id.catererName));
			((ImageView) view.findViewById(R.id.catIcon)).setImageResource(cate.imageId);
			
			category.setText(cate.name);
			category.setTextColor(cate.mainColor);
			caterer.setText(cate.description);
			caterer.setTextColor(cate.secColor);
			
			view.setClickable(true);
			view.setOnClickListener(new CategoryClickHandler(day, cat));
			
			return view;
		}
	}

}

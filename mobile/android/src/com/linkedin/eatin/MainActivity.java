package com.linkedin.eatin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkedin.eatin.model.BaseData;
import com.linkedin.eatin.utility.Constants;
import com.linkedin.eatin.utility.Updateable;

public class MainActivity extends FragmentActivity implements Updateable {
	
	protected class DayClickHandler implements OnClickListener {
		private int position;
		
		public DayClickHandler(int position) {
			this.position = position;
		}
		
		@Override
		public void onClick(View source) {
			mViewPager.setCurrentItem(position);
		}
	}

	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private LinearLayout mIndicator;
	private BaseData model;
	private ProgressDialog pdialog;
	
	private List<View> indicatorList;
	private int curDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		model = BaseData.getModel();

		// Set up the action bar.
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setIcon(R.drawable.icon);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mIndicator = (LinearLayout) findViewById(R.id.indicator);
		
		mViewPager.setAdapter(mSectionsPagerAdapter);
		indicatorList = setupIndicators();

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				changeDay(position);
			}
		});
		
		model.startSyncData(this);
		pdialog = ProgressDialog.show(this, "", "Synchronizing data...", true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void update(String result) {
		if (result.equals(BaseData.KEY_SYNC)) {
			changeDay(0);
			mSectionsPagerAdapter.notifyDataSetChanged();
			pdialog.cancel();
		}
	}
	
	private void changeDay(int day) {
		indicatorList.get(curDay).setBackgroundColor(Color.TRANSPARENT);
		curDay = day;
		indicatorList.get(curDay).setBackgroundColor(Color.parseColor("#83b5e3"));
	}
	
	private List<View> setupIndicators() {
		List<View> ret = new LinkedList<View>();
		
		for (int i = 0; i < Constants.NUM_DAY_SHOWN; i++) {
			RelativeLayout base = new RelativeLayout(this);
			TextView label = new TextView(this);
			label.setText(Constants.DAY_ABBREV[i]);
			
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(45, 45);
			lp.setMargins(10, 0, 10, 0);
			
			base.addView(label);
			base.setGravity(Gravity.CENTER);
			base.setLayoutParams(lp);
			base.setOnClickListener(new DayClickHandler(i));
			ret.add(base);
			mIndicator.addView(base);
		}
		
		return ret;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new MenuSectionFragment();
			Bundle args = new Bundle();
			args.putInt(Constants.ARG_DAY, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return Constants.NUM_DAY_SHOWN;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "";
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
				Intent intent = new Intent(context, MenuActivity.class);
				intent.putExtra(Constants.ARG_DAY, day);
				intent.putExtra(Constants.ARG_CAT_ID, catId);
				startActivity(intent);
			}			
		}
		
		public class CategoryListAdapter extends ArrayAdapter<Category> {
			private List<Category> objects;
			private Context context;

			public CategoryListAdapter(Context context, List<Category> objects) {
				super(context, R.layout.category_list_item, objects);

				this.objects = objects;
				this.context = context;
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.category_list_item, null);

				Category item = objects.get(position);

				TextView category = ((TextView) view.findViewById(R.id.catName));
				TextView caterer = ((TextView) view.findViewById(R.id.catererName));
				((ImageView) view.findViewById(R.id.catIcon)).setImageResource(item.imageId);
				
				category.setText(item.name);
				category.setTextColor(item.mainColor);
				caterer.setText(item.description);
				caterer.setTextColor(item.secColor);
				
				view.setClickable(true);
				view.setOnClickListener(new CategoryClickHandler(day, item.catId));

				return view;
			}
		}

		private int day;
		private ListView catListView;
		private List<Category> catInfoList;
		private Context context;
		private BaseData model;
		private com.linkedin.eatin.model.Menu menu;
		private SimpleDateFormat dateFormat;

		public MenuSectionFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			super.onCreateView(inflater, container, savedInstanceState);
			
			model = BaseData.getModel();
			
			if (model.hasData()) {
				View view = inflater.inflate(R.layout.main_menu_layout, null);
				
				init();
				
				catListView = (ListView) view.findViewById(R.id.categoryList);
				catListView.setAdapter(new CategoryListAdapter(context, catInfoList));
				
				TextView dayTitle = ((TextView) view.findViewById(R.id.day));
				dayTitle.setText(Constants.DAY_NAMES[day % 5]);
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(menu.getDate());
				int d1 = cal.get(Calendar.DAY_OF_MONTH);
				cal.setTime(new Date());
				int d2 = cal.get(Calendar.DAY_OF_MONTH);
				
				if (d1 == d2)
					dayTitle.setTextColor(Color.parseColor("#1a70c0"));
				((TextView) view.findViewById(R.id.date)).setText(dateFormat.format(menu.getDate()));

				return view;
			}
			
			return new LinearLayout(this.getActivity());
		}
		
		private void init() {
			day = getArguments().getInt(Constants.ARG_DAY);
			context = getActivity();
			
			menu = model.getMenuList().get(day);
			
			dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
			
			catInfoList = new ArrayList<Category>();
			catInfoList.add(new Category(Constants.CAT_CATER, "Daily Catering", menu.getCaterer(0).getName(), R.drawable.cater_logo, "#cb2525", "#f1a2a2"));
			catInfoList.add(new Category(Constants.CAT_INDIAN, "Indian Cuisine", menu.getCaterer(1).getName(), R.drawable.indian_logo, "#ada932", "#d6cb81"));
			catInfoList.add(new Category(Constants.CAT_VEGGIE, "Vegetarian", menu.getCaterer(2).getName(), R.drawable.vege_logo, "#5dad32", "#a1d681"));
		}
	}
}

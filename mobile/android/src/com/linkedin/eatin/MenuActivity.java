package com.linkedin.eatin;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.linkedin.eatin.model.Caterer;
import com.linkedin.eatin.model.Constants;
import com.linkedin.eatin.model.FoodItem;
import com.linkedin.eatin.model.Menu;
import com.linkedin.eatin.model.Model;

public class MenuActivity extends Activity {

	public class MenuListAdapter extends ArrayAdapter<FoodItem> {
		private List<FoodItem> objects;
		private Context context;

		public MenuListAdapter(Context context, List<FoodItem> objects) {
			super(context, R.layout.food_list_item, objects);

			this.objects = objects;
			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.food_list_item, null);

			FoodItem item = objects.get(position);

			((TextView) view.findViewById(R.id.foodName)).setText(item.getName());
			((TextView) view.findViewById(R.id.numRatings)).setText(item.getNumRating().toString());
			((View) view.findViewById(R.id.ratingIndicator)).setBackgroundColor(Color.parseColor(item.getRatingColor()));

			return view;
		}
	}

	private ListView foodList;
	private Menu menu;

	private int day;
	private int catId;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		Bundle bundle = getIntent().getExtras();

		catId = bundle.getInt(Constants.ARG_CAT_ID);
		day = bundle.getInt(Constants.ARG_DAY);

		menu = Model.getModel().getMenuList().get(day);
		Caterer caterer = menu.getCaterer(catId);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle(Constants.CAT_NAMES[catId]);
		
		switch (catId) {
		case Constants.CAT_CATER:
			ab.setLogo(R.drawable.cater_icon);
			break;
		case Constants.CAT_INDIAN:
			ab.setLogo(R.drawable.indian_icon);
			break;
		case Constants.CAT_VEGGIE:
			ab.setLogo(R.drawable.vege_icon);
			break;
		}

		((TextView) findViewById(R.id.catererName)).setText(caterer.getName());
		((TextView) findViewById(R.id.numAvgVotes)).setText(caterer.getNumRatings().toString());
		View plusRating = (View) findViewById(R.id.posRatingsBar);

		plusRating.getLayoutParams().width = (int)(getResources().getDimension(R.dimen.s160dp) * caterer.getAvgRating());


		foodList = (ListView) findViewById(R.id.foodList);
		foodList.setAdapter(new MenuListAdapter(this, menu.getFoodItems()[catId]));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

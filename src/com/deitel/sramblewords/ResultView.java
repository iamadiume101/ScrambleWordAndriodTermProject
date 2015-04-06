package com.deitel.sramblewords;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ResultView extends Activity  {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		ListView result = (ListView) findViewById(R.id.resultListview);
		result.setAdapter(new ResultAdapter(MainActivity.levelScore, ResultView.this));
	}

	class ResultAdapter extends BaseAdapter {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		ArrayList<Integer> levels = new ArrayList<Integer>();
		Context context;

		ResultAdapter(Map<Integer, Integer> map,Context context) {
			this.map = map;
			this.context = context;
			for (Integer level : map.keySet()) {
				levels.add(level);
			}
		}

		@Override
		public int getCount() {

			return levels.size();
		}

		@Override
		public Object getItem(int position) {

			return levels.get(position);
		}

		@Override
		public long getItemId(int position) {

			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.resultrow, null);
			((TextView)row.findViewById(R.id.levelTV)).setText("Level : "+(levels.get(position)-2));
			((TextView)row.findViewById(R.id.ScoreTV)).setText("Score :"+map.get(levels.get(position)));
			return row;
		}

	}
}

	
	



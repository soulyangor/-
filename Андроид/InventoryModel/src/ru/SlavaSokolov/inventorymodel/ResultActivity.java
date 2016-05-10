package ru.SlavaSokolov.inventorymodel;

import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultActivity extends Activity {

	TextView txt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		int n=getIntent().getExtras().getInt("Num");
		double f=getIntent().getExtras().getDouble("Free");
		for(int i=0;i<n;i++){
			LayoutInflater ltInflater = getLayoutInflater();        
		    View view = ltInflater.inflate(R.layout.result, null, false);        
		    LayoutParams lp = view.getLayoutParams();
		    LinearLayout linLayout = (LinearLayout) findViewById(R.id.LinearLayout);  
		    linLayout.addView(view);
		    double y=getIntent().getExtras().getDouble("Y"+i);
		    txt=(TextView)view.findViewById(R.id.txtResult);
		    int k=i+1;
		    txt.setText("Number of product types "+k+": "+y);
		}
		LayoutInflater ltInflater = getLayoutInflater();        
	    View view = ltInflater.inflate(R.layout.result, null, false);        
	    LayoutParams lp = view.getLayoutParams();
	    LinearLayout linLayout = (LinearLayout) findViewById(R.id.LinearLayout);  
	    linLayout.addView(view);
	    txt=(TextView)view.findViewById(R.id.txtResult);
	    txt.setText("Free area: "+f);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

}

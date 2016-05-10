package ru.SlavaSokolov.inventorymodel;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{

	Button btnOk;
	EditText etNumber, etStorage, etEps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnOk=(Button)findViewById(R.id.btnOk);
		btnOk.setOnClickListener(this);
		etNumber=(EditText)findViewById(R.id.etNumber);
		etStorage=(EditText)findViewById(R.id.etStorage);
		etEps=(EditText)findViewById(R.id.etEps);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			Intent intent=new Intent(MainActivity.this,ModelActivity.class);
			String str=etNumber.getText().toString();
			int Num=Integer.parseInt(str);
			str=etStorage.getText().toString();
		
			double A=Double.parseDouble(str);
			str=etEps.getText().toString();
			double e=Double.parseDouble(str);
			
			intent.putExtra("Number", Num);
			intent.putExtra("Storage", A);
			intent.putExtra("Eps", e);
			startActivity(intent);
		}catch(Exception e){
		}
	}

}

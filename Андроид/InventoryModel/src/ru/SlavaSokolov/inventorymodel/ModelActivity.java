package ru.SlavaSokolov.inventorymodel;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ModelActivity extends Activity implements OnClickListener{
	
	int num;
	double S, e;
	TextView txt;
	EditText[] etA, etB, etK, etH;
	Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_model);
		num=getIntent().getExtras().getInt("Number");
		S=getIntent().getExtras().getDouble("Storage");
		e=getIntent().getExtras().getDouble("Eps");
		txt=(TextView)findViewById(R.id.txt);
		txt.setText("Storage size "+S);
		etA=new EditText[num];
		etB=new EditText[num];
		etK=new EditText[num];
		etH=new EditText[num];
		for(int i=0;i<num;i++){
			LayoutInflater ltInflater = getLayoutInflater();        
		    View view = ltInflater.inflate(R.layout.group, null, false);        
		    LayoutParams lp = view.getLayoutParams();
		    LinearLayout linLayout = (LinearLayout) findViewById(R.id.LinearLayout);        
		    linLayout.addView(view);
		    txt=(TextView)view.findViewById(R.id.txtType);
		    int k=i+1;
		    txt.setText("Product characteristics "+k);
		    etB[i]=(EditText)view.findViewById(R.id.edit1);
		    etK[i]=(EditText)view.findViewById(R.id.edit2);
		    etH[i]=(EditText)view.findViewById(R.id.edit3);
		    etA[i]=(EditText)view.findViewById(R.id.edit4);
		}
		btn=(Button)findViewById(R.id.btn);
		btn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.model, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			double[] A, B, H, K;
			A=new double[num];
			B=new double[num];
			K=new double[num];
			H=new double[num];
			for(int i=0;i<num;i++){
				String str=etA[i].getText().toString();
				A[i]=Double.parseDouble(str);
				str=etB[i].getText().toString();
				B[i]=Double.parseDouble(str);
				str=etK[i].getText().toString();
				K[i]=Double.parseDouble(str);
				str=etH[i].getText().toString();
				H[i]=Double.parseDouble(str);
			}
			double l=0;
			double d=0;
			double tmpD;
			tmpD=S;
			double h=0.1;
		
			double[]y=new double[num];
			while(Math.abs(S-d)>e){
				double d1;
				double d2;
				do{
					d1=0;
					d2=0;
					double tmp=l;
					for(int i=0; i<num; i++) y[i]=Math.sqrt((K[i]*B[i])/(H[i]/2-tmp*A[i]));
					for(int i=0; i<num; i++) d1+=A[i]*y[i];
					if (d1-S>0) tmp-=h; else tmp+=h;
					for(int i=0; i<num; i++) y[i]=Math.sqrt((K[i]*B[i])/(H[i]/2-tmp*A[i]));
					for(int i=0; i<num; i++) d2+=A[i]*y[i];
					if((d1-S)*(d2-S)>0) d=d2; else h/=2;
				} while((d1-S)*(d2-S)<0);
				if (d-S>0) l-=h; else l+=h;
			}
			double sm=0;
			for(int i=0;i<num;i++) sm+=A[i]*Math.floor(y[i]);
			Intent intent=new Intent(ModelActivity.this,ResultActivity.class);
			intent.putExtra("Num", num);
			intent.putExtra("Free", S-sm);
			for(int i=0;i<num;i++) intent.putExtra("Y"+i, Math.floor(y[i]));
			startActivity(intent);
		}catch(Exception e){
		}
	}
}

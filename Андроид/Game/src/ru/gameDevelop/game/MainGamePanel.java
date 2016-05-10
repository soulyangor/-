package ru.gameDevelop.game;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.gameDevelop.game.Object.Node;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements
SurfaceHolder.Callback {


	float[] x = new float[10];    
	float[] y = new float[10];    
	boolean[] touched = new boolean[10];
	int aK[]=new int[10];
    int mK[]=new int[10];
    int cA=0;
    int cM=0;
    double controlX;
    double controlY;
    float str=0;
    
    int count=30;
    Random rand=new Random();
	
	private MainThread thread;
	
	public Object a,b,c;
	public control gamer;
	
	public List<environment> env;
	
	public Bitmap btn1,
				  btn2,
				  btn3,
				  btn4,
				  btn5,
				  btn6,
				  btnEnv;
	
	public Paint paint,
				 paintF;
	
	public double xDispl, yDispl;
	public int tick;	
	
	public MainGamePanel(Context context) {
						
		super(context);
		getHolder().addCallback(this);
		
				
		// создаем поток для игрового цикла
		thread = new MainThread(getHolder(), this);
		
		this.btn1=BitmapFactory.decodeResource(getResources(), R.drawable.back_ground);
		this.btn2=BitmapFactory.decodeResource(getResources(), R.drawable.enemy_sprite);
		this.btn3=BitmapFactory.decodeResource(getResources(), R.drawable.rct);
		this.btn4=BitmapFactory.decodeResource(getResources(), R.drawable.gamer_sprite);
		this.btn5=BitmapFactory.decodeResource(getResources(), R.drawable.exit);
		this.btn6=BitmapFactory.decodeResource(getResources(), R.drawable.control);
		
		this.btnEnv=BitmapFactory.decodeResource(getResources(), R.drawable.env_sprite);
		
		this.paint=new Paint();
		this.paint.setColor(Color.RED);
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setAntiAlias(true);
		this.paint.setTextSize(40);
		
		this.paintF=new Paint();
		this.paintF.setColor(Color.RED);
		this.paintF.setStyle(Paint.Style.FILL);
		this.paintF.setAntiAlias(true);
		this.paintF.setTextSize(60);
		
		setFocusable(true);
		
		this.a=new Object(); 
		this.b=new Object(); 
		this.c=new Object();
		this.gamer=new control();
		
		this.a.eH=getHeight();
		this.a.eW=getWidth();
		this.b.eH=getHeight();
		this.b.eW=getWidth();
		this.c.eH=getHeight();
		this.c.eW=getWidth();
				
		//this.c.Add(this.c.get(21,-500,-500,0,10,3,0,0,0.102));
		
		for(int i=0; i<count; i++){
			int x=rand.nextInt(1024)-512;
			int y=rand.nextInt(1024)-512;
			
			this.a.get(i+1,x,y,0,200,5+rand.nextInt(25),0,0,0.1*rand.nextInt(6)).index=i;
			this.a.Add(this.a.get(i+1,x,y,0,200,5+rand.nextInt(25),0,0,0.1*rand.nextInt(6) ) );
			
			/*this.a.Add(this.a.get(20,200,50,0,200,20,0,0,1));
			this.a.Add(this.a.get(20,200,350,0,200,20,0,0,1));
			this.a.Add(this.a.get(20,300,200,0,200,20,0,0,1));
			this.a.Add(this.a.get(20,100,200,0,200,20,0,0,1));*/
		}
		this.gamer.x=200;
		this.gamer.y=200;
		this.gamer.z=0;
		this.gamer.xC=0;
		this.gamer.yC=0;
					
		this.xDispl=0;
		this.yDispl=0;
		this.env=new ArrayList<environment>();
		
	/*	for (int i=0;i<10;i++){
			environment elem=new environment();
			elem.bitmap=this.btnEnv;
			elem.x=new Random().nextInt(1024)-512;
			elem.y=new Random().nextInt(1024)-512;
			this.env.add(elem);
		}*/
		this.tick=0;
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//посылаем потоку команду на закрытие и дожидаемся, 
		//пока поток не будет закрыт.
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// пытаемся снова остановить поток thread
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		double mn=0;
 		if(getHeight()>getWidth()) mn=getWidth()/6; else mn=getHeight()/6;
		str=(float) mn;
		 int action = event.getAction() & MotionEvent.ACTION_MASK;        
		 int pointerIndex = (event.getAction() &                            
							 MotionEvent.ACTION_POINTER_ID_MASK) >>
	     					 MotionEvent.ACTION_POINTER_ID_SHIFT;        
	     int pointerId = event.getPointerId(pointerIndex);
	     switch (action) {
	     case MotionEvent.ACTION_DOWN:        
	     case MotionEvent.ACTION_POINTER_DOWN:            
	    	 touched[pointerId] = true;            
	    	 x[pointerId] = (int)event.getX(pointerIndex);
	    	 y[pointerId] = (int)event.getY(pointerIndex);            
	    	 break;
	     case MotionEvent.ACTION_UP:        
	     case MotionEvent.ACTION_POINTER_UP:        
	     case MotionEvent.ACTION_CANCEL:            
	    	 touched[pointerId] = false;            
	    	 x[pointerId] = (int)event.getX(pointerIndex);            
	    	 y[pointerId] = (int)event.getY(pointerIndex);            
	    	 break;
	     case MotionEvent.ACTION_MOVE:            
	    	 int pointerCount = event.getPointerCount();            
	    	 for (int i = 0; i < pointerCount; i++) {                
	    		 pointerIndex = i;                
	    		 pointerId = event.getPointerId(pointerIndex);                
	    		 x[pointerId] = (int)event.getX(pointerIndex);                
	    		 y[pointerId] = (int)event.getY(pointerIndex);            
	    		 }            
	    	 break;        
	     }
	     
	     for(int i=0;i<cA;i++)if(!touched[aK[cA]])this.gamer.onAtack=false;else {
	    	double r;
		 	double xc=mn;
		 	double yc=getHeight()-mn;
		 	int ki=aK[cA];
		 	
		 	xc=getWidth()/2;
 			yc=getHeight()/2;
		 	
		 //	r=Math.sqrt(Math.pow(x[ki]-xc,2)+Math.pow(y[ki]-yc,2));
		 	
		/* 	if(r<mn)*/ this.gamer.onAtack=false;
	     }
		/*   for(int i=0;i<cM;i++)if(!touched[mK[cM]]){
		    	this.gamer.onMove=false;
		    	controlX=mn;
		    	controlY=getHeight()-mn;
		    }/*else {
		    	double r;
			 	double xc=mn;
			 	double yc=getHeight()-mn;
			 	int ki=mK[cM];
			 	
			 	xc=this.gamer.x;
	 			yc=this.gamer.y;
			 	
			 	r=Math.sqrt(Math.pow(x[ki]-xc,2)+Math.pow(y[ki]-yc,2));
			 	
			 	if(r>mn){
			 		this.gamer.onMove=false;
			 		controlX=mn;
			    	controlY=getHeight()-mn;
			 	}
		     }
*/	   	        
	    cM=0;
	    cA=0;
	     for(int i=0;i<10;i++)
	    	if(touched[i]){
	    	double r;
	 		double xc=mn;
	 		double yc=getHeight()-mn;
	 		if ((x[i]>getWidth()-20)&&(y[i]<20)) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			  	}
	 		/*if ((x[i]<100)&&(y[i]<20)) {
				thread.setRunning(true);
				}*/
	 	/*	xc=getWidth()/2;
 			yc=getHeight()/2;
	 		
	 		r=Math.sqrt(Math.pow(x[i]-xc,2)+Math.pow(y[i]-yc,2));*/
	 	//	if(r<mn){
				
	 				 		
	 		/*	double a;                                                                        //

	 			if((x[i]-xc)==0){
	 				if((y[i]-yc)>0) a=Math.PI/2; else a=-Math.PI/2;
	 				if((y[i]-yc)==0)a=0;
	 			}else a=Math.atan((y[i]-yc)/(x[i]-xc));  //
	 			if((x[i]-xc)<0) a+=Math.PI;
	 			this.gamer.alpha=a;//+Math.PI;
	 			this.gamer.beta=0;
	 			this.gamer.onMove=true;
	 			mK[cM]=i;
	 			cM++;
	 			controlX=x[i];
	 			controlY=y[i];*/
	 	//	}
	 	//	else {
	 			this.gamer.celx=x[i];
	 			this.gamer.cely=y[i];
	 			this.gamer.onAtack=true;
	 			aK[cA]=i;
	 			cA++;
	 		//}
	     }
	    
		return true;//super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		//=BitmapFactory.decodeResource(getResources(), R.drawable.bck_ground);
		
	        
	  /* Rect src = new Rect( -btn1.getWidth() / 2- (int)this.xDispl, -btn1.getHeight() / 2- (int)this.yDispl ,
			   -btn1.getWidth() / 2- (int)this.xDispl-getWidth(), -btn1.getHeight() / 2- (int)this.yDispl-getHeight());
	    		
	    
	     Rect dst = new Rect( 0, 0,getWidth(),getHeight());
		
	     canvas.drawBitmap(btn1, src, dst, null);*/
		canvas.drawBitmap(btn1, -btn1.getWidth() / 2- (int)this.xDispl, -btn1.getHeight() / 2- (int)this.yDispl , null);	
		
		//canvas.drawColor(Color.WHITE);
		
		for(int i=0;i<this.env.size();i++){
			this.env.get(i).draw(canvas,this.xDispl,this.yDispl);
		}
		//btn=BitmapFactory.decodeResource(getResources(), R.drawable.droid_1);
		a.DrawC(canvas,this.xDispl,this.yDispl,true);
		
	//	btn=BitmapFactory.decodeResource(getResources(), R.drawable.rct);
		b.DrawC(canvas,this.xDispl,this.yDispl,false);
		
		//btn=BitmapFactory.decodeResource(getResources(), R.drawable.droid_1);
		//c.draw(canvas,this.btn2,this.xDispl,this.yDispl);
		
		//btn=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		gamer.DrawC(canvas,this.xDispl,this.yDispl);
		
		//btn=BitmapFactory.decodeResource(getResources(), R.drawable.exit);
		canvas.drawBitmap(this.btn5,getWidth() -btn5.getWidth(),0 , null);
		
		//btn=BitmapFactory.decodeResource(getResources(), R.drawable.control);
		double mn=0;
 		if(getHeight()>getWidth()) mn=getWidth()/6; else mn=getHeight()/6;
		str=(float) mn;
		if(controlY==0){
			controlX=mn;
			controlY=getHeight()-mn;
		}
		canvas.drawBitmap(this.btn6, (float)controlX-btn6.getWidth()/2, (float)controlY-btn6.getHeight()/2, null);
		canvas.drawCircle(str, getHeight()-str, 10, paint);		
		
		 		
		canvas.drawText("Health "+this.gamer.hp+"%", getWidth()-320, getHeight()-60, paint);
		canvas.drawText("Speed "+this.gamer.h+" ", getWidth()-320, getHeight()-30, paint);
	}
		
	int k=0;
	protected void update(){
		
	/*	k++;
		if(k==1000) {
			count=5+rand.nextInt(10);
			k=0;
		}*/
		this.getDispl();
		this.gamer.Control(this.b);
		this.gamer.move();
		//this.a.action(this.b,this.gamer);
		this.b.move();
		//this.a.AImove(this.gamer,this.b,this.c);
		this.b.usl(this.gamer,this.a);
		this.b.Destroy();
		this.a.calcMove(this.gamer, this.b);
		
		this.a.osmos(this.gamer,this.b);
		
		this.a.move();
		this.a.Destroy();
		//this.gamer.hp+=10.0*this.a.Destroy();
		
	/*	if (count>a.head.size()){
			int x=rand.nextInt(1024)-512;
			int y=rand.nextInt(1024)-512;
			this.a.Add(this.a.get(1+rand.nextInt(10),x,y,0,200,5+rand.nextInt(45),0,0,0.1*rand.nextInt(6) ) );
		}*/
		if(this.tick!=10)this.tick++;else{this.tick=0;
		for(int i=0;i<this.env.size();i++){
			this.env.get(i).updateFrame();
		}
		this.a.frameCalc();
		}
	}
	
	protected void getDispl(){
		this.xDispl=this.gamer.x-getWidth()/2;
		this.yDispl=this.gamer.y-getHeight()/2;
		this.gamer.xC=this.xDispl;
		this.gamer.yC=this.yDispl;
	}
	
	protected void finalDraw(Canvas canvas)
	{
		canvas.drawText("GAME OVER", getWidth()/2-150, getHeight()/2-50, paintF);
	}
}
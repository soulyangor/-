package ru.gameDevelop.game;

import ru.gameDevelop.game.Object.Node;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class control {
	double x,y,z; // координаты
    double celx,cely,celz;//координаты цели
	double size;//размер
    double h;  //скорость
    int time; //
    int delayAtack=5; //задержка между атаками
    boolean onAtack;  //
    double hp;    //здоровье
    double alpha;
    double beta;
    boolean onMove;
    
    int rightFrame;
    int leftFrame;
    int topFrame;
    int downFrame;
    int tick;
    
    double xC,yC;
    control(){
    	this.time=0; 
    	this.delayAtack=10; 
    	this.onAtack=false; 
    	this.alpha=0;
    	this.beta=0;
    	this.onMove=false;
    	this.h=0;
    	this.hp=100;
    	this.celz=0;
    	this.size=20;
    	
    	
    	this.rightFrame=0;
    	this.leftFrame=0;
    	this.topFrame=0;
    	this.downFrame=0;
    	
    	this.tick=0;
    }
    
    public void draw(Canvas canvas,Bitmap bitmap,double x,double y) {
    	int srcX = 0;
        int srcY = 0;
    	
    	if((this.alpha>-Math.PI/4)&&(this.alpha<Math.PI/4))
    	{srcX = this.rightFrame * bitmap.getWidth() / 3;
         srcY = 2 * bitmap.getHeight() / 4;}
		
		if((this.alpha>Math.PI/4)&&(this.alpha<3*Math.PI/4))
		{srcX = this.topFrame * bitmap.getWidth() / 3;
        srcY = 0 * bitmap.getHeight() / 4;}
		
		if((this.alpha>3*Math.PI/4)&&(this.alpha<5*Math.PI/4))
		{srcX = this.leftFrame * bitmap.getWidth() / 3;
        srcY = 1 * bitmap.getHeight() / 4;}
		
		if((this.alpha>5*Math.PI/4)||(this.alpha<-Math.PI/4))
		{srcX = this.downFrame * bitmap.getWidth() / 3;
		srcY = 3 * bitmap.getHeight() / 4;}
		
    	    	
        Rect src = new Rect(srcX, srcY, srcX + bitmap.getWidth() / 3, srcY + bitmap.getHeight() / 4);
        
    	Rect dst = new Rect((int)this.x - (bitmap.getWidth() / 6)-(int)x, (int)this.y - (bitmap.getHeight() / 8)-(int)y,
        		(int)this.x + (bitmap.getWidth() / 6)-(int)x, (int)this.y + (bitmap.getHeight() / 8)-(int)y);
    	
    	canvas.drawBitmap(bitmap, src, dst, null);
    	//canvas.drawBitmap(bitmap, (int)this.x - (bitmap.getWidth() / 2)-(int)x, (int)this.y - (bitmap.getHeight() / 2)-(int)y, null);
    }
    
    public void DrawC(Canvas canvas, double x, double y) {
		
    	Paint paint;
    	paint=new Paint();
    	paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		paint.setTextSize(40);
		canvas.drawCircle((float)(this.x-x),(float)(this.y-y),(float)this.size, paint);
		
	}
           
    public void move(){
    	//if(this.onMove){
    		if(this.tick==10) this.tick=0;else this.tick++;
    		
    		if((this.alpha>-Math.PI/4)&&(this.alpha<Math.PI/4))
    			if(this.rightFrame==3)this.rightFrame=0;else if(this.tick==0) this.rightFrame++;
    		
    		if((this.alpha>Math.PI/4)&&(this.alpha<3*Math.PI/4))
    			if(this.topFrame==3)this.topFrame=0;else if(this.tick==0) this.topFrame++;
    		
    		if((this.alpha>3*Math.PI/4)&&(this.alpha<5*Math.PI/4))
    			if(this.leftFrame==3)this.leftFrame=0;else if(this.tick==0) this.leftFrame++;
    		
    		if((this.alpha>5*Math.PI/4)||(this.alpha<-Math.PI/4))
    			if(this.downFrame==3)this.downFrame=0;else if(this.tick==0) this.downFrame++;
    		
    		
    		
    		this.x+=this.h*Math.cos(this.alpha);
    		this.y+=this.h*Math.sin(this.alpha);
    		if(this.x<-512){
    			this.x=-512;
    			double a=this.alpha+Math.PI;
    			this.alpha=-a;
    		}
    		if(this.x>512){
    			this.x=512;
    			double a=this.alpha+Math.PI;
    			this.alpha=-a;
    		}
    		if(this.y<-512){
    			this.y=-512;
    			double a=this.alpha;
    			this.alpha=-a;
    		}
    		if(this.y>512){
    			this.y=512;
    			double a=this.alpha;
    			this.alpha=-a;
    		}
   //  	}
    }
    
    void atackControl(Object sn)
    {
      double rez=Math.sqrt(Math.pow(this.celx-this.x+this.xC,2)+Math.pow(this.cely-this.y+this.yC,2)+Math.pow(this.celz-this.z,2));
       if ((this.onAtack)&&(this.time==0)){
                                     double a;                        //

                                     if((this.celx-this.x+this.xC)==0)
                                     {
                                     if((this.cely-this.y+this.yC)>0) a=Math.PI/2;else a=-Math.PI/2;
                                     if((this.cely-this.y+this.yC)==0) a=0;
                                     }else a=Math.atan((this.cely-this.y+this.yC)/(this.celx-this.x+this.xC));
                                     if((this.celx-this.x+this.xC)<0) a+=Math.PI;
                                     double q=Math.asin((this.celz-this.z)/rez);

                                     double x0=this.x+(this.size+2)*Math.cos(a)*Math.cos(q);
                                     double y0=this.y+(this.size+2)*Math.sin(a)*Math.cos(q);
                                     double z0=this.z+(this.size+2)*Math.sin(q);

                                     sn.Add(sn.get(0,x0,
                                                      y0,
                                                      z0,
                                                     0,2,
                                                     a,q,3.0));
                                     this.time++;
                                     }else if ((!this.onAtack)&&(this.time==0)) this.time=0; else 
                                    	 if (this.time==this.delayAtack)  this.time=0; else this.time++;
       			   }
    
    void Control(Object sn)
    {
      double rez=Math.sqrt(Math.pow(this.celx-this.x+this.xC,2)+Math.pow(this.cely-this.y+this.yC,2)+Math.pow(this.celz-this.z,2));
       if ((this.onAtack)&&(this.time==0)){
                                     double a;                        //

                                     if((this.celx-this.x+this.xC)==0)
                                     {
                                     if((this.cely-this.y+this.yC)>0) a=Math.PI/2;else a=-Math.PI/2;
                                     if((this.cely-this.y+this.yC)==0) a=0;
                                     }else a=Math.atan((this.cely-this.y+this.yC)/(this.celx-this.x+this.xC));
                                     if((this.celx-this.x+this.xC)<0) a+=Math.PI;
                                     double q=Math.asin((this.celz-this.z)/rez);

                                     double x0=this.x+(this.size+2)*Math.cos(a)*Math.cos(q);
                                     double y0=this.y+(this.size+2)*Math.sin(a)*Math.cos(q);
                                     double z0=this.z+(this.size+2)*Math.sin(q);

                                     double al=0;
                                     double m=this.size/40;
                                     double u=3.0;
                                
                                     double chisl, znam;
                                     
                                     chisl=(this.size*this.h*Math.sin(this.alpha)-m*u*Math.sin(a));
                                     znam=(this.size*this.h*Math.cos(this.alpha)-m*u*Math.cos(a));
                                     
                                     if(Math.abs(znam)<0.001){
                                    	 if(chisl>0) al=Math.PI/2; else al=-Math.PI/2;
                                    	 if(Math.abs(chisl)<0.001) al=0;
                                     }else al=Math.atan(chisl/znam);
                                     
                                     if(znam<0) al+=Math.PI;
                                     
                                    if (Math.abs(Math.cos(al))<0.001) this.h=(this.size*this.h*Math.sin(this.alpha)-m*u*Math.sin(a))/
                                   		 ((this.size-m)*Math.sin(al));
                                    else this.h=(this.size*this.h*Math.cos(this.alpha)-m*u*Math.cos(a))/
                                    		 ((this.size-m)*Math.cos(al));
                                     this.alpha=al;
                                     if (this.h>1.6) this.h=1.6;
                                     this.size-=0.1;
                                     sn.Add(sn.get(0,x0,
                                                      y0,
                                                      z0,
                                                     0,2,
                                                     a,q,2.0));
                                     this.time++;
                                     }else if ((!this.onAtack)&&(this.time==0)) this.time=0; else 
                                    	 if (this.time==this.delayAtack)  this.time=0; else this.time++;  
    }
}

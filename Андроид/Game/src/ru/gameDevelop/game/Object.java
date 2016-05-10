package ru.gameDevelop.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Object {
	double maxSpeed=1.5;
	public double eH,eW;
	public class Node{
		int num;//тип объекта
		//0-неподвижный объект
		//1-подвижный объект с возможностью стрелять
		//2-подвижный объект, снаряд
		int index;
		double x,y,z; //координаты объекта
		double radius; //радиус видимости объекта
		double size; //размер объекта
		double alpha, beta; //углы направления движения
		double s;//скорость объекта
		double distance; //пройденный путь
		int time;//время прошедшее с момента выстрела
		int delayAtack;    //время перезарядки
		boolean goAtack;//флаг атаки
		boolean goDestroy;//флаг уничтожения
		boolean goAI;//флаг ИИ движения
		double hp;// хп объектов
		int gtime;//время под действием гравитацией
		
		double p;//импульс вылета
		double dm;//отнимаемая масса
		double k;//коэффициент поглощения
		double damage;//урон
		double mp;//бодрость
		
		int rightFrame;
	    int leftFrame;
	    int topFrame;
	    int downFrame;
	    		
	    Node(){
	    	this.rightFrame=0;
	    	this.leftFrame=0;
	    	this.topFrame=0;
	    	this.downFrame=0;
	    		    
		}
	}
	
	List<Node> head;
	
	Object(){
		this.head = new ArrayList<Node>();
	}
	
	Node get(int n,double x,
            double y,
            double z,
            double radius,
            double size,
            double alpha,
            double beta,
            double s)  // функция получения данных для нового объекта
	{                          // потенциально свести к 6-праметрам: n, x, y, z, alpha, beta
		Node tmp=new Node();
		tmp.num=n;
		tmp.x=x;
		tmp.y=y;
		tmp.z=z;
		tmp.radius=radius;
		tmp.size=size;
		tmp.alpha=alpha;
		tmp.beta=beta;
		tmp.s=s;
		tmp.time=0;
		int temp = new Random().nextInt(26);
		tmp.delayAtack=10+ temp;
		tmp.distance=0;
		tmp.goAtack=false;
		tmp.goDestroy=false;
		tmp.goAI=false;
		tmp.hp=10;
		tmp.gtime=0;
		return tmp;
	}
	
	void Add(Node tmp){
		this.head.add(tmp);
	}
	
	void action(Object sn, control msg){
		for(int i=0;i<this.head.size();i++){
        /**/
			Node cur=this.head.get(i);
			double rez=Math.sqrt(Math.pow(cur.x-msg.x,2)+
					   Math.pow(cur.y-msg.y,2)+
					   Math.pow(cur.z-msg.z,2));

			if ((rez<cur.radius)&&(cur.time==0)){
				double a;                                                                        //

				if((msg.x-cur.x)==0){
					if((msg.y-cur.y)>0) a=Math.PI/2; else a=-Math.PI/2;
                    if((msg.y-cur.y)==0)a=0;
                }else a=Math.atan((msg.y-cur.y)/(msg.x-cur.x));  //
                if((msg.x-cur.x)<0) a+=Math.PI;
                double q=Math.asin((msg.z-cur.z)/rez);                                             //
                
                int trand = new Random().nextInt(21)-10;
                a+=trand*(Math.PI/180);

                double x=cur.x+(cur.size+2)*Math.cos(a)*Math.cos(q);
                double y=cur.y+(cur.size+2)*Math.sin(a)*Math.cos(q);
                double z=cur.z+(cur.size+2)*Math.sin(q);
                                                  
                cur.goAtack=true;
      
                for(int j=0;j<this.head.size();j++){
                	Node curint=this.head.get(j);
                    if(cur!=curint){
                    	double d=Math.sqrt(Math.pow(cur.x-curint.x,2)+
                                           Math.pow(cur.y-curint.y,2)+
                                           Math.pow(cur.z-curint.z,2));
                                                      
                        double xc=cur.x+d*Math.cos(a)*Math.cos(q);
                        double yc=cur.y+d*Math.sin(a)*Math.cos(q);
                        double zc=cur.z+d*Math.sin(q);

                        double pd=Math.sqrt(Math.pow(xc-curint.x,2)+
                                            Math.pow(yc-curint.y,2)+
                                            Math.pow(zc-curint.z,2));
                        if(pd<curint.size){cur.goAtack=false; break;}
                    }
                }
                int temp = new Random().nextInt(2);
                
                if (cur.goAtack) sn.Add(sn.get(10,x,
                                                  y,
                                                  z,
                                                  0,2,
                                                  a,q,(2+temp)*1));
                                                  cur.time++;
			}else if(cur.time==cur.delayAtack) cur.time=0; else cur.time++;
        /**/
		}
	}
	
	void move(){
		for (int i=0;i<this.head.size();i++){
			Node cur=this.head.get(i);
			/**/
			cur.x+=Math.cos(cur.alpha)*Math.cos(cur.beta)*cur.s;
			cur.y+=Math.sin(cur.alpha)*Math.cos(cur.beta)*cur.s;
			cur.z+=Math.sin(cur.beta)*cur.s;
			
			if(cur.x<-512){
				cur.x=-512;
				double a=cur.alpha+Math.PI;
    			cur.alpha=-a;
    		}
    		if(cur.x>512){
    			cur.x=512;
    			double a=cur.alpha+Math.PI;
    			cur.alpha=-a;
    		}
    		if(cur.y<-512){
    			cur.y=-512;
    			double a=cur.alpha;
    			cur.alpha=-a;
    		}
    		if(cur.y>512){
    			cur.y=512;
    			double a=cur.alpha;
    			cur.alpha=-a;
    		}
			
			cur.distance+=cur.s;
         /**/
         // 
			//if((cur.distance-3000)>0) cur.goDestroy=true;
		}
	}
	
	void usl(control gam, Object enu){ 
		for (int i=0;i<this.head.size();i++){
			Node cur=this.head.get(i);
            
			//if((cur.distance-3000)>0) cur.goDestroy=true;
         //
			for (int j=0;j<this.head.size();j++){
				Node intr=this.head.get(j);
				double d=Math.sqrt(Math.pow(cur.x-intr.x,2)+
								   Math.pow(cur.y-intr.y,2)+
								   Math.pow(cur.z-intr.z,2));

				if((d<(cur.size+intr.size))&&(intr!=cur)){
					cur.goDestroy=true;
					intr.goDestroy=true;
				}
			}
           //
			double d=Math.sqrt(Math.pow(cur.x-gam.x,2)+
            		 		   Math.pow(cur.y-gam.y,2)+
            		 		   Math.pow(cur.z-gam.z,2));

			if (d<(cur.size+gam.size)) {
				cur.goDestroy=true;
				gam.hp-=1;
            	gam.x+=cur.s*Math.cos(cur.alpha)*Math.cos(cur.beta)*2;
            	gam.y+=cur.s*Math.sin(cur.alpha)*Math.cos(cur.beta)*2;
            	gam.z+=cur.s*Math.sin(cur.beta)*2;
			}
            //
			for(int ii=0;ii<enu.head.size();ii++){
				Node current=enu.head.get(ii);
            	 
				d=Math.sqrt(Math.pow(cur.x-current.x,2)+
							Math.pow(cur.y-current.y,2)+
							Math.pow(cur.z-current.z,2));
				if(d<(cur.size+current.size)) {
					cur.goDestroy=true;
					current.hp-=1;
					if(current.num!=21){
						current.x+=cur.s*Math.cos(cur.alpha)*Math.cos(cur.beta)*2;
						current.y+=cur.s*Math.sin(cur.alpha)*Math.cos(cur.beta)*2;
						current.z+=cur.s*Math.sin(cur.beta)*2;   
					}
					if (current.hp<0) current.goDestroy=true;
				}
			}
		}
	}
	
	void osmos(control gamer, Object enu){
		for (int i=0;i<this.head.size();i++){
			Node cur=this.head.get(i);
            			
			for (int j=0;j<this.head.size();j++){
				Node intr=this.head.get(j);
				double d=Math.sqrt(Math.pow(cur.x-intr.x,2)+
								   Math.pow(cur.y-intr.y,2)+
								   Math.pow(cur.z-intr.z,2));

				if((d<(cur.size+intr.size))&&(intr!=cur)){
					if (cur.size>intr.size){
						cur.size+=0.02;
						intr.size-=0.1;
					}else{
						cur.size-=0.1;
						intr.size+=0.02;
					}
				if (cur.size<1) cur.goDestroy=true;
				if (intr.size<1) intr.goDestroy=true;
				}
			}
		
           //
			double d=Math.sqrt(Math.pow(cur.x-gamer.x,2)+
            		 		   Math.pow(cur.y-gamer.y,2)+
            		 		   Math.pow(cur.z-gamer.z,2));

			if (d<(cur.size+gamer.size)){
				if (cur.size>gamer.size){
					cur.size+=0.02;
					gamer.size-=0.1;
				}else{
					cur.size-=0.1;
					gamer.size+=0.04;
				}
				if (cur.size<1) cur.goDestroy=true;
				if (gamer.size<1) gamer.hp=-10;
			}
		
            //
			for(int ii=0;ii<enu.head.size();ii++){
				Node current=enu.head.get(ii);
            	 
				d=Math.sqrt(Math.pow(cur.x-current.x,2)+
							Math.pow(cur.y-current.y,2)+
							Math.pow(cur.z-current.z,2));
				if(d<(cur.size+current.size)) 
					if(cur.num==current.num){
						current.goDestroy=true;
						cur.size+=current.size;
					}else{
						current.goDestroy=true;
						cur.size-=current.size/5;
					}
				
				d=Math.sqrt(Math.pow(current.x-gamer.x,2)+
     		 		   Math.pow(current.y-gamer.y,2)+
     		 		   Math.pow(current.z-gamer.z,2));

				if (d<(current.size+gamer.size)){
					if (current.num==0){
						current.goDestroy=true;
						gamer.size+=0.0101;
					}else{
						if (current.size>gamer.size){
							current.size+=0.1;
							gamer.size-=0.1;
						}else{
							current.size-=0.1;
							gamer.size+=0.02;
						}
						if (current.size<1) current.goDestroy=true;
						if (gamer.size<1) gamer.hp=-10;
					}
				}
			}
		}
	}
	
	void AImove(control gamer, Object coil, Object planet){
		for(int i=0;i<this.head.size();i++){
    		Node tmp=this.head.get(i);
    		if(tmp.goAI){                                
        
    			double x=tmp.x+Math.cos(tmp.alpha)*Math.cos(tmp.beta)*tmp.s;
    			double y=tmp.y+Math.sin(tmp.alpha)*Math.cos(tmp.beta)*tmp.s;
    			double z=tmp.z+Math.sin(tmp.beta)*tmp.s;

    			int re=0;
    			for(int j=0;j<this.head.size();j++){
    				Node cur=this.head.get(j);
    				double d=Math.sqrt(Math.pow(x-cur.x,2)+
    								   Math.pow(y-cur.y,2)+
    								   Math.pow(z-cur.z,2));
    				if((d<(tmp.size+cur.size))&&(cur!=tmp))re++;
    			}
    			double sk=tmp.s;
    			switch (re){
    				case 0:tmp.alpha=tmp.alpha; tmp.beta=tmp.beta; break;
    				case 1:tmp.alpha=tmp.alpha+Math.PI; break;
    				default:sk=0;
    			}
        // 
    			tmp.x+=Math.cos(tmp.alpha)*Math.cos(tmp.beta)*sk;
    			tmp.y+=Math.sin(tmp.alpha)*Math.cos(tmp.beta)*sk;
    			tmp.z+=Math.sin(tmp.beta)*sk;
    			tmp.distance+=tmp.s;
    			if(tmp.distance>30) tmp.goAI=false; 
    		}else{
        //
    			tmp.distance=0;
    			
    			double prior1=10000;
    			double prior2=10000;

    			boolean flag=false;   //
    			boolean onDangerous=false;
    			
    			int itr=0;
    			Node cur;
    			for (int ii=0;ii<coil.head.size();ii++){
    				cur=coil.head.get(ii);
    				double d=Math.sqrt(Math.pow(tmp.x-cur.x,2)+
    								   Math.pow(tmp.y-cur.y,2)+
    								   Math.pow(tmp.z-cur.z,2));

    				double x=cur.x+Math.cos(cur.alpha)*Math.cos(cur.beta)*d;
    				double y=cur.y+Math.sin(cur.alpha)*Math.cos(cur.beta)*d;
    				double z=cur.z+Math.sin(cur.beta)*d;

    				double u=Math.sqrt(Math.pow(tmp.x-x,2)+
    								   Math.pow(tmp.y-y,2)+
    								   Math.pow(tmp.z-z,2));

    				if (u<tmp.size) {prior1=d/cur.s; itr=ii; onDangerous=true; break;}
    			}
    			
    			if (onDangerous){
    				cur=coil.head.get(itr);
    				flag=true;
    				int k = new Random().nextInt(2);
    				if(k==0) tmp.alpha=cur.alpha+Math.PI/2; else tmp.alpha=cur.alpha-Math.PI/2;
    				tmp.goAI=true;
    			} else flag=false;

    			Node curplanet;//
    			prior2=prior1;
    			for(int jj=0;jj<planet.head.size();jj++){
    				curplanet=planet.head.get(jj);
    				double x=tmp.x;
    				double y=tmp.y;
    				double z=tmp.z;
    				double d=Math.sqrt(Math.pow(curplanet.x-x,2)+
    								   Math.pow(curplanet.y-y,2)+
    								   Math.pow(curplanet.z-z,2));

    				if(d<curplanet.radius){
    					flag=true;
    					double s=(0.5*(curplanet.s+
    								   curplanet.s*(curplanet.radius-d)/(curplanet.radius-2*curplanet.size)));
    					if((d/s)<prior2){

    						double a;
    						if((curplanet.x-x)==0){
    							if((curplanet.y-y)>0) a=Math.PI/2;else a=-Math.PI/2;
    							if((curplanet.y-y)==0)a=0;
    						}else a=Math.atan((curplanet.y-y)/(curplanet.x-x));  //
    						if((curplanet.x-x)<0) a+=Math.PI;
    						double q=Math.asin((curplanet.z-z)/d);

    						tmp.alpha=a+Math.PI;
    						tmp.beta=q;
    						tmp.goAI=true;
    						prior2=d/s;
    					}
    				}
    			}

    			double d=Math.sqrt(Math.pow(tmp.x-gamer.x,2)+
    							   Math.pow(tmp.y-gamer.y,2)+
    							   Math.pow(tmp.z-gamer.z,2));

    			if((d>tmp.radius-5)&&(!flag)){
    				double a;                                                                        //
    				if((gamer.x-tmp.x)==0){
    					if((gamer.y-tmp.y)>0) a=Math.PI/2;else a=-Math.PI/2;
    					if((gamer.y-tmp.y)==0)a=0;
    				}else a=Math.atan((gamer.y-tmp.y)/(gamer.x-tmp.x));  //
    				if((gamer.x-tmp.x)<0) a+=Math.PI;
    				double q=Math.asin((gamer.z-tmp.z)/d);
    				tmp.alpha=a;
    				tmp.beta=q;

    				tmp.goAI=true;

    			}else if(!flag){
    				boolean mv=false;
    				Node tcur;
    				int kol=0;
    				tmp.alpha=0;
    				for(int ij=0;ij<this.head.size();ij++){
    					tcur=this.head.get(ij);
    					if (tcur!=tmp){
    						double rst=Math.sqrt(Math.pow(tcur.x-tmp.x,2)+
    											 Math.pow(tcur.y-tmp.y,2)+
    											 Math.pow(tcur.z-tmp.z,2));
    						double a;                                                                        //
    						if((tcur.x-tmp.x)==0){
    							if((tcur.y-tmp.y)>0) a=Math.PI/2;else a=-Math.PI/2;
    							if((tcur.y-tmp.y)==0)a=0;
    						}else a=Math.atan((tcur.y-tmp.y)/(tcur.x-tmp.x));  //
    						if((tcur.x-tmp.x)<0) a+=Math.PI;
    						double q=Math.asin((tcur.z-tmp.z)/rst);

    						if (rst<(1+(tcur.size+tmp.size)*2)){
    							kol++;
    							tmp.alpha+=a;
    							tmp.alpha/=kol;
    							tmp.beta=q;
    							mv=true;
    						}
    					}
    				}
    				if(mv){
    					tmp.alpha+=Math.PI;
    					tmp.goAI=true;
    				}else{
    					double a;                                                                        //
    					if((gamer.x-tmp.x)==0){
    						if((gamer.y-tmp.y)>0) a=Math.PI/2; else a=-Math.PI/2;
    						if((gamer.y-tmp.y)==0)a=0;
    					}else a=Math.atan((gamer.y-tmp.y)/(gamer.x-tmp.x));  //
    					if((gamer.x-tmp.x)<0) a+=Math.PI;
    					double q=Math.asin((gamer.z-tmp.z)/d);
    					tmp.alpha=a;
    					tmp.beta=q;
    					double dq=Math.sqrt(Math.pow(tmp.x-gamer.x,2)+
    										Math.pow(tmp.y-gamer.y,2)+
    										Math.pow(tmp.z-gamer.z,2));

    					double defDis=tmp.size*2*this.maxSpeed/tmp.s;

    					if(dq<defDis) {tmp.alpha+=Math.PI; tmp.goAI=true;}else ;//{tmp->alpha=(pi/4)*random(8);tmp->goAI=true;}

    				}
    			} //
    		} //
		}//
	} //
	
	public class prior{
		double angle;
		double pr;
	}
	
	void calcMove(control gamer, Object coil){
		double dng=100;
		for(int i=0;i<this.head.size();i++){
			Node tmp=this.head.get(i);
			List<prior>hd = new ArrayList<prior>();
			for(int j=0;j<this.head.size();j++){
				Node cur=this.head.get(j);
				if(cur!=tmp){
					double r=Math.sqrt((tmp.x-cur.x)*(tmp.x-cur.x)+(tmp.y-cur.y)*(tmp.y-cur.y));
					double a;                        //

					if((cur.x-tmp.x)==0){
						if((cur.y-tmp.y)>0) a=Math.PI/2;else a=-Math.PI/2;
					}else a=Math.atan((cur.y-tmp.y)/(cur.x-tmp.x));
					if((cur.x-tmp.x)<0) a+=Math.PI;
               
					if(cur.size>tmp.size) dng=-100; else dng=100;
                
					boolean ext=false; 
					for(int k=0; k<hd.size(); k++){
						prior nxt=hd.get(k);
						if(Math.abs(nxt.angle-a)<Math.PI/72){
							nxt.pr+=dng/r;
							ext=true;
						}
					}
					if(!ext){
						prior nw=new prior(); 
						nw.angle=a;
						nw.pr=dng/r;
						hd.add(nw);
					}
				}
			}
			
			double r=Math.sqrt((tmp.x-gamer.x)*(tmp.x-gamer.x)+(tmp.y-gamer.y)*(tmp.y-gamer.y));
			double a;                        //

            if((gamer.x-tmp.x)==0){
            	if((gamer.y-tmp.y)>0) a=Math.PI/2;else a=-Math.PI/2;
            }else a=Math.atan((gamer.y-tmp.y)/(gamer.x-tmp.x));
            if((gamer.x-tmp.x)<0) a+=Math.PI;
			
            if(gamer.size>tmp.size) dng=-100; else dng=100;
            boolean ext=false; 
            for(int k=0; k<hd.size(); k++){
            	prior nxt=hd.get(k);
            	if(Math.abs(nxt.angle-a)<Math.PI/72){
            		nxt.pr+=dng/r;
            		ext=true;
            	}
            }
            if(!ext){
            	prior nw = new prior();
            	nw.angle=a;
            	nw.pr=dng/r;
            	hd.add(nw);
            }
            
			double max=0;
			int mk=0;
			for(int ii=0;ii<hd.size();ii++){
				prior tm=hd.get(ii);
				if(max<Math.abs(tm.pr)){
					max=Math.abs(tm.pr);
					mk=ii;
				}
			}
			if (hd.get(mk).pr>0) tmp.alpha=hd.get(mk).angle;else {
				/*double ch=0; double zn=0;
				for(int j=0;j<this.head.size();j++){
					Node cur=this.head.get(j);
					if(cur!=tmp){
						double R=Math.sqrt((tmp.x-cur.x)*(tmp.x-cur.x)+(tmp.y-cur.y)*(tmp.y-cur.y));
						double A;                        //

						if((cur.x-tmp.x)==0){
							if((cur.y-tmp.y)>0) A=Math.PI/2;else A=-Math.PI/2;
						}else A=Math.atan((cur.y-tmp.y)/(cur.x-tmp.x));
						if((cur.x-tmp.x)<0) A+=Math.PI;
						zn+=Math.cos(A);
						ch+=Math.sin(A);
						}
					double R=Math.sqrt((tmp.x-gamer.x)*(tmp.x-gamer.x)+(tmp.y-gamer.y)*(tmp.y-gamer.y));
					double A;                        //

					if((gamer.x-tmp.x)==0){
						if((gamer.y-tmp.y)>0) A=Math.PI/2;else A=-Math.PI/2;
					}else A=Math.atan((gamer.y-tmp.y)/(gamer.x-tmp.x));
					if((gamer.x-tmp.x)<0) A+=Math.PI;
					zn+=Math.cos(A);
					ch+=Math.sin(A);
				}
				double u;
				if(zn==0){
					if(ch>0) u=Math.PI/2;else u=-Math.PI/2;
				}else u=Math.atan(ch/zn);
				if(zn<0) u+=Math.PI;*/
				tmp.alpha=hd.get(mk).angle+Math.PI;
			}
		}
	}
	
	int Destroy(){
		 List<Node> delList=new ArrayList<Node>();
		 delList=this.head;
		 int k=0;
		 for(int i=0;i<delList.size();i++){
			 Node cur=delList.get(i);
			 if (cur.goDestroy) {
				 this.head.remove(cur);
				 k++;
			 }
		 }return k;
		/*for(int i=0;i<this.head.size();i++){
			Node cur=this.head.get(i);
			if (cur.goDestroy) a.add(i);
		}
		for(int j=0;j<a.size();j++){
			this.head.remove(this.head.get(a.get(j)));
		}*/
	}
	
	void frameCalc(){
		for(int i=0;i<this.head.size();i++){
    		Node tmp=this.head.get(i);
		
    	/*while(tmp.alpha>2*Math.PI) tmp.alpha-=2*Math.PI;
    	if(tmp.alpha>3*Math.PI/2) tmp.alpha=2*Math.PI-tmp.alpha;
    	
    	if(tmp.alpha<-Math.PI/2) tmp.alpha=2*Math.PI+tmp.alpha;*/
    		
		if((tmp.alpha>-Math.PI/4)&&(tmp.alpha<Math.PI/4))
			if(tmp.rightFrame==2)tmp.rightFrame=0;else  tmp.rightFrame++;
		
		if((tmp.alpha>Math.PI/4)&&(tmp.alpha<3*Math.PI/4))
			if(tmp.topFrame==2)tmp.topFrame=0;else  tmp.topFrame++;
		
		if((tmp.alpha>3*Math.PI/4)&&(tmp.alpha<5*Math.PI/4))
			if(tmp.leftFrame==2)tmp.leftFrame=0;else  tmp.leftFrame++;
		
		if((tmp.alpha>5*Math.PI/4)||(tmp.alpha<-Math.PI/4))
			if(tmp.downFrame==2)tmp.downFrame=0;else  tmp.downFrame++;
				
	}
	}
	
	//отрисовка
	 public void draw(Canvas canvas,Bitmap bitmap, double x, double y) {
		 for(int i=0;i<this.head.size();i++){
			 Node tmp=this.head.get(i);
			 
			// if ((tmp.x-x<0)||(tmp.y-y<0)||(tmp.x-x>this.eW)||(tmp.y-y>this.eH)) continue;  
			 int srcX = 0;
		        int srcY = 0;
		    	
		    	if((tmp.alpha>-Math.PI/4)&&(tmp.alpha<Math.PI/4))
		    	{srcX = tmp.rightFrame * bitmap.getWidth() / 3;
		         srcY = 2 * bitmap.getHeight() / 4;}
				
				if((tmp.alpha>Math.PI/4)&&(tmp.alpha<3*Math.PI/4))
				{srcX = tmp.topFrame * bitmap.getWidth() / 3;
		        srcY = 0 * bitmap.getHeight() / 4;}
				
				if((tmp.alpha>3*Math.PI/4)&&(tmp.alpha<5*Math.PI/4))
				{srcX = tmp.leftFrame * bitmap.getWidth() / 3;
		        srcY = 1 * bitmap.getHeight() / 4;}
				
				if((tmp.alpha>5*Math.PI/4)||(tmp.alpha<-Math.PI/4))
				{srcX = tmp.downFrame * bitmap.getWidth() / 3;
				srcY = 3 * bitmap.getHeight() / 4;}
				    	
		    	
		        Rect src = new Rect(srcX, srcY, srcX + bitmap.getWidth() / 3, srcY + bitmap.getHeight() / 4);
		        
		    	Rect dst = new Rect((int)tmp.x - (bitmap.getWidth() / 6)-(int)x, (int)tmp.y - (bitmap.getHeight() / 8)-(int)y,
		    			(int)tmp.x + (bitmap.getWidth() / 6)-(int)x, (int)tmp.y + (bitmap.getHeight() / 8)-(int)y);
		    	
		    	canvas.drawBitmap(bitmap, src, dst, null);
			 
			// canvas.drawBitmap(bitmap, (int)tmp.x - (bitmap.getWidth() / 2)-(int)x, (int)tmp.y - (bitmap.getHeight() / 2)-(int)y, null);
		 }
	}
	 
	 public void simpleDraw(Canvas canvas,Bitmap bitmap, double x, double y) {
		
		 for(int i=0;i<this.head.size();i++){
			 Node tmp=this.head.get(i);	 
			// if ((tmp.x-x<0)||(tmp.y-y<0)||(tmp.x-x>this.eW)||(tmp.y-y>this.eH)) continue;  
			 canvas.drawBitmap(bitmap, (int)tmp.x - (bitmap.getWidth() / 2)-(int)x, (int)tmp.y - (bitmap.getHeight() / 2)-(int)y, null);
		}
	 }
		 
	public void DrawC(Canvas canvas, double x, double y, boolean f) {
				
		for(int i=0;i<this.head.size();i++){
			Node tmp=this.head.get(i);	 
			Paint paint;
			paint=new Paint();
			if (f) paint.setColor(Color.RED); else paint.setColor(Color.BLUE);
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(true);
			paint.setTextSize(40);
			canvas.drawCircle((float)(tmp.x-x),(float)(tmp.y-y),(float)tmp.size, paint);
		}
	}
}

package ru.gameDevelop.game;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class environment {
	public int frame;
	public Bitmap bitmap;
	public double x,y;
	environment(){
		this.frame= new Random().nextInt(3);
	}
	void draw(Canvas canvas, double xs, double ys){
		
		int srcX = this.frame * this.bitmap.getWidth() / 3;
        
		Rect src = new Rect(srcX, 0, srcX + this.bitmap.getWidth() / 3, 0 + this.bitmap.getHeight());
        
    	Rect dst = new Rect((int)this.x - (this.bitmap.getWidth() / 6)-(int)xs, (int)this.y - (this.bitmap.getHeight() / 8)-(int)ys,
        		(int)this.x - (this.bitmap.getWidth() / 6)-(int)xs+this.bitmap.getWidth() / 3, (int)this.y - (this.bitmap.getHeight() / 8)-(int)ys+this.bitmap.getWidth() / 3);
    	
    	canvas.drawBitmap(bitmap, src, dst, null);
	}
	void updateFrame(){
		if(this.frame==2)this.frame=0;else this.frame++;
	}
}

package com.example.jyheo.customview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jyheo on 17. 5. 11.
 */

public class MyView extends View {
    Rect  rect = new Rect(10, 10, 110, 110);
    int   color = Color.BLUE;
    Paint paint = new Paint();

    public MyView(Context context) {
        super(context);

    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        color = attrs.getAttributeIntValue(null, "mycolor", Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
        canvas.drawRect(rect, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()== MotionEvent.ACTION_DOWN){
            rect.left = (int)event.getX();
            rect.top = (int)event.getY();
            rect.right = rect.left + 100;
            rect.bottom = rect.top + 100;
            invalidate();
        }
        return super.onTouchEvent(event);
    }

}

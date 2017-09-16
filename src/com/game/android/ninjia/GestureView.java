package com.game.android.ninjia;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;



	public class GestureView extends View {

	    private Paint paint;

	    private float startX = Float.NaN;
	    private float startY = Float.NaN;
	    private float endX = Float.NaN;
	    private float endY = Float.NaN;

	    //�²�view
	    //private View viewer;
	    private static final int gestureColor = Color.rgb(153, 153, 153);
	    private static final int alpha = 220;
	    private static final int alpha_full = 255;
	    //���泤��
	    private static final int shape_length = 80;
	    //����ض�ʱ��
	    private static final int shape_cut_time = 150;
	    public static final float getGap(float x0, float y0, float x1, float y1) {
	    	return (float) Math.pow(Math.pow((x0 - x1), 2) + Math.pow((y0 - y1), 2), 0.5);
	    	}
	    
	    public GestureView(Context context) {
	        super(context);
	        
	        paint = new Paint();
	        paint.setStyle(Paint.Style.FILL);
	    }
	    @Override
	    public void onDraw(Canvas canvas) {
	        super.onDraw(canvas);
	        if (!Float.isNaN(startX) && !Float.isNaN(endY)) {
	            float gap = getGap(startX, startY, endX, endY);
	            float w = gap / 10;
	            //����shape����߶�
	            float h = w > 7 ? 7 : w;
	            //���shape����߶�
	            float h2 = h * 2 / 3; 

	            double length = Math.pow(Math.pow(w, 2) + Math.pow((h), 2), 0.5);
	            double length2 = Math.pow(Math.pow(w, 2) + Math.pow((h2), 2), 0.5);
	            
	            double ang1_1 = Math.atan((endY - startY) / (endX - startX));
	            double ang1_2 = Math.atan(h / w);
	            double angle1_1 = ang1_1 + ang1_2;
	            double angle1_2 = ang1_1 - ang1_2;
	            
	            double ang2_2 = Math.atan(h2 / w);
	            double angle2_1 = ang1_1 + ang2_2;
	            double angle2_2 = ang1_1 - ang2_2;
	            if (endX > startX) {
	                float xx1 = endX - (float) (length * Math.cos(angle1_1));
	                float yy1 = endY - (float) (length * Math.sin(angle1_1));
	                float xx2 = endX - (float) (length * Math.cos(angle1_2));
	                float yy2 = endY - (float) (length * Math.sin(angle1_2));

	                float xx12 = endX - (float) (length2 * Math.cos(angle2_1));
	                float yy12 = endY - (float) (length2 * Math.sin(angle2_1));
	                float xx22 = endX - (float) (length2 * Math.cos(angle2_2));
	                float yy22 = endY - (float) (length2 * Math.sin(angle2_2));
	                
	                Path backPath = new Path();
	                backPath.moveTo(startX, startY);
	                backPath.lineTo(xx1, yy1);
	                backPath.lineTo(endX, endY);
	                backPath.lineTo(xx2, yy2);
	                backPath.close();
	                
	                Path fillPath = new Path();
	                fillPath.moveTo(startX, startY);
	                fillPath.lineTo(xx12, yy12);
	                fillPath.lineTo(endX, endY);
	                fillPath.lineTo(xx22, yy22);
	                fillPath.close();

	                paint.setColor(Color.RED);
	                paint.setAlpha(alpha);
	                canvas.drawPath(backPath, paint);
	                
	                paint.setColor(Color.RED);
	                paint.setAlpha(alpha_full);
	                canvas.drawPath(fillPath, paint);
	            } else {
	                float xx1 = endX + (float) (length * Math.cos(angle1_1));
	                float yy1 = endY + (float) (length * Math.sin(angle1_1));
	                float xx2 = endX + (float) (length * Math.cos(angle1_2));
	                float yy2 = endY + (float) (length * Math.sin(angle1_2));

	                float xx12 = endX + (float) (length2 * Math.cos(angle2_1));
	                float yy12 = endY + (float) (length2 * Math.sin(angle2_1));
	                float xx22 = endX + (float) (length2 * Math.cos(angle2_2));
	                float yy22 = endY + (float) (length2 * Math.sin(angle2_2));
	                
	                Path backPath = new Path();
	                backPath.moveTo(startX, startY);
	                backPath.lineTo(xx1, yy1);
	                backPath.lineTo(endX, endY);
	                backPath.lineTo(xx2, yy2);
	                backPath.close();

	                Path fillPath = new Path();
	                fillPath.moveTo(startX, startY);
	                fillPath.lineTo(xx12, yy12);
	                fillPath.lineTo(endX, endY);
	                fillPath.lineTo(xx22, yy22);
	                fillPath.close();

	                paint.setColor(Color.WHITE);
	                paint.setAlpha(alpha);
	                canvas.drawPath(backPath, paint);
	                
	                paint.setColor(Color.WHITE);
	                paint.setAlpha(alpha_full);
	                canvas.drawPath(fillPath, paint);
	            }
	        }
	    }
	    @Override
	    public boolean onTouchEvent(android.view.MotionEvent event) {
	        if(event.getPointerCount() == 1){
	            int action = event.getAction();
	            if (MotionEvent.ACTION_DOWN == action) {
	                startX = event.getX();
	                startY = event.getY();
	            } else if (MotionEvent.ACTION_MOVE == action) {
	                endX = event.getX();
	                endY = event.getY();
	                
	                //����ض�ʱ�䣬��ض���һ��
	                if ((event.getEventTime() - event.getDownTime()) > shape_cut_time) {
	                    if(Math.abs(endX - startX) > shape_length && Math.abs(endY - startY) > shape_length){
	                        startX = (float) (startX + (endX - startX) * 0.5);
	                        startY = (float) (startY + (endY - startY) * 0.5);
	                    }
	                }

	                invalidate();
	            } else if (MotionEvent.ACTION_UP == action) {
	                startX = Float.NaN;
	                startY = Float.NaN;
	                endX = Float.NaN;
	                endY = Float.NaN;
	                invalidate();
	            }
	        }
	        //��view������event�������²��view����dispatchTouchEvent���ܻ���¼�
	        //MotionEvent newEvent = MotionEvent.obtain(event);
	        //viewer.dispatchTouchEvent(newEvent);
	        return true;
	    }

	}
package com.yura.test2.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.yura.test2.entity.Table;

public abstract class TableView extends View {
    protected boolean touchedInsideTable, isTouch = false;
    protected Paint paint, textPaint;
    protected Table table;
    protected float centerX, centerY;
    protected int tableWidth, tableHeight, viewWidth, viewHeight;

    public TableView(Context context, Table table, int width, int height) {
        super(context);

        this.table = table;
        this.tableWidth = width;
        this.tableHeight = height;

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(18);
        textPaint.setTextAlign(Paint.Align.CENTER);

        paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);
    }

    public void setTable(Table table) {
        this.table = table;
    }

    protected abstract boolean PointInPolygon(float Xo, float Yo);

    protected void drawTableName(Canvas canvas){
        if(table.angle != 0){
            if(table.angle <=90) {
                canvas.rotate(table.angle, centerX, centerY);
            }else {
                canvas.rotate(-1* (360 - table.angle), centerX, centerY);
            }
        }

        canvas.drawText(table.NAME,centerX,centerY,textPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        viewWidth = getWidth();
        viewHeight = getHeight();

        centerX = (float)viewWidth/2.0f;
        centerY = (float)viewHeight/2.0f;

        if(isTouch){
            paint.setAlpha(150);
        }else {
            paint.setAlpha(255);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(0);
        float y = event.getY(0);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchedInsideTable = PointInPolygon(x, y);
                if(touchedInsideTable){
                    isTouch = true;
                    invalidate();
                    return true;
                }
                return false;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                invalidate();
                if(touchedInsideTable && PointInPolygon(x, y)){
                    callOnClick();
                }
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}

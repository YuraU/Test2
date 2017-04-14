package com.yura.test2.customview;


import android.content.Context;
import android.graphics.Canvas;

import com.yura.test2.entity.Table;

public class RoundTable extends TableView{

    private float r;

    public RoundTable(Context context, Table table, int width, int height) {
        super(context, table, width, height);
    }

    @Override
    protected boolean PointInPolygon(float Xo, float Yo) {
        //(x - x0)^2 + (y - y0)^2 <= R^2
        return (centerX-Xo) * (centerX-Xo) + (centerY-Yo) * (centerY-Yo) <= r*r;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        r = Math.min(centerX, centerY);

        canvas.drawCircle(centerX, centerY, r, paint);

        drawTableName(canvas);
    }

}

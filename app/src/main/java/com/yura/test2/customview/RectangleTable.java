package com.yura.test2.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

import com.yura.test2.entity.Table;

import java.util.ArrayList;
import java.util.List;

public class RectangleTable extends TableView{

    private float[] points;

    public RectangleTable(Context context, Table table, int width, int height) {
        super(context, table, width, height);
    }

    public boolean PointInPolygon(float Xo, float Yo) {
        int iro = -1;
        byte ifl;
        double xBegin, yBegin, xFinish, yFinish, coeff1, coeff2;

        List<Point> mPoints = new ArrayList<>();
        mPoints.add(new Point((int)points[0], (int)points[1]));
        mPoints.add(new Point((int)points[2], (int)points[3]));
        mPoints.add(new Point((int)points[4], (int)points[5]));
        mPoints.add(new Point((int)points[6], (int)points[7]));
        mPoints.add(new Point((int)points[0], (int)points[1]));

        for (int i = 0; i < mPoints.size() - 1; i++) {
            xBegin = mPoints.get(i).x;
            yBegin = mPoints.get(i).y;
            xFinish = mPoints.get(i+1).x;
            yFinish = mPoints.get(i+1).y;

            if ((Xo == xBegin && Yo == yBegin) || (Xo == xFinish && Yo == yFinish)) {
                return true;
            }

            coeff1 = (xFinish - xBegin) * (Yo - yBegin);
            coeff2 = Xo - xBegin;
            if (coeff2 != 0) {
                if ((yFinish - yBegin) == (coeff1 / coeff2)
                        && (Xo - xBegin) * (Xo - xFinish) <= 0
                        && (Yo - yBegin) * (Yo - yFinish) <= 0) {
                    return true;
                }
            }

            ifl = 0;
            if ((Yo - yBegin) * (Yo - yFinish) < 0) {
                ifl = 1;
            } else {
                if (((Yo != yBegin) || (Yo <= yFinish)) && ((Yo != yFinish) || (Yo <= yBegin))) {
                    continue;
                } else {
                    ifl = 1;
                }
            }

            if (yBegin == yFinish)
                continue;

            if ((coeff2 + coeff1 / (yBegin - yFinish) >= 0) || (ifl == 0))
                continue;

            iro *= -1;
        }

        if (iro > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rect =new RectF(0 + (viewWidth-tableWidth)/2, 0 + (viewHeight-tableHeight)/2, tableWidth + (viewWidth-tableWidth)/2, tableHeight + (viewHeight-tableHeight)/2);

        Path path = new Path();
        path.addRect(rect, Path.Direction.CW);

        canvas.save();

        points = new float[]{
                0 + (viewWidth - tableWidth) / 2, 0 + (viewHeight - tableHeight) / 2, //left, top
                tableWidth + (viewWidth - tableWidth) / 2, 0 + (viewHeight - tableHeight) / 2, //right, top
                tableWidth + (viewWidth - tableWidth) / 2, tableHeight + (viewHeight - tableHeight) / 2, //right, bottom
                0 + (viewWidth - tableWidth) / 2, tableHeight + (viewHeight - tableHeight) / 2//left, bottom
        };

        if(table.angle != 0){
            Matrix m = new Matrix();
            if(table.angle <=90) {
                m.postRotate(table.angle, centerX, centerY);
            }else {
                m.postRotate(-1* (360 - table.angle), centerX, centerY);
            }
            m.mapRect(rect);
            path.transform(m, path);

            m.mapPoints(points);
        }

        canvas.drawPath(path, paint);
        drawTableName(canvas);

        canvas.restore();

    }
}

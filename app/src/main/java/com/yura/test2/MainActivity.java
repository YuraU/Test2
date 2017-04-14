package com.yura.test2;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.yura.test2.customview.RectangleTable;
import com.yura.test2.customview.RoundTable;
import com.yura.test2.customview.TableView;
import com.yura.test2.databinding.ActivityMainBinding;
import com.yura.test2.entity.Table;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final List<Table> tables = new ArrayList<>();
        tables.add(new Table(Table.Type.Square, "1", "1", 50, 50, 200, 100, 0));
        tables.add(new Table(Table.Type.Square, "2", "2", 520, 100, 100, 250, 0));
        tables.add(new Table(Table.Type.Square, "3", "3", 120, 250, 280, 180, 0));
        tables.add(new Table(Table.Type.Square, "4", "4", 50, 480, 120, 40, 30));
        tables.add(new Table(Table.Type.Square, "5", "5", 480, 480, 100, 60, 320));
        tables.add(new Table(Table.Type.Round, "6", "6", 320, 550, 60, 60, 0));

        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.getRoot().getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int width = binding.getRoot().getWidth(); // ширина
                int height = binding.getRoot().getHeight(); // высота

                float kWidth = (float) width / 680;
                float kHeight = (float) height / 680;

                for(Table table : tables){
                    int w = (int)(table.WIDTH * kWidth);
                    int h = (int)(table.HEIGHT * kHeight);
                    TableView view;
                    if(table.TYPE.equals(Table.Type.Square)) {
                        view = new RectangleTable(getApplicationContext(), table, w, h);
                    }else {
                        view = new RoundTable(getApplicationContext(), table, w, h);
                    }

                    FrameLayout.LayoutParams lpView;
                    if(table.angle != 0) {
                        int c = (int) Math.sqrt(w * w + h * h);
                        lpView = new FrameLayout.LayoutParams(c, c);
                    }else {
                        lpView = new FrameLayout.LayoutParams(w, h);
                    }
                    lpView.leftMargin = (int)(table.X*kWidth);
                    lpView.topMargin = (int)(table.Y*kHeight);

                    view.setLayoutParams(lpView);

                    binding.activityMain.addView(view);

                    view.setTag(table);
                    view.setOnClickListener(tableClickListener);
                }

            }
        });
    }

    View.OnClickListener tableClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            final Table curTable = (Table) view.getTag();

            final AlertDialog.Builder ratingdialog = new AlertDialog.Builder(MainActivity.this);
            final EditText editText = new EditText(MainActivity.this);
            editText.setText(curTable.NAME);
            ratingdialog.setTitle("Enter table name!");
            ratingdialog.setView(editText);

            ratingdialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            curTable.NAME = editText.getText().toString();
                            ((TableView)view).setTable(curTable);
                            view.invalidate();
                        }
                    })

                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            ratingdialog.create();
            ratingdialog.show();
        }
    };

}

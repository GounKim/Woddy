package com.cookandroid.woddy_addwritings_and_search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddWritingsActivity extends AppCompatActivity {
    myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    Button cancelBtn, finishBtn;
    TextView titleTV, plotTV, testTV;
    int writing_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_writings_main);

        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        finishBtn = (Button) findViewById(R.id.finishBtn);
        titleTV = (TextView) findViewById(R.id.titleTextView);
        plotTV = (TextView) findViewById(R.id.plotTextView);
        testTV = (TextView) findViewById(R.id.testTextView);

        myDBHelper = new myDBHelper(this);
        sqlDB = myDBHelper.getWritableDatabase();
        myDBHelper.onUpgrade(sqlDB, 1, 2);
        sqlDB.close();

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")

            @Override
            public void onClick(View v) {
                sqlDB = myDBHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO writings VALUES ('" + titleTV.getText().toString() + "' , '" + plotTV.getText().toString() + "');");
                Toast.makeText(getApplicationContext(), "insert finished", Toast.LENGTH_SHORT).show();

                sqlDB = myDBHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM writings;", null);

                Toast.makeText(getApplicationContext(), "2 finished", Toast.LENGTH_SHORT).show();

                String strTitle = "";
                String strPlot = "";

                while (cursor.moveToNext()) {
                    strTitle += cursor.getString(0);
                    strPlot += cursor.getString(1);
                }
                testTV.setText(strTitle + '\n' + strPlot);
                cursor.close();
                sqlDB.close();

                Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show();
                writing_index++;
            }
        });
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "writingsDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE writings ( writing_title TEXT , writing_plot TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS writings");
            onCreate(db);

        }
    }
}

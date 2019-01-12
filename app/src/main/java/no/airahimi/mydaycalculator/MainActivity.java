package no.airahimi.mydaycalculator;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    public static final String MY_DATE_FORMAT = "dd.MM.yyyy";
    private Calendar calendarNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarNow = Calendar.getInstance();
        String date = new SimpleDateFormat(MY_DATE_FORMAT).format(calendarNow.getTime());

        TextView tvDateFrom = findViewById(R.id.tvDateFrom);
        tvDateFrom.setText(getString(R.string.text_from, date));
    }

    public void calculate(View view) {
        EditText etDate = findViewById(R.id.etDate);
        String toDate = etDate.getText().toString();

        TextView tvResult = findViewById(R.id.tvResult);

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MY_DATE_FORMAT);
            Calendar toCalendar = Calendar.getInstance();
            toCalendar.setTime(simpleDateFormat.parse(toDate));

            long diff = calendarNow.getTimeInMillis() - toCalendar.getTimeInMillis();
            diff = Math.abs(diff);
            long days = diff / 1000 / 60 / 60 / 24;
            diff -= days * 1000 * 60 * 60 * 24;
            long hours = diff / 1000 / 60 / 60;
            diff -= hours * 1000 * 60 * 60;
            long mins = diff / 1000 / 60;
            String result = String.format("%dd %dt %dm", days, hours, mins);
            tvResult.setText(result);
        } catch (ParseException ex) {
            tvResult.setText("Date incorrect");
        } finally {
            tvResult.setVisibility(View.VISIBLE);
        }
    }
}
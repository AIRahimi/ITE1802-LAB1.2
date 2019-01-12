package no.airahimi.mydaycalculator;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    public static final String MY_DATE_FORMAT = "dd.MM.yyyy";
    private Calendar calendarNow;
    private Calendar calenderThen;
    private TextView tvResult;
    private EditText etDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarNow = Calendar.getInstance();
        String date = new SimpleDateFormat(MY_DATE_FORMAT).format(calendarNow.getTime());

        calenderThen = Calendar.getInstance();

        TextView tvDateFrom = findViewById(R.id.tvDateFrom);
        tvDateFrom.setText(getString(R.string.text_from, date));

        etDate = findViewById(R.id.etDate);
        tvResult = findViewById(R.id.tvResult);
    }

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calenderThen.set(Calendar.YEAR, year);
            calenderThen.set(Calendar.MONTH, monthOfYear);
            calenderThen.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String date = new SimpleDateFormat(MY_DATE_FORMAT).format(calenderThen.getTime());
            etDate.setText(date);
        }
    };

    public void setDate(View view) {
        DatePickerDialog picker = new DatePickerDialog(MainActivity.this, dateListener, calenderThen
                .get(Calendar.YEAR), calenderThen.get(Calendar.MONTH),
                calenderThen.get(Calendar.DAY_OF_MONTH));
        picker.show();
    }

    public void calculate(View view) {
        String toDate = etDate.getText().toString();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MY_DATE_FORMAT);
            calenderThen = Calendar.getInstance();
            calenderThen.setTime(simpleDateFormat.parse(toDate));

            long diff = calendarNow.getTimeInMillis() - calenderThen.getTimeInMillis();
            diff = Math.abs(diff);
            long days = diff / 1000 / 60 / 60 / 24;
            diff -= days * 1000 * 60 * 60 * 24;
            long hours = diff / 1000 / 60 / 60;
            diff -= hours * 1000 * 60 * 60;
            long mins = diff / 1000 / 60;
            String result = String.format("%dd %dt %dm", days, hours, mins);
            tvResult.setText(result);
        } catch (ParseException ex) {
            tvResult.setText("Please select a date");
        } finally {
            tvResult.setVisibility(View.VISIBLE);
        }
    }
}

package app.admin.schoolappcentral.healpers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by admin on 01/12/2017.
 */

public class DatePickerFragment
{

    private EditText editText ;
    public DatePickerFragment(EditText text, Context context)
    {
        editText = text;

        new DatePickerDialog(context,date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new
            DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
                {
                    myCalendar.set(Calendar.YEAR, i);
                    myCalendar.set(Calendar.MONTH, i1);
                    myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                    updateLabel();
                }
            };

        private void updateLabel()
        {
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            editText.setText(sdf.format(myCalendar.getTime()));
        }
}

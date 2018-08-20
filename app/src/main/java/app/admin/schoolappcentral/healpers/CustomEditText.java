package app.admin.schoolappcentral.healpers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * Created by admin on 03/04/2016.
 */
public class CustomEditText extends EditText
{
    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont()
    {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/HPSimplified.ttf");
        setTypeface(tf);
    }

    private void setPadding()
    {
        View v = this;
        v.setPadding(2,5,2,5);
    }

}

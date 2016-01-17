package bmb.rns.com.bookworm_app.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import bmb.rns.com.bookworm_app.R;

/**
 * Created by touchy on 17/1/16.
 */

public class TypeFaceButton extends AppCompatButton {

    public TypeFaceButton(Context context) {
        super(context);
    }

    public TypeFaceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        int[] attrs_list = {R.attr.typeface};
        TypedArray ta = context.obtainStyledAttributes(attrs, attrs_list);
        String typeface = ta.getString(0);
        init(context, typeface);
    }

    public TypeFaceButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int[] attrs_list = {R.attr.typeface};
        TypedArray ta = context.obtainStyledAttributes(attrs, attrs_list);
        String typeface = ta.getString(0);
        init(context, typeface);
    }

    protected void init(Context context, String typeface) {
        if (isInEditMode()) return;
        setTypeface(TypeFace.get(context, typeface));
    }
}
package in.rohansarkar.adhuri.View.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class TextViewAllura extends android.support.v7.widget.AppCompatTextView {

    public TextViewAllura(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Allura.otf"));
    }

}
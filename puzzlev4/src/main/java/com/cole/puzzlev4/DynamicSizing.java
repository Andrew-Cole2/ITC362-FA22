package com.cole.puzzlev4;

import android.util.TypedValue;
import android.view.View.MeasureSpec;
import android.widget.TextView;

public class DynamicSizing {
    public static final int MAX_FONT_SIZE = 200;
    public static final int MIN_FONT_SIZE = 1;

    public static int setFontSizeToFitInView( TextView textView ) {
        int fontSize = MAX_FONT_SIZE;
        textView.setTextSize( TypedValue.COMPLEX_UNIT_SP, fontSize );
        textView.measure( MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED );
        int lines = textView.getLineCount( );
        if( lines > 0 ) {
            while( lines != 1 && fontSize >= MIN_FONT_SIZE + 2 ) {
                fontSize--;
                textView.setTextSize( TypedValue.COMPLEX_UNIT_SP, fontSize );
                textView.measure( MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED );
                lines = textView.getLineCount( );
            }
            textView.setTextSize( TypedValue.COMPLEX_UNIT_SP, --fontSize );
        }
        return fontSize;
    }
}

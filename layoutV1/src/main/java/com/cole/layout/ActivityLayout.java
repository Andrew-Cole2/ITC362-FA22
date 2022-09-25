package com.cole.layout;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class ActivityLayout extends ConstraintLayout{

    private Button button1;
    private Button button2;
    private Button button3;
    private TextView textView;

    public ActivityLayout(@NonNull Context context) {
        super(context);

        // Setup View area
        setId(View.generateViewId());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);
        setBackgroundColor(getResources().getColor(R.color.viewBackground));

        // Setup constraint set
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);

        // Setup Buttons and Click Handlers
        ButtonHandler buttonHandler = new ButtonHandler();
        button1 = addButton(context, constraintSet, getResources().getString(R.string.button1_text));
        button1.setOnClickListener(buttonHandler);
        button2 = addButton(context, constraintSet, getResources().getString(R.string.button2_text));
        button2.setOnClickListener(buttonHandler);
        button3 = addButton(context, constraintSet, getResources().getString(R.string.button3_text));
        button3.setOnClickListener(buttonHandler);

        // Setup TextView
        textView = new TextView(context);
        textView.setId(View.generateViewId());
        textView.setBackground(new ColorDrawable(getResources().getColor(R.color.buttonBackground)));
        textView.setWidth(context.getDisplay().getWidth());
        textView.setHeight(context.getDisplay().getHeight()/4);
        textView.setGravity(Gravity.CENTER);
        addView(textView);
        constraintSet.constrainWidth(textView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(textView.getId(), ConstraintSet.WRAP_CONTENT);

        // Create Layout Constraints
        constraintSet.connect(button1.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 50);
        constraintSet.connect(button1.getId(), ConstraintSet.BOTTOM, button2.getId(), ConstraintSet.TOP, 20);
        constraintSet.connect(button2.getId(), ConstraintSet.TOP, button1.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(button2.getId(), ConstraintSet.BOTTOM, button3.getId(), ConstraintSet.TOP, 20);
        constraintSet.connect(button3.getId(), ConstraintSet.TOP, button2.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(button3.getId(), ConstraintSet.BOTTOM, textView.getId(), ConstraintSet.TOP, 20);
        constraintSet.connect(textView.getId(), ConstraintSet.TOP, button3.getId(), ConstraintSet.BOTTOM,50);

        // Render Set
        constraintSet.applyTo(this);
        // TextView visibility must be set after constraintSet is applied or visibility state will be reset
        textView.setVisibility(View.INVISIBLE);
    }

    public Button addButton(Context context, ConstraintSet constraintSet, String buttonText) {
        Button button = new Button(context);
        button.setId(View.generateViewId());
        button.setText(buttonText);
        button.setBackground(new ColorDrawable(getResources().getColor(R.color.buttonBackground)));
        button.setWidth(context.getDisplay().getWidth());
        addView(button);

        constraintSet.constrainWidth(button.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(button.getId(), ConstraintSet.WRAP_CONTENT);
        return button;
    }



    private class ButtonHandler implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if (view.getId() == button1.getId()) {
                textView.setText(R.string.button1_desc);
            }
            if (view.getId() == button2.getId()) {
                textView.setText(R.string.button2_desc);
            }
            if (view.getId() == button3.getId()) {
                textView.setText(R.string.button3_desc);
            }
            textView.setVisibility(View.VISIBLE);
        }
    }
}

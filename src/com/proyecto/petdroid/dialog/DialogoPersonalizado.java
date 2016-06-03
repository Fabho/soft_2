package com.proyecto.petdroid.dialog;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyecto.petdroid.R;

public class DialogoPersonalizado extends AlertDialog.Builder{

	private View mDialogView;	
	private TextView mTitle;
	private ImageView mIcon;
	private View mDivider;
	
    public DialogoPersonalizado(Context context) {
        super(context);

        mDialogView = View.inflate(context, R.layout.qustom_dialog_layout, null);
        setView(mDialogView);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
        mDivider = mDialogView.findViewById(R.id.titleDivider);
	}

    public DialogoPersonalizado setDividerColor(int color) {
    	mDivider.setBackgroundColor(color);
    	return this;
    }
 
    @Override
    public DialogoPersonalizado setTitle(CharSequence text) {
        mTitle.setText(text);
        return this;
    }

    public DialogoPersonalizado setTitleColor(int color) {
    	mTitle.setTextColor(color);
    	return this;
    }

    @Override
    public DialogoPersonalizado setIcon(int drawableResId) {
        mIcon.setImageResource(drawableResId);
        return this;
    }

    @Override
    public DialogoPersonalizado setIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
        return this;
    }
    
    public DialogoPersonalizado setCustomView(View customView) {
    	((FrameLayout)mDialogView.findViewById(R.id.customPanel)).addView(customView);
    	return this;
    }
    
    @Override
    public AlertDialog show() {
    	if (mTitle.getText().equals("")) mDialogView.findViewById(R.id.topPanel).setVisibility(View.GONE);
    	return super.show();
    }

}

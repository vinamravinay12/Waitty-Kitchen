package com.waitty.kitchen.utility;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import com.waitty.kitchen.R;

public class squareimage_diffrentsize extends androidx.appcompat.widget.AppCompatImageView {

    Context context;
    String imageType;
    int normalWidth, normalHeight;

    public squareimage_diffrentsize(Context context) {
        super(context);
    }

    public squareimage_diffrentsize(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RoundCornersImageView);
        imageType = a.getString(R.styleable.RoundCornersImageView_image_type);
    }

    public squareimage_diffrentsize(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        normalWidth = widthMeasureSpec;
        normalHeight = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (imageType.equals("0")) {
            setMeasuredDimension(normalWidth, normalHeight);
        } else if (imageType.equals("1")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, width);
        } else if (imageType.equals("2")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width / 2);
        } else if (imageType.equals("3")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width * 2 / 3);
        } else if (imageType.equals("4")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width * 3 / 2);
        } else if (imageType.equals("5")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width * 5 / 4);
            Log.d("UBABKJBKBKJ", width + "");
        }
    }

    public void setImageType(String tem) {
        imageType = tem;
        if (imageType.equals("0")) {
            setMeasuredDimension(normalWidth, normalHeight);
        } else if (imageType.equals("1")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, width);
        } else if (imageType.equals("2")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width / 2);
        } else if (imageType.equals("3")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width * 2 / 3);
        } else if (imageType.equals("4")) {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, (int) width * 3 / 2);
        } else if (imageType.equals("5")) {
            int width = getMeasuredWidth();
            Log.d("UBABKJBKBKJ", width + "");
            setMeasuredDimension(width, (int) width * 5 / 4);
        }
    }

}

package com.wind.frosty.composites;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.card.MaterialCardView;
import com.wind.frosty.R;

public class CardComponent extends MaterialCardView {
    ImageView bkgImage;
    LinearLayout layout;
//    ImageView smallImage;
    CircleImageView smallImage;
    TextView title;

    public CardComponent(Context context) {
        super(context);
    }

    public CardComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public CardComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void initView(Context context,AttributeSet attrs){
        bkgImage=new ImageView(context);
        layout=new LinearLayout(context);
        smallImage=new CircleImageView(context);
        title=new TextView(context);

        //从values/attrs获取自定义属性
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CardComponent);

        //设置卡片背景
        Drawable cardBkg=typedArray.getDrawable(R.styleable.CardComponent_backgrd);
        float opacity=typedArray.getDimension(R.styleable.CardComponent_bkgAlpha,0.5f);
        MaterialCardView.LayoutParams bkgParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        bkgImage.setBackground(cardBkg);
        bkgImage.setAlpha(opacity);
        bkgImage.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(bkgImage,bkgParams);

        //使用linearlayout包含另外两个控件
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        addView(layout,layoutParams);

        //设置circle image
        int size=(int)typedArray.getDimension(R.styleable.CardComponent_cirSize,0);
        int mtop=(int)typedArray.getDimension(R.styleable.CardComponent_cirImgMTop,0);
        int mbot=(int)typedArray.getDimension(R.styleable.CardComponent_cirImgMBot,0);
        Drawable cirSrc=typedArray.getDrawable(R.styleable.CardComponent_cirImg);
        LinearLayout.LayoutParams cirImgParams=new LinearLayout.LayoutParams(size,size);
        cirImgParams.gravity= Gravity.CENTER_HORIZONTAL;
        cirImgParams.topMargin=mtop;
        cirImgParams.bottomMargin=mbot;
        smallImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        smallImage.setImageDrawable(cirSrc);
        layout.addView(smallImage,cirImgParams);

        //设置textview
        String title_text=typedArray.getString(R.styleable.CardComponent_title);
        LinearLayout.LayoutParams textParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textParams.gravity=Gravity.CENTER_HORIZONTAL;
        title.setText(title_text);
        title.setEms(1);//竖直文字
        layout.addView(title,textParams);


    }
}

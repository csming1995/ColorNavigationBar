package com.csming.colorfulnavigation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Created by csming on 2019/3/16.
 */
class ColorfulNavigationItem extends FrameLayout implements View.OnClickListener {

    private ColorfulNavigation.Item item;
    private ColorfulNavigation.OnItemSelectedListener onItemSelectedListener;

    private ImageView mIvIcon;
    private TextView mTvTitle;

    public ColorfulNavigationItem(Context context) {
        this(context, null);
    }

    public ColorfulNavigationItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorfulNavigationItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View containerView = LayoutInflater.from(context).inflate(R.layout.color_navigation_item, this);
        mIvIcon = containerView.findViewById(R.id.iv_icon);
        mTvTitle = containerView.findViewById(R.id.tv_title);
        setOnClickListener(this);
        setWillNotDraw(false);

    }

    public void setItem(ColorfulNavigation.Item item) {
        this.item = item;
        mIvIcon.setImageResource(item.getIcon());
        if (item.getTitle() != null) {
            mTvTitle.setText(item.getTitle());
            mTvTitle.setVisibility(VISIBLE);
        }
    }

    public void setOnSelectedListener(final ColorfulNavigation.OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public void onClick(View view) {
        if (onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(item);
        }
    }
}

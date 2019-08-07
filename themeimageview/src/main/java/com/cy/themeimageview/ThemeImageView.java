package com.cy.themeimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

public class ThemeImageView extends ImageView {
    private int fixedColor;

    private TypedArray typedArray;

    public void setNeedTheme(boolean needTheme) {
        this.needTheme = needTheme;
    }

    private boolean needTheme = false;

    private boolean needThemeInNormal = false;

    private int selectedColor;
    private final static int COLOR_NO = -31812;

    public ThemeImageView(Context context) {
        super(context);
        initView();
    }

    public ThemeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ThemeImageView);
        initView();
    }

    public ThemeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ThemeImageView);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ThemeImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ThemeImageView);
        initView();
    }

    private void initView() {
        if (typedArray != null) {
            needTheme = typedArray.getBoolean(R.styleable.ThemeImageView_needTheme, false);
            needThemeInNormal = typedArray.getBoolean(R.styleable.ThemeImageView_needThemeInNormal, false);
            fixedColor = typedArray.getColor(R.styleable.ThemeImageView_fixedColor, COLOR_NO);
            selectedColor = typedArray.getColor(R.styleable.ThemeImageView_selectColor, COLOR_NO);

            if (needThemeInNormal) {
                this.setColorFilter(ContextCompat.getColor(getContext(), R.color.ThemeImageView_ThemeColor), PorterDuff.Mode.SRC_IN);
            }
            if (fixedColor != COLOR_NO) {
                this.setColorFilter(fixedColor, PorterDuff.Mode.SRC_IN);
            }
        }
    }

    public void setFixedColor(@ColorInt int fixedColor) {
        this.fixedColor = fixedColor;
        if (fixedColor != COLOR_NO && !needTheme) {
            this.setColorFilter(fixedColor, PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (!needTheme) {
            return;
        }

        if (pressed) {
            if (selectedColor != COLOR_NO) {
                this.setColorFilter(selectedColor, PorterDuff.Mode.SRC_IN);
            } else {
                this.setColorFilter(ContextCompat.getColor(getContext(), R.color.ThemeImageView_ThemeColor), PorterDuff.Mode.SRC_IN);
            }
        } else if (!this.isSelected() && !needThemeInNormal) {
            this.clearColorFilter();
        } else if (fixedColor != COLOR_NO) {
            this.setColorFilter(fixedColor, PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (!needTheme) {
            return;
        }

        if (selected) {
            if (selectedColor != COLOR_NO) {
                this.setColorFilter(selectedColor, PorterDuff.Mode.SRC_IN);
            } else {
                this.setColorFilter(ContextCompat.getColor(getContext(), R.color.ThemeImageView_ThemeColor), PorterDuff.Mode.SRC_IN);
            }
        } else if (!needThemeInNormal) {
            this.clearColorFilter();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!needTheme) {
            return;
        }

        if (!enabled) {
            if (selectedColor != COLOR_NO) {
                this.setColorFilter(selectedColor, PorterDuff.Mode.SRC_IN);
            } else {
                this.setColorFilter(ContextCompat.getColor(getContext(), R.color.ThemeImageView_ThemeColor), PorterDuff.Mode.SRC_IN);
            }
        } else if (!needThemeInNormal) {
            this.clearColorFilter();
        }
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (!needTheme) {
            return;
        }

        if (gainFocus) {
            if (selectedColor != COLOR_NO) {
                this.setColorFilter(selectedColor, PorterDuff.Mode.SRC_IN);
            } else {
                this.setColorFilter(ContextCompat.getColor(getContext(), R.color.ThemeImageView_ThemeColor), PorterDuff.Mode.SRC_IN);
            }
        } else if (!needThemeInNormal) {
            this.clearColorFilter();
        } else if (fixedColor != COLOR_NO) {
            this.setColorFilter(fixedColor, PorterDuff.Mode.SRC_IN);
        }
    }
}

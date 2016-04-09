package com.beardedhen.androidbootstrap;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;
import com.beardedhen.androidbootstrap.api.view.BootstrapBrandView;
import com.beardedhen.androidbootstrap.api.view.BootstrapSizeView;
import com.beardedhen.androidbootstrap.utils.DimenUtils;

/**
 * See <a href="http://getbootstrap.com/components/#badges>http://getbootstrap.com/components/#badges</a>
 */
public class BootstrapBadge extends ImageView implements BootstrapSizeView, BootstrapBrandView {

    private int badgeCount;
    private int size;
    private boolean allowZeroValue;
    private boolean insideContainer;
    private BootstrapBrand bootstrapBrand = DefaultBootstrapBrand.REGULAR;
    private float bootstrapSize;
    private Drawable badgeDrawable;

    public BootstrapBadge(Context context) {
        super(context);
        init(null);
    }

    public BootstrapBadge(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BootstrapBadge(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BootstrapBadge);

        try {
            int sizeOrdinal = a.getInt(R.styleable.BootstrapBadge_bootstrapSize, -1);

            allowZeroValue = a.getBoolean(R.styleable.BootstrapBadge_allowZeroValue, false);
            bootstrapSize = DefaultBootstrapSize.fromAttributeValue(sizeOrdinal)
                                                .scaleFactor();
        }
        finally {
            a.recycle();
        }

        size = (int) DimenUtils.pixelsFromDpResource(getContext(),
                                                     R.dimen.bootstrap_badge_default_size);
        updateBootstrapState();
    }

    private void updateBootstrapState() {
        badgeDrawable = BootstrapDrawableFactory.createBadgeDrawable(getContext(), bootstrapBrand,
                                                                     (int) (size * bootstrapSize),
                                                                     (int) (size * bootstrapSize),
                                                                     badgeCount, allowZeroValue,
                                                                     insideContainer);

        if (Build.VERSION.SDK_INT >= 16) {
            setBackground(badgeDrawable);
        }
        else {
            setBackgroundDrawable(badgeDrawable);
        }
    }

    public void setBadgeCount(int badgeCount) {
        this.badgeCount = badgeCount;
        updateBootstrapState();
    }

    public boolean isAllowZeroValue() {
        return allowZeroValue;
    }

    public void setAllowZeroValue(boolean allowZeroValue) {
        this.allowZeroValue = allowZeroValue;
        updateBootstrapState();
    }

    public int getBadgeCount() {
        return badgeCount;
    }

    public Drawable getBadgeDrawable() {
        return badgeDrawable;
    }






    public void setBootstrapBrand(BootstrapBrand bootstrapBrand, boolean insideContainer) {
        this.insideContainer = insideContainer;
        setBootstrapBrand(bootstrapBrand);
    }

    @Override
    public void setBootstrapBrand(@NonNull BootstrapBrand bootstrapBrand) {
        this.bootstrapBrand = bootstrapBrand;
        updateBootstrapState();
    }

    @NonNull
    @Override
    public BootstrapBrand getBootstrapBrand() {
        return bootstrapBrand;
    }

    @Override
    public void setBootstrapSize(DefaultBootstrapSize bootstrapSize) {
        this.bootstrapSize = bootstrapSize.scaleFactor();
        updateBootstrapState();
    }

    @Override
    public void setBootstrapSize(float bootstrapSize) {
        this.bootstrapSize = bootstrapSize;
        updateBootstrapState();
    }

    @Override
    public float getBootstrapSize() {
        return bootstrapSize;
    }
}
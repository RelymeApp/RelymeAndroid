package lahds.relyme.UserInterface.EmojiView.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

import androidx.annotation.CallSuper;
import androidx.annotation.DimenRes;
import androidx.annotation.Px;
import androidx.appcompat.widget.AppCompatCheckBox;

import lahds.relyme.UserInterface.EmojiView.EmojiManager;
import lahds.relyme.R;
import lahds.relyme.UserInterface.EmojiView.Utils.Utils;

public class EmojiCheckbox extends AppCompatCheckBox {
    private float emojiSize;

    public EmojiCheckbox(final Context context) {
        this(context, null);
    }

    public EmojiCheckbox(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        final float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;

        if (attrs == null) {
            emojiSize = defaultEmojiSize;
        } else {
            final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmojiCheckbox);

            try {
                emojiSize = a.getDimension(R.styleable.EmojiCheckbox_emojiSize, defaultEmojiSize);
            } finally {
                a.recycle();
            }
        }
    }

    public EmojiCheckbox(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        final float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;

        if (attrs == null) {
            emojiSize = defaultEmojiSize;
        } else {
            final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmojiCheckbox);

            try {
                emojiSize = a.getDimension(R.styleable.EmojiCheckbox_emojiSize, defaultEmojiSize);
            } finally {
                a.recycle();
            }
        }
    }

    @Override
    @CallSuper
    public void setText(final CharSequence rawText, final BufferType type) {
        if (isInEditMode() || !EmojiManager.isInstalled()) {
            super.setText(rawText, type);
            return;
        }

        final CharSequence text = rawText == null ? "" : rawText;
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        //final float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;
        if (EmojiManager.isInstalled())
            EmojiManager.getInstance().replaceWithImages(getContext(), this, spannableStringBuilder,
                    emojiSize>0 ? emojiSize : Utils.getDefaultEmojiSize(fontMetrics), fontMetrics);
        super.setText(spannableStringBuilder, type);
    }

    public float getEmojiSize() {
        return emojiSize;
    }

    public final void setEmojiSize(@Px final int pixels) {
        setEmojiSize(pixels, true);
    }

    public final void setEmojiSize(@Px final int pixels, final boolean shouldInvalidate) {
        emojiSize = pixels;

        if (shouldInvalidate) {
            setText(getText().toString());
        }
    }

    public final void setEmojiSizeRes(@DimenRes final int res) {
        setEmojiSizeRes(res, true);
    }

    public final void setEmojiSizeRes(@DimenRes final int res, final boolean shouldInvalidate) {
        setEmojiSize(getResources().getDimensionPixelSize(res), shouldInvalidate);
    }
}
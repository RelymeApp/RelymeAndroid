package lahds.relyme.UserInterface.EmojiView.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;

import androidx.annotation.CallSuper;
import androidx.annotation.DimenRes;
import androidx.annotation.Px;
import androidx.appcompat.widget.AppCompatEditText;

import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import lahds.relyme.UserInterface.EmojiView.EmojiManager;
import lahds.relyme.UserInterface.EmojiView.EmojiUtils;
import lahds.relyme.R;
import lahds.relyme.UserInterface.EmojiView.Emoji.Emoji;
import lahds.relyme.UserInterface.EmojiView.Utils.Utils;

public class EmojiEditText extends AppCompatEditText {
    private float emojiSize;
    PopupInterface popupInterface;

    public EmojiEditText(final Context context) {
        this(context, null);
    }

    public EmojiEditText(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        final float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;

        if (attrs == null) {
            emojiSize = defaultEmojiSize;
        } else {
            final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmojiEditText);

            try {
                emojiSize = a.getDimension(R.styleable.EmojiEditText_emojiSize, defaultEmojiSize);
            } finally {
                a.recycle();
            }
        }

        setText(getText());
    }

    @Override
    @CallSuper
    protected void onTextChanged(final CharSequence text, final int start, final int lengthBefore, final int lengthAfter) {
        final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();

        if (EmojiManager.isInstalled())
            EmojiManager.getInstance().replaceWithImages(getContext(), this, getText(),
                    emojiSize>0 ? emojiSize : Utils.getDefaultEmojiSize(fontMetrics), fontMetrics);
    }

    @CallSuper
    public void backspace() {
        EmojiUtils.backspace(this);
    }

    @CallSuper
    public void input(final Emoji emoji) {
        EmojiUtils.input(this, emoji);
        if (listener != null) listener.onInput(this, emoji);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        try {
            if (popupInterface != null && keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && hasFocus()) {
                InputMethodManager mgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (popupInterface.isShowing()) {
                    mgr.hideSoftInputFromWindow(this.getWindowToken(), 0);
                    popupInterface.onBackPressed();
                    return true;
                }
            }
        }catch (Exception ignore){}

        return super.onKeyPreIme(keyCode,event);
    }

    OnInputEmojiListener listener;

    public void setOnInputEmojiListener(OnInputEmojiListener listener) {
        this.listener = listener;
    }

    public void removeOnInputEmojiListener() {
        this.listener = null;
    }

    public interface OnInputEmojiListener {
        void onInput(EmojiEditText editText, Emoji emoji);
    }

    public float getEmojiSize() {
        return emojiSize;
    }

    /**
     * sets the emoji size in pixels and automatically invalidates the text and renders it with the new size
     */
    public final void setEmojiSize(@Px final int pixels) {
        setEmojiSize(pixels, true);
    }

    /**
     * sets the emoji size in pixels and automatically invalidates the text and renders it with the new size when {@code shouldInvalidate} is true
     */
    public final void setEmojiSize(@Px final int pixels, final boolean shouldInvalidate) {
        emojiSize = pixels;

        if (shouldInvalidate) {
            if (getText()!=null) {
                setText(getText().toString());
            }
        }
    }

    /**
     * sets the emoji size in pixels with the provided resource and automatically invalidates the text and renders it with the new size
     */
    public final void setEmojiSizeRes(@DimenRes final int res) {
        setEmojiSizeRes(res, true);
    }

    /**
     * sets the emoji size in pixels with the provided resource and invalidates the text and renders it with the new size when {@code shouldInvalidate} is true
     */
    public final void setEmojiSizeRes(@DimenRes final int res, final boolean shouldInvalidate) {
        setEmojiSize(getResources().getDimensionPixelSize(res), shouldInvalidate);
    }
}

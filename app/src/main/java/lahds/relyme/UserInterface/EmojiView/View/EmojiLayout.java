package lahds.relyme.UserInterface.EmojiView.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class EmojiLayout extends EmojiBase {

    public EmojiLayout(Context context) {
        super(context);
    }

    public EmojiLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {

        public LayoutParams(int left, int top, int width, int height) {
            super(width, height);
            this.width = width;
            this.height = height;
            this.leftMargin = left;
            this.topMargin = top;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.width = width;
            this.height = height;
        }


        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(ViewGroup.LayoutParams lp) {
            super(lp);
        }
    }

}

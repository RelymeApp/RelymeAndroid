package lahds.relyme.UserInterface.EmojiView.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import lahds.relyme.UserInterface.EmojiView.EmojiManager;
import lahds.relyme.UserInterface.EmojiView.Listener.FindVariantListener;
import lahds.relyme.UserInterface.EmojiView.Utils.Utils;

@SuppressLint("ViewConstructor")
public class EmojiRecyclerView extends RecyclerView {
    FindVariantListener variantListener;

    public EmojiRecyclerView(@NonNull Context context, FindVariantListener variantListener) {
        super(context);
        this.variantListener = variantListener;
        GridLayoutManager lm = new GridLayoutManager(context, Utils.getGridCount(context));
        this.setLayoutManager(lm);
        Utils.forceLTR(this);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public EmojiRecyclerView(@NonNull Context context, FindVariantListener variantListener, LayoutManager layoutManager) {
        super(context);
        this.variantListener = variantListener;
        this.setLayoutManager(layoutManager);

        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (variantListener.findVariant() != null && variantListener.findVariant().onTouch(event, this))
            return true;
        return super.dispatchTouchEvent(event);
    }
}

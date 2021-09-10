package lahds.relyme.UserInterface.EmojiView.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import lahds.relyme.UserInterface.EmojiView.Listener.FindVariantListener;
import lahds.relyme.UserInterface.EmojiView.Utils.Utils;

@SuppressLint("ViewConstructor")
public class EmojiSingleRecyclerView extends RecyclerView {
    FindVariantListener variantListener;

    public EmojiSingleRecyclerView(@NonNull Context context, FindVariantListener variantListener) {
        super(context);
        this.variantListener = variantListener;
        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(Utils.getGridCount(context), StaggeredGridLayoutManager.VERTICAL);
        this.setLayoutManager(lm);
        Utils.forceLTR(this);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    boolean skipTouch = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (variantListener.findVariant() != null && variantListener.findVariant().onTouch(event, this))
            return true;
        return super.dispatchTouchEvent(event);
    }
}

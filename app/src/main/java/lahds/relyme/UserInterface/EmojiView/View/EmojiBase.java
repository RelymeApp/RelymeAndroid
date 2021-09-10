package lahds.relyme.UserInterface.EmojiView.View;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;

import lahds.relyme.UserInterface.EmojiView.Utils.Utils;

public class EmojiBase extends FrameLayout {

    public EmojiBase(Context context) {
        super(context);
        Utils.forceLTR(this);
    }

    public EmojiBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        Utils.forceLTR(this);
    }

    public EmojiBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Utils.forceLTR(this);
    }

    EditText editText;
    PopupInterface popupInterface;

    public void setEditText(EditText editText) {
        this.editText = editText;

        if (popupInterface!=null && editText instanceof EmojiEditText){
            ((EmojiEditText) editText).popupInterface = popupInterface;
        }
    }

    public EditText getEditText() {
        return editText;
    }

    public void setPopupInterface(PopupInterface popupInterface) {
        this.popupInterface = popupInterface;

        if (editText!=null && editText instanceof EmojiEditText){
            ((EmojiEditText) editText).popupInterface = popupInterface;
        }
    }

    public PopupInterface getPopupInterface() {
        return popupInterface;
    }

    public void dismiss() {
    }

    protected void addItemDecoration(RecyclerView.ItemDecoration decoration) {
    }

    protected void setScrollListener(RecyclerView.OnScrollListener listener) {
    }

    protected void setPageChanged(ViewPager.OnPageChangeListener listener) {
    }

    protected void refresh() {
    }

    public void setPageIndex(int Position) {
    }

    public int getPageIndex() {
        return 0;
    }

    protected void onShow() {
    }

}

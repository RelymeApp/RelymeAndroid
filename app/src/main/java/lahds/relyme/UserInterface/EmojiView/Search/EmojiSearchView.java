package lahds.relyme.UserInterface.EmojiView.Search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import lahds.relyme.UserInterface.EmojiView.EmojiManager;
import lahds.relyme.UserInterface.EmojiView.EmojiTheme;
import lahds.relyme.R;
import lahds.relyme.UserInterface.EmojiView.Emoji.Emoji;
import lahds.relyme.UserInterface.EmojiView.Listener.FindVariantListener;
import lahds.relyme.UserInterface.EmojiView.Listener.OnEmojiActions;
import lahds.relyme.UserInterface.EmojiView.Shared.VariantEmoji;
import lahds.relyme.UserInterface.EmojiView.Utils.Utils;
import lahds.relyme.UserInterface.EmojiView.View.EmojiBase;
import lahds.relyme.UserInterface.EmojiView.View.EmojiEditText;
import lahds.relyme.UserInterface.EmojiView.View.EmojiImageView;
import lahds.relyme.UserInterface.EmojiView.View.EmojiRecyclerView;
import lahds.relyme.UserInterface.EmojiView.View.EmojiView;
import lahds.relyme.UserInterface.EmojiView.View.SearchViewInterface;
import lahds.relyme.UserInterface.EmojiView.View.SingleEmojiView;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ViewConstructor")
public class EmojiSearchView extends FrameLayout implements SearchViewInterface {

    EmojiTheme theme;
    protected EmojiBase base;
    boolean showing;
    DataAdapter<Emoji> dataAdapter;
    protected RecyclerView recyclerView;
    protected AppCompatEditText editText;
    protected AppCompatImageView backButton;
    protected AppCompatTextView noResultTextView;

    public EmojiSearchView(Context context, EmojiBase emojiView) {
        super(context);
        if (!EmojiManager.isEmojiView(emojiView))
            throw new RuntimeException("EmojiView must be an instance of EmojiView or SingleEmojiView.");
        this.theme = EmojiManager.getEmojiViewTheme();
        this.base = emojiView;
        setDataAdapter(new SimpleEmojiDataAdapter(context));
    }

    public @NonNull
    DataAdapter<Emoji> getDataAdapter() {
        return dataAdapter;
    }

    public void setDataAdapter(@NonNull DataAdapter<Emoji> dataAdapter) {
        if (this.dataAdapter != null) this.dataAdapter.destroy();
        this.dataAdapter = dataAdapter;
        this.dataAdapter.init();
    }

    public EmojiTheme getTheme() {
        return theme;
    }

    /**
     * Theme needed properties :
     * - CategoryColor (BackgroundColor)
     * - TitleTypeface (EditTextTypeface)
     * - TitleColor (EditTextTextColor)
     * - DefaultColor (EditTextHintColor)
     */
    public void setTheme(EmojiTheme theme) {
        this.theme = theme;
        this.setBackgroundColor(theme.getCategoryColor());
        if (editText != null && editText.getParent() != null) {
            editText.setTypeface(theme.getTitleTypeface());
            editText.setHintTextColor(theme.getDefaultColor());
            editText.setTextColor(theme.getTitleColor());
            editText.setBackgroundColor(Color.TRANSPARENT);
            noResultTextView.setTextColor(theme.getDefaultColor());
            noResultTextView.setTypeface(theme.getTitleTypeface());

            Drawable dr = AppCompatResources.getDrawable(getContext(), R.drawable.arrow_back);
            DrawableCompat.setTint(DrawableCompat.wrap(dr), EmojiManager.getEmojiViewTheme().getDefaultColor());
            backButton.setImageDrawable(dr);
        }
    }

    @Override
    public int getSearchViewHeight() {
        return Utils.dp(getContext(), 98);
    }

    @Override
    public void show() {
        if (showing) return;
        showing = true;
        init();
    }

    @Override
    public void hide() {
        showing = false;
        removeAllViews();
    }

    @Override
    public boolean isShowing() {
        return showing;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public EditText getSearchTextField() {
        return editText;
    }

    protected void searched(boolean hasResult) {
        if (noResultTextView != null)
            noResultTextView.setVisibility(hasResult ? GONE : VISIBLE);
    }

    protected void init() {
        removeAllViews();
        this.setBackgroundColor(theme.getCategoryColor());

        recyclerView = new EmojiRecyclerView(getContext(), (FindVariantListener) base,
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        FrameLayout.LayoutParams lp_rv = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, Utils.dp(getContext(), 38));
        lp_rv.topMargin = Utils.dp(getContext(), 8);
        this.addView(recyclerView, lp_rv);
        recyclerView.setAdapter(createAdapter());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if (position == 0) {
                    outRect.left = Utils.dp(parent.getContext(), 16);
                } else {
                    outRect.left = Utils.dp(parent.getContext(), 6);
                }
                if (position == parent.getAdapter().getItemCount() - 1)
                    outRect.right = Utils.dp(parent.getContext(), 16);
            }
        });

        editText = new CustomEditText(getContext());
        editText.setHint("Search");
        editText.setTypeface(theme.getTitleTypeface());
        editText.setTextSize(18);
        editText.setHintTextColor(theme.getDefaultColor());
        editText.setTextColor(theme.getTitleColor());
        editText.setSingleLine();
        editText.setBackgroundColor(Color.TRANSPARENT);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-1, -1);
        lp.leftMargin = Utils.dp(getContext(), 48);
        lp.rightMargin = Utils.dp(getContext(), 48);
        lp.bottomMargin = Utils.dp(getContext(), 0);
        lp.topMargin = Utils.dp(getContext(), 46);
        lp.gravity = Gravity.BOTTOM;
        this.addView(editText, lp);

        final TextWatcher textWatcherSearchListener = new TextWatcher() {
            final android.os.Handler handler = new android.os.Handler();
            Runnable runnable;

            public void onTextChanged(final CharSequence s, int start, final int before, int count) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        searchFor(s.toString());
                    }
                };
                handler.postDelayed(runnable, 100);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        };
        editText.addTextChangedListener(textWatcherSearchListener);

        noResultTextView = new AppCompatTextView(getContext());
        this.addView(noResultTextView, lp_rv);
        noResultTextView.setVisibility(GONE);
        noResultTextView.setTextSize(18);
        noResultTextView.setText(R.string.no_emoji_found);
        noResultTextView.setTypeface(theme.getTitleTypeface());
        noResultTextView.setGravity(Gravity.CENTER);
        noResultTextView.setTextColor(theme.getDefaultColor());

        backButton = new AppCompatImageView(getContext());
        FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(Utils.dp(getContext(), 26), -1);
        lp2.leftMargin = Utils.dp(getContext(), 13);
        lp2.bottomMargin = Utils.dp(getContext(), 8);
        lp2.topMargin = Utils.dp(getContext(), 54);
        lp2.gravity = Gravity.BOTTOM;
        this.addView(backButton, lp2);
        Utils.setClickEffect(backButton, true);

        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText instanceof CustomEditText) {
                    ((CustomEditText) editText).reload();
                } else {
                    base.getPopupInterface().reload();
                }
                base.getPopupInterface().show();
            }
        });

        Drawable dr = AppCompatResources.getDrawable(getContext(), R.drawable.arrow_back);
        DrawableCompat.setTint(DrawableCompat.wrap(dr), EmojiManager.getEmojiViewTheme().getDefaultColor());
        backButton.setImageDrawable(dr);
    }

    protected void searchFor(String value) {
        if (recyclerView.getAdapter() == null) return;
        ((UISearchAdapter) recyclerView.getAdapter()).searchFor(value);
    }

    protected OnEmojiActions findActions() {
        if (base instanceof EmojiView) {
            return ((EmojiView) base).getInnerEmojiActions();
        } else if (base instanceof SingleEmojiView) {
            return ((SingleEmojiView) base).getInnerEmojiActions();
        }
        return null;
    }

    protected VariantEmoji findVariant() {
        if (base instanceof EmojiView) {
            return ((EmojiView) base).getVariantEmoji();
        } else if (base instanceof SingleEmojiView) {
            return ((SingleEmojiView) base).getVariantEmoji();
        }
        return EmojiManager.getVariantEmoji();
    }

    private RecyclerView.Adapter<?> createAdapter() {
        return new SearchAdapter(this);
    }


    protected static class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements UISearchAdapter {
        EmojiSearchView searchView;
        List<Emoji> list;

        public SearchAdapter(EmojiSearchView searchView) {
            super();
            this.searchView = searchView;
            list = new ArrayList<>();
            searchFor("");
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            FrameLayout frameLayout = new FrameLayout(parent.getContext());
            EmojiImageView emojiView = new EmojiImageView(parent.getContext());
            int cw = Utils.dp(parent.getContext(), 38);
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(cw, cw));
            frameLayout.addView(emojiView);

            int dp6 = Utils.dp(parent.getContext(), 6);
            emojiView.setPadding(dp6, dp6, dp6, dp6);
            return new ViewHolder(frameLayout);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            FrameLayout frameLayout = (FrameLayout) viewHolder.itemView;
            final EmojiImageView emojiView = (EmojiImageView) frameLayout.getChildAt(0);

            Emoji emoji = list.get(position);
            emojiView.setEmoji(searchView.findVariant().getVariant(emoji));
            emojiView.setOnEmojiActions(searchView.findActions(), true);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void searchFor(String value) {
            list.clear();
            if (value.trim().isEmpty()) {
                list.addAll(EmojiManager.getRecentEmoji().getRecentEmojis());
            } else {
                list.addAll(searchView.getDataAdapter().searchFor(value));
            }
            searchView.searched(!list.isEmpty());
            notifyDataSetChanged();
        }

        protected static class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }

    protected static class CustomEditText extends EmojiEditText {

        boolean req = false;

        public CustomEditText(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.clearFocus();

            if (((EmojiSearchView) getParent()).base.getEditText() != null)
                ((EmojiSearchView) getParent()).base.getEditText().requestFocus();
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();

            if (((EmojiSearchView) getParent()).base.getEditText() != null)
                ((EmojiSearchView) getParent()).base.getEditText().clearFocus();
        }

        @Override
        protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
            if (focused)
                req = false;

            if (!req && !focused && getParent() != null)
                reload();
        }

        public void reload() {
            ((EmojiSearchView) getParent()).base.getPopupInterface().reload();
            req = true;
        }

        @Override
        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
            if (getParent() != null && !req && keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && hasFocus()) {
                req = true;
                InputMethodManager mgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (mgr != null) {
                    mgr.hideSoftInputFromWindow(this.getWindowToken(), 0);
                }
                if (((EmojiSearchView) getParent()).base.getPopupInterface() != null) {
                    ((EmojiSearchView) getParent()).base.getPopupInterface().reload();
                }
                return true;
            }
            if (keyCode == KeyEvent.KEYCODE_BACK && req) return true;
            return super.onKeyPreIme(keyCode, event);
        }
    }

}

/*
 * Copyright (C) 2020 - Amir Hossein Aghajari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


package lahds.relyme.UserInterface.EmojiView.View;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;

import lahds.relyme.UserInterface.EmojiView.EmojiManager;
import lahds.relyme.UserInterface.EmojiView.EmojiUtils;
import lahds.relyme.UserInterface.EmojiView.Adapters.EmojiViewPagerAdapter;
import lahds.relyme.UserInterface.EmojiView.Emoji.Emoji;
import lahds.relyme.UserInterface.EmojiView.Listener.FindVariantListener;
import lahds.relyme.UserInterface.EmojiView.Listener.OnEmojiActions;
import lahds.relyme.UserInterface.EmojiView.Shared.RecentEmoji;
import lahds.relyme.UserInterface.EmojiView.Shared.RecentEmojiManager;
import lahds.relyme.UserInterface.EmojiView.Shared.VariantEmoji;
import lahds.relyme.UserInterface.EmojiView.Shared.VariantEmojiManager;
import lahds.relyme.UserInterface.EmojiView.Utils.Utils;
import lahds.relyme.UserInterface.EmojiView.Variant.EmojiVariantPopup;

public class EmojiView extends EmojiLayout implements FindVariantListener {
    public EmojiView(Context context) {
        super(context);
        init();
    }

    public EmojiView(Context context, OnEmojiActions ev) {
        super(context);
        this.events = ev;
        init();
    }

    CategoryViews categoryViews;
    ViewPager vp;
    RecentEmoji recent;
    VariantEmoji variant;
    EmojiVariantPopup variantPopup;

    OnEmojiActions events = new OnEmojiActions() {
        @Override
        public void onClick(View view, Emoji emoji, boolean fromRecent, boolean fromVariant) {
            if (!fromVariant && variantPopup != null && variantPopup.isShowing()) return;
            if (!fromVariant) recent.addEmoji(emoji);
            if (editText != null) EmojiUtils.input(editText, emoji);

            /**if (!fromRecent && ((EmojiViewPagerAdapter) vp.getAdapter()).add==1)
             ((EmojiViewPagerAdapter) vp.getAdapter()).recyclerViews.get(0).getAdapter().notifyDataSetChanged();*/

            variant.addVariant(emoji);
            if (variantPopup != null) variantPopup.dismiss();

            if (emojiActions != null) emojiActions.onClick(view, emoji, fromRecent, fromVariant);
        }

        @Override
        public boolean onLongClick(View view, Emoji emoji, boolean fromRecent, boolean fromVariant) {

            if (view!=null && variantPopup != null && (!fromRecent || EmojiManager.isRecentVariantEnabled())) {
                if (emoji.getBase().hasVariants())
                    variantPopup.show((EmojiImageView) view, emoji, fromRecent);
            }

            if (emojiActions != null)
                return emojiActions.onLongClick(view, emoji, fromRecent, fromVariant);
            return false;
        }
    };

    OnEmojiActions emojiActions = null;

    public OnEmojiActions getInnerEmojiActions() {
        return events;
    }

    /**
     * add emoji click and longClick listener
     *
     * @param listener
     */
    public void setOnEmojiActionsListener(OnEmojiActions listener) {
        emojiActions = listener;
    }

    public OnEmojiActions getOnEmojiActionsListener() {
        return emojiActions;
    }

    public VariantEmoji getVariantEmoji() {
        return variant;
    }

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        private boolean isShowing = true;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (recyclerView == null) {
                if (!EmojiManager.getEmojiViewTheme().isAlwaysShowDividerEnabled()) {
                    if (!isShowing) {
                        isShowing = true;
                        if (categoryViews != null) categoryViews.Divider.setVisibility(GONE);
                    }
                }
                return;
            }
            if (dy == 0) return;
            if (dy == 1) dy = 0;
            super.onScrolled(recyclerView, dx, dy);
            if (scrollListener2 != null)
                scrollListener2.onScrolled(recyclerView, dx, dy);

            if (!EmojiManager.getEmojiViewTheme().isAlwaysShowDividerEnabled()) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager == null) return;
                int firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                if ((visibleItemCount > 0 && (firstVisibleItemPosition) == 0)) {
                    if (!isShowing) {
                        isShowing = true;
                        if (categoryViews != null) categoryViews.Divider.setVisibility(GONE);
                    }
                } else {
                    if (isShowing) {
                        isShowing = false;
                        if (categoryViews != null) categoryViews.Divider.setVisibility(VISIBLE);
                    }
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (scrollListener2 != null)
                scrollListener2.onScrollStateChanged(recyclerView, newState);
        }
    };

    RecyclerView.OnScrollListener scrollListener2 = null;

    private void init() {
        if (EmojiManager.getRecentEmoji() != null) {
            recent = EmojiManager.getRecentEmoji();
        } else {
            recent = new RecentEmojiManager(getContext());
        }
        if (EmojiManager.getVariantEmoji() != null) {
            variant = EmojiManager.getVariantEmoji();
        } else {
            variant = new VariantEmojiManager(getContext());
        }

        int top = 0;
        if (EmojiManager.getEmojiViewTheme().isCategoryEnabled())
            top = Utils.dpToPx(getContext(), 39);

        vp = new ViewPager(getContext());
        this.addView(vp, new EmojiLayout.LayoutParams(0, top, -1, -1));
        vp.setAdapter(new EmojiViewPagerAdapter(events, scrollListener, recent, variant, this));
        //vp.setPadding(0, 0, 0, top);

        if (EmojiManager.getEmojiViewTheme().isCategoryEnabled()) {
            categoryViews = new CategoryViews(getContext(), this, recent);
            this.addView(categoryViews, new EmojiLayout.LayoutParams(0, 0, -1, top));
        } else {
            categoryViews = null;
        }

        this.setBackgroundColor(EmojiManager.getEmojiViewTheme().getBackgroundColor());
        if (categoryViews != null)
            categoryViews.setBackgroundColor(EmojiManager.getEmojiViewTheme().getBackgroundColor());

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (pagerListener2 != null) pagerListener2.onPageScrolled(i, v, i1);
            }

            @Override
            public void onPageSelected(int i) {
                vp.setCurrentItem(i, true);
                if (((EmojiViewPagerAdapter) vp.getAdapter()).recyclerViews.size() > i) {
                    scrollListener.onScrolled(((EmojiViewPagerAdapter) vp.getAdapter()).recyclerViews.get(i), 0, 1);
                } else {
                    scrollListener.onScrolled(null, 0, 1);
                }
                if (categoryViews != null) categoryViews.setPageIndex(i);
                if (pagerListener2 != null) pagerListener2.onPageSelected(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (pagerListener2 != null) pagerListener2.onPageScrollStateChanged(i);
            }
        });

    }

    ViewPager.OnPageChangeListener pagerListener2 = null;

    @Override
    public void setPageIndex(int index) {
        vp.setCurrentItem(index, true);
        if (!EmojiManager.getEmojiViewTheme().isAlwaysShowDividerEnabled()) {
            if (((EmojiViewPagerAdapter) vp.getAdapter()).recyclerViews.size() > index) {
                scrollListener.onScrolled(((EmojiViewPagerAdapter) vp.getAdapter()).recyclerViews.get(index), 0, 1);
            } else {
                scrollListener.onScrolled(null, 0, 1);
            }
        }
        if (categoryViews != null) categoryViews.setPageIndex(index);
    }

    @Override
    public void dismiss() {
        if (variantPopup != null) variantPopup.dismiss();
        recent.persist();
        variant.persist();
    }

    @Override
    public void setEditText(EditText editText) {
        super.setEditText(editText);
        variantPopup = EmojiManager.getEmojiVariantCreatorListener().create(editText.getRootView(), events);
    }

    @Override
    protected void setScrollListener(RecyclerView.OnScrollListener listener) {
        super.setScrollListener(listener);
        scrollListener2 = listener;
    }

    @Override
    protected void setPageChanged(ViewPager.OnPageChangeListener listener) {
        super.setPageChanged(listener);
        pagerListener2 = listener;
    }

    @Override
    protected void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        ((EmojiViewPagerAdapter) vp.getAdapter()).itemDecoration = itemDecoration;
        for (int i = 0; i < ((EmojiViewPagerAdapter) vp.getAdapter()).recyclerViews.size(); i++) {
            ((EmojiViewPagerAdapter) vp.getAdapter()).recyclerViews.get(i).addItemDecoration(itemDecoration);
        }
    }

    @Override
    protected void refresh() {
        super.refresh();
        if (categoryViews != null) {
            categoryViews.removeAllViews();
            categoryViews.init();
        }
        vp.getAdapter().notifyDataSetChanged();
        vp.setCurrentItem(0, false);
        if (!EmojiManager.getEmojiViewTheme().isAlwaysShowDividerEnabled())
            scrollListener.onScrolled(null, 0, 1);
        if (categoryViews != null) categoryViews.setPageIndex(0);
    }

    @Override
    public int getPageIndex() {
        return vp.getCurrentItem();
    }

    public ViewPager getViewPager() {
        return vp;
    }

    @Override
    public EmojiVariantPopup findVariant() {
        return variantPopup;
    }
}

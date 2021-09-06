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


package lahds.relyme.UserInterface.EmojiView.Adapters;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;

import lahds.relyme.UserInterface.EmojiView.EmojiManager;
import lahds.relyme.UserInterface.EmojiView.Utils.Utils;
import lahds.relyme.UserInterface.EmojiView.View.EmojiLayout;
import lahds.relyme.UserInterface.EmojiView.View.EmojiPager;

public class FooterIconsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    EmojiPager pager;

    public FooterIconsAdapter(EmojiPager pager) {
        this.pager = pager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int iconSize = Utils.dpToPx(viewGroup.getContext(), 24);
        EmojiLayout layout = new EmojiLayout(viewGroup.getContext());
        AppCompatImageView icon = new AppCompatImageView(viewGroup.getContext());
        layout.addView(icon, new EmojiLayout.LayoutParams(Utils.dpToPx(viewGroup.getContext(), 8), Utils.dpToPx(viewGroup.getContext(), 10), iconSize, iconSize));
        layout.setLayoutParams(new ViewGroup.LayoutParams(Utils.dpToPx(viewGroup.getContext(), 40), Utils.dpToPx(viewGroup.getContext(), 44)));
        return new RecyclerView.ViewHolder(layout) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        boolean selected = pager.getViewPager().getCurrentItem() == i;
        EmojiLayout layout = (EmojiLayout) viewHolder.itemView;
        AppCompatImageView icon = (AppCompatImageView) layout.getChildAt(0);

        if (pager.getPageBinder(i) != null) {
            pager.getPageBinder(i).onBindFooterItem(icon, i, selected);
        } else {
            Drawable dr = ContextCompat.getDrawable(icon.getContext().getApplicationContext(), pager.getPageIcon(i));
            if (selected) {
                DrawableCompat.setTint(DrawableCompat.wrap(dr), EmojiManager.getEmojiViewTheme().getFooterSelectedItemColor());
            } else {
                DrawableCompat.setTint(DrawableCompat.wrap(dr), EmojiManager.getEmojiViewTheme().getFooterItemColor());
            }
            icon.setImageDrawable(dr);
        }
        Utils.setClickEffect(icon, true);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pager.getViewPager().getCurrentItem() != i) {
                    pager.setPageIndex(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pager.getPagesCount();
    }
}

package lahds.relyme.UserInterface.EmojiView.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import lahds.relyme.UserInterface.EmojiView.EmojiManager;
import lahds.relyme.UserInterface.EmojiView.Emoji.Emoji;
import lahds.relyme.UserInterface.EmojiView.Listener.OnEmojiActions;
import lahds.relyme.UserInterface.EmojiView.Shared.RecentEmoji;
import lahds.relyme.UserInterface.EmojiView.Shared.VariantEmoji;
import lahds.relyme.UserInterface.EmojiView.Utils.Utils;
import lahds.relyme.UserInterface.EmojiView.View.EmojiImageView;

public class RecentEmojiRecyclerAdapter extends RecyclerView.Adapter<RecentEmojiRecyclerAdapter.ViewHolder> {
    RecentEmoji recentEmoji;
    OnEmojiActions events;
    VariantEmoji variantEmoji;

    public RecentEmojiRecyclerAdapter(RecentEmoji recentEmoji, OnEmojiActions events, VariantEmoji variantEmoji) {
        this.recentEmoji = recentEmoji;
        this.events = events;
        this.variantEmoji = variantEmoji;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FrameLayout frameLayout = new FrameLayout(viewGroup.getContext());
        EmojiImageView emojiView = new EmojiImageView(viewGroup.getContext());
        int cw = Utils.getColumnWidth(viewGroup.getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(cw, cw));
        frameLayout.addView(emojiView);

        int dp6 = Utils.dpToPx(viewGroup.getContext(), 6);
        emojiView.setPadding(dp6, dp6, dp6, dp6);

        return new ViewHolder(frameLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FrameLayout frameLayout = (FrameLayout) viewHolder.itemView;
        final EmojiImageView emojiView = (EmojiImageView) frameLayout.getChildAt(0);

        Emoji emoji = variantEmoji.getVariant((Emoji) recentEmoji.getRecentEmojis().toArray()[i]);
        emojiView.setEmoji(emoji);
        emojiView.setOnEmojiActions(events, true);


        if (!EmojiManager.getInstance().isRecentVariantEnabled()) {
            emojiView.setShowVariants(false);
        } else {
            emojiView.setShowVariants(EmojiManager.getTheme().isVariantDividerEnabled());
        }
    }

    @Override
    public int getItemCount() {
        return recentEmoji.getRecentEmojis().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

package lahds.relyme.UserInterface.EmojiView.Listener;

import android.view.View;

import lahds.relyme.UserInterface.EmojiView.Emoji.Emoji;

public interface OnEmojiActions {
    void onClick(View view, Emoji emoji, boolean fromRecent, boolean fromVariant);

    boolean onLongClick(View view, Emoji emoji, boolean fromRecent, boolean fromVariant);
}

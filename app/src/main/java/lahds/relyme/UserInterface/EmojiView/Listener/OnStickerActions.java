package lahds.relyme.UserInterface.EmojiView.Listener;

import android.view.View;

import lahds.relyme.UserInterface.EmojiView.Sticker.Sticker;

public interface OnStickerActions {
    void onClick(View view, Sticker sticker, boolean fromRecent);

    boolean onLongClick(View view, Sticker sticker, boolean fromRecent);
}

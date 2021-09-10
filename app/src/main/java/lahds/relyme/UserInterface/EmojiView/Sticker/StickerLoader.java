package lahds.relyme.UserInterface.EmojiView.Sticker;

import android.view.View;

@SuppressWarnings("rawtypes")
public interface StickerLoader {
    void onLoadSticker(View view, Sticker sticker);

    void onLoadStickerCategory(View icon, StickerCategory stickerCategory, boolean selected);
}

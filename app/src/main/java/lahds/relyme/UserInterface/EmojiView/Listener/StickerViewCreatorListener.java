package lahds.relyme.UserInterface.EmojiView.Listener;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lahds.relyme.UserInterface.EmojiView.Sticker.StickerCategory;

public interface StickerViewCreatorListener {
    View onCreateStickerView(@NonNull final Context context, @Nullable final StickerCategory category, final boolean isRecent);

    View onCreateCategoryView(@NonNull final Context context);
}
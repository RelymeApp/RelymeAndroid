package lahds.relyme.UserInterface.EmojiView.Listener;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lahds.relyme.UserInterface.EmojiView.Variant.EmojiVariantPopup;

public interface EmojiVariantCreatorListener {
    EmojiVariantPopup create(@NonNull final View rootView, @Nullable final OnEmojiActions listener);
}

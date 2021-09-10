package lahds.relyme.UserInterface.EmojiView.Variant;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import lahds.relyme.UserInterface.EmojiView.Emoji.Emoji;
import lahds.relyme.UserInterface.EmojiView.Listener.OnEmojiActions;
import lahds.relyme.UserInterface.EmojiView.View.EmojiImageView;

public abstract class EmojiVariantPopup {

    public EmojiVariantPopup(@NonNull final View rootView, @Nullable final OnEmojiActions listener) {}

    public abstract boolean isShowing();

    public abstract void show(@NonNull final EmojiImageView clickedImage, @NonNull final Emoji emoji, boolean fromRecent);

    public abstract void dismiss();

    public boolean onTouch(MotionEvent event, RecyclerView recyclerView) {
        return false;
    }
}

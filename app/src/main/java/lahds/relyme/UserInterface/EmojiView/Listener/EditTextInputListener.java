package lahds.relyme.UserInterface.EmojiView.Listener;

import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lahds.relyme.UserInterface.EmojiView.Emoji.Emoji;

public interface EditTextInputListener {
    void input(@NonNull final EditText editText, @Nullable final Emoji emoji);
}

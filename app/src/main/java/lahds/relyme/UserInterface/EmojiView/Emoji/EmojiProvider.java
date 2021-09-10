package lahds.relyme.UserInterface.EmojiView.Emoji;

import androidx.annotation.NonNull;

public interface EmojiProvider {
    @NonNull
    EmojiCategory[] getCategories();

    void destroy();
}

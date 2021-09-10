package lahds.relyme.UserInterface.EmojiView.IOSProvider;

import android.content.Context;

import androidx.annotation.NonNull;

import lahds.relyme.R;
import lahds.relyme.UserInterface.EmojiView.Emoji.Emoji;
import lahds.relyme.UserInterface.EmojiView.Emoji.EmojiCategory;
import lahds.relyme.UserInterface.EmojiView.Emoji.EmojiData;
import lahds.relyme.UserInterface.EmojiView.Emoji.EmojiProvider;

public final class IOSEmojiProvider extends IOSEmojiReplacer implements EmojiProvider {

    public static EmojiCategory[] emojiCategories = null;

    public IOSEmojiProvider(Context context) {
        IOSEmojiLoader.init(context);

        if (emojiCategories == null) {
            int[] icons = new int[]{
                    R.drawable.emoji_ios_category_people,
                    R.drawable.emoji_ios_category_nature,
                    R.drawable.emoji_ios_category_food,
                    R.drawable.emoji_ios_category_activity,
                    R.drawable.emoji_ios_category_travel,
                    R.drawable.emoji_ios_category_objects,
                    R.drawable.emoji_ios_category_symbols,
                    R.drawable.emoji_ios_category_flags
            };

            emojiCategories = new EmojiCategory[EmojiData.titles.length];
            for (int c = 0; c < EmojiData.titles.length; c++) {
                emojiCategories[c] = new IOSEmojiCategory(c, icons[c]);
            }
        }
    }

    public IOSEmojiProvider(Context context, int[] icons) {
        IOSEmojiLoader.init(context);

        if (emojiCategories == null) {
            emojiCategories = new EmojiCategory[EmojiData.titles.length];
            for (int c = 0; c < EmojiData.titles.length; c++) {
                emojiCategories[c] = new IOSEmojiCategory(c, icons[c]);
            }
        }
    }

    @Override
    @NonNull
    public EmojiCategory[] getCategories() {
        return emojiCategories;
    }

    @Override
    public void destroy() {

    }

    public static class IOSEmojiCategory implements EmojiCategory {
        Emoji[] DATA;
        String title;
        int icon;

        public IOSEmojiCategory(int i, int icon) {
            DATA = new Emoji[EmojiData.releaseData[i].length];
            for (int j = 0; j < EmojiData.releaseData[i].length; j++) {
                DATA[j] = new IOSEmoji(EmojiData.releaseData[i][j]);
            }
            title = EmojiData.titles[i];
            this.icon = icon;
        }

        @NonNull
        @Override
        public Emoji[] getEmojis() {
            return DATA;
        }

        @Override
        public int getIcon() {
            return icon;
        }

        @Override
        public CharSequence getTitle() {
            return title;
        }
    }
}

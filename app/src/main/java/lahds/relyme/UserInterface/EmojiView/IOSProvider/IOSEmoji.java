package lahds.relyme.UserInterface.EmojiView.IOSProvider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import lahds.relyme.UserInterface.EmojiView.Emoji.Emoji;
import lahds.relyme.UserInterface.EmojiView.Emoji.EmojiData;


@SuppressWarnings("serial")
public class IOSEmoji extends Emoji {

    public IOSEmoji(final String code) {
        super(code, -1);

        boolean isVariants = EmojiData.isColoredEmoji(code);
        if (isVariants) {
            IOSEmojiLoader.globalQueue.postRunnable(new Runnable() {
                @Override
                public void run() {
                    IOSEmoji[] variants = new IOSEmoji[5];
                    for (int i = 0; i < 5; i++) {
                        String color = "";
                        switch (i) {
                            case 0:
                                color = "\uD83C\uDFFB";
                                break;
                            case 1:
                                color = "\uD83C\uDFFC";
                                break;
                            case 2:
                                color = "\uD83C\uDFFD";
                                break;
                            case 3:
                                color = "\uD83C\uDFFE";
                                break;
                            case 4:
                                color = "\uD83C\uDFFF";
                                break;
                        }
                        variants[i] = new IOSEmoji(EmojiData.addColorToCode(code, color), -1, 0);
                    }
                    setVariants(variants);
                }
            });
        }
    }

    private IOSEmoji(String code, int resource, int count) {
        super(code, resource, new Emoji[count]);
    }

    @Override
    public Drawable getDrawable(final View view) {
        return IOSEmojiLoader.getEmojiBigDrawable(getUnicode());
    }

    @Override
    public Drawable getDrawable(final Context context) {
        return IOSEmojiLoader.getEmojiBigDrawable(getUnicode());
    }

    public Drawable getDrawable() {
        return IOSEmojiLoader.getEmojiBigDrawable(getUnicode());
    }

    public Drawable getDrawable(int size, boolean fullSize) {
        return IOSEmojiLoader.getEmojiDrawable(getUnicode(), size, fullSize);
    }

    @Override
    public Bitmap getEmojiBitmap() {
        return IOSEmojiLoader.getEmojiBitmap(getUnicode());
    }

    @Override
    public boolean isLoading() {
        return getEmojiBitmap() == null;
    }
}

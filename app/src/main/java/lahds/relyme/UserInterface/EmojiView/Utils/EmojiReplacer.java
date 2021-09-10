package lahds.relyme.UserInterface.EmojiView.Utils;

import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.view.View;

public interface EmojiReplacer {
    void replaceWithImages(Context context, View view, Spannable text, float emojiSize, Paint.FontMetrics fontMetrics);
}

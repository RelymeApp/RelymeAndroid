package lahds.relyme.UserInterface.EmojiView.Listener;

import lahds.relyme.UserInterface.EmojiView.View.EmojiBase;
import lahds.relyme.UserInterface.EmojiView.View.EmojiPager;

public interface OnEmojiPagerPageChanged {
    void onPageChanged(EmojiPager emojiPager, EmojiBase base, int position);
}

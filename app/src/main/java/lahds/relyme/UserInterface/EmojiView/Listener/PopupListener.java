package lahds.relyme.UserInterface.EmojiView.Listener;

public interface PopupListener {
    void onDismiss();

    void onShow();

    void onKeyboardOpened(int height);

    void onKeyboardClosed();

    void onViewHeightChanged (int height);
}

package lahds.relyme.UserInterface.EmojiView.View;

public interface PopupInterface {
    void toggle();

    void show();

    void dismiss();

    boolean isShowing();

    boolean onBackPressed();

    void reload();
}

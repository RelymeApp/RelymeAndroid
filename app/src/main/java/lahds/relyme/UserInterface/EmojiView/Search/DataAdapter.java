package lahds.relyme.UserInterface.EmojiView.Search;

import androidx.annotation.NonNull;

import java.util.List;

public interface DataAdapter<T> {

    void init();
    @NonNull List<T> searchFor(String value);
    void destroy();

}

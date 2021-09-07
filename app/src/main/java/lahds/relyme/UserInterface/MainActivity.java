package lahds.relyme.UserInterface;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import lahds.relyme.R;
import lahds.relyme.UserInterface.ActionBar.ActionBarLayout;
import lahds.relyme.UserInterface.ActionBar.BaseFragment;
import lahds.relyme.Utilities.NotificationCenter;

public class MainActivity extends AppCompatActivity {

    private ActionBarLayout actionBarLayout;
    private final ArrayList<BaseFragment> mainFragmentStack = new ArrayList<>();

    @Override
    public void onAttachedToWindow() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.closeChats);
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout layout_container = findViewById(R.id.layout_container);
        actionBarLayout = new ActionBarLayout(this);

        layout_container.addView(actionBarLayout);
        actionBarLayout.init(mainFragmentStack);
        actionBarLayout.setDelegate(new ActionBarLayout.ActionBarLayoutDelegate() {
            @Override
            public boolean onPreIme() {
                return false;
            }

            @Override
            public boolean needPresentFragment(BaseFragment fragment, boolean removeLast, boolean forceWithoutAnimation, ActionBarLayout layout) {
                return true;
            }

            @Override
            public boolean needAddFragmentToStack(BaseFragment fragment, ActionBarLayout layout) {
                return true;
            }

            @Override
            public boolean needCloseLastFragment(ActionBarLayout layout) {
                if(layout.fragmentsStack.size() <= 1){
                    finish();
                    return false;
                }
                return true;
            }

            @Override
            public void onRebuildAllFragments(ActionBarLayout layout) {

            }

        });
        actionBarLayout.presentFragment(new IntroActivity());
        updateStatusBar();
    }

    @Override
    public void onBackPressed() {
        actionBarLayout.onBackPressed();
    }

    private void updateStatusBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected void onDestroy() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
        super.onDestroy();
    }
}
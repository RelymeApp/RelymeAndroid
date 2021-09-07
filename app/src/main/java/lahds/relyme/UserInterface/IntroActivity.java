package lahds.relyme.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;
import lahds.relyme.R;
import lahds.relyme.UserInterface.ActionBar.BaseFragment;
import lahds.relyme.Utilities.NotificationCenter;

public class IntroActivity extends BaseFragment {

    private Context context;
    private FloatingActionButton floatingActionButton;
    private CircleImageView profile_imageview;

    public static IntroActivity newInstance() {
        return new IntroActivity();
    }

    @Override
    public boolean onFragmentCreate() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.didClickConversation);
        return super.onFragmentCreate();
    }

    @Override
    public void onFragmentDestroy() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didClickConversation);
        super.onFragmentDestroy();
    }

    @Override
    public View createView(Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        fragmentView = new FrameLayout(context);
        View view = inflater.inflate(R.layout.activity_intro, (ViewGroup) fragmentView, false);
        ((ViewGroup) fragmentView).addView(view);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        profile_imageview = view.findViewById(R.id.toolbar_icon);

        actionBar.setAddToContainer(false);
        initialize();
        getParentActivity().getWindow().getDecorView().setOnApplyWindowInsetsListener((view1, insets) -> {
            int marginBottom;
            marginBottom = insets.getSystemWindowInsetBottom() - insets.getStableInsetBottom();
            int marginTop = insets.getSystemWindowInsetTop() - insets.getStableInsetTop();

            if(marginTop == 0 | marginBottom == 0){
                marginBottom = insets.getStableInsetBottom();
                marginTop = insets.getSystemWindowInsetTop();
            }
            if (view1 != null) {
                ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams) floatingActionButton.getLayoutParams();
                params1.bottomMargin += marginBottom;
                floatingActionButton.setLayoutParams(params1);

                //((LinearLayout.LayoutParams)toolbar.getLayoutParams()).topMargin = marginTop;
            }

            return view1.onApplyWindowInsets(insets);
        });

        return fragmentView;
    }

    private void initialize() {

    }
}
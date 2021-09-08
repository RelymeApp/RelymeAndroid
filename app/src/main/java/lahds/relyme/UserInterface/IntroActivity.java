package lahds.relyme.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button button_next;

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

        Button button_next = view.findViewById(R.id.button_next);

        actionBar.setAddToContainer(false);
        initialize();
        return fragmentView;
    }

    private void initialize() {
        button_next.setOnClickListener((view) -> {
            presentFragment(new HomeActivity(), false, false);
        });
    }
}
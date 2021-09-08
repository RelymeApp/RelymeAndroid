package lahds.relyme.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;
import lahds.relyme.R;
import lahds.relyme.UserInterface.ActionBar.BaseFragment;
import lahds.relyme.Utilities.NotificationCenter;

public class HomeActivity extends BaseFragment {

    private Context context;
    private LinearLayout toolbar;
    private CircleImageView ic_avatar;
    private LinearLayout layout_text_avatar;
    private TextView text_header;
    private ImageView ic_search;
    private LinearLayout linear_main;
    private RecyclerView list_chats;
    private LinearLayout layout_no_chats;
    private TextView text_no_chats;
    private FloatingActionButton fab;

    public static HomeActivity newInstance() {
        return new HomeActivity();
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
        View view = inflater.inflate(R.layout.activity_home, (ViewGroup) fragmentView, false);
        ((ViewGroup) fragmentView).addView(view);

        LinearLayout toolbar = view.findViewById(R.id.toolbar);
        CircleImageView ic_avatar = view.findViewById(R.id.ic_avatar);
        LinearLayout layout_text_avatar = view.findViewById(R.id.layout_text_avatar);
        TextView text_header = view.findViewById(R.id.text_header);
        ImageView ic_search = view.findViewById(R.id.ic_search);
        LinearLayout layout_main = view.findViewById(R.id.layout_main);
        RecyclerView list_chats = view.findViewById(R.id.list_chats);
        LinearLayout layout_no_chats = view.findViewById(R.id.layout_no_chats);
        TextView text_no_chats = view.findViewById(R.id.text_no_chats);
        TextView text_start = view.findViewById(R.id.text_start);
        FloatingActionButton fab = view.findViewById(R.id.fab);

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
                ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams) fab.getLayoutParams();
                params1.bottomMargin += marginBottom;
                fab.setLayoutParams(params1);

                ((ConstraintLayout.LayoutParams)toolbar.getLayoutParams()).topMargin = marginTop;
            }

            return view1.onApplyWindowInsets(insets);
        });
        return fragmentView;
    }

    private void initialize() {

    }
}
package lahds.relyme.UserInterface;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;
import lahds.relyme.R;
import lahds.relyme.UserInterface.ActionBar.BaseFragment;
import lahds.relyme.Utilities.NotificationCenter;

public class HomeActivity extends BaseFragment {

    private Context context;
    private FloatingActionButton fab;
    private LinearLayout toolbar;
    private LinearLayout linear_main;
    private CardView linear_avatar;
    private LinearLayout linear_text_avatar;
    private TextView text_header;
    private ImageView ic_search;
    private ImageView ic_avatar;
    private TextView text_avatar;
    private RecyclerView list_messages;
    private LinearLayout linear_no_messages;
    private TextView text_no_messages;
    private TextView text_start;

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

        //BASE FRAGMENT
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        fragmentView = new FrameLayout(context);
        View view = inflater.inflate(R.layout.activity_home, (ViewGroup) fragmentView, false);
        ((ViewGroup) fragmentView).addView(view);
        actionBar.setAddToContainer(false);

        //INITIALIZE VIEWS
        fab = view.findViewById(R.id.fab);
        toolbar = view.findViewById(R.id.toolbar);
        linear_main = view.findViewById(R.id.linear_main);
        linear_avatar = view.findViewById(R.id.linear_avatar);
        linear_text_avatar = view.findViewById(R.id.linear_text_avatar);
        text_header = view.findViewById(R.id.text_header);
        ic_search = view.findViewById(R.id.ic_search);
        ic_avatar = view.findViewById(R.id.ic_avatar);
        text_avatar = view.findViewById(R.id.text_avatar);
        list_messages = view.findViewById(R.id.list_messages);
        linear_no_messages = view.findViewById(R.id.linear_no_messages);
        text_no_messages = view.findViewById(R.id.text_no_messages);
        text_start = view.findViewById(R.id.text_start);

        //USER INTERFACE
        toolbar.setElevation(3f);
        linear_text_avatar.setVisibility(View.GONE);
        list_messages.setVisibility(View.GONE);
        ic_search.setColorFilter(0xFF374D48, PorterDuff.Mode.MULTIPLY);
        linear_no_messages.setVisibility(View.VISIBLE);

        //GENERAL LOGIC
        fab.setOnClickListener(v -> {
            presentFragment(new ChatActivity(), false, false);
        });

        return fragmentView;
    }
}
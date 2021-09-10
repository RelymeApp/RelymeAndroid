package lahds.relyme.UserInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lahds.relyme.R;
import lahds.relyme.UserInterface.ActionBar.BaseFragment;
import lahds.relyme.UserInterface.EmojiView.View.EmojiEditText;
import lahds.relyme.UserInterface.EmojiView.View.EmojiTextView;
import lahds.relyme.Utilities.AndroidUtilities;
import lahds.relyme.Utilities.NotificationCenter;

public class ChatActivity extends BaseFragment {

    private Context context;
    private LinearLayout toolbar;
    private LinearLayout linear_main;
    private ImageView ic_back;
    private LinearLayout size_control;
    private LinearLayout linear_weight;
    private ImageView ic_more;
    private LinearLayout linear_toolbar_main;
    private LinearLayout linear_profile;
    private LinearLayout linear_details;
    private CardView linear_avatar;
    private LinearLayout linear_text_avatar;
    private ImageView ic_avatar;
    private EmojiTextView text_avatar;
    private LinearLayout linear_username;
    private TextView text_status;
    private EmojiTextView text_username;
    private ImageView ic_verified;
    private LinearLayout linear_messages;
    private LinearLayout layout_bottom;
    private LinearLayout layout_emoji;
    private RecyclerView list_messages;
    private LinearLayout layout_no_messages;
    private TextView text_no_messages;
    private TextView text_wave;
    private LinearLayout layout_message;
    private LinearLayout layout_send;
    private ImageView ic_emoji;
    private EmojiEditText text_message;
    private ImageView ic_camera;
    private ImageView ic_files;
    private ImageView ic_send;

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

        //BASE FRAGMENT
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        fragmentView = new FrameLayout(context);
        View view = inflater.inflate(R.layout.activity_chat, (ViewGroup) fragmentView, false);
        ((ViewGroup) fragmentView).addView(view);
        actionBar.setAddToContainer(false);

        //INITIALIZE VIEWS
        toolbar = view.findViewById(R.id.toolbar);
        linear_main = view.findViewById(R.id.linear_main);
        ic_back = view.findViewById(R.id.ic_back);
        size_control = view.findViewById(R.id.size_control);
        linear_weight = view.findViewById(R.id.linear_weight);
        ic_more = view.findViewById(R.id.ic_more);
        linear_toolbar_main = view.findViewById(R.id.linear_toolbar_main);
        linear_profile = view.findViewById(R.id.linear_profile);
        linear_details = view.findViewById(R.id.linear_details);
        linear_avatar = view.findViewById(R.id.linear_avatar);
        linear_text_avatar = view.findViewById(R.id.linear_text_avatar);
        ic_avatar = view.findViewById(R.id.ic_avatar);
        text_avatar = view.findViewById(R.id.text_avatar);
        linear_username = view.findViewById(R.id.linear_username);
        text_status = view.findViewById(R.id.text_status);
        text_username = view.findViewById(R.id.text_username);
        ic_verified = view.findViewById(R.id.ic_verified);
        linear_messages = view.findViewById(R.id.linear_messages);
        layout_bottom = view.findViewById(R.id.layout_bottom);
        layout_emoji = view.findViewById(R.id.layout_emoji);
        list_messages = view.findViewById(R.id.list_messages);
        layout_no_messages = view.findViewById(R.id.layout_no_messages);
        text_no_messages = view.findViewById(R.id.text_no_messages);
        text_wave = view.findViewById(R.id.text_wave);
        layout_message = view.findViewById(R.id.layout_message);
        layout_send = view.findViewById(R.id.layout_send);
        ic_emoji = view.findViewById(R.id.ic_emoji);
        text_message = view.findViewById(R.id.text_message);
        ic_camera = view.findViewById(R.id.ic_camera);
        ic_files = view.findViewById(R.id.ic_files);
        ic_send = view.findViewById(R.id.ic_send);

        //USER INTERFACE
        rippleRoundStroke(linear_text_avatar, "#F44336", "#EEEEEE", AndroidUtilities.dp(360), 0, "#FFFFFF");
        linear_avatar.setVisibility(View.GONE);
        ic_verified.setColorFilter(0xFF2196F3, PorterDuff.Mode.MULTIPLY);
        ic_emoji.setColorFilter(0xFF879694, PorterDuff.Mode.MULTIPLY);
        ic_camera.setColorFilter(0xFF879694, PorterDuff.Mode.MULTIPLY);
        ic_files.setColorFilter(0xFF879694, PorterDuff.Mode.MULTIPLY);
        ic_back.setColorFilter(0xFF374D48, PorterDuff.Mode.MULTIPLY);
        ic_more.setColorFilter(0xFF374D48, PorterDuff.Mode.MULTIPLY);
        toolbar.setElevation(3f);
        layout_bottom.setElevation(9f);
        layout_message.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int) AndroidUtilities.dp(30), (int)AndroidUtilities.dp(1), 0xFFEEEEEE, 0xFFFFFFFF));
        layout_send.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)AndroidUtilities.dp(360), 0xFF15C29E));
        layout_emoji.setVisibility(View.GONE);

        //GENERAL LOGIC


        return fragmentView;
    }

    public void rippleRoundStroke(final View view, final String onFocus, final String onPressed, final double Round, final double Stroke, final String strokeColor) {
        android.graphics.drawable.GradientDrawable gradientDrawable = new android.graphics.drawable.GradientDrawable();
        gradientDrawable.setColor(Color.parseColor(onFocus));
        gradientDrawable.setCornerRadius((float)Round);
        gradientDrawable.setStroke((int) Stroke,
                Color.parseColor("#" + strokeColor.replace("#", "")));
        android.graphics.drawable.RippleDrawable rippleDrawable = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(onPressed)}), gradientDrawable, null);
        view.setBackground(rippleDrawable);
    }
}
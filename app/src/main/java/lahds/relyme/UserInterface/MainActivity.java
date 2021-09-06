package lahds.relyme.UserInterface;

import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;

import lahds.relyme.R;
import lahds.relyme.Utilities.AndroidUtilities;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView text_main;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize(savedInstanceState);
        initializeUILogic();
        initializeLogic();
    }

    private void initialize(Bundle savedInstanceState) {
        text_main = findViewById(R.id.text_main);
    }

    private void initializeUILogic() {

    }

    private void initializeLogic() {
        text_main.setOnClickListener(v -> {
            intent.setClass(getApplicationContext(), AccountActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "This is my toast", Toast.LENGTH_LONG).show();
        });
    }
}
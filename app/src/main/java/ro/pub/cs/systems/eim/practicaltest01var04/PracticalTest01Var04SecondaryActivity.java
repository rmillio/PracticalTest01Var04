package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var04SecondaryActivity extends AppCompatActivity {

    TextView name;
    TextView group;

    Button okBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        name = findViewById(R.id.textView1);
        group = findViewById(R.id.textView2);

        okBtn = findViewById(R.id.ok_button);
        cancelBtn = findViewById(R.id.cancel_button);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("name") && intent.getExtras().containsKey("group")) {
            String nameText = intent.getStringExtra("name");
            String groupText = intent.getStringExtra("group");

            name.setText(nameText);
            group.setText(groupText);
        }

        okBtn.setOnClickListener(view -> {
            setResult(RESULT_OK, null);
            finish();
        });

        cancelBtn.setOnClickListener(view -> {
            setResult(RESULT_CANCELED, null);
            finish();
        });
    }
}
package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {

    Button navigationBtn;
    Button displayBtn;

    EditText name;
    EditText group;

    CheckBox checkBoxName;
    CheckBox checkBoxGroup;

    TextView label;

    IntentFilter intentFilter;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(android.content.Context context, Intent intent) {
            String message = intent.getStringExtra(Constants.SERVICE_LOG);
            Log.d(Constants.TAG, message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

        displayBtn = findViewById(R.id.display_button);
        navigationBtn = findViewById(R.id.navigate_button);

        name = findViewById(R.id.editText1);
        group = findViewById(R.id.editText2);

        checkBoxName = findViewById(R.id.checkBox1);
        checkBoxGroup = findViewById(R.id.checkBox2);

        label = findViewById(R.id.label);

        intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.action1);
        intentFilter.addAction(Constants.action2);

        displayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";
                if (checkBoxName.isChecked()) {
                    if (name.getText().toString().length() == 0) {
                        Toast.makeText(PracticalTest01Var04MainActivity.this, "Eroare nume", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    text += name.getText().toString();
                }
                if (checkBoxGroup.isChecked()) {
                    if (group.getText().toString().length() == 0) {
                        Toast.makeText(PracticalTest01Var04MainActivity.this, "Eroare grupa", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    text += " " + group.getText().toString();
                }
                label.setText(text);

                startMyService();
            }
        });

        // navigate to secondary activity
        navigationBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04SecondaryActivity.class);
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("group", group.getText().toString());
            startActivityForResult(intent, 1);
        });
    }

    private void startMyService() {
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("group", group.getText().toString());
        getApplicationContext().startService(intent);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
        getApplicationContext().stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("name", name.getText().toString());
        savedInstanceState.putString("group", group.getText().toString());
        savedInstanceState.putString("label", label.getText().toString());
        // save checkboxes
        savedInstanceState.putBoolean("nameChecked", checkBoxName.isChecked());
        savedInstanceState.putBoolean("groupChecked", checkBoxGroup.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("name")) {
            name.setText(savedInstanceState.getString("name"));
        }
        if (savedInstanceState.containsKey("group")) {
            group.setText(savedInstanceState.getString("group"));
        }
        if (savedInstanceState.containsKey("label")) {
            label.setText(savedInstanceState.getString("label"));
        }
        // restore checkboxes
        if (savedInstanceState.containsKey("nameChecked")) {
            checkBoxName.setChecked(savedInstanceState.getBoolean("nameChecked"));
        }
        if (savedInstanceState.containsKey("groupChecked")) {
            checkBoxGroup.setChecked(savedInstanceState.getBoolean("groupChecked"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "CANCEL", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}


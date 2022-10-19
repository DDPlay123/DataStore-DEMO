package com.tutorial.datastore;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.tutorial.datastore.databinding.ActivityMainBinding;

/*
    存放資料:
    String:  字串
    Int:     5
    Long:    500000L
    Boolean: true
    Float:   3.14f
    Double:  2.7182818284
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding = null;
    private DataStoreManager dataStore = null;
    private Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListener();
    }

    private void init() {
        String[] type = getResources().getStringArray(R.array.type);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.item_type, type);
        binding.tvInputHint.setAdapter(adapter);
        binding.tvOutputHint.setAdapter(adapter);

        dataStore = new DataStoreManager(getApplicationContext());
    }

    @SuppressLint("SetTextI18n")
    private void setListener() {
        // 輸入
        binding.btnInput.setOnClickListener(view -> {
            switch (binding.tvInputHint.getText().toString()) {
                case "String":
                    dataStore.putData("String", "字串");
                    showPrompt("儲存成功");
                    break;
                case "Int":
                    dataStore.putData("Int", 5);
                    showPrompt("儲存成功");
                    break;
                case "Long":
                    dataStore.putData("Long", 500000);
                    showPrompt("儲存成功");
                    break;
                case "Boolean":
                    dataStore.putData("Boolean", true);
                    showPrompt("儲存成功");
                    break;
                case "Float":
                    dataStore.putData("Float", 3.14f);
                    showPrompt("儲存成功");
                    break;
                case "Double":
                    dataStore.putData("Double", 2.7182818284);
                    showPrompt("儲存成功");
                    break;
                default:
            }
        });

        // 清空
        binding.btnClear.setOnClickListener(view -> {
            dataStore.clear();
            showPrompt("清除成功");
        });

        // 輸出
        binding.btnOutput.setOnClickListener(view -> {
            switch (binding.tvOutputHint.getText().toString()) {
                case "String":
                    binding.output.setText(getString(R.string.output) + dataStore.getData("String", ""));
                    showPrompt("輸出成功");
                    break;
                case "Int":
                    binding.output.setText(getString(R.string.output) + dataStore.getData("Int", 1));
                    showPrompt("輸出成功");
                    break;
                case "Long":
                    binding.output.setText(getString(R.string.output) + dataStore.getData("Long", 1L));
                    showPrompt("輸出成功");
                    break;
                case "Boolean":
                    binding.output.setText(getString(R.string.output) + dataStore.getData("Boolean", false));
                    showPrompt("輸出成功");
                    break;
                case "Float":
                    binding.output.setText(getString(R.string.output) + dataStore.getData("Float", 0.11f));
                    showPrompt("輸出成功");
                    break;
                case "Double":
                    binding.output.setText(getString(R.string.output) + dataStore.getData("Double", 0.1111111111111));
                    showPrompt("輸出成功");
                    break;
                default:
            }
        });
    }

    private void showPrompt(String msg) {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
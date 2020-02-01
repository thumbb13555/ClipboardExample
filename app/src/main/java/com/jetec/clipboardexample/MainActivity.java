package com.jetec.clipboardexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> arrayList = new ArrayList<>();
    ListView listView;
    ClipboardManager clipboard = null;//複製貼上
    ClipData clipData = null;//複製貼上
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button clear= findViewById(R.id.button_clear);
        EditText editText = findViewById(R.id.editText_in);
        listView = findViewById(R.id.listview_TextBox);
/**=========================================設置複製素材庫↓=========================================*/
        for (int i=0;i<4;i++){
            arrayList.add("複製素材"+i);
        }
        ArrayAdapter adapter
                = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);
/**=========================================設置複製素材庫↑=========================================*/

        //ListView素材庫長點擊處理
        listView.setOnItemLongClickListener((parent, view, position, id)->{
            Vibrator myVibrator = (Vibrator) getApplication()//取得震動
                    .getSystemService(Service.VIBRATOR_SERVICE);
            myVibrator.vibrate(50);
            clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipData = ClipData.newPlainText(null,arrayList.get(position));
            clipboard.setPrimaryClip(clipData);
            Toast.makeText(this, "已複製: " + arrayList.get(position)
                    , Toast.LENGTH_SHORT).show();

            return false;
        });

        //EditText長點擊處理
        editText.setOnLongClickListener((v -> {
            Vibrator myVibrator = (Vibrator) getApplication()//取得震動
                    .getSystemService(Service.VIBRATOR_SERVICE);
            myVibrator.vibrate(50);

            try {//如果未複製任何字就施行貼上，會導致程式閃退
                ClipData word = clipboard.getPrimaryClip();
                ClipData.Item item = word.getItemAt(0);
                String text = item.getText().toString();
                editText.setText(editText.getText().toString()+text);
                Toast.makeText(this, "已貼上: " + text, Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(this, "未複製任何字串", Toast.LENGTH_SHORT).show();
            }
            return true;
        }));

        clear.setOnClickListener(v -> {editText.setText("");});//清除Edittext內字串

    }

}

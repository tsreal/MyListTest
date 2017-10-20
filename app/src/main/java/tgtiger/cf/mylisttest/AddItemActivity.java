package tgtiger.cf.mylisttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.githang.statusbar.StatusBarCompat;

import java.util.Date;

import tgtiger.cf.mylisttest.model.Item;
import tgtiger.cf.mylisttest.util.SharedPerferenceHelper;

public class AddItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        StatusBarCompat.setStatusBarColor(this, 0xff669900, false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText editItemName = (EditText) findViewById(R.id.add_editText1);
        Button buttonSubmit = (Button) findViewById(R.id.add_submit_btn1);

        /*绑定监听器*/
        buttonSubmit.setOnClickListener(new OnSubmitListener(editItemName));


    }

    private class OnSubmitListener implements View.OnClickListener {
        private EditText editItemName;

        OnSubmitListener(EditText editItemName) {
            this.editItemName = editItemName;
        }

        @Override
        public void onClick(View view) {
            /*获取待办事项名字*/
            String itemName = editItemName.getText().toString();

            //保存
            Item item = new Item();
            item.setName(itemName);
            item.setId(Long.valueOf(new Date().getTime()).intValue());

            SharedPerferenceHelper.addNode(AddItemActivity.this, item);
            AddItemActivity.this.finish();
        }
    }

    /**
     * 启动Activity
     */

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddItemActivity.class);
        ((Activity) context).startActivityForResult(intent, 1);
    }

}

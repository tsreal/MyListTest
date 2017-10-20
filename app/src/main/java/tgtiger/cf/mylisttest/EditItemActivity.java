package tgtiger.cf.mylisttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.githang.statusbar.StatusBarCompat;

import tgtiger.cf.mylisttest.model.Item;
import tgtiger.cf.mylisttest.util.SharedPerferenceHelper;

public class EditItemActivity extends AppCompatActivity {
    private Item item = new Item();//待办事项实体类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        StatusBarCompat.setStatusBarColor(this, 0xff669900, false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 获取 item 的值
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");

        // 设置值
        item.setId(id);
        item.setName(name);

        // 绑定输入框和按钮
        EditText editItemName = (EditText) findViewById(R.id.edit_editText1);
        Button buttonSubmit = (Button) findViewById(R.id.edit_submit_btn1);

        //设置初始值
        editItemName.setText(name);

        // 设置点击事件
        buttonSubmit.setOnClickListener(new EditItemActivity.OnSubmitListener(editItemName, item));

    }

    private class OnSubmitListener implements View.OnClickListener {

        private EditText editItemName;

        private Item item;

        OnSubmitListener(EditText editItemName, Item item) {
            this.editItemName = editItemName;
            this.item = item;
        }

        @Override
        public void onClick(View view) {


            // 获取待办事项名字
            String itemName = editItemName.getText().toString();

            //保存
            item.setName(itemName);

            //修改数据
            SharedPerferenceHelper.modifyNode(EditItemActivity.this, item);

//            关闭页面
            EditItemActivity.this.finish();
        }
    }


    /**
     * 启动Activity
     *
     * @param context
     * @param item
     */
    public static void startActivity(Context context, Item item) {

        Intent intent = new Intent(context, EditItemActivity.class);
        intent.putExtra("id", item.getId());
        intent.putExtra("name", item.getName());

        ((Activity) context).startActivityForResult(intent, 1);
    }

}

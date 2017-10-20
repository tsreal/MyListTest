package tgtiger.cf.mylisttest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tgtiger.cf.mylisttest.model.Item;
import tgtiger.cf.mylisttest.util.SharedPerferenceHelper;

import static android.graphics.Color.GREEN;

public class MainActivity extends AppCompatActivity {
    private List<Item> itemList;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.setStatusBarColor(this, 0xff669900, false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*获得添加按钮*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*绑定点击事件*/
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AddItemActivity.startActivity(MainActivity.this);
            }
        });

        /*绑定ListView*/
        ListView listView = (ListView) findViewById(R.id.main_listview);

        /*设置Adapter*/
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,getData());
        listView.setAdapter(adapter);

        // 添加item的监听事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //点击时弹出对话框，让用户选择修改或者是完成

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                
                Locale curLocale = getResources().getConfiguration().locale;
                //通过Locale的equals方法，判断出当前语言环境
                String[] opt = new String[0];
                if (curLocale.equals(Locale.SIMPLIFIED_CHINESE)) {
                    //中文
                    opt = new String[]{"修改", "完成"};
                } else {
                    //英文
                    opt = new String[]{"edit", "done"};
                }
                

                builder.setItems(opt, new OnOptListener(itemList.get(i)));
                builder.show();

            }
        });
    }

    /**
     * item点击事件
     */
    private class OnOptListener implements DialogInterface.OnClickListener {
        private Item item;

        OnOptListener(Item item) {
            this.item = item;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case 0:
                    //点击修改
                    EditItemActivity.startActivity(MainActivity.this, item);
                    break;
                case 1:
                    //点击完成
                    //删除数据
                    SharedPerferenceHelper.delNode(MainActivity.this, item);
                    //更新数据
                    updateData();
                    break;
            }
            //隐藏弹出框
            dialogInterface.dismiss();

        }
    }

    /**
     * 获取本地数据
     */
    private List<String> getData() {
        itemList = SharedPerferenceHelper.getItemList(this);
        List<String> data = new ArrayList<String>();
        for(int i=0; i<itemList.size(); i++) {
            data.add(itemList.get(i).getName());
        }
        return data;
    }


    /**
     * 页面回调,从AddItemActivity及EditItemActivity返回时回调此方法。
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateData();
    }

    /**
     * 更新界面
     */
    private void updateData() {
        adapter.clear();
        adapter.addAll(getData());
        adapter.notifyDataSetInvalidated();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}

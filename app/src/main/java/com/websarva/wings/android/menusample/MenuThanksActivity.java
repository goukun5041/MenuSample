package com.websarva.wings.android.menusample;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MenuThanksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_thanks);

        //インテントオブジェクト取得
        Intent intent = getIntent();

        //リスト画面から渡されたデータを取得
        String menuName = intent.getStringExtra("menuName");
        String menuPrice = intent.getStringExtra("menuPrice");

        //定食名と金額を表示させるTextViewを取得
        TextView tvMenuName = (TextView) findViewById(R.id.tvMenuName);
        TextView tvMenuPrice = (TextView) findViewById(R.id.tvMenuPrice);

        //TextViewに定食名と金額を表示
        tvMenuName.setText(menuName);
        tvMenuPrice.setText(menuPrice);
        //アクションメニューを取得
        ActionBar actionBar = getSupportActionBar();
        //アクションバー「戻る」メニューを有効に設定
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //選択されたメニューのIDを取得
        int itemId = item.getItemId();
        //選択されたメニューが「戻る」の場合、アクティビティ終了
        if(itemId == android.R.id.home) {
            finish();
        }
        //親クラスの同盟メソッドを呼び出し、その戻り値を返却
        return super.onOptionsItemSelected(item);
    }
}

package com.websarva.wings.android.menusample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuListActivity extends AppCompatActivity {
    //リストビューを表すフィールド
    private ListView _lvMenu;
    //リストビューに表示するリストデータ
    private List<Map<String, Object>> _menuList;
    //SimpleAdapterの第４引数fromに使用する定義フィールド
    private static final String[] FROM ={"name","price"};
    //SimpleAdapterの第５引数toに使用する定数フィールド
    private static final int[] TO ={R.id.tvMenuName,R.id.tvMenuPrice};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        //画面部品ListViewを取得し、フィールドに格納
        _lvMenu = (ListView) findViewById(R.id.lvMenu);
        //定食メニューListオブジェクトをprivateメソッドを利用して用意し、フィールドに格納
        _menuList = createTeishokuList();
        //SimpleAdapterを生成
        SimpleAdapter adapter = new SimpleAdapter(MenuListActivity.this, _menuList, R.layout.row, FROM, TO);
        //アダプタの登録
        _lvMenu.setAdapter(adapter);
        //リストタップのリスなクラス登録
        _lvMenu.setOnItemClickListener(new ListItemClickListener());

        registerForContextMenu(_lvMenu);
    }
    private List<Map<String, Object>> createTeishokuList(){
        //定食メニューリスト用のListオブジェクトを用意
        List<Map<String, Object>> menuList = new ArrayList<>();
        //「唐揚げ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録
        Map<String, Object> menu = new HashMap<>();
        menu.put("name","唐揚げ定食");
        menu.put("price","800");
        menu.put("desc","若鶏の唐揚げにサラダ、ご飯とお味噌汁がつきます。");
        menuList.add(menu);
        //「ハンバーグ」
        menu = new HashMap<>();
        menu.put("name","ハンバーグ定食");
        menu.put("price","850");
        menu.put("desc","手ごねハンバーグにサラダ、ご飯とお味噌汁がつきます。");
        menuList.add(menu);
        //繰り返し
        return menuList;
    }
    /**
     * リストがタップされた時の処理が記述されたメンバクラス
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?>parent,View view,int position,long id){
            //タップされた行のデータ取得
            Map<String,Object> item = (Map<String,Object>)parent.getItemAtPosition(position);
            //注文処理
            order(item);
        }
    }
    private List<Map<String,Object>> createCurryList(){
        //カレーメニューリスト用のListオブジェクトを用意
        List<Map<String,Object>> menuList = new ArrayList<>();
        //「ビーフカレー」データ格納MapとmenuList登録
        Map<String, Object> menu = new HashMap<>();
        menu.put("name","ビーフカレー");
        menu.put("price","520");
        menu.put("desc","特選スパイスを効かせた国産ビーフ100％のカレーです。");
        menuList.add(menu);
        //「ポークカレー」
        menu = new HashMap<>();
        menu.put("name","ポークカレー");
        menu.put("price","420");
        menu.put("desc","特選スパイスを効かせた国産ポーク100％のカレーです。");
        menuList.add(menu);
        //繰り返し
        return menuList;
    }
    private void order(Map<String, Object> menu){
        //定食メイト金額を取得　Mapノアたい部分がobject型なのでキャストが必要
        String menuName =(String) menu.get("name");
        Integer menuPrice = (Integer) menu.get("name");
        //インテントオブジェクトを生成
        Intent intent = new Intent(MenuListActivity.this, MenuThanksActivity.class);
        //第２画面に送るデータ
        intent.putExtra("menuName",menuName);
        //menuThanksActivityでのデータ受け取りと合わせるために金額にここで「円」を追加する
        intent.putExtra("menuPrice",menuPrice+"円");
        //第２画面起動
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //長押しされたビューに関する情報が格納されたオブジェクト取得
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //長押しされたリストのポジション取得
        int listPosition = info.position;
        //ポジションから長押しされたメニュー情報Mapオブジェクト取得
        Map<String,Object> menu = _menuList.get(listPosition);
        
        //選択されたメニューID取得
        int itemId =item.getItemId();
        //IDのR値による処理の分岐
        switch(itemId){
            //定食メニュー
            case R.id.menuListOptionTeishoku:
                //定食メニューリストデータ生成
                _menuList = createTeishokuList();
                break;
            //カレーメニュー
            case R.id.menuListOptionCurry:
                //カレーリスト生成
                _menuList = createCurryList();
                break;
        }
        //SimpleAdapterを選択されたメニューデータで生成
        SimpleAdapter adapter = new SimpleAdapter(MenuListActivity.this, _menuList, R.layout.row, FROM, TO);
        //アダプタ登録
        _lvMenu.setAdapter(adapter);
        //親クラスの同盟メソッドを呼び出し、その戻り値を返却
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu,View view,ContextMenu.ContextMenuInfo menuInfo){
        //親クラスの同盟メソッド呼び出し
        super.onCreateContextMenu(menu,view,menuInfo);
        //メニューインフレーターの取得
        MenuInflater inflater =getMenuInflater();
        //コンテクストメニュー用.xmlファイルのインフレート
        inflater.inflate(R.menu.menu_context_menu_list, menu);
        //コンテクストメニューのヘッダタイトルを設定
        menu.setHeaderTitle(R.string.menu_list_context_header);
    }
}

package jp.co.infinicreations.memo.Activity;
import static jp.co.infinicreations.memo.Constants.MemoConstants.*;

import jp.co.infinicreations.memo.Alarm.AlarmMonitor;
import jp.co.infinicreations.memo.Alarm.AlarmMonitorReceiver;
import jp.co.infinicreations.memo.Alarm.MemoAlarm;
import jp.co.infinicreations.memo.R;
import jp.co.infinicreations.memo.Dao.*;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {
    // ListView　用アダプタ
    SimpleAdapter mAdapter = null;
    // ListView に設定するデータ
    List<Map<String, String>> mList = null;
    List<Memo> mDaoList = null;
    // tanaka Start
    private static final int DETAIL_ACTIVITY = 1;
    private MyDBHelper mHelper = null;
    // tanaka End

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ListView 用アダプタのリストを生成
        mList = new ArrayList<Map<String,String>>();

        // ListView 用アダプタを生成
        mAdapter = new SimpleAdapter(
                this,
                mList,
                R.layout.list,
                new String [] {"date","title", "alarm_date"},
                new int[] {R.id.date,R.id.title, R.id.alarm_date}
        );

        // tanaka Start
        // ここでDBオブジェクト生成
        mHelper = new MyDBHelper(this);
        // tanaka End

        // ListView にアダプターをセット
        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(mAdapter);
        // ListView のアイテム選択イベント
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                // 編集画面に渡すデータをセットし、受け渡し
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Memo memoMain = new Memo(Long.parseLong(mList.get(pos).get("id")),mList.get(pos).get("content"));
                memoMain.setAlarm_datetime(mList.get(pos).get("alarm_date"));
                intent.putExtra("memo_data", memoMain);
                startActivityForResult(intent, DETAIL_ACTIVITY);

            }
        });

        // ListView をコンテキストメニューに登録
        registerForContextMenu(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // メニュー選択処理
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_memoAdd:
                //　[追加] 選択時の処理
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                startActivityForResult(intent, DETAIL_ACTIVITY);
                // tanaka End
                break;
            default:
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        switch (v.getId()){
            case R.id.listView:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                ListView list = (ListView) v;
                //Map map = (Map) list.getItemAtPosition(info.position);
                getMenuInflater().inflate(R.menu.menu_list, menu);
                break;

        }
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.listview_delete:
                AdapterView.AdapterContextMenuInfo info  = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                SQLiteDatabase db = mHelper.getWritableDatabase();
                MemoDao  mDAO = new MemoDao(db);
                mDAO.delete(Long.parseLong(mList.get(info.position).get("id")));
                mList.remove(info.position);

                // ListView のデータ変更を表示に反映
                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // ListView 用アダプタのデータをクリア
        mList.clear();

        // ここでDBデータすべて読み込む
        SQLiteDatabase db = mHelper.getReadableDatabase();
        MemoDao  mDAO = new MemoDao(db);

        mDaoList = mDAO.getAll();
        for(Memo memoTitleList : mDaoList) {
            //タイトルは本文先頭7文字まで
            String title = memoTitleList.getMemo();
            if(TITLE_HEADER_NUM < title.length()) title = title.substring(0,7) ;

            Map<String, String> map = new HashMap<String, String>();
            map.put("date", memoTitleList.getDatetime());
            map.put("title", title);
            map.put("content", memoTitleList.getMemo());
            map.put("id", Long.toString(memoTitleList.getId()));
            map.put("alarm_date", memoTitleList.getAlarm_datetime());
            mList.add(map);
        }

        // ListView のデータ変更を表示に反映
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == DETAIL_ACTIVITY){
            if(intent == null){
                return;
            }

            Boolean registFlg = intent.getBooleanExtra("regist_flg", false);
            Memo memoDetail = (Memo) intent.getSerializableExtra("memo_detail");

            SQLiteDatabase db = mHelper.getWritableDatabase();
            MemoDao  mDAO = new MemoDao(db);

            long memoId;
            if(registFlg){
                //保存処理（新規登録）
                memoId = mDAO.insert(memoDetail);
            } else {
                //保存処理（更新）
                mDAO.update(memoDetail);
                memoId = memoDetail.getId();
            }

            //Alerm登録処理
            MemoAlarm memoAlarm = new MemoAlarm();
            // パラメータ
            // 更新時はIDがあるけれど、新規登録時はどうするか？
            memoAlarm.setId(memoId);
            memoAlarm.setMemo(memoDetail.getMemo());
            memoAlarm.setDatetime(memoDetail.getAlarm_datetime());

            AlarmMonitor alermMonitor = new AlarmMonitor(MainActivity.this);

            alermMonitor.regist(memoAlarm);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHelper.close();
    }
}

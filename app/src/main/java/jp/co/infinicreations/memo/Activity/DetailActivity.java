package jp.co.infinicreations.memo.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import jp.co.infinicreations.memo.R;
import jp.co.infinicreations.memo.Dao.*;
import static jp.co.infinicreations.memo.Constants.MemoConstants.*;

/**
 * Created by Hirotoshi on 2016/08/25.
 */
public class DetailActivity extends Activity {
    private Memo memoDetail = null;
    private TextView textAlarmDate = null;
    private TextView textAlarmTime = null;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        EditText eTxtContent = (EditText)findViewById(R.id.detailContent);

        textAlarmDate = (TextView)findViewById(R.id.textAlarmDate);
        textAlarmTime = (TextView)findViewById(R.id.textAlarmTime);

        // メイン画面からのメモ情報受け取り
        // （情報がない場合（新規作成の場合）は、設定しない）
        Intent intent = getIntent();
        memoDetail = (Memo)intent.getSerializableExtra("memo_data");
        if (memoDetail != null) {
            eTxtContent.setText(memoDetail.getMemo());

            // アラーム日時
            if(memoDetail.getAlarm_datetime() != null) {
                String[] alarm = memoDetail.getAlarm_datetime().split("　", 0);
                textAlarmDate.setText(alarm[0]);
                if(alarm.length >= 1){
                    textAlarmTime.setText("　" + alarm[1]);
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_alarmAdd:
                // アラーム設定用画面の表示
                // Dialogは二連続、日付→時間
                int year;
                int month;
                int day;

                String tempDate = textAlarmDate.getText().toString();
                if(!tempDate.equals("")){
                    year = Integer.parseInt(tempDate.substring(0, 4));
                    month = Integer.parseInt(tempDate.substring(5, 7)) - 1;
                    day = Integer.parseInt(tempDate.substring(8, 10));
                } else {
                    final Calendar calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                }

                datePickerDialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                textAlarmDate.setText(String.valueOf(year) + "年" +
                                        String.valueOf(monthOfYear + 1) + "月" +
                                        String.valueOf(dayOfMonth) + "日");

                                datePickerDialog.dismiss();
                                timePickerDialog.show();
                            }
                        },
                        year, month, day);

                // 時間
                String tempTime = textAlarmTime.getText().toString().replace("　", "");
                int hour;
                int minute;
                if(!tempTime.equals("")){
                    String[] arrHour = tempTime.split("時", 0);
                    hour = Integer.parseInt(arrHour[0]);
                    String[] arrMinute = arrHour[1].split("分", 0);
                    minute = Integer.parseInt(arrMinute[0]);
                } else {
                    hour = 0;
                    minute = 0;
                }

                timePickerDialog = new TimePickerDialog(
                        this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                textAlarmTime.setText("　" + String.valueOf(hourOfDay) + "時" +
                                        String.valueOf(minute) + "分");

                            }
                        },
                        hour, minute, true);

                datePickerDialog.show();
                timePickerDialog.hide();

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = this.getReturnData();
        setResult(RESULT_OK,intent);
        finish();

        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /*
       MainActivityに返却するデータ取得
     */
    private Intent getReturnData(){
        EditText textContent = (EditText)findViewById(R.id.detailContent);
        String content = textContent.getText().toString();

        // 内容が空の場合、保存しない
        if (content.isEmpty()) {
            return null;
        }

        // tanaka Start 新規作成の時
        if (memoDetail == null) {
            memoDetail = new Memo();
        }
        // tanaka End

        //メモ内容保持
        memoDetail.setMemo(content);
        //作成日時取得
        Date dateToday = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.JAPAN);
        memoDetail.setDatetime(sdf.format(dateToday));

        memoDetail.setAlarm_datetime(textAlarmDate.getText().toString() + textAlarmTime.getText().toString());

        // 登録はMainActivityで行う
        boolean registFlg = false;
        if(ID_UNREGISTERED == memoDetail.getId()){
            //保存処理（新規登録）
            registFlg = true;
        }

        Intent intent = new Intent();
        intent.putExtra("regist_flg", registFlg);
        intent.putExtra("memo_detail", memoDetail);

        return intent;
    }
}

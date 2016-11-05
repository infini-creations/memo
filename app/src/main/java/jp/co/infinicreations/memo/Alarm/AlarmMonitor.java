package jp.co.infinicreations.memo.Alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.text.DateFormat.getDateInstance;


/**
 * Created by kazut on 2016/08/27.
 */
public class AlarmMonitor {
    Activity con;
    private Date alarmDate;

    public AlarmMonitor(Activity con) {
        this.con = con;
    }

    public boolean regist(MemoAlarm memo){

        // GitHubテスト

        // データ変換
        this.convertDate(memo.getDatetime());

        // アラーム登録対象のメモか判定
        boolean isRegist = this.isAlarmRegist(memo);

        // アラーム対象の場合、AlarmManagerへ登録
        // AlarmManger#setでリマインド日時を設定
        if(isRegist){
            AlarmManager am = (AlarmManager)this.con.getSystemService(Context.ALARM_SERVICE); // AlramManager取得

            // AlaramManager用パラメータ
            // アラーム日時
            Calendar alarmDateTime = Calendar.getInstance();
            alarmDateTime.setTime(this.alarmDate);

            //  Intent ,第二パラメータはReceiver来るのインテント
            Intent intent = new Intent(con.getApplicationContext(), AlarmMonitorReceiver.class);
            //intent.putExtra("ID", memo.getId());
            intent.putExtra("MEMO", memo.getMemo());

            PendingIntent sender = PendingIntent.getBroadcast(con.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            am.set(AlarmManager.RTC, alarmDateTime.getTimeInMillis(), sender); // AlramManagerにPendingIntentを登録
        }

        return true;
    }

    private boolean isAlarmRegist(MemoAlarm memo){
        boolean isRegist = false;
        // 日時が過去の場合は登録しない。未来の場合のみ登録を行う。

        if(alarmDate == null){
            return false;
        }

        Date nowDate = new Date();

        if(alarmDate.compareTo(nowDate) > 0){
            isRegist = true;
        }

        return isRegist;
    }

    private void convertDate(String dateTime){

        try{
            DateFormat df = new SimpleDateFormat("yyyy年MM月dd日　H時m分");
            this.alarmDate = df.parse(dateTime);
        } catch (ParseException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

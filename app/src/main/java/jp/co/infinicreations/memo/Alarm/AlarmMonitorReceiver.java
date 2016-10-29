package jp.co.infinicreations.memo.Alarm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import jp.co.infinicreations.memo.Activity.MainActivity;
import jp.co.infinicreations.memo.Activity.PopupActivity;
import jp.co.infinicreations.memo.Notice.MemoNoticeNotificationBar;
import jp.co.infinicreations.memo.Notice.MemoNoticePopup;
import jp.co.infinicreations.memo.R;

import static jp.co.infinicreations.memo.Constants.MemoConstants.TITLE_HEADER_NUM;

/**
 * Created by kazut on 2016/09/17.
 */
public class AlarmMonitorReceiver extends BroadcastReceiver {
    MemoNoticeNotificationBar notice;


    @Override
    public void onReceive(Context context, Intent intent) {
        // 通知
        String memo = intent.getStringExtra("MEMO");
        //タイトルは本文先頭7文字まで
        if(TITLE_HEADER_NUM < memo.length()) memo= memo.substring(0,7) ;

        notice = new MemoNoticeNotificationBar(context);
        notice.setNoticeString(memo);
        notice.executeNotice();

        // ポップアップ（うまく動いてない）
        /*
        Intent popupIntent = new Intent(context, PopupActivity.class);
        popupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(popupIntent);
        */


        //Toast.makeText(context, "called ReceivedActivity", Toast.LENGTH_LONG).show();
    }
}

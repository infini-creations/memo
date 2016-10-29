package jp.co.infinicreations.memo.Notice;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import jp.co.infinicreations.memo.Activity.MainActivity;
import jp.co.infinicreations.memo.R;

/**
 * Created by kazut on 2016/10/21.
 */

public class MemoNoticeNotificationBar extends MemoNoticeBase implements MemoNoticeInterface {
    /**
     * メモ通知ベースクラスコンストラクタ
     *
     * @param cont
     */
    public MemoNoticeNotificationBar(Context cont) {
        super(cont);
    }

    @Override
    public void executeNotice() {

        Notification.Builder n = new Notification.Builder(m_cont);
        // 通知するタイミング
        n.setWhen(System.currentTimeMillis());
        // アイコン
        n.setSmallIcon(R.drawable.ic_launch);
        // ステータスバーに表示されるテキスト
        n.setTicker(this.getNoticeString());
        // Notificationを開いたときに表示されるタイトル
        n.setContentTitle("期限になったメモがあります");
        // Notificationを開いたときに表示されるサブタイトル
        n.setContentText(this.getNoticeString());
        // intent
        n.setContentIntent(contentIntent());
        n.setAutoCancel(true);

        // Notificationを作成して通知
        NotificationManager nm = (NotificationManager) m_cont.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, n.build());
    }

    private PendingIntent contentIntent(){
        Intent intent = new Intent(m_cont.getApplicationContext(), MainActivity.class);

        PendingIntent pi = PendingIntent.getActivity(m_cont, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        return pi;
    }

    @Override
    public void close() {

    }
}

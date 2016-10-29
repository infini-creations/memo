package jp.co.infinicreations.memo.Notice;

import android.content.res.Resources;
import android.view.View;
import android.app.Activity;

public interface MemoNoticeInterface {
    final public static int NOTICE_POPUP =1;        //PopupWindowによる通知
    final public static int NOTICE_NOTICEAREA=2;   //通知領域による通知
    final public static int NOTICE_MAIL=3;          //メールによる通知

    public void setNoticeString(String s);
    public void executeNotice();
    public void close();
}

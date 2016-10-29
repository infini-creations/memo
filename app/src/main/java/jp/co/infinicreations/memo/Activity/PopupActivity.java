package jp.co.infinicreations.memo.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jp.co.infinicreations.memo.Notice.MemoNoticePopup;
import jp.co.infinicreations.memo.R;

public class PopupActivity extends Activity {
    MemoNoticePopup popup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);


    }

    @Override
    protected void onStart() {
        super.onStart();

        this.displayPopup();
    }

    private void displayPopup(){
        // レイアウト設定
        View popupView = getLayoutInflater().inflate(R.layout.popup_window, null);

        popup = new MemoNoticePopup(PopupActivity.this, popupView, getResources());
        popup.setNoticeString("YYYY-MM-DD HH:MM 今日は○○の予定です。");
        popup.executeNotice();

    }

    @Override
    protected void onDestroy() {
        popup.close();
        super.onDestroy();
    }
}

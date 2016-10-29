package jp.co.infinicreations.memo.Notice;

import android.content.res.Resources;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;

import jp.co.infinicreations.memo.R;

public class MemoNoticePopup extends MemoNoticeBase implements MemoNoticeInterface{
    private PopupWindow popupWin;

    /**
     * コンストラクタ
     * @param act 　Activity
     * @param v　View
     * @param res　Resources
     */
    public MemoNoticePopup(Activity act, View v, Resources res) {

        super(act, v, res);
    }
    /**
     * 通知実行
     */
    public void executeNotice() {
        //ポップアップに表示するレイアウトの設定*/
        LinearLayout popLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.popup_window, null);
        ((TextView) popLayout.findViewById(R.id.popup_text)).setText(getNoticeString());
        popLayout.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWin.isShowing()) {
                    //ポップアップ画面を閉じる
                    popupWin.dismiss();
                }
            }
        });

        /*ポップアップの作成*/
        popupWin = new PopupWindow();
        popupWin.setWindowLayoutMode(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        popupWin.setContentView(popLayout);
        popupWin.setBackgroundDrawable(null);
        // ここで例外が発生
        // activityが動いている？
        try{
            popupWin.showAtLocation(popLayout.findViewById(R.id.popup_text), Gravity.CENTER, 0, 0);
        } catch (Exception e){
            Log.e("error", e.getMessage());
        }
        //popupWin.showAsDropDown(getView(), 0, 0);
    }

    /**
     * クローズ処理
     */
    public void close()
    {
        if (popupWin != null && popupWin.isShowing()) {
            popupWin.dismiss();
        }
    }
}

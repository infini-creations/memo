package jp.co.infinicreations.memo.Notice;

import android.content.Context;
import android.content.res.Resources;
import android.app.Activity;
import android.view.View;

public abstract class MemoNoticeBase implements MemoNoticeInterface{
    /**
     * 通知文字列
     */
    private String m_noticeString = "";
    public Activity m_act;
    private View m_v;
    private Resources m_res;
    protected Context m_cont;

    /**
     * メモ通知ベースクラスコンストラクタ
     * @param act
     * @param v
     * @param res
     */
    public MemoNoticeBase(Activity act, View v, Resources res)
    {
        m_act = act;
        m_v = v;
        m_res = res;
    }

    /**
     * メモ通知ベースクラスコンストラクタ(Context指定)
     * @param cont
     */
    public MemoNoticeBase(Context cont)
    {
        m_cont = cont;
    }

    /**
     * Activity取得
     * @return AppCompatActivity
     */
    public Activity getActivity() {
        return m_act;
    }

    /**
     * View取得
     * @return View
     */
    public View getView() {
        return m_v;
    }

    /**
     * Resources取得
     * @return Resources
     */
    public Resources getResources() {
        return m_res;
    }
    /**
     * 通知する文字列を設定する
     * @return 無し
     * @param s メモ文字列
     */
    public void setNoticeString(String s)
    {
        m_noticeString = s;
        return;
    }

    public String getNoticeString()
    {
        return m_noticeString;
    }

    /**
     * 通知実行（抽象メソッド）
     */
    public abstract void executeNotice();

    /**
     * 解放処理
     */
    public abstract void close();
}

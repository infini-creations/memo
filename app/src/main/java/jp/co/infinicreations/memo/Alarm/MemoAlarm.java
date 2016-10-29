package jp.co.infinicreations.memo.Alarm;

/**
 * Created by kazut on 2016/08/27.
 */
public class MemoAlarm {

    /*
    　- id : Long
    　- memo : String
    　- datetime : String
    */

    private long id;
    private String memo;
    private String datetime;

    public long getId() {
        return id;
    }

    public String getMemo() {
        return memo;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

}

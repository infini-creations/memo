package jp.co.infinicreations.memo.Dao;


import java.io.Serializable;
import static jp.co.infinicreations.memo.Constants.MemoConstants.*;

public class Memo implements Serializable{

    private long id;
    private String memo;
    private String datetime;
    private String alarm_datetime;

    public Memo(){
        this.id = ID_UNREGISTERED;//idが採番されていない状態を表す
    }
    public Memo(long id, String memo){
        this.id =id;
        this.memo = memo;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setAlarm_datetime(String alarm_datetime) { this.alarm_datetime = alarm_datetime; }

    public String getAlarm_datetime() { return alarm_datetime; }
}

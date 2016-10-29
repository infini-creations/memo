package jp.co.infinicreations.memo.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MemoDao {
    private SQLiteDatabase db;

    private static final String TABLE_NAME = "memo";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MEMO = "memo";
    private static final String COLUMN_DATE = "datetime";
    private static final String COLUMN_ALARMDATE = "alarm_datetime";

    private static final String[] COLUMNS = {COLUMN_ID, COLUMN_MEMO, COLUMN_DATE, COLUMN_ALARMDATE};

    public MemoDao(SQLiteDatabase db) {
        this.db = db;
    }

    public MemoDao() {

    }

    public List<Memo> getAll() {
        List<Memo> entityList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, COLUMN_ID);
        while(cursor.moveToNext()) {
            Memo dto = new Memo();
            dto.setId(cursor.getLong(0));
            //dto.setId(cursor.getInt(0));
            dto.setMemo(cursor.getString(1));
            dto.setDatetime(cursor.getString(2));
            dto.setAlarm_datetime(cursor.getString(3));
            entityList.add(dto);
        }
        return entityList;
    }

    public Memo getMemo(long id) {
        String selection = COLUMN_ID + "=" + id;
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, selection, null, null, null, null);
        cursor.moveToNext();
        Memo dto = new Memo();
        dto.setId(cursor.getLong(0));
        //dto.setId(cursor.getInt(0));
        dto.setMemo(cursor.getString(1));
        dto.setDatetime(cursor.getString(2));
        dto.setAlarm_datetime(cursor.getString(3));
        return dto;
    }

    public long insert(Memo dto) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, dto.getMemo());
        values.put(COLUMN_DATE, dto.getDatetime());
        values.put(COLUMN_ALARMDATE, dto.getAlarm_datetime());
        long id = db.insert(TABLE_NAME, null, values);
        return id;
    }

    public void update(Memo dto) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, dto.getMemo());
        values.put(COLUMN_DATE, dto.getDatetime());
        values.put(COLUMN_ALARMDATE, dto.getAlarm_datetime());
        String whereClause = COLUMN_ID + "=" + dto.getId();
        db.update(TABLE_NAME, values, whereClause, null);
    }

    public void delete(long id) {
        String whereClause = COLUMN_ID + "=" + id;
        db.delete(TABLE_NAME, whereClause, null);
    }

    public void deleteAll() {
        db.delete(TABLE_NAME, null, null);
    }
}

package id.branditya.hacktivfinalproject1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "taskData";
    private static final String TABEL_TASK = "Task";

    private static final String KEY_ID = "id";
    private static final String TASK_NAME = "Task_name";

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_COUNTRY_TABLE = "CREATE TABLE " + TABEL_TASK + "(" + KEY_ID +
                " INTEGER PRIMARY KEY," + TASK_NAME + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_COUNTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABEL_TASK);
        onCreate(sqLiteDatabase);
    }

    void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK_NAME, task.getTaskName());

        db.insert(TABEL_TASK, null, values);
        db.close();
    }

    public List<Task> getAllTask() {
        List<Task> taskList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABEL_TASK;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setTaskName(cursor.getString(1));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABEL_TASK, KEY_ID + "=?", new String[]{String.valueOf(task.getId())});
        db.close();
    }

}

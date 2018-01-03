package tom.lenormand.todolistv2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tomle on 14/12/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper
{
    public static final String TASK_KEY = "id";
    public static final String TASK_INTITULE = "intitule";
    public static final String TASK_TASK = "task";

    public static final String TASK_TABLE_NAME = "tasks";
    public static final String TASK_TABLE_CREATE =
            "CREATE TABLE " + TASK_TABLE_NAME + " (" +
                    TASK_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TASK_INTITULE + " TEXT, " +
                    TASK_TASK + " TEXT);";
    public static final String TASK_TABLE_DROP = "DROP TABLE IF EXISTS " + TASK_INTITULE + ";";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASK_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(TASK_TABLE_DROP);
        onCreate(db);
    }
}

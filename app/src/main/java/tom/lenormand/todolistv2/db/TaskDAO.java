package tom.lenormand.todolistv2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by tomle on 12/12/2017.
 */

public class TaskDAO
{
    private DAOBase mDb = null;
    public static final String TABLE_NAME = "tasks";
    public static final String KEY = "id";
    public static final String INTITULE = "intitule";
    public static final String TASK = "task";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INTITULE + " TEXT, " + TASK + " REAL);";
    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public TaskDAO(Context context)
    {
        mDb = new DAOBase(context);
    }

    /**
     * @param m le métier à ajouter à la base
     */
    public void ajouter(Task m)
    {
        mDb.open();
        ContentValues value = new ContentValues();
        value.put(TaskDAO.INTITULE, m.getIntitule());
        value.put(TaskDAO.TASK, m.getTask());
        mDb.mDb.insert(TaskDAO.TABLE_NAME, null, value);
        mDb.close();
    }

    public void addTitle(String title)
    {
        mDb.open();
        ContentValues value = new ContentValues();
        value.put(TaskDAO.INTITULE, title);
        mDb.mDb.insert(TaskDAO.TABLE_NAME, null, value);
        mDb.close();
    }

    /**
     * supprimer dans la base de donner (utliser pour le boutton Done)
     * @param taskTitle titre du todo
     */
    public void supprimer(String taskTitle)
    {
        mDb.open();
        mDb.mDb.delete(TABLE_NAME, INTITULE + " = ?", new String[]{taskTitle});
        mDb.close();
    }

    /**
     * delete a single task
     * @param taskTitle title of the task
     * @param task the task itself
     */
    public void deleteTask(String taskTitle, String task)
    {
        mDb.open();
        mDb.mDb.delete(TABLE_NAME, INTITULE + " = ? AND " + TASK + " = ?", new String[]{taskTitle, task});
        mDb.close();
    }
    /**
     * @param m le métier modifié
     */
    public void modifier(Task m, String newTask)
    {
        mDb.open();
        ContentValues value = new ContentValues();
        value.put(TASK, newTask);
        mDb.mDb.update(TABLE_NAME, value, INTITULE + " = ? AND " + TASK + " = ?", new String[]{String.valueOf(m.getIntitule()), String.valueOf(m.getTask())});
        mDb.close();
    }

    public boolean checkTitles(String title)
    {
        mDb.open();

        Cursor cursor = mDb.mDb.rawQuery("select * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext())
        {
            int idxIntitule = cursor.getColumnIndex(INTITULE);

            if (title.equals(cursor.getString(idxIntitule)))
            {
                cursor.close();
                mDb.close();
                return (true);
            }
        }
        cursor.close();
        mDb.close();
        return (false);
    }

    public boolean              checkTasksExist(String title, String task)
    {
        mDb.open();

        Cursor cursor = mDb.mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext())
        {
            int idxTask = cursor.getColumnIndex(TASK);
            int idxName = cursor.getColumnIndex(INTITULE);

            if (task.equals(cursor.getString(idxTask)) && title.equals(cursor.getString(idxName)))
            {
                cursor.close();
                mDb.close();
                return (true);
            }
        }
        cursor.close();
        mDb.close();
        return (false);
    }

    public ArrayList<String>    getTasksName()
    {
        ArrayList<String>       taskNameList = new ArrayList<>();

        mDb.open();

        boolean isIn;
        String taskName;
        String listName;

        Cursor cursor= mDb.mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext())
        {
            int idx = cursor.getColumnIndex(INTITULE);

            taskName = cursor.getString(idx);
            isIn = false;

            for (int i = 0;i < taskNameList.size();i++)
            {
                listName = taskNameList.get(i);
                if (listName.equals(taskName))
                    isIn = true;
            }
            if (!isIn)
                taskNameList.add(taskName);
        }
        cursor.close();
        mDb.close();
        return (taskNameList);
    }

    public ArrayList<String>      checkTasks(String title)
    {
        ArrayList<String>       taskList = new ArrayList<>();
        mDb.open();

        Cursor cursor = mDb.mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext())
        {
            int idxName = cursor.getColumnIndex(INTITULE);
            int idx = cursor.getColumnIndex(TASK);

            if (title.equals(cursor.getString(idxName)))
            {
                String tmp = cursor.getString(idx);
                if (tmp != null)
                    taskList.add(cursor.getString(idx));
            }
        }

        mDb.close();
        cursor.close();
        return (taskList);
    }
}

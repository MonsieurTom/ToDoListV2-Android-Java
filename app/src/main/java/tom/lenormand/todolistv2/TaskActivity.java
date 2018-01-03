package tom.lenormand.todolistv2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import tom.lenormand.todolistv2.db.Task;
import tom.lenormand.todolistv2.db.TaskDAO;

/**
 * Created by tomle on 19/12/2017.
 */

public class TaskActivity extends AppCompatActivity
{
    static String   taskName;

    TaskDAO         taskDAO;
    ListView        taskListView;
    TextView        taskTitle;
    Button          addToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        /*
         * initialize testView.
         */
        taskDAO = new TaskDAO(this);
        taskTitle = (TextView) findViewById(R.id.taskNameTitle);
        taskListView = (ListView) findViewById(R.id.ListView_tasks);
        // get the intent from which this activity is called.
        Intent intent = getIntent();
        // fetch value from key-value pair and make it visible on TextView.
        taskName = intent.getStringExtra("selected-item");
        taskTitle.setText(taskName);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTask);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("Using ToolBar");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(TaskActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /*
         * Initialize add Button
         */
        addToDo = (Button) findViewById(R.id.task_activity_add_button_todo);
        addToDo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(TaskActivity.this);
                LayoutInflater layoutInflater = TaskActivity.this.getLayoutInflater();
                final View cambioView = layoutInflater.inflate(R.layout.custom_alert_dialog, null);
                dialog.setView(cambioView);

                final EditText titleEditText = (EditText) findViewById(R.id.editTextDialog);

                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        final EditText taskEditText = (EditText) cambioView.findViewById(R.id.editTextDialog);

                        String task = taskEditText.getText().toString();

                        if (taskDAO.checkTasksExist(taskName, task))
                        {
                            popup_wrong();
                            dialogInterface.cancel();
                            return;
                        }
                        taskDAO.ajouter(new Task(taskName, task));
                        updateUI();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogg, int id) {
                        dialogg.cancel();
                    }
                });
                dialog.show();
            }
        });
        updateUI();
    }

    private void updateUI()
    {
        ArrayList<String> taskList = taskDAO.checkTasks(taskName);

        TaskToDoAdaptater adapter = new TaskToDoAdaptater(this, taskList);
        taskListView.setAdapter(adapter);
    }

    public void DeleteSingleTask(View view)
    {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.text_view_task);
        String task = String.valueOf(taskTextView.getText());
        taskDAO.deleteTask(taskName, task);
        updateUI();
    }

    public void editToDo(View view)
    {
        final View parent = (View) view.getParent();;
        TextView taskTextView = (TextView) parent.findViewById(R.id.text_view_task);
        final String firstTask = String.valueOf(taskTextView.getText());

        AlertDialog.Builder dialog = new AlertDialog.Builder(TaskActivity.this);
        LayoutInflater layoutInflater = TaskActivity.this.getLayoutInflater();
        final View cambioView = layoutInflater.inflate(R.layout.custom_alert_dialog_task, null);
        dialog.setView(cambioView);

        final EditText taskEditText = (EditText) cambioView.findViewById(R.id.editTextDialog2);
        taskEditText.setText(firstTask);
        taskEditText.setHint("Edit...");

        dialog.setPositiveButton("Edit", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                final EditText taskEditText = (EditText) cambioView.findViewById(R.id.editTextDialog2);
                String task = taskEditText.getText().toString();

                if (task.equals("")) {
                    popup_wrong();
                    dialogInterface.cancel();
                    return;
                }

                taskDAO.modifier(new Task(taskName, firstTask), task);
                updateUI();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogg, int id) {
                dialogg.cancel();
            }
        });
        dialog.show();
    }

    public void popup_wrong()
    {
        final TextView text = new TextView(this);
        text.setText("");

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Problem:").setMessage("something went wrong (already existing title or incomplete).")
                .setView(text).setPositiveButton("Close", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                }).create();
        dialog.show();
    }
}

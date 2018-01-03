package tom.lenormand.todolistv2;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tom.lenormand.todolistv2.db.Task;
import tom.lenormand.todolistv2.db.TaskDAO;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    TaskDAO taskDAO = null;
    ListView taskNameList = null;
    Button addButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskDAO = new TaskDAO(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        taskNameList = (ListView) findViewById(R.id.list_taskName);
        taskNameList.setOnItemClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("Using ToolBar");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        addButton = (Button) findViewById(R.id.add_task_name_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
                final View cambioView = layoutInflater.inflate(R.layout.custom_alert_dialog, null);
                dialog.setView(cambioView);

                final EditText titleEditText = (EditText) findViewById(R.id.editTextDialog);

                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        final EditText titleEditText = (EditText) cambioView.findViewById(R.id.editTextDialog);

                        String title = titleEditText.getText().toString();

                        if (title.equals("") || taskDAO.checkTitles(title))
                        {
                            popup_wrong();
                            dialogInterface.cancel();
                            return;
                        }

                        taskDAO.addTitle(title);
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

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3)
    {
        TextView listText = (TextView) view.findViewById(R.id.task_title);
        String text = listText.getText().toString();
        Intent intent = new Intent(MainActivity.this, TaskActivity.class);
        intent.putExtra("selected-item", text);
        startActivity(intent);
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

    public void deleteTask(View view)
    {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String taskTitle = String.valueOf(taskTextView.getText());
        taskDAO.supprimer(taskTitle);
        updateUI();
    }

    private void updateUI()
    {
        ArrayList<String> taskList = taskDAO.getTasksName();

        TaskNameAdaptater adapter = new TaskNameAdaptater(this, taskList);
        taskNameList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_one:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

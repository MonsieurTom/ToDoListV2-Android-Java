package tom.lenormand.todolistv2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tomle on 19/12/2017.
 */

public class TaskToDoAdaptater extends ArrayAdapter<String>
{
    public TaskToDoAdaptater(Context context, List<String> args)
    {
        super(context, 0, args);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_tasks, parent, false);
        }

        TaskToDoAdaptater.TaskToDoViewHolder viewHolder = (TaskToDoAdaptater.TaskToDoViewHolder) convertView.getTag();
        if(viewHolder == null)
        {
            viewHolder = new TaskToDoAdaptater.TaskToDoViewHolder();
            viewHolder.task = (TextView) convertView.findViewById(R.id.text_view_task);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        String task = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.task.setText(task);

        return convertView;
    }

    private class TaskToDoViewHolder{
        public TextView task;
    }
}

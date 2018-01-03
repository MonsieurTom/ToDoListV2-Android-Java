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
 * Created by tomle on 18/12/2017.
 */

public class TaskNameAdaptater extends ArrayAdapter<String>
{
    public TaskNameAdaptater(Context context, List<String> args)
    {
        super(context, 0, args);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_name, parent, false);
        }

        TaskNameViewHolder viewHolder = (TaskNameViewHolder) convertView.getTag();
        if(viewHolder == null)
        {
            viewHolder = new TaskNameViewHolder();
            viewHolder.Title = (TextView) convertView.findViewById(R.id.task_title);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        String task = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.Title.setText(task);

        return convertView;
    }

    private class TaskNameViewHolder{
        public TextView Title;
    }
}

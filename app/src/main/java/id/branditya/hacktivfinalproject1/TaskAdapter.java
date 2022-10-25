package id.branditya.hacktivfinalproject1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends BaseAdapter {
    private Activity context;
    ArrayList<Task> tasks;
    SQLiteDatabaseHandler db;

    public TaskAdapter(Activity context, ArrayList<Task> tasks, SQLiteDatabaseHandler db) {
        this.context = context;
        this.tasks = tasks;
        this.db = db;
    }

    public static class ViewHolder {
        TextView tvTaskName;
        Button btnCompleteTask;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder vh;
        if (view==null) {
            vh = new ViewHolder();
            row = inflater.inflate(R.layout.item_to_do_list, null, true);

            vh.tvTaskName = row.findViewById(R.id.tv_task);
            vh.btnCompleteTask = row.findViewById(R.id.btn_complete_task);

            row.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.tvTaskName.setText(tasks.get(position).getTaskName());

        vh.btnCompleteTask.setOnClickListener(view1 -> {
            db.deleteTask(tasks.get(position));
            tasks = (ArrayList<Task>) db.getAllTask();
            notifyDataSetChanged();
        });
        return row;
    }
}

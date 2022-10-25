package id.branditya.hacktivfinalproject1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Task> tasks;
    SQLiteDatabaseHandler db;
    ListView listView;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new SQLiteDatabaseHandler(this);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lv_task_list);

        tasks = (ArrayList<Task>) db.getAllTask();
        TaskAdapter taskAdapter = new TaskAdapter(this, tasks, db);
        listView.setAdapter(taskAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_btn_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btn_add) {
            addList();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addList() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_list, null);
        dialogBuilder.setView(dialogView);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        TextView btnCancel = dialogView.findViewById(R.id.btn_cancel_add_task);
        TextView btnConfirm = dialogView.findViewById(R.id.btn_confirm_add_task);
        EditText etTaskName = dialogView.findViewById(R.id.et_task_name);
        btnCancel.setOnClickListener(view -> {
            alertDialog.dismiss();
        });
        btnConfirm.setOnClickListener(view -> {
            String taskName = etTaskName.getText().toString();
            Task task =new Task(taskName);
            db.addTask(task);
            if (taskAdapter == null) {
                taskAdapter = new TaskAdapter(this, tasks, db);
                listView.setAdapter(taskAdapter);
            }
            taskAdapter.tasks = (ArrayList<Task>) db.getAllTask();
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            alertDialog.dismiss();
        });
    }
}
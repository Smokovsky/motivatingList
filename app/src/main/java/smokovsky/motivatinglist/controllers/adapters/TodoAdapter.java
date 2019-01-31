package smokovsky.motivatinglist.controllers.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.activities.MainActivity;
import smokovsky.motivatinglist.controllers.fileController.FileIO;
import smokovsky.motivatinglist.controllers.fragments.TodoListFragment;
import smokovsky.motivatinglist.model.Todo;

public class TodoAdapter extends ArrayAdapter<Todo> {

    private ArrayList<Todo> todoList;

    public TodoAdapter(Context context, ArrayList<Todo> todoList) {
        super(context, R.layout.todo_row, todoList);
        this.todoList = todoList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View todoRow = layoutInflater.inflate(R.layout.todo_row, parent, false);

        TextView todoName = (TextView) todoRow.findViewById(R.id.todo_name);
        Button doneTodoButton = (Button) todoRow.findViewById(R.id.todo_done_button);
        Button deleteTodoButton = (Button) todoRow.findViewById(R.id.todo_delete_button);
        LinearLayout background = (LinearLayout) todoRow.findViewById(R.id.todo_background);

        deleteTodoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Task has been deleted", Toast.LENGTH_SHORT).show();
                if(getItem(position).getTodoStatus())
                    MainActivity.profile.addPoints(-(getItem(position).getTodoRewardPoints()));
                todoList.remove(position);
                TodoListFragment.sortTodoListByStatus(todoList);
//                todo: tutaj zapisujemy dane
                FileIO.saveDataToFile(todoList, getContext());
                notifyDataSetChanged();
            }
        });

        doneTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean todoStatus = getItem(position).getTodoStatus();
                if(todoStatus) {
                    MainActivity.profile.addPoints(-getItem(position).getTodoRewardPoints());
                } else {
                    MainActivity.profile.addPoints(getItem(position).getTodoRewardPoints());
                }
                getItem(position).setTodoStatus(!todoStatus);
                TodoListFragment.sortTodoListByStatus(todoList);
                notifyDataSetChanged();
            }
        });

        if(getItem(position).getTodoStatus()) {
            background.setBackgroundColor(Color.rgb(128,128,128));
            todoName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            doneTodoButton.setText("Undo");
            doneTodoButton.setBackgroundColor(Color.rgb(96,96,96));
            deleteTodoButton.setBackgroundColor(Color.rgb(96,96,96));
        } else {
            background.setBackgroundColor(Color.rgb(239, 239, 239));
            todoName.setPaintFlags(0);
            doneTodoButton.setText("Done");
            doneTodoButton.setBackgroundColor(Color.rgb(192,192,192));
            deleteTodoButton.setBackgroundColor(Color.rgb(192,192,192));
        }

        todoName.setText(getItem(position).getTodoName() + " (" + getItem(position).getTodoRewardPoints() + ")");
        return todoRow;
    }
}

package smokovsky.motivatinglist.controllers.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        final Todo todo = getItem(position);

        TextView todoName = (TextView) todoRow.findViewById(R.id.todo_name);
        TextView todoRepeatable = (TextView) todoRow.findViewById(R.id.todo_repeatable);
        TextView todoFinishDate = (TextView) todoRow.findViewById(R.id.todo_finish_date);
        Button doneTodoButton = (Button) todoRow.findViewById(R.id.todo_done_button);
        TextView doneTodoHighlight = (TextView) todoRow.findViewById(R.id.todo_done_button_highlight);
        Button deleteTodoButton = (Button) todoRow.findViewById(R.id.todo_delete_button);
        ConstraintLayout background = (ConstraintLayout) todoRow.findViewById(R.id.todo_background);

        deleteTodoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                todoList.remove(position);
                Toast.makeText(getContext(), "Task has been deleted", Toast.LENGTH_SHORT).show();
                TodoListFragment.sortTodoListByStatus(todoList);
                TodoListFragment.updatePointsMeter(todoList);
                FileIO.saveDataToFile(MainActivity.profile, getContext());
                notifyDataSetChanged();
            }
        });

        doneTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(todo.getTodoRepeatable() == true) {
                    Todo repeatableInstance = new Todo(todo);
                    repeatableInstance.setTodoStatus(true);
                    repeatableInstance.setTodoFinishDateTime();
                    todoList.add(repeatableInstance);
                } else {
                    todo.setTodoFinishDateTime();
                    todo.setTodoStatus(!todo.getTodoStatus());
                }
                TodoListFragment.sortTodoListByStatus(todoList);
                TodoListFragment.updatePointsMeter(todoList);

                FileIO.saveDataToFile(MainActivity.profile, getContext());
                notifyDataSetChanged();
            }
        });

        // Zmiana wyglądu na podstawie todoStatus
        if(todo.getTodoStatus()) {
            background.setBackgroundColor(Color.rgb(128,128,128));
            todoName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            doneTodoButton.setText("UNDO");
            doneTodoHighlight.setBackgroundColor(0xFFC0B301);
            doneTodoButton.setBackgroundColor(Color.rgb(96,96,96));
            deleteTodoButton.setBackgroundColor(Color.rgb(96,96,96));
            todoFinishDate.setVisibility(View.VISIBLE);

            // Wpis powtarzalnego zadania - usuwamy undo
            if(todo.getTodoRepeatable()==true) {
                doneTodoButton.setVisibility(View.INVISIBLE);
                doneTodoHighlight.setVisibility(View.INVISIBLE);
            }

        } else {
            background.setBackgroundColor(Color.rgb(239, 239, 239));
            todoName.setPaintFlags(0);
            doneTodoButton.setText("\u2713");
            doneTodoHighlight.setBackgroundColor(0xFF22BB06);
            doneTodoButton.setBackgroundColor(Color.rgb(192,192,192));
            deleteTodoButton.setBackgroundColor(Color.rgb(192,192,192));
            todoFinishDate.setVisibility(View.INVISIBLE);
        }
        // Ukrywanie repeatable dla nie repeatable
        if(!todo.getTodoRepeatable())
            todoRepeatable.setHeight(0);

        todoName.setText(todo.getTodoName() + "\n" + todo.getTodoRewardPoints() + " points");
        todoFinishDate.setText("Done " + todo.getTodoFinishDateTimeString());
        return todoRow;
    }
}

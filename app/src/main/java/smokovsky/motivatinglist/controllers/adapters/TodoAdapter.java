package smokovsky.motivatinglist.controllers.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.activities.MainActivity;
import smokovsky.motivatinglist.controllers.fileController.FileIO;
import smokovsky.motivatinglist.controllers.fragments.TodoListFragment;
import smokovsky.motivatinglist.model.Todo;

public class TodoAdapter extends ArrayAdapter<Todo> {

    private TextView pointsCounter;
    private ArrayList<Todo> todoList;

    public TodoAdapter(Context context, ArrayList<Todo> todoList, TextView pointsCounter) {
        super(context, R.layout.todo_row, todoList);
        this.todoList = todoList;
        this.pointsCounter = pointsCounter;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

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
                if (Objects.requireNonNull(todo).getTodoStatus())
                    MainActivity.subtractPoints(todo.getTodoRewardPoints());
                Toast.makeText(getContext(), "Todo has been deleted", Toast.LENGTH_SHORT).show();
                TodoListFragment.sortTodoList(todoList);
                updatePointsCounter();
                FileIO.saveDataToFile(MainActivity.profile, getContext());
                notifyDataSetChanged();
            }
        });

        doneTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.requireNonNull(todo).getTodoRepeatable()) {
                    Todo repeatableInstance = new Todo(todo);
                    repeatableInstance.setTodoStatus(true);
                    repeatableInstance.setTodoFinishDateTime();
                    todoList.add(repeatableInstance);
                    MainActivity.addPoints(todo.getTodoRewardPoints());
                } else {
                    todo.setTodoFinishDateTime();
                    todo.setTodoStatus(!todo.getTodoStatus());
                    if (todo.getTodoStatus())
                        MainActivity.addPoints(todo.getTodoRewardPoints());
                    else
                        MainActivity.subtractPoints(todo.getTodoRewardPoints());
                }

                TodoListFragment.sortTodoList(todoList);
                updatePointsCounter();

                FileIO.saveDataToFile(MainActivity.profile, getContext());
                notifyDataSetChanged();
            }
        });

        // Zmiana wyglądu na podstawie todoStatus
        if(Objects.requireNonNull(todo).getTodoStatus()) {
            todoName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            doneTodoButton.setText("UNDO");
            doneTodoButton.setBackgroundColor(Color.rgb(96,96,96));
            doneTodoHighlight.setBackgroundColor(Color.rgb(192,179,1));
            deleteTodoButton.setBackgroundColor(Color.rgb(96,96,96));
            background.setBackgroundColor(Color.rgb(128,128,128));
            todoFinishDate.setVisibility(View.VISIBLE);

            // Ukrycie undo dla wpisu wykonanej instacji powtarzalnego zadania
            if(Objects.requireNonNull(todo).getTodoRepeatable()) {
                doneTodoButton.setVisibility(View.INVISIBLE);
                doneTodoHighlight.setVisibility(View.INVISIBLE);
            }

        } else {
            todoName.setPaintFlags(0);
            doneTodoButton.setText("\u2713");
            doneTodoHighlight.setBackgroundColor(Color.rgb(34,187,6));
            doneTodoButton.setBackgroundColor(Color.rgb(192,192,192));
            deleteTodoButton.setBackgroundColor(Color.rgb(192,192,192));
            background.setBackgroundColor(Color.rgb(239, 239, 239));
            todoFinishDate.setVisibility(View.INVISIBLE);
        }

        // Ukrywanie symbuolu repeatable dla nie repeatable
        if(!todo.getTodoRepeatable())
            todoRepeatable.setHeight(0);

        todoName.setText(String.format("%s%n%s%s",todo.getTodoName(),todo.getTodoRewardPoints()," points"));
        todoFinishDate.setText(String.format("%s %s","Done ",todo.getTodoFinishDateTimeString()));

        return todoRow;
    }

    private void updatePointsCounter(){
        pointsCounter.setText(String.format("%s%s","Reward points: ",Integer.toString(MainActivity.profile.getPoints())));
    }
}
package smokovsky.motivatinglist.controllers.adapters;

import android.content.Context;
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
import smokovsky.motivatinglist.controllers.fileController.FileIO;
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
        Button doneTodo = (Button) todoRow.findViewById(R.id.todo_done_button);
        Button deleteTodo = (Button) todoRow.findViewById(R.id.todo_delete_button);
        LinearLayout background = (LinearLayout) todoRow.findViewById(R.id.todo_background);


        deleteTodo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Task " + position + " has been deleted", Toast.LENGTH_SHORT).show();
                todoList.remove(position);
//                todo: tutaj zapisujemy dane
                FileIO.saveDataToFile(todoList, getContext());
                notifyDataSetChanged();
            }
        });

        doneTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        todoName.setText(getItem(position).getTodoName());

        return todoRow;
    }
}

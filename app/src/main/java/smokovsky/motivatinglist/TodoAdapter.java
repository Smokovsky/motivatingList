package smokovsky.motivatinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TodoAdapter extends ArrayAdapter<String> {

    private ArrayList<String> todos;

    TodoAdapter(Context context, ArrayList<String> todos) {
        super(context, R.layout.todo_row, todos);
        this.todos = todos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View todoRow = layoutInflater.inflate(R.layout.todo_row, parent, false);
        String singleTodo = getItem(position);
        TextView todoName = (TextView) todoRow.findViewById(R.id.todo_name);
        Button deleteTodo = (Button) todoRow.findViewById(R.id.delete_todo_button);

        deleteTodo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Task " + position + " has been deleted", Toast.LENGTH_SHORT).show();
                todos.remove(position);
                notifyDataSetChanged();
            }
        });

        todoName.setText(singleTodo);

        return todoRow;
    }
}

package smokovsky.motivatinglist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TodoListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener  {

    private EditText todoName;
    private Button addNewTaskButton;
    private ListView todoListView;

    public ArrayList<String> todoList;
    private ArrayAdapter<String> todoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        todoName = view.findViewById(R.id.new_task_name_text);
        addNewTaskButton = view.findViewById(R.id.add_new_task_btn);
        todoListView = view.findViewById(R.id.tasks_list);

        todoList = FileIO.loadDataFromFile(getContext());

        todoAdapter = new TodoAdapter(getContext(), todoList);
        todoListView.setAdapter(todoAdapter);

        addNewTaskButton.setOnClickListener(this);
        todoListView.setOnItemClickListener(this);

        todoAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_new_task_btn:
                String itemEntered = todoName.getText().toString();
                addTodo(itemEntered);
                todoName.setText("");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openEditTodoFragment(position);
//        tasks.remove(position);
//        adapter.notifyDataSetChanged();
//        FileIO.saveDataToFile(tasks, getContext());
        Toast.makeText(getContext(), "Task " + position + " has been clicked", Toast.LENGTH_SHORT).show();
    }

    private void openEditTodoFragment(int position){
        try {
            // Podmieniamy widok z nowo stworzonym fragmentem
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.to_do_list_fragment, TodoEditFragment.newInstance(todoList, todoAdapter, position))
                    .addToBackStack(null)
                    .commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addTodo(String todoName){
        todoAdapter.add(todoName);
        FileIO.saveDataToFile(todoList, getContext());
        Toast.makeText(getContext(), "Task " + (todoList.size()-1) + " added succesfully", Toast.LENGTH_SHORT).show();
    }
}

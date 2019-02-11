package smokovsky.motivatinglist.controllers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.activities.MainActivity;
import smokovsky.motivatinglist.controllers.adapters.TodoAdapter;
import smokovsky.motivatinglist.model.Todo;

public class TodoListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener  {

    private Button addNewTaskButton;
    private ListView todoListView;
    public static TextView pointsMeter;


    public ArrayList<Todo> todoList = MainActivity.profile.getTodoList();
    private ArrayAdapter<Todo> todoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        addNewTaskButton = view.findViewById(R.id.todo_list_add_button);
        todoListView = view.findViewById(R.id.todo_list_todo_list);
        pointsMeter = view.findViewById(R.id.todo_list_points_meter);

        todoAdapter = new TodoAdapter(getContext(), todoList);
        todoListView.setAdapter(todoAdapter);
        addNewTaskButton.setOnClickListener(this);
        todoListView.setOnItemClickListener(this);

        sortTodoListByStatus(todoList);
        updatePointsMeter(todoList);
        todoAdapter.notifyDataSetChanged();

        return view;
    }

    static public void sortTodoListByStatus(ArrayList<Todo> todoList){
        Collections.sort(todoList, new Comparator<Todo>() {
            @Override
            public int compare(Todo o1, Todo o2) {
                if(o1.getTodoStatus() == o2.getTodoStatus())
                    if(o1.getTodoStatus())
                        return o2.getTodoFinishDateTime().compareTo(o1.getTodoFinishDateTime());
                    else
                        return o2.getTodoSetupDateTime().compareTo(o1.getTodoSetupDateTime());
                else
                    return Boolean.compare(o1.getTodoStatus(),o2.getTodoStatus());
            }
        });
    }

    static public void sortTodoList(ArrayList<Todo> todoList) {
        Collections.sort(todoList, new Comparator<Todo>() {
            @Override
            public int compare(Todo o1, Todo o2) {
                if(o1.getTodoStatus() == o2.getTodoStatus())
                    return o1.getTodoSetupDateTime().compareTo(o2.getTodoSetupDateTime());
                else
                    return Boolean.compare(o1.getTodoStatus(),o2.getTodoStatus());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openEditTodoFragment(position);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.todo_list_add_button:
                openNewTodoFragment();
                break;
        }
    }

    private void openNewTodoFragment(){
        try {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_todo_list, TodoNewFragment.newInstance(todoList, todoAdapter))
                    .addToBackStack(null)
                    .commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openEditTodoFragment(int position){
        try {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_todo_list, TodoEditFragment.newInstance(todoList, todoAdapter, position))
                    .addToBackStack(null)
                    .commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updatePointsMeter(ArrayList<Todo> todoList){
        int points = 0;
        for(int i = 0; i < todoList.size(); i++)
            if(todoList.get(i).getTodoStatus())
                points += todoList.get(i).getTodoRewardPoints();
        MainActivity.profile.setPoints(points);
        pointsMeter.setText("Reward points: " + points);
    }


}

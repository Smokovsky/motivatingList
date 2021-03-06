package smokovsky.motivatinglist.controllers.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.activities.MainActivity;
import smokovsky.motivatinglist.controllers.adapters.TodoAdapter;
import smokovsky.motivatinglist.model.Todo;

public class TodoListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener  {

    private ArrayList<Todo> todoList = MainActivity.profile.getTodoList();
    private ArrayAdapter<Todo> todoAdapter;
    private TextView pointsCounter;

    public static TodoListFragment newInstance(TextView pointsCounter) {
        TodoListFragment fragment = new TodoListFragment();
        fragment.pointsCounter = pointsCounter;

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        Button addNewTaskButton = view.findViewById(R.id.todo_list_add_button);
        ListView todoListView = view.findViewById(R.id.todo_list_todo_list);

        todoAdapter = new TodoAdapter(getContext(), todoList, pointsCounter);
        todoListView.setAdapter(todoAdapter);
        addNewTaskButton.setOnClickListener(this);
        todoListView.setOnItemClickListener(this);

        sortTodoList(todoList);
        updatePointsCounter();
        todoAdapter.notifyDataSetChanged();

        return view;
    }

    public static void sortTodoList(ArrayList<Todo> todoList){
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

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
            Objects.requireNonNull(getActivity())
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
            Objects.requireNonNull(getActivity())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_todo_list, TodoEditFragment.newInstance(todoList, todoAdapter, position, pointsCounter))
                    .addToBackStack(null)
                    .commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updatePointsCounter(){
        pointsCounter.setText(String.format("%s %s",getString(R.string.reward_points),Integer.toString(MainActivity.profile.getPoints())));
    }
}

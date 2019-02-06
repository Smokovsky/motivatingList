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


    public ArrayList<Todo> todoList;
    private ArrayAdapter<Todo> todoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        addNewTaskButton = view.findViewById(R.id.add_new_task_btn);
        todoListView = view.findViewById(R.id.tasks_list);
        pointsMeter = view.findViewById(R.id.points_meter);


//          PROBLEM TUTAJ
//        todo: tutaj wczytujemy dane
//        todoList = FileIO.loadDataFromFile(getContext());


//        --------------------------------------------------------
//        AKTUALNIE AKTYWNE
//        todo: tutaj w zastepstwie jest generowany nowy ArrayList i kilka obiektów
        todoList = new ArrayList<Todo>();
        todoList.add(new Todo("Posprzątać graty", false, 50));
        todoList.add(new Todo("Przykładowe zadanie", false, 40));
        todoList.add(new Todo("Wynieść śmieci", false, 70));
        todoList.add(new Todo("Umyć okna", false, 60));
        todoList.add(new Todo("Zasadzić bambus", false, 20));
//        --------------------------------------------------------

        todoAdapter = new TodoAdapter(getContext(), todoList);
        todoListView.setAdapter(todoAdapter);

        addNewTaskButton.setOnClickListener(this);
        todoListView.setOnItemClickListener(this);

        sortTodoListByStatus(todoList);
        updatePointsMeter();
        todoAdapter.notifyDataSetChanged();

        return view;
    }

    static public void sortTodoListByStatus(ArrayList<Todo> todoList){
        Collections.sort(todoList, new Comparator<Todo>() {
            @Override
            public int compare(Todo o1, Todo o2) {
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
            case R.id.add_new_task_btn:
                openNewTodoFragment();
                break;
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

    public static void updatePointsMeter(){
        pointsMeter.setText("Reward points: " + new Integer(MainActivity.profile.getPoints()).toString());
    }


}

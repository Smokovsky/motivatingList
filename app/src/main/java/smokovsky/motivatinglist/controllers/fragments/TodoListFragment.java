package smokovsky.motivatinglist.controllers.fragments;

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
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.activities.MainActivity;
import smokovsky.motivatinglist.controllers.fileController.FileIO;
import smokovsky.motivatinglist.controllers.adapters.TodoAdapter;
import smokovsky.motivatinglist.model.Todo;

public class TodoListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener  {

    private EditText newTodoName;
    private EditText newTodoPoints;
    private Button addNewTaskButton;
    private ListView todoListView;
    private static TextView pointsMeter;
    private Todo todo = null;

    public ArrayList<Todo> todoList;
    private ArrayAdapter<Todo> todoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        newTodoName = view.findViewById(R.id.new_task_name_text);
        newTodoPoints = view.findViewById(R.id.new_task_points_text);
        addNewTaskButton = view.findViewById(R.id.add_new_task_btn);
        todoListView = view.findViewById(R.id.tasks_list);
        pointsMeter = view.findViewById(R.id.points_meter);


/**
 ******************************************* OPIS PROBLEMU ***************************************
 *
 *
 *       Program za pierwszym razem się włącza i działa poprawnie aż do zamknięcia.
 *       Jeśli zamykając program, zostawimy jakieś obiekty na liście, ten już się nie otworzy,
 *       w związku z problem zapisu lub odczytu do pliku (controllers/fileController/FileIO).
 *
 *
 *************************************************************************************************
 */

//        todo: tutaj wczytujemy dane
//        todoList = FileIO.loadDataFromFile(getContext());


//        --------------------------------------------------------
//        AKTUALNIE AKTYWNE
//        todo: tutaj w zastepstwie byl generowany nowy ArrayList i kilka obiektów
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
        todoAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.add_new_task_btn:
                String itemEntered = newTodoName.getText().toString();
                int pointsEntered = 0;
                if(newTodoPoints.getText().length() > 0)
                    pointsEntered = new Integer(newTodoPoints.getText().toString());
                else
                    pointsEntered = 0;
                todo = new Todo(itemEntered, false, pointsEntered);
                addTodo(todo);
                newTodoName.setText("");
                newTodoPoints.setText("");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openEditTodoFragment(position);
    }

    private void openEditTodoFragment(int position){
        try {
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

    private void addTodo(Todo todo){
        todoAdapter.add(todo);
//        todo: tutaj zapisujemy dane
        FileIO.saveDataToFile(todoList, getContext());
        sortTodoListByStatus(todoList);
        todoAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Task added succesfully", Toast.LENGTH_SHORT).show();
    }

    static public void sortTodoListByStatus(ArrayList<Todo> todoList){
        Collections.sort(todoList, new Comparator<Todo>() {
            @Override
            public int compare(Todo o1, Todo o2) {
                return Boolean.compare(o1.getTodoStatus(),o2.getTodoStatus());
            }
        });

        TodoListFragment.pointsMeter.setText(new Integer(MainActivity.profile.getPoints()).toString());
    }
}

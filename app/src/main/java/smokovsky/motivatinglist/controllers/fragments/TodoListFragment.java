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
import android.widget.Toast;
import java.util.ArrayList;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.fileController.FileIO;
import smokovsky.motivatinglist.controllers.adapters.TodoAdapter;
import smokovsky.motivatinglist.model.Todo;

public class TodoListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener  {

    private EditText newTodoName;
    private Button addNewTaskButton;
    private ListView todoListView;
    private Todo todo = null;

    public ArrayList<Todo> todoList;
    private ArrayAdapter<Todo> todoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        newTodoName = view.findViewById(R.id.new_task_name_text);
        addNewTaskButton = view.findViewById(R.id.add_new_task_btn);
        todoListView = view.findViewById(R.id.tasks_list);
/**
 *
 *    *   *  * ******************** *   *    *  OPIS PROBLEMU  *    *   * ******************** *  *   *
 *
 *
 *       Program za pierwszym razem się włącza i działa poprawnie aż do zamknięcia.
 *       Jeśli zamykając program, zostawimy jakieś obiekty na liście, ten już się nie otworzy,
 *       w związku z problem zapisu lub odczytu do pliku (controllers/fileController/FileIO).
 *
 *
 *    *   *  * ******************** *   *    *        *        *    *   * ******************** *  *   *
 */

//        todo: tutaj wczytujemy dane
        todoList = FileIO.loadDataFromFile(getContext());


//        --------------------------------------------------------
////        AKTUALNIE NIEAKTYWNE
//        todo: tutaj w zastepstwie byl generowany nowy ArrayList i kilka obiektów
//        todoList = new ArrayList<Todo>();
//        todoList.add(new Todo("Zadanie testowe", false));
//        todoList.add(new Todo("Zadanie testowe 2", false));
//        todoList.add(new Todo("Zadanie testowe 3", false));
//        --------------------------------------------------------

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
                String itemEntered = newTodoName.getText().toString();
                todo = new Todo(itemEntered, false);
                addTodo(todo);
                newTodoName.setText("");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openEditTodoFragment(position);
        Toast.makeText(getContext(), "Task " + position + " has been clicked", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getContext(), "Task " + (todoList.size()-1) + " added succesfully", Toast.LENGTH_SHORT).show();
    }
}

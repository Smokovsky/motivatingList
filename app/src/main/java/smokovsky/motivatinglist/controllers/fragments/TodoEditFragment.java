package smokovsky.motivatinglist.controllers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.fileController.FileIO;
import smokovsky.motivatinglist.model.Todo;


public class TodoEditFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Todo> todoList = new ArrayList<Todo>();
    private ArrayAdapter todoAdapter;
    private int position;

    private Todo todo = new Todo("", false);
    private EditText todoNameInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_edit_name, container, false);

        todoNameInput = (EditText) view.findViewById(R.id.name_edittext);
        Button changeNameButton = (Button) view.findViewById(R.id.change_name_button);
        Button deleteButton = (Button) view.findViewById(R.id.delete_button);
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);

        changeNameButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        todoNameInput.setText(todo.getTodoName());

        return view;
    }

    public static TodoEditFragment newInstance(ArrayList<Todo> todoList, ArrayAdapter<Todo> todoAdapter, int position) {
        TodoEditFragment fragment = new TodoEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        fragment.todoAdapter = todoAdapter;
        fragment.todoList = todoList;
        fragment.position = position;

        fragment.todo = todoList.get(position);

        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.change_name_button:
                todoList.set(position, new Todo(todoNameInput.getText().toString(), false));
                Toast.makeText(getContext(), "Task " + position +" name has been changed", Toast.LENGTH_SHORT).show();
//                todo: tutaj zapisujemy dane
                FileIO.saveDataToFile(todoList, getContext());
                todoAdapter.notifyDataSetChanged();
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;

            case R.id.delete_button:
                todoList.remove(position);
                Toast.makeText(getContext(), "Task " + position + " has been deleted", Toast.LENGTH_SHORT).show();
//                todo: tutaj zapisujemy dane
                FileIO.saveDataToFile(todoList, getContext());
                todoAdapter.notifyDataSetChanged();
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;

            case R.id.cancel_button:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
        }
    }
}

package smokovsky.motivatinglist.controllers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.fileController.FileIO;
import smokovsky.motivatinglist.model.Todo;

public class TodoNewFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Todo> todoList = new ArrayList<Todo>();
    private ArrayAdapter todoAdapter;

    private EditText todoNameInput;
    private EditText todoRewardPointsInput;
    private CheckBox todoRepeatableInput;
    private Button saveButton;
    private Button cancelButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_todo_new, container, false);

        todoNameInput = (EditText) view.findViewById(R.id.todo_name_field);
        todoRewardPointsInput = (EditText) view.findViewById(R.id.todo_points_field);
        todoRepeatableInput = (CheckBox) view.findViewById(R.id.todo_repeatable_field);
        saveButton = (Button) view.findViewById(R.id.save_button);
        cancelButton = (Button) view.findViewById(R.id.cancel_button);

        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        return view;
    }

    public static TodoNewFragment newInstance(ArrayList<Todo> todoList, ArrayAdapter<Todo> todoAdapter){
        TodoNewFragment fragment = new TodoNewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        fragment.todoList = todoList;
        fragment.todoAdapter = todoAdapter;

        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:

                if(todoNameInput.getText().toString().length()==0)
                    Toast.makeText(getContext(), "Please enter todo name", Toast.LENGTH_SHORT).show();
                else if (todoRewardPointsInput.getText().toString().length() == 0)
                    Toast.makeText(getContext(), "Please enter todo reward points", Toast.LENGTH_SHORT).show();
                else{
                    todoList.add(new Todo(todoNameInput.getText().toString(), todoRepeatableInput.isChecked(), new Integer(todoRewardPointsInput.getText().toString())));
                    TodoListFragment.sortTodoListByStatus(todoList);
//                      todo: tutaj zapisujemy dane
                    FileIO.saveDataToFile(todoList, getContext());
                    todoAdapter.notifyDataSetChanged();
                    Objects.requireNonNull(getActivity()).onBackPressed();
                }
                break;
            case R.id.cancel_button:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
        }
    }
}

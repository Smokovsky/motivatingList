package smokovsky.motivatinglist.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import smokovsky.motivatinglist.controllers.activities.MainActivity;
import smokovsky.motivatinglist.controllers.fileController.FileIO;
import smokovsky.motivatinglist.model.Todo;

public class TodoNewFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Todo> todoList = new ArrayList<Todo>();
    private ArrayAdapter todoAdapter;

    private EditText todoNameInput;
    private EditText todoRewardPointsInput;
    private CheckBox todoRepeatableInput;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_todo_new, container, false);

        todoNameInput = (EditText) view.findViewById(R.id.new_todo_name_field);
        todoRewardPointsInput = (EditText) view.findViewById(R.id.new_todo_points_field);
        todoRepeatableInput = (CheckBox) view.findViewById(R.id.new_todo_repeatable_field);

        Button saveButton = (Button) view.findViewById(R.id.new_todo_add_button);
        Button cancelButton = (Button) view.findViewById(R.id.new_todo_cancel_button);

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
            case R.id.new_todo_add_button:
                if(todoNameInput.getText().toString().length()==0)
                    Toast.makeText(getContext(), getString(R.string.todo_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
                else if (todoRewardPointsInput.getText().toString().length() == 0)
                    Toast.makeText(getContext(), getString(R.string.todo_value_cannot_be_empty), Toast.LENGTH_SHORT).show();
                else{
                    todoList.add(new Todo(todoNameInput.getText().toString(), todoRepeatableInput.isChecked(), Integer.valueOf(todoRewardPointsInput.getText().toString())));
                    TodoListFragment.hideKeyboard(Objects.requireNonNull(getActivity()));
                    TodoListFragment.sortTodoList(todoList);
                    FileIO.saveDataToFile(MainActivity.profile, Objects.requireNonNull(getContext()));
                    todoAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), getString(R.string.task_has_been_added), Toast.LENGTH_SHORT).show();
                    Objects.requireNonNull(getActivity()).onBackPressed();
                }
                break;
            case R.id.new_todo_cancel_button:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
        }
    }
}

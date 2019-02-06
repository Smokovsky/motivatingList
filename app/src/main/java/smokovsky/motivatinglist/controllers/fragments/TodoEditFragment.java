package smokovsky.motivatinglist.controllers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import smokovsky.motivatinglist.R;
import smokovsky.motivatinglist.controllers.activities.MainActivity;
import smokovsky.motivatinglist.controllers.fileController.FileIO;
import smokovsky.motivatinglist.model.Todo;


public class TodoEditFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Todo> todoList = new ArrayList<>();
    private ArrayAdapter todoAdapter;

    private int position;
    private Todo todo;

    private EditText todoNameInput;
    private EditText todoRewardPointsInput;

    private TextView todoDateTime;

    private Button saveButton;
    private Button deleteButton;
    private Button cancelButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_todo_edit, container, false);

        todoNameInput = (EditText) view.findViewById(R.id.todo_name_field);
        todoRewardPointsInput = (EditText) view.findViewById(R.id.todo_points_field);
        todoDateTime = (TextView) view.findViewById(R.id.todo_date_time);
        saveButton = (Button) view.findViewById(R.id.save_button);
        deleteButton = (Button) view.findViewById(R.id.delete_button);
        cancelButton = (Button) view.findViewById(R.id.cancel_button);

        saveButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        todoNameInput.setText(todo.getTodoName());
        todoRewardPointsInput.setText(new Integer(todo.getTodoRewardPoints()).toString());
        todoDateTime.setText("created: " + todo.getTodoDateTime());

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

            case R.id.save_button:

                todo.setTodoName(todoNameInput.getText().toString());
                todo.setTodoRewardPoints(new Integer(todoRewardPointsInput.getText().toString()));
                Toast.makeText(getContext(), "Task has been saved", Toast.LENGTH_SHORT).show();
//                todo: tutaj zapisujemy dane
                FileIO.saveDataToFile(todoList, getContext());
                todoAdapter.notifyDataSetChanged();
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;

            case R.id.delete_button:
                if(todo.getTodoStatus())
                    MainActivity.profile.addPoints(-(todo.getTodoRewardPoints()));
                todoList.remove(position);
                Toast.makeText(getContext(), "Task has been deleted", Toast.LENGTH_SHORT).show();
                TodoListFragment.sortTodoListByStatus(todoList);
                TodoListFragment.updatePointsMeter();
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

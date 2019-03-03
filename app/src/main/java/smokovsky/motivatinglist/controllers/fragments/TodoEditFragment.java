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
    private TextView pointsCounter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_todo_edit, container, false);

        todoNameInput = (EditText) view.findViewById(R.id.edit_todo_name_field);
        todoRewardPointsInput = (EditText) view.findViewById(R.id.edit_todo_points_field);

        TextView todoNewDateTime = (TextView) view.findViewById(R.id.edit_todo_new_date_time);
        TextView todoEndDateTime = (TextView) view.findViewById(R.id.edit_todo_end_date_time);

        Button acceptButton = (Button) view.findViewById(R.id.edit_todo_accept_button);
        Button deleteButton = (Button) view.findViewById(R.id.edit_todo_delete_button);
        Button cancelButton = (Button) view.findViewById(R.id.edit_todo_cancel_button);

        acceptButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        todoNameInput.setText(todo.getTodoName());
        todoRewardPointsInput.setText(String.format("%s",todo.getTodoRewardPoints()));
        todoNewDateTime.setText(String.format("%s %s",getString(R.string.created),todo.getTodoSetupDateTimeString()));
        if(todo.getTodoStatus())
            todoEndDateTime.setText(String.format("%s %s",getString(R.string.accomplished),todo.getTodoFinishDateTimeString()));
        else
            todoEndDateTime.setHeight(0);

        return view;
    }

    public static TodoEditFragment newInstance(ArrayList<Todo> todoList, ArrayAdapter<Todo> todoAdapter, int position, TextView pointsCounter) {
        TodoEditFragment fragment = new TodoEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        fragment.todoAdapter = todoAdapter;
        fragment.todoList = todoList;
        fragment.position = position;

        fragment.pointsCounter = pointsCounter;
        fragment.todo = todoList.get(position);

        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.edit_todo_accept_button:
                if (todoNameInput.getText().toString().length() == 0)
                    Toast.makeText(getContext(), getString(R.string.todo_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
                else if (todoRewardPointsInput.getText().toString().length() == 0)
                    Toast.makeText(getContext(), getString(R.string.todo_value_cannot_be_empty), Toast.LENGTH_SHORT).show();
                else {
                    int formerPoints = todo.getTodoRewardPoints();
                    todo.setTodoName(todoNameInput.getText().toString());
                    if(formerPoints != Integer.valueOf(todoRewardPointsInput.getText().toString())) {
                        todo.setTodoRewardPoints(Integer.valueOf(todoRewardPointsInput.getText().toString()));
                        MainActivity.addPoints( - formerPoints + Integer.valueOf(todoRewardPointsInput.getText().toString()));
                    }
                    TodoListFragment.hideKeyboard(Objects.requireNonNull(getActivity()));
                    Toast.makeText(getContext(), getString(R.string.todo_has_been_saved), Toast.LENGTH_SHORT).show();
                    updatePointsCounter();
                    FileIO.saveDataToFile(MainActivity.profile, Objects.requireNonNull(getContext()));
                    todoAdapter.notifyDataSetChanged();
                    Objects.requireNonNull(getActivity()).onBackPressed();
                }
                break;

            case R.id.edit_todo_delete_button:
                todoList.remove(position);
                if(todo.getTodoStatus())
                    MainActivity.subtractPoints(todo.getTodoRewardPoints());
                TodoListFragment.hideKeyboard(Objects.requireNonNull(getActivity()));
                Toast.makeText(getContext(), getString(R.string.todo_has_been_deleted), Toast.LENGTH_SHORT).show();
                TodoListFragment.sortTodoList(todoList);
                updatePointsCounter();
                FileIO.saveDataToFile(MainActivity.profile, Objects.requireNonNull(getContext()));
                todoAdapter.notifyDataSetChanged();
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;

            case R.id.edit_todo_cancel_button:
                TodoListFragment.hideKeyboard(Objects.requireNonNull(getActivity()));
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
        }
    }

    private void updatePointsCounter(){
        pointsCounter.setText(String.format("%s %s",getString(R.string.reward_points),Integer.toString(MainActivity.profile.getPoints())));
    }
}

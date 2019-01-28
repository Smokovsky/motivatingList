package smokovsky.motivatinglist;

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


public class TodoEditFragment extends Fragment implements View.OnClickListener {

    private ArrayList<String> todoList = new ArrayList<String>();
    private ArrayAdapter todoAdapter;
    private int position;

    private String todoName;
    private EditText changeNameInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_edit_name, container, false);

        changeNameInput = (EditText) view.findViewById(R.id.name_edittext);
        Button changeNameButton = (Button) view.findViewById(R.id.change_name_button);
        Button deleteButton = (Button) view.findViewById(R.id.delete_button);
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);

        changeNameButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        changeNameInput.setText(todoName);
        return view;
    }

    public static TodoEditFragment newInstance(ArrayList<String> todoList, ArrayAdapter<String> todoAdapter, int position) {
        TodoEditFragment fragment = new TodoEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        fragment.todoAdapter = todoAdapter;
        fragment.todoList = todoList;
        fragment.position = position;


        fragment.todoName = todoList.get(position);

        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cancel_button:
                Toast.makeText(getContext(), "No problem ^^", Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.change_name_button:
                todoList.set(position, String.valueOf(changeNameInput.getText()));
                Toast.makeText(getContext(), "Task " + position +" wants to get name changed", Toast.LENGTH_SHORT).show();
                todoAdapter.notifyDataSetChanged();
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.delete_button:
                todoList.remove(position);
                Toast.makeText(getContext(), "Task " + position + " wants to be deleted", Toast.LENGTH_SHORT).show();
                todoAdapter.notifyDataSetChanged();
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
        }
    }
}

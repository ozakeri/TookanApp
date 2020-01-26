package com.tokan.ir.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.tokan.ir.R;
import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.User;
import com.tokan.ir.utils.Util;
import com.tokan.ir.widget.BEditTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {

    @BindView(R.id.edt_oldPass)
    BEditTextView edt_oldPass;

    @BindView(R.id.edt_newPass)
    BEditTextView edt_newPass;

    @BindView(R.id.edt_confirmNewPass)
    BEditTextView edt_confirmNewPass;

    @BindView(R.id.btn_action)
    Button btn_action;

    private List<User> userList = new ArrayList<>();

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        ButterKnife.bind(this, view);

        edt_oldPass.setHint("رمز عبور قدیم");
        edt_newPass.setHint("رمز عبور جدید");
        edt_confirmNewPass.setHint("تکرار رمز عبور جدید");

        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserList();
            }
        });

        return view;
    }


    public void getUserList() {

        String oldPass = edt_oldPass.getText().toString();
        String newPass = edt_newPass.getText().toString();
        String confirmNewPass = edt_confirmNewPass.getText().toString();


        if (oldPass.equals("")) {
            edt_oldPass.setError("کامل کنید");
            return;
        }

        if (newPass.equals("")) {
            edt_newPass.setError("کامل کنید");
            return;
        }

        if (confirmNewPass.equals("")) {
            edt_confirmNewPass.setError("کامل کنید");
            return;
        }

        class GetData extends AsyncTask<Void, Void, List<User>> {


            @Override
            protected List<User> doInBackground(Void... voids) {
                userList = DatabaseClient.getInstance(getActivity()).getAppDatabase().userDao().getUsers();
                return userList;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                User user = users.get(0);

                String password = user.getPassword();

                if (Util.faToEn(oldPass).equals(password) && newPass.equals(confirmNewPass)) {
                    user.setPassword(newPass);
                    update(user);
                } else {
                    Toast.makeText(getActivity(), "اطلاعات اشتباه است", Toast.LENGTH_SHORT).show();
                }

                System.out.println("-+-+-+-+-++" + oldPass);
                System.out.println("-+-+-+-+-++" + password);
                System.out.println("-+-+-+-+-++" + confirmNewPass);
            }
        }

        new GetData().execute();
    }


    public void update(User user) {
        class UpdateUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getActivity()).getAppDatabase().userDao().updateUser(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity(), "انجام شد", Toast.LENGTH_SHORT).show();
            }
        }

        new UpdateUser().execute();
    }

}

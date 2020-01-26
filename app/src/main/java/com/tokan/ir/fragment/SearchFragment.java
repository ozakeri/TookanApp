package com.tokan.ir.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokan.ir.R;
import com.tokan.ir.adapter.SearchListAdapter;
import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.Customer;
import com.tokan.ir.widget.BTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    @BindView(R.id.edt_search)
    EditText edt_search;

    @BindView(R.id.txt_result)
    BTextView txt_result;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private SearchListAdapter adapter;

    private List<Customer> customerList = new ArrayList<>();


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        getCustomerList("%%");

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getCustomerList("%" + charSequence.toString()+ "%");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    public void getCustomerList(String s) {


        /*class CustomerList extends AsyncTask<String, Void, List<Customer>> {

            @Override
            protected List<Customer> doInBackground(String... strings) {
                System.out.println("sssssssssssss" + s);
                customerList = DatabaseClient.getInstance(getActivity()).getAppDatabase().customerDao().getCustomersByKeyword(strings[0]);
                System.out.println("sssssssssssss" + customerList.size());
                return customerList;
            }

            @Override
            protected void onPostExecute(List<Customer> customers) {
                super.onPostExecute(customers);
                adapter = new SearchListAdapter(customers);
                recyclerView.setAdapter(adapter);
                txt_result.setText(" نتیجه جستجو " + customers.size() + " نفر ");
                System.out.println("sssssssssssss+++" + customers.size());
            }
        }


        new CustomerList().execute(s);*/

        class CustomerList extends AsyncTask<Void, Void, List<Customer>> {

            @Override
            protected List<Customer> doInBackground(Void... voids) {
                System.out.println("sssssssssssss" + s);
                customerList = DatabaseClient.getInstance(getActivity()).getAppDatabase().customerDao().getCustomersByKeyword(s);
                System.out.println("sssssssssssss" + customerList.size());
                return customerList;
            }

            @Override
            protected void onPostExecute(List<Customer> customers) {
                super.onPostExecute(customers);

                adapter = new SearchListAdapter(customers);
                recyclerView.setAdapter(adapter);
                txt_result.setText(" نتیجه جستجو " + customers.size() + " نفر ");
                System.out.println("sssssssssssss+++" + customers.size());
            }
        }

        new CustomerList().execute();


    }
}

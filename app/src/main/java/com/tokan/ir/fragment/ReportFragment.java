package com.tokan.ir.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokan.ir.R;
import com.tokan.ir.adapter.SearchListAdapter;
import com.tokan.ir.callback.OnBackPressedListener;
import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.Customer;
import com.tokan.ir.model.EventModel;
import com.tokan.ir.utils.FragmentUtil;
import com.tokan.ir.widget.BTextView;
import com.tokan.ir.widget.RecyclerItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment implements OnBackPressedListener {

    @BindView(R.id.edt_search)
    EditText edt_search;

    @BindView(R.id.txt_result)
    BTextView txt_result;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private SearchListAdapter adapter;

    private List<Customer> customerList = new ArrayList<>();


    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        getCustomerList("%%");

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getCustomerList("%" + charSequence.toString() + "%");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Customer customer = customerList.get(position);
                //deleteReport(customer);
                Bundle bundle = new Bundle();
                bundle.putParcelable("customer", customer);
                gotoFragment(new ReportDetailFragment(), "ReportDetailFragment",bundle);
                EventBus.getDefault().post(new EventModel("ReportDetailFragment"));
            }
        }));

        //backPress(view);
        return view;
    }

    public void getCustomerList(String s) {

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

    private void gotoFragment(Fragment fragment, String fragmentName,Bundle bundle) {

        FragmentManager fragmentManager = getFragmentManager();
        Fragment frg = FragmentUtil.getFragmentByTagName(fragmentManager, fragmentName);
        if (frg == null) {
            frg = fragment;
            frg.setArguments(bundle);
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_container, frg, fragmentName);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

        FragmentUtil.printActivityFragmentList(fragmentManager);
    }

    public void deleteReport(Customer customer) {
        class DeleteReport extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getActivity()).getAppDatabase().customerDao().deleteCustomer(customer);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        new DeleteReport().execute();
    }
/*
    public void backPress(View view){
        EventBus.getDefault().post(new EventModel(""));
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("TAG", "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("TAG", "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
    }*/

    @Override
    public void doBack() {
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}

package com.tokan.ir.fragment;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.tokan.ir.R;
import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphTestFragment extends Fragment {

    LineGraphSeries<DataPoint> lineGraphSeries;
    private final Handler mHandler = new Handler();
    private Runnable mTimer2;
    //private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private double graph2LastXValue = 5d;
    private String nationalCode;
    private String name;
    private String birthDate;
    private String testDate;
    private String sex;
    private AppCompatButton btn_action;
    private List<Customer> customers = new ArrayList<>();
    private List<Double> doubleList = new ArrayList<>();
    private String json_str = null;

    public GraphTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph_test, container, false);

        getUserInfo();

        if (getArguments() != null) {
            nationalCode = getArguments().getString("nationalCode");
            name = getArguments().getString("name");
            birthDate = getArguments().getString("birthDate");
            testDate = getArguments().getString("testDate");
            sex = getArguments().getString("sex");
        }

        btn_action = view.findViewById(R.id.btn_action);
        GraphView graph2 = (GraphView) view.findViewById(R.id.graphView1);
        mSeries2 = new LineGraphSeries<>();
        graph2.addSeries(mSeries2);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(40);

        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*((MainActivity) activity).onSectionAttached(
                getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));*/
    }

    @Override
    public void onResume() {
        super.onResume();

        mTimer2 = new Runnable() {
            @Override
            public void run() {
                graph2LastXValue += 1d;
                System.out.println("getRandom====" + getRandom());
                doubleList.add(getRandom());
                mSeries2.appendData(new DataPoint(graph2LastXValue, getRandom()), true, 3000);
                mHandler.postDelayed(this, 200);
            }
        };
        mHandler.postDelayed(mTimer2, 300);
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer2);
        super.onPause();
    }


    double mLastRandom = 2;
    Random mRand = new Random();

    private double getRandom() {
        return mLastRandom = mRand.nextInt(3000);
    }

    public void saveData() {
        class SaveData extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                Gson gson = new Gson();
                String json = gson.toJson(doubleList);


                Customer customer = new Customer();
                customer.setNationalCode(nationalCode);
                customer.setNameFamily(name);
                customer.setBirthDate(birthDate);
                customer.setTestDate(testDate);
                customer.setSex(sex);
                customer.setJsonValue(json);
                DatabaseClient.getInstance(getActivity()).getAppDatabase().customerDao().insertCustomer(customer);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }
        new SaveData().execute();
    }


    public void getUserInfo() {
        class GetInfo extends AsyncTask<Void, Void, List<Customer>> {

            @Override
            protected List<Customer> doInBackground(Void... voids) {
                customers = DatabaseClient.getInstance(getActivity()).getAppDatabase().customerDao().getCustomers();
                return customers;
            }

            @Override
            protected void onPostExecute(List<Customer> users) {
                super.onPostExecute(users);

                for (Customer customer : users) {

                    System.out.println("++++++++" + customer.getNationalCode());
                    System.out.println("++++++++" + customer.getNameFamily());
                    System.out.println("++++++++" + customer.getBirthDate());
                    System.out.println("++++++++" + customer.getTestDate());
                    System.out.println("++++++++" + customer.getSex());
                    System.out.println("++++++++" + customer.getJsonValue());
                }

            }
        }

        new GetInfo().execute();
    }

    public void saveValue() {

    }
}

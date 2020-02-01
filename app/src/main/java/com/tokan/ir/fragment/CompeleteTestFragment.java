package com.tokan.ir.fragment;


import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.tokan.ir.R;
import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.Customer;
import com.tokan.ir.entity.User;
import com.tokan.ir.model.EventModel;
import com.tokan.ir.widget.BEditTextView;
import com.tokan.ir.widget.BTextView;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompeleteTestFragment extends Fragment {

    @BindView(R.id.txt_name)
    BTextView txt_name;

    @BindView(R.id.txt_customer)
    BTextView txt_customer;

    @BindView(R.id.txt_nationalCode)
    BTextView txt_nationalCode;

    @BindView(R.id.txt_nameFamily)
    BTextView txt_nameFamily;

    @BindView(R.id.txt_sex)
    BTextView txt_sex;

    @BindView(R.id.txt_birthDate)
    BTextView txt_birthDate;

    @BindView(R.id.txt_testDate)
    BTextView txt_testDate;

    @BindView(R.id.txt_averageFlowRate)
    BTextView txt_averageFlowRate;

    @BindView(R.id.txt_maximumFlowRate)
    BTextView txt_maximumFlowRate;

    @BindView(R.id.txt_timeToMaximumFlow)
    BTextView txt_timeToMaximumFlow;

    @BindView(R.id.txt_voidedVolume)
    BTextView txt_voidedVolume;

    @BindView(R.id.txt_flowTime)
    BTextView txt_flowTime;

    @BindView(R.id.txt_voidingTime)
    BTextView txt_voidingTime;

    @BindView(R.id.txt_interval)
    BTextView txt_interval;

    @BindView(R.id.txt_delayTime)
    BTextView txt_delayTime;

    @BindView(R.id.graphView1)
    GraphView graphView1;

    @BindView(R.id.graphView2)
    GraphView graphView2;

    @BindView(R.id.btn_save)
    Button btn_save;

    @BindView(R.id.txt_comment)
    EditText txt_comment;

    private List<Double> doubleList = new ArrayList<>();
    private List<Double> doubleList1 = new ArrayList<>();
    private double graph2LastXValue = 5d;
    private LineGraphSeries<DataPoint> mSeries;
    private LineGraphSeries<DataPoint> mSeries1;
    private List<User> userList = new ArrayList<>();

    public CompeleteTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compelete_test, container, false);


        ButterKnife.bind(this, view);

        mSeries = new LineGraphSeries<>();
        mSeries1 = new LineGraphSeries<>();
        graphView1.getViewport().setXAxisBoundsManual(true);
        graphView1.getViewport().setMinX(0);
        graphView1.getViewport().setMaxX(40);
        graphView1.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
        graphView1.getViewport().setScrollable(true);  // activate horizontal scrolling
        graphView1.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
        graphView1.getViewport().setScrollableY(true);  // activate vertical scrolling

        graphView2.getViewport().setXAxisBoundsManual(true);
        graphView2.getViewport().setMinX(0);
        graphView2.getViewport().setMaxX(40);
        graphView2.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
        graphView2.getViewport().setScrollable(true);  // activate horizontal scrolling
        graphView2.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
        graphView2.getViewport().setScrollableY(true);  // activate vertical scrolling

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Customer customer = bundle.getParcelable("customer");

            if (customer != null) {
                getUserList();
                txt_customer.setText(customer.getNameFamily());
                txt_nameFamily.setText(customer.getNameFamily());
                txt_nationalCode.setText(customer.getNationalCode());
                txt_sex.setText(customer.getSex());
                txt_birthDate.setText(customer.getBirthDate());
                txt_testDate.setText(customer.getTestDate());

                Gson gson = new Gson();
                String json1 = customer.getFlowValue();
                String json2 = customer.getVolumeValue();
                Type type = new TypeToken<ArrayList<Double>>() {
                }.getType();
                doubleList = gson.fromJson(json1, type);
                doubleList1 = gson.fromJson(json2, type);
                for (double d : doubleList) {
                    graph2LastXValue += 1d;
                    mSeries.appendData(new DataPoint(graph2LastXValue, d), true, 3000);
                }

                for (double d : doubleList1) {
                    graph2LastXValue += 1d;
                    mSeries1.appendData(new DataPoint(graph2LastXValue, d), true, 3000);
                }
                graphView1.addSeries(mSeries);
                graphView2.addSeries(mSeries1);


                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txt_comment.getText() != null) {
                            customer.setComment(txt_comment.getText().toString());
                        }
                        saveData(customer);
                        gotoFragment(new ReportFragment(), "ReportFragment");
                        getActivity().finish();
                    }
                });
            }

        }
        return view;
    }


    public void saveData(Customer customer) {
        class SaveData extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
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

    public void getUserList() {
        class GetInfo extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                userList = DatabaseClient.getInstance(getActivity()).getAppDatabase().userDao().getUsers();
                return userList;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);

                User user = users.get(0);
                txt_name.setText(user.getNameFamily());

                System.out.println("user.getPath()===" + user.getPath());
            }
        }

        new GetInfo().execute();
    }

    private void gotoFragment(Fragment fragment, String name) {
        EventBus.getDefault().post(new EventModel(name));
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

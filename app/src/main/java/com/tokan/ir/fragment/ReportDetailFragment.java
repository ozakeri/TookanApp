package com.tokan.ir.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.tokan.ir.R;
import com.tokan.ir.entity.Customer;
import com.tokan.ir.widget.BTextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportDetailFragment extends Fragment {

    @BindView(R.id.txt_name)
    BTextView txt_name;

    @BindView(R.id.txt_date)
    BTextView txt_date;

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

    @BindView(R.id.txt_delayTime)
    BTextView txt_delayTime;

    @BindView(R.id.graphView1)
    GraphView graphView1;

    @BindView(R.id.graphView2)
    GraphView graphView2;

    @BindView(R.id.txt_comment)
    BTextView txt_comment;

    private List<Double> doubleList = new ArrayList<>();
    private List<Double> doubleList1 = new ArrayList<>();
    private double graph2LastXValue = 5d;
    private LineGraphSeries<DataPoint> mSeries;
    private LineGraphSeries<DataPoint> mSeries1;
    private double maxFlow = 0;

    public ReportDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_report, container, false);

        ButterKnife.bind(this, view);

        mSeries = new LineGraphSeries<>();
        mSeries1 = new LineGraphSeries<>();
        graphView1.getViewport().setXAxisBoundsManual(true);
        graphView1.getViewport().setMinX(0);
        graphView1.getViewport().setMaxX(100);
        graphView1.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
        graphView1.getViewport().setScrollable(true);  // activate horizontal scrolling
        graphView1.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
        graphView1.getViewport().setScrollableY(true);  // activate vertical scrolling

        graphView2.getViewport().setXAxisBoundsManual(true);
        graphView2.getViewport().setMinX(0);
        graphView2.getViewport().setMaxX(100);
        graphView2.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
        graphView2.getViewport().setScrollable(true);  // activate horizontal scrolling
        graphView2.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
        graphView2.getViewport().setScrollableY(true);  // activate vertical scrolling
        txt_comment.setVerticalScrollBarEnabled(true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Customer customer = bundle.getParcelable("customer");

            if (customer != null) {
                txt_name.setText(customer.getNameFamily());
                txt_date.setText(customer.getTestDate());
                txt_comment.setText(customer.getComment());

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

                double sum = 0;
                for (double d : doubleList1) {
                    sum += d;
                }
                String avgFlow = String.valueOf(sum / doubleList1.size());
                txt_averageFlowRate.setText(avgFlow);

                for (double d : doubleList1) {
                    if (maxFlow < d) {
                        maxFlow = d;
                    }
                }
                txt_maximumFlowRate.setText(String.valueOf((int) maxFlow));

                double flowSum = 0;
                for (double d : doubleList1) {
                    flowSum += d;
                }
                String avg = String.valueOf(flowSum);
                txt_flowTime.setText(avg);

                txt_voidedVolume.setText(customer.getVoidedVolume());

                getVodingTime(customer.getStartVoidedTime(), customer.getEndVoidedTime());
                getDelayTime(customer.getStartVoidedTime(), customer.getDelayTime());
                getTimeMaxFlow(customer.getStartFlowTime(), customer.getTimeToMaxFlow());

                //*************************
            }


        }


        return view;
    }

    public void getVodingTime(String dateStart, String dateStop) {

        //HH converts hour in 24 hours format (0-23), day calculation
        //SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date d1 = new Date(dateStart);
        Date d2 = new Date(dateStop);

        try {
            //d1 = format.parse(dateStart);
            //d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffMinutes + " minutes11, ");
            System.out.print(diffSeconds + " seconds11.");
            txt_voidingTime.setText(String.valueOf((int) diffSeconds));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDelayTime(String dateStart, String dateStop) {

        //HH converts hour in 24 hours format (0-23), day calculation
        //SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");



        Date d1 = new Date(dateStart);
        Date d2 = new Date(dateStop);

        try {
            //d1 = format.parse(dateStart);
            // d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffMinutes + " minutes22, ");
            System.out.print(diffSeconds + " seconds22.");
            txt_delayTime.setText(String.valueOf((int) diffSeconds));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTimeMaxFlow(String dateStart, String dateStop) {

        //HH converts hour in 24 hours format (0-23), day calculation
        //SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


        System.out.println("dateStart===" + dateStart);
        System.out.println("dateStop===" + dateStop);

        Date d1 = new Date(dateStart);
        Date d2 = new Date(dateStop);

        try {
            //d1 = format.parse(dateStart);
            // d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffMinutes + " minutes333, ");
            System.out.print(diffSeconds + " seconds333.");
            txt_timeToMaximumFlow.setText((int) diffSeconds + " seconds");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

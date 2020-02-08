package com.tokan.ir.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.tokan.ir.R;
import com.tokan.ir.entity.Customer;
import com.tokan.ir.model.EventModel;
import com.tokan.ir.utils.FragmentUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphTestFragment extends Fragment {

    LineGraphSeries<DataPoint> lineGraphSeries;
    private final Handler mHandler = new Handler();
    private Runnable mTimer2;
    //private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private double graph1LastXValue = 0;
    private double graph1LastXValue2 = 0;
    private String nationalCode;
    private String name;
    private String birthDate;
    private String testDate;
    private String sex;
    private AppCompatButton btn_action;
    private List<Customer> customers = new ArrayList<>();
    private List<Double> doubleListX1 = new ArrayList<>();
    private List<Double> doubleListX2 = new ArrayList<>();
    private String json_str = null;
    private boolean isActionClick = false;
    private Date startTime;
    private Date endTime;
    private Date start1;
    private Date startFlow;
    private Date endFlow;
    private Date timeToMaxFlow;
    private boolean start = false;
    private boolean isStartFlow = false;

    private ServerSocket serverSocket = null;
    Handler updateConversationHandler;
    Thread serverThread = null;
    private TextView text;
    public static final int SERVERPORT = 8888;
    private int number = 0;
    private RelativeLayout relative_Layout;
    private String numberOnly = null;
    private int numberOne = 0;
    private String msg = null;
    private GraphTestFragment.CommunicationThread commThread;
    private int flow = 0, resultFlow = 0;
    private int maxFlow = 0, voidedVolume = 0;
    private boolean update = false;

    public GraphTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph_test, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        relative_Layout = view.findViewById(R.id.relative_Layout);
        relative_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //getUserInfo();

        if (getArguments() != null) {
            nationalCode = getArguments().getString("nationalCode");
            name = getArguments().getString("name");
            birthDate = getArguments().getString("birthDate");
            testDate = getArguments().getString("testDate");
            sex = getArguments().getString("sex");
        }

        btn_action = view.findViewById(R.id.btn_action);
        GraphView graphView1 = (GraphView) view.findViewById(R.id.graphView1);
        GraphView graphView2 = (GraphView) view.findViewById(R.id.graphView2);
        mSeries1 = new LineGraphSeries<>();
        mSeries2 = new LineGraphSeries<>();

        graphView1.addSeries(mSeries1);
        graphView1.getViewport().setXAxisBoundsManual(true);
        graphView1.getViewport().setMinX(0);
        graphView1.getViewport().setMaxX(100);
        graphView1.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
        graphView1.getViewport().setScrollable(true);  // activate horizontal scrolling
        graphView1.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
        graphView1.getViewport().setScrollableY(true);  // activate vertical scrolling

        graphView2.addSeries(mSeries2);
        graphView2.getViewport().setXAxisBoundsManual(true);
        graphView2.getViewport().setMinX(0);
        graphView2.getViewport().setMaxX(100);
        graphView2.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
        graphView2.getViewport().setScrollable(true);  // activate horizontal scrolling
        graphView2.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
        graphView2.getViewport().setScrollableY(true);  // activate vertical scrolling

        //getData();
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isActionClick) {
                    endTime = Calendar.getInstance().getTime();
                    endFlow = Calendar.getInstance().getTime();
                    saveData();

                } else {
                    btn_action.setText("پایان تست");
                    isActionClick = true;

                    startTime = Calendar.getInstance().getTime();

                    getData();

                }
            }
        });

        return view;
    }


    public void saveData() {

        Gson gson1 = new Gson();
        Gson gson2 = new Gson();
        String json1 = gson1.toJson(doubleListX1);
        String json2 = gson2.toJson(doubleListX2);


        Customer customer = new Customer();
        customer.setNationalCode(nationalCode);
        customer.setNameFamily(name);
        customer.setBirthDate(birthDate);
        customer.setTestDate(testDate);
        customer.setSex(sex);
        customer.setFlowValue(json1);
        customer.setVolumeValue(json2);
        customer.setVoidedVolume(String.valueOf(voidedVolume));

        customer.setStartVoidedTime(String.valueOf(startTime));
        customer.setEndVoidedTime(String.valueOf(endTime));
        customer.setDelayTime(String.valueOf(start1));
        customer.setStartFlowTime(String.valueOf(startFlow));
        customer.setEndFlowTime(String.valueOf(endFlow));
        customer.setTimeToMaxFlow(String.valueOf(timeToMaxFlow));

        Bundle bundle = new Bundle();
        bundle.putParcelable("customer", customer);
        gotoFragment(new CompeleteTestFragment(), "CompeleteTestFragment", bundle);
    }


 /*   private void gotoFragment(Fragment fragment, String fragmentName, Bundle bundle) {
        EventBus.getDefault().post(new EventModel(fragmentName));
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
    }*/


    public void getData() {
        updateConversationHandler = new Handler();
        this.serverThread = new Thread(new GraphTestFragment.ServerThread());
        this.serverThread.start();
    }


    class ServerThread implements Runnable {

        public void run() {
            Socket socket = null;
            try {
                serverSocket = new ServerSocket(SERVERPORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {

                try {

                        socket = serverSocket.accept();
                        commThread = new GraphTestFragment.CommunicationThread(socket);
                        new Thread(commThread).start();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CommunicationThread implements Runnable {

        private Socket clientSocket;

        private BufferedReader input = null;

        public CommunicationThread(Socket clientSocket) {

            this.clientSocket = clientSocket;

            try {

                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {


            while (!Thread.currentThread().isInterrupted()) {

                try {

                    String read = input.readLine();

                    if (read == null) {
                        Thread.currentThread().interrupt();
                    } else {
                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                        out.write("TstMsg");
                        updateConversationHandler.post(new GraphTestFragment.updateUIThread(read));

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class updateUIThread implements Runnable {


        public updateUIThread(String str) {
            msg = str;
        }

        @Override
        public void run() {
            System.out.println("Client Says: " + msg);
            numberOnly = msg.replaceAll("[^0-9]", "");
            System.out.println("numberOnly====" + numberOnly);

            numberOne = Integer.parseInt(numberOnly);
            voidedVolume += numberOne;
            resultFlow = numberOne - flow;


            if (numberOne > 0) {
                if (!start) {
                    start1 = new Date();
                    start = true;
                }
            }

            graph1LastXValue += 0.5d;
            graph1LastXValue2 += 0.5d;
            doubleListX1.add((double) (numberOne));


            if (resultFlow < 0) {
                resultFlow = 0;
            }

            if (resultFlow > 0) {
                if (!isStartFlow) {
                    startFlow = new Date();
                    isStartFlow = true;
                }

                if (maxFlow < resultFlow) {
                    maxFlow = resultFlow;
                    timeToMaxFlow = new Date();
                    System.out.println("timeToMaxFlow111====" + timeToMaxFlow);
                }

            }


            mSeries1.appendData(new DataPoint(graph1LastXValue, (double) (numberOne)), true, 10000);
            doubleListX2.add((double) resultFlow);
            mSeries2.appendData(new DataPoint(graph1LastXValue2, (double) (resultFlow)), true, 10000);

            flow = numberOne;

            System.out.println("Double====" + (double) numberOne);
            System.out.println("Double====" + (double) resultFlow);

        }

    }

    private void gotoFragment(Fragment fragment, String name,Bundle bundle) {
        EventBus.getDefault().post(new EventModel(name));
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        transaction.replace(R.id.home_container, fragment);
        transaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Thread.currentThread().isInterrupted();
    }

    @Override
    public void onStop() {
        super.onStop();
        Thread.currentThread().isInterrupted();
    }
}

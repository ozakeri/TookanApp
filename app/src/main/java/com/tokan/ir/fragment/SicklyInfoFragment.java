package com.tokan.ir.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tokan.ir.R;
import com.tokan.ir.callback.OnBackPressedListener;
import com.tokan.ir.model.EventModel;
import com.tokan.ir.sundatepicker.DatePicker;
import com.tokan.ir.sundatepicker.interfaces.DateSetListener;
import com.tokan.ir.utils.FragmentUtil;
import com.tokan.ir.utils.JalaliCalendarUtil;
import com.tokan.ir.utils.WheelView;
import com.tokan.ir.widget.BEditTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.format;

/**
 * A simple {@link Fragment} subclass.
 */
public class SicklyInfoFragment extends Fragment implements OnBackPressedListener {

    @BindView(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;

    @BindView(R.id.btn_action)
    AppCompatButton btn_action;

    @BindView(R.id.edt_nationalCode)
    BEditTextView edt_nationalCode;

    @BindView(R.id.edt_nameFamily)
    BEditTextView edt_nameFamily;

    @BindView(R.id.edt_birthDate)
    AppCompatTextView edt_birthDate;

    @BindView(R.id.edt_testDate)
    AppCompatTextView edt_testDate;

    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    @BindView(R.id.radio_group)
    RadioGroup radio_group;

    private String sex;
    private ArrayList<String> hourArrayList = new ArrayList<>();
    private ArrayList<String> minuteArrayList = new ArrayList<>();
    private int h;
    private int m;

    public SicklyInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sickly_info, container, false);

        ButterKnife.bind(this, view);

        edt_nationalCode.setHint("کد ملی");
        edt_nationalCode.setInputType(InputType.TYPE_CLASS_NUMBER);
        edt_nameFamily.setHint("نام و نام خانوادگی بیمار");
        edt_birthDate.setHint("تاریخ تولد");
        edt_testDate.setHint("تاریخ و ساعت انجام تست");

        for (int i = 0; i <= 59; i++) {
            if (i < 10) {
                minuteArrayList.add("0" + i);
            } else {
                minuteArrayList.add(String.valueOf(i));
            }
        }


        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                hourArrayList.add("0" + i);
            } else {
                hourArrayList.add(String.valueOf(i));
            }
        }


        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        edt_birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                Calendar minDate = Calendar.getInstance();
                Calendar maxDate = Calendar.getInstance();
                maxDate.set(Calendar.YEAR, maxDate.get(Calendar.YEAR) + 1);
                minDate.set(Calendar.YEAR, maxDate.get(Calendar.YEAR) - 90);
                new DatePicker.Builder()
                        .id(1)
                        .minDate(minDate)
                        .maxDate(maxDate)
                        .build(new DateSetListener() {
                            @Override
                            public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year) {
                                if (calendar == null)
                                    return;

                                JalaliCalendarUtil jalaliCalendarUtil = new JalaliCalendarUtil(calendar);
                                edt_birthDate.setText(jalaliCalendarUtil.getIranianWeekDayStr() + " " + jalaliCalendarUtil.getIranianDate3());
                                System.out.println("+++++++++++++" + jalaliCalendarUtil.getIranianWeekDayStr() + " " + jalaliCalendarUtil.getIranianDate3());
                            }
                        })
                        .show(fm, "");

            }
        });


        edt_testDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                Calendar minDate = Calendar.getInstance();
                Calendar maxDate = Calendar.getInstance();
                maxDate.set(Calendar.YEAR, maxDate.get(Calendar.YEAR) + 1);
                minDate.set(Calendar.YEAR, maxDate.get(Calendar.YEAR) - 90);
                new DatePicker.Builder()
                        .id(1)
                        .minDate(minDate)
                        .maxDate(maxDate)
                        .build(new DateSetListener() {
                            @Override
                            public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year) {
                                setDate(calendar);
                            }
                        })
                        .show(fm, "");

            }
        });

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.man:
                        sex = "آقا";
                        break;

                    case R.id.woman:
                        sex = "خانم";
                        break;
                }
            }
        });

        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nationalCode = edt_nationalCode.getText().toString();
                String name = edt_nameFamily.getText().toString();
                String birthDate = edt_birthDate.getText().toString();
                String testDate = edt_testDate.getText().toString();

                if (nationalCode.equals("")) {
                    edt_nationalCode.setError("تکمیل شود");
                    return;
                }
                if (name.equals("")) {
                    edt_nameFamily.setError("تکمیل شود");
                    return;
                }
                if (birthDate.equals("")) {
                    edt_birthDate.setError("تکمیل شود");
                    return;
                }
                if (testDate.equals("")) {
                    edt_testDate.setError("تکمیل شود");
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("nationalCode", nationalCode);
                bundle.putString("name", name);
                bundle.putString("birthDate", birthDate);
                bundle.putString("testDate", testDate);
                bundle.putString("sex", sex);

                gotoFragment(new GraphTestFragment(), "GraphTestFragment", bundle);

            }
        });

        // backPress(view);
        return view;
    }


   /* private void gotoFragment(Fragment fragment, String fragmentName, Bundle bundle) {
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

  /*  public void backPress(View view){
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

    private void setDate(final Calendar calendar) {
        if (calendar == null)
            return;
        final Dialog dialog_wait = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog_wait.setContentView(R.layout.dialog_select_time);
        dialog_wait.setCancelable(true);
        dialog_wait.show();
        Window window = dialog_wait.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        final WheelView wheel_view1 = dialog_wait.findViewById(R.id.wheel_view1);
        final WheelView wheel_view2 = dialog_wait.findViewById(R.id.wheel_view2);

        wheel_view1.setSeletion(Calendar.getInstance().getTime().getHours());
        wheel_view1.setItems(hourArrayList);
        wheel_view1.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                h = Integer.parseInt(item);
            }
        });


        wheel_view2.setSeletion(Calendar.getInstance().getTime().getMinutes());
        wheel_view2.setItems(minuteArrayList);
        wheel_view2.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                m = Integer.parseInt(item);
            }
        });

        final LinearLayout lin = (LinearLayout) dialog_wait.findViewById(R.id.lin);
        TextView cancel = (TextView) dialog_wait.findViewById(R.id.cancel);
        TextView ok = (TextView) dialog_wait.findViewById(R.id.ok);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_wait.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (h == 0) {
                    h = Integer.parseInt(wheel_view1.getSeletedItem());
                }
                if (m == 0) {
                    m = Integer.parseInt(wheel_view2.getSeletedItem());
                }

                calendar.set(Calendar.HOUR_OF_DAY, h);
                calendar.set(Calendar.MINUTE, m);
                JalaliCalendarUtil jalaliCalendarUtil = new JalaliCalendarUtil(calendar);
                String time = format(Locale.US, "%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

                edt_testDate.setText(jalaliCalendarUtil.getIranianWeekDayStr() + " " + jalaliCalendarUtil.getIranianDate3() + " " + " ساعت " + " " + time);
                dialog_wait.dismiss();
            }
        });

    }

    private void gotoFragment(Fragment fragment, String name,Bundle bundle) {
        EventBus.getDefault().post(new EventModel(name));
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        transaction.replace(R.id.home_container, fragment);
        transaction.commit();
    }
}

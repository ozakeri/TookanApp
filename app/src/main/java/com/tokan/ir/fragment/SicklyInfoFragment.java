package com.tokan.ir.fragment;


import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tokan.ir.R;
import com.tokan.ir.model.EventModel;
import com.tokan.ir.sundatepicker.DatePicker;
import com.tokan.ir.sundatepicker.interfaces.DateSetListener;
import com.tokan.ir.utils.FragmentUtil;
import com.tokan.ir.utils.JalaliCalendarUtil;
import com.tokan.ir.widget.BEditTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SicklyInfoFragment extends Fragment {

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
                                if (calendar == null)
                                    return;

                                JalaliCalendarUtil jalaliCalendarUtil = new JalaliCalendarUtil(calendar);
                                edt_testDate.setText(jalaliCalendarUtil.getIranianWeekDayStr() + " " + jalaliCalendarUtil.getIranianDate3());
                                System.out.println("+++++++++++++" + jalaliCalendarUtil.getIranianWeekDayStr() + " " + jalaliCalendarUtil.getIranianDate3());
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

                gotoFragment(new GraphTestFragment(), "GraphTestFragment", bundle );

            }
        });
        return view;
    }


    private void gotoFragment(Fragment fragment, String fragmentName, Bundle bundle) {
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
    }

}

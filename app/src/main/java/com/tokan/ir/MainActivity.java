package com.tokan.ir;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.tokan.ir.callback.OnBackPressedListener;
import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.User;
import com.tokan.ir.fragment.AboutFragment;
import com.tokan.ir.fragment.BackupFragment;
import com.tokan.ir.fragment.BackupManageFragment;
import com.tokan.ir.fragment.ChangePasswordFragment;
import com.tokan.ir.fragment.CompeleteTestFragment;
import com.tokan.ir.fragment.ContactUsFragment;
import com.tokan.ir.fragment.DeviceSettingFragment;
import com.tokan.ir.fragment.GraphTestFragment;
import com.tokan.ir.fragment.GuideFragment;
import com.tokan.ir.fragment.ReportDetailFragment;
import com.tokan.ir.fragment.ReportErrorFragment;
import com.tokan.ir.fragment.ReportFragment;
import com.tokan.ir.fragment.SearchFragment;
import com.tokan.ir.fragment.SettingFragment;
import com.tokan.ir.fragment.SicklyInfoFragment;
import com.tokan.ir.fragment.UserSettingFragment;
import com.tokan.ir.model.EventModel;
import com.tokan.ir.widget.BTextView;
import com.tokan.ir.widget.drawer.DrawerList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView listView_drawer;
    private RelativeLayout layout_drawer;

    @BindView(R.id.btn_test)
    CardView btn_test;

    @BindView(R.id.txt_username)
    BTextView txt_username;

    @BindView(R.id.img_user)
    CircleImageView img_user;

    @BindView(R.id.btn_search)
    CardView btn_search;

    @BindView(R.id.btn_report)
    CardView btn_report;

    @BindView(R.id.btn_setting)
    CardView btn_setting;

    @BindView(R.id.txt_title)
    BTextView txt_title;

    @BindView(R.id.img_arrow)
    ImageView img_arrow;

    private List<User> userList = new ArrayList<>();
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        layout_drawer = (RelativeLayout) findViewById(R.id.layout_drawer);
        listView_drawer = (RecyclerView) findViewById(R.id.listView_drawer);

        DrawerList drawerlayout = new DrawerList(MainActivity.this, drawerLayout, layout_drawer, listView_drawer);
        drawerlayout.addListDrawer();

        ButterKnife.bind(this);

        //txt_title.setVisibility(View.GONE);
        img_arrow.setBackgroundResource(R.drawable.ic_slidemenu);


        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                closeDrawer();
            }
        });


        getUserList();


    }

    public void test(View view) {
        gotoFragment(new SicklyInfoFragment(), "SicklyInfoFragment");

    }

    public void search(View view) {
        gotoFragment(new SearchFragment(), "SearchFragment");

    }

    public void report(View view) {
        gotoFragment(new ReportFragment(), "ReportFragment");
    }


    public void setting(View view) {

        gotoFragment(new SettingFragment(), "SettingFragment");
    }

    private void gotoFragment(Fragment fragment, String name) {
        EventBus.getDefault().post(new EventModel(name));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void getUserList() {
        class GetInfo extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                userList = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().userDao().getUsers();
                return userList;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);

                User user = users.get(0);
                Bitmap bitmap = resizeBitmap(user.getPath(), 200, 200);
                img_user.setImageBitmap(bitmap);
                txt_username.setText(user.getNameFamily());

                System.out.println("user.getPath()===" + user.getPath());
            }
        }

        new GetInfo().execute();
    }


    private Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }

    protected OnBackPressedListener onBackPressedListener;

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }


    private void closeDrawer() {
        if (drawerLayout.isDrawerOpen(layout_drawer))
            drawerLayout.closeDrawer(layout_drawer);
        else
            drawerLayout.openDrawer(layout_drawer);
    }


    @Subscribe
    public void getFragmentName(EventModel model) {

        getFragmentName(model.getFragmentNAme());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Fragment fragmentInFrame = getSupportFragmentManager().findFragmentById(R.id.home_container);
        /*if (fragmentInFrame != null) {
            super.onBackPressed();
        }*/

        String name = "";
        if (fragmentInFrame instanceof SicklyInfoFragment) {
            name = "SicklyInfoFragment";
        } else if (fragmentInFrame instanceof SearchFragment) {
            name = "SearchFragment";
        } else if (fragmentInFrame instanceof ReportFragment) {
            name = "ReportFragment";
        } else if (fragmentInFrame instanceof SettingFragment) {
            name = "SettingFragment";
        } else if (fragmentInFrame instanceof GraphTestFragment) {
            name = "GraphTestFragment";
        } else if (fragmentInFrame instanceof CompeleteTestFragment) {
            name = "CompeleteTestFragment";
        } else if (fragmentInFrame instanceof ReportDetailFragment) {
            name = "ReportDetailFragment";
        } else if (fragmentInFrame instanceof UserSettingFragment) {
            name = "UserSettingFragment";
        } else if (fragmentInFrame instanceof DeviceSettingFragment) {
            name = "DeviceSettingFragment";
        } else if (fragmentInFrame instanceof ChangePasswordFragment) {
            name = "ChangePasswordFragment";
        } else if (fragmentInFrame instanceof BackupManageFragment) {
            name = "BackupManageFragment";
        } else if (fragmentInFrame instanceof BackupFragment) {
            name = "BackupFragment";
        } else if (fragmentInFrame instanceof ReportErrorFragment) {
            name = "ReportErrorFragment";
        } else if (fragmentInFrame instanceof GuideFragment) {
            name = "GuideFragment";
        } else if (fragmentInFrame instanceof AboutFragment) {
            name = "AboutFragment";
        } else if (fragmentInFrame instanceof ContactUsFragment) {
            name = "ContactUsFragment";
        } else {
            name = "";
        }

        EventBus.getDefault().post(new EventModel(name));

        if (fragmentInFrame == null) {
            txt_title.setText("");
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }
    }

    public void getFragmentName(String name) {
        switch (name) {
            case "SicklyInfoFragment":
                txt_title.setText("اطلاعات بیمار");
                break;

            case "SearchFragment":
                txt_title.setText("جستجو");
                break;

            case "ReportFragment":
                txt_title.setText("گزارشات");
                break;

            case "SettingFragment":
                txt_title.setText("تنظیمات");
                break;

            case "GraphTestFragment":
                txt_title.setText("تست");
                break;

            case "CompeleteTestFragment":
                txt_title.setText("اتمام تست");
                break;

            case "ReportDetailFragment":
                txt_title.setText("جزییات گزارش");
                break;

            case "UserSettingFragment":
                txt_title.setText("تنظیمات کاربری");
                break;

            case "DeviceSettingFragment":
                txt_title.setText("تنظیمات دستگاه");
                break;

            case "ChangePasswordFragment":
                txt_title.setText("تغییر رمز عبور");
                break;

            case "BackupManageFragment":
                txt_title.setText("مدیریت پشتیبان ها");
                break;

            case "BackupFragment":
                txt_title.setText("پشتیبان گیری");
                break;

            case "ReportErrorFragment":
                txt_title.setText("گزارش خطا");
                break;

            case "GuideFragment":
                txt_title.setText("راهنما");
                break;

            case "AboutFragment":
                txt_title.setText("درباره ما");
                break;

            case "ContactUsFragment":
                txt_title.setText("تماس باما");
                break;

        }
    }
}

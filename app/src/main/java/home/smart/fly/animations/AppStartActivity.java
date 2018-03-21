package home.smart.fly.animations;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import home.smart.fly.animations.fragments.ImitateFragment;
import home.smart.fly.animations.fragments.OtherFragment;
import home.smart.fly.animations.fragments.PropertyFragment;
import home.smart.fly.animations.fragments.TraditionFragment;
import home.smart.fly.animations.fragments.ViewsFragment;


public class AppStartActivity extends AppCompatActivity {
    private static final String TAG = "AppStartActivity";
    private Snackbar snackbar = null;
    private CoordinatorLayout main_contetn;
    private Context mContext;

    private List<String> datas = new ArrayList<>();
    private List<String> links = new LinkedList<>();

    private Glide mGlide;
    private Transformation mTransformation;

    private AsyncTask mAsyncTask;
    private Handler mHandler;
    private Looper mLooper;

    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setup spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(
                toolbar.getContext(),
                getResources().getStringArray(R.array.fragments)));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.
                FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
                switch (position) {
                    case 0:
                        transition.replace(R.id.container, new TraditionFragment()).commit();
                        break;
                    case 1:
                        transition.replace(R.id.container, new PropertyFragment()).commit();
                        break;
                    case 2:
                        transition.replace(R.id.container, new ImitateFragment()).commit();
                        break;
                    case 3:
                        transition.replace(R.id.container, new ViewsFragment()).commit();
                        break;
                    case 4:
                        transition.replace(R.id.container, new OtherFragment()).commit();
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner.setSelection(2);
        main_contetn = (CoordinatorLayout) findViewById(R.id.main_content);
        snackbar = Snackbar.make(main_contetn, "确认要退出吗？", Snackbar.LENGTH_SHORT)
                .setAction("退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.cpb_blue));
        snackbar.setActionTextColor(getResources().getColor(R.color.white));

//        PrintSystemDirInfo();

        //


    }

    /**
     * 打印系统目录信息
     */
    private void PrintSystemDirInfo() {


        final int version = Build.VERSION.SDK_INT;
        final String mRelease = Build.VERSION.RELEASE;
        final String mSerial = Build.SERIAL;
        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
        //
        String getDataDirectory = Environment.getDataDirectory().getAbsolutePath();
        String getRootDirectory = Environment.getRootDirectory().getAbsolutePath();
        String getDownloadCacheDirectory = Environment.getDownloadCacheDirectory().getAbsolutePath();
        //
        String DIRECTORY_DCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        String DIRECTORY_DOCUMENTS = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
        String DIRECTORY_PICTURES = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        String DIRECTORY_DOWNLOADS = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        //
        String cacheDir = mContext.getCacheDir().getAbsolutePath();
        String filesDir = mContext.getFilesDir().getAbsolutePath();
        //
        String getExternalCacheDir = mContext.getExternalCacheDir().getAbsolutePath();
        String getExternalFilesDir_DIRECTORY_PICTURES = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        String getExternalFilesDir_DIRECTORY_DOCUMENTS = mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();


        String[] files = new String[mContext.getExternalCacheDirs().length];
        Log.e("device_info1", "mContext = " + mContext);
        Log.e("device_info2", "mContext = " + mContext.getExternalCacheDirs());
        Log.e("device_info3", "mContext = " + mContext.getExternalCacheDirs().length);
        for (int i = 0; i < mContext.getExternalCacheDirs().length; i++) {
            File mFile = mContext.getExternalCacheDirs()[i];
            Log.e("device_info4", "mContext = " + mFile);
            Log.e("device_info5", "mContext = " + mFile.getAbsolutePath());
            files[i] = mFile.getAbsolutePath() + "\n";
        }


        Log.e("device_info", "android.os.Build.VERSION.SDK_INT = " + version);
        Log.e("device_info", "android.os.Build.VERSION.RELEASE = " + mRelease);
        Log.e("device_info", "android.os.Build.SERIAL = " + mSerial);
        Log.e("device_info", "Secure.ANDROID_ID = " + android_id);
        Log.e("device_info", "--------------------------------------------------");
        Log.e("device_info", "Environment.getExternalStorageDirectory() = " + filepath);
        Log.e("device_info", "Environment.getDataDirectory() = " + getDataDirectory);
        Log.e("device_info", "Environment.getRootDirectory() = " + getRootDirectory);
        Log.e("device_info", "Environment.getDownloadCacheDirectory() = " + getDownloadCacheDirectory);
        Log.e("device_info", "--------------------------------------------------");
        Log.e("device_info", "Environment.getExternalStorageDirectory(Environment.DIRECTORY_DCIM) = " + DIRECTORY_DCIM);
        Log.e("device_info", "Environment.getExternalStorageDirectory(Environment.DIRECTORY_DOCUMENTS) = " + DIRECTORY_DOCUMENTS);
        Log.e("device_info", "Environment.getExternalStorageDirectory(Environment.DIRECTORY_PICTURES) = " + DIRECTORY_PICTURES);
        Log.e("device_info", "Environment.getExternalStorageDirectory(Environment.DIRECTORY_DOWNLOADS) = " + DIRECTORY_DOWNLOADS);
        Log.e("device_info", "--------------------------------------------------");
        Log.e("device_info", "mContext.getCacheDir() = " + cacheDir);
        Log.e("device_info", "mContext.getFilesDir() = " + filesDir);
        Log.e("device_info", "--------------------------------------------------");
        Log.e("device_info", "mContext.getExternalCacheDir() = " + getExternalCacheDir);
        Log.e("device_info", "mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES) = " + getExternalFilesDir_DIRECTORY_PICTURES);
        Log.e("device_info", "mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) = " + getExternalFilesDir_DIRECTORY_DOCUMENTS);
        Log.e("device_info", "mContext.getExternalCacheDirs() size= " + files.length + " \n [ ");
        for (String str : files) {
            Log.e("device_info", str);
        }

        ActivityManager mManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        int size = mManager.getMemoryClass();

        Log.e("device_info", "mManager.getMemoryClass()  应用可用内存 = " + size + " M");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_start, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
        //仿home键效果
        moveTaskToBack(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.file_path.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(AppStartActivity.this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MyAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));
            return view;
        }

        @Override
        public Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }
    }

}

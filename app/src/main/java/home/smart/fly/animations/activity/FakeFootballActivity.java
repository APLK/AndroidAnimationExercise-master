package home.smart.fly.animations.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import home.smart.fly.animations.R;
import home.smart.fly.animations.adapter.PlayerBean;
import home.smart.fly.animations.customview.views.BallGameView;
import home.smart.fly.animations.utils.StatusBarUtil;
import home.smart.fly.animations.utils.Tools;

public class FakeFootballActivity extends AppCompatActivity implements BGAOnRVItemClickListener {

    @BindView(R.id.gameView)
    BallGameView mGameView;
    @BindView(R.id.playerLists)
    RecyclerView playerLists;

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.next)
    TextView next;
    @BindView(R.id.content)
    FrameLayout content;
    private Context mContext;
    private MyAdapter adapter;


    private List<PlayerBean> mPlayerBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_football);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.transblue), 0);
        ButterKnife.bind(this);
        mContext = this;
        initData();
    }

    @OnClick({R.id.back, R.id.next})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.next:
                mGameView.clearInvalidView();

                mGameView.setDrawingCacheEnabled(true);
                Bitmap mBitmap = mGameView.getDrawingCache();

                if (mBitmap != null) {
                    new SavePicTask().execute(mBitmap);
                } else {
                    Toast.makeText(mContext, "fail", Toast.LENGTH_SHORT).show();
                }


                break;
            default:
                break;
        }
    }

    private class SavePicTask extends AsyncTask<Bitmap, Void, String> {


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!TextUtils.isEmpty(s)) {
                Intent mIntent = new Intent(mContext, GameViewSaveActivity.class);
                mIntent.putExtra("picUrl", s);
                mContext.startActivity(mIntent);
            } else {
                Toast.makeText(mContext, "fail", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(Bitmap... params) {
            Bitmap mBitmap = params[0];
            String filePath = "";
            Calendar now = new GregorianCalendar();
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            String fileName = simpleDate.format(now.getTime());
            //保存在应用内目录，免去申请读取权限的麻烦

            File mFile = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName + ".jpg");
            try {
                OutputStream mOutputStream = new FileOutputStream(mFile);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, mOutputStream);
                mOutputStream.flush();
                mOutputStream.close();
                filePath = mFile.getAbsolutePath();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return filePath;
        }
    }


    private void initData() {
        String json = Tools.readStrFromAssets("player.json", mContext);
        Gson mGson = new Gson();
        mPlayerBeanList = mGson.fromJson(json, new TypeToken<List<PlayerBean>>() {
        }.getType());

        adapter = new MyAdapter(playerLists);
        adapter.setOnRVItemClickListener(this);
        GridLayoutManager manager = new GridLayoutManager(mContext, 2, LinearLayoutManager.HORIZONTAL, false);
        playerLists.setLayoutManager(manager);
        playerLists.setAdapter(adapter);
        adapter.setData(mPlayerBeanList);
    }


    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

        int mBubblePosition = mGameView.getCurrentPos();

        if (mBubblePosition == -1) {
            Toast.makeText(mContext, "先点击气泡，再添加球员", Toast.LENGTH_SHORT).show();
            return;
        }


        if (mPlayerBeanList.get(position).isSelected()) {
            Toast.makeText(mContext, "球员已被选择!", Toast.LENGTH_SHORT).show();
        } else {


            View avatar = itemView.findViewById(R.id.img);


            int width = avatar.getWidth();
            int height = avatar.getHeight();
            Bitmap bitmap = Tools.View2Bitmap(avatar, width, height);
            int[] location = new int[2];
            itemView.getLocationOnScreen(location);
            if (bitmap != null) {
                mGameView.updatePlayer(bitmap, mPlayerBeanList.get(position).getName(), location, content);
                for (int i = 0; i < mPlayerBeanList.size(); i++) {
                    if (mPlayerBeanList.get(i).getPosition() == mBubblePosition) {
                        //同一个位置，先把上次选中的球员，设置为未选中
                        mPlayerBeanList.get(i).setSelected(false);
                    }
                }
                //将此次更新的球员设置为气泡上选中的球员
                mPlayerBeanList.get(position).setSelected(true);
                mPlayerBeanList.get(position).setPosition(mBubblePosition);
                adapter.notifyDataSetChanged();
            }

        }

    }


    private class MyAdapter extends BGARecyclerViewAdapter<PlayerBean> {


        public MyAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.dqd_player_item);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, PlayerBean model) {
            helper.setText(R.id.name, model.getName());
            Glide.with(mContext).load(model.getPerson_img()).into(helper.getImageView(R.id.img));
            if (model.isSelected()) {
                helper.setImageResource(R.id.isSel, R.drawable.battle_player_state_checked);
            } else {
                helper.setImageResource(R.id.isSel, R.drawable.battle_player_state_unchecked);
            }
        }
    }
}

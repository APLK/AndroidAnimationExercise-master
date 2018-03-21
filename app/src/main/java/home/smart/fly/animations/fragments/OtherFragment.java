package home.smart.fly.animations.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import home.smart.fly.animations.R;
import home.smart.fly.animations.activity.CameraActivity;
import home.smart.fly.animations.activity.FullscreenActivity;
import home.smart.fly.animations.activity.ImgCacheActivity;
import home.smart.fly.animations.activity.InputActivity;
import home.smart.fly.animations.activity.LaunchOtherAppActivity;
import home.smart.fly.animations.activity.LoadCircleImageActivity;
import home.smart.fly.animations.activity.MailActivity;
import home.smart.fly.animations.activity.MergeIncludeViewActivity;
import home.smart.fly.animations.activity.OrientationActivity;
import home.smart.fly.animations.activity.PdfActivity;
import home.smart.fly.animations.activity.ScreenCaptureActivity;
import home.smart.fly.animations.activity.ViewPagerNestedActivity;
import home.smart.fly.animations.activity.ViewStubActivity;
import home.smart.fly.animations.bga.BgaAllActivity;
import home.smart.fly.animations.customview.bottomsheet.BottomSheetActivity;
import home.smart.fly.animations.customview.puzzle.PuzzleActivity;
import home.smart.fly.animations.recyclerview.BaseRecyclerViewActivity;
import home.smart.fly.animations.utils.V;
import home.smart.fly.animations.webview.AllWebViewActivity;


/**
 * Created by rookie on 2016/8/12.
 */
public class OtherFragment extends Fragment {
    private Context mContext;
    private View rootView;

    private RecyclerView recyclerView;

    private List<ItemInfo> demos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frament_custom_views, null);
        InitView();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void InitView() {
        demos.add(new ItemInfo(R.string.take_screen, ScreenCaptureActivity.class));
        demos.add(new ItemInfo(R.string.ViewStub, ViewStubActivity.class));
        demos.add(new ItemInfo(R.string.ViewMerge, MergeIncludeViewActivity.class));
        demos.add(new ItemInfo(R.string.launch_app, LaunchOtherAppActivity.class));
        demos.add(new ItemInfo(R.string.viewpager_nested, ViewPagerNestedActivity.class));
        demos.add(new ItemInfo(R.string.full_screen, FullscreenActivity.class));
        demos.add(new ItemInfo(R.string.pdf_view, PdfActivity.class));
        demos.add(new ItemInfo(R.string.mail_view, MailActivity.class));
        demos.add(new ItemInfo(R.string.puzzle_game, PuzzleActivity.class));
        demos.add(new ItemInfo(R.string.webviewInfo, AllWebViewActivity.class));
        demos.add(new ItemInfo(R.string.camera, CameraActivity.class));
        demos.add(new ItemInfo(R.string.bottomSheet, BottomSheetActivity.class));
        demos.add(new ItemInfo(R.string.image_cache, ImgCacheActivity.class));
        demos.add(new ItemInfo(R.string.bga_view, BgaAllActivity.class));
        demos.add(new ItemInfo(R.string.circle_view, LoadCircleImageActivity.class));
        demos.add(new ItemInfo(R.string.base_recyclerview, BaseRecyclerViewActivity.class));
        demos.add(new ItemInfo(R.string.input_view, InputActivity.class));
        demos.add(new ItemInfo(R.string.orientation, OrientationActivity.class));


        recyclerView = V.f(rootView, R.id.recyclerView);
        MyAdpater myAdpater = new MyAdpater();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(myAdpater);
    }


    private class MyAdpater extends RecyclerView.Adapter<MyAdpater.MyHolder> {


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.demo_info_item, null);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            holder.title.setText(demos.get(position).activitys.getSimpleName());
            holder.desc.setText(demos.get(position).desc);
            holder.itemshell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, demos.get(position).activitys);
                    startActivity(intent);
                }
            });
        }


        @Override
        public long getItemId(int id) {
            return id;
        }

        @Override
        public int getItemCount() {
            return demos.size();
        }


        class MyHolder extends RecyclerView.ViewHolder {
            TextView title, desc;
            LinearLayout itemshell;

            public MyHolder(View itemView) {
                super(itemView);
                title = V.f(itemView, R.id.title);
                desc = V.f(itemView, R.id.desc);
                itemshell = V.f(itemView, R.id.itemshell);
            }
        }

    }


    private class ItemInfo {
        private final int desc;
        private final Class<? extends Activity> activitys;

        public ItemInfo(int desc, Class<? extends Activity> demoClass) {
            this.desc = desc;
            this.activitys = demoClass;
        }
    }
}

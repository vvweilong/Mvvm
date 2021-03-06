package com.yezao.mvvm;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

public class CoordinatActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinat);


        refreshInit();

        recyclerViewInit();

        appbarInit();

//        tabLayoutInit();// 单个 tablayout

        tabLayoutsInit();//两个tablayout
    }

    private void refreshInit() {
        SmartRefreshLayout refreshLayout = findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshLayout.finishLoadmore(1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshLayout.finishRefresh(1000);
            }
        });
    }

    private void recyclerViewInit() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public int getItemViewType(int position) {
                return position % 5;
//
//                if (position % 5) {
//                    return 1;
//                } else if (position <= 40) {
//                    return 2;
//                } else if (position <= 60) {
//                    return 3;
//                } else if (position <= 80) {
//                    return 4;
//                } else {
//                    return 5;
//                }
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_layout, viewGroup, false);
                return new MyHolder(itemView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                String tagContent = null;
                switch (getItemViewType(i)) {
                    case 2:
                        tagContent = "第二种 了 这是 ";
                        break;
                    case 3:
                        tagContent = "这是 第三种 tag ";
                        break;
                    default:
                        tagContent = "";
                        break;
                }

                ((MyHolder) viewHolder).setTagText(tagContent);
            }

            @Override
            public int getItemCount() {
                return 100;
            }

            class MyHolder extends RecyclerView.ViewHolder {
                private TextView tagView;

                public MyHolder(@NonNull View itemView) {
                    super(itemView);
                    tagView = itemView.findViewById(R.id.tag_textview);
                }

                public void setTagText(String tagStr) {

                    if (tagView != null) {
                        tagView.setVisibility(TextUtils.isEmpty(tagStr) ? View.GONE : View.VISIBLE);
                        tagView.setText(tagStr);
                    }
                }

                public void showTag(boolean show) {
                    if (tagView != null) {
                        tagView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
                    }
                }
            }

        });
        SwitchAnimView switchAnimView = findViewById(R.id.swipe_anim_layout);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i(TAG, "onScrolled: dy = " + dy);
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                String tagContent = null;
                switch (recyclerView.getAdapter().getItemViewType(firstVisibleItemPosition)) {
                    case 2:
                        tagContent = "第二种 了 这是 ";
                        break;
                    case 3:
                        tagContent = "这是 第三种 tag ";
                        break;
                    default:
                        tagContent = "";
                        break;

                }
                switchAnimView.showTagView(tagContent, dy > 0);
            }
        });
    }

    private void appbarInit() {
        AppBarLayout appBarLayout = findViewById(R.id.appbar_layout);

        final int[] totalRange = {0};
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
                if (totalRange[0] == 0) {
                    totalRange[0] = appBarLayout.getTotalScrollRange();
                    return;
                }
                if (Math.abs(offset) == 0) {//展开到最大了
                    showTabAnim();
                } else if (Math.abs(offset) == totalRange[0]) {//折叠到最小
                    hideTabAnim();
                }
            }
        });
    }

    private void hideTabAnim() {
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            ((CustomTabItem) tabLayout.getTabAt(i).getCustomView()).hideDescribe();
//        }
//
        if (multiTabLayoutView != null) {
            multiTabLayoutView.hideDescribe();
        }
    }

    private void showTabAnim() {
//        if (tabLayout != null) {
//            for (int i = 0; i < tabLayout.getTabCount(); i++) {
//                ((CustomTabItem) tabLayout.getTabAt(i).getCustomView()).showDescribe();
//            }
//        }
        if (multiTabLayoutView != null) {
            multiTabLayoutView.showDescribe();
        }
    }

    TabLayout tabLayout;

    private void tabLayoutInit() {
        tabLayout = findViewById(R.id.multitab_tablayout);
        for (int i = 0; i < 4; i++) {
            CustomTabItem customTabItem = new CustomTabItem(tabLayout.getContext());
            tabLayout.addTab(tabLayout.newTab().setCustomView(customTabItem));
            customTabItem.setTabText("第" + i + "个TAB");
            customTabItem.setDescribeText("tab 描述文字");
        }
    }

    private MultiTabLayoutView multiTabLayoutView;

    private void tabLayoutsInit() {
        multiTabLayoutView = findViewById(R.id.multi_tablayout_view);

        String[] mainTabs = {"第一个", "第二个", "第三个", "第四个"};
        String[] describeTabs = {"第一描述", "第二描述", "第三描述", "第四描述"};
        multiTabLayoutView.setmainTabs(mainTabs);
        multiTabLayoutView.setDescribeTabs(describeTabs);

    }


}

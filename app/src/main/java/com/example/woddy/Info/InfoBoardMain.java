package com.example.woddy.Info;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.woddy.R;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

public class InfoBoardMain extends Fragment {

    View myFragment;

    ViewPager viewPager;
    TabLayout tabLayout;

    String boardName;
    String tagName;

    public InfoBoardMain() {
    }

    public static InfoBoardMain getInstance() {
        return new InfoBoardMain();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getChildFragmentManager().setFragmentResultListener("tagKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle bundle) {
                tagName = bundle.getString("tagName");
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_info_board_main, container, false);

        viewPager = myFragment.findViewById(R.id.info_viewpager);
        tabLayout = myFragment.findViewById(R.id.info_tablayout);

        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //첫 화면 셋팅
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF6B6B"));
        tabLayout.setSelectedTabIndicatorHeight(10);

        boardName = "정보";
        tagName = "물품지원";

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                switch (pos) {
                    case 0:
                        boardName = "정보";
                        tagName = "생활지원";
                        break;
                    case 1:
                        boardName = "기관";
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new InfoFragment(), "1인 생활");
        adapter.addFragment(new InfoDepartFragment(), "기관 정보");

        viewPager.setAdapter(adapter);

    }
}
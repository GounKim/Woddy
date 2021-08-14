package com.example.woddy.Notice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woddy.Notice.Fragment.FreeFragment;
import com.example.woddy.Notice.Fragment.HabitFragment;
import com.example.woddy.Notice.Fragment.ShareFragment;
import com.example.woddy.Notice.Fragment.TalkFragment;
import com.example.woddy.R;
import com.google.android.material.tabs.TabLayout;


@SuppressWarnings("deprecation")

public class NoticeMain extends Fragment {
    View myFragment;

    ViewPager viewPager;
    TabLayout tabLayout;

    public NoticeMain(){

    }

    public static NoticeMain getInstance() {return new NoticeMain();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_notice_board,container,false);

        viewPager = myFragment.findViewById(R.id.viewPager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);

        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setUpViewPager(ViewPager viewPager2) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new TalkFragment(),"소통게시판");
        adapter.addFragment(new HabitFragment(),"취미게시판");
        adapter.addFragment(new ShareFragment(),"쉐어게시판");
        adapter.addFragment(new FreeFragment(),"자유게시판");

        viewPager.setAdapter(adapter);
    }
}
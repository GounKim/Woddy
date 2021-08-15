package com.example.woddy.PostBoard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.woddy.PostBoard.Fragment.FreeFragment;
import com.example.woddy.PostBoard.Fragment.HabitFragment;
import com.example.woddy.PostBoard.Fragment.ShareFragment;
import com.example.woddy.PostBoard.Fragment.TalkFragment;
import com.example.woddy.PostWriting.AddWritingsActivity;
import com.example.woddy.R;
import com.google.android.material.tabs.TabLayout;


@SuppressWarnings("deprecation")

public class PostBoardMain extends Fragment {
    View myFragment;

    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView addPosting;

    String boardName;
    String tagName;

    public PostBoardMain(){

    }

    public static PostBoardMain getInstance() {return new PostBoardMain();}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getChildFragmentManager().setFragmentResultListener("tagKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                tagName = bundle.getString("tagName");
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_post_board_main,container,false);

        viewPager = myFragment.findViewById(R.id.viewPager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);
        addPosting = myFragment.findViewById(R.id.add_new_posting);


        addPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AddWritingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("boardName", boardName);
                intent.putExtra("tagName", tagName);
                startActivity(intent);
            }
        });

        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        boardName = "소통";

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());
                int pos = tab.getPosition();
                switch (pos) {
                    case 0:
                        boardName = "소통";
                        break;
                    case 1:
                        boardName = "취미";
                        break;
                    case 2:
                        boardName = "쉐어";
                        break;
                    case 3:
                        boardName = "자유";
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

        adapter.addFragment(new TalkFragment(),"소통");
        adapter.addFragment(new HabitFragment(),"취미");
        adapter.addFragment(new ShareFragment(),"쉐어");
        adapter.addFragment(new FreeFragment(),"자유");

        viewPager.setAdapter(adapter);
    }
}
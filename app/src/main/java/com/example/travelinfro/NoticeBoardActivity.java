package com.example.travelinfro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class NoticeBoardActivity extends FragmentActivity {
    TabLayout tabs;
    RecentPostsFragment recentPostsFragment;
    MyPostsFragment myPostsFragment;
    MyTopPostsFragment myTopPostsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        recentPostsFragment = new RecentPostsFragment();
        myPostsFragment = new MyPostsFragment();
        myTopPostsFragment = new MyTopPostsFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.board_frame_layout,recentPostsFragment).commit();
        tabs = findViewById(R.id.board_tab_layout);
        tabs.addTab(tabs.newTab().setText("최신글"));
        tabs.addTab(tabs.newTab().setText("내가 쓴글"));
        tabs.addTab(tabs.newTab().setText("내가 쓴 인기글"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position==0){
                    selected = recentPostsFragment;
                }else if(position==1){
                    selected = myPostsFragment;
                }else if(position==2){
                    selected = myTopPostsFragment;
                }
                assert selected != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.board_frame_layout,selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}

package com.example.travelinfro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class NoticeBoardActivity extends FragmentActivity {
    private static final String TAG = "NoticeBoardActivity";

    TabLayout tabs;
    ViewPager viewPager;

    int boardNum = 0;
    String board = "board";
    FloatingActionButton mBtnNewPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        Intent intent = new Intent(this.getIntent());
        boardNum = intent.getIntExtra("boardNum",0);
        board +=String.valueOf(boardNum);
        Log.e(TAG,"boardNum: "+boardNum);
        Log.e(TAG,"board: "+board );

        tabs = findViewById(R.id.board_tab_layout);
        viewPager = findViewById(R.id.board_view_pager);

        FragmentPagerAdapter mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            private final Fragment[] mFragments = new Fragment[]{
                    new RecentPostsFragment(board),
                    new MyPostsFragment(board),
                    new MyTopPostsFragment(board),
            };
            private final String[] mFragmentNames = new String[]{
                    getString(R.string.heading_recent),
                    getString(R.string.heading_my_posts),
                    getString(R.string.heading_my_top_posts)
            };

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };

        viewPager.setAdapter(mPagerAdapter);
        tabs.setupWithViewPager(viewPager);

        mBtnNewPost = findViewById(R.id.board_floating_action_btn);
        mBtnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NewPostActivity.class);
                intent.putExtra("boardNum",boardNum);
                Log.e(TAG,"send board number"+boardNum);
                startActivity(intent);
            }
        });


    }
}

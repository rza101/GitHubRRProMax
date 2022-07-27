package com.rhezarijaya.githubrrpromax.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.rhezarijaya.githubrrpromax.fragment.UserDetailFollowFragment;
import com.rhezarijaya.githubrrpromax.fragment.UserDetailInfoFragment;

public class UserDetailFragmentAdapter extends FragmentStateAdapter {
    public UserDetailFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();

        switch (position) {
            case 0:
                fragment = new UserDetailInfoFragment();
                break;
            case 1:
                bundle.putInt(UserDetailFollowFragment.USER_DETAIL_FOLLOW_TYPE,
                        UserDetailFollowFragment.USER_DETAIL_FOLLOWERS_FRAGMENT);

                fragment = new UserDetailFollowFragment();
                fragment.setArguments(bundle);

                break;
            case 2:
                bundle.putInt(UserDetailFollowFragment.USER_DETAIL_FOLLOW_TYPE,
                        UserDetailFollowFragment.USER_DETAIL_FOLLOWING_FRAGMENT);

                fragment = new UserDetailFollowFragment();
                fragment.setArguments(bundle);
                break;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

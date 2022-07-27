package com.rhezarijaya.githubrrpromax.ui.userdetail;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayoutMediator;
import com.rhezarijaya.githubrrpromax.adapter.UserDetailFragmentAdapter;
import com.rhezarijaya.githubrrpromax.database.FavoriteRepository;
import com.rhezarijaya.githubrrpromax.databinding.ActivityUserDetailBinding;
import com.rhezarijaya.githubrrpromax.model.UserDetail;
import com.rhezarijaya.githubrrpromax.util.Constants;

public class UserDetailActivity extends AppCompatActivity {

    private final String[] pageTitles = {
            "User Info",
            "Followers",
            "Followings"
    };

    private UserDetail userDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUserDetailBinding binding = ActivityUserDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getParcelableExtra(Constants.INTENT_MAIN_TO_USER_DETAIL) == null) {
            Toast.makeText(this, "Illegal Access!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            userDetail = getIntent().getParcelableExtra(Constants.INTENT_MAIN_TO_USER_DETAIL);
        }

        if (getSupportActionBar() != null) {
            // TODO sebaiknya pakai StringBuilder
            getSupportActionBar().setTitle("@" + userDetail.getLogin());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        FavoriteRepository favoriteRepository = FavoriteRepository.getInstance(getApplication());

        UserDetailViewModel userDetailViewModel = new ViewModelProvider(
                this, (ViewModelProvider.Factory) new UserDetailViewModelFactory(favoriteRepository)
        ).get(UserDetailViewModel.class);

        userDetailViewModel.initUserSearchData(userDetail);
        userDetailViewModel.fetchUserDetail(userDetail.getLogin());
        userDetailViewModel.fetchFollowers(userDetail.getLogin());
        userDetailViewModel.fetchFollowings(userDetail.getLogin());

        UserDetailFragmentAdapter userDetailFragmentAdapter = new UserDetailFragmentAdapter(this);

        binding.userDetailViewPager.setAdapter(userDetailFragmentAdapter);
        new TabLayoutMediator(
                binding.userDetailTabLayout,
                binding.userDetailViewPager,
                (tab, position) -> tab.setText(pageTitles[position])
        ).attach();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
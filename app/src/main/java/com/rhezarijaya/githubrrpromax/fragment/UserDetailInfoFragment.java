package com.rhezarijaya.githubrrpromax.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.rhezarijaya.githubrrpromax.databinding.FragmentUserDetailInfoBinding;
import com.rhezarijaya.githubrrpromax.ui.userdetail.UserDetailViewModel;

public class UserDetailInfoFragment extends Fragment {

    private FragmentUserDetailInfoBinding binding;

    public UserDetailInfoFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserDetailInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserDetailViewModel userDetailViewModel = new ViewModelProvider(requireActivity()).get(UserDetailViewModel.class);

        userDetailViewModel.getUserDetail().observe(requireActivity(), userDetail -> {
            if (userDetail.getLogin() != null) {
                Glide.with(requireActivity())
                        .load(userDetail.getAvatarUrl())
                        .into(binding.fragmentUserDetailInfoCivAvatar);

                binding.fragmentUserDetailInfoTvName.setText(userDetail.getName());
                binding.fragmentUserDetailInfoTvRepositories.setText(String.valueOf(userDetail.getPublicRepos()));
                binding.fragmentUserDetailInfoTvFollowers.setText(String.valueOf(userDetail.getFollowers()));
                binding.fragmentUserDetailInfoTvFollowings.setText(String.valueOf(userDetail.getFollowing()));
                binding.fragmentUserDetailInfoTvEmail.setText(userDetail.getEmail() == null ? "-" : userDetail.getEmail());
                binding.fragmentUserDetailInfoTvLocation.setText(userDetail.getLocation() == null ? "-" : userDetail.getLocation());
                binding.fragmentUserDetailInfoTvCompany.setText(userDetail.getCompany() == null ? "-" : userDetail.getCompany());
            }
        });

        userDetailViewModel.getIsUserDetailLoading().observe(requireActivity(),
                isLoading -> binding.fragmentUserDetailInfoProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        userDetailViewModel.getUserDetailsError().observe(requireActivity(), error -> {
            if (!error.hasBeenDone()) {
                Toast.makeText(requireActivity(), error.getData(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
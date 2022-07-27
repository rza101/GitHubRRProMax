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
import com.rhezarijaya.githubrrpromax.R;
import com.rhezarijaya.githubrrpromax.database.FavoriteRepository;
import com.rhezarijaya.githubrrpromax.databinding.FragmentUserDetailInfoBinding;
import com.rhezarijaya.githubrrpromax.model.Favorite;
import com.rhezarijaya.githubrrpromax.ui.userdetail.UserDetailViewModel;
import com.rhezarijaya.githubrrpromax.ui.userdetail.UserDetailViewModelFactory;

public class UserDetailInfoFragment extends Fragment {

    private FragmentUserDetailInfoBinding binding;
    private UserDetailViewModel userDetailViewModel;

    public UserDetailInfoFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserDetailInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FavoriteRepository favoriteRepository = FavoriteRepository.getInstance(requireActivity().getApplication());

        userDetailViewModel = new ViewModelProvider(
                requireActivity(), (ViewModelProvider.Factory) new UserDetailViewModelFactory(favoriteRepository)
        ).get(UserDetailViewModel.class);

        userDetailViewModel.getUserDetailWithFavorite().observe(requireActivity(), userDetail -> {
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

                Glide.with(requireActivity())
                        .load(userDetail.isOnFavorite() ?
                                R.drawable.ic_baseline_star_24 :
                                R.drawable.ic_baseline_star_outline_24)
                        .into(binding.fragmentUserDetailInfoIvFavorite);

                binding.fragmentUserDetailInfoIvFavorite.setOnClickListener(v -> {
                    Favorite favorite = new Favorite();
                    favorite.setUsername(userDetail.getLogin());
                    favorite.setUserId(userDetail.getId());
                    favorite.setAvatarUrl(userDetail.getAvatarUrl());

                    if (userDetail.isOnFavorite()) {
                        userDetailViewModel.deleteFavorite(favorite);
                        Toast.makeText(requireActivity(), "Successfully deleted from favorites!", Toast.LENGTH_SHORT).show();
                    } else {
                        userDetailViewModel.insertFavorite(favorite);
                        Toast.makeText(requireActivity(), "Successfully inserted into favorites!", Toast.LENGTH_SHORT).show();
                    }
                });
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

    @Override
    public void onPause() {
        userDetailViewModel.mediatorRemoveSources();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        userDetailViewModel.mediatorAddSources();
    }
}
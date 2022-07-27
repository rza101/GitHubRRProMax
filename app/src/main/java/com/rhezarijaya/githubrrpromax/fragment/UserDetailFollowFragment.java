package com.rhezarijaya.githubrrpromax.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rhezarijaya.githubrrpromax.R;
import com.rhezarijaya.githubrrpromax.adapter.UserListAdapter;
import com.rhezarijaya.githubrrpromax.database.FavoriteRepository;
import com.rhezarijaya.githubrrpromax.databinding.FragmentUserDetailFollowBinding;
import com.rhezarijaya.githubrrpromax.model.Favorite;
import com.rhezarijaya.githubrrpromax.ui.userdetail.UserDetailActivity;
import com.rhezarijaya.githubrrpromax.ui.userdetail.UserDetailViewModel;
import com.rhezarijaya.githubrrpromax.ui.userdetail.UserDetailViewModelFactory;
import com.rhezarijaya.githubrrpromax.util.Constants;

public class UserDetailFollowFragment extends Fragment {

    public static final String USER_DETAIL_FOLLOW_TYPE = "USER_DETAIL_FOLLOW_TYPE";
    public static final int USER_DETAIL_FOLLOWERS_FRAGMENT = 0;
    public static final int USER_DETAIL_FOLLOWING_FRAGMENT = 1;

    private FragmentUserDetailFollowBinding binding;
    private UserListAdapter userListAdapter;
    private UserDetailViewModel userDetailViewModel;

    public UserDetailFollowFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserDetailFollowBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            int fragmentType = getArguments().getInt(USER_DETAIL_FOLLOW_TYPE);

            if (fragmentType == USER_DETAIL_FOLLOWERS_FRAGMENT ||
                    fragmentType == USER_DETAIL_FOLLOWING_FRAGMENT) {

                FavoriteRepository favoriteRepository = FavoriteRepository.getInstance(requireActivity().getApplication());

                userDetailViewModel = new ViewModelProvider(
                        requireActivity(), (ViewModelProvider.Factory) new UserDetailViewModelFactory(favoriteRepository)
                ).get(UserDetailViewModel.class);

                userListAdapter = new UserListAdapter(
                        data -> {
                            Intent intent = new Intent(requireActivity(), UserDetailActivity.class);
                            intent.putExtra(Constants.INTENT_MAIN_TO_USER_DETAIL, data);
                            startActivity(intent);
                        },
                        data -> {
                            Favorite favorite = new Favorite();
                            favorite.setUsername(data.getLogin());
                            favorite.setUserId(data.getId());
                            favorite.setAvatarUrl(data.getAvatarUrl());

                            if (data.isOnFavorite()) {
                                userDetailViewModel.deleteFavorite(favorite);
                                Toast.makeText(requireActivity(), "Successfully deleted from favorites", Toast.LENGTH_SHORT).show();
                            } else {
                                userDetailViewModel.insertFavorite(favorite);
                                Toast.makeText(requireActivity(), "Successfully inserted from favorites", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                // followers
                if (fragmentType == USER_DETAIL_FOLLOWERS_FRAGMENT) {
                    userDetailViewModel.getFollowersWithFavorite().observe(requireActivity(), userDetails -> {
                        if (userDetails.size() == 0) {
                            binding.fragmentUserDetailFollowTvNoItem.setText(R.string.no_item);
                        } else {
                            binding.fragmentUserDetailFollowTvNoItem.setText("");
                        }

                        userListAdapter.submitList(userDetails);
                    });
                    userDetailViewModel.getFollowersError().observe(requireActivity(), error -> {
                        if (!error.hasBeenDone()) {
                            Toast.makeText(requireActivity(), error.getData(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    userDetailViewModel.getIsFollowersLoading().observe(requireActivity(),
                            isLoading -> {
                                binding.fragmentUserDetailFollowProgressBar.setVisibility(
                                        isLoading ?
                                                View.VISIBLE :
                                                View.GONE);
                                binding.fragmentUserDetailFollowTvNoItem.setVisibility(
                                        isLoading ?
                                                View.GONE :
                                                View.VISIBLE);
                            }
                    );
                }

                // followings
                if (fragmentType == USER_DETAIL_FOLLOWING_FRAGMENT) {
                    userDetailViewModel.getFollowingsWithFavorite().observe(requireActivity(), userDetails -> {
                        if (userDetails.size() == 0) {
                            binding.fragmentUserDetailFollowTvNoItem.setText(R.string.no_item);
                        } else {
                            binding.fragmentUserDetailFollowTvNoItem.setText("");
                        }

                        userListAdapter.submitList(userDetails);
                    });
                    userDetailViewModel.getFollowingsError().observe(requireActivity(), error -> {
                        if (!error.hasBeenDone()) {
                            Toast.makeText(requireActivity(), error.getData(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    userDetailViewModel.getIsFollowingsLoading().observe(requireActivity(),
                            isLoading -> {
                                binding.fragmentUserDetailFollowProgressBar.setVisibility(isLoading ?
                                        View.VISIBLE :
                                        View.GONE);
                                binding.fragmentUserDetailFollowTvNoItem.setVisibility(
                                        isLoading ?
                                                View.GONE :
                                                View.VISIBLE);
                            }
                    );
                }

                binding.fragmentUserDetailFollowRv.setAdapter(userListAdapter);
                binding.fragmentUserDetailFollowRv.setLayoutManager(new LinearLayoutManager(requireActivity()));
            }
        }
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
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

import com.rhezarijaya.githubrrpromax.adapter.UserListAdapter;
import com.rhezarijaya.githubrrpromax.database.FavoriteRepository;
import com.rhezarijaya.githubrrpromax.databinding.FragmentMainFavoriteBinding;
import com.rhezarijaya.githubrrpromax.model.Favorite;
import com.rhezarijaya.githubrrpromax.model.UserDetail;
import com.rhezarijaya.githubrrpromax.ui.main.MainViewModel;
import com.rhezarijaya.githubrrpromax.ui.main.MainViewModelFactory;
import com.rhezarijaya.githubrrpromax.ui.userdetail.UserDetailActivity;
import com.rhezarijaya.githubrrpromax.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainFavoriteFragment extends Fragment {

    private FragmentMainFavoriteBinding binding;
    private MainViewModel mainViewModel;
    private UserListAdapter userListAdapter;

    public MainFavoriteFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FavoriteRepository favoriteRepository = FavoriteRepository.getInstance(requireActivity().getApplication());

        mainViewModel = new ViewModelProvider(requireActivity(),
                (ViewModelProvider.Factory) new MainViewModelFactory(favoriteRepository)
        ).get(MainViewModel.class);

        mainViewModel.getFavoriteList().observe(requireActivity(), favoriteList -> {
            if(favoriteList.size() == 0){
                binding.fragmentMainFavoriteTvNoItem.setVisibility(View.VISIBLE);
            }else{
                binding.fragmentMainFavoriteTvNoItem.setVisibility(View.GONE);
            }

            List<UserDetail> userDetailList = new ArrayList<>();

            for (Favorite favorite : favoriteList) {
                UserDetail userDetail = new UserDetail();
                userDetail.setLogin(favorite.getUsername());
                userDetail.setId(favorite.getUserId());
                userDetail.setAvatarUrl(favorite.getAvatarUrl());
                userDetail.setOnFavorite(true);

                userDetailList.add(userDetail);
            }

            userListAdapter.submitList(userDetailList);
        });

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

                    mainViewModel.deleteFavorite(favorite);
                    Toast.makeText(requireActivity(), "Successfully deleted from favorites!", Toast.LENGTH_SHORT).show();
                }
        );

        binding.fragmentMainFavoriteRvFav.setAdapter(userListAdapter);
        binding.fragmentMainFavoriteRvFav.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }
}
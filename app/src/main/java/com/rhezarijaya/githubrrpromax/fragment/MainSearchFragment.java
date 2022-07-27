package com.rhezarijaya.githubrrpromax.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rhezarijaya.githubrrpromax.R;
import com.rhezarijaya.githubrrpromax.adapter.UserListAdapter;
import com.rhezarijaya.githubrrpromax.database.FavoriteRepository;
import com.rhezarijaya.githubrrpromax.databinding.FragmentMainSearchBinding;
import com.rhezarijaya.githubrrpromax.model.Favorite;
import com.rhezarijaya.githubrrpromax.ui.main.MainViewModel;
import com.rhezarijaya.githubrrpromax.ui.main.MainViewModelFactory;
import com.rhezarijaya.githubrrpromax.ui.userdetail.UserDetailActivity;
import com.rhezarijaya.githubrrpromax.util.Constants;

import java.util.ArrayList;

public class MainSearchFragment extends Fragment {

    private FragmentMainSearchBinding binding;
    private MainViewModel mainViewModel;
    private UserListAdapter userListAdapter;

    public MainSearchFragment() {}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FavoriteRepository favoriteRepository = FavoriteRepository.getInstance(requireActivity().getApplication());

        mainViewModel = new ViewModelProvider(requireActivity(), (ViewModelProvider.Factory) new MainViewModelFactory(favoriteRepository)).get(MainViewModel.class);

        mainViewModel.getUserDetailWithFavorite().observe(requireActivity(), userDetails -> {
            if (userDetails.size() == 0) {
                binding.fragmentMainSearchTvNoResult.setText(R.string.no_result);
                binding.fragmentMainSearchTvInfo.setText("");
            } else {
                binding.fragmentMainSearchTvNoResult.setText("");
                binding.fragmentMainSearchTvInfo.setText(
                        String.format(
                                requireActivity().getResources().getString(R.string.search_info),
                                userDetails.size(),
                                binding.fragmentMainSearchSearchview.getQuery().toString()
                        )
                );
            }

            userListAdapter.submitList(userDetails);
        });

        mainViewModel.getError().observe(requireActivity(), errors -> {
            if (!errors.hasBeenDone()) {
                Toast.makeText(requireActivity(), errors.getData(), Toast.LENGTH_SHORT).show();
            }
        });

        mainViewModel.isLoading().observe(requireActivity(), isLoading -> {
            binding.fragmentMainSearchProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.fragmentMainSearchTvInfo.setVisibility(isLoading ? View.GONE : View.VISIBLE);
            binding.fragmentMainSearchTvNoResult.setVisibility(isLoading ? View.GONE : View.VISIBLE);
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

                    if (data.isOnFavorite()) {
                        mainViewModel.deleteFavorite(favorite);
                        Toast.makeText(requireActivity(), "Successfully deleted from favorites!", Toast.LENGTH_SHORT).show();
                    } else {
                        mainViewModel.insertFavorite(favorite);
                        Toast.makeText(requireActivity(), "Successfully inserted into favorites!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        binding.fragmentMainSearchSearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.fragmentMainSearchSearchview.clearFocus();
                userListAdapter.submitList(new ArrayList<>());
                mainViewModel.search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.fragmentMainSearchRvSearch.setAdapter(userListAdapter);
        binding.fragmentMainSearchRvSearch.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }

    @Override
    public void onPause() {
        mainViewModel.mediatorRemoveSources();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mainViewModel.mediatorAddSources();
    }
}
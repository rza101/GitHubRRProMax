package com.rhezarijaya.githubrrpromax.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayoutMediator;
import com.rhezarijaya.githubrrpromax.R;
import com.rhezarijaya.githubrrpromax.adapter.MainFragmentAdapter;
import com.rhezarijaya.githubrrpromax.databinding.ActivityMainBinding;
import com.rhezarijaya.githubrrpromax.ui.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private final String[] pageTitles = {
            "Search",
            "Favorites"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(this);

        binding.mainViewPager.setAdapter(mainFragmentAdapter);
        binding.mainViewPager.setUserInputEnabled(false);

        new TabLayoutMediator(
                binding.mainTabLayout,
                binding.mainViewPager,
                (tab, position) -> tab.setText(pageTitles[position])
        ).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_main_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
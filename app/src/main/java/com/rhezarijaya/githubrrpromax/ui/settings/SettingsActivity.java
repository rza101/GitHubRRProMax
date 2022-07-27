package com.rhezarijaya.githubrrpromax.ui.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rhezarijaya.githubrrpromax.R;
import com.rhezarijaya.githubrrpromax.databinding.ActivitySettingsBinding;
import com.rhezarijaya.githubrrpromax.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.settings);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(binding.settingsClRoot.getId(), new SettingsFragment())
                .commit();
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
package com.example.ioproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ViewPager2 sampleViewPager = findViewById(R.id.sampleViewPager);
        sampleViewPager.setAdapter(
                new SampleAdapter(this)
        );
    }

    class SampleAdapter extends FragmentStateAdapter {

        public SampleAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public SampleAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        public SampleAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new SampleFragment();
                case 1:
                    return new SampleFragment2();
                case 2:
                    return new SampleFragment3();
                // Add more cases as needed for additional fragments
                default:
                    return new SampleFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
package com.expensexpert.expensexpert;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.expensexpert.expensexpert.fragments.BalanceFragment;
import com.expensexpert.expensexpert.fragments.ExpenseFragment;
import com.expensexpert.expensexpert.fragments.MemberFragment;
import com.expensexpert.expensexpert.models.DatabaseHelper;
import com.expensexpert.expensexpert.models.Group;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SingleTour extends AppCompatActivity {
    int GroupId;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_tour);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Bundle extras = getIntent().getExtras();

        GroupId = extras.getInt("GroupId", 1);

        Fragment fragment = new BalanceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("GroupId", GroupId);
        fragment.setArguments(bundle);

        DatabaseHelper db = new DatabaseHelper(this);
        Group group = db.get_Group_specific(GroupId);
        getSupportActionBar().setTitle(group.getName());

        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_host_fragment, fragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_balance:
                    fragment = new BalanceFragment();
                    break;
                case R.id.navigation_expense:
                    fragment = new ExpenseFragment();
                    break;
                case R.id.navigation_members:
                    fragment = new MemberFragment();
                    break;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("GroupId", GroupId);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_host_fragment, fragment).commit();
            return true;
        }
    };
}

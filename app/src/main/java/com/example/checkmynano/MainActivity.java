package com.example.checkmynano;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {



/*Bottom nav listener******************************************************************************/
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.navigation_home:

                    MainFragment mp = new MainFragment();
                    android.support.v4.app.FragmentManager manager1 = getSupportFragmentManager();
                    manager1.beginTransaction().replace(R.id.mainContentLayout,
                            mp,
                            mp.getTag()).commit();

                    return true;
                case R.id.navigation_options:
                    OptionsFragment op = new OptionsFragment();
                    android.support.v4.app.FragmentManager manager2 = getSupportFragmentManager();
                    manager2.beginTransaction().replace(R.id.mainContentLayout,
                            op,
                            op.getTag()).commit();


                    return true;
            }


           return false;
        }
    };
/* onCreate function*******************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainFragment mp = new MainFragment();
        android.support.v4.app.FragmentManager manager1 = getSupportFragmentManager();
        manager1.beginTransaction().replace(R.id.mainContentLayout,
                mp,
                mp.getTag()).commit();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}

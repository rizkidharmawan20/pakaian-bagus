package com.example.pakaianbagus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.pakaianbagus.presentation.barangmasuk.BarangMasukFragment;
import com.example.pakaianbagus.presentation.home.HomeFragment;
import com.example.pakaianbagus.presentation.katalog.KatalogFragment;
import com.example.pakaianbagus.presentation.mutasibarang.MutasiBarangFragment;
import com.example.pakaianbagus.presentation.penjualan.InputHarianFragment;
import com.example.pakaianbagus.presentation.stockopname.StockOpnameFragment;

import java.util.Objects;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    final Fragment fragmentHome = new HomeFragment();
    final Fragment fragmentStockOpname = new StockOpnameFragment();
    final Fragment fragmentBarangMasuk = new BarangMasukFragment();
    final Fragment fragmentInputHarian = new InputHarianFragment();
    final Fragment fragmentMutasiBarang = new MutasiBarangFragment();
    final Fragment katalogFragment = new KatalogFragment();
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = Objects.requireNonNull(fm).beginTransaction();
    Fragment active = fragmentHome;

    @SuppressLint("CommitTransaction")
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                setFragments(fragmentHome, "1");
                active = fragmentHome;
                return true;
            case R.id.navigation_katalog:
                setFragments(katalogFragment, "2");
                active = fragmentMutasiBarang;
                return true;
            case R.id.navigation_mutasi_barang:
                setFragments(fragmentMutasiBarang, "3");
                active = fragmentMutasiBarang;
                return true;
            case R.id.navigation_penjualan:
                setFragments(fragmentInputHarian, "4");
                active = fragmentInputHarian;
                return true;
        }
        return false;
    };

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Objects.requireNonNull(getSupportActionBar()).hide(); //<< this
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm.beginTransaction().add(R.id.main_container, fragmentHome, "1").commit();

    }

    private void setFragments(Fragment fragment, String string){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = Objects.requireNonNull(fm).beginTransaction();
        ft.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left);
        ft.replace(R.id.main_container, fragment, string);
        ft.commit();
    }

}

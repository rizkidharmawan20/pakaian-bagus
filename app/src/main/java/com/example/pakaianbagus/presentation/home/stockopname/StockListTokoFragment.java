package com.example.pakaianbagus.presentation.home.stockopname;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pakaianbagus.R;
import com.example.pakaianbagus.api.KatalogHelper;
import com.example.pakaianbagus.models.ApiResponse;
import com.example.pakaianbagus.models.KatalogTokoModel;
import com.example.pakaianbagus.models.TokoResponse;
import com.example.pakaianbagus.presentation.home.stockopname.adapter.StockTokoAdapter;
import com.example.pakaianbagus.util.Constanta;
import com.example.pakaianbagus.util.IOnBackPressed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockListTokoFragment extends Fragment implements IOnBackPressed {

    private List<KatalogTokoModel> katalogTokoModels;
    private int choose;
    private String idBrand;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    public StockListTokoFragment() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.spg_fragment, container, false);
        ButterKnife.bind(this, rootView);

        try {
            choose = Objects.requireNonNull(getArguments()).getInt("choose");
            idBrand = Objects.requireNonNull(getArguments()).getString("id_brand");
        } catch (Exception e) {
            e.printStackTrace();
        }

        toolbar_title.setText("Stock Opname");
        katalogTokoModels = new ArrayList<>();

        getCurrentDateChecklist();
        getListToko();

        swipeRefresh.setOnRefreshListener(() -> {
            katalogTokoModels.clear();
            getListToko();
        });

        return rootView;
    }

    private void getCurrentDateChecklist() {
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy MMMM dd");
        String formattedDate = df.format(c);
        tvDate.setText(formattedDate);
    }

    public void getListToko() {
        swipeRefresh.setRefreshing(true);
        KatalogHelper.getListToko(new Constanta().PLACE_TYPE_SHOP, new Callback<ApiResponse<List<TokoResponse>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<List<TokoResponse>>> call, @NonNull Response<ApiResponse<List<TokoResponse>>> response) {
                swipeRefresh.setRefreshing(false);
                try {
                    if (Objects.requireNonNull(response.body()).getData() != null) {
                        List<TokoResponse> tokoResponse = response.body().getData();
                        for (int i = 0; i < tokoResponse.size(); i++) {
                            TokoResponse dataToko = tokoResponse.get(i);
                            katalogTokoModels.add(new KatalogTokoModel(dataToko.getId(), dataToko.getName(), dataToko.getType()));
                        }
                        StockTokoAdapter stockTokoAdapter = new StockTokoAdapter(katalogTokoModels, getContext(), StockListTokoFragment.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                                RecyclerView.VERTICAL, false));
                        recyclerView.setAdapter(stockTokoAdapter);
                    }
                } catch (Exception e) {
                    Log.d("Catch", "getListToko");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<List<TokoResponse>>> call, @NonNull Throwable t) {
                swipeRefresh.setRefreshing(false);
                Log.d("List Toko Katalog", t.getMessage());

            }
        });
    }

    public void onClickItem(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id_toko", id);
        bundle.putString("id_brand", idBrand);
        bundle.putInt("choose", choose);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = Objects.requireNonNull(fm).beginTransaction();
        StockOpnameFragment stockOpnameFragment = new StockOpnameFragment();
        stockOpnameFragment.setArguments(bundle);
        ft.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
        ft.replace(R.id.baseLayoutSpg, stockOpnameFragment);
        ft.commit();
    }

    @OnClick(R.id.toolbar_back)
    public void toolbarBack() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = Objects.requireNonNull(fm).beginTransaction();
        StockListBrandFragment homeFragment = new StockListBrandFragment();
        ft.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
        ft.replace(R.id.baseLayoutSpg, homeFragment);
        ft.commit();
    }

    @Override
    public boolean onBackPressed() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = Objects.requireNonNull(fm).beginTransaction();
        StockListBrandFragment homeFragment = new StockListBrandFragment();
        ft.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
        ft.replace(R.id.baseLayout, homeFragment);
        ft.commit();

        return false;
    }
}

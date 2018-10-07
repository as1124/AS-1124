package com.as1124.ch14test;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.as1124.ch14test.db.City;
import com.as1124.ch14test.db.Country;
import com.as1124.ch14test.db.Province;
import com.as1124.ch14test.db.ProvinceDao;
import com.as1124.ch14test.util.GSONUtil;
import com.as1124.ch14test.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {

    public static final int LEVEL_PROVINCE = 0;

    public static final int LEVEL_CITY = 1;

    public static final int LEVEL_COUNTRY = 2;

    private ProgressDialog progressDialog;

    private ListView listView;

    private TextView titleText;

    private Button backButton;

    private ArrayAdapter<String> adapter;

    private List<String> dataList = new ArrayList<>();

    private List<Province> provinceList;
    private List<City> cityList;
    private List<Country> countryList;

    private Province selectedProvince;
    private City selectedCity;
    private Country selectedCountry;

    private int currentLevel;

    public ChooseAreaFragment() {
        // Required empty public constructor
    }

    public static ChooseAreaFragment newInstance(String param1, String param2) {
        ChooseAreaFragment fragment = new ChooseAreaFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_area, container, false);
        titleText = view.findViewById(R.id.title_text);
        backButton = view.findViewById(R.id.back_button);
        listView = view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener((parent, item, position, id) -> {
            switch (currentLevel) {
                case LEVEL_PROVINCE:
                    selectedProvince = provinceList.get(position);
                    queryCities();
                    break;
                case LEVEL_CITY:
                    selectedCity = cityList.get(position);
                    queryCounties();
                    break;
                case LEVEL_COUNTRY:
                    selectedCountry = countryList.get(position);
                default:
                    break;
            }
        });
        backButton.setOnClickListener(v -> {
            if (LEVEL_COUNTRY == currentLevel) {
                queryCities();
            } else if (LEVEL_CITY == currentLevel) {
                queryProvinces();
            }
        });
        queryProvinces();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void queryProvinces() {
        titleText.setText("China");
        backButton.setVisibility(View.INVISIBLE);
        provinceList = CH14Application.getDaoSession().getProvinceDao().loadAll();
        if (!provinceList.isEmpty()) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "");
        }

    }

    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        ProvinceDao provinceDao = CH14Application.getDaoSession().getProvinceDao();
        Province province = provinceDao.load(selectedProvince.getId());
        cityList = province.getCities();
        if (cityList.isEmpty()) {
            String address = "";
        } else {

        }
    }

    private void queryCounties() {

    }

    protected void queryFromServer(String address, String type) {
//        showProgressDialog();
        okhttp3.Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                android.util.Log.e("HttpRequest", "数据查询失败" + address, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.toString();
                response.close();
                if (LEVEL_PROVINCE == currentLevel) {
                    GSONUtil.handleProvinceResponse(result);
                } else if (LEVEL_CITY == currentLevel) {
                    GSONUtil.handleCityResponse(result, selectedProvince.getId());
                } else {
                    GSONUtil.handleCountryResponse(result, selectedCity.getId());
                }
            }
        };
        HttpUtil.sendHttpRequest(address, callback);
    }

}

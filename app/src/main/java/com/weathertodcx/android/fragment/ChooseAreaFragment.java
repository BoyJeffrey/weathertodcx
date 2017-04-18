package com.weathertodcx.android.fragment;

import android.app.ProgressDialog;
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

import com.weathertodcx.android.db.City;
import com.weathertodcx.android.db.Country;
import com.weathertodcx.android.db.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择地理分布
 * Created by Administrator on 2017/4/19.
 */

public class ChooseAreaFragment extends Fragment {

    public static final int GEOLEVEL_PROVINCE = 1; // 地理级别:省
    public static final int GEOLEVEL_CITY = 2; // 地理级别:市
    public static final int GEOLEVEL_CCOUNTRY = 3; // 地理级别:县
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    /**
     * 省列表
     */
    private List<Province> provinceList;
    /**
     * 市列表
     */
    private List<City> cityList;
    /**
     * 县列表
     */
    private List<Country> countryList;
    /**
     * 选中的省份
     */
    private Province selectedProvince;
    /**
     * 选中的市
     */
    private City selectedCity;
    /**
     * 选中的县
     */
    private Country selectedCountry;
    /**
     * 当前选中的级别
     */
    private int currentGeoLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area)
    }
}

package com.weathertodcx.android.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.weathertodcx.android.R;
import com.weathertodcx.android.WeatherActivity;
import com.weathertodcx.android.db.City;
import com.weathertodcx.android.db.County;
import com.weathertodcx.android.db.Province;
import com.weathertodcx.android.gson.GsonCity;
import com.weathertodcx.android.gson.GsonCounty;
import com.weathertodcx.android.gson.GsonProvice;
import com.weathertodcx.android.util.HttpUtil;
import com.weathertodcx.android.util.ParseGeoDistributionUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 选择地理分布
 * Created by Administrator on 2017/4/19.
 */

public class ChooseAreaFragment extends Fragment {

    public static final int GEOLEVEL_PROVINCE = 1; // 地理级别:省
    public static final int GEOLEVEL_CITY = 2; // 地理级别:市
    public static final int GEOLEVEL_COUNTY = 3; // 地理级别:县
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
    private List<County> countyList;
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
    private County selectedCounty;
    /**
     * 当前选中的级别
     */
    private int currentGeoLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentGeoLevel == GEOLEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentGeoLevel == GEOLEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                } else if(currentGeoLevel == GEOLEVEL_COUNTY) { // 当点击到县时，跳转到天气界面
                    String weatherId = countyList.get(position).getWeatherId();
                    Intent intent = new Intent(getActivity(), WeatherActivity.class);
                    intent.putExtra("weather_id", weatherId);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentGeoLevel == GEOLEVEL_COUNTY)
                    queryCities();
                else if (currentGeoLevel == GEOLEVEL_CITY)
                    queryProvinces();
            }
        });
        queryProvinces();
    }

    /**
     * 查询所有省,从数据库查询,如果没有数据则去服务器查询并初始化数据库
     */
    private void queryProvinces() {
        // 更新UI
        titleText.setText("dcx'");
        backButton.setVisibility(View.GONE); // 隐藏返回按钮
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() == 0) { // 从服务器获取
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, GEOLEVEL_PROVINCE);
        }
        if (provinceList.size() > 0) { // 更新界面UI
            dataList.clear();
            for (Province province : provinceList)
                dataList.add(province.getProviceName());
            adapter.notifyDataSetChanged();
            listView.setSelection(0); // 默认选中城市第一条数据
            currentGeoLevel = GEOLEVEL_PROVINCE;
        }
    }

    /**
     * 查询所有市,从数据库查询,如果没有数据则去服务器查询并初始化数据库
     */
    private void queryCities() {
        // 更新UI
        titleText.setText(selectedProvince.getProviceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() == 0) { // 从服务器获取
            int provinceCode = selectedProvince.getProviceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, GEOLEVEL_CITY);
        }
        if (cityList.size() > 0) { // 更新界面UI
            dataList.clear();
            for (City city : cityList)
                dataList.add(city.getCityName());
            adapter.notifyDataSetChanged();
            listView.setSelection(0); // 默认选中城市第一条数据
            currentGeoLevel = GEOLEVEL_CITY;
        }
    }

    /**
     * 查询所有县,从数据库查询,如果没有数据则去服务器查询并初始化数据库
     */
    private void queryCounties() {
        // 更新UI
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() == 0) { // 从服务器获取
            int provinceCode = selectedProvince.getProviceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, GEOLEVEL_COUNTY);
        }
        if (countyList.size() > 0) { // 更新界面UI
            dataList.clear();
            for (County county : countyList)
                dataList.add(county.getCountyName());
            adapter.notifyDataSetChanged();
            listView.setSelection(0); // 默认选中城市第一条数据
            currentGeoLevel = GEOLEVEL_COUNTY;
        }
    }

    /**
     * 根据服务地址及类型获取服务器中省市县数据，并保存至数据库
     *
     * @param address 请求服务器接口url
     * @param geoleve 当前所选择省-市-县标志:1省;2市;3县
     */
    private void queryFromServer(String address, final int geoleve) {
        showProgressDialog(); // 显示进度条
        HttpUtil.sendOkHttpRequest(address, new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int param = 0;
                String responseText = response.body().string();
                boolean result;
                ParseGeoDistributionUtil distributionUtil = null;
                if (geoleve == GEOLEVEL_PROVINCE) {
                    distributionUtil = new ParseGeoDistributionUtil(GsonProvice.class);
                } else if (geoleve == GEOLEVEL_CITY) {
                    distributionUtil = new ParseGeoDistributionUtil(GsonCity.class);
                    param = selectedProvince.getId();
                } else if (geoleve == GEOLEVEL_COUNTY) {
                    distributionUtil = new ParseGeoDistributionUtil(GsonCounty.class);
                    param = selectedCity.getId();
                }
                if (distributionUtil != null) { // 获取Db列表数据，并返回主线程更新UI
                    result = distributionUtil.handleResponse(responseText, param);
                    if(result) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeProgressDialog(); // 关闭进度条
                                if (geoleve == GEOLEVEL_PROVINCE) {
                                    queryProvinces();
                                } else if (geoleve == GEOLEVEL_CITY) {
                                    queryCities();
                                } else if (geoleve == GEOLEVEL_COUNTY) {
                                    queryCounties();
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                // 通过runOnUiThread返回主线程进行UI更新
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog(); // 关闭进度条
                        Toast.makeText(getContext(), "(⊙o⊙)哦,获取位置基本信息失败了", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 显示进度条
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度条
     */
    private void closeProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }
}

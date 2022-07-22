package com.hgc.coolweather;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hgc.coolweather.annotation.LoginFilter;
import com.hgc.coolweather.db.City;
import com.hgc.coolweather.db.County;
import com.hgc.coolweather.db.Province;
import com.hgc.coolweather.entity.Source;
import com.hgc.coolweather.util.HttpUtil;
import com.hgc.coolweather.util.SharePreferenceUtil;
import com.hgc.coolweather.util.Utility;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @Description 遍历省市县的碎片
 * @Author hanguangchuan
 * Date 2022/6/5 12:25
 */
public class ChooseAreaFragment extends Fragment {

    public static final int LEVEL_PROVINCE = 0;

    public static final int LEVEL_CITY = 1;

    public static final int LEVEL_COUNTY = 2;

    public String host;

    private ProgressDialog progressDialog;

    private TextView titleText;

    private Button backButton;

    private Button titleLogout;

//    private ListView listView;
    private RecyclerView recyclerView;

    private CustomAdapter adapter;

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
     * 选中的城市
     */
    private City selectedCity;

    /**
     * "当前"选中的级别
     */
    private int currentLevel;

    private String setWeatherIdUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choose_area, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleText = view.findViewById(R.id.title_text);
        backButton = view.findViewById(R.id.back_button);
        titleLogout = view.findViewById(R.id.title_logout);
        host = getString(R.string.self_remote_pro_host);
        setWeatherIdUrl = getString(R.string.setWeatherIdUrl);

        // 使用 listView(deprecated)
//        listView = view.findViewById(R.id.list_view);
//        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(((parent, view2, position, id) -> {
//            if (currentLevel == LEVEL_PROVINCE) {
//                selectedProvince = provinceList.get(position);
//                queryCities();
//            } else if (currentLevel == LEVEL_CITY) {
//                selectedCity = cityList.get(position);
//                queryCounties();
//            } else if (currentLevel == LEVEL_COUNTY) {
//                String weatherId = countyList.get(position).getWeatherId();
//
//                /*
//                判断是通过MainActivity加载的fragment
//                还是WeatherActivity加载的
//                 */
//                if (getActivity() instanceof MainActivity) {
//                    Intent intent = new Intent(getActivity(), WeatherActivity.class);
//                    intent.putExtra("weather_id", weatherId);
//                    startActivity(intent);
//                    getActivity().finish();
//                } else if (getActivity() instanceof WeatherActivity) {
//                    WeatherActivity weatherActivity = (WeatherActivity) getActivity();
//                    weatherActivity.drawerLayout.closeDrawers();
//                    weatherActivity.requestWeather(weatherId, false);
//                }
//            }
//        }));

        // 使用RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new CustomAdapter(dataList, new CustomAdapter.OnItemClickListener() {
            @LoginFilter(skip = MainActivity.class)
            @Override
            public void onClick(int position) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    String weatherId = countyList.get(position).getWeatherId();

                    // TODO: 2022/7/19 weather_id和用户账号保存到后台服务器
                    Source source = new Source();
                    source.setWeatherId(weatherId);
                    Gson gson = new Gson();
                    HttpUtil.sendPostHeadOkhttpRequest(setWeatherIdUrl, gson.toJson(source), SharePreferenceUtil.getToken(SharePreferenceUtil.TOKEN, getContext()), new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            throw new RuntimeException("远程访问异常");
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        }
                    });

                    /*
                    判断是通过MainActivity加载的fragment
                    还是WeatherActivity（侧面滑动）加载的
                    如果是侧面滑动加载的可以不用再重复创建视图了
                     */
                    if (getActivity() instanceof MainActivity) {
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (getActivity() instanceof WeatherActivity) {
                        WeatherActivity weatherActivity = (WeatherActivity) getActivity();
                        weatherActivity.drawerLayout.closeDrawers();
                        weatherActivity.requestWeather(weatherId, false);
                    }
                }
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        backButton.setOnClickListener((v) -> {
            if (currentLevel == LEVEL_COUNTY) {
                queryCities();
            } else if (currentLevel == LEVEL_CITY) {
                queryProvinces();
            }
        });

        titleLogout.setOnClickListener((v) -> {
            SharePreferenceUtil.clearSharePref(SharePreferenceUtil.IS_LOGIN, getContext());
            Toast.makeText(getContext(), "退出登录", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            startActivity(new Intent(getContext(), LoginActivity.class));
        });

        queryProvinces();
    }

    /**
     * 查询全国所有的省，优先从数据库中查询，如果没有查询到再去服务器上查询
     */


    private void queryProvinces() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList = LitePal.findAll(Province.class);
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province: provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
//            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
//            String address = "http://guolin.tech/api/china";
            /*
            修改为自己的接口
             */
            String address = host + "api/china";
            queryFromService(address, "province");
        }
    }

    /**
     * 查询选中省内的所有的市，优先从数据库查询，如果没有查询到再去服务器上查询
     */

    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = LitePal.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city: cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
//            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = host + "api/china/" + provinceCode;
            queryFromService(address, "city");
        }
    }


    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = LitePal.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county: countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
//            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        }else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = host + "api/china/" + provinceCode + "/" + cityCode;
            queryFromService(address, "county");
        }
    }

    /**
     * 根据传入的地址和类型从服务器上查询省市县数据
     * @param address
     * @param type
     */
    private void queryFromService(String address, final String type) {
        showProgressDialog();

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 通过runOnUiThread() 方法回到主线程处理逻辑
                // getActivity() requireActivity()
                requireActivity().runOnUiThread(() -> {
                    closeProgressDialog();
                    e.printStackTrace();
                    Toast.makeText(getContext(), "加载失败", Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;

                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(responseText);
                } else if ("city".equals(type)) {
                    result = Utility.handleCityResponse(responseText, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountyResponse(responseText, selectedCity.getId());
                }

                if (result) {
                    requireActivity().runOnUiThread(() -> {
                        closeProgressDialog();
                        if ("province".equals(type)) {
                            queryProvinces();
                        } else if ("city".equals(type)) {
                            queryCities();
                        } else {
                            queryCounties();
                        }
                    });
                }
            }
        });
    }

    /**
     * 显示进度对话框
     */

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}

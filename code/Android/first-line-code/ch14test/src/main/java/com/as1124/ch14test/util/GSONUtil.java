package com.as1124.ch14test.util;

import android.text.TextUtils;

import com.as1124.ch14test.CH14Application;
import com.as1124.ch14test.db.City;
import com.as1124.ch14test.db.Country;
import com.as1124.ch14test.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GSONUtil {

    public static boolean handleProvinceResponse(String provinceData) {
        if (TextUtils.isEmpty(provinceData)) {
            return false;
        } else {
            try {
                JSONArray all = new JSONArray(provinceData);
                int size = all.length();
                for (int i = 0; i < size; i++) {
                    JSONObject object = all.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(object.getString("name"));
                    province.setProvinceCode(object.getInt("id"));
                    CH14Application.getDaoSession().getProvinceDao().insert(province);
                }
                return true;
            } catch (JSONException e) {
                android.util.Log.e("GSONUtil", e.getMessage(), e);
                return false;
            }
        }
    }

    public static boolean handleCityResponse(String cityData, Long provinceId) {
        if (TextUtils.isEmpty(cityData)) {
            return false;
        } else {
            try {
                JSONArray all = new JSONArray(cityData);
                int size = all.length();
                for (int i = 0; i < size; i++) {
                    JSONObject object = all.getJSONObject(i);
                    City city = new City();
                    city.setCityName(object.getString("name"));
                    city.setCityCode(object.getInt("id"));
                    city.setProvinceId(provinceId);
                    CH14Application.getDaoSession().getCityDao().insert(city);
                }
                return true;
            } catch (JSONException e) {
                android.util.Log.e("GSONUtil", e.getMessage(), e);
                return false;
            }
        }
    }

    public static boolean handleCountryResponse(String countryData, Long cityId) {
        if (TextUtils.isEmpty(countryData)) {
            return false;
        } else {
            try {
                JSONArray all = new JSONArray(countryData);
                int size = all.length();
                for (int i = 0; i < size; i++) {
                    JSONObject object = all.getJSONObject(i);
                    Country country = new Country();
                    country.setCountryName(object.getString("name"));
                    country.setWeatherId(object.getString("weather_id"));
                    country.setCityId(cityId);
                    CH14Application.getDaoSession().getCountryDao().insert(country);
                }
                return true;
            } catch (JSONException e) {
                android.util.Log.e("GSONUtil", e.getMessage(), e);
                return false;
            }
        }
    }
}

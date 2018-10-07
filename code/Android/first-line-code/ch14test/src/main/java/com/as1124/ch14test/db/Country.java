package com.as1124.ch14test.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;


/**
 * 县信息
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
@Entity(nameInDb = "country")
public class Country {

    @Id
    private Long id;

    @Property(nameInDb = "name")
    @NotNull
    private String countryName;

    @Property(nameInDb = "weather_id")
    @NotNull
    private String weatherId;

    @Property(nameInDb = "city_id")
    private Long cityId;

    @Generated(hash = 1741602306)
    public Country(Long id, @NotNull String countryName, @NotNull String weatherId,
            Long cityId) {
        this.id = id;
        this.countryName = countryName;
        this.weatherId = weatherId;
        this.cityId = cityId;
    }

    @Generated(hash = 668024697)
    public Country() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}

package com.epam.consumer.entity;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

/**
 * @author Li Ming
 * @date 2021.2021/11/8 08:07
 */


@Data
@ToString
public class Area implements Serializable {
    static final long serialVersionUID = 42;
    private String code;
    private String name;
    private Area pre, next;

    public Area(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Getter
    @Setter
    @ToString
    public static class Province extends Area {
        /**
         * http://www.weather.com.cn/data/city3jdata/china.html
         * {"10101":"北京","10102":"上海","10103":"天津","10104":"重庆","10105":"黑龙江","10106":"吉林","10107":"辽宁",
         * "10108":"内蒙古","10109":"河北","10110":"山西","10111":"陕西","10112":"山东","10113":"新疆","10114":"西藏",
         * "10115":"青海","10116":"甘肃","10117":"宁夏","10118":"河南","10119":"江苏","10120":"湖北","10121":"浙江",
         * "10122":"安徽","10123":"福建","10124":"江西","10125":"湖南","10126":"贵州","10127":"四川","10128":"广东",
         * "10129":"云南","10130":"广西","10131":"海南","10132":"香港","10133":"澳门","10134":"台湾"}
         */
        private Map<String, String> cities;//name=code

        public Province(String code, String name) {
            super(code, name);
        }

        public void setCities(Map<String, String> cities) {
            Optional.ofNullable(cities).ifPresent(cs -> {
                this.cities = Maps.newHashMap();
                cs.forEach((k, v) -> {
                    this.cities.put(v, k);
                });
            });
        }

    }

    @Getter
    @Setter
    @ToString
    public static class City extends Area {
        /**
         * http://www.weather.com.cn/data/city3jdata/provshi/10119.html
         * {"01":"南京","02":"无锡","03":"镇江","04":"苏州","05":"南通","06":"扬州",
         * "07":"盐城","08":"徐州","09":"淮安","10":"连云港","11":"常州","12":"泰州","13":"宿迁"}
         */

        private Province province;

        private Map<String, String> counties;

        public City(String code, String name) {
            super(code, name);
        }

        public void setCounties(Map<String, String> counties) {
            Optional.ofNullable(counties).ifPresent(cs -> {
                this.counties = Maps.newHashMap();
                cs.forEach((k, v) -> {
                    this.counties.put(v, k);
                });
            });
        }
    }

    @Getter
    @Setter
    @ToString
    public static class County extends Area {
        /**
         * http://www.weather.com.cn/data/city3jdata/station/1011904.html
         * {"01":"苏州","02":"常熟","03":"张家港","04":"昆山","05":"吴中","07":"吴江","08":"太仓"}
         */
        private Province province;
        private City city;

        public County(String code, String name) {
            super(code, name);
        }
    }
}

package fr.erpriex.wakeproxy.core.ip;

import lombok.Getter;

public class IpApiResponse {

    @Getter private String status;
    @Getter private String country;
    @Getter private String countryCode;
    @Getter private String region;
    @Getter private String regionName;
    @Getter private String city;
    @Getter private String zip;
    @Getter private double lat;
    @Getter private double lon;
    @Getter private String timezone;
    @Getter private String isp;
    @Getter private String org;
    @Getter private String query;
}

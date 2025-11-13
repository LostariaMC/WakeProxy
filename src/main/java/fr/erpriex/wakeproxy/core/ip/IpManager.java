package fr.erpriex.wakeproxy.core.ip;

import fr.erpriex.wakeproxy.core.api.ApiException;

import java.util.HashMap;
import java.util.Map;

public class IpManager {

    private IpApiClient ipApiClient;

    private Map<String, Boolean> ipAuthorized = new HashMap<>();

    public IpManager() {
        this.ipApiClient = new IpApiClient();
    }

    public boolean checkIp(String ip) throws ApiException {
        if(ipAuthorized.containsKey(ip)) {
            return ipAuthorized.get(ip);
        };
        IpApiResponse response = ipApiClient.getIpInfo(ip);
        if(response.getCountryCode().equals("FR") || response.getCountryCode().equals("BE")) {
            ipAuthorized.put(ip, true);
            return true;
        } else {
            ipAuthorized.put(ip, false);
            return false;
        }
    }

}

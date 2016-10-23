package ru.buharov.fhelp.mmvb.service;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.buharov.fhelp.resources.JsonResourceService;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class MmvbServiceImpl implements MmvbService {

    private static final String MMVB_RATES_URL = "http://moex.com/iss/statistics/engines/currency/markets/selt/rates.json";

    private Logger log = Logger.getLogger(MmvbServiceImpl.class);

    @Autowired
    private JsonResourceService jsonResourceService;

    public Map<String, Double> getCurrentCbrfRates() {
        try {
            JSONObject jsonObject = jsonResourceService.getJsonFromUrl(MMVB_RATES_URL);
            return parseRatesJson(jsonObject);
        } catch (IOException |JSONException e) {
            log.error(e.getMessage(), e);
            return new HashMap<>();
        }
    }

    private Map<String, Double> parseRatesJson(JSONObject jsonObject) {
        Map<String, Double> rates = new HashMap<>();
        JSONObject cbrf = jsonObject.getJSONObject("cbrf");
        JSONArray columns = cbrf.getJSONArray("columns");
        JSONArray data = cbrf.getJSONArray("data").getJSONArray(0);
        for (int i = 0; i < columns.length(); ++i){
            String col = columns.getString(i);
            switch (col) {
                case "CBRF_USD_LAST":
                    rates.put(USD, data.getDouble(i));
                    break;
                case "CBRF_EUR_LAST":
                    rates.put(EUR, data.getDouble(i));
                    break;
            }
        }

        return rates;
    }
}

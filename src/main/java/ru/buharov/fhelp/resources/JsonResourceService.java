package ru.buharov.fhelp.resources;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.buharov.fhelp.mmvb.service.MmvbServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

@Service
public class JsonResourceService {

    private Logger log = Logger.getLogger(MmvbServiceImpl.class);

    public JSONObject getJsonFromUrl(String url) throws IOException {
        try (InputStream is = new URL(url).openStream();) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readString(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private String readString(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}

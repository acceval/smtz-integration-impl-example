package com.acceval.core.service;

import com.acceval.core.filehandler.StorageException;
import com.acceval.core.filehandler.StorageFileNotFoundException;
import com.acceval.core.filehandler.StorageProperties;
import com.acceval.core.microservice.model.LabelValue;
import com.acceval.core.model.Timezone;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

@Service
public class TimezoneServiceImpl implements TimezoneService {

    private final ObjectMapper mapper = new ObjectMapper();

    private List<Timezone> getTimezoneDataSourceFromJSON()  throws Exception{

        List<Timezone> timezones = new ArrayList<>();

        InputStream fileInStream = getResource("/masterdata/timezones.json");
        ObjectReader stringListReader = mapper.readerFor(new TypeReference<List<Timezone>>() {});
        timezones = stringListReader.readValue(fileInStream);

        return timezones;
    }

    @Override
    public List<Timezone> getTimezones() {

        List<Timezone> timezones = new ArrayList<>();
        try {
            timezones = getTimezoneDataSourceFromJSON();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return timezones;
    }

    public List<LabelValue> getTimezoneDataSource() {

        List<LabelValue> items = new ArrayList<>();
        try {
            List<Timezone> timezones = getTimezones();
            for (Iterator<Timezone> itr = timezones.iterator(); itr.hasNext(); ) {
                Timezone timezone = itr.next();
                String id = timezone.getAbbr();
                String label = timezone.getText();
                items.add(new LabelValue(label, label));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    @Override
    public Timezone getTimezone(String timezoneid) {
        Timezone timezone = null;

        List<Timezone> timezones = getTimezones();
        if (timezones != null) {
            timezone = timezones.stream().filter(x -> x.getAbbr().equalsIgnoreCase(timezoneid)
                    || x.getText().equalsIgnoreCase(timezoneid)).findFirst().orElse(null);

            if (timezone != null) {
                TimeZone timeZone = null;

                List<String> utcTimeZoneList = timezone.getUtc();
                for (int i = 0; i < utcTimeZoneList.size(); i++) {
                    String stimeZone = utcTimeZoneList.get(i);
                    timeZone = TimeZone.getTimeZone(stimeZone);
                    if (timeZone != null) {
                        timezone.setUtcId(stimeZone);
                        break;
                    }
                }

                //backward compatibility with jdk8 utc timezoneid
                if (timeZone == null) timezone.setUtcId(timezoneid);
            }
        }

        return timezone;
    }

    @Override
    public String convertToUTCTimeZoneId(String timezoneid) {
        String stzutcid = null;

        Timezone timezone = getTimezone(timezoneid);
        if (timezone != null) stzutcid = timezone.getUtcId();

        return stzutcid;
    }

    @Override
    public TimeZone convertToUTCTimeZone(String timezoneid) {
        return TimeZone.getTimeZone(convertToUTCTimeZoneId(timezoneid));
    }

    private InputStream getResource(String resourcePath) {
        return this.getClass().getResourceAsStream(resourcePath);
    }
}

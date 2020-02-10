package com.acceval.core.service;

import com.acceval.core.microservice.model.LabelValue;
import com.acceval.core.model.Timezone;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

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

        //backward compatibilty
        if (stzutcid == null) stzutcid = timezoneid;

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

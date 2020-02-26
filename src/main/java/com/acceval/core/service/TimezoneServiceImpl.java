package com.acceval.core.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.acceval.core.microservice.model.LabelValue;
import com.acceval.core.model.Timezone;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.jknack.handlebars.internal.lang3.StringUtils;

/**
 * <h1>Simplified TimeZones List!</h1>
 * The TimezoneServiceImpl program implements to load simplified
 * list of timezones from timezones.json files instead of using
 * JDK's built timezone list. It also has functions to convert
 * custom timezone to JDK timezone
 * <p>
 * <b>eg.</b> (UTC+08:00) Kuala Lumpur, Singapore  => Asia/Kuala_Lumpur
 *
 * @author  Raja
 * @version 1.0
 * @since   2020-02-12
 */
@Service
public class TimezoneServiceImpl implements TimezoneService {
    private static Logger logger = LoggerFactory.getLogger(TimezoneServiceImpl.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private static List<Timezone> timezonesds = new ArrayList<>();

    private List<Timezone> getTimezoneDataSourceFromJSON()  throws Exception{

        //one time loading of timezones.json file
        if (timezonesds == null || timezonesds.size() == 0) {
            timezonesds = new ArrayList<>();

            logger.debug("loading timezone data from timezones.json...");
            InputStream fileInStream = getResource("/masterdata/timezones.json");
            ObjectReader stringListReader = mapper.readerFor(new TypeReference<List<Timezone>>() {});
            timezonesds = stringListReader.readValue(fileInStream);
        }

        return timezonesds;
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
    public Timezone getTimezone(String utcTimeZoneId) {
        Timezone timezone = null;

        List<Timezone> timezones = getTimezones();
        if (timezones != null) {
			timezone = timezones.stream().filter(x -> x.getText().equalsIgnoreCase(utcTimeZoneId)).findFirst()
					.orElse(null);

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
				if (timeZone == null || StringUtils.isBlank(timezone.getUtcId()))
					timezone.setUtcId(utcTimeZoneId);
            }
        }

        return timezone;
    }

    @Override
    public String convertToUTCTimeZoneId(String customTimezone) {
        String stzutcid = null;

        Timezone timezone = getTimezone(customTimezone);
        if (timezone != null) stzutcid = timezone.getUtcId();

        //backward compatibilty
        if (stzutcid == null) stzutcid = customTimezone;

        return stzutcid;
    }

    @Override
    public TimeZone convertToUTCTimeZone(String customTimezone) {
        return TimeZone.getTimeZone(convertToUTCTimeZoneId(customTimezone));
    }

    private InputStream getResource(String resourcePath) {
        return this.getClass().getResourceAsStream(resourcePath);
    }
}

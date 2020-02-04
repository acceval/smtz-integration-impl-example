package com.acceval.core.service;

import com.acceval.core.microservice.model.LabelValue;
import com.acceval.core.model.Timezone;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Stream;

public interface TimezoneService {

    List<LabelValue> getTimezoneDataSource();

    List<Timezone> getTimezones();

    Timezone getTimezone(String timezoneid);

    String convertToUTCTimeZoneId(String timezoneid);

    TimeZone convertToUTCTimeZone(String timezoneid);
}

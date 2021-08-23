package com.acceval.core.service;

import java.util.List;
import java.util.TimeZone;

import com.acceval.core.microservice.model.LabelValue;
import com.acceval.core.model.Timezone;

public interface TimezoneService {

    List<LabelValue> getTimezoneDataSource();

    List<Timezone> getTimezones();

    Timezone getTimezone(String timezoneid);

    String convertToUTCTimeZoneId(String timezoneid);

    TimeZone convertToUTCTimeZone(String timezoneid);
}

package com.acceval.core.cache.processor;

import com.acceval.core.cache.impl.ConditionRecordCache;
import com.acceval.core.cache.model.ConditionRecord;
import com.acceval.core.cache.model.ConditionRecordConfig;
import com.acceval.core.microservice.model.LabelValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(name = "microservice.cache", havingValue = "true")
public class ConditionRecordRetriever {

    @Autowired
    private ConditionRecordCache conditionRecordCache;

    public ConditionRecord singleResultConditionRecord(ConditionRecordConfig config,
                                                MultiValueMap<String, String> mapParam) {
        return null;
    }
}

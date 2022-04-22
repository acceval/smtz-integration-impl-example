package com.acceval.core.pricing;

import java.util.List;

import com.acceval.core.pricing.so.BaseVCMapping;


public interface DefaultContextMapping {

	List<BaseVCMapping> getDefaultContextMapping(String... param);

}

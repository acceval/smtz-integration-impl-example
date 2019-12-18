package com.acceval.core.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acceval.core.repository.BaseRepository;
import com.acceval.core.repository.Criteria;
import com.acceval.core.repository.Criterion;
import com.acceval.core.repository.QueryResult;
import com.acceval.core.util.BaseBeanUtil;

@RestController
@RequestMapping("common")
public class GenericCommonController {
	public static final String KEY_ENTITY_CLASS = "ENTITY_CLASS";
	public static final String KEY_IS_COLLECTION = "IS_COLLECTION";

	@RequestMapping(method = RequestMethod.GET, value = "query")
	public Object query(@RequestParam MultiValueMap<String, String> mapParam) {

		String entityClass = mapParam.getFirst(KEY_ENTITY_CLASS);

		// generic query by Entity Class
		Criteria criteria = new Criteria();
		for (String key : mapParam.keySet()) {
			if (KEY_ENTITY_CLASS.equals(key) || KEY_IS_COLLECTION.equals(key)) continue;

			criteria.appendCriterion(new Criterion(key, mapParam.get(key).toArray()));
		}

		Object obj = null;
		boolean isCollection = BooleanUtils.toBoolean(mapParam.getFirst(KEY_IS_COLLECTION));
		BaseRepository baseRepo = BaseBeanUtil.getRepositoryBaseOnEntityName(entityClass);
		if (baseRepo != null) {
			QueryResult qResult = baseRepo.queryByCriteria(criteria);
			if (qResult != null && !CollectionUtils.isEmpty(qResult.getResults())) {
				if (isCollection) {
					obj = qResult.getResults();
				} else {
					obj = qResult.getResults().iterator().next();
				}
			}
		}

		return obj;
	}
}

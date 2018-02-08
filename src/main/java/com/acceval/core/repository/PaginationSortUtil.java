package com.acceval.core.repository;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import com.acceval.core.MicroServiceUtilException;
import com.acceval.core.microservice.MicroServiceRequest;
import com.acceval.core.microservice.MicroServiceUtil;

public class PaginationSortUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaginationSortUtil.class);

	public static List<?> query(PaginationSortRequest paginationSortUtilRequest) throws MicroServiceUtilException {

		/** init */
		paginationSortUtilRequest.assertNull(PaginationSortUtil.class);
		DiscoveryClient discoveryClient=paginationSortUtilRequest.getDiscoveryClient(); 
		RestTemplate restTemplate=paginationSortUtilRequest.getRestTemplate();
		EntityManager entityManager = paginationSortUtilRequest.getEntityManager();
		Class<?> targetClass = paginationSortUtilRequest.getTargetClass();
		int pageNumber = paginationSortUtilRequest.getPageNumber();
		int pageSize = paginationSortUtilRequest.getPageSize();
		String orderTarget = paginationSortUtilRequest.getOrderTarget();
		String orderParam = paginationSortUtilRequest.getOrderParam();
		boolean isAsc = paginationSortUtilRequest.isAsc();
		String whereStatement = paginationSortUtilRequest.getWhereStatement();

		String processedWhereStatement = StringUtils.isNotBlank(whereStatement) ? " where " + whereStatement : "";

		/** 1. get distinct of IDs */
		Query queryDistinct =
				entityManager.createQuery("select distinct(" + orderTarget + ") from " + targetClass.getName() + processedWhereStatement);
		List<?> lstDistincted = queryDistinct.getResultList();

		/** 2. fire distinct IDs to child MS and get sorted indexes */
		String params = StringUtils.join(lstDistincted, ",");
		MicroServiceRequest msRequest = null;
		Field field = ReflectionUtils.findField(targetClass, orderTarget);
		if (field.isAnnotationPresent(MicroServiceField.class)) {
			Annotation objFieldAnno = field.getAnnotation(MicroServiceField.class);
			MicroServiceField fieldAnno = (MicroServiceField) objFieldAnno;

			/** validation */
			String msService = fieldAnno.serviceID();
			if (StringUtils.isBlank(msService)) {
				throw new MicroServiceUtilException(PaginationSortUtil.class,
						"MS Application Name not define in POJO for [" + field.getName() + "] field!");
			}
			String msFunction = fieldAnno.sortFunction();
			if (StringUtils.isBlank(msFunction)) {
				throw new MicroServiceUtilException(PaginationSortUtil.class,
						"MS Sort Service is not define in POJO for [" + field.getName() + "] field!");
			}

			msRequest =
					new MicroServiceRequest(discoveryClient, restTemplate, msService, msFunction, orderParam + "/" + isAsc + "/" + params);
		} else {
			throw new MicroServiceUtilException(PaginationSortUtil.class,
					"Field [" + orderTarget + "] in [" + targetClass.getName() + "] is not support for sorting service.");
		}
		msRequest.assertNull();
		List<?> orderedIDs = (List<?>) MicroServiceUtil.getForObject(msRequest, List.class);

		/** 3. apply order to the query */
		int position = 0;
		StringBuilder sbOrder = new StringBuilder();
		sbOrder.append(" order by (case " + orderTarget);
		for (Object objID : orderedIDs) {
			sbOrder.append(" when " + String.valueOf(objID) + " then " + ++position);
		}
		sbOrder.append(" else 9999 end)");

		/** 4. fire query */
		String sql = "from " + targetClass.getName() + processedWhereStatement + sbOrder.toString();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SQL for sorting: " + sql);
		}
		Query query = entityManager.createQuery(sql, targetClass);
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);

		List<?> results = query.getResultList();
		return results;
	}
}

package com.acceval.core.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.acceval.core.MicroServiceUtilException;
import com.acceval.core.repository.BaseRepository;

@Service
public class BaseBeanUtil implements ApplicationContextAware {
	private static Logger Logger = LoggerFactory.getLogger(BaseBeanUtil.class);

	private static ApplicationContext context;
	private static Map<String, Object> mapSpeialBean;
	private static Repositories repositories;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
		mapSpeialBean = new HashMap<>();
		repositories = new Repositories(applicationContext);
	}

	public static <T> T getBean(Class<T> beanClass) {
		if (RestTemplate.class.getName().equals(beanClass.getName())) {
			if (mapSpeialBean.get(RestTemplate.class.getName()) != null) {
				return (T) mapSpeialBean.get(RestTemplate.class.getName());
			} else {
				RestTemplateBuilder builder = getBean(RestTemplateBuilder.class);
				RestTemplate restTemplate = builder.build();
				mapSpeialBean.put(RestTemplate.class.getName(), restTemplate);

				return (T) restTemplate;
			}
		}
		return context.getBean(beanClass);
	}

	public static BaseRepository<?> getRepositoryBaseOnEntityName(String entityName) {
		try {
			Object objRepo = repositories.getRepositoryFor(ClassUtil.getClass(entityName)).orElse(null);
			if (objRepo instanceof BaseRepository) {
				return (BaseRepository) objRepo;
			}
			return null; // BaseMongoRepo not support yet
		} catch (MicroServiceUtilException e) {
			Logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static <T, ID extends Serializable> Repository<T, ID> getSpringRepositoryBaseOnEntityName(String entityName) {
		try {
			return (Repository<T, ID>) repositories.getRepositoryFor(ClassUtil.getClass(entityName)).orElse(null);
		} catch (MicroServiceUtilException e) {
			Logger.error(e.getMessage(), e);
		}
		return null;
	}
}

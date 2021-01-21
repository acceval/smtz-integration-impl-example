package com.acceval.core.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.acceval.core.repository.MicroServiceObjectUtil;

//@Service
public class BaseEntityListener {

	private static final Logger logger = LoggerFactory.getLogger(MicroServiceObjectUtil.class);
	@Autowired
	MicroServiceObjectUtil microServiceObjectUtil;

//	@PrePersist
//	public void onPrePersist(BasePOJO basePojo) {
//		basePojo.setDateCreated(LocalDateTime.now());
//	}
//
//	@PreUpdate
//	public void onPreUpdate(BasePOJO basePojo) {
//		basePojo.setDateModified(LocalDateTime.now());
//	}

	//	@PostLoad
	public void onPostLoad(BaseEntity basePOJO) {
		try {
			microServiceObjectUtil.refreshObjectDependency(basePOJO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}

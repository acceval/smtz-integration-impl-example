package com.acceval.core.repository;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import com.acceval.core.model.BaseModel;
import com.acceval.core.model.OwnerModel;
import com.acceval.core.model.company.BaseCompanyModel;
import com.acceval.core.security.PrincipalUtil;

@Component
public class CommonMongoEventListener extends AbstractMongoEventListener<Object> {

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Object> event) {
		Object source = event.getSource();
		if (source instanceof OwnerModel) {
			OwnerModel ownerModel = (OwnerModel) source;
			if (ownerModel.getRecordOwner() == null) {
				ownerModel.setRecordOwner(PrincipalUtil.getUsername());
			}
		}

		if (source instanceof BaseCompanyModel) {
			BaseCompanyModel baseCompanyModel = (BaseCompanyModel) source;
			//			if (baseModel.getCompanyId() == null) {
				baseCompanyModel.setCompanyId(PrincipalUtil.getCompanyID());
			//			}
		}
	}

	@Override
	public void onBeforeSave(BeforeSaveEvent<Object> event) {
		Object source = event.getSource();
		if (source instanceof OwnerModel) {
			OwnerModel ownerModel = (OwnerModel) source;
			if (ownerModel.getRecordOwner() == null) {
				ownerModel.setRecordOwner(PrincipalUtil.getUsername());
			}
		}

		if (source instanceof BaseCompanyModel) {
			BaseCompanyModel baseCompanyModel = (BaseCompanyModel) source;
			//			if (baseModel.getCompanyId() == null) {
				baseCompanyModel.setCompanyId(PrincipalUtil.getCompanyID());
			//			}
		}
	}
}

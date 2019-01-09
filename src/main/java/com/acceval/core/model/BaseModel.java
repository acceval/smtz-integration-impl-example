package com.acceval.core.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.acceval.core.util.ClassUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, CompanyModelListener.class})
public abstract class BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@Column(nullable = false, updatable = false)
    private Long companyId;
    
	@JsonIgnore
	@CreatedBy
    @Column(nullable = false, updatable = false)	
    private String createdBy;

	@JsonIgnore
    @CreatedDate
    @Column(nullable = false, updatable = false)    
    private LocalDateTime created;

	@JsonIgnore
    @LastModifiedBy
    @Column(nullable = false)    
    private String modifiedBy;

	@JsonIgnore
    @LastModifiedDate
    @Column(nullable = false)    
    private LocalDateTime modified;

	public Object retrievePrimaryKey() {
		if (this.getClass().isAnnotationPresent(Entity.class)) {
			Class<?> classToFind = this.getClass();
			for (Field field : classToFind.getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.class)) {
					return ClassUtil.getPropertyValue(this, field.getName());
				}
			}
		}
		return null;
	}

	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public LocalDateTime getModified() {
		return modified;
	}
	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}	
        
}

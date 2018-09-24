package com.acceval.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, CompanyModelListener.class})
public abstract class BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(updatable = false)
    private Long companyId;
    
	@CreatedBy
    @Column(nullable = false, updatable = false, columnDefinition = "varchar(255) default 'anonymousUser'")	
    private String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "timestamp default now()")    
    private LocalDateTime created;

    @LastModifiedBy
    @Column(nullable = false, columnDefinition = "varchar(255) default 'anonymousUser'")    
    private String modifiedBy;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "timestamp default now()")    
    private LocalDateTime modified;

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

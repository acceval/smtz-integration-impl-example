package com.acceval.core.pricing.so;

import java.io.Serializable;
import java.util.List;

/**
 * refer to com.acceval.pricing.model.ModelResult
 */
public class ModelResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long modelID; // empty if is TechniqueResult
	private Integer modelVersion; // empty if is TechniqueResult

	private List<ModelCompResult> modelCompResults;

	public Long getModelID() {
		return modelID;
	}

	public void setModelID(Long modelID) {
		this.modelID = modelID;
	}

	public Integer getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(Integer modelVersion) {
		this.modelVersion = modelVersion;
	}

	public List<ModelCompResult> getModelCompResults() {
		return modelCompResults;
	}

	public void setModelCompResults(List<ModelCompResult> modelCompResults) {
		this.modelCompResults = modelCompResults;
	}

}

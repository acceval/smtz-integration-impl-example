package com.acceval.core.pricing.so;

import com.acceval.core.model.VariableContext;

public class ModelVariableContext extends VariableContext {
	private static final long serialVersionUID = 1L;

	private ModelResult modelResult;

	public ModelResult getModelResult() {
		return modelResult;
	}

	public void setModelResult(ModelResult modelResult) {
		this.modelResult = modelResult;
	}
}

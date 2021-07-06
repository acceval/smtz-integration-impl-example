package com.acceval.core.integration.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesContractStatusResponse {

	private boolean success;
	private String message = "";
	
	public List<Item> items = new ArrayList<>();

	private Map<String, List<Item>> contractMappedItems;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void setContractMappedItems(Map<String, List<Item>> contractMappedItems) {
		this.contractMappedItems = contractMappedItems;
	}

	public Map<String, List<Item>> getContractMappedItems() {
		return contractMappedItems;
	}

	public List<Item> getItemsByContractNumber(String contractNumber) {
		return contractMappedItems.get(contractNumber);
	}

	public static class Item {

		private double soQuantity;
		private double doQuantity;
		private double invQuantity;
		private String contractNumber;
		private String pmtItem;

		public void addSoQuantity(double qty) {
			this.soQuantity += qty;
		}
		
		public void addDoQuantity(double qty) {
			this.doQuantity += qty;
		}
		
		public void addInvQuantity(double qty) {
			this.invQuantity += qty;
		}
		
		
		public double getSoQuantity() {
			return soQuantity;
		}

		public void setSoQuantity(double soQuantity) {
			this.soQuantity = soQuantity;
		}

		public double getDoQuantity() {
			return doQuantity;
		}

		public void setDoQuantity(double doQuantity) {
			this.doQuantity = doQuantity;
		}

		public double getInvQuantity() {
			return invQuantity;
		}

		public void setInvQuantity(double invQuantity) {
			this.invQuantity = invQuantity;
		}

		public String getContractNumber() {
			return contractNumber;
		}

		public void setContractNumber(String contractNumber) {
			this.contractNumber = contractNumber;
		}

		public String getPmtItem() {
			return pmtItem;
		}

		public void setPmtItem(String pmtItem) {
			this.pmtItem = pmtItem;
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void appendMessage(String message) {
		this.message += message;
	}
}

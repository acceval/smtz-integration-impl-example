package com.acceval.core.integration.model;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

public class MasterDataCustomerInMessage implements Serializable {

	private String code; // Done
	private String name1; // Done
	private String name2;// Done
	private String name3;// Done
	private String name4;// Done
	private String shortName; // Done
	private String orderBlock; // Done
	private String deletionFlag; // Done
	private String accountGroup; // Done
	private String creationDate;// Done
	private String street1; // Done
	private String street2; // Done
	private String street3; // Done
	private String street4;// Done
	private String street5;// Done
	private String city; // Done
	private String poBox;// Done
	private String postalCode; // Done
	private String region; // Done
	private String country; // Done
	private String telephoneNo; // Done
	private String faxNo; // Done
	private String industry; // Done
	private String shippingCondition; // Done
	private String houseNo; //Done
	
	private List<SalesItem> salesItems;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
    @XmlElement(nillable = true)
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
    @XmlElement(nillable = true)
	public String getName3() {
		return name3;
	}
	public void setName3(String name3) {
		this.name3 = name3;
	}
    @XmlElement(nillable = true)
	public String getName4() {
		return name4;
	}
	public void setName4(String name4) {
		this.name4 = name4;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
    @XmlElement(nillable = true)
	public String getOrderBlock() {
		return orderBlock;
	}
	public void setOrderBlock(String orderBlock) {
		this.orderBlock = orderBlock;
	}
    @XmlElement(nillable = true)
	public String getDeletionFlag() {
		return deletionFlag;
	}
	public void setDeletionFlag(String deletionFlag) {
		this.deletionFlag = deletionFlag;
	}
	public String getAccountGroup() {
		return accountGroup;
	}
	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}
    @XmlElement(nillable = true)
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
    @XmlElement(nillable = true)
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
    @XmlElement(nillable = true)
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
    @XmlElement(nillable = true)
	public String getStreet3() {
		return street3;
	}
	public void setStreet3(String street3) {
		this.street3 = street3;
	}
    @XmlElement(nillable = true)
	public String getStreet4() {
		return street4;
	}
	public void setStreet4(String street4) {
		this.street4 = street4;
	}
    @XmlElement(nillable = true)
	public String getStreet5() {
		return street5;
	}
	public void setStreet5(String street5) {
		this.street5 = street5;
	}
    @XmlElement(nillable = true)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
    @XmlElement(nillable = true)
	public String getPoBox() {
		return poBox;
	}
	public void setPoBox(String poBox) {
		this.poBox = poBox;
	}
    @XmlElement(nillable = true)
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
    @XmlElement(nillable = true)
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
    @XmlElement(nillable = true)
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
    @XmlElement(nillable = true)
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
    @XmlElement(nillable = true)
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	@Override
	public String toString() {
		return "PetronasCustomerInMessage [code=" + code + ", name1=" + name1
				+ ", name2=" + name2 + ", name3=" + name3 + ", name4=" + name4
				+ ", shortName=" + shortName + ", orderBlock=" + orderBlock
				+ ", deletionFlag=" + deletionFlag + ", accountGroup="
				+ accountGroup + ", creationDate=" + creationDate
				+ ", street1=" + street1 + ", street2=" + street2
				+ ", street3=" + street3 + ", street4=" + street4
				+ ", street5=" + street5 + ", city=" + city + ", poBox="
				+ poBox + ", postalCode=" + postalCode + ", region=" + region
				+ ", country=" + country + ", telephoneNo=" + telephoneNo
				+ ", faxNo=" + faxNo + ", industry=" + industry + "]";
	}
	public List<SalesItem> getSalesItems() {
		return salesItems;
	}
	public void setSalesItems(List<SalesItem> salesItems) {
		this.salesItems = salesItems;
	}
	

	public static class SalesItem {
		private String type; // Done
		private String salesOrg; // Done
		private String distributionChannel; // Done
		private String division; // Done
		private String salesOffice; // Done
		private String salesGroup; // Done
		private String currency; // Done
		private String exchangeRateType; // Done
		private String incoterm1; // Done
		private String unloadingPort; // Done
		private String paymentTerm; // Done
		private String deliveryPlant; // Done
		private String orderBlock;
		private String deletionFlag; // Done

		private List<CustomerHierarchy> customerHierarchies;

	    @XmlElement(nillable = true)
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getSalesOrg() {
			return salesOrg;
		}
		public void setSalesOrg(String salesOrg) {
			this.salesOrg = salesOrg;
		}
		public String getDistributionChannel() {
			return distributionChannel;
		}
		public void setDistributionChannel(String distributionChannel) {
			this.distributionChannel = distributionChannel;
		}
		public String getDivision() {
			return division;
		}
		public void setDivision(String division) {
			this.division = division;
		}
	    @XmlElement(nillable = true)
		public String getSalesOffice() {
			return salesOffice;
		}
		public void setSalesOffice(String salesOffice) {
			this.salesOffice = salesOffice;
		}
	    @XmlElement(nillable = true)
		public String getSalesGroup() {
			return salesGroup;
		}
		public void setSalesGroup(String salesGroup) {
			this.salesGroup = salesGroup;
		}
	    @XmlElement(nillable = true)
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
	    @XmlElement(nillable = true)
		public String getExchangeRateType() {
			return exchangeRateType;
		}
		public void setExchangeRateType(String exchangeRateType) {
			this.exchangeRateType = exchangeRateType;
		}
	    @XmlElement(nillable = true)
		public String getIncoterm1() {
			return incoterm1;
		}
		public void setIncoterm1(String incoterm1) {
			this.incoterm1 = incoterm1;
		}
	    @XmlElement(nillable = true)
		public String getUnloadingPort() {
			return unloadingPort;
		}
		public void setUnloadingPort(String unloadingPort) {
			this.unloadingPort = unloadingPort;
		}
	    @XmlElement(nillable = true)
		public String getPaymentTerm() {
			return paymentTerm;
		}
		public void setPaymentTerm(String paymentTerm) {
			this.paymentTerm = paymentTerm;
		}
	    @XmlElement(nillable = true)
		public String getDeliveryPlant() {
			return deliveryPlant;
		}
		public void setDeliveryPlant(String deliveryPlant) {
			this.deliveryPlant = deliveryPlant;
		}
	    @XmlElement(nillable = true)
		public String getOrderBlock() {
			return orderBlock;
		}
		public void setOrderBlock(String orderBlock) {
			this.orderBlock = orderBlock;
		}
	    @XmlElement(nillable = true)
		public String getDeletionFlag() {
			return deletionFlag;
		}
		public void setDeletionFlag(String deletionFlag) {
			this.deletionFlag = deletionFlag;
		}
		
		@Override
		public String toString() {
			return "SalesItem [type=" + type + ", salesOrg=" + salesOrg
					+ ", distributionChannel=" + distributionChannel
					+ ", division=" + division + ", salesOffice=" + salesOffice
					+ ", salesGroup=" + salesGroup + ", currency=" + currency
					+ ", exchangeRateType=" + exchangeRateType + ", incoterm1="
					+ incoterm1 + ", unloadingPort=" + unloadingPort
					+ ", paymentTerm=" + paymentTerm + ", deliveryPlant="
					+ deliveryPlant + ", orderBlock=" + orderBlock
					+ ", deletionFlag=" + deletionFlag
					+ ", customerHierarchies=" + customerHierarchies + "]";
		}
		public List<CustomerHierarchy> getCustomerHierarchies() {
			return customerHierarchies;
		}
		public void setCustomerHierarchies(List<CustomerHierarchy> customerHierarchies) {
			this.customerHierarchies = customerHierarchies;
		}
	}

	public static class CustomerHierarchy {
		private String partnerFunction;
		private String customerCode;
//		private String soldTo; // Done
//		private String shipTo; // Done
//		private String billTo;
//		private String payer;
//		private String consignee;
//		private String notifyParty;
//		public String getSoldTo() {
//			return soldTo;
//		}
//		public void setSoldTo(String soldTo) {
//			this.soldTo = soldTo;
//		}
//		public String getShipTo() {
//			return shipTo;
//		}
//		public void setShipTo(String shipTo) {
//			this.shipTo = shipTo;
//		}
//		public String getBillTo() {
//			return billTo;
//		}
//		public void setBillTo(String billTo) {
//			this.billTo = billTo;
//		}
//		public String getPayer() {
//			return payer;
//		}
//		public void setPayer(String payer) {
//			this.payer = payer;
//		}
//		public String getConsignee() {
//			return consignee;
//		}
//		public void setConsignee(String consignee) {
//			this.consignee = consignee;
//		}
//		public String getNotifyParty() {
//			return notifyParty;
//		}
//		public void setNotifyParty(String notifyParty) {
//			this.notifyParty = notifyParty;
//		}
//		@Override
//		public String toString() {
//			return "CustomerHierarchy [soldTo=" + soldTo + ", shipTo=" + shipTo
//					+ ", billTo=" + billTo + ", payer=" + payer + ", consignee="
//					+ consignee + ", notifyParty=" + notifyParty + "]";
//		}
		public String getPartnerFunction() {
			return partnerFunction;
		}
		public void setPartnerFunction(String partnerFunction) {
			this.partnerFunction = partnerFunction;
		}
		public String getCustomerCode() {
			return customerCode;
		}
		public void setCustomerCode(String customerCode) {
			this.customerCode = customerCode;
		}
		@Override
		public String toString() {
			return "CustomerHierarchy [partnerFunction=" + partnerFunction
					+ ", customerCode=" + customerCode + "]";
		}
	}

    @XmlElement(nillable = true)
	public String getShippingCondition() {
		return shippingCondition;
	}

	public void setShippingCondition(String shippingCondition) {
		this.shippingCondition = shippingCondition;
	}

	@XmlElement(nillable = true)
	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}
}

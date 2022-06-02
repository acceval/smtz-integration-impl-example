package com.acceval.core.integration.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class CustomerAddressInMessage implements Serializable {
	private String code;
	private String name1;
	private String city;
	private String postalCode;
	private String poBox;
	private String houseNo;
	private String street1;
	private String street2;
	private String street3;
	private String street4;
	private String street5;
	private String languageCode;

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
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@XmlElement(nillable = true)
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@XmlElement(nillable = true)
	public String getPoBox() {
		return poBox;
	}

	public void setPoBox(String poBox) {
		this.poBox = poBox;
	}

	@XmlElement(nillable = true)
	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
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

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

}

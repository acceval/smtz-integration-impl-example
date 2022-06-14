package com.acceval.core.integration.model;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

public class MasterDataMaterialInMessage implements Serializable {

	private String code; // Done
	private String name; // Done
	private String parentProduct; // Done
	private String baseUom; // Done
	private String materialType; // Done
	private String prodocutDeletionFlag; // Done
	private String division; // Done
	private String salesSpec;
	
	private List<Item> items;
	private List<PlantItem> plantItems;
	private List<MultilingualValue> multilingualNames;
	private List<MultilingualValue> multilingualSalesSpecs;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentProduct() {
		return parentProduct;
	}

	public void setParentProduct(String parentProduct) {
		this.parentProduct = parentProduct;
	}

	public String getBaseUom() {
		return baseUom;
	}

	public void setBaseUom(String baseUom) {
		this.baseUom = baseUom;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	
	

    @XmlElement(nillable = true)
	public String getProdocutDeletionFlag() {
		return prodocutDeletionFlag;
	}

	public void setProdocutDeletionFlag(String prodocutDeletionFlag) {
		this.prodocutDeletionFlag = prodocutDeletionFlag;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}
	
	@XmlElement(nillable = true)
	public String getSalesSpec() {
		return salesSpec;
	}

	public void setSalesSpec(String salesSpec) {
		this.salesSpec = salesSpec;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "PetronasMaterialInMessage [code=" + code + ", name=" + name
				+ ", parentProduct=" + parentProduct + ", baseUom=" + baseUom
				+ ", materialType=" + materialType + ", prodocutDeletionFlag="
				+ prodocutDeletionFlag + ", division=" + division + ", items="
				+ items + "]";
	}

	public static class PlantItem {

		private String plant;
		private String valuationClass;
		private String plantDeletionFlag;
		
		public String getPlant() {
			return plant;
		}
		public void setPlant(String plant) {
			this.plant = plant;
		}
	    @XmlElement(nillable = true)
		public String getPlantDeletionFlag() {
			return plantDeletionFlag;
		}
		public void setPlantDeletionFlag(String plantDeletionFlag) {
			this.plantDeletionFlag = plantDeletionFlag;
		}
		@Override
		public String toString() {
			return "PlantItem [plant=" + plant + ", plantDeletionFlag="
					+ plantDeletionFlag + "]";
		}
	    @XmlElement(nillable = true)
		public String getValuationClass() {
			return valuationClass;
		}
		public void setValuationClass(String valuationClass) {
			this.valuationClass = valuationClass;
		}
	}
	
	public static class Item {
		private String salesOrganisation; // Done
		private String distributionChannel; // Done
		private String salesAreaDeletionFlag; // Done
		private String deliveryPlant; // Done
		private String salesUom; // Done
		private String packaging;
		private String materialGroup1;
		private String materialGroup2;
		private String materialGroup3;

		public String getSalesOrganisation() {
			return salesOrganisation;
		}
		public void setSalesOrganisation(String salesOrganisation) {
			this.salesOrganisation = salesOrganisation;
		}
		public String getDistributionChannel() {
			return distributionChannel;
		}
		public void setDistributionChannel(String distributionChannel) {
			this.distributionChannel = distributionChannel;
		}
	    @XmlElement(nillable = true)
		public String getSalesAreaDeletionFlag() {
			return salesAreaDeletionFlag;
		}
		public void setSalesAreaDeletionFlag(String salesAreaDeletionFlag) {
			this.salesAreaDeletionFlag = salesAreaDeletionFlag;
		}
	    @XmlElement(nillable = true)
		public String getDeliveryPlant() {
			return deliveryPlant;
		}
		public void setDeliveryPlant(String deliveryPlant) {
			this.deliveryPlant = deliveryPlant;
		}
	    @XmlElement(nillable = true)
		public String getSalesUom() {
			return salesUom;
		}
		public void setSalesUom(String salesUom) {
			this.salesUom = salesUom;
		}
		@Override
		public String toString() {
			return "Item [salesOrganisation=" + salesOrganisation
					+ ", distributionChannel=" + distributionChannel
					+ ", salesAreaDeletionFlag=" + salesAreaDeletionFlag
					+ ", deliveryPlant=" + deliveryPlant + ", salesUom="
					+ salesUom + "]";
		}

	    @XmlElement(nillable = true)
		public String getPackaging() {
			return packaging;
		}
		public void setPackaging(String packaging) {
			this.packaging = packaging;
		}

        public String getMaterialGroup1() {
            return materialGroup1;
        }

        public void setMaterialGroup1(String materialGroup1) {
            this.materialGroup1 = materialGroup1;
        }

        public String getMaterialGroup2() {
            return materialGroup2;
        }

        public void setMaterialGroup2(String materialGroup2) {
            this.materialGroup2 = materialGroup2;
        }

        public String getMaterialGroup3() {
            return materialGroup3;
        }

        public void setMaterialGroup3(String materialGroup3) {
            this.materialGroup3 = materialGroup3;
        }
    }

	public List<PlantItem> getPlantItems() {
		return plantItems;
	}

	public void setPlantItems(List<PlantItem> plantItems) {
		this.plantItems = plantItems;
	}
	
	public static class MultilingualValue {
		private String language;
		private String value;
		
		@XmlElement(nillable = true)
		public String getLanguage() {
			return language;
		}
		
		public void setLanguage(String language) {
			this.language = language;
		}
		
		@XmlElement(nillable = true)
		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	@XmlElement(nillable = true)
	public List<MultilingualValue> getMultilingualNames() {
		return multilingualNames;
	}

	public void setMultilingualNames(List<MultilingualValue> multilingualNames) {
		this.multilingualNames = multilingualNames;
	}
	
	@XmlElement(nillable = true)
	public List<MultilingualValue> getMultilingualSalesSpecs() {
		return multilingualSalesSpecs;
	}

	public void setMultilingualSalesSpecs(List<MultilingualValue> multilingualSalesSpecs) {
		this.multilingualSalesSpecs = multilingualSalesSpecs;
	}
	
}


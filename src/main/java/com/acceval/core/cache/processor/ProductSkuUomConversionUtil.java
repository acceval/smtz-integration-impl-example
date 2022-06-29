package com.acceval.core.cache.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.acceval.core.cache.model.ProductSkuUom;

@Service
public class ProductSkuUomConversionUtil {
	public final static String PRODUCT_SKU_UOM = "PRODUCT_SKU_UOM";

	private final static List<GlobalUom> GlobalUoms = new ArrayList<>();
	static {
		GlobalUoms.add(new GlobalUom("GAL", "LT", 3.7854));
		GlobalUoms.add(new GlobalUom("LT", "ML", 1000));

		GlobalUoms.add(new GlobalUom("MT", "KG", 1000));
		GlobalUoms.add(new GlobalUom("KG", "G", 1000));

		GlobalUoms.add(new GlobalUom("WEEK", "DAY", 7));
		GlobalUoms.add(new GlobalUom("DAY", "HOUR", 24));
		GlobalUoms.add(new GlobalUom("HOUR", "MINUTE", 60));

		GlobalUoms.add(new GlobalUom("KM", "M", 1000));
		GlobalUoms.add(new GlobalUom("M", "CM", 100));
		GlobalUoms.add(new GlobalUom("CM", "MM", 10));
	}

	public double getConvertedQuantity(String fromUomCode, String toUomCode, double quantity,
			List<ProductSkuUom> lstProductSkuUom) {
		List<String[]> lstExecuted = new ArrayList<>();
		Double factor = getFactorRecursive(fromUomCode, toUomCode, lstProductSkuUom, 0, lstExecuted);
//		System.out.println("getFactorRecursive from: " + fromUomCode + ", to: " + toUomCode + ", factor: " + factor);
		return factor == null ? quantity : quantity * factor;
	}

	private Double getFactorRecursive(String fromUomCode, String toUomCode, List<ProductSkuUom> lstProductSkuUom,
			int deepLvl, List<String[]> lstExecuted) {
		if (StringUtils.isBlank(fromUomCode) || StringUtils.isBlank(toUomCode) || deepLvl > 10) {
			return null;
		}
		if (StringUtils.equals(fromUomCode, toUomCode)) {
			return 1d;
		}

		for (String[] executed : lstExecuted) {
			if (StringUtils.equals(executed[0], fromUomCode) && StringUtils.equals(executed[1], toUomCode)) {
				return null;
			} else if (StringUtils.equals(executed[0], toUomCode) && StringUtils.equals(executed[1], fromUomCode)) {
				return null;
			}
		}

		Double factor = getConversionFactor(lstProductSkuUom, fromUomCode, toUomCode);
//		System.out.println(StringUtils.repeat("=", deepLvl) + "> " + "from:" + fromUomCode + ", to:" + toUomCode
//				+ ", found factor: " + factor);
		lstExecuted.add(new String[] { fromUomCode, toUomCode });
		if (factor != null) {
			return factor;
		}

		if (lstProductSkuUom != null) {
			// recursive try
			for (ProductSkuUom skuUom : lstProductSkuUom) {

				if (fromUomCode.equals(skuUom.getFromUom().getCode())) {
					Double recFactor = getFactorRecursive(skuUom.getToUom().getCode(), toUomCode, lstProductSkuUom,
							deepLvl + 1, lstExecuted);
//					System.out.println(StringUtils.repeat("=", deepLvl) + "> " + "from:" + fromUomCode + ", to:"
//							+ toUomCode + ", factor: " + skuUom.getConversionFactor() + " * " + recFactor);
					if (recFactor != null) {
						return skuUom.getConversionFactor() * recFactor;
					}
				} else if (fromUomCode.equals(skuUom.getToUom().getCode())) {
					Double recFactor = getFactorRecursive(skuUom.getFromUom().getCode(), toUomCode, lstProductSkuUom,
							deepLvl + 1, lstExecuted);
//					System.out.println(StringUtils.repeat("=", deepLvl) + "> " + "from:" + fromUomCode + ", to:"
//							+ toUomCode + ", factor: " + recFactor + " / " + skuUom.getConversionFactor());
					if (recFactor != null) {
						return recFactor / skuUom.getConversionFactor();
					}
				}
			}
		}

		// recursive try
		for (GlobalUom globalUom : GlobalUoms) {

			if (fromUomCode.equals(globalUom.getFrom())) {
				Double recFactor = getFactorRecursive(globalUom.getTo(), toUomCode, lstProductSkuUom, deepLvl + 1,
						lstExecuted);
//				System.out.println(StringUtils.repeat("=", deepLvl) + "> " + "(global)from:" + fromUomCode + ", to:"
//						+ toUomCode + ", factor: " + globalUom.getFactor() + " * " + recFactor);
				if (recFactor != null) {
					return globalUom.getFactor() * recFactor;
				}
			} else if (fromUomCode.equals(globalUom.getTo())) {
				Double recFactor = getFactorRecursive(globalUom.getFrom(), toUomCode, lstProductSkuUom, deepLvl + 1,
						lstExecuted);
//				System.out.println(StringUtils.repeat("=", deepLvl) + "> " + "(global)from:" + fromUomCode + ", to:"
//						+ toUomCode + ", factor: " + recFactor + " / " + globalUom.getFactor());
				if (recFactor != null) {
					return recFactor / globalUom.getFactor();
				}
			}
		}

		return null;
	}

	private Double getConversionFactor(List<ProductSkuUom> lstProductSkuUom, String fromUomCode, String toUomCode) {
		if (lstProductSkuUom != null) {
			for (ProductSkuUom skuUom : lstProductSkuUom) {
				if (skuUom == null || skuUom.getFromUom() == null || skuUom.getToUom() == null) {
					continue;
				}
				if (fromUomCode.equals(skuUom.getFromUom().getCode())
						&& toUomCode.equals(skuUom.getToUom().getCode())) {
					return skuUom.getConversionFactor();
				} else if (toUomCode.equals(skuUom.getFromUom().getCode())
						&& fromUomCode.equals(skuUom.getToUom().getCode())) {
							return 1 / skuUom.getConversionFactor();
				}
			}
		}
		for (GlobalUom globalUom : GlobalUoms) {
			if (globalUom == null || globalUom.getFrom() == null || globalUom.getTo() == null) {
				continue;
			}
			if (fromUomCode.equals(globalUom.getFrom()) && toUomCode.equals(globalUom.getTo())) {
				return globalUom.getFactor();
			} else if (toUomCode.equals(globalUom.getFrom()) && fromUomCode.equals(globalUom.getTo())) {
				return 1 / globalUom.getFactor();
			}
		}
		return null;
	}

}

class GlobalUom {
	private String from;
	private String to;
	private double factor;

	public GlobalUom(String from, String to, double factor) {
		super();
		this.from = from;
		this.to = to;
		this.factor = factor;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}
}
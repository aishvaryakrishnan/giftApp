package com.app;

import java.lang.reflect.Method;

public class Product {

	private int styleId;
	private float price;
	private float originalPrice;
	private int colorId;
	private String productUrl;
	private String brandName;
	private String productName;
	private String imgUrl;

	public int getStyleId() {
		return styleId;
	}

	public void setStyleId(int styleId) {
		this.styleId = styleId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}

	public int getColorId() {
		return colorId;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			Class c = Class.forName(this.getClass().getName());
			for (Method m : c.getDeclaredMethods())
				if (m.getName().startsWith("get")) {
					sb.append(m.getName().substring(3) + " : "
							+ String.valueOf(m.invoke(this, null)) + "\n");
				}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}

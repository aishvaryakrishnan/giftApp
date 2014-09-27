package com.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Gift {

	private static int comboCnt = 0;
	
	public static String httpGet() throws IOException {
		URL url = new URL(
				"http://api.zappos.com/Search?key=52ddafbe3ee659bad97fcce7c53592916a6bfd73");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();

		conn.disconnect();
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter budget");
		int budget = Integer.parseInt(br.readLine());
		System.out.println("Enter quantity");
		int quantity = Integer.parseInt(br.readLine());
		String data = httpGet();

		JSONObject jObj = new JSONObject(data);

		JSONArray resObj = getArray("results", jObj);
		ArrayList<Product> products = new ArrayList<Product>();
		for (int i = 0; i < resObj.length(); i++) {
			JSONObject prodObj = resObj.getJSONObject(i);
			Product prod = new Product();
			prod.setStyleId(Integer.parseInt(getString("styleId", prodObj)));
			String price = getString("price", prodObj);
			prod.setPrice(Float.parseFloat(price.substring(price.indexOf("$") + 1)));
			price = getString("originalPrice", prodObj);
			prod.setOriginalPrice(Float.parseFloat(price.substring(price
					.indexOf("$") + 1)));
			prod.setColorId(Integer.parseInt(getString("colorId", prodObj)));
			prod.setProductUrl(getString("productUrl", prodObj));
			prod.setBrandName(getString("brandName", prodObj));
			prod.setImgUrl(getString("thumbnailImageUrl", prodObj));
			prod.setProductName(getString("productName", prodObj));
			products.add(prod);
		}

		getCombos(products, budget, quantity, new ArrayList<Product>(),
				0, 0);
	}

	static void getCombos(ArrayList<Product> products, int budget,
			int quantity, ArrayList<Product> data, int index, int i) {
		if (index == quantity) {
			float sum = 0;
			StringBuilder prod = new StringBuilder();
			for (int j = 0; j < quantity; j++) {
				if (sum <= budget) {
					Product p = data.get(j);
					prod.append(p.toString() + "\n");
					sum += data.get(j).getPrice();
				} else {
					break;
				}
			}
			if (sum <= budget) {
				System.out.println("Purchase Combination: " + ++comboCnt);
				System.out.println(prod);
			}
			return;
		}

		if (i >= products.size())
			return;
		data.add(index, products.get(i));
		getCombos(products, budget, quantity, data, index + 1, i + 1);
		getCombos(products, budget, quantity, data, index, i + 1);
	}

	private static JSONArray getArray(String tagName, JSONObject jObj)
			throws JSONException {
		return jObj.getJSONArray(tagName);
	}

	private static String getString(String tagName, JSONObject jObj)
			throws JSONException {
		return jObj.getString(tagName);
	}
}

package jieun.util;

import java.io.Serializable;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ProductDataHandler implements Serializable {
    private JSONObject productField;
    public ProductDataHandler() {

    }
    public ProductDataHandler(List<String> productList) {
        productField = new JSONObject();
        JSONArray productArray = new JSONArray();
        for (String product : productList) {
            productArray.add(product);
        }
        productField.put("products", productArray);
    }

    public JSONObject getProductField() {
        return productField;
    }

    @Override
    public String toString() {
        return productField.toJSONString();
    }
}

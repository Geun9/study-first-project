package jieun.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import jieun.dto.ProductDto;

public class StatusHandler implements Serializable {
    private JSONObject statusField;
    public StatusHandler() {
        statusField = new JSONObject();
        List<ProductDto> productList = new ArrayList<>();

        statusField.put("status", null);
        statusField.put("isExit", null);
//        statusField.put("data", productList.toArray());
    }

    public void setStatus(String status) {
        statusField.put("status", status);
    }

    public void setExit(String isExit) {
        statusField.put("isExit", isExit);
    }

    @Override
    public String toString() {
        return statusField.toJSONString();
    }
}

package jieun.service;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import jieun.dto.ProductDto;

public class ServerService {

    static List<ProductDto> productList = new ArrayList<>();
    static final String INVALID_PRODUCT_NUMBER_MESSAGE = "유효하지 않은 상품 번호입니다.";

    public List<String> getProductList() {
        List<String> result = new ArrayList<>();
        for (ProductDto product : productList) {
            result.add(product.toString());
        }
        return result;
    }

    public void createProduct(JSONObject data) {
        String productName = data.get("name").toString();
        int productPrice = Integer.parseInt(data.get("price").toString());
        int productStock = Integer.parseInt(data.get("stock").toString());
        productList.add(new ProductDto(productName, productPrice, productStock));
    }

    public void updateProduct(JSONObject data) {
        long productId = Long.parseLong(data.get("id").toString());

        if (!isValidProductId(productId)) {
            System.out.println(INVALID_PRODUCT_NUMBER_MESSAGE);
            return;
        }

        String updatedProductName = data.get("name").toString();
        int updatedProductPrice = Integer.parseInt(data.get("price").toString());
        int updatedProductStock = Integer.parseInt(data.get("stock").toString());
        ProductDto updateProduct = findById(productId);

        updateProduct.updatedProduct(updateProduct, updatedProductName, updatedProductPrice, updatedProductStock);
    }

    public void deleteProduct(JSONObject data) {
        long productId = Long.parseLong(data.get("id").toString());

        if (!(isValidProductId(productId))) {
            System.out.println(INVALID_PRODUCT_NUMBER_MESSAGE);
            return;
        }

        productList.remove(findById(productId));
    }

    public void exitApplication() {
        System.out.println("EXIT");
    }

    public boolean isValidProductId(long id) {
        boolean isValid = false;
        for (ProductDto product : productList) {
            if (product.getId() == id) {
                isValid = !isValid;
                break;
            }
        }
        return isValid;
    }

    public ProductDto findById(long id) {
        int index = -1;
        for (ProductDto product : productList) {
            if (product.getId() == id) {
                index = productList.indexOf(product);
                break;
            }
        }
        return productList.get(index);
    }
}
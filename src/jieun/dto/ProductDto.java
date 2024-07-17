package jieun.dto;

import java.io.Serializable;

public class ProductDto implements Serializable {
    private static long cnt = 0;
    private long id;
    private String name;
    private int price;
    private int stock;

    {
        ++cnt;
    }

    ProductDto() {

    }

    public ProductDto(String name, int price, int stock) {
        this.id = cnt;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public static ProductDto updatedProduct(ProductDto product, String name, int price, int stock) {
        product.name = name;
        product.price = price;
        product.stock = stock;

        return product;
    }

    @Override
    public String toString() {
        String listFormat = String.format("%-6d%-20s\t%-15d\t%-10d", this.id, this.name, this.price, this.stock);
        return listFormat;
    }
}

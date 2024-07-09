package jieun.service;

import jieun.dto.ProductDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    static List<ProductDto> productList = new ArrayList<>();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static final String header = "\n[상품 목록]";
    static final String borderLine = "--------------------------------------------------";
    static final String menuTitle = "no\t\tname\t\t\t\t\t\t\t\tprice\t\t\t\t\t stock";
    static final String menu = "메뉴: 1.Create | 2. Update | 3. Delete | 4.Exit";
    static final String menu1 = "\n[상품 생성]";
    static final String menu2 = "\n[상품 수정]";
    static final String menu3 = "\n[상품 삭제]";
    static final String menu4 = "\n[종료]";
    static final String selectMenu = "선택: ";
    static final String invalidProductNumberMessage = "유효하지 않은 상품 번호입니다.";

    static boolean isQuit = false;
    static int inputMenuNum;

    public static void main(String[] args) throws IOException{
        ProductService productService = new ProductService();

        while (!isQuit) {
            productService.printMenuList(); // 메뉴 리스트 출력 메서드
            productService.selectMenu();
        }
    }

    public void printMenuList() {
        System.out.println(header);
        System.out.println(borderLine);
        System.out.println(menuTitle);
        System.out.println(borderLine);
        getProductList();
        System.out.println(borderLine);
        System.out.println(menu);
    }

    public void getProductList() {
        for (ProductDto product : productList) {
            System.out.println(product.toString());
        }
    }

    public void selectMenu() throws IOException{
        System.out.print(selectMenu);
        inputMenuNum = Integer.parseInt(br.readLine());

        switch (inputMenuNum) {
            case 1:
                createProduct();
                break;
            case 2:
                updateProduct();
                break;
            case 3:
                deleteProduct();
                break;
            case 4:
                exitApplication();
                break;
            default:
                System.out.println("다시 입력해주세요.");
        }
    }

    public void createProduct() throws IOException{
        String productName;
        int productPrice;
        int productStock;

        System.out.println(menu1);
        System.out.print("상품 이름: ");
        productName = br.readLine();
        System.out.print("상품 가격: ");
        productPrice = Integer.parseInt(br.readLine());
        System.out.print("상품 재고: ");
        productStock = Integer.parseInt(br.readLine());

        productList.add(new ProductDto(productName, productPrice, productStock));
    }

    public void updateProduct() throws IOException{
        long productId;
        int index;
        String updatedProductName;
        int updatedProductPrice;
        int updatedProductStock;

        System.out.println(menu2);
        System.out.print("상품 번호: ");
        productId = Long.parseLong(br.readLine());

        if (isValidProductId(productId)) {
            System.out.print("이름 변경: ");
            updatedProductName = br.readLine();
            System.out.print("가격 변경: ");
            updatedProductPrice = Integer.parseInt(br.readLine());
            System.out.print("재고 변경: ");
            updatedProductStock = Integer.parseInt(br.readLine());

            ProductDto updateProduct = findById(productId);
            updateProduct.updatedProduct(updateProduct, updatedProductName, updatedProductPrice, updatedProductStock);
        } else {
            System.out.println(invalidProductNumberMessage);
        }
    }

    public void deleteProduct() throws IOException {
        long productId;

        System.out.println(menu3);
        System.out.print("상품 번호: ");
        productId = Long.parseLong(br.readLine());

        if (isValidProductId(productId)) {
            productList.remove(findById(productId));
        } else {
            System.out.println(invalidProductNumberMessage);
        }
    }

    public void exitApplication() {
        System.out.println(menu4);
        isQuit = !isQuit;
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
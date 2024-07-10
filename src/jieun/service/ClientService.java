package jieun.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import jieun.util.MenuDataHandler;
import jieun.util.MenuText;

public class ClientService {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int inputMenuNum;
    static MenuDataHandler menuDataHandler;
    static final String INVALID_NON_POSITIVE_NUMBER = "0 이상의 값을 입력하세요.";

    public ClientService() {
        this.menuDataHandler = new MenuDataHandler();
    }

    public MenuDataHandler getProductData(List<String> productList) throws IOException {
        displayMenuList(productList);
        selectMenuOption();
        return menuDataHandler;
    }

    public void displayMenuList(List<String> productList) {
        System.out.println(MenuText.HEADER.getText());
        System.out.println(MenuText.BORDER_LINE.getText());
        System.out.println(MenuText.MENU_TITLE.getText());
        System.out.println(MenuText.BORDER_LINE.getText());
        getProductList(productList);
        System.out.println(MenuText.BORDER_LINE.getText());
        System.out.println(MenuText.MENU.getText());
    }

    public void getProductList(List<String> productList) {
        for (String product : productList) {
            System.out.println(product);
        }
    }

    public void selectMenuOption() throws IOException {
        System.out.print(MenuText.SELECT.getText());
        inputMenuNum = Integer.parseInt(br.readLine());

        menuDataHandler.setMenuOption(inputMenuNum);

        switch (inputMenuNum) {
            case 1 -> createProduct();
            case 2 -> updateProduct();
            case 3 -> deleteProduct();
            case 4 -> exitApplication();
            default -> System.out.println("다시 입력해주세요.");
        }
    }

    public void createProduct() throws IOException{
        System.out.println(MenuText.MENU1.getText());
        System.out.print("상품 이름: ");
        String productName = br.readLine();
        System.out.print("상품 가격: ");
        int productPrice = Integer.parseInt(br.readLine());
        System.out.print("상품 재고: ");
        int productStock = Integer.parseInt(br.readLine());

        menuDataHandler.setName(productName);
        menuDataHandler.setPrice(productPrice);
        menuDataHandler.setStock(productStock);
    }

    public void updateProduct() throws IOException{
        System.out.println(MenuText.MENU2.getText());
        System.out.print("상품 번호: ");
        long productId = Long.parseLong(br.readLine());

        System.out.print("이름 변경: ");
        String updatedProductName = br.readLine();
        System.out.print("가격 변경: ");
        int updatedProductPrice = Integer.parseInt(br.readLine());
        System.out.print("재고 변경: ");
        int updatedProductStock = Integer.parseInt(br.readLine());

        menuDataHandler.setId(productId);
        menuDataHandler.setName(updatedProductName);
        menuDataHandler.setPrice(updatedProductPrice);
        menuDataHandler.setStock(updatedProductStock);
    }

    public void deleteProduct() throws IOException {
        System.out.println(MenuText.MENU3.getText());
        System.out.print("상품 번호: ");
        long productId = Long.parseLong(br.readLine());

        menuDataHandler.setId(productId);
    }

    public void exitApplication() {
        System.out.println(MenuText.MENU4.getText());
    }

    public boolean isNonNegative(int price, int stock) {
        return (price >= 0 && stock >= 0);
    }
}
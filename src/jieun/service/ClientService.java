package jieun.service;

import static jieun.util.ErrorCode.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import jieun.exception.ProductException;
import jieun.util.MenuDataHandler;
import jieun.util.MenuText;


public class ClientService {
    private static final int FIRST_MENU = 1;
    private static final int LAST_MENU = 4;
    private static final int MAX_PRICE = 999999;
    private static final int MIN_PRICE = 0;
    private static final int MAX_STOCK = 999;
    private static final int MIN_STOCK = 0;
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static List<String> productList;
    private static int inputMenuNum;
    private static MenuDataHandler menuDataHandler;
    public ClientService() {
        this.menuDataHandler = new MenuDataHandler();
    }

    public MenuDataHandler getProductData(List<String> inputProductList) throws IOException {
        productList = inputProductList;
        displayMenuList();
        selectMenuOption();
        return menuDataHandler;
    }

    /*
     * 메뉴 정보 출력
     */
    public void displayMenuList() {
        System.out.println(MenuText.HEADER.getText());
        System.out.println(MenuText.BORDER_LINE.getText());
        System.out.println(MenuText.MENU_TITLE.getText());
        System.out.println(MenuText.BORDER_LINE.getText());
        getProductList();
        System.out.println(MenuText.BORDER_LINE.getText());
        System.out.println(MenuText.MENU.getText());
    }

    /*
     * 저장된 상품 List 출력
     */
    public void getProductList() {
        for (String product : productList) {
            System.out.println(product);
        }
    }

    /*
     * 메뉴 번호 선택
     */
    public void selectMenuOption() throws IOException {
        System.out.print(MenuText.SELECT.getText());
        inputMenuNum = Integer.parseInt(br.readLine());
        validateMenuNum();

        menuDataHandler.setMenuOption(inputMenuNum);

        switch (inputMenuNum) {
            case 1 -> createProduct();
            case 2 -> updateProduct();
            case 3 -> deleteProduct();
            case 4 -> exitApplication();
        }
    }

    /*
     * 메뉴: 1. Create (상품 생성)
     * 입력: 상품 이름, 상품 가격, 상품 재고
     */
    public void createProduct() throws IOException{
        System.out.println(MenuText.MENU1.getText());
        System.out.print("상품 이름: ");
        String productName = br.readLine();
        System.out.print("상품 가격: ");
        int productPrice = Integer.parseInt(br.readLine());
        validateProductPrice(productPrice);
        System.out.print("상품 재고: ");
        int productStock = Integer.parseInt(br.readLine());
        validateProductStock(productStock);

        menuDataHandler.setName(productName);
        menuDataHandler.setPrice(productPrice);
        menuDataHandler.setStock(productStock);
    }

    /*
     * 메뉴: 2. Update (상품 수정)
     * 입력: 상품 번호, 상품 이름, 상품 가격, 상품 재고
     */
    public void updateProduct() throws IOException{
        System.out.println(MenuText.MENU2.getText());
        System.out.print("상품 번호: ");
        long productId = Long.parseLong(br.readLine());

        System.out.print("이름 변경: ");
        String updatedProductName = br.readLine();
        System.out.print("가격 변경: ");
        int updatedProductPrice = Integer.parseInt(br.readLine());
        validateProductPrice(updatedProductPrice);
        System.out.print("재고 변경: ");
        int updatedProductStock = Integer.parseInt(br.readLine());
        validateProductStock(updatedProductStock);

        menuDataHandler.setId(productId);
        menuDataHandler.setName(updatedProductName);
        menuDataHandler.setPrice(updatedProductPrice);
        menuDataHandler.setStock(updatedProductStock);
    }

    /*
     * 메뉴: 3. Delete (상품 삭제)
     * 입력: 상품 번호
     */
    public void deleteProduct() throws IOException {
        System.out.println(MenuText.MENU3.getText());
        System.out.print("상품 번호: ");
        long productId = Long.parseLong(br.readLine());

        menuDataHandler.setId(productId);
    }

    /*
     * 메뉴: 4. Exit (프로그램 종료)
     */
    public void exitApplication() {
        System.out.println(MenuText.MENU4.getText());
    }

    /*
     * 메뉴 번호 유효성 검사
     * 메뉴 번호: 1 ~ 4
     */
    public void validateMenuNum() {
        if (inputMenuNum < FIRST_MENU || LAST_MENU < inputMenuNum) {
            throw new ProductException(MENU_OPTION_NOT_FOUND_EXCEPTION);
        }
    }

    /*
     * 상품 가격 유효성 검사
     * 상품 가격: 0 ~ 999,999
     */
    public void validateProductPrice(int productPrice) {
        if (productPrice < MIN_PRICE || MAX_PRICE < productPrice) {
            throw new ProductException(PRODUCT_PRICE_OUT_OF_RANGE_EXCEPTION);
        }
    }

    /*
     * 상품 재고 유효성 검사
     * 상품 재고: 0 ~ 999
     */
    public void validateProductStock(int productStock) {
        if (productStock < MIN_STOCK || MAX_STOCK < productStock) {
            throw new ProductException(PRODUCT_STOCK_OUT_OF_RANGE_EXCEPTIO);
        }
    }
}
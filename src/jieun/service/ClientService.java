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
    private static final String MENU_NUMBER = "^[1-4]";
    private static final String NAME = "^[a-zA-Z0-9ㄱ-힣]{1,10}";
    private static final String PRICE = "^\\d{0,6}$";
    private static final String STOCK = "^\\d{0,3}$";
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static List<String> productList;
    private static int menuNum;
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

    /**
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

    /**
     * 저장된 상품 List 출력
     */
    public void getProductList() {
        for (String product : productList) {
            System.out.println(product);
        }
    }

    /**
     * 메뉴 번호 선택
     */
    public void selectMenuOption() throws IOException {
        System.out.print(MenuText.SELECT.getText());
        String inputMenu = br.readLine();

        menuNum = validateMenuNum(inputMenu);
        menuDataHandler.setMenuOption(menuNum);

        switch (menuNum) {
            case 1 -> createProduct();
            case 2 -> updateProduct();
            case 3 -> deleteProduct();
            case 4 -> exitApplication();
        }
    }

    /**
     * 메뉴: 1. Create (상품 생성)
     * 입력: 상품 이름, 상품 가격, 상품 재고
     */
    public void createProduct() throws IOException {
        System.out.println(MenuText.MENU1.getText());
        System.out.print("상품 이름: ");
        String inputName = br.readLine();
        String productName = validateProductName(inputName);

        System.out.print("상품 가격: ");
        String inputPrice = br.readLine();
        int productPrice = validateProductPrice(inputPrice);

        System.out.print("상품 재고: ");
        String inputStock = br.readLine();
        int productStock = validateProductStock(inputStock);

        menuDataHandler.setName(productName);
        menuDataHandler.setPrice(productPrice);
        menuDataHandler.setStock(productStock);
    }

    /**
     * 메뉴: 2. Update (상품 수정)
     * 입력: 상품 번호, 상품 이름, 상품 가격, 상품 재고
     */
    public void updateProduct() throws IOException {
        System.out.println(MenuText.MENU2.getText());

        System.out.print("상품 번호: ");
        String inputId = br.readLine();
        long productId = validateProductId(inputId);

        System.out.print("이름 변경: ");
        String updatedProductName = br.readLine();

        System.out.print("가격 변경: ");
        String inputPrice = br.readLine();
        int updatedProductPrice = validateProductPrice(inputPrice);

        System.out.print("재고 변경: ");
        String inputStock = br.readLine();
        int updatedProductStock = validateProductStock(inputStock);

        menuDataHandler.setId(productId);
        menuDataHandler.setName(updatedProductName);
        menuDataHandler.setPrice(updatedProductPrice);
        menuDataHandler.setStock(updatedProductStock);
    }

    /**
     * 메뉴: 3. Delete (상품 삭제)
     * 입력: 상품 번호
     */
    public void deleteProduct() throws IOException {
        System.out.println(MenuText.MENU3.getText());
        System.out.print("상품 번호: ");
        String inputId = br.readLine();

        long productId = validateProductId(inputId);

        menuDataHandler.setId(productId);
    }

    /**
     * 메뉴: 4. Exit (프로그램 종료)
     */
    public void exitApplication() {
        System.out.println(MenuText.MENU4.getText());
    }

    /**
     * 메뉴 번호 유효성 검사
     * 메뉴 번호: 1 ~ 4
     */
    public int validateMenuNum(String inputMenu) {
        try {
            if (inputMenu.matches(MENU_NUMBER)) {
                return Integer.parseInt(inputMenu);
            } else {
                throw new ProductException(INVALID_MENU_OPTION);
            }
        } catch (NumberFormatException e) {
            throw new ProductException(INVALID_MENU_OPTION);
        }
    }

    /**
     * 상품 이름 유효성 검사
     * [0~9, a~z, A~Z, ㄱ~힣] 1~10
     */
    public String validateProductName(String inputName) {
        if (inputName.matches(NAME)) {
            return inputName;
        } else {
            throw new ProductException(INVALID_PRODUCT_NAME);
        }
    }

    /**
     * 상품 Id 유효성 검사
     */
    public long validateProductId(String inputId) {
        try {
            long productId = Long.parseLong(inputId);
            return productId;
        } catch (NumberFormatException e) {
            throw new ProductException(INVALID_PRODUCT_ID);
        }
    }

    /**
     * 상품 가격 유효성 검사
     * 상품 가격: 0 ~ 999,999
     */
    public int validateProductPrice(String inputPrice) {
        try {
            if (inputPrice.matches(PRICE)) {
                return Integer.parseInt(inputPrice);
            } else {
                throw new ProductException(INVALID_PRODUCT_PRICE);
            }
        } catch (NumberFormatException e) {
            throw new ProductException(INVALID_PRODUCT_PRICE);
        }
    }

    /**
     * 상품 재고 유효성 검사
     * 상품 재고: 0 ~ 999
     */
    public int validateProductStock(String inputStock) {
        try {
            if (inputStock.matches(STOCK)) {
                return Integer.parseInt(inputStock);
            } else {
                throw new ProductException(INVALID_PRODUCT_STOCK);
            }
        } catch (NumberFormatException e) {
            throw new ProductException(INVALID_PRODUCT_STOCK);
        }
    }

}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JsonClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            boolean isQuitted = false;
            while (!isQuitted) {
                try (Socket socket = new Socket("127.0.0.1", 9999);
                     InputStream is = socket.getInputStream();
                     OutputStream os = socket.getOutputStream();
                     BufferedReader br = new BufferedReader(new InputStreamReader(is));
                     PrintWriter out = new PrintWriter(new OutputStreamWriter(os), true)) {

                    System.out.println("연결 성공");
                    System.out.println("[상품목록]");
                    System.out.println("----------------------------------------------");
                    System.out.println("no  name                    price       stock ");
                    System.out.println("----------------------------------------------");

                    // 서버로부터 상품 목록 받기
                    String line = br.readLine();
                    printProductList(line);

                    System.out.println("----------------------------------------------");
                    System.out.println("메뉴: 1. Create | 2. Update | 3. Delete | 4. Exit");
                    System.out.print("선택: ");
                    int num = sc.nextInt();
                    sc.nextLine(); // consume newline character

                    JSONObject root = new JSONObject();
                    JSONObject object = new JSONObject();

                    switch (num) {
                        case 1:
                            System.out.println("[상품 생성]");
                            System.out.print("상품 이름: ");
                            String productName = sc.nextLine();
                            System.out.print("상품 가격: ");
                            int productPrice = sc.nextInt();
                            System.out.print("상품 재고: ");
                            int productStock = sc.nextInt();

                            object.put("productName", productName);
                            object.put("productPrice", productPrice);
                            object.put("productStock", productStock);

                            break;
                        case 2:
                            System.out.println("[상품 수정]");
                            System.out.print("상품 번호: ");
                            int productNo = sc.nextInt();
                            sc.nextLine(); // consume newline character
                            System.out.print("상품 이름: ");
                            productName = sc.nextLine();
                            System.out.print("상품 가격: ");
                            productPrice = sc.nextInt();
                            System.out.print("상품 재고: ");
                            productStock = sc.nextInt();

                            object.put("productNo", productNo);
                            object.put("productName", productName);
                            object.put("productPrice", productPrice);
                            object.put("productStock", productStock);

                            break;
                        case 3:
                            System.out.println("[상품 삭제]");
                            System.out.print("상품 번호: ");
                            productNo = sc.nextInt();

                            object.put("productNo", productNo);

                            break;
                        case 4:
                            root.put("menu", num);
                            String jsonToStr = root.toJSONString();
                            out.println(jsonToStr);
                            out.flush();
                            isQuitted = true;
                            System.out.println("프로그램을 종료합니다.");
                            break;
                        default:
                            System.out.println("올바른 메뉴 번호를 입력하세요.");
                            continue; // 다시 메뉴 선택
                    }

                    if (!isQuitted) {
                        root.put("menu", num);
                        root.put("data", object);

                        String jsonToStr = root.toJSONString();
                        out.println(jsonToStr);
                        out.flush();

                        // 서버로부터 상품 목록 받기
                        line = br.readLine();
                        printProductList(line);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printProductList(String line) {
        try {
            JSONObject jsonResult = (JSONObject) JSONValue.parse(line);
            JSONArray jsonArray = (JSONArray) jsonResult.get("data");
            if (jsonArray.size() > 0) {
                for (Object obj : jsonArray) {
                    JSONObject jsonObj = (JSONObject) obj;
                    int no = ((Long) jsonObj.get("no")).intValue();
                    String name = (String) jsonObj.get("name");
                    int price = ((Long) jsonObj.get("price")).intValue();
                    int stock = ((Long) jsonObj.get("stock")).intValue();
                    System.out.printf("%-4d%-24s%-12d%-8d\n", no, name, price, stock);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
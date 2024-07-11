package jieun.product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jieun.service.ServerService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import jieun.util.Casting;
import jieun.util.ProductDataHandler;
import jieun.util.StatusHandler;

public class ProductServer {

    public static void main(String[] args) {
        ServerService serverService = new ServerService();

        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println(getCurrentTime() + " Server is ready.");

            while (true) {
                // Accept a client connection
                System.out.println(getCurrentTime() + " Waiting for a connection request.");
                Socket clientSocket = serverSocket.accept(); // Socket for communication with client
                System.out.println(
                    getCurrentTime() + " A connection request has been received from "
                        + clientSocket.getInetAddress());

                // Initialize the streams
                BufferedReader clientReader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);

                try {
                    while (true) {

                        /*
                         * 1번
                         * server -> client (productList)
                         * 상품 목록을 Client로 보내기
                         */
                        List<String> productList = serverService.getProductList();
                        ProductDataHandler productDataHandler = new ProductDataHandler(productList);

                        clientWriter.println(productDataHandler.toString());

                        /*
                         * 2번
                         * client -> server (menuData)
                         * Client에서 보낸 user가 입력한 정보 받아서 처리하기
                         */
                        String menuDataString = clientReader.readLine();
                        if (menuDataString == null || menuDataString.equals("END_OF_MENU_DATA")) {
                            break;
                        }
                        JSONObject jsonData = Casting.toJson(menuDataString);

                        int menuOption = Integer.parseInt(jsonData.get("menu").toString());
                        JSONObject productData = (JSONObject) (jsonData.get("data"));

                        switch (menuOption) {
                            case 1 -> serverService.createProduct(productData);
                            case 2 -> serverService.updateProduct(productData);
                            case 3 -> serverService.deleteProduct(productData);
                            case 4 -> serverService.exitApplication();
                        }

                        /*
                         * 3번
                         * server -> client (result)
                         * 2번의 처리 결과 Client로 보내기
                         */
                        StatusHandler statusHandler = new StatusHandler();
                        statusHandler.setStatus("success");
                        if (menuOption == 4) {
                            statusHandler.setExit("EXIT");
                        }
                        clientWriter.println(statusHandler.toString());

                        // 4. Check exit condition
                        String exitCommand = clientReader.readLine();
                        if (exitCommand.equalsIgnoreCase("EXIT")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println(
                        getCurrentTime() + " Error occurred during communication with the client: "
                            + e.getMessage());
                } catch (ParseException e) {
                    System.out.println(getCurrentTime() + " Error occurred while parsing JSON data: " + e.getMessage());
                }
                clientSocket.close();
                System.out.println(getCurrentTime() + " Client connection closed.");

            }
        } catch (IOException e) {
            System.out.println(
                getCurrentTime() + " Error occurred while creating the server socket: "
                    + e.getMessage());
        }
    }

    static String getCurrentTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    }
}
package jieun.product;

import static jieun.util.Casting.parseProductList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import jieun.service.ClientService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import jieun.util.MenuDataHandler;

public class ProductClient {


    public static void main(String[] args) {
        try {
            // Connect to the server at IP address "127.0.0.1" and port 8080
            String serverIp = "127.0.0.1";
            System.out.println("서버에 연결중입니다.\n서버 IP: " + serverIp);
            Socket socket = new Socket(serverIp, 8080);

            // Initialize the streams
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                // 1. server -> client (productList)
                List<String> productList = new ArrayList<>();
                String productDataString = serverReader.readLine();

                productList = parseProductList(productDataString);

                // 2. client -> server (menuData)
                ClientService clientService = new ClientService();
                MenuDataHandler menuData = clientService.getProductData(productList);
                serverWriter.println(menuData.toString());
                serverWriter.println("END_OF_MENU_DATA");

                // 3. server -> client (result)
                String statusResult = serverReader.readLine();
                String isExit = "";
                if (statusResult != null) {
                    JSONObject jsonData = (JSONObject) new JSONParser().parse(statusResult);
                    isExit = (String) jsonData.get("isExit");
                }

                // 4. Check exit condition
                if (isExit != null && isExit.equals("EXIT")) {
                    serverWriter.println("exit");
                    break;
                }

            }

            // End of communication
            System.out.println("Closing the connection.");

            // Close socket
            socket.close();
            System.out.println("Connection closed.");
        } catch (ConnectException e) {
            e.printStackTrace();
            System.out.println("Connection error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("I/O error: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("JSON parsing error: " + e.getMessage());
        }
    }
}
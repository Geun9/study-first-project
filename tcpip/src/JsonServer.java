import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JsonServer {

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static ArrayList<Product> productList = new ArrayList<>();

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(9999);

            while (true) {
                System.out.println("연결 대기");
                Socket socket = serverSocket.accept();
                System.out.println(socket);

                Task task = new Task(socket);
                executorService.submit(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public static class Task implements Runnable {
        private Socket socket;

        public Task(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (InputStream is = socket.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                OutputStream os = socket.getOutputStream();
                PrintWriter out = new PrintWriter(new OutputStreamWriter(os), true);) {
                
                JSONObject root = null;
                root = new JSONObject();
                root.put("status", "success");
                root.put("data", productsToJsonArray());
                
                String result = root.toJSONString();
                out.println(result);
                out.flush();
                
                String data = in.readLine(); 
                System.out.println(data);

                JSONObject jsonRoot = (JSONObject) JSONValue.parse(data);
                JSONObject jsonData = null;
                
                // root = new JSONObject();
                // root.put("status", "success");
                // root.put("data", productsToJsonArray());
                
                // System.out.println(productsToJsonArray());
                
                // String result = root.toJSONString();
                // System.out.println(result);
                // out.println(result);
                // out.flush();
                Long action = (Long) jsonRoot.get("menu");
                System.out.println("action: " + action);
                switch (action.intValue()) {
                    case 1:
                        System.out.println("생성----------------------------");
                        jsonData = (JSONObject) jsonRoot.get("data");
                        System.out.println("data: " + jsonData);
                        String productName = jsonData.get("productName").toString();
                        System.out.println("productName: " + productName);
                        Long productPrice = (Long)jsonData.get("productPrice");
                        System.out.println("productPrice: " + productPrice);
                        Long productStock = (Long)jsonData.get("productStock");
                        System.out.println("productStock: " + productStock);
                        Product product = new Product(productName, productPrice.intValue(), productStock.intValue());
                        productList.add(product);

                        root = new JSONObject();
                        JSONObject object = new JSONObject();
                        object.put("productNo", productList.indexOf(product));
                        object.put("productName", product.getName());
                        object.put("productPrice", product.getPrice());
                        object.put("productStock", product.getStock());

                        root.put("status", "success");
                        root.put("data", productsToJsonArray());
                        
                        result = root.toJSONString();
                        System.out.println(result);
                        out.println(result);
                        out.flush();
                        break;
                    case 2:
                        System.out.println("수정----------------------------");
                        jsonData = (JSONObject) jsonRoot.get("data");
                        Long productNo = (Long)jsonData.get("productNo");
                        product = productList.get(productNo.intValue());
                        System.out.println("data: " + jsonData);
                        productName = jsonData.get("productName").toString();
                        product.setName(productName);
                        System.out.println("productName: " + productName);
                        productPrice = (Long)jsonData.get("productPrice");
                        product.setPrice(productPrice.intValue());
                        System.out.println("productPrice: " + productPrice);
                        productStock = (Long)jsonData.get("productStock");
                        product.setStock(productStock.intValue());
                        System.out.println("productStock: " + productStock);
                        
                        root = new JSONObject();
                        object = new JSONObject();
                    
                        root.put("status", "success");
                        root.put("data", productsToJsonArray());
                        
                        result = root.toJSONString();
                        System.out.println(result);
                        out.println(result);
                        out.flush();
                        break;
                    case 3:
                        System.out.println("삭제----------------------------");
                        jsonData = (JSONObject) jsonRoot.get("data");
                        productNo = (Long)jsonData.get("productNo");
                        product = productList.get(productNo.intValue());
                        productList.remove(product);
                        
                        root = new JSONObject();
                        object = new JSONObject();
                    
                        root.put("status", "success");
                        root.put("data", productsToJsonArray());
                        
                        result = root.toJSONString();
                        out.println(result);
                        out.flush();
                        break;
                    case 4:
                        System.out.println("서버를 종료합니다.");
                        System.exit(0);
                    default:
                        break;
                }
                System.out.println(Thread.currentThread().getName() + "가(이) 처리함.");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private JSONArray productsToJsonArray() {
            JSONArray jsonArray = new JSONArray();
            Iterator<Product> e = productList.iterator();
            
            while (e.hasNext()) {
                JSONObject jsonObject = new JSONObject();
                Product product = e.next();
                jsonObject.put("no", productList.indexOf(product));
                jsonObject.put("name", product.getName());
                jsonObject.put("price", product.getPrice());
                jsonObject.put("stock", product.getStock());
                jsonArray.add(jsonObject);
            }

            return jsonArray;
        }
        
    }
}
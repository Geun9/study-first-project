// import java.util.Scanner;

// public class SocketClient {
//     public static void main(String[] args) {
        
//         Scanner sc = new Scanner(System.in);
//         Product[] products = new Product[100];
//         boolean isQuit = false;
//         int index = 0;
        
//         while (!isQuit) {
//             System.out.println("[상품목록]");
//             System.out.println("------------------------------------------------------");
//             System.out.println("no      name                    price           stock");
//             System.out.println("------------------------------------------------------");
    
//             for (Product p : products) {
//                 if (p != null) {
//                     System.out.println(p.no + "     " + p.name + "      " + p.price + "     " + p.stock);
//                 }
//             }

//             System.out.println("---------------------------------------------------");
//             System.out.println("메뉴: 1. Create | 2. Update | 3. Delete | 4. Exit");
//             System.out.print("선택: ");
//             int num = sc.nextInt();

//             switch (num) {
//                 case 1:
//                     System.out.println("[상품 생성]");
//                     System.out.print("상품 이름: ");
//                     String name = sc.next();
//                     sc.nextLine();
//                     System.out.print("상품 가격: ");
//                     int price = sc.nextInt();
//                     sc.nextLine();
//                     System.out.print("상품 재고: ");
//                     int stock = sc.nextInt();
//                     sc.nextLine();

//                     Product product = new Product(index, name, price, stock);
//                     products[index] = product;
//                     index++;

//                     break;
//                 case 2:
//                     System.out.println("[상품 수정]");
//                     System.out.print("상품 번호: ");
//                     int no = sc.nextInt();
//                     sc.nextLine();
//                     System.out.print("상품 이름: ");
//                     name = sc.nextLine();
//                     System.out.print("상품 가격: ");
//                     price = sc.nextInt();
//                     sc.nextLine();
//                     System.out.print("상품 재고: ");
//                     stock = sc.nextInt();
//                     sc.nextLine();

//                     product = products[no];
//                     if (product != null) {
//                         product.name = name;
//                         product.price = price;
//                         product.stock = stock;
//                     } else {
//                         System.out.println("등록된 상품이 없습니다.");
//                     }
//                 case 3:
//                     System.out.println("[상품 삭제]");
//                     System.out.print("상품 번호: ");
//                     num = sc.nextInt();

//                     if (products[num] != null) {
//                         products[num] = null;
//                     } else {
//                         System.out.println("등록된 상품이 없습니다.");
//                     }
//                     break;
//                 case 4:
//                     System.out.println("프로그램을 종료합니다.");
//                     isQuit = true;
//                 default:
//                     break;
//             }
//         }

//         sc.close();
//     }
// }

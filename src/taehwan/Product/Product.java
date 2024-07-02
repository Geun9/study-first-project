package taehwan.Product;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Product {
  public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  public int[] No = new int[10];
  public String[] Name = new String[10];
  public int[] price = new int[10];
  public int[] stock = new int[10];
  //public static int cnt = 0;

  //public Product(){}

  /*public Product(int[] No, String[] Name, int[] Price, int[] Stock) {
    this.No = No;
    this.Name = Name;
    this.price = Price;
    this.stock = Stock;
  }*/

  public int firstMenu()throws IOException{
    System.out.println("[상품 목록]");
    System.out.println("-------------------------------------------------------");
    System.out.println("no\t\t\tname\t\t\t\t\t\t\t\t\tprice\t\t\t\t\t\tstock");
    System.out.println("-------------------------------------------------------");
    for(int i = 0; i < No.length; i++) {
      if (No[i] > 0) {
        System.out.println(No[i] +"\t\t\t\t" + Name[i]+ "\t\t\t\t\t\t\t\t\t\t" + price[i]+ "\t\t\t\t\t\t\t" + stock[i]);
      }
    }
    System.out.println("-------------------------------------------------------");
    System.out.println("메뉴: 1.Create | 2.Update | 3.Delete | 4.Exit");
    System.out.print("선택:  ");
    int num = Integer.parseInt(br.readLine());
    return num;
  }

  public void Create() throws IOException {
    System.out.println("[상품 생성]");
    for(int i = 0; i<No.length; i++){
      if(Name[i] == null) {
        No[i] = i + 1;
        System.out.print("상품 이름: ");
        Name[i] = br.readLine();
        System.out.print("상품 가격: ");
        price[i] = Integer.parseInt(br.readLine());
        System.out.print("상품 재고: ");
        stock[i] = Integer.parseInt(br.readLine());
        break;
      }
    }
    //cnt++;
  }

  public void Update() throws IOException {
    System.out.println("[상품 수정]");
    System.out.print("상품 번호: ");
    int num = Integer.parseInt(br.readLine());
    for(int i = 0; i<=num; i++){
      if(No[i] == num) {
        System.out.print("이름 변경: ");
        Name[i] = br.readLine();
        System.out.print("가격 변경: ");
        price[i] = Integer.parseInt(br.readLine());
        System.out.print("재고 변경: ");
        stock[i] = Integer.parseInt(br.readLine());
        break;
      }
    }
  }

  public void Delete() throws IOException {
    System.out.println("[상품 삭제]");
    System.out.print("상품 번호:  ");
    int Num = Integer.parseInt(br.readLine());
    for(int i = 0; i <No.length; i++) {
      if (No[i]==Num){
        No[i] = 0;
        Name[i]= null;
        price[i] = 0;
        stock[i] = 0;
      }
    }
  }


}


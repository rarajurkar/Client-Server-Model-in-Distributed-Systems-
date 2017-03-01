import java.util.Scanner;

public class FirstPage {
	
	int Display(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Select one option : ");
		System.out.println("1) Create new file");
		System.out.println("2) Read the complete file ");
		System.out.println("3) Write");
		System.out.println("4) Seek then read or write");
		System.out.println("5) Delete");
		System.out.println("6) Terminate");
		return (sc.nextInt());
	}

}

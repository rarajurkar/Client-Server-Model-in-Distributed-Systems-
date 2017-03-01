import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.io.OutputStream; 
import java.io.OutputStreamWriter; 
import java.net.InetAddress; 
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner; 
 
public class Client1 { 
     
    private static Socket socket; 
 
public static void main(String args[]) 
{ 
    try 
    { 
        boolean doAgain = true;
        Map<String,Integer> ip = new HashMap<String,Integer>();
        ip.put("10.176.66.51", 25000);
        ip.put("10.176.66.52", 26000);
        ip.put("10.176.66.53", 27000);
        String[] array = {"10.176.66.51","10.176.66.52","10.176.66.53"};
	while(doAgain)
	{
        String randomIp = (array[new Random().nextInt(array.length)]); //randomly select the server
        int port = ip.get(randomIp); 
	if(port == 25000)
	{
		System.out.println("Server 1 ::");
	}else if(port == 26000)
	{
		System.out.println("Server 2 ::");
	}else if(port == 27000)
	{
		System.out.println("Server 3 ::");
	}
        socket = new Socket(randomIp, port);  
        Scanner sc = new Scanner(System.in); 
        FirstPage fp = new FirstPage(); 
        OutputStream os = socket.getOutputStream(); 
        OutputStreamWriter osw = new OutputStreamWriter(os); 
        BufferedWriter bw = new BufferedWriter(osw);
        InputStream is = socket.getInputStream(); 
        InputStreamReader isr = new InputStreamReader(is); 
        BufferedReader br = new BufferedReader(isr); 
        //display the options  
        String successMessage;            
        //FirstPage fp = new FirstPage();  
            //display the options 
        bw.write(port);
        bw.flush();  
 	String data;  
        int option = fp.Display();
        bw.write(option); //send the option to the server 
        bw.flush();
	if(option ==6){
		System.out.println("The session is going to close..");
		System.exit(0);
	 }
            System.out.println("Message sent to the server : "+option); 
            System.out.println("Enter the file name : "); 
            String fileName = sc.nextLine();
            bw.write(fileName+"\n"); 
	    
            bw.flush(); //send file name to the server
        switch(option){ 
                case 1:
                successMessage = br.readLine(); 
                    System.out.println(successMessage); 
                    break; 
                case 2: 
                    //logic to read a file 
		int k = 1;
		k=Integer.parseInt(br.readLine().trim());
		System.out.println(k);
		if(k==1)
                {
                	while((data=br.readLine())!=null && data.trim().length() !=0){
                	    System.out.println(data);
                	} 
			System.out.println("Done");
			successMessage = br.readLine(); 
                	System.out.println(successMessage);
			dbr.close();
			System.out.println("Done");
						
		}
		else{
			successMessage = br.readLine(); 
                	System.out.println(successMessage);
		}
		break; 
                case 3: 
                    //code to write a file at server
		k = 1;
		k=Integer.parseInt(br.readLine().trim());
		System.out.println(k);
		if(k==1){
		        System.out.println("Enter the data to be written and enter q to quit : ");
		        String wData; 
		        while(!((wData = sc.nextLine()).equalsIgnoreCase("q"))){
		            bw.write(wData + "\n");
		            bw.flush();
		        }
			bw.write("q"+"\n");
			bw.flush();
		} 
                successMessage = br.readLine(); 
                System.out.println(successMessage); 
		bw.close();
		br.close();
                    break; 
                case 4:
		k = 1;
		k=Integer.parseInt(br.readLine().trim());
		System.out.println(k);
		if(k==1){
		        System.out.println("Enter 1.read 2.write : ");
		        int appendOption = sc.nextInt();
		        bw.write(appendOption); //send the append option to the server 
		        bw.flush();
		        System.out.println("Enter offset from and number of bytes : ");
		        int from = sc.nextInt();
			int to = sc.nextInt();
			System.out.println("The cursor is at "+from);
		        bw.write(from); //send offset 
		        bw.flush();
		        bw.write(to); //send offset 
		        bw.flush();
		        if(appendOption == 1){
		            String AData; 
		            while((AData=br.readLine())!=null){
		                System.out.println(AData);
		            }     
		        }
		        else {
		            System.out.println("Enter the data to be written and enter q to quit : ");
		            String wData; 
		            while(!((wData = sc.nextLine()).equalsIgnoreCase("q"))){
		                bw.write(wData + "\n");
		                bw.flush();
		            }
			bw.write("q"+"\n");
			bw.flush();
		        }  
		 }   
                 successMessage = br.readLine(); 
                 System.out.println(successMessage); 
		bw.close(); 
		br.close();
                 break; 
                case 5: 
           	 successMessage = br.readLine(); 
                 System.out.println(successMessage); 
		br.close();
            	break;
        	case 6:
                    socket.close(); 
                    doAgain = false;
		break; 
		default : break;                    
            }  
        }  
    } 
    catch (Exception exception) 
    { 
        exception.printStackTrace(); 
    } 
    finally 
    { 
        //Closing the socket 
        try 
        { 
            socket.close(); 
        } 
        catch(Exception e) 
        { 
            e.printStackTrace(); 
        } 
    } 
} 
} 
 
 

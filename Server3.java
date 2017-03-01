import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Server3 {
    //static final int serverId = 1;
        private static Socket socket; 
        @SuppressWarnings("unchecked")
        public static void main(String args[]){
            Socket sSocket, c1Socket, c2Socket;
            int replyServer1=0,replyServer2=0;
            int serverPort = 27000;
            try{
               //client to server
               ServerSocket serverSocket = new ServerSocket(serverPort);
               System.out.println("Server Started and listening to the port "+serverPort); 
               int areSync = 1;
               //Server is always running. This is done using this while(true) loop  
           while(true){
               sSocket = serverSocket.accept();
               InputStream is = sSocket.getInputStream();
               InputStreamReader isr = new InputStreamReader(is);
               BufferedReader br = new BufferedReader(isr);
                  OutputStream os = sSocket.getOutputStream(); 
               OutputStreamWriter osw = new OutputStreamWriter(os); 
                   BufferedWriter bw = new BufferedWriter(osw); 
                        int clientPort =  br.read(); 
                
               if(clientPort ==serverPort){  
            	  c1Socket = new Socket("10.176.66.51", 25000);
                   System.out.println("port 26000 in use");
                          c2Socket = new Socket("10.176.66.53", 27000);
                   System.out.println("port 27000 in use");
                     
                   InputStream is1 = c1Socket.getInputStream();
                          InputStreamReader isr1 = new InputStreamReader(is1);
                          BufferedReader br1 = new BufferedReader(isr1);
                   OutputStream os1 = c1Socket.getOutputStream(); 
                         OutputStreamWriter osw1 = new OutputStreamWriter(os1); 
                   BufferedWriter bw1 = new BufferedWriter(osw1);  
                         
                   InputStream is2 = c2Socket.getInputStream();
                   InputStreamReader isr2 = new InputStreamReader(is2);
                   BufferedReader br2 = new BufferedReader(isr2);
                   OutputStream os2 = c2Socket.getOutputStream(); 
                   OutputStreamWriter osw2 = new OutputStreamWriter(os2); 
                   BufferedWriter bw2 = new BufferedWriter(osw2);


                  // while(true){
            	  //read the option
                 int option= br.read();
                       System.out.println("Message received from client is "+option);
                       System.out.println("Client has selected this server..");
                       //get the file name
                       String fileName = br.readLine(); 
                       System.out.println("Name of the file " +fileName);


                
                //send option and filename to other servers
                bw1.write(" "+option+"\n");
                      bw1.flush();
                      bw2.write(" "+option+"\n");
                      bw2.flush();
                      bw1.write(fileName+"\n");
                      bw1.flush();
                      bw2.write(fileName+"\n");
                      bw2.flush();
            
                      //check the files other servers 
                      //list of files in its own folder
                      File directory = new File("./");
                      File fList[] = directory.listFiles();
                      Map<File,String> fileDetails = new HashMap<File,String>();
                  System.out.println("maps not working");
                      Server3 s = new Server3();
                int n = fList.length;

                //send number of files to other servers 
                bw1.write(n);
                System.out.println(n);
                bw1.flush();
                bw2.write(n);
                bw2.flush();


                //sedn files names and checksum
   /*                   for(File f : fList){
                         bw1.write(f.getName()+"\n");
                         bw1.flush();
                         System.out.println(f.getName());
                         bw2.write(f.getName()+"\n");
                         bw2.flush();
                          String chksm = s.checksum(f);
                         // fileDetails.put(f,chksm);
                          bw1.write(chksm+"\n");
                         bw1.flush();
                         System.out.println(chksm);
                         bw2.write(chksm+"\n");
                         bw2.flush();
                      }
                  System.out.println(fileDetails);
*/


                  System.out.println("before receiving result");
                      //int ifSync1 = Integer.parseInt(br1.readLine().trim());
                      //int ifSync2 = Integer.parseInt(br2.readLine().trim());//ois2.readBoolean();
                int ifSync1 = 1;
                int ifSync2 =1;
                  System.out.println(ifSync1 +" "+ifSync2);
                      if((ifSync1==1) && (ifSync2==1)) {
                          // do everything
                       String filePath ="./" + fileName ; 
                       File f = new File(filePath); 
                         switch(option)
                       { 
                           case 1: 
                               //creating a file
                               boolean b = f.createNewFile();
                               System.out.println("above if");
                               //replyServer1=Integer.parseInt(br1.readLine().trim());
                               //replyServer2=Integer.parseInt(br2.readLine().trim());
                    
                               replyServer1=Integer.parseInt(br1.readLine().trim());
                               replyServer2=Integer.parseInt(br2.readLine().trim());
                               if(b ){//&& (replyServer1==1) && (replyServer2==1)){
                                   System.out.println("File created successfully..");
                                   bw.write("File created successfully.. \n");
                                   bw.flush();
                               }else {
                            	  bw.write("File creation failed.. \n");
                                   bw.flush();
                               }
                              break; 
                           case 2: 
                                   //logic to read a file
			if(f.exists()){
				bw.write("1"+"\n");
				bw.flush();
                               BufferedReader fbr = new BufferedReader(new FileReader(f));
                               System.out.println("Inside read..");
                               String str;
                               
	                  	while(((str=fbr.readLine()) != null) && str.trim().length() !=0) { 
		                              System.out.println(str);
		                              bw.write(str+"\n");
		                              bw.flush(); 
		                              } 
					  System.out.println("read completed");
		                          bw.write("read completed..."+ "\n");
		                          bw.flush();
 					bw.close();
                               		}
                               else{
					bw.write("0"+"\n");
					bw.flush();
                            	  	System.out.println(f.getName()+" does not exists..");
	                              bw.write(f.getName()+" does not exists.. \n");
	                              bw.flush();
					bw.close();
                               }
                                   break; 
                           case 3: 
                                  //logic to write a file 
 			if(f.exists()){
				bw.write("1"+"\n");
				bw.flush();
                               	FileWriter fileWriter = new FileWriter(f,true);
                               	System.out.println("data to be written in the file : ");
                               	String st;
	                             while(!((st = br.readLine()).equalsIgnoreCase("q"))){//(((st=br.readLine())!=null) && st.trim().length() !=0){
	                                  System.out.println(st); 
	                                  fileWriter.write(st+"\n"); 
	                                  fileWriter.flush();
	                                  bw1.write(st+"\n");
	                                  bw1.flush();
	                                  bw2.write(st+"\n");
	                                  bw2.flush();
	                              }
					
	                             	bw1.write("q"+"\n"); 
					bw1.flush();
					bw2.write("q"+"\n");
					bw2.flush();
	                              bw.write(f.getName()+"--Written successfully.. \n");
	                              bw.flush();
					System.out.println("done with writing");
					fileWriter.close();
					bw1.close();
					bw2.close();
					bw.close();

                               }
                               else{
				      bw.write("0"+"\n");
				      bw.flush();
                            	      System.out.println(f.getName()+" does not exists..");
	                              bw.write(f.getName()+" does not exists..\n");
	                              bw.flush();
					bw.close();
                               }
                               break; 
                           case 4: 
                        	  if(f.exists()){
				      System.out.println("inside server seek");
				      bw.write("1"+"\n");
				      bw.flush();
	                              RandomAccessFile raf = new RandomAccessFile(f,"rw");
	                              int appendOption = br.read();
	                              bw1.write(appendOption);
	                              bw2.write(appendOption);
	                              System.out.println("Append option : " +appendOption);
	                              int from = br.read();
	                              int to = br.read();
	                              bw1.write(from);
	                              bw1.flush();
	                              bw2.write(from);
	                              bw2.flush();
	                              bw1.write(to);
	                              bw1.flush();
	                              bw2.write(to);
	                              bw2.flush();                               
	                              System.out.println("from : "+from +"and bytes : "+to);
	                              raf.seek(from);
	                              byte[] bytes = new byte[1024];
	                              if(appendOption == 1){
	                                  raf.readFully(bytes, from,to);
	                                  String sAppend = new String(bytes);
	                                  System.out.println(sAppend);
	                                  bw.write(sAppend+"\n");
	                                  bw.flush();
	                              } 
	                              else{
	                                  System.out.println("data to be written in the file : ");
	                                  String string;
	                                  while(!((string = br.readLine()).equalsIgnoreCase("q"))){//((string=br.readLine())!=null){
	                                      System.out.println(string); 
	                                      raf.write(string.getBytes());                                        
	                                      bw1.write(string+"\n");
	                                      bw1.flush();
	                                      bw2.write(string+"\n");
	                                      bw2.flush();
	                                  }
					bw1.write("q"+"\n"); 
					bw1.flush();
					bw2.write("q"+"\n");
					bw2.flush();
	                              }
	                              bw.write("Seek operation successful..The cursor is at "+ (from+to) +"\n");
	                              bw.flush();
					bw.close();
					bw1.close();
					bw2.close();
                        	  }
                        	  else{
					bw.write("0"+"\n");
				        bw.flush();
                                	System.out.println(f.getName()+" does not exists..\n");
    	                                bw.write(f.getName()+" does not exists..\n");
    	                                bw.flush();
					bw.close();
                        	  }
                               break; 
	                    case 5:
		               	  if(f.exists()){
			                   boolean bd = f.delete();
			                   if(bd){
			                        System.out.println(f.getName()+"File deleted successfully..");
			                        bw.write(f.getName()+"--File deleted successfully.. \n");
			                        bw.flush();
			                        }
			                   else {
			                           bw.write("File deletion failed.. \n");
			                           bw.flush();
			                   }
		               	  }
		               	  else{
		                       	      System.out.println(f.getName()+" does not exists..");
		                              bw.write(f.getName()+" does not exists..");
		                              bw.flush();
						bw.close();
		               	  }
		               	  break;
	                   case 6: 
	                                 System.out.println("Client wants to close");  
	                         break;               
	                     }  
                       //continue;    
                      }
                  }
              else{
            //receive option ad filename
            String selectedOption1 = br.readLine();
                  String fName = br.readLine();
            System.out.println(selectedOption1+" "+fName);
            int selectedOption = Integer.parseInt(selectedOption1);
            //read numbr of files 
                 int n = br.read();
            System.out.println("number of files :" +n);

            //receive file anmes and checksum


                  String fileP = "./" + fName ; 
                  File f = new File(fileP); 
              System.out.println(fName);
                  switch(selectedOption){
                  case 1 :
                    //creating a file
                      Boolean b = f.createNewFile();
                              System.out.println("above if");
                              if(b){
                      System.out.println("in if");
                                 bw.write("1" +"\n");
                                 bw.flush();
                              }
                              else {
                                 bw.write("0" +"\n");
                                 bw.flush();
                              }
                    break;
                  case 2 :
                    break;
                  case 3 :
                    //write the file 
                    FileWriter fileWriter = new FileWriter(f,true);
                      System.out.println("data to be written in the file : ");
                      String st;
                      while(!((st = br.readLine()).equalsIgnoreCase("q"))){//((st=br.readLine())!=null){
                          System.out.println(st); 
                          fileWriter.write(st+"\n"); 
                          fileWriter.flush();
                       }
		fileWriter.close();
                    break;
                  case 4 :
                    int appendOption=br.read();
                    int from = br.read();
                    int to = br.read();
                    if(appendOption == 2){
                        RandomAccessFile raf = new RandomAccessFile(f,"rw");
                        raf.seek(from);
                        System.out.println("data to be written in the file : ");
                          String string;
                          while(!((string = br.readLine()).equalsIgnoreCase("q"))){//((string=br.readLine())!=null){
                              System.out.println(string); 
                              raf.write(string.getBytes()); 
                          }   
                    }   
                    break;
                case 5:
                boolean bd = f.delete();
            break;
                  case 6 :
                    System.out.println("Client wants to exit..");
                    break;
                  }
              }
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public String checksum(File file) 
    {
        try 
        {
           InputStream fin = new FileInputStream(file);
           java.security.MessageDigest md5er = MessageDigest.getInstance("MD5");
           byte[] buffer = new byte[1024];
           int read;
           do 
           {
               read = fin.read(buffer);
               if (read > 0)
                   md5er.update(buffer, 0, read);
           } while (read != -1);
           fin.close();
           byte[] digest = md5er.digest();
           if (digest == null)
             return null;
           String strDigest = "0x";
           for (int i = 0; i < digest.length; i++) 
           {
               strDigest += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1).toUpperCase();
           }
           return strDigest;
        } 
        catch (Exception e) 
        {
           return null;
        }
    }
}



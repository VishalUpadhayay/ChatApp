import java.net.*;
import java.io.*;
class Client {
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Client()
    {
        try{
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1",8080);
            System.out.println("connection done.");
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        } catch (Exception e) {


        }
    }
    
    public void startReading()
        {
            //thread-read karke deta rahega
            Runnable r1=()->{
                System.out.println("reader started..");

                while(true) {
                    try {
                        String msg=br.readLine();
                        if(msg.equals("exit")){
                           System.out.println("Server terminated the chat");
                           break;
                        }
                        System.out.println("Server : "+msg);
                    } catch (Exception e) {
                        e.printStackTrace(); //printStackTrace ye console pe technical information trace ko print karne ke liye ki kis line pe exception aata hai toh isse print kar deta hai
                    }
                } //ye hamne jo likha runnable se voh ek thread banaya
            };
            new Thread(r1).start();
        }
    

    public void startWriting()
        {
            //thread - data user lega & then send karega client tak
            Runnable r2=()->{
                System.out.println("writer started..");
                while(true)
                {
                    try{
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                        String content=br1.readLine();
                        out.println(content);
                        out.flush();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(r2).start();
        }

        public static void main(String[] args) {
        System.out.println("this is client...");
        new Client();
    }
}

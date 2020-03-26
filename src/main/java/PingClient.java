import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class PingClient {

    public long startTime;
    public long endTime;
    private Socket socket;
    public BufferedReader fromServer;
    public DataOutputStream toServer;

    public PingClient() {
        socket = new Socket();

    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        PingClient pingClient = new PingClient();
        System.out.println("Please input domain: ");
        String hostName = sc.nextLine();
        System.out.println("Please input port: ");
        int port = sc.nextInt();
        System.out.println("Please input timeout: ");
        int timeout = sc.nextInt();
        System.out.println("Number of pings: ");
        int times = sc.nextInt();
        sc.close();

        pingClient.connectSocket(hostName, port, timeout);
        pingClient.requestTimes(times);

    }

    private void connectSocket(String hostName, int port, int timeout) throws IOException {
        socket.connect(new InetSocketAddress(hostName, port), timeout);
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    public void sendMessage() throws IOException {
        if (!socket.isConnected()) {
            return;
        }
        startTime();
        toServer.writeByte(32);
        fromServer.readLine();
        stopTime();
        System.out.println(endTime - startTime + " ms");

    }

    public void requestTimes(int times) throws IOException {
        for (int i = 0; i < times; i++) {
            sendMessage();
        }
    }

    private void startTime() {
        startTime = System.currentTimeMillis();
    }

    private void stopTime() {
        endTime = System.currentTimeMillis();
    }

}
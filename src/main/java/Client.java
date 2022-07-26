import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        InetSocketAddress socketAddress = new InetSocketAddress(Main.HOST, Main.PORT);
        final SocketChannel channel = SocketChannel.open();
        channel.connect(socketAddress);

        try (Scanner scanner = new Scanner(System.in)) {
            // буфур для данных
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            String number;
            while (true) {
                System.out.println("Введите значение N-го члена ряда Фибоначчи");
                number = scanner.nextLine();
                // условие завершения программы
                if (number.equals("end")) break;
                // запись в буфер и отправка сообщения серверу
                channel.write(ByteBuffer.wrap(number.getBytes(StandardCharsets.UTF_8)));
                // читаем данные с сервера в буфер
                int bytesCount = channel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                inputBuffer.clear();

            }
        }

//        try (Socket clientSocket = new Socket(host, port);
//             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
//            out.println("Senior Tomato\n");
//            System.out.println(in.readLine());
//        }  catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

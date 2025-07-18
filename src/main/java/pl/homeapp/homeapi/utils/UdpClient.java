package pl.homeapp.homeapi.utils;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class UdpClient {

    private static final int DEVICE_PORT = 38899;
    private static final int SOCKET_TIMEOUT = 5000;
    private static final int BUFFER_SIZE = 1024;

    public static CompletableFuture<String> sendCommand(String jsonCommand, String deviceIp) {
        return CompletableFuture.supplyAsync(() -> {
            DatagramSocket socket = null;
            try {
                byte[] sendData = jsonCommand.getBytes(StandardCharsets.UTF_8);
                InetAddress ipAddress = InetAddress.getByName(deviceIp);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, DEVICE_PORT);

                socket = new DatagramSocket();
                socket.setSoTimeout(SOCKET_TIMEOUT);

                System.out.println("Sending to IP: " + deviceIp + ", Port: " + DEVICE_PORT);
                socket.send(sendPacket);
                System.out.println("Message sent successfully: " + jsonCommand);

                byte[] receiveBuffer = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                socket.receive(receivePacket);

                String responseString = new String(
                        receivePacket.getData(),
                        0,
                        receivePacket.getLength(),
                        StandardCharsets.UTF_8
                );

                System.out.println("Received from " + receivePacket.getAddress() + ":" + receivePacket.getPort());
                System.out.println("Message content: " + responseString);

                return responseString;

            } catch (SocketTimeoutException e) {
                throw new CompletionException(new TimeoutException("Timeout waiting for response"));
            } catch (Exception e) {
                throw new CompletionException(e);
            } finally {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            }
        });
    }
}

package client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientSocketReader {
    private static final int SERVER_PORT_READ = 5555;

    public static void startReading(){
        new Thread(() -> {
            try(ServerSocket clientSocket = new ServerSocket(SERVER_PORT_READ)) {
                System.out.println("Клиент принимает информацию на порту " + SERVER_PORT_READ);

                while (true){
                    Socket socket = clientSocket.accept();
                    handleServer(socket);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }
    private static void handleServer(Socket socket) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
            String action = in.readUTF();
            boolean value = in.readBoolean();

            SphereEvent.activeSphere.put(action, value);
            System.out.println("packet send " + action + " " + SphereEvent.activeSphere.get(action));

            if (!value) {
                ClientEvent.playerSoundUpSphere(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

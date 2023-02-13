/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import battleship.BattleShip;

/**
 *
 * @author Trung
 */
public class MyClient {

    private Socket socket;
    private ObjectOutputStream out;
    private BattleShip gui;
    private MyClientReceive myClientRX;
    private boolean isConnected = false;

    public MyClient(BattleShip gui) {
        this.gui = gui;
    }

    public boolean StartConnection(String host, int port) {
        try {
            this.socket = new Socket(host, port);
        } catch (Exception ec) {
            System.out.println("Lỗi kết nối với Chủ phòng" + ec);
            this.gui.writeOutputMessage(" - Lỗi kết nối với Chủ phòng" + ec);
            return false;
        }

        this.gui.writeOutputMessage(" - Kết nối được chấp nhận " + this.socket.getInetAddress() + ":" + this.socket.getPort());
        this.gui.writeOutputMessage(" - Hãy bắt đầu ván đấu!");

        this.myClientRX = new MyClientReceive(this.socket, this.gui);
        this.myClientRX.start();

        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.out.flush();
        } catch (IOException eIO) {
            System.out.println("Ngoại lệ khi tạo mới Input/output Streams: " + eIO);
            return false;
        }

        SendMessage("C:Tôi đến để chiến đấu!");

        return true;
    }

    public boolean SendMessage(String msg) {
        try {
            this.out.flush();
            this.out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void StopClient() {
        try {
            this.socket.close();
            this.out.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setIsConnected(boolean value) {
        this.isConnected = value;
    }

    public boolean getIsConnected() {
        return this.isConnected;
    }
}

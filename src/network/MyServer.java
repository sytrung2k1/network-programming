/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import battleship.BattleShip;
import battleship.MyDefines;

/**
 *
 * @author Trung
 */
public class MyServer extends Thread {

    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private BattleShip gui;
    private boolean myBreak = false;
    private MyServerReceive myServerRX;
    private ObjectOutputStream out;
    private boolean hasClient = false;

    public MyServer(BattleShip gui) {
        this.gui = gui;
    }

    public void StartSocket(int port, String ip) {
        try {
            this.serverSocket = new ServerSocket(port, 0, InetAddress.getByName(ip));
        } catch (IOException e) {
            this.gui.writeOutputMessage(" - Phòng không thể mở được!");
            return;
        }

        //Thong bao viec tao socket
        this.gui.writeOutputMessage(" - Phòng đã được mở!");
        this.gui.writeOutputMessage(" - Chờ đợi kẻ địch của bạn...");
    }

    public void run() {
        //Vao vong lap chinh
        while (!this.myBreak) {
            //Nhan 1 Client dang co gang ket noi
            try {
                this.socket = serverSocket.accept();
                this.gui.writeOutputMessage(" - Kẻ địch " + this.socket.getInetAddress() + ":" + this.socket.getPort() + " muốn tấn công tàu của bạn!");

                // Open the OutputStream
                try {
                    this.out = new ObjectOutputStream(this.socket.getOutputStream());
                    this.out.flush();
                } catch (IOException e) {
                    System.out.println("Không thể mở output stream!");
                    this.gui.writeOutputMessage(" - Không thể mở output stream!");
                    return;
                }

                this.myServerRX = new MyServerReceive(this.socket, this.gui);
                this.myServerRX.start();
            } catch (IOException e) {
                System.out.println("Không thể có được một người chơi!");
            }

            // Sleep
            try {
                Thread.sleep(MyDefines.DELAY_200MS);
            } catch (InterruptedException e) {
                this.gui.writeOutputMessage(" - Lỗi Thread. Kết nối đã bị gián đoạn!");
            }
        }
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

    public void StopServer() {
        this.myBreak = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.serverSocket = null;
    }

    public void StopCommunication() {
        try {
            this.socket.close();
            this.out.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.socket = null;
    }

    public boolean hasClient() {
        if (this.socket == null) {
            return false;
        }

        return this.socket.isConnected();
    }

    public void setHasClient(boolean value) {
        this.hasClient = value;
    }

    public boolean getHasClient() {
        return this.hasClient;
    }
}

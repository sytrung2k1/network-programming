/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import battleship.BattleShip;
import battleship.MyDefines;

/**
 *
 * @author Trung
 */

/*
 * Protocol:
 * <ID>:<MSG>
 * Send IDs:
 * G - Gửi chỉ mục đã chơi
 * A - Gửi nếu trúng hay không (1: trúng; 0: không trúng)
 * DA - Gửi câu trả lời xúc xắc (số của tôi)
 * E - Trận đấu kết thúc
 * C - Kết nối đầu tiên, thông báo cho máy chủ
 * M - Tin nhắn trò chuyện
 * B - Đóng kết nối
 * 
 * Receive IDs:
 * G - nhận trong trò chơi, với vị trí bắn
 * A - nhận được trong trò chơi, nếu vị trí bắn có trúng hay không
 * DQ - thông báo để mở bộ xử lý xúc xắc (để chọn một số)
 * DA - thông báo ai sẽ bắt đầu chơi
 * DE - lỗi xúc xắc, cả hai người chơi chọn cùng một số, xúc xắc sẽ mở lại
 * E - Trận đấu kết thúc
 * S - Kết nối đầu tiên, máy chủ thông báo trò chơi có thể bắt đầu
 * SN - Kết nối lần đầu, máy chủ chưa sẵn sàng
 * M - Tin nhắn trò chuyện
 * B - Máy chủ sẽ đóng kết nối
 */
public class MyClientReceive extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private boolean myBreak = false;
    private String msg = "";
    private BattleShip gui;

    public MyClientReceive(Socket socket, BattleShip gui) {
        this.socket = socket;
        this.gui = gui;
    }

    public void run() {
        // Open the InputStream
        try {
            this.in = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Không thể lấy luồng đầu vào từ " + toString());
        }

        while (!this.myBreak) {
            try {
                this.msg = (String) this.in.readObject();
                if (!this.msg.equals("")) {
                    switch (this.msg.charAt(0)) {
                        case 'G': //tin nhan tu server ve game
                        {
                            int index = Integer.parseInt(this.msg.substring(2));
                            this.gui.writeOutputMessage(" - Kẻ địch của bạn đã tấn công bạn ở: " + this.gui.getMyMapUpdate().getLine(index) + this.gui.getMyMapUpdate().getColumn(index));
                            this.gui.getMyMapUpdate().setMyTurn(true);
                            if (this.gui.getMyMapUpdate().hitSomething(index)) {
                                this.gui.writeOutputMessage(" - Kẻ địch của bạn đã bắn trúng!");
                                this.gui.getMyClient().SendMessage("A:1;" + index);
                            } else {
                                this.gui.writeOutputMessage(" - Bạn an toàn, kẻ địch của bạn bắn trượt!");
                                this.gui.getMyClient().SendMessage("A:0;" + index);
                            }
                            this.gui.getMyMapUpdate().updatePosition(index);
                            this.gui.repaintMyBoard();
                            if (this.gui.getMyMapUpdate().isGameOver()) {
                                this.gui.writeOutputMessage(" - Bạn đã thua ván đấu này! Bạn có thể thử lại!");
                                this.gui.getGameOverGui().ShowGameOver(0);
                                this.gui.getMyClient().SendMessage("E");
                            } else {
                                this.gui.writeOutputMessage(" - Đến lượt của bạn!");
                            }
                            break;
                        }
                        case 'A': //tin nhan tu server co bi ban hay khong
                        {
                            String[] impMsg = this.msg.substring(2).split(";");
                            if (impMsg[0].equals("1")) {
                                this.gui.getMyEnemyMapUpdate().setEnemyHit(Integer.parseInt(impMsg[1]), true);
                                this.gui.writeOutputMessage(" - Làm tốt lắm, bạn đã bắn trúng kẻ địch của mình!");
                            } else {
                                this.gui.getMyEnemyMapUpdate().setEnemyHit(Integer.parseInt(impMsg[1]), false);
                                this.gui.writeOutputMessage(" - Chúc may mắn lần sau, bạn đã bắt trượt!");
                            }
                            this.gui.repaintMyEnemyBoard();
                            break;
                        }
                        case 'D': //xuc xac: noi chuyen voi may chu
                        {
                            if (this.msg.charAt(1) == 'Q') {
                                this.gui.getDiceHandler().ShowMyDice();
                                this.gui.getMyClient().SendMessage("DA:" + this.gui.getDiceHandler().getMyNumber());
                            } else if (this.msg.charAt(1) == 'A') {
                                String[] impMsg = this.msg.substring(3).split(";");
                                this.gui.getDiceHandler().setEnemyNumber(Integer.parseInt(impMsg[1]));
                                if (impMsg[0].equals("0")) {
                                    this.gui.getMyMapUpdate().setMyTurn(false);
                                    this.gui.writeOutputMessage(" - Bạn đã chọn: " + this.gui.getDiceHandler().getMyNumber() + ", và kẻ địch của bạn chọn: " + this.gui.getDiceHandler().getEnemyNumber());
                                    this.gui.writeOutputMessage(" - Kẻ địch của bạn sẽ bắt đầu trước!");
                                } else {
                                    this.gui.getMyMapUpdate().setMyTurn(true);
                                    this.gui.writeOutputMessage(" - Bạn đã chọn: " + this.gui.getDiceHandler().getMyNumber() + ", và kẻ địch của bạn chọn: " + this.gui.getDiceHandler().getEnemyNumber());
                                    this.gui.writeOutputMessage(" - Bạn sẽ bắt đầu trước!");
                                }
                            } else if (this.msg.charAt(1) == 'E') {
                                this.gui.writeOutputMessage(" - Bạn và kẻ địch của bạn chọn cùng một số!");
                                this.gui.writeOutputMessage(" - Vui lòng chọn một số khác.");
                                this.gui.getDiceHandler().ShowMyDice();
                                this.gui.getMyClient().SendMessage("DA:" + this.gui.getDiceHandler().getMyNumber());
                            }
                            break;
                        }
                        case 'E': //tro choi ket thuc
                        {
                            this.gui.writeOutputMessage(" - Xin chúc mừng! Bạn đã thắng! Nhưng hãy cẩn thận, kẻ địch của bạn có thể sẽ tìm cách trả thù!");
                            this.gui.getGameOverGui().ShowGameOver(1);
                            break;
                        }
                        case 'S': //tin nhan tu Server ket noi dau tien
                        {
                            if (this.msg.charAt(1) == 'N') {
                                this.gui.writeOutputMessage(" - Kẻ địch chưa sẵn sàng. Đang phân bố lại tàu hoặc có thể sợ bạn!");
                                this.gui.writeOutputMessage(" - Đợi một lát và thử kết nối lại!");
                                this.gui.getMyClient().StopClient();
                                this.gui.btnConnect.setEnabled(true);
                                this.gui.btnStopServer.setEnabled(false);
                            } else {
                                this.gui.writeOutputMessage(" - Kẻ địch nói: " + this.msg.substring(2));
                                this.gui.btnSend.setEnabled(true);
                                this.gui.sendTxtField.setEnabled(true);
                                this.gui.getMyClient().setIsConnected(true);
                                this.gui.enableShipAllocation(false);
                            }
                            break;
                        }
                        case 'M': //tin nhan tu chat
                        {
                            this.gui.writeChatMessage(this.msg.substring(2), 1);
                            break;
                        }
                        case 'B': //ngat ket noi
                        {
                            this.gui.getMyClient().StopClient();
                            this.myBreak = true;
                            break;
                        }
                    }
                }
            } catch (ClassNotFoundException cnf) {
                cnf.printStackTrace();
            } catch (IOException io) {
                this.myBreak = true;
            }

            // Sleep
            try {
                Thread.sleep(MyDefines.DELAY_200MS);
            } catch (Exception e) {
                System.out.println(toString() + " có đầu vào bị gián đoạn.");
            }
        }

        this.gui.writeOutputMessage(" - Phòng đã đóng!");
        this.gui.btnSend.setEnabled(false);
        this.gui.sendTxtField.setEnabled(false);
        this.gui.btnConnect.setEnabled(true);
        this.gui.btnStopServer.setEnabled(false);
        this.gui.getMyClient().setIsConnected(false);
        this.gui.enableShipAllocation(true);

        try {
            this.socket.close();
            this.in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

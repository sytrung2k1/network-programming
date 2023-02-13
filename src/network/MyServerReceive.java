/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import battleship.BattleShip;
import battleship.MyDefines;

/**
 *
 * @author Trung
 */

/*
 * Protocol
 * <ID>:<MSG>
 * Send IDs:
 * G - Gửi chỉ mục đã chơi
 * A - Gửi nếu trúng hay không (1: trúng; 0: không trúng)
 * DQ - Gửi câu hỏi xúc xắc cho khách hàng (hỏi số của khách hàng)
 * DE - Lỗi gửi xúc xắc, trùng số
 * E - Trận đấu kết thúc
 * S - Kết nối đầu tiên, thông báo cho khách hàng (sẵn sàng)
 * SN - Lần kết nối đầu tiên, thông báo cho khách hàng (chưa sẵn sàng)
 * M - Tin nhắn trò chuyện
 * B - Đóng kết nối
 * 
 * Receive IDs:
 * G - nhận trong trò chơi, với vị trí bắn
 * A - nhận được trong trò chơi, nếu vị trí bắn có trúng hay không
 * DA - phản ứng xúc xắc từ khách hàng, số kẻ thù
 * E - Trận đấu kết thúc
 * C - Kết nối đầu tiên, khách hàng thông báo đã sẵn sàng
 * M - Tin nhắn trò chuyện
 * B - Máy khách sẽ ngắt kết nối
 */
public class MyServerReceive extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private boolean myBreak = false;
    private String msg = "";
    private BattleShip gui;

    public MyServerReceive(Socket socket, BattleShip gui) {
        this.socket = socket;
        this.gui = gui;
    }

    public void run() {
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
                        case 'G': //tin nhan tu Client chi muc da choi
                        {
                            int index = Integer.parseInt(this.msg.substring(2));
                            this.gui.writeOutputMessage(" - Kẻ địch của bạn đã tấn công bạn ở: " + this.gui.getMyMapUpdate().getLine(index) + this.gui.getMyMapUpdate().getColumn(index));
                            this.gui.getMyMapUpdate().setMyTurn(true);
                            if (this.gui.getMyMapUpdate().hitSomething(index)) {
                                this.gui.writeOutputMessage(" - Kẻ địch của bạn đã bắn trúng!");
                                this.gui.getMyServer().SendMessage("A:1;" + index);
                            } else {
                                this.gui.writeOutputMessage(" - Bạn an toàn, kẻ địch của bạn bắn trượt!");
                                this.gui.getMyServer().SendMessage("A:0;" + index);
                            }
                            this.gui.getMyMapUpdate().updatePosition(index);
                            this.gui.repaintMyBoard();
                            if (this.gui.getMyMapUpdate().isGameOver()) {
                                this.gui.writeOutputMessage(" - Bạn đã thua ván đấu này! Bạn có thể thử lại!!");
                                this.gui.getGameOverGui().ShowGameOver(0);
                                this.gui.getMyServer().SendMessage("E");
                            } else {
                                this.gui.writeOutputMessage(" - Đến lượt của bạn!");
                            }
                            break;
                        }
                        case 'A': //Thong bao tu client bi danh hay khong
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
                        case 'D': //cau tra loi ve xuc xac tu client
                        {
                            if (this.msg.charAt(1) == 'A') {
                                String[] impMsg = this.msg.split(":");
                                this.gui.getDiceHandler().setEnemyNumber(Integer.parseInt(impMsg[1]));
                                if (this.gui.getDiceHandler().whoStarts() == 0) {
                                    this.gui.getMyMapUpdate().setMyTurn(true);
                                    this.gui.getMyServer().SendMessage("DA:0;" + this.gui.getDiceHandler().getMyNumber());
                                    this.gui.writeOutputMessage(" - Bạn đã chọn: " + this.gui.getDiceHandler().getMyNumber() + ", và kẻ địch của bạn chọn: " + this.gui.getDiceHandler().getEnemyNumber());
                                    this.gui.writeOutputMessage(" - Bạn sẽ bắt đầu trước!");
                                } else if (this.gui.getDiceHandler().whoStarts() == 1) {
                                    this.gui.getMyMapUpdate().setMyTurn(false);
                                    this.gui.getMyServer().SendMessage("DA:1;" + this.gui.getDiceHandler().getMyNumber());
                                    this.gui.writeOutputMessage(" - Bạn đã chọn: " + this.gui.getDiceHandler().getMyNumber() + ", và kẻ địch của bạn chọn: " + this.gui.getDiceHandler().getEnemyNumber());
                                    this.gui.writeOutputMessage(" - Kẻ địch của bạn sẽ bắt đầu trước!");
                                } else {
                                    this.gui.writeOutputMessage(" - Bạn và kẻ địch của bạn chọn cùng một số!");
                                    this.gui.writeOutputMessage(" - Vui lòng chọn một số khác!");
                                    this.gui.getDiceHandler().ShowMyDice();
                                    this.gui.getMyServer().SendMessage("DE");
                                }
                            }
                            break;
                        }
                        case 'E': //game is over
                        {
                            this.gui.writeOutputMessage(" - Xin chúc mừng! Bạn đã thắng! Nhưng hãy cẩn thận, kẻ địch của bạn có thể sẽ tìm cách trả thù!");
                            this.gui.getGameOverGui().ShowGameOver(1);
                            break;
                        }
                        case 'C': //message from client first connection
                        {
                            if (!this.gui.getMyMapUpdate().isSelectionDone()) {
                                this.gui.getMyServer().SendMessage("SN");
                                this.gui.writeOutputMessage(" - Nhanh lên! Bạn mất quá nhiều thời gian để phân bố lại tàu của mình!");
                                this.gui.writeOutputMessage(" - Kẻ địch của bạn đang cố kết nối!");
                                this.myBreak = true;
                            } else {
                                this.gui.writeOutputMessage(" - Kẻ địch nói: " + this.msg.substring(2));
                                if (!this.gui.getMyServer().SendMessage("S:Chà, bạn đã sẵn sàng để bị tiêu diệt chưa?")) {
                                    this.gui.writeOutputMessage(" - Lỗi không thể giao tiếp với kẻ địch của bạn!");
                                }
                                this.gui.btnSend.setEnabled(true);
                                this.gui.sendTxtField.setEnabled(true);
                                this.gui.getMyServer().setHasClient(true);
                                this.gui.enableShipAllocation(false);
                                this.gui.getDiceHandler().ShowMyDice();
                                this.gui.getMyServer().SendMessage("DQ");
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
                            this.myBreak = true;
                            break;
                        }
                    }
                }
            } catch (ClassNotFoundException cnf) {
                cnf.printStackTrace();
            } catch (SocketException e) {
                this.myBreak = true;
            } catch (IOException io) {
                io.printStackTrace();
            }

            // Sleep
            try {
                Thread.sleep(MyDefines.DELAY_200MS);
            } catch (Exception e) {
                System.out.println(toString() + " có đầu vào bị gián đoạn!");
            }
        }

        this.gui.getMyServer().StopCommunication();
        this.gui.writeOutputMessage(" - Phòng đã đóng!");
        this.gui.btnSend.setEnabled(false);
        this.gui.sendTxtField.setEnabled(false);
        this.gui.getMyServer().setHasClient(false);
        this.gui.enableShipAllocation(true);

        try {
            this.socket.close();
            this.in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package computer;

import javax.swing.JOptionPane;

import battleship.BattleShip;
import battleship.MapUpdateHandler;
import battleship.MyDefines;

/**
 *
 * @author Trung
 */
public class SinglePlayerHandler extends Thread {

    private BattleShip gui;
    private boolean myTurn;
    private boolean myBreak;
    private PCGameHandler myGameHandler;
    private MapUpdateHandler pcMap;

    public SinglePlayerHandler(BattleShip gui) {
        this.gui = gui;
        this.myBreak = false;
        this.myTurn = false;
        this.pcMap = new MapUpdateHandler(this.gui.getMyMapUpdate().getApplicationPath());
        this.myGameHandler = new PCGameHandler(this.gui, this.pcMap);
    }

     //Phan bo tau cua may
    public void AllocateShips() {
        int timeOutCounter = 0;

        this.pcMap.restartAllocation();

        //no co gang phan bo cho den khi tim thay 1 vi tri hop le cho tat ca cac thuyen hoac het thoi gian cho
        while (!this.pcMap.randomAllocation()) {
            try {
                //doi 100ms de thuc hien vi tri ngau nhien tot hon
                //va co bo dem len toi 5s khi het thoi gian cho 
                Thread.sleep(MyDefines.DELAY_100MS);
            } catch (java.lang.InterruptedException ie) {
                JOptionPane.showMessageDialog(this.gui, "Lỗi của Thread. Vui lòng nhấn lại \"Ván mới\".");
                this.pcMap.restartAllocation();
                return;
            }
            timeOutCounter += 100;
            //het thoi gian 5s de ngung thu
            if (timeOutCounter >= MyDefines.DELAY_5S) {
                JOptionPane.showMessageDialog(this.gui, "Lỗi hết thời gian. Vui lòng nhấn lại \"Ván mới\".");
                this.pcMap.restartAllocation();
                return;
            }
            this.pcMap.restartAllocation();
        }

        //May da san sang
        this.gui.writeOutputMessage(" - Máy nói: Tôi đã phân bố xong tàu của mình!");
        this.gui.writeOutputMessage(" - Máy nói: Đến lượt của bạn! Hãy bắt đầu ván đấu!");
    }

    //Luong cho den luot may choi
    public void run() {
        while (!this.myBreak) {
            //cho cho den luot cua minh
            if (this.myTurn) {
                this.myGameHandler.play();
                SetMyTurn(false);
            }

            // Sleep
            try {
                Thread.sleep(MyDefines.DELAY_200MS);
            } catch (InterruptedException e) {
                this.gui.writeOutputMessage(" - Lỗi của Thread. Phòng đã đóng!");
            }
        }
    }

    //stop game
    public void StopGame() {
        this.myBreak = true;
        this.gui.writeOutputMessage(" - Máy nói: Tôi sẽ đợi bạn!");
    }

    //Dat luot cua May
    public void SetMyTurn(boolean value) {
        this.myTurn = value;
    }

    //Xu ly cu ban cua user, gameover va dat luot cho may
    public void indexPlayed(int index) {
        if (this.pcMap.hitSomething(index)) {
            this.gui.getMyEnemyMapUpdate().setEnemyHit(index, true);
            this.gui.writeOutputMessage(" - Làm tốt lắm. Bạn đã bắn trúng kẻ địch của mình!");
        } else {
            this.gui.getMyEnemyMapUpdate().setEnemyHit(index, false);
            this.gui.writeOutputMessage(" - Chúc bạn may mắn lần sau, bạn đã bắn trượt!");
        }

        this.pcMap.updatePosition(index);
        if (this.pcMap.isGameOver()) {
            this.gui.getGameOverGui().ShowGameOver(1);
            this.gui.writeOutputMessage(" - Xin chúc mừng! Bạn đã thắng! Nhưng hãy cẩn thận, kẻ địch của bạn có thể sẽ tìm cách trả thù!");
        }

        SetMyTurn(true);
    }
}

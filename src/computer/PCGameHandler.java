/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package computer;

import java.util.ArrayList;
import java.util.Random;

import battleship.BattleShip;
import battleship.MapUpdateHandler;
import battleship.MyDefines;

/**
 *
 * @author Trung
 */
public class PCGameHandler {

    private BattleShip gui;
    private boolean wasHit;				//cu danh dau tien
    private boolean lastIndexWasHit;	//chi so cuoi cung co 1 lam danh
    private boolean horizontalHit;		//co 1 cu danh ngang
    private int hitIndex;				//chi muc cu danh dau tien
    private int lastIndex;				//chi muc lan choi cuoi
    private int direction; 				//huong se duoc choi (kiem tra Mydefines)

    private MapUpdateHandler pcMap; 					//computer map
    private ArrayList<Integer> reservedList = new ArrayList<Integer>();		//list with A,B,C... positions

    public PCGameHandler(BattleShip gui, MapUpdateHandler pcMap) {
        this.wasHit = false;
        this.horizontalHit = false;
        this.lastIndexWasHit = false;
        this.direction = MyDefines.NO_DIRECTION;
        this.lastIndex = -1;
        this.hitIndex = -1;
        this.gui = gui;
        this.pcMap = pcMap;

        //sao chep danh sach danh rieng tu class MyDefines (chi su dung phuong thuc contains)
        for (int i = 0; i < MyDefines.RESERVED_LIST.length; i++) {
            this.reservedList.add(MyDefines.RESERVED_LIST[i]);
        }
    }

    //xu ly cu danh cua may
    public void play() {
        int index = -1;

        //neu khong co cu danh nao, hay tao chi muc ngau nhien
        if (!this.wasHit) {
            index = getNewRandomNumber();
        } //da co 1 cu danh
        else {
            //di sang trai
            if (this.direction == MyDefines.LEFT) {
                //kiem tra xem vi tri cuoi cung co phai la da danh hay khong
                if (this.lastIndexWasHit) {
                    //neu no da danh, tiep tuc di ben trai
                    index = this.lastIndex - 1;
                    if (!isValidIndex(index)){ //LEFT
                        //khong co vi tri co san ben trai, di ben phai
                        index = this.hitIndex + 1;
                        if (!isValidIndex(index)){ //right
                            //khong co vi tri ben phai, kiem tra xem co bi danh ngang khong
                            if (this.horizontalHit) {
                                //danh ngang dung, may khong can tra doc
                                //tat ca cac chieu ngang da duwoc danh (ca trai va phai), no co the bat dau lai voi 1 so ngau nhien
                                this.horizontalHit = false;
                                index = getNewRandomNumber();
                                this.direction = MyDefines.NO_DIRECTION;
                                this.wasHit = false;
                            }//khong phai la 1 cu danh ngang
                            else {
                                //thu di xuong
                                index = this.hitIndex + 11;
                                if (!isValidIndex(index)){ //down
                                    //khong ci vi tri di xuong, co gang di len
                                    index = this.hitIndex - 11;
                                    if (!isValidIndex(index)){ //up
                                        //khong co vi tri co san, bat dau lai voi 1 so ngau nhien
                                        index = getNewRandomNumber();
                                        this.direction = MyDefines.NO_DIRECTION;
                                        this.wasHit = false;
                                    } else {
                                        this.direction = MyDefines.UP;
                                    }
                                } else {
                                    this.direction = MyDefines.DOWN;
                                }
                            }
                        } else {
                            this.direction = MyDefines.RIGHT;
                        }
                    }
                } //vi tri cuoi cung khong phai la 1 cu danh
                else {
                    //dung di sang trai va phai
                    index = this.hitIndex + 1;
                    if (!isValidIndex(index)){ //right
                        //khong ci vi tri ben phai, khiem tra danh ngang
                        if (this.horizontalHit) {
                            //co 1 cu danh ngang, da kiem tra tat ca cac vi tri (trai va phai), khong can phai di len va di xuong
                            this.horizontalHit = false;
                            index = getNewRandomNumber();
                            this.direction = MyDefines.NO_DIRECTION;
                            this.wasHit = false;
                        } //khong danh ngang
                        else {
                            //co gang di xuong
                            index = this.hitIndex + 11;
                            if (!isValidIndex(index)) //down
                            {
                                //khong co vi tri di xuong, thu di len
                                index = this.hitIndex - 11;
                                if (!isValidIndex(index)){ //up
                                    //khong the di len, bat dau lai voi so ngau nhien
                                    index = getNewRandomNumber();
                                    this.direction = MyDefines.NO_DIRECTION;
                                    this.wasHit = false;
                                } else {
                                    this.direction = MyDefines.UP;
                                }
                            } else {
                                this.direction = MyDefines.DOWN;
                            }
                        }
                    } else {
                        this.direction = MyDefines.RIGHT;
                    }
                }
            } //tuong tu voi MyDefines.LEFT
            else if (this.direction == MyDefines.RIGHT) {
                if (this.lastIndexWasHit) {
                    index = this.lastIndex + 1;
                    if (!isValidIndex(index)){ //RIGHT
                        if (this.horizontalHit) {
                            this.horizontalHit = false;
                            index = getNewRandomNumber();
                            this.direction = MyDefines.NO_DIRECTION;
                            this.wasHit = false;
                        } else {
                            index = this.hitIndex + 11;
                            if (!isValidIndex(index)){ //down
                                index = this.hitIndex - 11;
                                if (!isValidIndex(index)){ //up
                                    index = getNewRandomNumber();
                                    this.direction = MyDefines.NO_DIRECTION;
                                    this.wasHit = false;
                                } else {
                                    this.direction = MyDefines.UP;
                                }
                            } else {
                                this.direction = MyDefines.DOWN;
                            }
                        }
                    }
                } else {
                    if (this.horizontalHit) {
                        this.horizontalHit = false;
                        index = getNewRandomNumber();
                        this.direction = MyDefines.NO_DIRECTION;
                        this.wasHit = false;
                    } else {
                        index = this.hitIndex + 11;
                        if (!isValidIndex(index)){ //down
                            index = this.hitIndex - 11;
                            if (!isValidIndex(index)){ //up
                                index = getNewRandomNumber();
                                this.direction = MyDefines.NO_DIRECTION;
                                this.wasHit = false;
                            } else {
                                this.direction = MyDefines.UP;
                            }
                        } else {
                            this.direction = MyDefines.DOWN;
                        }
                    }
                }
            } //tuong tu voi MyDefines.LEFT
            else if (this.direction == MyDefines.DOWN) {
                if (this.lastIndexWasHit) {
                    index = this.lastIndex + 11;
                    if (!isValidIndex(index)){ //DOWN
                        index = this.hitIndex - 11;
                        if (!isValidIndex(index)){ //UP
                            index = getNewRandomNumber();
                            this.direction = MyDefines.NO_DIRECTION;
                            this.wasHit = false;
                        } else {
                            this.direction = MyDefines.UP;
                        }
                    }
                } else {
                    index = this.hitIndex - 11;
                    if (!isValidIndex(index)){ //up
                        index = getNewRandomNumber();
                        this.direction = MyDefines.NO_DIRECTION;
                        this.wasHit = false;
                    } else {
                        this.direction = MyDefines.UP;
                    }
                }
            } //tuong tu voi MyDefines.LEFT
            else if (this.direction == MyDefines.UP) {
                index = this.lastIndex - 11;
                if (!isValidIndex(index)){ //up
                    index = getNewRandomNumber();
                    this.direction = MyDefines.NO_DIRECTION;
                    this.wasHit = false;
                }
            }
        }

        this.pcMap.addPlayedPosition(index);

        this.gui.writeOutputMessage(" - Quả bom của Máy bắn tới: " + this.gui.getMyMapUpdate().getLine(index) + this.gui.getMyMapUpdate().getColumn(index));

        this.gui.getMyMapUpdate().setMyTurn(true);
        if (this.gui.getMyMapUpdate().hitSomething(index)) {
            this.gui.writeOutputMessage(" - Máy bắn trúng bạn!");

            this.wasHit = true;		//dat da danh trung la true
            this.lastIndex = index; //luu tru chi muc

            if (this.direction == MyDefines.LEFT || this.direction == MyDefines.RIGHT) //if the direction is right or left, it means there was a horizontal hit
            {
                this.horizontalHit = true;
            }

            //khong xac dinh huong co nghia la: lan ban dau tien hoac tat ca cac duong co the co xung quanh 1 chiec thuyen da duoc kiem tra
            //bat dau luon o ben trai va luu tru o vi tri da ban
            if (this.direction == MyDefines.NO_DIRECTION) {
                this.hitIndex = index;
                this.direction = MyDefines.LEFT;
            }

            //chi muc cuoi cung la 1 cu danh
            this.lastIndexWasHit = true;
        } else {
            this.lastIndex = index;	//luu tru chi muc
            this.gui.writeOutputMessage(" - Bạn ao toàn, Máy đã bắn trượt!");
            this.lastIndexWasHit = false; //chi muc cuoi cung khong phai la 1 cu danh
        }

        this.gui.getMyMapUpdate().updatePosition(index);
        this.gui.repaintMyBoard();

        if (this.gui.getMyMapUpdate().isGameOver()) {
            this.gui.writeOutputMessage(" - Bạn đã thua. Bạn có thể thử lại!");
            this.gui.getGameOverGui().ShowGameOver(0);
        } else {
            this.gui.writeOutputMessage(" - Đến lượt của bạn!");
        }
    }

    //tao chi muc ngau nhien
    private int getNewRandomNumber() {
        Random r = new Random(System.currentTimeMillis()); //tao chi muc
        int index = -1;

        while (index == -1) {
            //gioi han cua index la 120
            index = r.nextInt(120);

            //kiem tra xem chi muc da tao co nam trong danh sach danh rieng khong, neu co tao chi muc moi, neu khong thi them vao danh sach
            if (isValidIndex(index)) {
                break;
            } else {
                index = -1;
            }
        }

        return index;
    }

    //kiem tra xem chi muc co hop le khong
    private boolean isValidIndex(int index) {
        if (this.reservedList.contains(index) || this.pcMap.positionPlayed(index)) {
            return false;
        }
        if (index < 0 || index > (MyDefines.NAME_LIST.length - 1)) {
            return false;
        }

        return true;
    }
}

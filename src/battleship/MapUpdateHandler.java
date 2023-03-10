/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author Trung
 */

/* Matrix Map Help <chi muc hinh anh>
 * -1 - Water
 *  0 - Water hit clear
 *  1 - Water hit something
 *  2 - Tau tuan tra (Phan 1, ngang)
 *  3 - Tau tuan tra (Phan 2, ngang)
 *  4 - Tau tuan tra (Phan 1, doc)
 *  5 - Tau tuan tra (Phan 2, doc)
 *  6 - Tau tuan tra bi danh trung (Phan 1, ngang)
 *  7 - Tau tuan tra bi danh trung (Phan 2, ngang)
 *  8 - Tau tuan tra bi danh trung (Phan 1, doc)
 *  9 - Tau tuan tra bi danh trung (Phan 2, doc)
 *  10 - Tau khu truc (Phan 1, ngang)
 *  11 - Tau khu truc (Phan 2, ngang)
 *  12 - Tau khu truc (Phan 3, ngang)
 *  13 - Tau khu truc (Phan 1, doc)
 *  14 - Tau khu truc (Phan 2, doc)
 *  15 - Tau khu truc (Phan 3, doc)
 *  16 - Tau khu truc bi danh trung (Phan 1, ngang)
 *  17 - Tau khu truc bi danh trung (Phan 2, ngang)
 *  18 - Tau khu truc bi danh trung (Phan 3, ngang)
 *  19 - Tau khu truc bi danh trung (Phan 1, doc)
 *  20 - Tau khu truc bi danh trung (Phan 2, doc)
 *  21 - Tau khu truc bi danh trung (Phan 3, doc)
 *  22 - Tau ngam (Phan 1, ngang)
 *  23 - Tau ngam (Phan 2, ngang)
 *  24 - Tau ngam (Phan 3, ngang)
 *  25 - Tau ngam (Phan 1, doc)
 *  26 - Tau ngam (Phan 2, doc)
 *  27 - Tau ngam (Phan 3, doc)
 *  28 - Tau ngam bi danh trung (Phan 1, ngang)
 *  29 - Tau ngam bi danh trung (Phan 2, ngang)
 *  30 - Tau ngam bi danh trung (Phan 3, ngang)
 *  31 - Tau ngam bi danh trung (Phan 1, doc)
 *  32 - Tau ngam bi danh trung (Phan 2, doc)
 *  33 - Tau ngam bi danh trung (Phan 3, doc)
 *  34 - Tau chien (Phan 1, ngang)
 *  35 - Tau chien (Phan 2, ngang)
 *  36 - Tau chien (Phan 3, ngang)
 *  37 - Tau chien (Phan 4, ngang)
 *  38 - Tau chien (Phan 1, doc)
 *  39 - Tau chien (Phan 2, doc)
 *  40 - Tau chien (Phan 3, doc)
 *  41 - Tau chien (Phan 4, doc)
 *  42 - Tau chien bi danh trung (Phan 1, ngang)
 *  43 - Tau chien bi danh trung (Phan 2, ngang)
 *  44 - Tau chien bi danh trung (Phan 3, ngang)
 *  45 - Tau chien bi danh trung (Phan 4, ngang)
 *  46 - Tau chien bi danh trung (Phan 1, doc)
 *  47 - Tau chien bi danh trung (Phan 2, doc)
 *  48 - Tau chien bi danh trung (Phan 3, doc)
 *  49 - Tau chien bi danh trung (Phan 4, doc)
 *  50 - Tau san bay (Phan 1, ngang)
 *  51 - Tau san bay (Phan 2, ngang)
 *  52 - Tau san bay (Phan 3, ngang)
 *  53 - Tau san bay (Phan 4, ngang)
 *  54 - Tau san bay (Phan 5, ngang)
 *  55 - Tau san bay (Phan 1, doc)
 *  56 - Tau san bay (Phan 2, doc)
 *  57 - Tau san bay (Phan 3, doc)
 *  58 - Tau san bay (Phan 4, doc)
 *  59 - Tau san bay (Phan 5, doc)
 *  60 - Tau san bay bi danh trung (Phan 1, ngang)
 *  61 - Tau san bay bi danh trung (Phan 2, ngang)
 *  62 - Tau san bay bi danh trung (Phan 3, ngang)
 *  63 - Tau san bay bi danh trung (Phan 4, ngang)
 *  64 - Tau san bay bi danh trung (Phan 5, ngang)
 *  65 - Tau san bay bi danh trung (Phan 1, doc)
 *  66 - Tau san bay bi danh trung (Phan 2, doc)
 *  67 - Tau san bay bi danh trung (Phan 3, doc)
 *  68 - Tau san bay bi danh trung (Phan 4, doc)
 *  69 - Tau san bay bi danh trung (Phan 5, doc)
 */
public class MapUpdateHandler {

    private int boatType;

    //map 11*11 = 121 phan tu
    private Integer[] MatrixMap = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    private String applicationPath;

    //image map <name to image>
    private Map<String, ImageIcon> map = new HashMap<>();

    //image map <index to name>
    private Map<Integer, String> helpMap = new HashMap<>();

    //danh sach co cac vi tri de tranh
    //danh sach danh rieng
    private ArrayList<Integer> reservedList = new ArrayList<Integer>();		//list with A,B,C... positions
    //danh sach le ben phai (de thuyen khong di qua gioi han)
    private ArrayList<Integer> rightBorderList = new ArrayList<Integer>();  //list with board corner
    //danh sach ca vi tri da choi
    private ArrayList<Integer> playedPositions = new ArrayList<Integer>();  //list with positions already played

    //da phan bo thuyen xong
    private boolean selectionDone;

    //luot nguoi choi
    private boolean myTurn = false;

    public MapUpdateHandler(String appPath) {
        //duong dan toi file hinh anh
        this.applicationPath = appPath;

        //bat dau voi Tau tuan tra
        this.boatType = MyDefines.PATROL_BOAT;

        //luc dau viec lua chon khong duoc thuc hien
        this.selectionDone = false;

        //sao chep danh sach danh rieng tu MyDefines class ( chi su dung phuong thuc contains)
        for (int i = 0; i < MyDefines.RESERVED_LIST.length; i++) {
            this.reservedList.add(MyDefines.RESERVED_LIST[i]);
        }

        //sao chep danh sach le ben phai tu MyDefines class ( chi su dung phuong thuc contains)
        for (int i = 0; i < MyDefines.BORDER_RIGHT_LIST.length; i++) {
            this.rightBorderList.add(MyDefines.BORDER_RIGHT_LIST[i]);
        }

        //tao image map <name,image> va <image index, name>
        //de biet image index hay xem Matrix Map Help o dau trang
        createImageMap();
    }

    //tao image map de su dung tren man hinh cap nhap
    private void createImageMap() {
        //hinh anh nuoc
        this.map.put("WATER", new ImageIcon(this.applicationPath + "water.png"));
        this.map.put("WATER_HIT_CLEAR", new ImageIcon(this.applicationPath + "water_hit_clear.png"));
        this.map.put("WATER_HIT_STH", new ImageIcon(this.applicationPath + "water_hit_something.png"));

        this.helpMap.put(-1, "WATER");
        this.helpMap.put(0, "WATER_HIT_CLEAR");
        this.helpMap.put(1, "WATER_HIT_STH");

        //hinh anh Tau tuan tra
        this.map.put("PATROL_BOAT_1", new ImageIcon(this.applicationPath + "boat2\\boat_2_1.png"));
        this.map.put("PATROL_BOAT_2", new ImageIcon(this.applicationPath + "boat2\\boat_2_2.png"));
        this.map.put("PATROL_BOAT_1_V", new ImageIcon(this.applicationPath + "boat2\\boat_2_1_v.png"));
        this.map.put("PATROL_BOAT_2_V", new ImageIcon(this.applicationPath + "boat2\\boat_2_2_v.png"));
        this.map.put("PATROL_BOAT_HIT_1", new ImageIcon(this.applicationPath + "boat2\\boat_2_1_hit.png"));
        this.map.put("PATROL_BOAT_HIT_2", new ImageIcon(this.applicationPath + "boat2\\boat_2_2_hit.png"));
        this.map.put("PATROL_BOAT_HIT_1_V", new ImageIcon(this.applicationPath + "boat2\\boat_2_1_hit_v.png"));
        this.map.put("PATROL_BOAT_HIT_2_V", new ImageIcon(this.applicationPath + "boat2\\boat_2_2_hit_v.png"));

        this.helpMap.put(2, "PATROL_BOAT_1");
        this.helpMap.put(3, "PATROL_BOAT_2");
        this.helpMap.put(4, "PATROL_BOAT_1_V");
        this.helpMap.put(5, "PATROL_BOAT_2_V");
        this.helpMap.put(6, "PATROL_BOAT_HIT_1");
        this.helpMap.put(7, "PATROL_BOAT_HIT_2");
        this.helpMap.put(8, "PATROL_BOAT_HIT_1_V");
        this.helpMap.put(9, "PATROL_BOAT_HIT_2_V");

        //Hinh anh Tau khu truc
        this.map.put("DESTROYER_1", new ImageIcon(this.applicationPath + "boat3_1\\boat_31_1.png"));
        this.map.put("DESTROYER_2", new ImageIcon(this.applicationPath + "boat3_1\\boat_31_2.png"));
        this.map.put("DESTROYER_3", new ImageIcon(this.applicationPath + "boat3_1\\boat_31_3.png"));
        this.map.put("DESTROYER_1_V", new ImageIcon(this.applicationPath + "boat3_1\\boat_31_1_v.png"));
        this.map.put("DESTROYER_2_V", new ImageIcon(this.applicationPath + "boat3_1\\boat_31_2_v.png"));
        this.map.put("DESTROYER_3_V", new ImageIcon(this.applicationPath + "boat3_1\\boat_31_3_v.png"));
        this.map.put("DESTROYER_HIT_1", new ImageIcon(this.applicationPath + "boat3_1\\boat_31_1_hit.png"));
        this.map.put("DESTROYER_HIT_2", new ImageIcon(this.applicationPath + "boat3_1\\boat_31_2_hit.png"));
        this.map.put("DESTROYER_HIT_3", new ImageIcon(this.applicationPath + "boat3_1\\boat_31_3_hit.png"));
        this.map.put("DESTROYER_HIT_1_V", new ImageIcon(this.applicationPath + "boat3_1\\boat_31_1_hit_v.png"));
        this.map.put("DESTROYER_HIT_2_V", new ImageIcon(this.applicationPath + "boat3_1\\boat_31_2_hit_v.png"));
        this.map.put("DESTROYER_HIT_3_V", new ImageIcon(this.applicationPath + "boat3_1\\boat_31_3_hit_v.png"));

        this.helpMap.put(10, "DESTROYER_1");
        this.helpMap.put(11, "DESTROYER_2");
        this.helpMap.put(12, "DESTROYER_3");
        this.helpMap.put(13, "DESTROYER_1_V");
        this.helpMap.put(14, "DESTROYER_2_V");
        this.helpMap.put(15, "DESTROYER_3_V");
        this.helpMap.put(16, "DESTROYER_HIT_1");
        this.helpMap.put(17, "DESTROYER_HIT_2");
        this.helpMap.put(18, "DESTROYER_HIT_3");
        this.helpMap.put(19, "DESTROYER_HIT_1_V");
        this.helpMap.put(20, "DESTROYER_HIT_2_V");
        this.helpMap.put(21, "DESTROYER_HIT_3_V");

        //hinh anh Tau ngam
        this.map.put("SUBMARINE_1", new ImageIcon(this.applicationPath + "boat3_2\\boat_32_1.png"));
        this.map.put("SUBMARINE_2", new ImageIcon(this.applicationPath + "boat3_2\\boat_32_2.png"));
        this.map.put("SUBMARINE_3", new ImageIcon(this.applicationPath + "boat3_2\\boat_32_3.png"));
        this.map.put("SUBMARINE_1_V", new ImageIcon(this.applicationPath + "boat3_2\\boat_32_1_v.png"));
        this.map.put("SUBMARINE_2_V", new ImageIcon(this.applicationPath + "boat3_2\\boat_32_2_v.png"));
        this.map.put("SUBMARINE_3_V", new ImageIcon(this.applicationPath + "boat3_2\\boat_32_3_v.png"));
        this.map.put("SUBMARINE_HIT_1", new ImageIcon(this.applicationPath + "boat3_2\\boat_32_1_hit.png"));
        this.map.put("SUBMARINE_HIT_2", new ImageIcon(this.applicationPath + "boat3_2\\boat_32_2_hit.png"));
        this.map.put("SUBMARINE_HIT_3", new ImageIcon(this.applicationPath + "boat3_2\\boat_32_3_hit.png"));
        this.map.put("SUBMARINE_HIT_1_V", new ImageIcon(this.applicationPath + "boat3_2\\boat_32_1_hit_v.png"));
        this.map.put("SUBMARINE_HIT_2_V", new ImageIcon(this.applicationPath + "boat3_2\\boat_32_2_hit_v.png"));
        this.map.put("SUBMARINE_HIT_3_V", new ImageIcon(this.applicationPath + "boat3_2\\boat_32_3_hit_v.png"));

        this.helpMap.put(22, "SUBMARINE_1");
        this.helpMap.put(23, "SUBMARINE_2");
        this.helpMap.put(24, "SUBMARINE_3");
        this.helpMap.put(25, "SUBMARINE_1_V");
        this.helpMap.put(26, "SUBMARINE_2_V");
        this.helpMap.put(27, "SUBMARINE_3_V");
        this.helpMap.put(28, "SUBMARINE_HIT_1");
        this.helpMap.put(29, "SUBMARINE_HIT_2");
        this.helpMap.put(30, "SUBMARINE_HIT_3");
        this.helpMap.put(31, "SUBMARINE_HIT_1_V");
        this.helpMap.put(32, "SUBMARINE_HIT_2_V");
        this.helpMap.put(33, "SUBMARINE_HIT_3_V");

        //Hinh anh Tau chien
        this.map.put("BATTLESHIP_1", new ImageIcon(this.applicationPath + "boat4\\boat_4_1.png"));
        this.map.put("BATTLESHIP_2", new ImageIcon(this.applicationPath + "boat4\\boat_4_2.png"));
        this.map.put("BATTLESHIP_3", new ImageIcon(this.applicationPath + "boat4\\boat_4_3.png"));
        this.map.put("BATTLESHIP_4", new ImageIcon(this.applicationPath + "boat4\\boat_4_4.png"));
        this.map.put("BATTLESHIP_1_V", new ImageIcon(this.applicationPath + "boat4\\boat_4_1_v.png"));
        this.map.put("BATTLESHIP_2_V", new ImageIcon(this.applicationPath + "boat4\\boat_4_2_v.png"));
        this.map.put("BATTLESHIP_3_V", new ImageIcon(this.applicationPath + "boat4\\boat_4_3_v.png"));
        this.map.put("BATTLESHIP_4_V", new ImageIcon(this.applicationPath + "boat4\\boat_4_4_v.png"));
        this.map.put("BATTLESHIP_HIT_1", new ImageIcon(this.applicationPath + "boat4\\boat_4_1_hit.png"));
        this.map.put("BATTLESHIP_HIT_2", new ImageIcon(this.applicationPath + "boat4\\boat_4_2_hit.png"));
        this.map.put("BATTLESHIP_HIT_3", new ImageIcon(this.applicationPath + "boat4\\boat_4_3_hit.png"));
        this.map.put("BATTLESHIP_HIT_4", new ImageIcon(this.applicationPath + "boat4\\boat_4_4_hit.png"));
        this.map.put("BATTLESHIP_HIT_1_V", new ImageIcon(this.applicationPath + "boat4\\boat_4_1_hit_v.png"));
        this.map.put("BATTLESHIP_HIT_2_V", new ImageIcon(this.applicationPath + "boat4\\boat_4_2_hit_v.png"));
        this.map.put("BATTLESHIP_HIT_3_V", new ImageIcon(this.applicationPath + "boat4\\boat_4_3_hit_v.png"));
        this.map.put("BATTLESHIP_HIT_4_V", new ImageIcon(this.applicationPath + "boat4\\boat_4_4_hit_v.png"));

        this.helpMap.put(34, "BATTLESHIP_1");
        this.helpMap.put(35, "BATTLESHIP_2");
        this.helpMap.put(36, "BATTLESHIP_3");
        this.helpMap.put(37, "BATTLESHIP_4");
        this.helpMap.put(38, "BATTLESHIP_1_V");
        this.helpMap.put(39, "BATTLESHIP_2_V");
        this.helpMap.put(40, "BATTLESHIP_3_V");
        this.helpMap.put(41, "BATTLESHIP_4_V");
        this.helpMap.put(42, "BATTLESHIP_HIT_1");
        this.helpMap.put(43, "BATTLESHIP_HIT_2");
        this.helpMap.put(44, "BATTLESHIP_HIT_3");
        this.helpMap.put(45, "BATTLESHIP_HIT_4");
        this.helpMap.put(46, "BATTLESHIP_HIT_1_V");
        this.helpMap.put(47, "BATTLESHIP_HIT_2_V");
        this.helpMap.put(48, "BATTLESHIP_HIT_3_V");
        this.helpMap.put(49, "BATTLESHIP_HIT_4_V");

        //Hinh anh Tau san bay
        this.map.put("AIRCRAFT_CARRIER_1", new ImageIcon(this.applicationPath + "boat5\\boat_5_1.png"));
        this.map.put("AIRCRAFT_CARRIER_2", new ImageIcon(this.applicationPath + "boat5\\boat_5_2.png"));
        this.map.put("AIRCRAFT_CARRIER_3", new ImageIcon(this.applicationPath + "boat5\\boat_5_3.png"));
        this.map.put("AIRCRAFT_CARRIER_4", new ImageIcon(this.applicationPath + "boat5\\boat_5_4.png"));
        this.map.put("AIRCRAFT_CARRIER_5", new ImageIcon(this.applicationPath + "boat5\\boat_5_5.png"));
        this.map.put("AIRCRAFT_CARRIER_1_V", new ImageIcon(this.applicationPath + "boat5\\boat_5_1_v.png"));
        this.map.put("AIRCRAFT_CARRIER_2_V", new ImageIcon(this.applicationPath + "boat5\\boat_5_2_v.png"));
        this.map.put("AIRCRAFT_CARRIER_3_V", new ImageIcon(this.applicationPath + "boat5\\boat_5_3_v.png"));
        this.map.put("AIRCRAFT_CARRIER_4_V", new ImageIcon(this.applicationPath + "boat5\\boat_5_4_v.png"));
        this.map.put("AIRCRAFT_CARRIER_5_V", new ImageIcon(this.applicationPath + "boat5\\boat_5_5_v.png"));
        this.map.put("AIRCRAFT_CARRIER_HIT_1", new ImageIcon(this.applicationPath + "boat5\\boat_5_1_hit.png"));
        this.map.put("AIRCRAFT_CARRIER_HIT_2", new ImageIcon(this.applicationPath + "boat5\\boat_5_2_hit.png"));
        this.map.put("AIRCRAFT_CARRIER_HIT_3", new ImageIcon(this.applicationPath + "boat5\\boat_5_3_hit.png"));
        this.map.put("AIRCRAFT_CARRIER_HIT_4", new ImageIcon(this.applicationPath + "boat5\\boat_5_4_hit.png"));
        this.map.put("AIRCRAFT_CARRIER_HIT_5", new ImageIcon(this.applicationPath + "boat5\\boat_5_5_hit.png"));
        this.map.put("AIRCRAFT_CARRIER_HIT_1_V", new ImageIcon(this.applicationPath + "boat5\\boat_5_1_hit_v.png"));
        this.map.put("AIRCRAFT_CARRIER_HIT_2_V", new ImageIcon(this.applicationPath + "boat5\\boat_5_2_hit_v.png"));
        this.map.put("AIRCRAFT_CARRIER_HIT_3_V", new ImageIcon(this.applicationPath + "boat5\\boat_5_3_hit_v.png"));
        this.map.put("AIRCRAFT_CARRIER_HIT_4_V", new ImageIcon(this.applicationPath + "boat5\\boat_5_4_hit_v.png"));
        this.map.put("AIRCRAFT_CARRIER_HIT_5_V", new ImageIcon(this.applicationPath + "boat5\\boat_5_5_hit_v.png"));

        this.helpMap.put(50, "AIRCRAFT_CARRIER_1");
        this.helpMap.put(51, "AIRCRAFT_CARRIER_2");
        this.helpMap.put(52, "AIRCRAFT_CARRIER_3");
        this.helpMap.put(53, "AIRCRAFT_CARRIER_4");
        this.helpMap.put(54, "AIRCRAFT_CARRIER_5");
        this.helpMap.put(55, "AIRCRAFT_CARRIER_1_V");
        this.helpMap.put(56, "AIRCRAFT_CARRIER_2_V");
        this.helpMap.put(57, "AIRCRAFT_CARRIER_3_V");
        this.helpMap.put(58, "AIRCRAFT_CARRIER_4_V");
        this.helpMap.put(59, "AIRCRAFT_CARRIER_5_V");
        this.helpMap.put(60, "AIRCRAFT_CARRIER_HIT_1");
        this.helpMap.put(61, "AIRCRAFT_CARRIER_HIT_2");
        this.helpMap.put(62, "AIRCRAFT_CARRIER_HIT_3");
        this.helpMap.put(63, "AIRCRAFT_CARRIER_HIT_4");
        this.helpMap.put(64, "AIRCRAFT_CARRIER_HIT_5");
        this.helpMap.put(65, "AIRCRAFT_CARRIER_HIT_1_V");
        this.helpMap.put(66, "AIRCRAFT_CARRIER_HIT_2_V");
        this.helpMap.put(67, "AIRCRAFT_CARRIER_HIT_3_V");
        this.helpMap.put(68, "AIRCRAFT_CARRIER_HIT_4_V");
        this.helpMap.put(69, "AIRCRAFT_CARRIER_HIT_5_V");
    }

    //return image map (can thiet cho trinh ket xuat)
    public Map<String, ImageIcon> getImageMap() {
        return this.map;
    }

    //return image map index (can thiet cho trinh ket xuat)
    public Map<Integer, String> getImageMapHelp() {
        return this.helpMap;
    }

    //return application path
    public String getApplicationPath() {
        return this.applicationPath;
    }

    //return matrix map (map indexes)
    public Integer[] getMatrixMap() {
        return this.MatrixMap;
    }

    //khoi dong lai map cho phan bo moi
    public void restartAllocation() {
        //luon bat dau voi tau tuan tra
        this.boatType = MyDefines.PATROL_BOAT;

        //dat toan bo map thanh -1 (khong them gi)
        for (int i = 0; i < this.MatrixMap.length; i++) {
            this.MatrixMap[i] = -1;
        }

        //lua chon dien ra sai (chua duoc phan bo)
        this.selectionDone = false;

        ///game bi dung, khong ai co the choi
        this.myTurn = false;

        //khoi dong lai cai vi tri da choi
        this.playedPositions.clear();
    }

    //kiem tra loai thuyen nao dang duoc su dung
    //duoc su dung de kiem tra xem no da ket thuc chua va de danh dau vi tri (khi pha nbo thu cong dang hoat dong)
    public int getBoatType() {
        return this.boatType;
    }

    //kiem tra xem viec phan bo da hoan thanh chua
    public boolean isSelectionDone() {
        return this.selectionDone;
    }

    //cap nhat map voi thuyen moi duoc them vao
    //kiem tra xem vi tri co duoc phep hay khong va tra ve true neu phan bo thanh cong vaf false (phai chon vi tri khac)
    public boolean updateMap(int orientation, ArrayList<Integer> indexes) {
        //kiem tra cac vi tri co san
        for (int i = 0; i < indexes.size(); i++) {
            //neu vi bat ki ly do nao chi muc nho hon ), tra ve false
            if (indexes.get(i) < 0) {
                return false;
            }
            //kiem tra xem chi muc co thuoc danh sach danh rieng khong (khong duoc su dung)
            if (this.reservedList.contains(indexes.get(i))) {
                return false;
            }
        }

        //them tau tuan tra theo dinh huong va di den tau khu truc
        if (this.boatType == MyDefines.PATROL_BOAT) {
            if (orientation == MyDefines.ORIENTATION_HORIZONTAL) {
                this.MatrixMap[indexes.get(0)] = 2;
                this.MatrixMap[indexes.get(1)] = 3;
            } else {
                this.MatrixMap[indexes.get(0)] = 4;
                this.MatrixMap[indexes.get(1)] = 5;
            }
            this.boatType = MyDefines.DESTROYER;
        } //them tau khu truc theo dinh huong va di den tau ngam
        else if (this.boatType == MyDefines.DESTROYER) {
            if (orientation == MyDefines.ORIENTATION_HORIZONTAL) {
                this.MatrixMap[indexes.get(0)] = 10;
                this.MatrixMap[indexes.get(1)] = 11;
                this.MatrixMap[indexes.get(2)] = 12;
            } else {
                this.MatrixMap[indexes.get(0)] = 13;
                this.MatrixMap[indexes.get(1)] = 14;
                this.MatrixMap[indexes.get(2)] = 15;
            }
            this.boatType = MyDefines.SUBMARINE;
        } //them tau ngam theo dinh huong va di den tau chien
        else if (this.boatType == MyDefines.SUBMARINE) {
            if (orientation == MyDefines.ORIENTATION_HORIZONTAL) {
                this.MatrixMap[indexes.get(0)] = 22;
                this.MatrixMap[indexes.get(1)] = 23;
                this.MatrixMap[indexes.get(2)] = 24;
            } else {
                this.MatrixMap[indexes.get(0)] = 25;
                this.MatrixMap[indexes.get(1)] = 26;
                this.MatrixMap[indexes.get(2)] = 27;
            }
            this.boatType = MyDefines.BATTLESHIP;
        } //them tau chien theo dinh huong va di den tau san bay
        else if (this.boatType == MyDefines.BATTLESHIP) {
            if (orientation == MyDefines.ORIENTATION_HORIZONTAL) {
                this.MatrixMap[indexes.get(0)] = 34;
                this.MatrixMap[indexes.get(1)] = 35;
                this.MatrixMap[indexes.get(2)] = 36;
                this.MatrixMap[indexes.get(3)] = 37;
            } else {
                this.MatrixMap[indexes.get(0)] = 38;
                this.MatrixMap[indexes.get(1)] = 39;
                this.MatrixMap[indexes.get(2)] = 40;
                this.MatrixMap[indexes.get(3)] = 41;
            }
            this.boatType = MyDefines.AIRCRAFT_CARRIER;
        } //them tau san bay theo dinh huong va lua chon xong
        else if (this.boatType == MyDefines.AIRCRAFT_CARRIER) {
            if (orientation == MyDefines.ORIENTATION_HORIZONTAL) {
                this.MatrixMap[indexes.get(0)] = 50;
                this.MatrixMap[indexes.get(1)] = 51;
                this.MatrixMap[indexes.get(2)] = 52;
                this.MatrixMap[indexes.get(3)] = 53;
                this.MatrixMap[indexes.get(4)] = 54;
            } else {
                this.MatrixMap[indexes.get(0)] = 55;
                this.MatrixMap[indexes.get(1)] = 56;
                this.MatrixMap[indexes.get(2)] = 57;
                this.MatrixMap[indexes.get(3)] = 58;
                this.MatrixMap[indexes.get(4)] = 59;
            }
            this.selectionDone = true;
        }

        return true;
    }

    //khi muon phan bo tu dong - ngau nhien
    public boolean randomAllocation() {
        ArrayList<Integer> myListIndex = new ArrayList<>();
        ArrayList<Integer> myListOrientation = new ArrayList<>();

        //seed duoc su dung mili giay cua he thong de cung cap nhieu so ngau nhien hon
        Random r1 = new Random(System.currentTimeMillis()); //tao chi muc
        Random r2 = new Random(System.currentTimeMillis()); //tao dinh huong

        //tao 5 chi muc ngau nhien (cho moi thuyen) va dinh huong ngau nhien
        //day la cac chi so noi thuyen duoc dat
        while (myListIndex.size() < (MyDefines.NUMBER_OF_SHIPS)) {
            //chi muc gioi han laf 120
            int index = r1.nextInt(120);

            //kiem tra xem chi muc da tao co nam trong danh sach danh rieng khong, neu co tao chi muc moi, neu khong thi them vao danh sach
            if (!this.reservedList.contains(index)) {
                myListIndex.add(index);
                myListOrientation.add(r2.nextInt(2)); //dinh huong chi co the la 0 hoac 1 (ngang hoac doc)
            }
        }

        /*
         * Them tat ca cac thuyen theo chi muc duoc tao va dinh huong
         * Kiem tra xem 1 chiec thuyen se thay the 1 chien thuyen khac khong (khong duoc phep)
         * Kiem tra xem chi muc cos phai la vi tri hop le khong (khong phai tren dong dau tien va cot dau tien)
         * Kiem tra xem thuyen co nam trong 1 dong khong (khong duoc phep bat dau tu dong nay va nhay sang dong khac)
         * Trong truong hop tat ca da hoan thanh, thuyen duoc them vao, truong hop khong tra ve false, luong se ngu 
         * trong 100ms (de cung cap them thoi gian cho cac so ngau nhien) roi thu lai.
         * Co thoi gian cho la 5s, neu trong 5s phan mem khong tim duoc vi tri se dung va nguoi dung co the thu lai.
         */
        //Tau tuan tra
        if (myListOrientation.get(0) == MyDefines.ORIENTATION_HORIZONTAL) {
            if (this.reservedList.contains(myListIndex.get(0) + 1)) {
                this.MatrixMap[myListIndex.get(0) - 1] = 2;
                this.MatrixMap[myListIndex.get(0)] = 3;
            } else {
                if (this.MatrixMap.length <= myListIndex.get(0) + 1) {
                    return false;
                }

                if (this.rightBorderList.contains(myListIndex.get(0))) {
                    return false;
                }

                this.MatrixMap[myListIndex.get(0)] = 2;
                this.MatrixMap[myListIndex.get(0) + 1] = 3;
            }
        } else {
            if (this.reservedList.contains(myListIndex.get(0) - 11) || (myListIndex.get(0) - 11 < 0)) {
                if (this.MatrixMap.length <= myListIndex.get(0) + 11) {
                    return false;
                }

                this.MatrixMap[myListIndex.get(0) + 11] = 4;
                this.MatrixMap[myListIndex.get(0)] = 5;
            } else {
                this.MatrixMap[myListIndex.get(0)] = 4;
                this.MatrixMap[myListIndex.get(0) - 11] = 5;
            }
        }

        //Tau khu truc
        if (myListOrientation.get(1) == MyDefines.ORIENTATION_HORIZONTAL) {
            if (this.reservedList.contains(myListIndex.get(1) + 2)) {
                if (this.MatrixMap[myListIndex.get(1) - 2] != -1 || this.MatrixMap[myListIndex.get(1) - 1] != -1 || this.MatrixMap[myListIndex.get(1)] != -1) {
                    return false;
                }
                this.MatrixMap[myListIndex.get(1) - 2] = 10;
                this.MatrixMap[myListIndex.get(1) - 1] = 11;
                this.MatrixMap[myListIndex.get(1)] = 12;
            } else {
                if (this.MatrixMap.length <= myListIndex.get(1) + 1 || this.MatrixMap.length <= myListIndex.get(1) + 2) {
                    return false;
                }

                if (this.MatrixMap[myListIndex.get(1)] != -1 || this.MatrixMap[myListIndex.get(1) + 1] != -1 || this.MatrixMap[myListIndex.get(1) + 2] != -1) {
                    return false;
                }

                if (this.rightBorderList.contains(myListIndex.get(1) + 1) || this.rightBorderList.contains(myListIndex.get(1))) {
                    return false;
                }

                this.MatrixMap[myListIndex.get(1)] = 10;
                this.MatrixMap[myListIndex.get(1) + 1] = 11;
                this.MatrixMap[myListIndex.get(1) + 2] = 12;
            }
        } else {
            if (this.reservedList.contains(myListIndex.get(1) - 22) || (myListIndex.get(1) - 22 < 0)) {
                if (this.MatrixMap[myListIndex.get(1) + 22] != -1 || this.MatrixMap[myListIndex.get(1) + 11] != -1 || this.MatrixMap[myListIndex.get(1)] != -1) {
                    return false;
                }
                this.MatrixMap[myListIndex.get(1) + 22] = 13;
                this.MatrixMap[myListIndex.get(1) + 11] = 14;
                this.MatrixMap[myListIndex.get(1)] = 15;
            } else {
                if (this.MatrixMap[myListIndex.get(1)] != -1 || this.MatrixMap[myListIndex.get(1) - 11] != -1 || this.MatrixMap[myListIndex.get(1) - 22] != -1) {
                    return false;
                }
                this.MatrixMap[myListIndex.get(1)] = 13;
                this.MatrixMap[myListIndex.get(1) - 11] = 14;
                this.MatrixMap[myListIndex.get(1) - 22] = 15;
            }
        }

        //Tau ngam
        if (myListOrientation.get(2) == MyDefines.ORIENTATION_HORIZONTAL) {
            if (this.reservedList.contains(myListIndex.get(2) + 2)) {
                if (this.MatrixMap[myListIndex.get(2) - 2] != -1 || this.MatrixMap[myListIndex.get(2) - 1] != -1 || this.MatrixMap[myListIndex.get(2)] != -1) {
                    return false;
                }
                this.MatrixMap[myListIndex.get(2) - 2] = 22;
                this.MatrixMap[myListIndex.get(2) - 1] = 23;
                this.MatrixMap[myListIndex.get(2)] = 24;
            } else {
                if (this.MatrixMap.length <= myListIndex.get(2) + 1 || this.MatrixMap.length <= myListIndex.get(2) + 2) {
                    return false;
                }

                if (this.MatrixMap[myListIndex.get(2) + 2] != -1 || this.MatrixMap[myListIndex.get(2) + 1] != -1 || this.MatrixMap[myListIndex.get(2)] != -1) {
                    return false;
                }

                if (this.rightBorderList.contains(myListIndex.get(2) + 1) || this.rightBorderList.contains(myListIndex.get(2))) {
                    return false;
                }

                this.MatrixMap[myListIndex.get(2)] = 22;
                this.MatrixMap[myListIndex.get(2) + 1] = 23;
                this.MatrixMap[myListIndex.get(2) + 2] = 24;
            }
        } else {
            if (this.reservedList.contains(myListIndex.get(2) - 22) || (myListIndex.get(2) - 22 < 0)) {
                if (this.MatrixMap[myListIndex.get(2) + 22] != -1 || this.MatrixMap[myListIndex.get(2) + 11] != -1 || this.MatrixMap[myListIndex.get(2)] != -1) {
                    return false;
                }
                this.MatrixMap[myListIndex.get(2) + 22] = 25;
                this.MatrixMap[myListIndex.get(2) + 11] = 26;
                this.MatrixMap[myListIndex.get(2)] = 27;
            } else {
                if (this.MatrixMap[myListIndex.get(2) - 22] != -1 || this.MatrixMap[myListIndex.get(2) - 11] != -1 || this.MatrixMap[myListIndex.get(2)] != -1) {
                    return false;
                }
                this.MatrixMap[myListIndex.get(2)] = 25;
                this.MatrixMap[myListIndex.get(2) - 11] = 26;
                this.MatrixMap[myListIndex.get(2) - 22] = 27;
            }
        }

        //Tau chien
        if (myListOrientation.get(3) == MyDefines.ORIENTATION_HORIZONTAL) {
            if (this.reservedList.contains(myListIndex.get(3) + 3)) {
                if (this.MatrixMap[myListIndex.get(3) - 3] != -1 || this.MatrixMap[myListIndex.get(3) - 2] != -1 || this.MatrixMap[myListIndex.get(3) - 1] != -1 || this.MatrixMap[myListIndex.get(3)] != -1) {
                    return false;
                }
                this.MatrixMap[myListIndex.get(3) - 3] = 34;
                this.MatrixMap[myListIndex.get(3) - 2] = 35;
                this.MatrixMap[myListIndex.get(3) - 1] = 36;
                this.MatrixMap[myListIndex.get(3)] = 37;
            } else {
                if (this.MatrixMap.length <= myListIndex.get(3) + 3 || this.MatrixMap.length <= myListIndex.get(3) + 2 || this.MatrixMap.length <= myListIndex.get(3) + 1) {
                    return false;
                }

                if (this.MatrixMap[myListIndex.get(3) + 3] != -1 || this.MatrixMap[myListIndex.get(3) + 2] != -1 || this.MatrixMap[myListIndex.get(3) + 1] != -1 || this.MatrixMap[myListIndex.get(3)] != -1) {
                    return false;
                }

                if (this.rightBorderList.contains(myListIndex.get(3) + 2) || this.rightBorderList.contains(myListIndex.get(3) + 1) || this.rightBorderList.contains(myListIndex.get(3))) {
                    return false;
                }

                this.MatrixMap[myListIndex.get(3)] = 34;
                this.MatrixMap[myListIndex.get(3) + 1] = 35;
                this.MatrixMap[myListIndex.get(3) + 2] = 36;
                this.MatrixMap[myListIndex.get(3) + 3] = 37;
            }
        } else {
            if (this.reservedList.contains(myListIndex.get(3) - 33) || (myListIndex.get(3) - 33 < 0)) {
                if (this.MatrixMap[myListIndex.get(3) + 33] != -1 || this.MatrixMap[myListIndex.get(3) + 22] != -1 || this.MatrixMap[myListIndex.get(3) + 11] != -1 || this.MatrixMap[myListIndex.get(3)] != -1) {
                    return false;
                }
                this.MatrixMap[myListIndex.get(3) + 33] = 38;
                this.MatrixMap[myListIndex.get(3) + 22] = 39;
                this.MatrixMap[myListIndex.get(3) + 11] = 40;
                this.MatrixMap[myListIndex.get(3)] = 41;
            } else {
                if (this.MatrixMap[myListIndex.get(3) - 33] != -1 || this.MatrixMap[myListIndex.get(3) - 22] != -1 || this.MatrixMap[myListIndex.get(3) - 11] != -1 || this.MatrixMap[myListIndex.get(3)] != -1) {
                    return false;
                }
                this.MatrixMap[myListIndex.get(3)] = 38;
                this.MatrixMap[myListIndex.get(3) - 11] = 39;
                this.MatrixMap[myListIndex.get(3) - 22] = 40;
                this.MatrixMap[myListIndex.get(3) - 33] = 41;
            }
        }

        //Tau san bay
        if (myListOrientation.get(4) == MyDefines.ORIENTATION_HORIZONTAL) {
            if (this.reservedList.contains(myListIndex.get(4) + 4)) {
                if (this.MatrixMap[myListIndex.get(4) - 4] != -1 || this.MatrixMap[myListIndex.get(4) - 3] != -1 || this.MatrixMap[myListIndex.get(4) - 2] != -1 || this.MatrixMap[myListIndex.get(4) - 1] != -1 || this.MatrixMap[myListIndex.get(4)] != -1) {
                    return false;
                }
                this.MatrixMap[myListIndex.get(4) - 4] = 50;
                this.MatrixMap[myListIndex.get(4) - 3] = 51;
                this.MatrixMap[myListIndex.get(4) - 2] = 52;
                this.MatrixMap[myListIndex.get(4) - 1] = 53;
                this.MatrixMap[myListIndex.get(4)] = 54;
            } else {
                if (this.MatrixMap.length <= myListIndex.get(4) + 4 || this.MatrixMap.length <= myListIndex.get(4) + 3 || this.MatrixMap.length <= myListIndex.get(4) + 2 || this.MatrixMap.length <= myListIndex.get(4) + 1) {
                    return false;
                }

                if (this.MatrixMap[myListIndex.get(4) + 4] != -1 || this.MatrixMap[myListIndex.get(4) + 3] != -1 || this.MatrixMap[myListIndex.get(4) + 2] != -1 || this.MatrixMap[myListIndex.get(4) + 1] != -1 || this.MatrixMap[myListIndex.get(4)] != -1) {
                    return false;
                }

                if (this.rightBorderList.contains(myListIndex.get(4) + 3) || this.rightBorderList.contains(myListIndex.get(4) + 2) || this.rightBorderList.contains(myListIndex.get(4) + 1) || this.rightBorderList.contains(myListIndex.get(4))) {
                    return false;
                }

                this.MatrixMap[myListIndex.get(4)] = 50;
                this.MatrixMap[myListIndex.get(4) + 1] = 51;
                this.MatrixMap[myListIndex.get(4) + 2] = 52;
                this.MatrixMap[myListIndex.get(4) + 3] = 53;
                this.MatrixMap[myListIndex.get(4) + 4] = 54;
            }
        } else {
            if (this.reservedList.contains(myListIndex.get(4) - 44) || (myListIndex.get(4) - 44 < 0)) {
                if (this.MatrixMap[myListIndex.get(4) + 44] != -1 || this.MatrixMap[myListIndex.get(4) + 33] != -1 || this.MatrixMap[myListIndex.get(4) + 22] != -1 || this.MatrixMap[myListIndex.get(4) + 11] != -1 || this.MatrixMap[myListIndex.get(4)] != -1) {
                    return false;
                }
                this.MatrixMap[myListIndex.get(4) + 44] = 55;
                this.MatrixMap[myListIndex.get(4) + 33] = 56;
                this.MatrixMap[myListIndex.get(4) + 22] = 57;
                this.MatrixMap[myListIndex.get(4) + 11] = 58;
                this.MatrixMap[myListIndex.get(4)] = 59;
            } else {
                if (this.MatrixMap[myListIndex.get(4) - 44] != -1 || this.MatrixMap[myListIndex.get(4) - 33] != -1 || this.MatrixMap[myListIndex.get(4) - 22] != -1 || this.MatrixMap[myListIndex.get(4) - 11] != -1 || this.MatrixMap[myListIndex.get(4)] != -1) {
                    return false;
                }
                this.MatrixMap[myListIndex.get(4)] = 55;
                this.MatrixMap[myListIndex.get(4) - 11] = 56;
                this.MatrixMap[myListIndex.get(4) - 22] = 57;
                this.MatrixMap[myListIndex.get(4) - 33] = 58;
                this.MatrixMap[myListIndex.get(4) - 44] = 59;
            }
        }
        this.selectionDone = true;
        return true;
    }

    //cap nhat vi tri trong game cho moi lan ban
    //Kiem tra Matrix Map Help o dau class
    public void updatePosition(int index) {
        if (this.MatrixMap[index] == -1) //WATER
        {
            this.MatrixMap[index] = 0; 		//WATER_HIT_CLEAR
        } else if (this.MatrixMap[index] == 2) //PATROL_BOAT_1
        {
            this.MatrixMap[index] = 6;		//PATROL_BOAT_HIT_1
        } else if (this.MatrixMap[index] == 3) //PATROL_BOAT_2
        {
            this.MatrixMap[index] = 7;		//PATROL_BOAT_HIT_2
        } else if (this.MatrixMap[index] == 4) //PATROL_BOAT_1_V
        {
            this.MatrixMap[index] = 8;		//PATROL_BOAT_HIT_1_V
        } else if (this.MatrixMap[index] == 5) //PATROL_BOAT_2_V
        {
            this.MatrixMap[index] = 9;		//PATROL_BOAT_HIT_2_V
        } else if (this.MatrixMap[index] == 10) //DESTROYER_1
        {
            this.MatrixMap[index] = 16;		//DESTROYER_HIT_1
        } else if (this.MatrixMap[index] == 11) //DESTROYER_2
        {
            this.MatrixMap[index] = 17;		//DESTROYER_HIT_2
        } else if (this.MatrixMap[index] == 12) //DESTROYER_3
        {
            this.MatrixMap[index] = 18;		//DESTROYER_HIT_3
        } else if (this.MatrixMap[index] == 13) //DESTROYER_1_V
        {
            this.MatrixMap[index] = 19;		//DESTROYER_HIT_1_V
        } else if (this.MatrixMap[index] == 14) //DESTROYER_2_V
        {
            this.MatrixMap[index] = 20;		//DESTROYER_HIT_2_V
        } else if (this.MatrixMap[index] == 15) //DESTROYER_3_V
        {
            this.MatrixMap[index] = 21;		//DESTROYER_HIT_3_V
        } else if (this.MatrixMap[index] == 22) //SUBMARINE_1
        {
            this.MatrixMap[index] = 28;		//SUBMARINE_HIT_1
        } else if (this.MatrixMap[index] == 23) //SUBMARINE_2
        {
            this.MatrixMap[index] = 29;		//SUBMARINE_HIT_2
        } else if (this.MatrixMap[index] == 24) //SUBMARINE_3
        {
            this.MatrixMap[index] = 30;		//SUBMARINE_HIT_3
        } else if (this.MatrixMap[index] == 25) //SUBMARINE_1_V
        {
            this.MatrixMap[index] = 31;		//SUBMARINE_HIT_1_V
        } else if (this.MatrixMap[index] == 26) //SUBMARINE_2_V
        {
            this.MatrixMap[index] = 32;		//SUBMARINE_HIT_2_V
        } else if (this.MatrixMap[index] == 27) //SUBMARINE_3_V
        {
            this.MatrixMap[index] = 33;		//SUBMARINE_HIT_3_V
        } else if (this.MatrixMap[index] == 34) //BATTLESHIP_1
        {
            this.MatrixMap[index] = 42;		//BATTLESHIP_HIT_1
        } else if (this.MatrixMap[index] == 35) //BATTLESHIP_2
        {
            this.MatrixMap[index] = 43;		//BATTLESHIP_HIT_2
        } else if (this.MatrixMap[index] == 36) //BATTLESHIP_3
        {
            this.MatrixMap[index] = 44;		//BATTLESHIP_HIT_3
        } else if (this.MatrixMap[index] == 37) //BATTLESHIP_4
        {
            this.MatrixMap[index] = 45;		//BATTLESHIP_HIT_4	
        } else if (this.MatrixMap[index] == 38) //BATTLESHIP_1_V
        {
            this.MatrixMap[index] = 46;		//BATTLESHIP_HIT_1_V
        } else if (this.MatrixMap[index] == 39) //BATTLESHIP_2_V
        {
            this.MatrixMap[index] = 47;		//BATTLESHIP_HIT_2_V
        } else if (this.MatrixMap[index] == 40) //BATTLESHIP_3_V
        {
            this.MatrixMap[index] = 48;		//BATTLESHIP_HIT_3_V
        } else if (this.MatrixMap[index] == 41) //BATTLESHIP_4_V
        {
            this.MatrixMap[index] = 49;		//BATTLESHIP_HIT_4_V
        } else if (this.MatrixMap[index] == 50) //AIRCRAFT_CARRIER_1
        {
            this.MatrixMap[index] = 60;		//AIRCRAFT_CARRIER_HIT_1
        } else if (this.MatrixMap[index] == 51) //AIRCRAFT_CARRIER_2
        {
            this.MatrixMap[index] = 61;		//AIRCRAFT_CARRIER_HIT_2	
        } else if (this.MatrixMap[index] == 52) //AIRCRAFT_CARRIER_3
        {
            this.MatrixMap[index] = 62;		//AIRCRAFT_CARRIER_HIT_3
        } else if (this.MatrixMap[index] == 53) //AIRCRAFT_CARRIER_4
        {
            this.MatrixMap[index] = 63;		//AIRCRAFT_CARRIER_HIT_4
        } else if (this.MatrixMap[index] == 54) //AIRCRAFT_CARRIER_5
        {
            this.MatrixMap[index] = 64;		//AIRCRAFT_CARRIER_HIT_5
        } else if (this.MatrixMap[index] == 55) //AIRCRAFT_CARRIER_1_V
        {
            this.MatrixMap[index] = 65;		//AIRCRAFT_CARRIER_HIT_1_V
        } else if (this.MatrixMap[index] == 56) //AIRCRAFT_CARRIER_2_V
        {
            this.MatrixMap[index] = 66;		//AIRCRAFT_CARRIER_HIT_2_V
        } else if (this.MatrixMap[index] == 57) //AIRCRAFT_CARRIER_3_V
        {
            this.MatrixMap[index] = 67;		//AIRCRAFT_CARRIER_HIT_3_V
        } else if (this.MatrixMap[index] == 58) //AIRCRAFT_CARRIER_4_V
        {
            this.MatrixMap[index] = 68;		//AIRCRAFT_CARRIER_HIT_4_V
        } else if (this.MatrixMap[index] == 59) //AIRCRAFT_CARRIER_5_V
        {
            this.MatrixMap[index] = 69;		//AIRCRAFT_CARRIER_HIT_5_V
        } else {
        }
    }

    //thiet lap luot choi cua nguoi choi
    public void setMyTurn(boolean value) {
        this.myTurn = value;
    }

    //kiem tra luot choi cua nguoi choi
    public boolean getMyTurn() {
        return this.myTurn;
    }

    //tim kien dong co chi muc
    private int searchLine(int index) {
        for (int i = 0; i < MyDefines.MAP_N_ROW; i++) {
            for (int j = 0; j < MyDefines.MAP_N_COL; j++) {
                if (index == MyDefines.LINES[i][j]) {
                    return j + 1;
                }
            }
        }
        return -1;
    }

    //tim kiem cot co chi muc
    private int searchColumn(int index) {
        for (int i = 0; i < MyDefines.MAP_N_ROW; i++) {
            for (int j = 0; j < MyDefines.MAP_N_COL; j++) {
                if (index == MyDefines.LINES[i][j]) {
                    return i;
                }
            }
        }
        return -1;
    }

    //kiem tra xem chi muc thuoc ve dong nao
    public String getLine(int index) {
        return Integer.toString(searchLine(index));
    }

    //kiem tra xem chi muc thuoc ve cot nao
    public String getColumn(int index) {
        return MyDefines.sLINES[searchColumn(index)];
    }

    //tra ve neu 1 cai gi do bi danh trung hoac khong
    public boolean hitSomething(int index) {
        if (this.MatrixMap[index] == -1) {
            return false;
        }

        return true;
    }

    //tra ve neu chi muc la vi tri duoc phep
    public boolean isPositionLegal(int index) {
        return !this.reservedList.contains(index);
    }

    //cap nhat ban do cua ke thu neu vi tri bi tan cong hay khong
    public void setEnemyHit(int index, boolean hit) {
        if (hit) {
            this.MatrixMap[index] = 1;
        } else {
            this.MatrixMap[index] = 0;
        }
    }

    //cap nhat danh sach vi tri da choi
    public void addPlayedPosition(int index) {
        this.playedPositions.add(index);
    }

    //kiem tra xem vi tri da duoc choi hay chua
    public boolean positionPlayed(int index) {
        return this.playedPositions.contains(index);
    }

    //kiem tra xem tro choi da ket thuc chua
    public boolean isGameOver() {
        //vong lap trong Matrix Map de tim bat ki phan thuyen nao con chua bi ban trung
        //kiem tra Matrix Map Help
        for (int i = 0; i < this.MatrixMap.length; i++) {
            if (this.MatrixMap[i] == 2 || this.MatrixMap[i] == 3 || this.MatrixMap[i] == 4 || this.MatrixMap[i] == 5
                    || this.MatrixMap[i] == 10 || this.MatrixMap[i] == 11 || this.MatrixMap[i] == 12 || this.MatrixMap[i] == 13 || this.MatrixMap[i] == 14 || this.MatrixMap[i] == 15
                    || this.MatrixMap[i] == 22 || this.MatrixMap[i] == 23 || this.MatrixMap[i] == 24 || this.MatrixMap[i] == 25 || this.MatrixMap[i] == 26 || this.MatrixMap[i] == 27
                    || this.MatrixMap[i] == 34 || this.MatrixMap[i] == 35 || this.MatrixMap[i] == 36 || this.MatrixMap[i] == 37 || this.MatrixMap[i] == 38 || this.MatrixMap[i] == 39 || this.MatrixMap[i] == 40 || this.MatrixMap[i] == 41
                    || this.MatrixMap[i] == 50 || this.MatrixMap[i] == 51 || this.MatrixMap[i] == 52 || this.MatrixMap[i] == 53 || this.MatrixMap[i] == 54 || this.MatrixMap[i] == 55 || this.MatrixMap[i] == 56 || this.MatrixMap[i] == 57 || this.MatrixMap[i] == 58 || this.MatrixMap[i] == 59) {
                return false;
            }
        }
        return true;
    }
}

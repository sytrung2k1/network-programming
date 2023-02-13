/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class MyListRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = 1L;

    private Font font = new Font("helvitica", Font.BOLD, 24);

    private Map<String, ImageIcon> imageMap;	//map <name,image>
    private Map<Integer, String> imageMapHelp;  //map<image index, name>  **kiem tra MapUpdateHandler.java de biet giai thich ve chi muc hinh anh

    private ArrayList<Integer> auxIndexHover = new ArrayList<Integer>();	//danh sach tro giup khi di chuot (highlights)
    private ArrayList<Integer> reservedList = new ArrayList<Integer>();	//vi tri khong duoc phep

    private Integer[] MatrixMap;	//matrix map voi cac loai hinh anh **kiem tra MapUpdateHandler.java de biet giai thich ve chi muc hinh anh

    public MyListRenderer(Map<String, ImageIcon> imageMap, Map<Integer, String> imageMapHelp, String appPath, Integer[] startMatrix) {
        this.imageMap = imageMap;
        this.imageMapHelp = imageMapHelp;

        //copy danh sach danh rieng tu MyDefines.java
        for (int i = 0; i < MyDefines.RESERVED_LIST.length; i++) {
            this.reservedList.add(MyDefines.RESERVED_LIST[i]);
        }

        //matrix map dau tien (khoi tao -1 cho tat ca) chi co nuoc
        this.MatrixMap = startMatrix;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String sValue = (String) value;

        //kiem tra xem co vi tri nao trong danh sach phu tro khong va neu vi tri hien tai khong co trong danh sach danh rieng
        if (this.auxIndexHover.size() != 0 && !this.reservedList.contains(index)) {
            //kiem tra xem vi tri hien tai co nam trong danh sach phu tro khong neu no co trong danh sach, hay thay doi nen cua chi muc thanh mau xam (highlights)
            if (this.auxIndexHover.contains(index)) {
                Color backgroundColor = this.auxIndexHover.get(this.auxIndexHover.indexOf(index)) == index ? Color.gray : Color.white;
                JPanel pane = new JPanel(new BorderLayout());
                pane.setBackground(backgroundColor);
                return pane;
            }
        }

        //neu chi muc khac 0 va sValue (gia tring chuoi cua chi muc hien tai) bang "" thi co the cap nhat vi tri
        //no cap nhat voi matrix map de them hinh anh phu hop vao chi muc
        if (index != 0 && sValue.equals("")) {
            label.setIcon(this.imageMap.get(this.imageMapHelp.get(this.MatrixMap[index])));
        }
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setFont(this.font);
        return label;
    }

    //di chuot noi cac vi tri chi muc chuot hien tai vao danh sach phu tro
    public void setIndexHover(ArrayList<Integer> mHoveredJListIndex) {
        this.auxIndexHover = mHoveredJListIndex;
    }

    //bat ki sua doi nao tren matrix map can duoc cap nhat
    public void updateMatrix(Integer[] newMatrix) {
        this.MatrixMap = newMatrix;
    }

}

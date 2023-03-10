/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;

import computer.PCGameHandler;
import java.awt.Point;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import network.MyClient;
import network.MyServer;
import computer.SinglePlayerHandler;

import javax.swing.border.LineBorder;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import java.awt.Font;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Trung
 */
public class BattleShip extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    //bang
    private JList myEnemyList;
    private JList myBoardList;

    //trinh ket xuat bang
    private MyListRenderer myRenderer;
    private MyListRenderer myEnemyRenderer;

    //di chuyen chuot
    private int mHoveredJListIndex = -1;
    private ArrayList<Integer> mHoveredJListIndexList = new ArrayList<Integer>();
    private int orientation = MyDefines.ORIENTATION_HORIZONTAL;

    //cap nhat bang
    private MapUpdateHandler myMapUpdate;
    private MapUpdateHandler myEnemyMapUpdate;

    //dau ra bang dieu khien
    private JTextPane outputTextField;
    private HTMLEditorKit outputKit;
    private HTMLDocument outputDoc;

    //bang phan bo
    private JButton btnNewAllocation;
    private JRadioButton rdbtnAutomaticAllocation;
    private JRadioButton rdbtnManualAllocation;
    private JButton btnRestartAllocation;

    //bang ket noi
    public JButton btnSendIP;
    private JLabel lblMyIpAddress;
    private JTextField ipAddressTextField;
    public JButton btnConnect;
    public JButton btnStopServer;
    private boolean IsServer = false;
    private JTextField portTextField;
    private String serverIP = "";
    private boolean PlayAgainsPC = true;

    //choi don
    SinglePlayerHandler mySinglePlayerObj;

    //server
    private MyServer myServerObj;
    private MyClient myClientObj;

    //bang chat
    private JTextPane chatHistory;
    private HTMLEditorKit chatKit;
    private HTMLDocument chatDoc;
    public JButton btnSend;
    public JTextPane sendTxtField;

    //main frame
    private JFrame myFrame;

    //ai bat dau xu ly
    private DiceHandler myDiceHandler;

    //giao dien GameOver
    private GameOverGui myGOScreen;

    //xu ly gui (chat)
    private static final String TEXT_SEND = "text-submit";
    private static final String INSERT_BREAK = "insert-break";

    public BattleShip() {
        //bat dau xu ly
        startMyHandlers();
        //tao bang cua toi
        initializeMyBoard();
        //tao bang cua ke thu cua toi
        initializeMyEnemyBoard();
        //tao bang dieu khien dau ra
        initializeOutputPanel();
        //tao bang phan bo
        initialiazeAllocationPanel();
        //tao bang ket noi
        initializeConnectionPanel();
        //tao bang tro chuyen
        initializeChatPanel();
        //cac thanh phan con lai
        initializeComponents();
        //man hinh splash
        splashScreenHandler();

        setVisible(true);
    }

    private void startMyHandlers() {
        //lay duong dan ung dung
        File currDir = new File("");

        //khung chinh cho tat ca JOptionPane
        this.myFrame = this;

        //xu ly xuc xac de cho nguoi bat dau
        this.myDiceHandler = new DiceHandler(myFrame);

        //man hinh GameOver
        this.myGOScreen = new GameOverGui(currDir.getAbsolutePath() + "\\img\\", this);

        //trinh xu ly cap nhat bang
        this.myMapUpdate = new MapUpdateHandler(currDir.getAbsolutePath() + "\\img\\");
        this.myEnemyMapUpdate = new MapUpdateHandler(currDir.getAbsolutePath() + "\\img\\");

        //trinh ket xuat bang
        this.myRenderer = new MyListRenderer(this.myMapUpdate.getImageMap(), this.myMapUpdate.getImageMapHelp(), this.myMapUpdate.getApplicationPath(), this.myMapUpdate.getMatrixMap());
        this.myEnemyRenderer = new MyListRenderer(this.myEnemyMapUpdate.getImageMap(), this.myMapUpdate.getImageMapHelp(), this.myEnemyMapUpdate.getApplicationPath(), this.myEnemyMapUpdate.getMatrixMap());

        //trinh xu ly may khach
        this.myClientObj = new MyClient(this);
    }

    private void initializeMyBoard() {
        //bang dieu khien cua toi
        JPanel myShipsPanel = new JPanel();
        myShipsPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Thuy???n c???a b???n", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        myShipsPanel.setBounds(10, 11, 349, 369);
        getContentPane().add(myShipsPanel);

        //danh sach bang cua toi
        this.myBoardList = new JList(MyDefines.NAME_LIST);
        myShipsPanel.add(this.myBoardList);
        this.myBoardList.setCellRenderer(this.myRenderer);
        this.myBoardList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        this.myBoardList.setVisibleRowCount(11);
        this.myBoardList.setFixedCellHeight(30);
        this.myBoardList.setFixedCellWidth(29);

        //phan bo 1 con thuyen hoac thay doi dinh huong nguoi choi
        this.myBoardList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //neu phan bo xong, nguoi choi quay lai vi khong co tau nao duoc phan bo
                if (myMapUpdate.isSelectionDone() || btnNewAllocation.isEnabled()) {
                    return;
                }

                //chuot tra de dat tau
                if (e.getButton() == MouseEvent.BUTTON1) {
                    //co gang cap nhat ban do voi vi tri da chon
                    if (myMapUpdate.updateMap(orientation, mHoveredJListIndexList)) {
                        //son lai bang
                        myRenderer.updateMatrix(myMapUpdate.getMatrixMap());
                        myBoardList.repaint();

                        //cho nguoi dung biet thuyen nao da duoc phan bo
                        if (myMapUpdate.isSelectionDone()) {
                            writeOutputMessage(" - B???n ???? ph??n b??? T??u s??n bay c???a m??nh!");
                            writeOutputMessage(" - V?? b??y gi??? b???n ???? s???n s??ng ????? ch??i");
                        } else if ((myMapUpdate.getBoatType() - 1) == MyDefines.PATROL_BOAT) {
                            writeOutputMessage(" - B???n ???? ph??n b??? T??u tu???n tra c???a m??nh!");
                        } else if ((myMapUpdate.getBoatType() - 1) == MyDefines.DESTROYER) {
                            writeOutputMessage(" - B???n ???? ph??n b??? T??u khu tr???c c???a m??nh!");
                        } else if ((myMapUpdate.getBoatType() - 1) == MyDefines.SUBMARINE) {
                            writeOutputMessage(" - B???n ???? ph??n b??? T??u ng???m c???a m??nh!");
                        } else {
                            writeOutputMessage(" - B???n ???? ph??n b??? T??u chi???n c???a m??nh!");
                        }
                    } else {
                        //trong truong hop vi tri khong hop le
                        JOptionPane.showMessageDialog(myFrame, "B???n kh??ng th??? th??m m???t con t??u ra kh???i c???a s??? ???????c!");
                    }
                } //chuot phai de thay doi huong tau
                else if (e.getButton() == MouseEvent.BUTTON3) {
                    //ngang sang doc
                    if (orientation == MyDefines.ORIENTATION_HORIZONTAL) {
                        orientation = MyDefines.ORIENTATION_VERTICAL;
                        mHoveredJListIndexList.clear();
                        mHoveredJListIndexList.add(mHoveredJListIndex);
                        mHoveredJListIndexList.add(mHoveredJListIndex - 11);
                        myRenderer.setIndexHover(mHoveredJListIndexList);
                        myBoardList.repaint();
                    } //doc sang ngang
                    else {
                        orientation = MyDefines.ORIENTATION_HORIZONTAL;
                        mHoveredJListIndexList.clear();
                        mHoveredJListIndexList.add(mHoveredJListIndex);
                        mHoveredJListIndexList.add(mHoveredJListIndex + 1);
                        myRenderer.setIndexHover(mHoveredJListIndexList);
                        myBoardList.repaint();
                    }
                } else {
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });

        //highlight vi tri tau
        this.myBoardList.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent me) {
                //lay vi tri chuot
                Point p = new Point(me.getX(), me.getY());

                //lay chi so ma tran theo vi tri chuot
                int index = myBoardList.locationToIndex(p);

                //Kiem tra xem da phan bo xong chua (truong hop co thi khong can boi den bang)
                if (myMapUpdate.isSelectionDone() || btnNewAllocation.isEnabled()) {
                    mHoveredJListIndex = index;
                    mHoveredJListIndexList.clear();
                    myBoardList.repaint();
                }

                //chi thuc hien mot cai gi do neu chi muc hien tai khac voi chi muc cuoi cung
                if (index != mHoveredJListIndex) {
                    //Neu loai tau la tau tuan tra thi can to sang 2 o
                    if (myMapUpdate.getBoatType() == MyDefines.PATROL_BOAT) {
                        mHoveredJListIndex = index;
                        mHoveredJListIndexList.clear();
                        mHoveredJListIndexList.add(index);
                        if (orientation == MyDefines.ORIENTATION_HORIZONTAL) {
                            mHoveredJListIndexList.add(index + 1);
                        } else {
                            mHoveredJListIndexList.add(index - 11);
                        }
                        myRenderer.setIndexHover(mHoveredJListIndexList);
                        myBoardList.repaint();
                    } //Neu loai tau la Tau khu truc hoac Tau ngam thi can to sang 3 o
                    else if (myMapUpdate.getBoatType() == MyDefines.DESTROYER || myMapUpdate.getBoatType() == MyDefines.SUBMARINE) {
                        mHoveredJListIndex = index;
                        mHoveredJListIndexList.clear();
                        mHoveredJListIndexList.add(index);
                        if (orientation == MyDefines.ORIENTATION_HORIZONTAL) {
                            mHoveredJListIndexList.add(index + 1);
                            mHoveredJListIndexList.add(index + 2);
                        } else {
                            mHoveredJListIndexList.add(index - 11);
                            mHoveredJListIndexList.add(index - 22);
                        }
                        myRenderer.setIndexHover(mHoveredJListIndexList);
                        myBoardList.repaint();
                    } //Neu loai tau la Tau chien thi can to sang 4 o
                    else if (myMapUpdate.getBoatType() == MyDefines.BATTLESHIP) {
                        mHoveredJListIndex = index;
                        mHoveredJListIndexList.clear();
                        mHoveredJListIndexList.add(index);
                        if (orientation == MyDefines.ORIENTATION_HORIZONTAL) {
                            mHoveredJListIndexList.add(index + 1);
                            mHoveredJListIndexList.add(index + 2);
                            mHoveredJListIndexList.add(index + 3);
                        } else {
                            mHoveredJListIndexList.add(index - 11);
                            mHoveredJListIndexList.add(index - 22);
                            mHoveredJListIndexList.add(index - 33);
                        }
                        myRenderer.setIndexHover(mHoveredJListIndexList);
                        myBoardList.repaint();
                    } //Neu loai tau la Tau san baty thi can to sang 5 o
                    else {
                        mHoveredJListIndex = index;
                        mHoveredJListIndexList.clear();
                        mHoveredJListIndexList.add(index);
                        if (orientation == MyDefines.ORIENTATION_HORIZONTAL) {
                            mHoveredJListIndexList.add(index + 1);
                            mHoveredJListIndexList.add(index + 2);
                            mHoveredJListIndexList.add(index + 3);
                            mHoveredJListIndexList.add(index + 4);
                        } else {
                            mHoveredJListIndexList.add(index - 11);
                            mHoveredJListIndexList.add(index - 22);
                            mHoveredJListIndexList.add(index - 33);
                            mHoveredJListIndexList.add(index - 44);
                        }
                        myRenderer.setIndexHover(mHoveredJListIndexList);
                        myBoardList.repaint();
                    }
                }
            }
        });
    }

    private void initializeMyEnemyBoard() {
        JPanel myEnemyPanel = new JPanel();
        myEnemyPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Thuy???n c???a k??? ?????ch:", TitledBorder.LEFT, TitledBorder.TOP, null, null));
        myEnemyPanel.setBounds(369, 11, 349, 369);
        getContentPane().add(myEnemyPanel);

        //bang cua ke dich
        this.myEnemyList = new JList(MyDefines.NAME_LIST);
        myEnemyPanel.add(this.myEnemyList);
        this.myEnemyList.setCellRenderer(this.myEnemyRenderer);
        this.myEnemyList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        this.myEnemyList.setVisibleRowCount(11);
        this.myEnemyList.setFixedCellHeight(30);
        this.myEnemyList.setFixedCellWidth(29);

        //play listener
        this.myEnemyList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //nhan chuot trai
                if (e.getButton() == MouseEvent.BUTTON1) {
                    //lay vi tri chuot
                    Point p = new Point(e.getX(), e.getY());

                    //chuyen doi vi tri chuot sang chi muc ma tran
                    int index = myBoardList.locationToIndex(p);

                    //kiem tra xem co ket noi khong (neu khong ket noi thi khong choi duoc)
                    if (!btnConnect.isEnabled()) {
                        if (PlayAgainsPC) {
                            if (myMapUpdate.getMyTurn()) {
                                //kiem tra xem vi tri nay cos hop le khong
                                if (myEnemyMapUpdate.isPositionLegal(index)) {
                                    //kiem tra xem vi tri da duoc choi chua
                                    if (myMapUpdate.positionPlayed(index)) {
                                        JOptionPane.showMessageDialog(myFrame, "B???n ???? ????nh v??? tr?? n??y r???i, H??y th??? m???t v??? tr?? kh??c!");
                                    } else {
                                        //thong bao
                                        writeOutputMessage(" - Qu??? bom ???????c b???n t???i: " + myEnemyMapUpdate.getLine(index) + myEnemyMapUpdate.getColumn(index));

                                        //vi tri hop le va chua duoc choi, no bao cho may tinh noi na da duoc choi
                                        mySinglePlayerObj.indexPlayed(index);

                                        //them vao danh sach da choi
                                        myMapUpdate.addPlayedPosition(index);

                                        //dat luot cua toi la sai
                                        myMapUpdate.setMyTurn(false);
                                    }
                                } //khong phai la mot vi tri hop le
                                else {
                                    JOptionPane.showMessageDialog(myFrame, "B???n ???? c??? g???ng ????nh ??? m???t v?? tr?? kh??ng h???p l???. Vui l??ng ch???n m???t v??? tr?? kh??c tr??n b???ng.");
                                }
                            }
                        } else {
                            //kiem tra xem co dang choi voi tu cach Server khong
                            if (IsServer) {
                                //kiem tra xem co Client nao duoc ket noi khong va co den luot toi khong
                                if (myServerObj.getHasClient() && myMapUpdate.getMyTurn()) {
                                    //kiem tra xem vi tri ny co hop le khong
                                    if (myEnemyMapUpdate.isPositionLegal(index)) {
                                        //kiem tra xem vi tri da duoc choi chua
                                        if (myMapUpdate.positionPlayed(index)) {
                                            JOptionPane.showMessageDialog(myFrame, "B???n ???? ????nh v??? tr?? n??y r???i, H??y th??? m???t v??? tr?? kh??c!");
                                        } else {
                                            //vi tri hop le va chua duoc choi, no se gui cho ke dich noi do da duoc choi
                                            myServerObj.SendMessage("G:" + index);

                                            //them vao danh sach da danh
                                            myMapUpdate.addPlayedPosition(index);

                                            //dat luot cua toi la false
                                            myMapUpdate.setMyTurn(false);

                                            //thong bao
                                            writeOutputMessage(" - Qu??? bom ???????c b???n t???i: " + myEnemyMapUpdate.getLine(index) + myEnemyMapUpdate.getColumn(index));
                                        }
                                    } //khong phai la mot vi tri hop le
                                    else {
                                        JOptionPane.showMessageDialog(myFrame, "B???n ???? c??? g???ng ????nh ??? m???t v?? tr?? kh??ng h???p l???. Vui l??ng ch???n m???t v??? tr?? kh??c tr??n b???ng.");
                                    }
                                }
                            } //choi voi tu cach Client
                            else {
                                //kiem tra xem da ket noi chua va da den luot minh chua
                                if (myClientObj.getIsConnected() && myMapUpdate.getMyTurn()) {
                                    //kiem tra vi tri hop le
                                    if (myEnemyMapUpdate.isPositionLegal(index)) {
                                        //kiem tra xem vi tri da duoc choi chua
                                        if (myMapUpdate.positionPlayed(index)) {
                                            JOptionPane.showMessageDialog(myFrame, "B???n ???? ????nh v??? tr?? n??y r???i, H??y th??? m???t v??? tr?? kh??c!");
                                        } else {
                                            //vi tri hop le va chua duoc choi, no se gui cho ke dich noi do da duoc choi
                                            myClientObj.SendMessage("G:" + index);

                                            //them vao danh sach da danh
                                            myMapUpdate.addPlayedPosition(index);

                                            //dat luot cua toi la false
                                            myMapUpdate.setMyTurn(false);

                                            //thong bao
                                            writeOutputMessage(" - Qu??? bom ???????c b???n t???i: " + myEnemyMapUpdate.getLine(index) + myEnemyMapUpdate.getColumn(index));
                                        }
                                    } //khong phai la mot vi tri hop le
                                    else {
                                        JOptionPane.showMessageDialog(myFrame, "B???n ???? c??? g???ng ????nh ??? m???t v?? tr?? kh??ng h???p l???. Vui l??ng ch???n m???t v??? tr?? kh??c tr??n b???ng.");
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void initializeOutputPanel() {
        this.outputKit = new HTMLEditorKit();
        this.outputDoc = new HTMLDocument();

        this.outputTextField = new JTextPane();
        this.outputTextField.setContentType("text/html");
        this.outputTextField.setEditorKit(this.outputKit);
        this.outputTextField.setDocument(this.outputDoc);

        JScrollPane outputScrollPane = new JScrollPane(this.outputTextField);

        this.outputTextField.setFont(this.outputTextField.getFont().deriveFont(this.outputTextField.getFont().getStyle() | Font.BOLD));
        this.outputTextField.setEditable(false);
        this.outputTextField.setBounds(10, 21, 688, 141);
        writeOutputMessage("<b> - Ch???o m???ng ?????n v???i BattleShip!!!</b>");
        writeOutputMessage(" - B???n c???n ph???i ph??n b??? t??u c???a b???n. ????a chu???t v??o v??? tr?? b???n mu???n v?? nh???p v??o v??? tr?? ???? ho???c b???t ph??n b??? t??? ?????ng.");

        JPanel outputPanel = new JPanel();
        outputPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "L???ch s??? h??nh ?????ng", TitledBorder.LEFT, TitledBorder.TOP, null, null));
        outputPanel.setBounds(10, 404, 708, 207);
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(outputScrollPane, BorderLayout.CENTER);
        getContentPane().add(outputPanel);
    }

    private void initialiazeAllocationPanel() {
        JPanel shipsAllocationPanel = new JPanel();
        shipsAllocationPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Ph??n b??? t??u: ", TitledBorder.LEFT, TitledBorder.TOP, null, null));
        shipsAllocationPanel.setBounds(740, 11, 244, 107);
        getContentPane().add(shipsAllocationPanel);
        shipsAllocationPanel.setLayout(null);

        this.rdbtnAutomaticAllocation = new JRadioButton("Ph??n b??? t??? ?????ng");
        this.rdbtnAutomaticAllocation.setBounds(6, 44, 203, 23);
        shipsAllocationPanel.add(this.rdbtnAutomaticAllocation);

        this.rdbtnManualAllocation = new JRadioButton("Ph??n b??? th??? c??ng");
        this.rdbtnManualAllocation.setSelected(true);
        this.rdbtnManualAllocation.setBounds(6, 18, 109, 23);
        shipsAllocationPanel.add(this.rdbtnManualAllocation);

        this.rdbtnAutomaticAllocation.addActionListener(this);
        this.rdbtnManualAllocation.addActionListener(this);

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(this.rdbtnAutomaticAllocation);
        group.add(this.rdbtnManualAllocation);

        this.btnRestartAllocation = new JButton("Ph??n b??? l???i");
        this.btnRestartAllocation.setBounds(6, 74, 109, 23);
        shipsAllocationPanel.add(this.btnRestartAllocation);

        //restart ban bo ( dat tat ca thanh nuoc va clear cac vi tri)
        //xoa ban do cua chinh minh va ke dich
        this.btnRestartAllocation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                writeOutputMessage(" - B???n ???? kh???i ?????ng l???i vi???c ph??n b??? t??u c???a m??nh.");
                myMapUpdate.restartAllocation();
                myRenderer.updateMatrix(myMapUpdate.getMatrixMap());
                myBoardList.repaint();

                myEnemyMapUpdate.restartAllocation();
                myEnemyRenderer.updateMatrix(myEnemyMapUpdate.getMatrixMap());
                myEnemyList.repaint();
            }
        });

        this.btnNewAllocation = new JButton("Ph??n b??? m???i");
        this.btnNewAllocation.setEnabled(false);
        this.btnNewAllocation.setBounds(125, 74, 109, 23);
        shipsAllocationPanel.add(this.btnNewAllocation);

        //phan bo tu dong
        this.btnNewAllocation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //clear ban do
                myMapUpdate.restartAllocation();
                myRenderer.updateMatrix(myMapUpdate.getMatrixMap());
                myBoardList.repaint();

                //khoi tao bo dem thoi gian cho
                int timeOutCounter = 0;

                //no co gang phan bo cho den khi tim thay mot vi tri hop le cho tat ca cac thuyen hoac het thoi gian cho
                while (!myMapUpdate.randomAllocation()) {
                    try {
                        //doi 100ms de thuc hien vi tri ngau nhien tot hon va co bo dem 5s khi het thoi gian cho
                        Thread.sleep(MyDefines.DELAY_100MS);
                    } catch (java.lang.InterruptedException ie) {
                        JOptionPane.showMessageDialog(myFrame, "L???i c???a Thread. Vui l??ng nh???n l???i \"Ph??n b??? l???i\".");
                        myMapUpdate.restartAllocation();
                        myRenderer.updateMatrix(myMapUpdate.getMatrixMap());
                        myBoardList.repaint();
                        return;
                    }
                    timeOutCounter += 100;
                    //het thoi gian 5s de ngung thu
                    if (timeOutCounter >= MyDefines.DELAY_5S) {
                        JOptionPane.showMessageDialog(myFrame, "L???i h???t th???i gian ch???. Vui l??ng nh???n l???i \"Ph??n b??? m???i\".");
                        myMapUpdate.restartAllocation();
                        myRenderer.updateMatrix(myMapUpdate.getMatrixMap());
                        myBoardList.repaint();
                        return;
                    }
                    myMapUpdate.restartAllocation();
                    myRenderer.updateMatrix(myMapUpdate.getMatrixMap());
                    myBoardList.repaint();
                }
                writeOutputMessage(" - T??u c???u b???n ???? ???????c ph??n b???, b???n b?? th??? b???t ?????u ch??i ngay b??y gi???!");
                myRenderer.updateMatrix(myMapUpdate.getMatrixMap());
                myBoardList.repaint();
            }
        });
    }

    private void initializeConnectionPanel() {
        JPanel connectionPanel = new JPanel();
        connectionPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "C???u h??nh k???t n???i:", TitledBorder.LEFT, TitledBorder.TOP, null, null));
        connectionPanel.setBounds(740, 129, 244, 178);
        getContentPane().add(connectionPanel);
        connectionPanel.setLayout(null);

        JRadioButton rdbtnPlayAsServer = new JRadioButton("L??m ch??? ph??ng");
        rdbtnPlayAsServer.setBounds(6, 17, 109, 23);
        connectionPanel.add(rdbtnPlayAsServer);

        JRadioButton rdbtnPlayAsClient = new JRadioButton("L??m ng?????i ch??i");
        rdbtnPlayAsClient.setBounds(6, 43, 109, 23);
        connectionPanel.add(rdbtnPlayAsClient);

        JRadioButton rdbtnPlayAgainstComputer = new JRadioButton("Ch??i v???i M??y");
        rdbtnPlayAgainstComputer.setSelected(true);
        rdbtnPlayAgainstComputer.setBounds(117, 17, 109, 23);
        connectionPanel.add(rdbtnPlayAgainstComputer);
        
        this.btnSendIP = new JButton("G???i l???i m???i");
        this.btnSendIP.setBounds(117, 43, 109, 23);
        connectionPanel.add(this.btnSendIP);
        btnSendIP.setEnabled(false);

        //restart ban bo ( dat tat ca thanh nuoc va clear cac vi tri)
        //xoa ban do cua chinh minh va ke dich
        this.btnSendIP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(PlayAgainsPC){
                    
                }else if(IsServer){
                    String sentence_to_server;

                    InetAddress ip = null;
                    try {
                        ip = Inet4Address.getLocalHost();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(BattleShip.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    sentence_to_server = ip.toString();
                    sentence_to_server = sentence_to_server.substring(sentence_to_server.indexOf("/") + 1, sentence_to_server.length());

                    //T???o socket cho client k???t n???i ?????n server qua ID address v?? port number
                    Socket clientSocket = null;
                    try {
                        clientSocket = new Socket("127.0.0.1", 4000);
                    } catch (IOException ex) {
                        Logger.getLogger(BattleShip.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //T???o OutputStream n???i v???i Socket
                    DataOutputStream outToServer = null;
                    try {
                        outToServer = new DataOutputStream(clientSocket.getOutputStream());
                    } catch (IOException ex) {
                        Logger.getLogger(BattleShip.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        //T???o inputStream n???i v???i Socket
                        BufferedReader inFromServer =
                                new BufferedReader(new
                                InputStreamReader(clientSocket.getInputStream()));
                    } catch (IOException ex) {
                        Logger.getLogger(BattleShip.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        //
                        //G???i chu???i k?? t??? t???i Server th??ng qua outputStream ???? n???i v???i Socket (??? tr??n)
                        outToServer.writeBytes(sentence_to_server + '\n');
                    } catch (IOException ex) {
                        Logger.getLogger(BattleShip.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        //????ng li??n k???t socket
                        clientSocket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(BattleShip.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    String sentence_from_server = null;
                    //T???o socket cho client k???t n???i ?????n server qua ID address v?? port number
                    Socket clientSocket = null;
                    try {
                        clientSocket = new Socket("127.0.0.1", 4000);
                    } catch (IOException ex) {
                        Logger.getLogger(BattleShip.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        //T???o OutputStream n???i v???i Socket
                        DataOutputStream outToServer =
                                new DataOutputStream(clientSocket.getOutputStream());
                    } catch (IOException ex) {
                        Logger.getLogger(BattleShip.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //T???o inputStream n???i v???i Socket
                    BufferedReader inFromServer = null;
                    try {
                        inFromServer = new BufferedReader(new
                                InputStreamReader(clientSocket.getInputStream()));
                    } catch (IOException ex) {
                        Logger.getLogger(BattleShip.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        //?????c tin t??? Server th??ng qua InputSteam ???? n???i v???i socket
                        sentence_from_server = inFromServer.readLine();
                    } catch (IOException ex) {
                        Logger.getLogger(BattleShip.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //print k???t qua ra m??n h??nh
                    JOptionPane.showMessageDialog(myFrame, "?????a ch??? IP ch??? ph??ng: " + sentence_from_server);

                    try {
                        //????ng li??n k???t socket
                        clientSocket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(BattleShip.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        ButtonGroup group1 = new ButtonGroup();
        group1.add(rdbtnPlayAsServer);
        group1.add(rdbtnPlayAsClient);
        group1.add(rdbtnPlayAgainstComputer);
        group1.add(btnSendIP);

        rdbtnPlayAsServer.addActionListener(this);
        rdbtnPlayAsClient.addActionListener(this);
        rdbtnPlayAgainstComputer.addActionListener(this);

        JSeparator separator = new JSeparator();
        separator.setBounds(6, 73, 226, 2);
        connectionPanel.add(separator);

        this.lblMyIpAddress = new JLabel("?????a ch??? IP c???a t??i:");
        this.lblMyIpAddress.setBounds(6, 86, 97, 14);
        connectionPanel.add(this.lblMyIpAddress);
        InetAddress ip = null;

        try {
            //lay dia chi IP cua may
            ip = Inet4Address.getLocalHost();
            this.serverIP = ip.toString();
            this.serverIP = this.serverIP.substring(this.serverIP.indexOf("/") + 1, this.serverIP.length());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        this.portTextField = new JTextField("9090");
        portTextField.setEnabled(false);
        this.portTextField.setBounds(123, 111, 109, 20);
        connectionPanel.add(this.portTextField);
        this.portTextField.setColumns(10);

        this.ipAddressTextField = new JTextField(serverIP);
        ipAddressTextField.setEnabled(false);
        this.ipAddressTextField.setBounds(6, 111, 109, 20);
        connectionPanel.add(this.ipAddressTextField);
        this.ipAddressTextField.setColumns(10);

        this.btnConnect = new JButton("V??n M???i");

        //Bat dau nghe ket noi
        this.btnConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //kiem tra xem co dang choi voi may khong
                if (PlayAgainsPC) {
                    if (myMapUpdate.isSelectionDone()) {
                        //tao server thread cua toi
                        mySinglePlayerObj = new SinglePlayerHandler(getMyClassObject());

                        //phan bo tau cu may
                        mySinglePlayerObj.AllocateShips();

                        //bat dau thread
                        mySinglePlayerObj.start();

                        //vo hieu hoa cac nut
                        btnConnect.setEnabled(false);
                        btnStopServer.setEnabled(true);
                        myMapUpdate.setMyTurn(true);
                        btnNewAllocation.setEnabled(false);
                        btnRestartAllocation.setEnabled(false);
                        rdbtnAutomaticAllocation.setEnabled(false);
                        rdbtnManualAllocation.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(myFrame, "B???n ph???i ph??n b??? l???i t??u c???a m??nh tr?????c khi b???t ?????u tr?? ch??i.");
                    }
                } else {
                    //kiem tra xem do co phai server khong
                    if (IsServer) {
                        //kiem tra xem dia chi Ip va cong co trong khong
                        if (ipAddressTextField.getText().equals("") || portTextField.getText().equals("")) {
                            JOptionPane.showMessageDialog(myFrame, "B???n ph???i ??i???n ?????a ch??? IP c???a Ch??? ph??ng v?? c???ng tr?????c khi b???t ?????u tr?? ch??i.");
                        } else {
                            //tao server thread cua toi
                            myServerObj = new MyServer(getMyClassObject());

                            //khoi dong socket
                            myServerObj.StartSocket(Integer.parseInt(portTextField.getText()), ipAddressTextField.getText());

                            //bat dau thread
                            myServerObj.start();

                            //vo hieu hoa cac nut
                            btnConnect.setEnabled(false);
                            btnStopServer.setEnabled(true);
                            myMapUpdate.setMyTurn(false);
                        }
                    } //choi nhu mot Client
                    else {
                        //kiem tra xem lua chon da xong chua (khong the bat dau ket noi khi chua phan bo tau) 
                        if (myMapUpdate.isSelectionDone()) {
                            //kiem tra xem dia chi Ip va cong co trong khong
                            if (ipAddressTextField.getText().equals("") || portTextField.getText().equals("")) {
                                JOptionPane.showMessageDialog(myFrame, "B???n ph???i ??i???n ?????a ch??? IP c???a Ch??? ph??ng v?? c???ng tr?????c khi b???t ?????u tr?? ch??i.");
                            } else {
                                //thu bat dau ket noi toi server
                                if (myClientObj.StartConnection(ipAddressTextField.getText(), Integer.parseInt(portTextField.getText()))) {
                                    //vo hieu hoa cac nut
                                    btnConnect.setEnabled(false);
                                    btnStopServer.setEnabled(true);
                                    myMapUpdate.setMyTurn(false);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(myFrame, "B???n ph???i ph??n b??? l???i t??u c???a m??nh tr?????c khi b???t ?????u tr?? ch??i.");
                        }
                    }
                }
            }
        });
        this.btnConnect.setBounds(6, 142, 109, 23);
        connectionPanel.add(btnConnect);

        JLabel lblPort = new JLabel("S??? C???ng:");
        lblPort.setBounds(123, 86, 46, 14);
        connectionPanel.add(lblPort);

        this.btnStopServer = new JButton("D???ng Game");
        this.btnStopServer.setEnabled(false);

        //dung lang nghe ket noi
        this.btnStopServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //kiem tra xem co dang choi voi may khong
                if (PlayAgainsPC) {
                    StopSingleGame();

                    rdbtnAutomaticAllocation.setEnabled(true);
                    rdbtnManualAllocation.setEnabled(true);
                    btnRestartAllocation.setEnabled(true);
                    if (rdbtnAutomaticAllocation.isSelected()) {
                        btnNewAllocation.setEnabled(true);
                    }
                } else {
                    //kiem tra xem no co phai la server khong
                    if (IsServer) {
                        //kiem tra xem co Client nao ket noi khong
                        if (myServerObj.hasClient()) {
                            //thong bao cho client
                            myServerObj.SendMessage("B");

                            //ngung giao tiep
                            myServerObj.StopCommunication();

                            //ngung Server
                            myServerObj.StopServer();
                        } else {
                            //khong Client, thi chi can dung Server
                            myServerObj.StopServer();
                        }
                        //ngung thread
                        myServerObj.stop();

                        //vo hieu hoa cac nut
                        btnConnect.setEnabled(true);
                        btnStopServer.setEnabled(false);
                    } //choi voi tu cach Client
                    else {
                        //thong bao server
                        myClientObj.SendMessage("B");

                        //ngung client
                        myClientObj.StopClient();

                        //vo hieu hoa cac nut
                        btnConnect.setEnabled(true);
                        btnStopServer.setEnabled(false);
                    }
                    myMapUpdate.setMyTurn(false);
                }
            }
        });
        this.btnStopServer.setBounds(123, 142, 109, 23);
        connectionPanel.add(this.btnStopServer);
    }

    private void initializeChatPanel() {
        JPanel chatHistoryPanel = new JPanel();
        chatHistoryPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "L???ch s??? Chat", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        chatHistoryPanel.setBounds(739, 318, 245, 207);
        getContentPane().add(chatHistoryPanel);
        chatHistoryPanel.setLayout(null);

        this.chatKit = new HTMLEditorKit();
        this.chatDoc = new HTMLDocument();

        this.chatHistory = new JTextPane();
        this.chatHistory.setContentType("text/html");
        this.chatHistory.setEditorKit(this.chatKit);
        this.chatHistory.setDocument(this.chatDoc);
        this.chatHistory.setEditable(false);
        this.chatHistory.setBounds(10, 21, 196, 123);

        JScrollPane chatScrollPane = new JScrollPane(chatHistory);

        chatHistoryPanel.setLayout(new BorderLayout());
        chatHistoryPanel.add(chatScrollPane, BorderLayout.CENTER);

        JPanel sendMsgPanel = new JPanel();
        sendMsgPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        sendMsgPanel.setBounds(740, 536, 244, 75);
        getContentPane().add(sendMsgPanel);

        this.btnSend = new JButton("G???i");
        this.btnSend.setEnabled(false);
        this.btnSend.setBounds(177, 11, 57, 53);
        this.btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SendMessage2Enemy();
            }
        });

        JScrollPane charHistoryScrollPane = new JScrollPane();

        sendMsgPanel.setLayout(new BorderLayout());
        sendMsgPanel.add(charHistoryScrollPane, BorderLayout.CENTER);

        this.sendTxtField = new JTextPane();
        this.sendTxtField.setEnabled(false);
        this.sendTxtField.setText("N??i chuy???n v???i k??? ?????ch c???a b???n...");
        this.sendTxtField.setContentType("text/html");
        charHistoryScrollPane.setViewportView(this.sendTxtField);

        this.sendTxtField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    //nhay toi nut gui
                    btnSend.requestFocusInWindow();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        /*
         * Nhap ban do de xu ly phim ENTER
         * SHIFT + ENTER = new line
         * ENTER = send message
         */
        InputMap input = this.sendTxtField.getInputMap();
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        KeyStroke shiftEnter = KeyStroke.getKeyStroke("shift ENTER");
        input.put(shiftEnter, INSERT_BREAK);
        input.put(enter, TEXT_SEND);

        ActionMap actions = this.sendTxtField.getActionMap();
        actions.put(TEXT_SEND, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SendMessage2Enemy();
            }
        });

        sendMsgPanel.add(this.btnSend, BorderLayout.SOUTH);
    }

    private void initializeComponents() {
        JSeparator separatorH = new JSeparator();
        separatorH.setBounds(10, 391, 719, 2);
        getContentPane().add(separatorH);

        JSeparator separatorV = new JSeparator();
        separatorV.setOrientation(SwingConstants.VERTICAL);
        separatorV.setBounds(728, 11, 2, 600);
        getContentPane().add(separatorV);

        getContentPane().setLayout(null);

        setTitle("BattleShip");
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.myMapUpdate.getApplicationPath() + "\\icon.png"));
        setResizable(false);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void splashScreenHandler() {
        final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash != null) {
            Graphics2D g = splash.createGraphics();
            int progressBar = 0;
            if (g != null) {
                for (int i = 0; i < 100; i++) {
                    if ((i % 25) == 0) {
                        progressBar += 50;
                    }
                    renderSplashFrame(g, i, progressBar);
                    splash.update();
                    try {
                        Thread.sleep(90);
                    } catch (InterruptedException e) {
                    }
                }
            }
            splash.close();
        }
    }

    private void renderSplashFrame(Graphics2D g, int frame, int progressBar) {
        final String[] comps = {".", "..", "...", "....", ".....", "......", ".......", "........", ".........", "..........", "...........", "...........", "...........", "...........", "..........."};
        g.setComposite(AlphaComposite.Clear);
        g.setPaintMode();
        g.setColor(Color.GRAY);
        g.drawRect(163, 85, 200, 7);
        g.setColor(Color.RED);
        g.fillRect(163, 85, progressBar, 7);
        g.setColor(Color.BLACK);
        g.drawString("Loading " + comps[(frame / 5) % 15], 163, 105);
    }

    //lang nghe nut radio
    public void actionPerformed(ActionEvent e) {
        //Nut radio Phan bo tu dong duoc nhan
        if (e.getActionCommand().equals("Ph??n b??? t??? ?????ng")) {
            this.myMapUpdate.restartAllocation();
            this.myRenderer.updateMatrix(this.myMapUpdate.getMatrixMap());
            this.myBoardList.repaint();
            this.btnNewAllocation.setEnabled(true);
        } //Nut radio Phan bo thu cong duoc nhan
        else if (e.getActionCommand().equals("Ph??n b??? th??? c??ng")) {
            this.myMapUpdate.restartAllocation();
            this.myRenderer.updateMatrix(this.myMapUpdate.getMatrixMap());
            this.myBoardList.repaint();
            this.btnNewAllocation.setEnabled(false);
        } //Nut radio Choi nhu server duoc nhan
        else if (e.getActionCommand().equals("L??m ch??? ph??ng")) {
            this.btnSendIP.setEnabled(true);
            this.btnConnect.setText("T???o ph??ng");
            this.btnStopServer.setText("Tho??t ph??ng");
            this.lblMyIpAddress.setText("?????a ch??? IP c???a t??i:");
            this.ipAddressTextField.setText(serverIP);
            this.ipAddressTextField.setEnabled(true);
            this.portTextField.setEnabled(true);
            this.PlayAgainsPC = false;
            this.IsServer = true;
        } //Nut radio Choi nhu client duoc nhan
        else if (e.getActionCommand().equals("L??m ng?????i ch??i")) {
            this.btnSendIP.setEnabled(true);
            this.btnConnect.setText("T??m ph??ng");
            this.btnStopServer.setText("Tho??t ph??ng");
            this.lblMyIpAddress.setText("?????a ch??? IP ch??? ph??ng:");
            this.ipAddressTextField.setEnabled(true);
            this.portTextField.setEnabled(true);
            this.PlayAgainsPC = false;
            this.IsServer = false;
        } //Nut radio Choi voi PC duoc nhan
        else {
            this.btnSendIP.setEnabled(false);
            this.btnConnect.setText("Game M???i");
            this.btnStopServer.setText("D???ng Game");
            this.ipAddressTextField.setEnabled(false);
            this.portTextField.setEnabled(false);
            this.PlayAgainsPC = true;
            this.IsServer = false;
        }
    }

    //viet tin nhan vao bang dieu khien dau ra
    public void writeOutputMessage(String msg) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();

        try {
            this.outputKit.insertHTML(this.outputDoc, this.outputDoc.getLength(), dateFormat.format(date) + msg, 0, 0, null);
        } catch (BadLocationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        this.outputTextField.setCaretPosition(this.outputDoc.getLength());
    }

    //gui tin nhan chat
    private void SendMessage2Enemy() {
        //viet vao lich su cua rieng toi
        writeChatMessage(sendTxtField.getText(), 0);

        //gui cho ke dich
        if (this.IsServer) {
            this.myServerObj.SendMessage("M:" + this.sendTxtField.getText());
        } else {
            this.myClientObj.SendMessage("M:" + this.sendTxtField.getText());
        }
        this.sendTxtField.setText("");
    }

    /*
     * Viet tin nhan vao lich su tro chuyen
     * flag=0 - tin nhan cua toi
     * flaf=1 - tin nhan cua ke dich
     */
    public void writeChatMessage(String msg, int flag) {
        String header = "";
        switch (flag) {
            case 0: {
                header = "<font color=\"blue\">T??i: </font>";
                break;
            }
            case 1: {
                header = "<font color=\"red\">K??? ?????ch: </font>";
                break;
            }
        }

        try {
            this.chatKit.insertHTML(this.chatDoc, this.chatDoc.getLength(), header, 0, 0, null);
            this.chatKit.insertHTML(this.chatDoc, this.chatDoc.getLength(), msg, 0, 0, null);
        } catch (BadLocationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        this.chatHistory.setCaretPosition(this.chatDoc.getLength());
    }

    //son lai bang cua toi
    public void repaintMyBoard() {
        this.myBoardList.repaint();
    }

    //son lai bang cua ke dich
    public void repaintMyEnemyBoard() {
        this.myEnemyList.repaint();
    }

    //bat/tat cac nut phan bo
    public void enableShipAllocation(boolean value) {
        if (value == true && this.rdbtnManualAllocation.isSelected()) {
            this.btnNewAllocation.setEnabled(false);
        } else {
            this.btnNewAllocation.setEnabled(value);
        }
        this.rdbtnAutomaticAllocation.setEnabled(value);
        this.rdbtnManualAllocation.setEnabled(value);
        this.btnRestartAllocation.setEnabled(value);
    }

    //khoi dong lai phan bo
    public void restartAllocations() {
        this.myMapUpdate.restartAllocation();
        this.myRenderer.updateMatrix(this.myMapUpdate.getMatrixMap());
        this.myBoardList.repaint();

        this.myEnemyMapUpdate.restartAllocation();
        this.myEnemyRenderer.updateMatrix(this.myEnemyMapUpdate.getMatrixMap());
        this.myEnemyList.repaint();
    }

    //dung choi 1 nguoi
    public void StopSingleGame() {
        this.mySinglePlayerObj.SetMyTurn(false);
        this.mySinglePlayerObj.StopGame();

        this.btnConnect.setEnabled(true);
        this.btnStopServer.setEnabled(false);
        this.myMapUpdate.setMyTurn(false);
    }

    //tra ve doi tuong BattleShip class
    public BattleShip getMyClassObject() {
        return this;
    }

    //tra ve doi tuong MapUpdateHandler cua toi
    public MapUpdateHandler getMyMapUpdate() {
        return this.myMapUpdate;
    }

    //tra ve doi tuong MapUpdateHandler cua ke dich
    public MapUpdateHandler getMyEnemyMapUpdate() {
        return this.myEnemyMapUpdate;
    }

    //tra ve server object
    public MyServer getMyServer() {
        return this.myServerObj;
    }

    //tra ve client object
    public MyClient getMyClient() {
        return this.myClientObj;
    }

    //kiem tra xem nguoi cho co phai la server khong
    public boolean IsServer() {
        return this.IsServer;
    }

    //tra ve neu dang choi voi PC
    public boolean isSinglePlayer() {
        return this.PlayAgainsPC;
    }

    //tra ve dice obiect (gui de chon 1 so de quyet dinh nguoi choi truoc)
    public DiceHandler getDiceHandler() {
        return this.myDiceHandler;
    }

    //tra ve giao dien GameOver
    public GameOverGui getGameOverGui() {
        return this.myGOScreen;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            //xu ly ngoai le
        } catch (ClassNotFoundException e) {
            //xu ly ngoai le
        } catch (InstantiationException e) {
            //xu ly ngoai le
        } catch (IllegalAccessException e) {
            //xu ly ngoai le
        }
        new BattleShip();
    }
}

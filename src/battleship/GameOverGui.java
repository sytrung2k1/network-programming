/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;

import javax.swing.JDialog;
import java.awt.Toolkit;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;

/**
 *
 * @author Trung
 */
public class GameOverGui extends JDialog {

    private static final long serialVersionUID = 1L;

    private BattleShip gui;
    private String appPath;
    private JLabel congratLbl;
    private JLabel congratMsgLbl;
    private JLabel picLbl;

    public GameOverGui(String appPath, BattleShip myGui) {
        this.gui = myGui;
        this.appPath = appPath;
        initializeComponents();
    }

    private void initializeComponents() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.appPath + "icon.png"));
        setTitle("Game Over");
        setAlwaysOnTop(true);
        setResizable(false);
        setSize(600, 600);
        getContentPane().setLayout(null);

        this.congratLbl = new JLabel("XIN CHÚC MỪNG!");
        this.congratLbl.setHorizontalAlignment(SwingConstants.CENTER);
        this.congratLbl.setFont(new Font("Arial", Font.BOLD, 28));
        this.congratLbl.setBounds(10, 11, 574, 33);
        getContentPane().add(this.congratLbl);

        this.congratMsgLbl = new JLabel("Bạn đã thắng, nhưng đối thủ có thể sẽ tìm cách trả thù!");
        this.congratMsgLbl.setHorizontalAlignment(SwingConstants.CENTER);
        this.congratMsgLbl.setFont(new Font("Arial", Font.PLAIN, 22));
        this.congratMsgLbl.setBounds(20, 55, 574, 33);
        getContentPane().add(this.congratMsgLbl);

        this.picLbl = new JLabel("");
        this.picLbl.setHorizontalAlignment(SwingConstants.CENTER);
        this.picLbl.setIcon(new ImageIcon(this.appPath + "win.jpg"));
        this.picLbl.setBounds(10, 99, 574, 300);
        getContentPane().add(this.picLbl);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 410, 574, 2);
        getContentPane().add(separator);

        JLabel logoLbl = new JLabel("");
        logoLbl.setIcon(new ImageIcon(this.appPath + "logo.png"));
        logoLbl.setBounds(10, 423, 317, 61);
        getContentPane().add(logoLbl);

        JSeparator separator1 = new JSeparator();
        separator1.setBounds(10, 495, 574, 2);
        getContentPane().add(separator1);

        JButton btnNewGame = new JButton("Ván mới");
        btnNewGame.setFont(new Font("Arial", Font.PLAIN, 18));
        btnNewGame.setBounds(10, 508, 264, 53);
        btnNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                gui.enableShipAllocation(true);
                gui.restartAllocations();
                gui.getDiceHandler().clearResults();
                if (gui.isSinglePlayer()) {
                    gui.StopSingleGame();
                    gui.writeOutputMessage(" - Bạn phải phân bố lại tàu của mình và nhấn Ván mới!");
                } else {
                    if (gui.IsServer()) {
                        gui.writeOutputMessage(" - Phân bố lại tàu của mình và chờ đợi đối thủ của bạn!");
                    } else {
                        gui.getMyClient().SendMessage("B");
                        gui.getMyClient().StopClient();
                        gui.btnConnect.setEnabled(true);
                        gui.btnStopServer.setEnabled(false);
                        gui.writeOutputMessage(" - Bạn phải phân bố lại tàu cảu mình và kết nối lại với chủ phòng!");
                    }
                }
                dispose();
            }
        });
        getContentPane().add(btnNewGame);

        JButton btnClose = new JButton("Đóng");
        btnClose.setFont(new Font("Arial", Font.PLAIN, 18));
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                gui.enableShipAllocation(true);
                gui.restartAllocations();
                gui.getDiceHandler().clearResults();
                if (gui.isSinglePlayer()) {
                    gui.StopSingleGame();
                    gui.writeOutputMessage(" - Cảm ơn bạn đã chơi với BattleShip!");
                    gui.writeOutputMessage(" - Nếu bạn quyết định chơi ván mới. Bạn phải phân bố lại tàu và nhấn Ván mới!");
                } else {
                    if (gui.IsServer()) {
                        if (gui.getMyServer().hasClient()) {
                            gui.getMyServer().SendMessage("B");
                            gui.getMyServer().StopCommunication();
                            gui.getMyServer().StopServer();
                        } else {
                            gui.getMyServer().StopServer();
                        }
                        gui.getMyServer().stop();
                        gui.btnConnect.setEnabled(true);
                        gui.btnStopServer.setEnabled(false);
                        gui.writeOutputMessage(" - Cảm ơn bạn đã chơi với BattleShip!");
                        gui.writeOutputMessage(" - Nếu bạn quyết định chơi ván mới. Bạn phải phân bố lại tàu và bắt đầu tạo phòng mới!");
                    } else {
                        gui.getMyClient().SendMessage("B");
                        gui.getMyClient().StopClient();
                        gui.btnConnect.setEnabled(true);
                        gui.btnStopServer.setEnabled(false);
                        gui.writeOutputMessage(" - Cảm ơn bạn đã chơi với BattleShip!");
                        gui.writeOutputMessage(" - Nếu bạn quyết định chơi ván mới. Bạn phải phân bố lại tàu và vào lại phòng mới!");
                    }
                }
                dispose();
            }
        });
        btnClose.setBounds(320, 508, 264, 53);
        getContentPane().add(btnClose);

        JLabel lblIfYouWant = new JLabel("Nếu bạn muốn biết thêm về lịch sử của game BattleShip, hãy thử vào trang web này:");
        lblIfYouWant.setBounds(337, 434, 247, 14);
        getContentPane().add(lblIfYouWant);

        JButton urlBtn = new JButton();
        urlBtn.setText("<HTML><FONT color=\"#000099\"><U>https://en.wikipedia.org/wiki/Battleship_(game)</U></FONT></HTML>");

        urlBtn.setHorizontalAlignment(SwingConstants.LEFT);
        urlBtn.setBorderPainted(false);
        urlBtn.setOpaque(false);
        urlBtn.setBackground(Color.LIGHT_GRAY);
        urlBtn.setToolTipText("https://en.wikipedia.org/wiki/Battleship_(game)");
        urlBtn.setHorizontalAlignment(SwingConstants.CENTER);
        urlBtn.setBounds(337, 459, 247, 25);
        urlBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        URI uri = null;
                        try {
                            uri = new URI("https://en.wikipedia.org/wiki/Battleship_(game)");
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        if (uri != null) {
                            Desktop.getDesktop().browse(uri);
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }
        );
        getContentPane().add(urlBtn);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                gui.enableShipAllocation(true);
                gui.restartAllocations();
                gui.getDiceHandler().clearResults();
                if (gui.isSinglePlayer()) {
                    gui.StopSingleGame();
                    gui.writeOutputMessage(" - Cảm ơn bạn đã chơi với BattleShip!");
                    gui.writeOutputMessage(" - Nếu bạn quyết định chơi ván mới. Bạn phải phân bố lại tàu và nhấn Ván mới!");
                } else {
                    if (gui.IsServer()) {
                        if (gui.getMyServer().hasClient()) {
                            gui.getMyServer().SendMessage("B");
                            gui.getMyServer().StopCommunication();
                            gui.getMyServer().StopServer();
                        } else {
                            gui.getMyServer().StopServer();
                        }
                        gui.getMyServer().stop();
                        gui.btnConnect.setEnabled(true);
                        gui.btnStopServer.setEnabled(false);
                        gui.writeOutputMessage(" - Cảm ơn bạn đã chơi với BattleShip!");
                        gui.writeOutputMessage(" - Nếu bạn quyết định chơi ván mới. Bạn phải phân bố lại tàu và bắt đầu tạo phòng mới!");
                    } else {
                        gui.getMyClient().SendMessage("B");
                        gui.getMyClient().StopClient();
                        gui.btnConnect.setEnabled(true);
                        gui.btnStopServer.setEnabled(false);
                        gui.writeOutputMessage(" - Cảm ơn bạn đã chơi với BattleShip!");
                        gui.writeOutputMessage(" - Nếu bạn quyết định chơi ván mới. Bạn phải phân bố lại tàu và vào lại phòng mới!");
                    }
                }
            }
        });
    }

    public void ShowGameOver(int flag) {
        switch (flag) {
            case 0: //lose
            {
                this.congratLbl.setText("Bạn đã thua!");
                this.congratMsgLbl.setText("Bạn đã thua. Bạn có thể thử lại!");
                this.picLbl.setIcon(new ImageIcon(this.appPath + "lose.jpg"));
                break;
            }
            case 1: //win
            {
                this.congratLbl.setText("Xin chúc mừng!");
                this.congratMsgLbl.setText("Bạn đã thắng, nhưng đối thủ có thể sẽ tìm cách trả thù!");
                this.picLbl.setIcon(new ImageIcon(this.appPath + "win.jpg"));
                break;
            }
        }
        setLocationRelativeTo(this.gui);
        show();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Trung
 */
public class DiceHandler {

    private int myNumber;
    private int enemyNumber;
    private JFrame mainFrame;

    public DiceHandler(JFrame mainFrame) {
        this.myNumber = -1;
        this.enemyNumber = -1;
        this.mainFrame = mainFrame;
    }

    public void ShowMyDice() {
        Icon dice = UIManager.getIcon("OptionPane.questionIcon");
        Object[] possibilities = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer i = (Integer) JOptionPane.showOptionDialog(this.mainFrame, "Chọn một số:", "Ai là người chơi trước?", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, dice, possibilities, "Numbers");

        this.myNumber = i + 1;
    }

    public void setEnemyNumber(int number) {
        this.enemyNumber = number;
    }

    public int getMyNumber() {
        return this.myNumber;
    }

    public int getEnemyNumber() {
        return this.enemyNumber;
    }

    public int whoStarts() {
        if (this.myNumber > this.enemyNumber) {
            return 0;
        } else if (this.myNumber < this.enemyNumber) {
            return 1;
        } else {
            return -1;
        }
    }

    public void clearResults() {
        this.myNumber = -1;
        this.enemyNumber = -1;
    }
}

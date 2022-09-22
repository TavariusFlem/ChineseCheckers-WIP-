import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.util.Random;

//================== IMPORTS ==================================
public class ChineseCheckers
{
public static void main(String[] args)
    {
    new ChineseCheckersGameFrame();
    }
}
class ChineseCheckersGameFrame extends JFrame
                               implements ActionListener
    {
//=================== DATA MEMBERS ===========================
    Board               g;
    //ConnectionToServer    cts;

    int                 numberOfPlayers = 0;
    int                 randomNumber;

    Random              r;

    JLabel              turnLabel;
    JLabel              playerColorLabel;

    String              playerColor;
    StringBuilder       currentTurnColor;

    String              playerOrder[];

    JPanel              boardPanel;

    int                 playTypeSelection;

    boolean             isOnline;

//************************************************************
    ChineseCheckersGameFrame()
        {
        Object[] options = {"Local Play","Online Play"};
        playTypeSelection = JOptionPane.showOptionDialog(this, "Online or local play?", "Method of play", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if(playTypeSelection == -1)
            {
            System.exit(0);
            }

        setUpGUI();
        setupMainFrame(40, 40,"Chinese Checkers");

        NumberOfPlayersSelectionDialog playerDialog = new NumberOfPlayersSelectionDialog(this);
        isOnline = false;
        //waiting for players to join session dialog
        if(playTypeSelection == 1)
            {
            isOnline = true;
            WaitingForPlayersDialog waitingDialog = new WaitingForPlayersDialog();
            }
        
        if(numberOfPlayers == 2)
            {
            playerOrder = new String[2];
            playerOrder[0] = "red";
            playerOrder[1] = "orange";
            }
        else if (numberOfPlayers == 3)
            {
            playerOrder = new String[3];
            playerOrder[0] = "green";
            playerOrder[1] = "blue";
            playerOrder[2] = "orange";
            }
        else if (numberOfPlayers == 4)
            {
            playerOrder = new String[4];
            playerOrder[0] = "green";
            playerOrder[1] = "blue";
            playerOrder[2] = "yellow";
            playerOrder[3] = "purple";

            }
        else
            {
            playerOrder = new String[6];
            playerOrder[0] = "red";
            playerOrder[1] = "green";
            playerOrder[2] = "blue";
            playerOrder[3] = "yellow";
            playerOrder[4] = "purple";
            playerOrder[5] = "orange";
            }

        //TURN ORDER
        r = new Random();
        randomNumber = r.nextInt(numberOfPlayers);

        if(playTypeSelection == 1)
            {
            playerColorLabel.setText("You are " + playerOrder[randomNumber]);
            }
        turnLabel.setText("Current Turn: " + playerOrder[randomNumber]);
        
        playerColor = playerOrder[randomNumber];

        currentTurnColor = new StringBuilder(playerOrder[randomNumber]);

        //SET UP BOARD
        setUpBoard(numberOfPlayers);

        createBoardGUI();
        repaint();
        revalidate();
        }// end of default constructor
//======================== SET PLAYER COUNT ===============================================================================
    void setPlayerCount(String numberOfPlayersString)
        {
        numberOfPlayers = Integer.parseInt(numberOfPlayersString);
        }//end of set player count
//======================== SET UP GUI =====================================================================================
    void setUpGUI()
        {
        //---------- MAIN PANEL -----------------------------
        JPanel mainPanel;
        mainPanel = new JPanel(new BorderLayout());
        this.add(mainPanel);

        //---------- BOARD PANEL ----------------------------
        boardPanel = new JPanel(null);
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        //---------- SIDE PANEL -----------------------------
        JPanel sidePanel;
        sidePanel = new JPanel(new BorderLayout());
        sidePanel.setBackground(Color.GRAY);
        mainPanel.add(sidePanel, BorderLayout.EAST);


        //---------- TURN SIGNIFIER PANEL -------------------
        JPanel turnSignifierPanel;
        turnSignifierPanel = new JPanel(new BorderLayout());
        sidePanel.add(turnSignifierPanel,BorderLayout.SOUTH);
        turnSignifierPanel.setBackground(Color.GRAY);

        //---------- TURN LABEL -----------------------------
        turnLabel = new JLabel("Current turn:");
        turnSignifierPanel.add(turnLabel, BorderLayout.NORTH);
        turnLabel.setBackground(Color.DARK_GRAY);
        turnLabel.setForeground(Color.WHITE);

        //---------- END TURN BUTTON ------------------------
        JButton endTurnButton;
        endTurnButton = new JButton("endTurn");
        endTurnButton.addActionListener(this);
        endTurnButton.setActionCommand("END_TURN");
        getRootPane().setDefaultButton(endTurnButton);
        endTurnButton.setBackground(Color.DARK_GRAY);
        endTurnButton.setForeground(Color.WHITE);
        turnSignifierPanel.add(endTurnButton,BorderLayout.SOUTH);

        //----------- PlayerColorLabel -----------------------
        playerColorLabel = new JLabel();
        playerColorLabel.setForeground(Color.WHITE);
        sidePanel.add(playerColorLabel, BorderLayout.NORTH);
        }//end of setUpGUI

//======================== ACTION PERFORMED ===============================================================================
    public void actionPerformed(ActionEvent cmd)
        {
        if(cmd.getActionCommand().equals("END_TURN"))
            {
            this.doEndTurn();
            }
        }//end of actionPerformed

//======================== DO END TURN ====================================================================================
    void doEndTurn()
        {
        randomNumber++;
        if(randomNumber > playerOrder.length-1)
            {
            randomNumber = 0;
            }
        turnLabel.setText("Current Turn: " + playerOrder[randomNumber]);
        currentTurnColor.replace(0, currentTurnColor.length(), playerOrder[randomNumber]);

        }
//======================== SET UP BOARD ===================================================================================
    void setUpBoard(int numberOfPlayers)
        {
        g = new Board(numberOfPlayers, currentTurnColor, this);
        g.addEdge(g.gameSpaces.elementAt(1),g.gameSpaces.elementAt(2));
        g.addEdge(g.gameSpaces.elementAt(1),g.gameSpaces.elementAt(3));

        g.addEdge(g.gameSpaces.elementAt(2),g.gameSpaces.elementAt(3)); 
        g.addEdge(g.gameSpaces.elementAt(2),g.gameSpaces.elementAt(6));
        g.addEdge(g.gameSpaces.elementAt(2),g.gameSpaces.elementAt(7));

        g.addEdge(g.gameSpaces.elementAt(3),g.gameSpaces.elementAt(7));
        g.addEdge(g.gameSpaces.elementAt(3),g.gameSpaces.elementAt(8));

        g.addEdge(g.gameSpaces.elementAt(4),g.gameSpaces.elementAt(5));
        g.addEdge(g.gameSpaces.elementAt(4),g.gameSpaces.elementAt(11));

        g.addEdge(g.gameSpaces.elementAt(5),g.gameSpaces.elementAt(6));
        g.addEdge(g.gameSpaces.elementAt(5),g.gameSpaces.elementAt(11));
        g.addEdge(g.gameSpaces.elementAt(5),g.gameSpaces.elementAt(12));

        g.addEdge(g.gameSpaces.elementAt(6),g.gameSpaces.elementAt(7));
        g.addEdge(g.gameSpaces.elementAt(6),g.gameSpaces.elementAt(8));
        g.addEdge(g.gameSpaces.elementAt(6),g.gameSpaces.elementAt(12));
        g.addEdge(g.gameSpaces.elementAt(6),g.gameSpaces.elementAt(13));

        g.addEdge(g.gameSpaces.elementAt(7),g.gameSpaces.elementAt(8));
        g.addEdge(g.gameSpaces.elementAt(7),g.gameSpaces.elementAt(13));
        g.addEdge(g.gameSpaces.elementAt(7),g.gameSpaces.elementAt(14));

        g.addEdge(g.gameSpaces.elementAt(8),g.gameSpaces.elementAt(9));
        g.addEdge(g.gameSpaces.elementAt(8),g.gameSpaces.elementAt(14));
        g.addEdge(g.gameSpaces.elementAt(8),g.gameSpaces.elementAt(15));

        g.addEdge(g.gameSpaces.elementAt(9),g.gameSpaces.elementAt(10));
        g.addEdge(g.gameSpaces.elementAt(9),g.gameSpaces.elementAt(15));
        g.addEdge(g.gameSpaces.elementAt(9),g.gameSpaces.elementAt(16));

        g.addEdge(g.gameSpaces.elementAt(10),g.gameSpaces.elementAt(16));

        g.addEdge(g.gameSpaces.elementAt(11),g.gameSpaces.elementAt(12));
        g.addEdge(g.gameSpaces.elementAt(11),g.gameSpaces.elementAt(17));

        g.addEdge(g.gameSpaces.elementAt(12),g.gameSpaces.elementAt(13));
        g.addEdge(g.gameSpaces.elementAt(12),g.gameSpaces.elementAt(17));
        g.addEdge(g.gameSpaces.elementAt(12),g.gameSpaces.elementAt(18));

        g.addEdge(g.gameSpaces.elementAt(13),g.gameSpaces.elementAt(14));
        g.addEdge(g.gameSpaces.elementAt(13),g.gameSpaces.elementAt(18));
        g.addEdge(g.gameSpaces.elementAt(13),g.gameSpaces.elementAt(19));

        g.addEdge(g.gameSpaces.elementAt(14),g.gameSpaces.elementAt(15));
        g.addEdge(g.gameSpaces.elementAt(14),g.gameSpaces.elementAt(19));
        g.addEdge(g.gameSpaces.elementAt(14),g.gameSpaces.elementAt(20));

        g.addEdge(g.gameSpaces.elementAt(15),g.gameSpaces.elementAt(16));
        g.addEdge(g.gameSpaces.elementAt(15),g.gameSpaces.elementAt(20));
        g.addEdge(g.gameSpaces.elementAt(15),g.gameSpaces.elementAt(21));

        g.addEdge(g.gameSpaces.elementAt(16),g.gameSpaces.elementAt(21));

        g.addEdge(g.gameSpaces.elementAt(17),g.gameSpaces.elementAt(18));
        g.addEdge(g.gameSpaces.elementAt(17),g.gameSpaces.elementAt(22));
        g.addEdge(g.gameSpaces.elementAt(17),g.gameSpaces.elementAt(23));

        g.addEdge(g.gameSpaces.elementAt(18),g.gameSpaces.elementAt(19));
        g.addEdge(g.gameSpaces.elementAt(18),g.gameSpaces.elementAt(23));
        g.addEdge(g.gameSpaces.elementAt(18),g.gameSpaces.elementAt(24));

        g.addEdge(g.gameSpaces.elementAt(19),g.gameSpaces.elementAt(20));
        g.addEdge(g.gameSpaces.elementAt(19),g.gameSpaces.elementAt(24));
        g.addEdge(g.gameSpaces.elementAt(19),g.gameSpaces.elementAt(25));

        g.addEdge(g.gameSpaces.elementAt(20),g.gameSpaces.elementAt(21));
        g.addEdge(g.gameSpaces.elementAt(20),g.gameSpaces.elementAt(25));
        g.addEdge(g.gameSpaces.elementAt(20),g.gameSpaces.elementAt(26));

        g.addEdge(g.gameSpaces.elementAt(21),g.gameSpaces.elementAt(26));
        g.addEdge(g.gameSpaces.elementAt(21),g.gameSpaces.elementAt(27));

        g.addEdge(g.gameSpaces.elementAt(22),g.gameSpaces.elementAt(23));
        g.addEdge(g.gameSpaces.elementAt(22),g.gameSpaces.elementAt(28));
        g.addEdge(g.gameSpaces.elementAt(22),g.gameSpaces.elementAt(29));

        g.addEdge(g.gameSpaces.elementAt(23),g.gameSpaces.elementAt(24));
        g.addEdge(g.gameSpaces.elementAt(23),g.gameSpaces.elementAt(29));
        g.addEdge(g.gameSpaces.elementAt(23),g.gameSpaces.elementAt(30));

        g.addEdge(g.gameSpaces.elementAt(24),g.gameSpaces.elementAt(25));
        g.addEdge(g.gameSpaces.elementAt(24),g.gameSpaces.elementAt(30));
        g.addEdge(g.gameSpaces.elementAt(24),g.gameSpaces.elementAt(31));

        g.addEdge(g.gameSpaces.elementAt(25),g.gameSpaces.elementAt(26));
        g.addEdge(g.gameSpaces.elementAt(25),g.gameSpaces.elementAt(31));
        g.addEdge(g.gameSpaces.elementAt(25),g.gameSpaces.elementAt(32));

        g.addEdge(g.gameSpaces.elementAt(26),g.gameSpaces.elementAt(27));
        g.addEdge(g.gameSpaces.elementAt(26),g.gameSpaces.elementAt(32));
        g.addEdge(g.gameSpaces.elementAt(26),g.gameSpaces.elementAt(33));

        g.addEdge(g.gameSpaces.elementAt(27),g.gameSpaces.elementAt(33));
        g.addEdge(g.gameSpaces.elementAt(27),g.gameSpaces.elementAt(34));

        g.addEdge(g.gameSpaces.elementAt(28),g.gameSpaces.elementAt(29));

        g.addEdge(g.gameSpaces.elementAt(29),g.gameSpaces.elementAt(30));

        g.addEdge(g.gameSpaces.elementAt(30),g.gameSpaces.elementAt(31));
        g.addEdge(g.gameSpaces.elementAt(30),g.gameSpaces.elementAt(35));

        g.addEdge(g.gameSpaces.elementAt(31),g.gameSpaces.elementAt(32));
        g.addEdge(g.gameSpaces.elementAt(31),g.gameSpaces.elementAt(35));
        g.addEdge(g.gameSpaces.elementAt(31),g.gameSpaces.elementAt(36));

        g.addEdge(g.gameSpaces.elementAt(32),g.gameSpaces.elementAt(33));
        g.addEdge(g.gameSpaces.elementAt(32),g.gameSpaces.elementAt(36));

        g.addEdge(g.gameSpaces.elementAt(33),g.gameSpaces.elementAt(34));

        g.addEdge(g.gameSpaces.elementAt(35),g.gameSpaces.elementAt(36));
        g.addEdge(g.gameSpaces.elementAt(35),g.gameSpaces.elementAt(37));

        g.addEdge(g.gameSpaces.elementAt(36),g.gameSpaces.elementAt(37));

        }// end of set up board

//======================== CREATE BOARD GUI ===============================================================================
    void createBoardGUI()
        {
        boardPanel.setPreferredSize(new Dimension (944, 574));
        //sets adds the buttons into visibility and sets their positions
        boardPanel.setBackground(Color.GRAY);
        for(int i = 1; i < g.gameSpaces.size(); i++)
            {
            boardPanel.add(g.gameSpaces.elementAt(i));
            }
        repaint();
        revalidate();

        g.gameSpaces.elementAt(1).setBounds (420, 30, 50, 50);
        g.gameSpaces.elementAt(2).setBounds (395, 80, 50, 50);
        g.gameSpaces.elementAt(3).setBounds (445, 80, 50, 50);
        g.gameSpaces.elementAt(4).setBounds (270, 130, 50, 50);
        g.gameSpaces.elementAt(5).setBounds (320, 130, 50, 50);
        g.gameSpaces.elementAt(6).setBounds (370, 130, 50, 50);
        g.gameSpaces.elementAt(7).setBounds (420, 130, 50, 50);
        g.gameSpaces.elementAt(8).setBounds (470, 130, 50, 50);
        g.gameSpaces.elementAt(9).setBounds (520, 130, 50, 50);
        g.gameSpaces.elementAt(10).setBounds (570, 130, 50, 50);
        g.gameSpaces.elementAt(11).setBounds (295, 180, 50, 50);
        g.gameSpaces.elementAt(12).setBounds (345, 180, 50, 50);
        g.gameSpaces.elementAt(13).setBounds (395, 180, 50, 50);
        g.gameSpaces.elementAt(14).setBounds (445, 180, 50, 50);
        g.gameSpaces.elementAt(15).setBounds (495, 180, 50, 50);
        g.gameSpaces.elementAt(16).setBounds (545, 180, 50, 50);
        g.gameSpaces.elementAt(17).setBounds (325, 230, 50, 50);
        g.gameSpaces.elementAt(18).setBounds (375, 230, 50, 50);
        g.gameSpaces.elementAt(19).setBounds (420, 230, 50, 50);
        g.gameSpaces.elementAt(20).setBounds (470, 230, 50, 50);
        g.gameSpaces.elementAt(21).setBounds (520, 230, 50, 50);
        g.gameSpaces.elementAt(22).setBounds (300, 280, 50, 50);
        g.gameSpaces.elementAt(23).setBounds (350, 280, 50, 50);
        g.gameSpaces.elementAt(24).setBounds (400, 280, 50, 50);
        g.gameSpaces.elementAt(25).setBounds (450, 280, 50, 50);
        g.gameSpaces.elementAt(26).setBounds (500, 280, 50, 50);
        g.gameSpaces.elementAt(27).setBounds (550, 280, 50, 50);
        g.gameSpaces.elementAt(28).setBounds (275, 330, 50, 50);
        g.gameSpaces.elementAt(29).setBounds (325, 330, 50, 50);
        g.gameSpaces.elementAt(30).setBounds (375, 330, 50, 50);
        g.gameSpaces.elementAt(31).setBounds (425, 330, 50, 50);
        g.gameSpaces.elementAt(32).setBounds (475, 330, 50, 50);
        g.gameSpaces.elementAt(33).setBounds (525, 330, 50, 50);
        g.gameSpaces.elementAt(34).setBounds (575, 330, 50, 50);
        g.gameSpaces.elementAt(35).setBounds (400, 380, 50, 50);
        g.gameSpaces.elementAt(36).setBounds (450, 380, 50, 50);
        g.gameSpaces.elementAt(37).setBounds (425, 430, 50, 50);
        //maybe sets their listeners too IDK?

        }//end of create board GUI

//======================== SET UP MAINFRAME ===============================================================================
    void setupMainFrame(int xScreenPercentage, int yScreenPercentage, String title)
        {
        Toolkit tk;
        Dimension d;

        tk = Toolkit.getDefaultToolkit();
        d =tk.getScreenSize();
        setSize(d.width/2,d.height/2);
        setLocation(d.width/4,d.height/4);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle(title);
        setVisible(true);
        setResizable(false);
        }//end of set up main frame

//*************************************************
    }// end of class
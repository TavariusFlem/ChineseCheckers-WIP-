import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

class NumberOfPlayersSelectionDialog extends JDialog
                                     implements ActionListener, WindowListener
    {
//=================== DATA MEMBERS ===========================
    JComboBox<String> numberOfPlayersComboBox;
    ChineseCheckersGameFrame parentFrame;
//************************************************************
    NumberOfPlayersSelectionDialog(ChineseCheckersGameFrame parentFrame)
        {
        this.parentFrame = parentFrame;
        addWindowListener(this);
        String[] numberOfPlayers = {"2", "3", "4", "6"};
        JPanel dialogPanel;
        dialogPanel = new JPanel(new BorderLayout());
        this.add(dialogPanel);

        //-------- COMBO BOX PANEL ------------------------------
        JPanel comboBoxPanel;
        comboBoxPanel = new JPanel(new BorderLayout());
        dialogPanel.add(comboBoxPanel,BorderLayout.NORTH);

        //-------- NUMBER OF PLAYERS LABEL ----------------------
        JLabel numberOfPlayersLabel;
        numberOfPlayersLabel = new JLabel("Select number of players");
        dialogPanel.add(numberOfPlayersLabel, BorderLayout.NORTH);

        //-------- NUMBER OF PLAYERS COMBO BOX ------------------

        numberOfPlayersComboBox = new JComboBox<>(numberOfPlayers);
        dialogPanel.add(numberOfPlayersComboBox, BorderLayout.CENTER);

        //-------- SELECTION BUTTON -----------------------------
        JButton selectionButton;
        selectionButton = new JButton("Ok");
        selectionButton.addActionListener(this);
        selectionButton.setActionCommand("CONFIRM");
        dialogPanel.add(selectionButton, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(selectionButton);


        this.setModalityType(ModalityType.APPLICATION_MODAL);
        setupMainFrame(40, 40,"Player Selection!");
        }//end of default sconstructor
//======================== ACTION PERFORMED ===============================================================================
        public void actionPerformed(ActionEvent cmd)
        {
        if(cmd.getActionCommand().equals("CONFIRM"))
            {
            parentFrame.setPlayerCount(numberOfPlayersComboBox.getSelectedItem() + "");
            dispose();
            }

        }//end of Action Performed
//======================== WINDOW ACTIVATED ===============================================================================
    public void windowActivated(WindowEvent e)
        {
        }//end of window activated
//======================== WINDOW CLOSED ==================================================================================
    public void windowClosed(WindowEvent e)
        {
        }//end of window closed
//======================== WINDOW CLOSING =================================================================================
    public void windowClosing(WindowEvent e)
        {
        if(JOptionPane.showConfirmDialog(this.getParent(),"Are you sure you want to exit the program?") == JOptionPane.YES_OPTION )
            {
            System.exit(0);
            }
        }//end of window closing
//======================== WINDOW DEACTIVATED =============================================================================
    public void windowDeactivated(WindowEvent e)
        {
        }//end of window deactivated
//======================== WINDOW DEICONIFIED =============================================================================
    public void windowDeiconified(WindowEvent e)
        {
        }//end of window deiconified
//======================== WINDOW ICONIFIED ===============================================================================
    public void windowIconified(WindowEvent e)
        {
        }//end of window iconified
//======================== WINDOW OPENED ==================================================================================
    public void windowOpened(WindowEvent e)
        {
        }//end of window opened
//======================== SET UP MAINFRAME ===============================================================================
     void setupMainFrame(int xScreenPercentage,
                         int yScreenPercentage,
                         String title)
         {
         Toolkit tk;
         Dimension d;

         tk = Toolkit.getDefaultToolkit();
         d =tk.getScreenSize();
         setSize(d.width/5,d.height/10);
         setLocation(d.width/2,d.height/2);
         setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);

         setTitle(title);
         setVisible(true);
        }//end of setup main frame
//******************************************************************
    }//end of NumberOfPlayersSelectionDialog

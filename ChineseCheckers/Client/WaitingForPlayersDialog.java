import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

class WaitingForPlayersDialog extends JDialog
	{
	WaitingForPlayersDialog()
		{
		//WHAT WILL NEED TO BE PASSED SO THAT WE WAIT UNTIL ALL PLAYERS HAVE JOINED
		JLabel		waitingLabel;
		waitingLabel = new JLabel("Waiting for players to join....");
		this.add(waitingLabel);

		this.setModalityType(ModalityType.APPLICATION_MODAL);
		setupMainFrame(40,40, "Waiting for players!!");
		}
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
         setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);

         setTitle(title);
         setVisible(true);
        }//end of setup main frame
//******************************************************************
	}// end of class
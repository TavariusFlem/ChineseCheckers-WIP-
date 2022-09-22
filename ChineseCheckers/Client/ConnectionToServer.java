import java.lang.*;
import java.io.*;
import javax.swing.*;

class ConnectionToServer
                        implements Runnable
    {
//=============== DATA MEMBERS ============================
    Talker                          talker;
    Thread                          t;
    String                          message;
    ChineseCheckersGameFrame        client;
    String[]                        messageParts;
    int                             lobbyCap;
    boolean                         isLobbyOpen;

//---------------------
    ConnectionToServer(int portNumber,String ip,String message,ChineseCheckersGameFrame client)
        {
        this.client = client;
        this.message = message;
        talker = new Talker(portNumber,ip);

        lobbyCap = client.numberOfPlayers;
        isLobbyOpen = true;

        t = new Thread(this);
        t.start();

        }
/*=================================================================================
================================ RUN ==============================================
=================================================================================*/
    public void run()
        {
        try
            {
            talker.send(message);
            message = talker.receive();
            messageParts = message.split(" ");

            //client.playerColor = messageParts[1];

            while(true)
                {
                message = talker.receive();
                messageParts = message.split(" ");
                if(message.startsWith("ERROR"))
                    {
                    handleErrorMessage(message.substring(5,message.length()));
                    }
                else if(message.startsWith("START_LOBBY"))
                    {
                    handleStartLobby();
                    }
                }
                }
            
        catch(IOException ioe)
            {
            SwingUtilities.invokeLater(
                new Runnable()
                    {
                    public void run()
                        {
                        JOptionPane.showMessageDialog(null,"Lost connection to the server try logging in again","Error",JOptionPane.ERROR_MESSAGE);
                        client.dispose();
                        }//end of run
                    }//end of runnable
                    );
            }
        }

/*=================================================================================
================================ HANDLE ERROR MESSAGE =============================
=================================================================================*/
    void handleErrorMessage(String errorMessage) throws IOException
        {
        SwingUtilities.invokeLater(
            new Runnable()
                {
                public void run()
                    {
                    JOptionPane.showMessageDialog(null,errorMessage,"Error",JOptionPane.ERROR_MESSAGE);
                    }//end of run
                }//end of runnable
                );
        }
//======================== HANDLE START LOBBY =============================================================================
    void handleStartLobby()
        {
        try
            {
            System.out.println("LOBBY START " + lobbyCap);
            talker.send("MAX_CAPACITY " + lobbyCap);
            }
        catch(IOException e)
            {
            e.printStackTrace();
            }
 
        }
//======================== HANDLE NUMBER OF PLAYERS WAITING ===============================================================
    void handleLobbyUpdate(String playersWaiting)
        {
        int tempWaitingPlayers;
        int availableSlots;
        // string to in for wiating players - max capacity
        tempWaitingPlayers = Integer.parseInt(playersWaiting);
        availableSlots = lobbyCap - tempWaitingPlayers;

        if(availableSlots == 0)
            {
            isLobbyOpen = false;
            }
        }
//======================== MAKE MOVE ======================================================================================
    void handleMakeMove(String sourcePosition, String destinationPosition)
        {
        client.g.makeMove(client.g.gameSpaces.elementAt(Integer.parseInt(sourcePosition)),client.g.gameSpaces.elementAt(Integer.parseInt(destinationPosition)));
        }
//======================= HANDLE END TURN
    }//end of Connection To Server
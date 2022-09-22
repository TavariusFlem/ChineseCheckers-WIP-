//IMPORTS
import java.lang.*;
import java.io.*;
import java.net.*;

class ConnectionToClient
            implements Runnable
    {

    String              message;
    Talker              talker;
    BroadcastServer     server;

//========================== DATA MEMBERS ================================


    ConnectionToClient(Socket normalSocket,BroadcastServer server)
        {
        Thread t;
        this.server = server;

        talker = new Talker(normalSocket);
        t = new Thread(this);
        t.start();
        }

/*=======================================================================================
================================= RUN ===================================================
=======================================================================================*/

    public void run()//public or not??
        {
        try
            {
            while(true)
                {
                message = talker.receive();
                if(message != null)
                    {

                    System.out.println("GOT MESSAGE FROM CLIENT");
                    }
                }
            }
        catch(Exception e)
            {
            e.printStackTrace();
            System.out.println("disconnect??");
            server.removeClient(this);

            }
        }//end of run
    }//end of class
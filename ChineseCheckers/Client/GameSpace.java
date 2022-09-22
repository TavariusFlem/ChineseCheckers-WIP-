import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import javax.swing.TransferHandler;
import javax.swing.border.*;
import java.awt.event.*;

class GameSpace extends JLabel
    {
//************** DATA MEMBERS ***********************
    int         gameSpacePosition;
    String      colorOfPiece;
    boolean     isOccupied;
    DragSource  dragSource;
    DropTarget  dt;
//================== CONSTRUCTOR ====================
    GameSpace()
        {
        }//end of default constructor
//===================================================
    GameSpace(int gameSpacePosition, String colorOfPiece, boolean isOccupied, Board gameBoard)
        {
        this.gameSpacePosition = gameSpacePosition;
        this.colorOfPiece = colorOfPiece;
        this.isOccupied = isOccupied;

        
        setUpVisuals();
        this.setTransferHandler(new GameSpaceTransferHandler(gameSpacePosition,gameBoard));
        this.addMouseMotionListener(new MouseAdapter()
            {                   
            @Override
            public void mouseDragged(MouseEvent e) 
                {
                GameSpace gSpace = (GameSpace) e.getSource();
                TransferHandler handle = gSpace.getTransferHandler();
                handle.exportAsDrag(gSpace, e, TransferHandler.COPY);
                }
            });
        }// end of constructor
//=================== SET UP VISUALS ================================================
    public void setUpVisuals()
        {
        setText(gameSpacePosition + "");


        setFont(new Font("Arial", Font.PLAIN, 8));
        this.setOpaque(true);
        this.setBorder(new CompoundBorder( // sets two borders
                        BorderFactory.createMatteBorder(10, 10, 10, 10, Color.GRAY), // outer border
                        BorderFactory.createEmptyBorder(10, 10, 10, 10))); // inner invisible border as the margin
        setColor();
        }// end of set up visuals
//===================== SET COLOR ===================================================
    public void setColor()
        {
        setForeground(Color.WHITE);
        if(this.colorOfPiece == "red")
                {
                setBackground(Color.red);
                }
            else if(colorOfPiece == "green")
                {
                setBackground(Color.green);
                }
            else if(colorOfPiece == "blue")
                {
                setBackground(Color.blue);
                }
            else if(colorOfPiece == "yellow")
                {
                setBackground(Color.yellow);
                }
            else if(colorOfPiece == "purple")
                {
                setBackground(Color.magenta);
                }
            else if(colorOfPiece == "orange")
                {
                setBackground(Color.orange);
                }
            else if(colorOfPiece == "none")
                {
                setBackground(Color.darkGray);
                }
        }// end of set color
//***********************************************
    }// end of class
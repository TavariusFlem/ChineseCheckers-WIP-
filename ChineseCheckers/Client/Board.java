import java.util.*;
import java.io.*;
import javax.swing.*;

class Board
    {
//=================================== DATA MEMBERS ==============================================================================
    private Map<GameSpace, List<GameSpace>>     boardMap = new HashMap<>();        //HashMap used to store the edges of the graph
    Vector<GameSpace>           gameSpaces;
    Map <String, int[]>         domainMap = new HashMap<>();
    StringBuilder               currentTurnsColor;
    ChineseCheckersGameFrame    frame;

//=================================== CONSTRUCTOR ===============================================================================
    Board(int numberOfPlayers, StringBuilder currentTurnsColor, ChineseCheckersGameFrame frame)
        {
        this.currentTurnsColor =  currentTurnsColor;
        this.frame = frame;

        createVertices(numberOfPlayers);
        createDomains(numberOfPlayers);
        //super.actionPerformed(new ActionEvent(new Object(), 2 ,"YO"));
        }//end of constructor
//=================================== ADD VERTEX ================================================================================
    public void addVertex(GameSpace sourceVertex)
        {
        boardMap.put(sourceVertex, new LinkedList<GameSpace>());
        }//end of addVertex
//================================== ADD EDGE ===================================================================================
    public void addEdge(GameSpace source, GameSpace destination)
        {
        if(!boardMap.containsKey(source))                       // checks if the destination is in the map and if not then
            {
            addVertex(source);                                  // adds a vertex with blank adjacency list
            }
        if(!boardMap.containsKey(destination))
            {
            addVertex(destination);
            }
        boardMap.get(source).add(destination);
        boardMap.get(destination).add(source);                  // because the graph is bidirectional
        }//end of addEdge
//================================ HAS EDGE =====================================================================================
    public boolean hasEdge(GameSpace sourceVertex, GameSpace destinationVertex)
        // this will be utilized for moves!!!
        {
        if(boardMap.get(sourceVertex).contains(destinationVertex) || boardMap.get(destinationVertex).contains(sourceVertex))
            {
            return true;
            }
        else
            {
            return false;
            }
        }//end of hasEdge
//=============================== CHECK FOR WIN =================================================================================
    public boolean checkForWin(String color)
        {
        boolean     hasWon = false;
        
        Object      keyArray[] = domainMap.keySet().toArray();          //gets the keys for the map of the locations of the domains which will be the colors

        int         tempDomainLocation[] = {-1,-1,-1};
        int         tempColorLocation[] = {-1,-1,-1};

        //find all the pieces of the desired color
        for(int i = 1; i < gameSpaces.size(); i++)
            {
            if(gameSpaces.elementAt(i).colorOfPiece == color)
                {
                                                                       //stores the location of the color that you're checking for win condition
                System.out.println("" + color);
                for(int j = 0; j < tempColorLocation.length; j++)
                    {
                    if(tempColorLocation[j] == -1)
                        {
                        tempColorLocation[j] = gameSpaces.elementAt(i).gameSpacePosition;
                        System.out.println("Postion of colors: " + gameSpaces.elementAt(i).gameSpacePosition);
                        break;
                        }
                    }
                }
            }

        for(int i = 0; i < keyArray.length; i++)
            {                                                           // look through the domains to find if the pieces of the chosen color are in any of them.
            if(keyArray[i].toString() != color)
                {
                for(int j = 0; j< domainMap.get(keyArray[i].toString()).length; j++)
                    {                                                   // looking through the domain array of each color that isn't the selected color
                    for(int k = 0; k < tempColorLocation.length; k++)
                        {
                        if(domainMap.get(keyArray[i])[j] == tempColorLocation[k])
                            {                                           //is in a domain
                            System.out.println("IS IN A DOMAIN");       // then check if the rest of the domain is populated
                            if(isDomainPopulated(domainMap.get(keyArray[i])) )
                                {
                                return true;                            // if so then that is a WIN!!!
                                }
                            }
                        }
                    }
                }
            }
        return hasWon;
        }// check for win
//============================= CREATE VERTICES ================================================================================
    void createVertices(int numberOfPlayers)
        {
        gameSpaces = new Vector<GameSpace>();
        gameSpaces.add(new GameSpace(0,"none",false, this)); // 

        for(int i = 1; i<38; i++)
            {
            if((i > 0 && i< 4) && (numberOfPlayers == 2 || numberOfPlayers == 6))
                {
                gameSpaces.add(new GameSpace(i,"red",true, this));
                }

            else if((i == 4 || i == 5 || i == 11) && (numberOfPlayers == 3 || numberOfPlayers == 4 || numberOfPlayers == 6))
                {
                gameSpaces.add(new GameSpace(i,"green",true, this));
                }

            else if((i == 9 || i == 10 || i == 16) && (numberOfPlayers == 3 || numberOfPlayers == 4 || numberOfPlayers == 6))
                {
                gameSpaces.add(new GameSpace(i,"blue",true, this));
                }

            else if((i == 22 || i == 28 || i == 29) && (numberOfPlayers == 4 || numberOfPlayers == 6))
                {
                gameSpaces.add(new GameSpace(i,"yellow",true, this));
                }

            else if((i == 27 || i == 33 || i == 34) && (numberOfPlayers == 4 || numberOfPlayers == 6))
                {
                gameSpaces.add(new GameSpace(i,"purple",true, this));
                }

            else if((i>34 && i<38) && (numberOfPlayers == 2 || numberOfPlayers == 3 || numberOfPlayers == 6))
                {
                gameSpaces.add(new GameSpace(i,"orange",true, this));
                }

            else
                {
                gameSpaces.add(new GameSpace(i,"none",false, this));
                }
            }
        }//end of create verticies
//============================= CREATE DOMAINS ====================================================================================
    void createDomains(int numberOfPlayers)
        {
        String colors[] = {"red",
                           "green",
                           "blue",
                           "yellow",
                           "purple",
                           "orange"};

        int domainLocations[][] = {{1,2,3},
                                   {4,5,11},
                                   {9,10,16},
                                   {22,28,29},
                                   {27,33,34},
                                   {35,36,37}};

        if(numberOfPlayers == 2)
            {
            domainMap.put(colors[0],domainLocations[0]);
            domainMap.put(colors[5],domainLocations[5]);
            }
        else if(numberOfPlayers == 3)
            {
            domainMap.put(colors[1],domainLocations[1]);
            domainMap.put(colors[2],domainLocations[2]);
            domainMap.put(colors[5],domainLocations[5]);
            }
        else if(numberOfPlayers == 4)
            {
            for(int i = 1; i < 5; i ++)
                {
                domainMap.put(colors[i],domainLocations[i]);
                }
            }
        else
            {
            for(int i = 0; i < colors.length; i++)
                {
                domainMap.put(colors[i] ,domainLocations[i]);
                }
            }
        }// end of create Domains
//============================= MAKE MOVE ===========================================================================================
    public void makeMove(GameSpace source,GameSpace destination)
        {
        GameSpace sourceSpace = source; //copy of the source space
        GameSpace destinationSpace = destination; //copy of the destination space
        GameSpace bucketSpace = new GameSpace();

        boolean result = currentTurnsColor.toString().trim().equals(source.colorOfPiece.toString().trim());
        System.out.println(result + " RESULT");
        if( (this.hasEdge(source,destination)) && (source.isOccupied && !destination.isOccupied) &&  (result))
            {

            //swapping
           	bucketSpace.colorOfPiece = sourceSpace.colorOfPiece;
           	bucketSpace.isOccupied = sourceSpace.isOccupied;

           	sourceSpace.colorOfPiece = destinationSpace.colorOfPiece;
           	sourceSpace.isOccupied = destinationSpace.isOccupied;

           	destinationSpace.colorOfPiece = bucketSpace.colorOfPiece;
           	destinationSpace.isOccupied = bucketSpace.isOccupied;

            source = sourceSpace;
            destination = destinationSpace;
   
            if(checkForWin(destinationSpace.colorOfPiece))
                {
                //win dialog and reset the application
                String dialogString = destinationSpace.colorOfPiece + " Has won!!!";
                JOptionPane.showMessageDialog(null, dialogString, "WINNER" , JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
                }    
            //allow move
            frame.doEndTurn();
            }
        //either illegal move or a skip move
        else if(hasSharedEdge(source, destination) && (source.isOccupied && !destination.isOccupied) && (result) && canSkip(source, destination))
            {
            bucketSpace.colorOfPiece = sourceSpace.colorOfPiece;
            bucketSpace.isOccupied = sourceSpace.isOccupied;

            sourceSpace.colorOfPiece = destinationSpace.colorOfPiece;
            sourceSpace.isOccupied = destinationSpace.isOccupied;

            destinationSpace.colorOfPiece = bucketSpace.colorOfPiece;
            destinationSpace.isOccupied = bucketSpace.isOccupied;

            source = sourceSpace;
            destination = destinationSpace;
   
            if(checkForWin(destinationSpace.colorOfPiece))
                {
                //win dialog and reset the application
                String dialogString = destinationSpace.colorOfPiece + " Has won!!!";
                JOptionPane.showMessageDialog(null, dialogString, "WINNER" , JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
                }    
            }
        else
            {
            JOptionPane.showMessageDialog(null, "ILLEGAL MOVE", "ILLEGAL MOVE", JOptionPane.ERROR_MESSAGE);
            }
        reColorBoard();
        }// end of make move
//============================= IS DOMAIN POPULATED =================================================================================
    boolean isDomainPopulated(int domainArray[])
        {
        boolean isPopulated = true;
        for(int i = 1; i < gameSpaces.size(); i++)
            {
            for(int j = 0; j < domainArray.length; j++)
                {
                if(domainArray[j] == gameSpaces.elementAt(i).gameSpacePosition)
                    {
                    if(!gameSpaces.elementAt(i).isOccupied)
                        {
                        return false;
                        }
                    }
                }
            }
        return isPopulated;
        }// end of is domain populated
//============================== HAS SHARED EDGE ===================================================================================
    boolean hasSharedEdge(GameSpace source, GameSpace destination)
        {
        for(int i = 0; i < boardMap.get(source).size(); i++)
            {
            for(int j = 0; j < boardMap.get(destination).size(); j++)
                {

               if(boardMap.get(source).get(i) == boardMap.get(destination).get(j))
                    {
                    if(boardMap.get(source).get(i).isOccupied)
                        {
                        System.out.println("has shared edge is true");
                        return true;
                        }
                    }
                }
            }

        System.out.println("has shared edge is false");
        return false;
        }// end of has shared edge
//========================== CAN SKIP ===============================================================================================
    boolean canSkip(GameSpace source, GameSpace destination)
        {
        System.out.println(Math.abs(source.gameSpacePosition - destination.gameSpacePosition) + "");
        if( (source.gameSpacePosition < 4) || (destination.gameSpacePosition < 4) || (source.gameSpacePosition > 34) || (destination.gameSpacePosition > 34) )
            {
            System.out.println("CORNER ABSOLUTE");
            if( (Math.abs(source.gameSpacePosition - destination.gameSpacePosition) == 5 ) ||
                (Math.abs(source.gameSpacePosition - destination.gameSpacePosition) == 7 ) ||
                (Math.abs(source.gameSpacePosition - destination.gameSpacePosition) == 10 ) ||
                (Math.abs(source.gameSpacePosition - destination.gameSpacePosition) == 12 ) )
                {
                return true;
                }
            }
        if( (source.gameSpacePosition == 11) || (destination.gameSpacePosition == 11) ||
                 (source.gameSpacePosition == 16) || (destination.gameSpacePosition == 16) ||
                 (source.gameSpacePosition == 22) || (destination.gameSpacePosition == 22) ||
                 (source.gameSpacePosition == 27) || (destination.gameSpacePosition == 27))
                {
                if(Math.abs( source.gameSpacePosition - destination.gameSpacePosition) == 11)
                    {
                    System.out.println("EDGE CASE FALSE");
                    return false;
                    }
                }
        System.out.println("BEFORE SIDE SKIPS");
        if( Math.abs(source.gameSpacePosition - destination.gameSpacePosition) == 2)
            {
            return true;
            }
        if( (((source.gameSpacePosition > 3) && (source.gameSpacePosition < 14)) || ((destination.gameSpacePosition > 3) && (destination.gameSpacePosition < 14)) ) || 
            ( ((source.gameSpacePosition > 29) && (source.gameSpacePosition < 35) ) || ((destination.gameSpacePosition > 29) && (destination.gameSpacePosition < 35) ))  )
            {
            System.out.println("ABSOLUTE 13");
            if( Math.abs( source.gameSpacePosition - destination.gameSpacePosition ) == 13)
                {
                return true;
                }

            }
        if( (((source.gameSpacePosition > 10) && (source.gameSpacePosition < 16)) || ( (destination.gameSpacePosition > 10)&&(destination.gameSpacePosition < 16)) ) || 
            ( ((source.gameSpacePosition > 22) && (source.gameSpacePosition < 28) ) || ( (destination.gameSpacePosition > 22)&&(destination.gameSpacePosition < 28) ) )  )
            {
            System.out.println("ABSOLUTE 12");
            if( Math.abs( source.gameSpacePosition - destination.gameSpacePosition ) == 12)
                {
                return true;
                }
            }
        if( (((source.gameSpacePosition > 5) && (source.gameSpacePosition < 11)) || ((destination.gameSpacePosition > 5) && (destination.gameSpacePosition < 11)) ) ||  
            ( ((source.gameSpacePosition > 27) && (source.gameSpacePosition < 33) ) || ((destination.gameSpacePosition > 27) && (destination.gameSpacePosition < 33) ))  )
            {
            System.out.println("ABSOLUTE 11");
            if( Math.abs( source.gameSpacePosition - destination.gameSpacePosition ) == 11)
                {
                return true;
                }

            }
        if( (((source.gameSpacePosition > 11) && (source.gameSpacePosition < 17)) || ((destination.gameSpacePosition > 11) && (destination.gameSpacePosition < 17)) ) ||  
            ( ((source.gameSpacePosition > 21) && (source.gameSpacePosition < 27) ) || ((destination.gameSpacePosition < 21) && (destination.gameSpacePosition < 27) ))  )
            {
            System.out.println("ABSOLUTE 10");
            if( Math.abs( source.gameSpacePosition - destination.gameSpacePosition ) == 10)
                {
                return true;
                }
            }
        //hasSharedEdge(source,destination);
        System.out.println("FALSE END");
        return false;
        }// end of can skip
//========================== RECOLOR BOARD ==========================================================================================
    public void reColorBoard()
        {
        for(int i = 1; i<gameSpaces.size(); i++)
            {
            gameSpaces.elementAt(i).setColor();
            }
        }// end of recolor board
    }// end of class
import java.awt.datatransfer.*;
import javax.swing.*;
import javax.swing.TransferHandler;
import java.awt.*;
import java.awt.dnd.*;


class GameSpaceTransferHandler extends TransferHandler
	{
//=========================== DATA MEMBERS =====================================================
	public static final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;
    private int value;
    Board gameBoard;
//**********************************************************************************************

//=========================== VALUE EXPORT TRANSFER HANDLER ====================================
        public GameSpaceTransferHandler(int value, Board gameBoard) 
        	{
        	this.gameBoard = gameBoard;
            this.value = value;
        	}// end of constructor
//=========================== GET VALUE ========================================================
        public String getValue() 
        	{
        	String stringValue = (value + "");
            return stringValue;
        	}// end of get value
//=========================== GET SOURCE ACTIONS ===============================================
        @Override
        public int getSourceActions(JComponent c) 
        	{
            return DnDConstants.ACTION_COPY_OR_MOVE;
        	}// end of get source actions
//=========================== CREATE TRANSFERABLE ==============================================
        @Override
        protected Transferable createTransferable(JComponent c) 
        	{
            Transferable t = new StringSelection(getValue());
            return t;
        	}// end of create transferable
//=========================== EXPORT DONE ======================================================
        @Override
        protected void exportDone(JComponent source, Transferable data, int action) 
        	{
            super.exportDone(source, data, action);
            // Decide what to do after the drop has been accepted
        	}// end of export done
//=========================== CAN IMPORT =======================================================

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) 
        	{
            return support.isDataFlavorSupported(SUPPORTED_DATE_FLAVOR);
        	}// end of can import
//=========================== IMPORT DATA ======================================================
        @Override
        public boolean importData(TransferHandler.TransferSupport support) 
    		{
            boolean accept = false;
            if (canImport(support)) {
                try {
                    Transferable t = support.getTransferable();
                    Object value = t.getTransferData(SUPPORTED_DATE_FLAVOR);
                    if (value instanceof String) {
                        Component component = support.getComponent();
                        if (component instanceof GameSpace) 
                        	{
                            int i = Integer.parseInt(value.toString());
                            gameBoard.makeMove(gameBoard.gameSpaces.elementAt(i), gameBoard.gameSpaces.elementAt(( (GameSpace) component).gameSpacePosition));
                            ((GameSpace)component).setColor();
                            accept = true;
                        }
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
            return accept;
        	}// end of import data
//****************************************************
	}// end of class
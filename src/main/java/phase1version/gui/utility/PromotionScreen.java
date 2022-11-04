package phase1version.gui.utility;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import phase1version.chess.ChessMatch;
import phase1version.chess.interfaces.IChessPiece;


public class PromotionScreen extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
    ChessMatch match;
    IChessPiece piece;

    private char value;
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public PromotionScreen(Frame frame, String title, ChessMatch match, IChessPiece piece)
    {
    super(frame, title);
    this.match = match;
    this.piece = piece;
    setBounds(1100,100,1300,100);
    Container ControlHost = getContentPane();
    ControlHost.setLayout(new FlowLayout());
        String[] pawns = {"Queen","Knight","Bishop","Rook"};
        comboBox = new JComboBox(pawns);
        comboBox.addActionListener(this);
        this.add(comboBox);


        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        System.out.println("reached");
        if(actionEvent.getSource() == comboBox)
        {
            if(comboBox.getSelectedItem().equals("Queen"))
            {
                value = 'q';
                match.promote(piece, value);
                System.out.println(value);
            }
            if(comboBox.getSelectedItem().equals("Knight"))
            {
                value = 'n';
                match.promote(piece, value);
                System.out.println(value);
            }
            if(comboBox.getSelectedItem().equals("Bishop"))
            {
                value = 'b';
                match.promote(piece, value);
                System.out.println(value);
            }
            if(comboBox.getSelectedItem().equals("Rook"))
            {
                value = 'r';
                match.promote(piece, value);
                System.out.println(value);
            }

            this.setVisible(false);
        }
    }

    public char getValue()
    {
        return value;
    }


    public static void main(String[] args)
    {
        //PromotionScreen popup = new PromotionScreen("choose promotion");
        //popup.setVisible(true);

    }
}



/*
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        String[] pawns = {"Queen","Knight","Bishop","Rook"};
        comboBox = new JComboBox(pawns);
        this.add(comboBox);
        this.pack();
        this.setVisible(true);
 */

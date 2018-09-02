package BinaryTree;



import BinaryTree.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author Adem KOCAMAZ
 */
public class Main {
    public static void main(String[] args){
        BinaryTree bt=new BinaryTree();
        bt.insertRandomNum(100);
        
        BinaryTreeGUI bgui=new BinaryTreeGUI(bt);
        bgui.getInfos();
        
        JFrame frame = new JFrame("Binary Tree");
        
        frame.setSize(800, 600);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(bgui);
        //frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
//        System.out.println("Efficiency:"+bt.getEfficiency());                
        
//        System.out.println(bn==null?"":bn.iData);
//        System.out.println("Number of comparison: "+bt.getNumOfComparison());
        
        
        
    }
}

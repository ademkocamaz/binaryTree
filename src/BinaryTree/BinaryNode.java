package BinaryTree;

import java.awt.Color;

/**
 *
 * @author Adem KOCAMAZ
 */
public class BinaryNode {

    public long iData;              // data item (key)    
    public BinaryNode leftChild;         // this node's left child
    public BinaryNode rightChild;        // this node's right child
    //public BinaryNode parent;
    //public Color color;
    
    public BinaryNode() {
    }

    public BinaryNode(int iData) {
        this.iData = iData;
    }

    @Override
    public String toString() {
        return "" + iData;
    }

}

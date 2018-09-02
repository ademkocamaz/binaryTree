package BinaryTree;

/*
 * @author Adem KOCAMAZ
 */
public class BinaryTree {

    private BinaryNode root;             // first node of tree
    private double efficiency;
    private int numOfComparison;
    private long searchKey;
// -------------------------------------------------------------

    public BinaryTree() // constructor
    {
        root = null;
        efficiency = 0;
        numOfComparison = 0;
    }            // no nodes in tree yet
// -------------------------------------------------------------

    public BinaryNode getRoot() {
        return root;
    }

    public BinaryNode findParent(long key, BinaryNode node) {
        BinaryNode current = root;               // start at root
        BinaryNode parent = current;
        while (current.iData != key) // while no match,
        {
            parent = current;
            if (key < current.iData) // go left?
            {
                current = current.leftChild;
            } else // or go right?
            {
                current = current.rightChild;
            }
            if (current == null) // if no child,
            {
                return null;                 // didn't find it
            }

        }
        return parent;                    // found it
    }
// -------------------------------------------------------------

    public BinaryNode find(long key) // find node with given key
    {                           // (assumes non-empty tree)

        searchKey = key;
        numOfComparison = 1;
        BinaryNode current = root;               // start at root
        while (current.iData != key) // while no match,
        {
            numOfComparison++;
            if (key < current.iData) // go left?
            {
                current = current.leftChild;
            } else // or go right?
            {
                current = current.rightChild;
            }
            if (current == null) // if no child,
            {
                return null;                 // didn't find it
            }

        }
        return current;                    // found it
    }  // end find()
// -------------------------------------------------------------

    public void insert(long id) {
        BinaryNode newNode = new BinaryNode();    // make new node
        newNode.iData = id;           // insert data        
        if (root == null) // no node in root
        {
            root = newNode;
        } else // root occupied
        {
            BinaryNode current = root;       // start at root
            BinaryNode parent;
            while (true) // (exits internally)
            {
                parent = current;
                if (id < current.iData) // go left?
                {
                    current = current.leftChild;
                    if (current == null) // if end of the line,
                    {                 // insert on left
                        parent.leftChild = newNode;
                        return;
                    }
                } // end if go left
                else // or go right?
                {
                    current = current.rightChild;
                    if (current == null) // if end of the line
                    {                 // insert on right
                        parent.rightChild = newNode;
                        return;
                    }
                }  // end else go right
            }  // end while 
        }  // end else not root        
    }  // end insert()
// -------------------------------------------------------------   

    public double getEfficiency() {
        long N = countNodes();
        efficiency = Math.log(N) / Math.log(2);
        return efficiency;
    }
// -------------------------------------------------------------   

    public long getSearchKey() {
        return searchKey;
    }
// -------------------------------------------------------------   

    public int getNumOfComparison() {
        return numOfComparison;
    }
// -------------------------------------------------------------   

    private int getHeight(BinaryNode node) {
        if (node == null) {
            return 0;
        }
        int left = getHeight(node.leftChild);
        int right = getHeight(node.rightChild);
        //return Math.max(left, right)+1;
        return ((left >= right) ? left : right) + 1;

    }
// -------------------------------------------------------------   

    public int getHeight() {
        return getHeight(root);
    }
// -------------------------------------------------------------   

    public void insertRandomNum(int size) {
        int randNum[] = new int[size];
        int num = 0;
        for (int i = 0; i < randNum.length; i++) {
            while (check(randNum, num, i)) {
                num = (int) (Math.random() * 400) - 200;
            }
            randNum[i] = num;
        }
        for (int i = 0; i < randNum.length; i++) {
            insert(randNum[i]);
        }
    }
// -------------------------------------------------------------   

    private boolean check(int[] array, int key, int index) {
        for (int i = 0; i <= index; i++) {
            if (array[i] == key) {
                return true;
            }
        }
        return false;
    }
// -------------------------------------------------------------   

    public int countNodes() {
        return countNodes(root);
    }
// -------------------------------------------------------------   

    private int countNodes(BinaryNode node) {
        if (node != null) {
            return 1 + countNodes(node.leftChild) + countNodes(node.rightChild);
        }
        return 0;
    }
// -------------------------------------------------------------

    public BinaryNode getMinimum() {
        BinaryNode current = null, last = null;
        current = root;
        while (current != null) {
            last = current;
            current = current.leftChild;
        }
        return last;
    }
// -------------------------------------------------------------

    public BinaryNode getMaximum() {
        BinaryNode current = null, last = null;
        current = root;
        while (current != null) {
            last = current;
            current = current.rightChild;
        }
        return last;
    }
// -------------------------------------------------------------
}  // end class Tree
////////////////////////////////////////////////////////////////


package BinaryTree;

import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import java.awt.Dimension;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

/**
 *
 * @author Adem KOCAMAZ
 */
public class BinaryTreeGUI extends JPanel {
// ------------------------------------------------------------- 

    private DelegateForest g;
    private DynamicTreeLayout layout;
    private int edgeCounter;
    private VisualizationViewer vv;
    private BinaryTree bt;

    private JSplitPane splitPane;

    private JScrollPane scrollPane;

    private JLabel efficiency;
    private JLabel numOfComparisons;
    private JLabel numOfNodes;
    private JLabel height;
    private JLabel searchKey;
    private JLabel max;
    private JLabel min;

    //private JLabel 
// -------------------------------------------------------------   
    public BinaryTreeGUI(BinaryTree bt) {
// -------------------------------------------------------------           

// -------------------------------------------------------------   
        //scrollPane=new JScrollPane();
// -------------------------------------------------------------   
        efficiency = new JLabel();
        numOfComparisons = new JLabel();
        numOfNodes = new JLabel();
        height = new JLabel();
        searchKey = new JLabel();
//        min = new JLabel();
//        max = new JLabel();

// -------------------------------------------------------------   
        this.bt = bt;
// -------------------------------------------------------------   
        g = new DelegateForest(new DirectedOrderedSparseMultigraph());
// -------------------------------------------------------------   
        buildTree();
// -------------------------------------------------------------   
        layout = new DynamicTreeLayout(g);
// -------------------------------------------------------------   
        vv = new VisualizationViewer(layout, new Dimension(1024, 768));
// -------------------------------------------------------------   
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.QuadCurve());
// -------------------------------------------------------------           

        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        vv.getRenderContext().setVertexFillPaintTransformer(new ConstantTransformer(Color.ORANGE));
        vv.getRenderContext().setVertexFontTransformer(new Transformer<Object, Font>() {
            public Font transform(Object i) {
                Font font = new Font("Times", Font.BOLD, 12);
                return font;
            }
        });

        Transformer<Object, Shape> vertexSize;
        vertexSize = new Transformer<Object, Shape>() {
            @Override
            public Shape transform(Object i) {
                Ellipse2D circle = new Ellipse2D.Double(-15, -15, 30, 30);
                // in this case, the vertex is twice as large
                if (i != null) {
                    return AffineTransform.getScaleInstance(1.5, 1.5).createTransformedShape(circle);
                } else {
                    return circle;
                }
            }
        };
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
// -------------------------------------------------------------   
        final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        vv.setGraphMouse(graphMouse);
        vv.addKeyListener(graphMouse.getModeKeyListener());
// -------------------------------------------------------------
        //splitPane.add(topPanel());
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);        
        scrollPane = new JScrollPane(panel);
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel(), scrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);
        //splitPane.add(scrollPane);
        add(splitPane);

// -------------------------------------------------------------   
    }
// -------------------------------------------------------------   

    public void getInfos() {
        efficiency.setText("O(logN): " + String.format("%.3f", bt.getEfficiency()));
        numOfNodes.setText("Number of nodes (N): " + bt.countNodes());
        numOfComparisons.setText("Number of comparisons: " + bt.getNumOfComparison());
        searchKey.setText("Search Key: " + bt.getSearchKey());
        height.setText("Height: " + bt.getHeight());
//        min.setText("Minimum: " + bt.getMinimum());
//        max.setText("Maximum: " + bt.getMaximum());
    }
// ------------------------------------------------------------- 

    private JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
// ------------------------------------------------------------- 

    private JPanel topPanel() {

        JPanel panelLeft = new JPanel(new GridLayout(5, 1));
        panelLeft.add(efficiency);
        panelLeft.add(numOfNodes);
//        panelLeft.add(min);
//        panelLeft.add(max);
        panelLeft.add(height);

        JPanel panelRightTop = new JPanel(new GridLayout(3, 3));

        JButton search = new JButton("Search");
        final JTextField key = new JTextField();
        search.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final long skey = Long.parseLong(key.getText());
                BinaryNode node = bt.find(skey);
                getInfos();
                if (node == null) {
                    searchKey.setText("Search Key: " + skey + " can't find");
                    Transformer<Long, Paint> vertexPaint = new Transformer<Long, Paint>() {
                        public Paint transform(Long i) {
                            return Color.ORANGE;
                        }
                    };
                    vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
                } else {
                    Transformer<Long, Paint> vertexPaint = new Transformer<Long, Paint>() {
                        public Paint transform(Long i) {
                            if (i == skey) {
                                return Color.GREEN;
                            }
                            return Color.ORANGE;
                        }
                    };
                    vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
                    vv.repaint();
                    searchKey.setText("Search Key: " + skey + " found");
                }

            }

        });

        JButton insert = new JButton("Insert");
        final JTextField insKey = new JTextField();
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final long key = Long.parseLong(insKey.getText());
                searchKey.setText("Insert Key: " + insKey.getText());
                if (insKey.getText().equals("")) {
                    return;
                }
                if (bt.find(key) == null) {
                    bt.insert(key);
                    getInfos();
                    //g.addEdge(edgeCounter++, bt.findParent(key, bt.getRoot()).iData, bt.find(key).iData);                    
                    g = null;
                    g = new DelegateForest(new DirectedOrderedSparseMultigraph());
                    buildTree();
                    layout.setGraph(g);
                    Transformer<Long, Paint> vertexPaint = new Transformer<Long, Paint>() {
                        public Paint transform(Long i) {
                            if (i == key) {
                                return Color.CYAN;
                            }
                            return Color.ORANGE;
                        }
                    };
                    vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
                    vv.repaint();

                    //buildTree();
                    searchKey.setText("Insert Key: " + insKey.getText() + " is inserted.");
                } else {
                    searchKey.setText("Insert Key: " + insKey.getText() + " is already exist.");
                }

            }
        });
        JButton random = new JButton("Random (-200 and 200)(Max=200)");
        final JTextField randNum = new JTextField();
        random.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!randNum.getText().equals("")) {
                    int rand = Integer.parseInt(randNum.getText());
                    if (rand > 200) {
                        rand = 200;
                    }
                    bt = null;
                    bt = new BinaryTree();
                    bt.insertRandomNum(rand);
                    g = null;
                    g = new DelegateForest(new DirectedOrderedSparseMultigraph());
                    buildTree();
                    layout.setGraph(g);
                    vv.repaint();
                    getInfos();
                } else {
                    JOptionPane.showMessageDialog(randNum, "the random number is empty!");
                }

            }
        });

        panelRightTop.add(search);
        panelRightTop.add(key);

        panelRightTop.add(insert);
        panelRightTop.add(insKey);

        panelRightTop.add(random);
        panelRightTop.add(randNum);

        JPanel panelRightBottom = new JPanel(new GridLayout(2, 1));
        panelRightBottom.add(searchKey);
        panelRightBottom.add(numOfComparisons);

        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneRight.add(panelRightTop);
        splitPaneRight.add(panelRightBottom);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPaneRight, panelLeft);
        //splitPane.setDividerLocation(200);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(splitPane);
        return panel;
    }

    private void buildTree(BinaryNode node) {
        if (node != null) {
            BinaryNode parent, left, right;
            parent = node;

            left = parent.leftChild;
            if (left != null) {
                g.addEdge(edgeCounter++, parent.iData, left.iData);
            }

            right = parent.rightChild;
            if (right != null) {
                g.addEdge(edgeCounter++, parent.iData, right.iData);
            }

            buildTree(left);
            buildTree(right);
        }

    }

    public void addNode(BinaryNode node) {

    }
// -------------------------------------------------------------   

    public void buildTree() {
        g.addVertex(bt.getRoot().iData);
        buildTree(bt.getRoot());
    }
// -------------------------------------------------------------   
}

package BinaryTree;

/**
 *
 * @author Adem KOCAMAZ
 */
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import java.awt.Point;
import java.util.Collection;

import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.util.TreeUtils;

public class DynamicTreeLayout<V, E>
    extends TreeLayout<V, E>
{
public DynamicTreeLayout(Forest<V, E> g) {
    this(g, DEFAULT_DISTX, DEFAULT_DISTY);
}

public DynamicTreeLayout(Forest<V, E> g, int distx) {
    this(g, distx, DEFAULT_DISTY);
}

public DynamicTreeLayout(Forest<V, E> g, int distx, int disty) {
    super(g, distx, disty);
}

protected void buildTree() {
    alreadyDone.clear(); // This was missing and prevented the layout to update positions

    this.m_currentPoint = new Point(20, 20);
    Collection<V> roots = TreeUtils.getRoots(graph);
    if (roots.size() > 0 && graph != null) {
        calculateDimensionX(roots);
        for (V v : roots) {
            calculateDimensionX(v);
            m_currentPoint.x += this.basePositions.get(v) / 2 + this.distX;
            buildTree(v, this.m_currentPoint.x);
        }
    }
}

private int calculateDimensionX(V v) {
    int localSize = 0;
    int childrenNum = graph.getSuccessors(v).size();

    if (childrenNum != 0) {
        for (V element : graph.getSuccessors(v)) {
            localSize += calculateDimensionX(element) + distX;
        }
    }
    localSize = Math.max(0, localSize - distX);
    basePositions.put(v, localSize);

    return localSize;
}

private int calculateDimensionX(Collection<V> roots) {
    int localSize = 0;
    for (V v : roots) {
        int childrenNum = graph.getSuccessors(v).size();

        if (childrenNum != 0) {
            for (V element : graph.getSuccessors(v)) {
                localSize += calculateDimensionX(element) + distX;
            }
        }
        localSize = Math.max(0, localSize - distX);
        basePositions.put(v, localSize);
    }

    return localSize;
}
}

package link.westermann.gameoflive.domain.hashlive;


/**
 * Containing Tree structure of neighbor Nodes:
 * 	nw = northwest
 * 	ne = northeast
 * 	sw = Southwest
 * 	se = Southeast
 * 
 * @author Alex
 * 
 */
public class TreeNodeBase {
	final TreeNode nw, ne, sw, se;
	final int depth;
	final boolean alive;
	final double population;

	public TreeNodeBase(boolean isAlive) {
		nw = ne = sw = se = null;
		depth = 0;
		alive = isAlive;
		population = alive ? 1 : 0;
	}

	public TreeNodeBase(TreeNode nw, TreeNode ne, TreeNode sw, TreeNode se) {
		this.nw = nw;
		this.ne = ne;
		this.sw = sw;
		this.se = se;
		depth = nw.depth + 1;
		population = nw.population + ne.population + sw.population + se.population;
		alive = population > 0;
	}
	
	public static TreeNode create() {
		return new TreeNode(false).emptyTree(3);
	}
	
	public TreeNode setBit(int x, int y, boolean isAlive) {
		if (depth == 0)
			return new TreeNode(isAlive);
		int offset = 1 << (depth - 2);
		if (x < 0)
			if (y < 0)
				return create(nw.setBit(x + offset, y + offset, isAlive), ne, sw, se);
			else
				return create(nw, ne, sw.setBit(x + offset, y - offset, isAlive), se);
		else if (y < 0)
			return create(nw, ne.setBit(x - offset, y + offset, isAlive), sw, se);
		else
			return create(nw, ne, sw, se.setBit(x - offset, y - offset, isAlive));
	}
	
	public boolean getBit(int x, int y) {
		if (depth == 0)
			return alive;
		int offset = 1 << (depth - 2);
		if (x < 0)
			if (y < 0)
				return nw.getBit(x + offset, y + offset);
			else
				return sw.getBit(x + offset, y - offset);
		else if (y < 0)
			return ne.getBit(x - offset, y + offset);
		else
			return se.getBit(x - offset, y - offset);
	}
	
	public TreeNode extendUniverse() {
		TreeNode border = emptyTree(depth - 1);
		return create(create(border, border, border, nw), create(border, border, ne, border),
				create(border, sw, border, border), create(se, border, border, border));
	}

	protected TreeNode create(boolean living) {
		return new TreeNode(living);
	}

	protected TreeNode create(TreeNode nw, TreeNode ne, TreeNode sw, TreeNode se) {
		return new TreeNode(nw, ne, sw, se);
	}

	protected TreeNode emptyTree(int lev) {
		if (lev == 0)
			return create(false);
		TreeNode n = emptyTree(lev - 1);
		return create(n, n, n, n);
	}
}

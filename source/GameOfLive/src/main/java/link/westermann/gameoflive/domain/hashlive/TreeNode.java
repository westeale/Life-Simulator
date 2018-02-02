package link.westermann.gameoflive.domain.hashlive;

public class TreeNode extends TreeNodeBase {
	
	public TreeNode(boolean living) {
		super(living);
	}

	public TreeNode(TreeNode nw, TreeNode ne, TreeNode sw, TreeNode se) {
		super(nw, ne, sw, se);
	}
	
	public TreeNode nextGeneration() {
		// skip empty regions quickly
		if (population == 0)
			return nw;
		if (depth == 2)
			return slowSimulation();
		TreeNode n00 = nw.centeredSubnode(), n01 = centeredHorizontal(nw, ne), n02 = ne.centeredSubnode(),
				n10 = centeredVertical(nw, sw), n11 = centeredSubSubnode(), n12 = centeredVertical(ne, se),
				n20 = sw.centeredSubnode(), n21 = centeredHorizontal(sw, se), n22 = se.centeredSubnode();
		return create(create(n00, n01, n10, n11).nextGeneration(), create(n01, n02, n11, n12).nextGeneration(),
				create(n10, n11, n20, n21).nextGeneration(), create(n11, n12, n21, n22).nextGeneration());
	}
	
	private TreeNode oneGen(int bitmask) {
		if (bitmask == 0)
			return create(false);
		int self = (bitmask >> 5) & 1;
		//bitmask for irrelevant Bits
		bitmask &= 0x757;
		int neighborCount = 0;
		while (bitmask != 0) {
			neighborCount++;
			// delete least significant bit
			bitmask &= bitmask - 1; 
		}
		if (neighborCount == 3 || (neighborCount == 2 && self != 0))
			return create(true);
		else
			return create(false);
	}

	private TreeNode slowSimulation() {
		int allbits = 0;
		for (int y = -2; y < 2; y++)
			for (int x = -2; x < 2; x++)
				allbits = (allbits << 1) + (getBit(x, y)?1:0);
		return create(oneGen(allbits >> 5), oneGen(allbits >> 4), oneGen(allbits >> 1), oneGen(allbits));
	}

	private TreeNode centeredHorizontal(TreeNode w, TreeNode e) {
		return create(w.ne.se, e.nw.sw, w.se.ne, e.sw.nw);
	}

	private TreeNode centeredVertical(TreeNode n, TreeNode s) {
		return create(n.sw.se, n.se.sw, s.nw.ne, s.ne.nw);
	}

	private TreeNode centeredSubSubnode() {
		return create(nw.se.se, ne.sw.sw, sw.ne.ne, se.nw.nw);
	}

	private TreeNode centeredSubnode() {
		return create(nw.se, ne.sw, sw.ne, se.nw);
	}
}

package link.westermann.gameoflive.domain.hashlive;

/**
 * Game of Live algorithm, implemented with a tree.
 *   
 * @author Alex
 *
 */
public class TreeUniverse implements UniverseStrategy {

	private double generationCount = 0;
	private TreeNode rootNode;
	
	public TreeUniverse() {
		rootNode = TreeNode.create();
	}

	@Override
	public void setBit(int x, int y, boolean isAlive) {
		while (true) {
			int maxCoordinate = 1 << (rootNode.depth - 1);
			if (-maxCoordinate <= x && x <= maxCoordinate - 1 && -maxCoordinate <= y && y <= maxCoordinate - 1)
				break;
			rootNode = rootNode.extendUniverse();
		}
		rootNode = rootNode.setBit(x, y, isAlive);
	}

	@Override
	public void runStep() {
		while (rootNode.depth < 3 || rootNode.nw.population != rootNode.nw.se.se.population
				|| rootNode.ne.population != rootNode.ne.sw.sw.population || rootNode.sw.population != rootNode.sw.ne.ne.population
				|| rootNode.se.population != rootNode.se.nw.nw.population)
			rootNode = rootNode.extendUniverse();
		rootNode = rootNode.nextGeneration();
		generationCount++;
	}

	@Override
	public boolean getBit(int x, int y) {
		return rootNode.getBit(x, y);
	}

	@Override
	public double getGenerationCount() {
		return generationCount;
	}

}

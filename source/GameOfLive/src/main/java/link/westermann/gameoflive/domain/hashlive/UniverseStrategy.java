package link.westermann.gameoflive.domain.hashlive;

public interface UniverseStrategy {
	void setBit(int x, int y, boolean isAlive);
	void runStep();
	double getGenerationCount();
	boolean getBit(int x, int y);
}

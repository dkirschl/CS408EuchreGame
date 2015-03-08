public abstract class AI {
	public abstract boolean passOrPickUp();
	public abstract String chooseSuit();
	public abstract Card playCard();
	public abstract int calculateValues(String suit);
	public abstract void removeCard(Card middle);
}
package demo.oo;

public abstract class Animal {
	
	public String name="Animal: name";
	public static String staticName="Animal£ºstaticName";	
	
	public enum State {ALIVE, DEAD};
	
	public State state;
	
	public float weight;

	protected void finalize() {	
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public static void staticTest() {
		System.out.println("Animal static m");
	}
}

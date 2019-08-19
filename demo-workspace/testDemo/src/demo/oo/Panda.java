package demo.oo;

public class Panda extends Animal  implements Active{
	{
		state = Animal.State.DEAD;
		System.out.println("first");
	}
	
	public String name="Panda: name";
	public static String staticName="Panda£ºstaticName";
	
	public Panda() {
		sum++;
	}
	
	public Panda(Animal.State state,float weight) {
		this.state = state;
		this.weight = weight;
		sum++;
	}

	public Panda(float weight) {
		this.weight=weight;
		sum++;
	}
	
	@Override
	public void eat() {
		System.out.println("eat ");
	}
	
	public void eat(String food) {
		System.out.println("eat "+food);
	}
	
	
	protected void finalize() {
	}

	@Override
	public float getWeight() {
		System.out.print(this.name.split(":")[0]+":");
		return weight;
	}
	
	private static int sum;
	
	public static int getSum() {
		return sum;
	}
	
	public static void staticTest() {
		System.out.println("Panda static m");
	}
}

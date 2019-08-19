package demo;

import demo.oo.*;

public class ClassTest {
	public static void main(String[] args) {
		System.out.println("------");
		Active active=new Panda(Animal.State.ALIVE,99);
		Active.interfaceName();
		//active.interfaceName();
		active.eat();
		
		//Panda pa=(Panda) new Animal();
		System.out.println("------");
		final Animal animal=(Animal) active;
		System.out.println(animal.getState());
		System.out.println(animal.getWeight());
		
		animal.staticTest();
		System.out.println(animal.name);
		System.out.println(animal.staticName);
		
		System.out.println("------");
		Panda panda = new Panda(Animal.State.ALIVE,100);
		System.out.println("Sum:"+panda.getSum());
	}
	
	private static float getI() {
		return i;
	}

	private static int i;
	
	class InClass{
		public int getI(){
			return ClassTest.i;
		}
		
	}
}

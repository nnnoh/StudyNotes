package demo.structures;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class DataStructuresDemo {
	public static void main(String[] args) {
		dictionaryTest();
	}
	
	public static void dictionaryTest() {
		Dictionary<String, String> pw = new Hashtable<String, String>();
		pw.put("root", "123456");
		pw.put("guest", "111111");
		
		Enumeration<String> user=pw.keys();
		while(user.hasMoreElements()) {
			System.out.println(user.nextElement());
		}
		
		System.out.println(pw.toString());
	}
}

package demo.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataSetDemo {
	public static void main(String[] arg) {
		listTest();
	}

	public static void mapTest() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("root", "123456");
		map.put("guest", "111111");
		map.put("two", "222222");
		Iterator<?> iter = map.entrySet().iterator();
		Set<String> set = map.keySet();

		iter.forEachRemaining((m) -> {
			System.out.println(m.getClass() + " " + m.toString());
		});

		System.out.println();
		iter = map.entrySet().iterator(); // forEachRemaining 函数遍历了一遍集合，指向最后元素的后面，需要重新赋值才能使用。
		iter.next();
		iter.remove();
		System.out.println(map.toString());
		System.out.println(set.toString());
	}

	public static void listTest() {
		List<InListObj> list = new ArrayList<InListObj>();
		list.add(new InListObj("Wangwu",22));
		list.add(new InListObj("wangliu",21));
		list.add(new InListObj("madongmei",25));
		list.add(new InListObj("madongmei",25));
		
//		Collections.sort(list);	
		Collections.sort(list,Comparator.comparing(InListObj::getAge));
//		Collections.sort(list,);
		list.forEach(n -> System.out.println(n.toString()));
	}

	static class InListObj implements Comparable<InListObj> {
		@Override
		public String toString() {
			return "InListObj [name=" + name + ", age=" + age + "]";
		}

		public InListObj(String name,int age) {
			this.name = name;
			this.age = age;
		}
		
		private String name;

		private int age;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public int compareTo(InListObj o) {
			System.out.println(name+" vs "+o.getName()+" : "+name.compareTo(o.getName()));
			return name.compareTo(o.getName());
//			System.out.println(age+" vs "+o.getAge()+" : "+new Integer(age).compareTo(o.getAge()));
//			return new Integer(age).compareTo(o.getAge());
			// == 参数：0
			// < 参数 ：负数
			// > 参数 ：正数
		}

	}
}

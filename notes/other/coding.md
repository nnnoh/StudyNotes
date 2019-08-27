LeetCode - 3/1150

Language: Java

| #                                                            | 题目                 | 描述                                     | 标签                           | 思路                                              |
| ------------------------------------------------------------ | -------------------- | ---------------------------------------- | ------------------------------ | ------------------------------------------------- |
| [1](https://leetcode-cn.com/problems/two-sum/)               | 两数之和             | 在两数组中找出和为目标值的那两个整数     | 数组、哈希表                   | 暴力for；哈希表查找                               |
| [2](https://leetcode-cn.com/problems/add-two-numbers/)       | 两数相加             | 将两个链表表示的非负的整数相加，返回链表 | 链表、数学                     | 链表遍历                                          |
| [3](https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/) | 无重复字符的最长子串 | 找出不含有重复字符的最长子串的长度。     | 哈希、双指针、字符串、滑动窗口 | 暴力for；滑动窗口，使用 HashSet/ HashMap 判断重复 |
|                                                              |                      |                                          |                                |                                                   |
|                                                              |                      |                                          |                                |                                                   |

## 考虑特殊输入情况!!!

## Array

### length()、length 和 size()

- **length()** 方法是针对字符串来说的，要求一个字符串的长度就要用到它的length()方法；
- **length 属性**是针对 Java 中的数组来说的，要求数组的长度可以用其 length 属性；
- **size()** 方法是针对泛型集合说的，如果想看这个泛型有多少个元素, 就调用此方法来查看。

### Map

Map 容器记录元素的值与索引，借助 Map 接口的方法解决问题，降低时间复杂度。

哈希表**查找**的时间复杂度为 O(1)



## List

```java
public class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
```

使用**头结点**简化代码。如果没有头结点，则必须编写额外的条件语句来初始化表头的值。

### 遍历 List

```java
 	ListNode dummyHead = new ListNode(0);
    ListNode curr = dummyHead;
    while (curr != null) {
        // do something
        curr = curr.next;
    }
```

> 遍历多个 List
>
> ```java
> 	while (p != null || q != null) {
>         // (p != null) ? p.val : 0;
>         // 或使用 &&，之后在特别处理
>         if (p != null) p = p.next;
>         if (q != null) q = q.next;
>     }
> ```

## Sliding Window

滑动窗口用于处理数组或字符串的连续子串问题。滑动窗口是可以将两个边界向某一方向滑动的窗口，如 [i, j)。

从一端滑动到另一端的时间复杂度：O(n)。

滑动窗口时，针对有关**不重复**的问题，可借助 HashSet / HashMap 解决。

```java
public static void main(String[] args) {
		int[] nums1={1,2,7};
		int[] nums2={8,9};
		System.out.println(findMedianSortedArrays(nums1,nums2));
	}
	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
		// 两边收缩 可行？
		// nums length not 0?
		int mid=(nums1.length+nums2.length)/2;
		boolean two=(nums1.length+nums2.length)%2==0;
		int i=0,j=0,p=0,c;
		while(p<mid) {		//out bound
			if(nums1[i]>nums2[j])
				j++;
			else
				i++;
			p++;
		}
		if(two) {
			return 0;
		}
		else {
			return nums1[i]>nums2[j]?nums2[j]:nums1[i];
		}
	}
```


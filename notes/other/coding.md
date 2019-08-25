LeetCode - 1/1150

Language: Java

## Array

### length()、length 和 size()

- **length()** 方法是针对字符串来说的，要求一个字符串的长度就要用到它的length()方法；
- **length 属性**是针对 Java 中的数组来说的，要求数组的长度可以用其 length 属性；
- **size()** 方法是针对泛型集合说的，如果想看这个泛型有多少个元素, 就调用此方法来查看。

### Map

Map 容器记录元素的值与索引，借助 Map 接口的方法解决问题。

哈希查找的时间复杂度为 O(1)

## List

```java
public class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
```

使用**头结点**简化代码。如果没有头结点，则必须编写额外的条件语句来初始化表头的值。

遍历 List

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
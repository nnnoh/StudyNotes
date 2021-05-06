# Algorithms

## Tips

- 考虑特殊输入情况!!!
- 检查冗余，优化代码。
- 数字题，思考规律，降低复杂度。
- 实现困难时（涉及情况无法全面考虑），考虑换个角度，从局部到全局，抓住题目的根本要求。

## Array/String

### Thought

- 判断重复 -> Set/Map
- 不重复序列的查找 -> HashSet/Map
- 连续序列 -> Sliding Window
- 时间复杂度 -> 二分查找
- 概念转化为公式
- 对数组预处理（如，判断重复先排序）

### 二分查找

对于非连续空间的有序数据，可通过多次不同维度的二分查找以找出目标值。

### 字符串匹配

- KMP
- 字典树
- Boyer-Moore
- Sunday
- Horspool

### Map

Map 容器记录元素的值与索引，借助 Map 接口的方法解决问题，降低时间复杂度。

哈希表**查找**的时间复杂度为 O(1)

### Sliding Window

滑动窗口用于处理数组或字符串的**连续子串**问题。滑动窗口是可以将两个边界向某一方向滑动的窗口，如 [i, j)。

滑动窗口时，针对有关**不重复**的问题，可借助 HashSet / HashMap 解决。

### Matrix

经典走矩阵

### 语言相关

#### Java中的length()、length 和 size()

- **length()** 方法是针对字符串来说的，要求一个字符串的长度就要用到它的length()方法；
- **length 属性**是针对 Java 中的数组来说的，要求数组的长度可以用其 length 属性；
- **size()** 方法是针对泛型集合说的，如果想看这个泛型有多少个元素, 就调用此方法来查看。

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

遍历多个 List

```java
	while (p != null || q != null) {
		// (p != null) ? p.val : 0;
		// 或使用 &&，之后在特别处理
		if (p != null) p = p.next;
		if (q != null) q = q.next;
	}
```

链表的前序遍历和后序遍历

```java
void traverse(ListNode head) {
    // 前序遍历代码
    traverse(head.next);
    // 后序遍历代码
}
```

如果想正序打印链表中的`val`值，可以在前序遍历位置写代码；反之，如果想倒序遍历链表，就可以在后序遍历位置操作。

#### 反转列表

```java

```

List x Array

时间复杂度

## Tree

思路：

- 找指定节点：
  - 如：最近公共祖先
  - 明确遍历过程中，指定的节点需要满足什么条件（表达式），然后进一步确定遍历方式。
  - 将树遍历一边存储值与父节点或子节点关系（或其他关系），然后根据关系找出指定节点。

### Binary tree

思路：把题目的要求细化，搞清楚根节点应该做什么，然后根据题目要求选择使用前序，中序，后续的递归框架。

### Binary Search Tree

二叉搜索树的定义：

1、对于 BST 的每一个节点 `node`，左子树节点的值都比 `node` 的值要小，右子树节点的值都比 `node` 的值大。

2、对于 BST 的每一个节点 `node`，它的左侧子树和右侧子树都是 BST。

## Stack

模拟递归

## Backtracking

即 DFS。

- 数组选择回溯写法
- DFS写法
- 迭代DFS写法

## Dynamic programming

思路

- 在题目有限制的情况下（如，打家劫舍），确定不同情况下的迁移方程及对应的计算范围。
- 抓住题目本质，迁移方程变量数尽可能小，减少 `for` 循环数量。
- 空间优化：当 `f[i]` 时只依赖 `f[i-1]` 和 `f[i-2]` 等有限个状态时，可以利用**滚动数组**思想进行优化。
- 迁移方程尽量简洁，特殊情况一般化。
- 根据题意剪枝。
- 利用公式传递性简化计算过程。
- 当迁移限制在几种有限的情况下可以考虑使用dfs遍历，配合记忆化求解。

### 矩阵求区间和

- 前缀和。

  一维：$\sum\limits_{k=i}^j{nums[k]}=\sum\limits_{k=0}^j{nums[k]}-\sum\limits_{k=0}^{i-1}{nums[k]}$

  二维：同理推广

- 分块和。如，一维数组以数组 nums 的长度的最大值的平方根为块长。

  优点：如果数据需要修改，只需重新计算涉及的分块即可。

## Recursion

递归技巧：不要跳进递归，而是利用明确的定义来实现算法逻辑。

处理看起来比较困难的问题，可以尝试化整为零，明确简单场景下的解法，将其修改，解决困难的问题。

使用场景：

- 使用递归中的回溯模拟链表从后向前的遍历。

## Math

### 公式求值

思路：

- 在暴力枚举的情况下，剪枝优化时间复杂度。
- 两个变量的变化可视为在二维矩阵上搜索，帮助理解找出搜索路径，利用双指针求解。
- 数论等知识的解法并不一定最好，不强求。

### 位运算

- [Integer.bitCount() 函数理解（尽量通俗易懂）_雷震子的博客-CSDN博客](https://blog.csdn.net/qq_27007509/article/details/112246576?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_title-0&spm=1001.2101.3001.4242)

有限状态自动机

数字电路设计

- [只出现一次的数字 II - 只出现一次的数字 II - 力扣（LeetCode）](https://leetcode-cn.com/problems/single-number-ii/solution/single-number-ii-mo-ni-san-jin-zhi-fa-by-jin407891/)

#### 位运算分治

- 若要翻转一个二进制串，可以将其均分成左右两部分，对每部分递归执行翻转操作，然后将左半部分拼在右半部分的后面，即完成了翻转。

  由于左右两部分的计算方式是相似的，利用位掩码和位移运算，我们可以自底向上地完成这一分治流程。

  1. 取出所有奇数位和偶数位；
  2. 将奇数位移到偶数位上，偶数位移到奇数位上。

  题目固定了二进制位数，通过有限的位运算即可实现要求，即时间复杂度：$O(1)$。

### 充分必要条件证明

如：

- 贪心算法（全局最优解）证明

- 特殊排序过程中，compare 可行性的证明。
  - [最大数 - 最大数 - 力扣（LeetCode）](https://leetcode-cn.com/problems/largest-number/)

### 理论知识

- 平方数之和：[Which Numbers are the Sum of Two Squares?](https://wstein.org/edu/124/lectures/lecture21/lecture21/node2.html)

## Two Pointers

无法高效获取长度，无法根据偏移快速访问元素，是链表的两个劣势。诸如获取倒数第k个元素，获取中间位置的元素，判断链表是否存在环，判断环的长度等和长度与位置有关的问题，都可以通过灵活运用双指针来解决。

关键点在于找出**两指针移动路径之间的关系**。

当要获取准确的值时，可以考虑通过**数学公式**解决。

快慢指针：

- 两指针移动步长不同，有定长和变长两种

- 定长：通常是2倍关系，以达到经过的路径快是慢的2倍。

  关键点在于两指针移动路径之间的关系。

- 变长：根据题目关系符合条件时移动。

  关键点在于明确快、慢指针指向位置的意义。

  如，删除数组中重复 k 次以上的项。

### 快慢指针寻找链表的中点

```java
ListNode slow, fast;
slow = fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
// slow 指针现在指向链表中点
```

![img](https://gitee.com/nnnoh/image-hosting-service/raw/master/img/2020/e3f9643f57342c88edd155a8af2b11ab280977cc.jpg)

## Design

- [实现 Trie (前缀树)](https://leetcode-cn.com/problems/implement-trie-prefix-tree/solution/shi-xian-trie-qian-zhui-shu-by-leetcode/)

## Reference

- [力扣](https://leetcode-cn.com/)

- [第零章、必读系列 - labuladong 的算法教程](https://labuladong.gitbook.io/algo/di-ling-zhang-bi-du-xi-lie)
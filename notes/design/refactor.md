## Refactor

> 《重构：改善既有代码的设计》

### Introduce

Matin Fowler给出的重构的目标:

- 不改变软件可观察行为
- 提高软件可理解性
- 降低软件修改成本

Kent Beck的简单设计四原则:

- Pass All Test: 通过全部测试;
- No Duplication: 没有重复(DRY)
- Reveals Intent: 程序表达意图，易于理解
- Has no superfluous parts: 没有冗余，或者YAGNI原则

> 上述四条的重要程度依次降低.

#### 什么样的软件需要重构？

由于我们重构的目标是使软件满足简单设计四原则，那么任何违反简单设计四原则的代码都应该是我们重构的目标。例如：

- 代码很容易出现bug，导致测试失败；
- 代码存在重复使得不易修改；
- 代码写的晦涩非常难以理解；
- 代码存在过度设计，存在冗余导致复杂！



#### 什么时候进行重构？

https://www.jianshu.com/p/3b34f337eaee
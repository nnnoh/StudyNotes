# Concurrency

LeetCode - 5/7(5)

Language: Java

## Tips

- 有循环时注意每个线程应该循环的次数。

不同方式间的比较

## Lock

### ReentrantLock

### Condition

```java
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();
```

```java
void funcName() throws InterruptedException {
	lock.lock();
	try{
        while(...){
            condition.await();
        }
        // ...
        condition.signalAll();
    } finally{
        lock.unlock();
    }
}
```



## Semaphore

```java
	Semaphore sem = new Semaphore(i);
```

```java
	sem.acquire();	
	sem.release();
	sem.acquire(x);
	sem.release(y);
```


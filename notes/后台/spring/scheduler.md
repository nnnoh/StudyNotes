## 定时任务

### 定时任务的几种实现方式

- Timer：Java自带的java.util.Timer类，这个类允许你调度一个java.util.TimerTask任务。 使用这种方式可以让你的程序按照某一个频度执行，但不能在指定时间运行。一般用的较少。
- Quartz：是一个功能完善的任务调度框架，它支持集群环境下的任务调度，需要将任务调度状态序列化到数据库。
- Spring Task：Spring3.0以后自带的task，可以将它看成一个轻量级的Quartz，而且使用起来比Quartz简单许多，支持固定时间 (支持cron表达式)和固定时间间隔调度任务，支持线程池管理。



https://blog.csdn.net/noaman_wgs/article/details/80984873


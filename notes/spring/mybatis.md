resultMap 

list map



mybatis主要处理的就是sqlMap，\<select>的话，使用executeQuery来实现的，而\<insert>是用executeUpdate来实现的。两个的主要区别就是在结果返回上，executeQuery返回的是resultSet,而\<insert>是返回int的，虽然sql语句都可以执行但是意义不一样。



Mybatis Generator

selective 注意事项




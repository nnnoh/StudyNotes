## MongoDB

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>
```

数据源配置：

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=testdb
```

### 实体类注解

`@Document` 表明该类是一个文档对象，collection属性指定对应的文档名。

`@Id` 标注主键字段，String类型的主键值在插入的时候Mongo DB会帮我们自动生成。

`@Transient` 如果对象中的某个属性为非表字段，可以使用该注解进行排除。

### MongoRepository

实体类的**Dao**接口需继承`MongoRepository<T,ID>`，泛型分别为**实体对象**和**主键类型**。接口包含了基本的增删改查的方法。

`Dao`接口通过继承`MongoRepository`已经具有了JPA的特性，可以通过方法名来构建多查询条件的SQL。

在使用MongoRepository的过程中，非模糊查询多配合使用Example/ExampleMatcher来完成工作。

如果实体类中包含有基本数据类型的属性，那么在使用repository.find(Example)时，需要把这些属性忽略掉，因为基本数据类型在新建对象时会有默认值，这时如果按照别的属性查找数据时，这些属性也会附带到条件里。

#### 排序与分页

排序和分页需要使用`MongoTemplate`对象来完成。

示例：

```java
@Autowired
private MongoTemplate template;

public Page<User> getUserByCondition(int size, int page, User user) {
    Query query = new Query();
    Criteria criteria = new Criteria();

    if (!StringUtils.isEmpty(user.getName())) {
        criteria.and("name").is(user.getName());
    }
    query.addCriteria(criteria);

    Sort sort = new Sort(Sort.Direction.DESC, "age");
    // page 0 表示第一页
    Pageable pageable = PageRequest.of(page, size, sort);

    List<User> users = template.find(query.with(pageable), User.class);
    return PageableExecutionUtils.getPage(users, pageable, () -> template.count(query, User.class));
}
```
## MongoDB

[MONGODB MANUAL](https://docs.mongodb.com/manual/)

### 概念

| RDBMS  | MongoDB                        |
| :----- | :----------------------------- |
| 数据库 | 数据库                         |
| 表格   | 集合                           |
| 行     | 文档                           |
| 列     | 字段                           |
| 表联合 | 嵌入文档                       |
| 主键   | 主键 (自动将_id字段设置为主键) |

#### 文档

文档是一组键值(key-value)对(即 BSON)。

**注意**：

1. 文档中的键/值对是有序的。
2. 文档中的值不仅可以是在双引号里面的字符串，还可以是其他几种数据类型（甚至可以是整个嵌入的文档)。
3. MongoDB区分类型和大小写。
4. MongoDB的文档不能有重复的键。
5. 文档的键是字符串。除了少数例外情况，键可以使用任意UTF-8字符。

**文档键命名规范**：

- 键不能含有\0 (空字符)。这个字符用来表示键的结尾。
- .和$有特别的意义，只有在特定环境下才能使用。
- 以下划线"_"开头的键是保留的(不是严格要求的)。

#### 集合

集合没有固定的结构，这意味着对集合可以插入不同格式和类型的数据。

当第一个文档插入时，集合就会被创建。

**合法的集合名**

- 集合名不能是空字符串""。
- 集合名不能含有\0字符（空字符)，这个字符表示集合名的结尾。
- 集合名不能以"system."开头，这是为系统集合保留的前缀。
- 用户创建的集合名字不能含有保留字符。有些驱动程序的确支持在集合名里面包含，这是因为某些系统生成的集合中包含该字符。除非你要访问这种系统创建的集合，否则千万不要在名字里出现$。　

#### 数据库

MongoDB的默认数据库为"db"，该数据库存储在data目录中。

**数据库名**可以是满足以下条件的任意UTF-8字符串。

- 不能是空字符串（"")。
- 不得含有' '（空格)、.、$、/、\和\0 (空字符)。
- 应全部小写。
- 最多64字节。

**特殊的数据库**

- **admin**： 从权限的角度来看，这是"root"数据库。要是将一个用户添加到这个数据库，这个用户自动继承所有数据库的权限。一些特定的服务器端命令也只能从这个数据库运行，比如列出所有的数据库或者关闭服务器。
- **local:** 这个数据永远不会被复制，可以用来存储限于本地单台服务器的任意集合
- **config**: 当Mongo用于分片设置时，config数据库在内部使用，用于保存分片的相关信息。

**capped collections**

Capped collections 就是固定大小的collection。

它有很高的性能以及队列过期的特性(过期按照插入的顺序)。

它非常适合类似记录日志的功能和标准的 collection 不同，你必须要显式的创建一个capped collection，指定一个 collection 的大小，单位是字节。其的数据存储空间值提前分配的。

由于 Capped collection 是按照文档的插入顺序而不是使用索引确定插入位置，这样的话可以提高增加数据的效率。

MongoDB 的操作日志文件 oplog.rs 就是利用 Capped Collection 来实现的。

要注意的是指定的存储大小包含了数据库的头信息。

```
db.createCollection("mycoll", {capped:true, size:100000})
```

- 在 capped collection 中，你能添加新的对象。
- 能进行更新，然而，对象不会增加存储空间。如果增加，更新就会失败 。
- 使用 Capped Collection 不能删除一个文档，可以使用 drop() 方法删除 collection 所有的行。
- 删除之后，你必须显式的重新创建这个 collection。
- 在32bit机器中，capped collection 最大存储为 1e9( 1X109)个字节。

#### 元数据

数据库的信息是存储在集合中。它们使用了系统的命名空间：`dbname.system.*`

| 集合命名空间             | 描述                                      |
| :----------------------- | :---------------------------------------- |
| dbname.system.namespaces | 列出所有名字空间。                        |
| dbname.system.indexes    | 列出所有索引。                            |
| dbname.system.profile    | 包含数据库概要(profile)信息。             |
| dbname.system.users      | 列出所有可访问数据库的用户。              |
| dbname.local.sources     | 包含复制对端（slave）的服务器信息和状态。 |

对于修改系统集合中的对象有如下限制。

在system.indexes插入数据，可以创建索引。但除此之外该表信息是不可变的(特殊的drop index命令将自动更新相关信息)。

system.users是可修改的。 system.profile是可删除的

#### 数据类型

| 数据类型           | 描述                                                         |
| :----------------- | :----------------------------------------------------------- |
| String             | 字符串。存储数据常用的数据类型。在 MongoDB 中，UTF-8 编码的字符串才是合法的。 |
| Integer            | 整型数值。用于存储数值。根据你所采用的服务器，可分为 32 位或 64 位。 |
| Boolean            | 布尔值。用于存储布尔值（真/假）。                            |
| Double             | 双精度浮点值。用于存储浮点值。                               |
| Min/Max keys       | 将一个值与 BSON（二进制的 JSON）元素的最低值和最高值相对比。 |
| Array              | 用于将数组或列表或多个值存储为一个键。                       |
| Timestamp          | 时间戳。记录文档修改或添加的具体时间。                       |
| Object             | 用于内嵌文档。                                               |
| Null               | 用于创建空值。                                               |
| Symbol             | 符号。该数据类型基本上等同于字符串类型，但不同的是，它一般用于采用特殊符号类型的语言。 |
| Date               | 日期时间。用 UNIX 时间格式来存储当前日期或时间。你可以指定自己的日期时间：创建 Date 对象，传入年月日信息。 |
| Object ID          | 对象 ID。用于创建文档的 ID。                                 |
| Binary Data        | 二进制数据。用于存储二进制数据。                             |
| Code               | 代码类型。用于在文档中存储 JavaScript 代码。                 |
| Regular expression | 正则表达式类型。用于存储正则表达式。                         |

##### ObjectId

ObjectId 类似唯一主键，可以快速的生成和排序，包含 12 bytes，含义是：

- 前 4 个字节表示创建 **unix** 时间戳，格林尼治时间 **UTC** 时间，比北京时间晚了 8 个小时
- 接下来的 3 个字节是机器标识码
- 紧接的两个字节由进程 id 组成 PID
- 最后三个字节是随机数

MongoDB 中存储的文档必须有一个 _id 键。这个键的值可以是任何类型的，默认是个 ObjectId 对象。

由于 ObjectId 中保存了创建的时间戳，所以你不需要为你的文档保存时间戳字段，你可以通过 getTimestamp 函数来获取文档的创建时间。

```
> var newObject = ObjectId()
> newObject.getTimestamp()
ISODate("2017-11-25T07:21:10Z")
```

ObjectId 转为字符串

```
> newObject.str
5a1919e63df83ce79df8b38f
```

##### String

BSON 字符串都是 UTF-8 编码。

##### Timestamp

BSON 有一个特殊的时间戳类型用于 MongoDB 内部使用，与普通的 日期 类型不相关。 时间戳值是一个 64 位的值。其中：

- 前32位是一个 time_t 值（与Unix新纪元相差的秒数）
- 后32位是在某秒中操作的一个递增的序数

在单个 mongod 实例中，时间戳值通常是唯一的。

在复制集中， oplog 有一个 ts 字段。这个字段中的值使用BSON时间戳表示了操作时间。

> BSON 时间戳类型主要用于 MongoDB 内部使用。在大多数情况下的应用开发中，你可以使用 BSON 日期类型。

##### Date

表示当前距离 Unix新纪元（1970年1月1日）的毫秒数。日期类型是有符号的, 负数表示 1970 年之前的日期。

```
> var mydate1 = new Date()     //格林尼治时间
> mydate1
ISODate("2018-03-04T14:58:51.233Z")
> var mydate2 = ISODate() //格林尼治时间
> mydate2
ISODate("2018-03-04T15:00:45.479Z")
> typeof mydate2
object
```

这样创建的时间是日期类型，可以使用 JS 中的 Date 类型的方法。

返回一个时间类型的字符串：

```
> var mydate1str = mydate1.toString()
> mydate1str
Sun Mar 04 2018 14:58:51 GMT+0000 (UTC) 
> typeof mydate1str
string
```

或者

```
> Date()
Sun Mar 04 2018 15:02:59 GMT+0000 (UTC)   
```

> ```javascript
> >typeof new Date()
> "object"
> >typeof Date()
> "string"
> ```

### 连接

标准 URI 连接语法：

```
mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
```

- **mongodb://** 这是固定的格式，必须要指定。
- **username:password@** 可选项，如果设置，在连接数据库服务器之后，驱动都会尝试登陆这个数据库
- **host1** 必须的指定至少一个host, host1 是这个URI唯一要填写的。它指定了要连接服务器的地址。如果要连接复制集，请指定多个主机地址。
- **portX** 可选的指定端口，如果不填，默认为27017
- **/database** 如果指定username:password@，连接并验证登陆指定数据库。若不指定，默认打开 test 数据库。
- **?options** 是连接选项。如果不使用/database，则前面需要加上/。所有连接选项都是键值对name=value，键值对之间通过&或;（分号）隔开。

### 基本操作

#### 数据库

`show dbs` 显示所有数据的列表

`db` 显示当前数据库对象

`use dbname` 连接指定的数据库

##### 创建数据库

1. `use dbname` 连接数据库
2. `db.collectionname.insert({"key":"value"})`  只有在有内容插入后才会创建

##### 删除数据库

`db.dropDatabase()` 删除当前数据库

#### 集合

`show collections` / `show tables` 查看已有集合

##### 创建集合

`db.creteCollection(name,options)`

options参数如下：

| 字段        | 类型 | 描述                                                         |
| :---------- | :--- | :----------------------------------------------------------- |
| capped      | 布尔 | （可选）如果为 true，则创建固定集合。固定集合是指有着固定大小的集合，当达到最大值时，它会自动覆盖最早的文档。 **当该值为 true 时，必须指定 size 参数。** |
| autoIndexId | 布尔 | （可选）如为 true，自动在 _id 字段创建索引。默认为 false。   |
| size        | 数值 | （可选）为固定集合指定一个最大值，以千字节计（KB）。 **如果 capped 为 true，也需要指定该字段。** |
| max         | 数值 | （可选）指定固定集合中包含文档的最大数量。                   |

插入一些文档时，自动创建集合：

`db.colname.insert({"key":"value"})`

##### 删除集合

`db.colname.drop()`

如果成功删除选定集合，则 drop() 方法返回 true，否则返回 false。

#### 文档

文档的数据结构和 JSON 基本一样。所有存储在集合中的数据都是 BSON 格式。

BSON 是一种类似 JSON 的二进制形式的存储格式，是 Binary JSON 的简称。

##### 插入文档

`db.colname.insert(document)`

> 或使用 db.colname.save() 
>
> 如果不指定 _id 字段 save() 方法类似于 insert() 方法。如果指定 _id 字段，则会更新该 _id 的数据。

如果该集合不在该数据库中， MongoDB 会自动创建该集合并插入文档。

插入数据可以是一条`{}`或者多条`[]`。

可以将数据定义为一个变量。（同js）

3.2 版本后还有以下几种语法可用于插入文档:

- `db.collection.insertOne({})` 向指定集合中插入一条文档数据
- `db.collection.insertMany([])` 向指定集合中插入多条文档数据

##### 更新文档

**update**

```
db.colname.update(
   <query>,
   <update>,
   {
     upsert: <boolean>,
     multi: <boolean>,
     writeConcern: <document>
   }
)
```

- **query** : update的查询条件，类似sql update查询内where后面的。
- **update** : update的对象和一些更新的操作符（如$,$inc...）等，也可以理解为sql update查询内set后面的
- **upsert** : 可选，这个参数的意思是，如果不存在update的记录，是否插入objNew，true为插入，默认是false，不插入。
- **multi** : 可选，mongodb 默认是false,只更新找到的第一条记录，如果这个参数为true,就把按条件查出来多条记录全部更新。
- **writeConcern** :可选，抛出异常的级别。

update：

- $set
- $unset
- $setOnInsert

writeConcern：

- WriteConcern.NONE:没有异常抛出
- WriteConcern.NORMAL:仅抛出网络错误异常，没有服务器错误异常
- WriteConcern.SAFE:抛出网络错误异常、服务器错误异常；并等待服务器完成写操作。
- WriteConcern.MAJORITY: 抛出网络错误异常、服务器错误异常；并等待一个主服务器完成写操作。
- WriteConcern.FSYNC_SAFE: 抛出网络错误异常、服务器错误异常；写操作等待服务器将数据刷新到磁盘。
- WriteConcern.JOURNAL_SAFE:抛出网络错误异常、服务器错误异常；写操作等待服务器提交到磁盘的日志文件。
- WriteConcern.REPLICAS_SAFE:抛出网络错误异常、服务器错误异常；等待至少2台服务器完成写操作。

在3.2版本开始，MongoDB提供以下更新集合文档的方法：

- `db.collection.updateOne(<query>,
     <update>)` 向指定集合更新单个文档

- `db.collection.updateMany(<query>,
     <update>)` 向指定集合更新多个文档

**save**

```
db.colname.save(
   <document>,
   {
     writeConcern: <document>
   }
)
```

- **document** : 文档数据。
- **writeConcern** :可选，抛出异常的级别。

需指定更新数据的主键 _id，不指定则插入该文档数据。

##### 删除文档

```
db.colname.remove(
   <query>,
   <justOne>
)
```

2.6 版本以后的，语法格式如下：

```
db.collection.remove(
   <query>,
   {
     justOne: <boolean>,
     writeConcern: <document>
   }
)
```

- **query** :（可选）删除的文档的条件。

- **justOne** : （可选）如果设为 true 或 1，则只删除一个文档，如果不设置该参数，或使用默认值 false，则删除所有匹配条件的文档。
- **writeConcern** :（可选）抛出异常的级别。

remove() 方法 并不会真正释放空间。

需要继续执行 `db.repairDatabase()` 或 `db.runCommand({ repairDatabase: 1 })` 来回收磁盘空间。

remove() 方法已经过时了，现在官方推荐使用 `deleteOne()` 和 `deleteMany()` 方法。

如删除集合下全部文档：

```
db.inventory.deleteMany({})
```

##### 查询文档

```
db.collection.find(query, projection)
```

- **query** ：可选，使用查询操作符指定查询条件
- **projection** ：可选，使用投影操作符指定返回的键。查询时返回文档中所有键值， 只需省略该参数即可（默认省略）。

如果需以易读的方式来读取数据，可以使用 pretty() 方法，语法格式如下：

```
>db.col.find().pretty()
```

 `findOne()` 方法，只返回一个文档。

###### find 参数

query：

and 条件

传入多个，每个键值对以逗号隔开

or 条件

```
>db.col.find(
   {
      $or: [
         {key1: value1}, {key2:value2}
      ]
   }
).pretty()
```

(不)等式 条件

- 等于	`{<key>:<value>}`
- 小于	`{<key>:{$lt:<value>}`}
- 小于或等于	`{<key>:{$lte:<value>}`}
- 大于	`{<key>:{$gt:<value>}`}
- 大于或等于	`{<key>:{$gte:<value>}`}
- 不等于	`{<key>:{$ne:<value>}`}

query中也可使用正则表达式。

如查询 title 字段以"教"字开头的文档：

```
db.col.find({title:/^教/})
```

其他操作符：

- $type

  $type操作符是基于BSON类型来检索集合中匹配的数据类型，并返回结果。

  如果想获取 "col" 集合中 title 为 String 的数据，你可以使用以下命令：

  `db.col.find({"title" : {$type : 2}})` 或 `db.col.find({"title" : {$type : 'string'}})`

projection：

```
db.collection.find(query, {title: 1, by: 1}) // inclusion模式 指定返回的键，不返回其他键
db.collection.find(query, {title: 0, by: 0}) // exclusion模式 指定不返回的键,返回其他键
```

_id 键默认返回，需要主动指定 _id:0 才会隐藏。

两种模式不可混用（因为这样的话无法推断其他键是否应返回），除了在inclusion模式时可以指定_id为0。

###### limit

`db.COLLECTION_NAME.find().limit(NUMBER)`

指定从MongoDB中读取的记录条数。

如果没有指定limit()方法中的参数则显示集合中的所有数据。

###### skip

```
db.COLLECTION_NAME.find().limit(NUMBER).skip(NUMBER) // limit, skip 顺序随意
```

跳过指定数量的记录数据。

默认参数为 0 。

skip_limit方法只适合小数据量分页，如果是百万级效率就会非常低，因为skip方法是一条条数据数过去的，建议使用where_limit。

另一种分页：时间戳分页方案，这种利用字段的有序性质，利用查询来取数据的方式，可以直接避免掉了大量的数数。

###### sort

```
db.COLLECTION_NAME.find().sort({KEY:1})
```

通过参数指定排序的字段，并使用 1 和 -1 来指定排序的方式，其中 1 为升序排列，而 -1 是用于降序排列。

skip，sort，和limit三者执行顺序和位置无关，执行的顺序是先 sort()，然后是 skip()，最后是显示的 limit()。但是在聚合aggregate中使用的时候，具有管道流的特质，执行顺序是按照位置关系顺序执行的。

#### 索引

索引通常能够极大的提高查询的效率，如果没有索引，MongoDB在读取数据时必须扫描集合中的每个文档并选取那些符合查询条件的记录。

索引存储在一个易于遍历读取的数据集合中，索引是对数据库表中一列或多列的值进行排序的一种结构

##### 创建索引

```
db.collection.createIndex(keys, options)
```

> *注意在 3.0.0 版本前创建索引方法为 db.collection.ensureIndex()，之后的版本使用了 db.collection.createIndex() 方法，ensureIndex() 还能用，但只是 createIndex() 的别名。*

keys 的key值为要创建的索引字段，value指定排序方式，1为按升序创建索引，-1按降序来创建索引。

可以设置使用多个字段创建索引（关系型数据库中称作复合索引）

可选参数列表如下：

| Parameter          | Type          | Description                                                  |
| :----------------- | :------------ | :----------------------------------------------------------- |
| background         | Boolean       | 建索引过程会阻塞其它数据库操作，background可指定以后台方式创建索引，即增加 "background" 可选参数。 "background" 默认值为**false**。 |
| unique             | Boolean       | 建立的索引是否唯一。指定为true创建唯一索引。默认值为**false**. |
| name               | string        | 索引的名称。如果未指定，MongoDB的通过连接索引的字段名和排序顺序生成一个索引名称。 |
| dropDups           | Boolean       | **3.0+版本已废弃。**在建立唯一索引时是否删除重复记录,指定 true 创建唯一索引。默认值为 **false**. |
| sparse             | Boolean       | 对文档中不存在的字段数据不启用索引；这个参数需要特别注意，如果设置为true的话，在索引字段中不会查询出不包含对应字段的文档.。默认值为 **false**. |
| expireAfterSeconds | integer       | 指定一个以秒为单位的数值，完成 TTL设定，设定集合的生存时间。 |
| v                  | index version | 索引的版本号。默认的索引版本取决于mongod创建索引时运行的版本。 |
| weights            | document      | 索引权重值，数值在 1 到 99,999 之间，表示该索引相对于其他索引字段的得分权重。 |
| default_language   | string        | 对于文本索引，该参数决定了停用词及词干和词器的规则的列表。 默认为英语 |
| language_override  | string        | 对于文本索引，该参数指定了包含在文档中的字段名，语言覆盖默认的language，默认值为 language. |

##### 查看索引

查看集合索引

```
db.col.getIndexes()
```

查看集合索引大小

```
db.col.totalIndexSize()
```

##### 删除索引

删除集合所有索引

```
db.col.dropIndexes()
```

删除集合指定索引

```
db.col.dropIndex("索引名称")
```

##### 设置生效时间

http://www.mongoing.com/archives/26867

https://www.runoob.com/mongodb/mongodb-indexing.html

#### 聚合

MongoDB中聚合(aggregate)主要用于处理数据(诸如统计平均值,求和等)，并返回计算后的数据结果。

```
db.COLLECTION_NAME.aggregate(AGGREGATE_OPERATION)
```

聚合操作可以是单个操作`{}`，也可以有多个操作`[]`，多个操作按顺序执行。

示例：

```
db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$sum : 1}}}])
```

类似sql语句，`select by_user, count(*) from mycol group by by_user`。

常用聚合操作表达式：

| 表达式    | 描述                                           | 实例                                                         |
| :-------- | :--------------------------------------------- | :----------------------------------------------------------- |
| $sum      | 计算总和。                                     | db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$sum : "$likes"}}}]) |
| $avg      | 计算平均值                                     | db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$avg : "$likes"}}}]) |
| $min      | 获取集合中所有文档对应值得最小值。             | db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$min : "$likes"}}}]) |
| $max      | 获取集合中所有文档对应值得最大值。             | db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$max : "$likes"}}}]) |
| $push     | 在结果文档中插入值到一个数组中。               | db.mycol.aggregate([{$group : {_id : "$by_user", url : {$push: "$url"}}}]) |
| $addToSet | 在结果文档中插入值到一个数组中，但不创建副本。 | db.mycol.aggregate([{$group : {_id : "$by_user", url : {$addToSet : "$url"}}}]) |
| $first    | 根据资源文档的排序获取第一个文档数据。         | db.mycol.aggregate([{$group : {_id : "$by_user", first_url : {$first : "$url"}}}]) |
| $last     | 根据资源文档的排序获取最后一个文档数据         | db.mycol.aggregate([{$group : {_id : "$by_user", last_url : {$last : "$url"}}}]) |

时间聚合操作：

- `$dayOfYear` 返回该日期是这一年的第几天（全年 366 天）。
- `$dayOfMonth` 返回该日期是这一个月的第几天（1到31）。
- `$dayOfWeek` 返回的是这个周的星期几（1：星期日，7：星期六）。
- `$year` 返回该日期的年份部分。
- `$month` 返回该日期的月份部分（ 1 到 12）。
- `$week` 返回该日期是所在年的第几个星期（ 0 到 53）。
- `$hour` 返回该日期的小时部分。
- `$minute` 返回该日期的分钟部分。
- `$second` 返回该日期的秒部分（以0到59之间的数字形式返回日期的第二部分，但可以是60来计算闰秒）。
- `$millisecond` 返回该日期的毫秒部分（ 0 到 999）。
- `$dateToString` `{ $dateToString: { format: , date: } }`。

##### 管道

聚合管道将MongoDB文档在一个管道处理完毕后将结果传递给下一个管道处理。管道操作是可以重复的。

聚合中常用操作：

- `$project`修改输入文档的结构。可以用来重命名、增加或删除域，也可以用于创建计算结果以及嵌套文档。

  非0表示显示该字段，0表示不显示。

- `$match`用于过滤数据，只输出符合条件的文档。$match使用MongoDB的标准查询操作。

- `$limit`用来限制MongoDB聚合管道返回的文档数。

- `$skip`在聚合管道中跳过指定数量的文档，并返回余下的文档。

- `$unwind`将文档中的某一个数组类型字段拆分成多条，每条包含数组中的一个值。

- `$group`将集合中的文档分组，可用于统计结果。

  键值对的键为显示字段名，值及为该字段的值。第一个键的必须是`_id`，其值没有要求。

- `$sort`将输入文档排序后输出。

- `$geoNear`输出接近某一地理位置的有序文档。

### 数据关系

文档间可以通过嵌入和引用来建立联系。

MongoDB 中的关系可以是：

- 1:1 (1对1)
- 1: N (1对多)
- N: 1 (多对1)
- N: N (多对多)

比如，用户与用户地址的1对多关系

#### 嵌入式关系

嵌入式关系，即直接将用户地址嵌入到用户文档中。

```json
{...
   "address": [
      {
         "building": "22 A, Indiana Apt",
      },
      {
         "building": "170 A, Acropolis Apt",
      }]
} 
```

数据保存在单一的文档中，可以比较容易的获取和维护数据。 

缺点是，如果用户和用户地址在不断增加，数据量不断变大，会影响读写性能。

#### 引用式关系

引用式关系把用户数据文档和用户地址数据文档分开，通过引用文档的 **id** 字段来建立关系。

```json
{...
    "address_ids": [
      ObjectId("52ffc4a5d85242602e000000"),
      ObjectId("52ffc4a5d85242602e000001")
   ]
}
```

如上实例，用户文档的 address_ids 字段包含用户地址的对象id（ObjectId）数组。可以读取这些用户地址的对象id（ObjectId）来获取用户的详细地址信息。

这种方法需要两次查询，第一次查询用户地址的对象id（ObjectId），第二次通过查询的id获取用户的详细地址信息。

```json
var result = db.users.findOne({"name":"Tom Benzamin"},{"address_ids":1})
var addresses = db.address.find({"_id":{"$in":result["address_ids"]}})
```

### 数据库引用

MongoDB 引用有两种：

- 手动引用（Manual References）
- DBRefs

当一个文档从多个集合引用文档，应该使用 DBRefs。

DBRef的形式：

```json
{ $ref : , $id : , $db :  }
```

- $ref：集合名称
- $id：引用的id
- $db:数据库名称，可选参数

使用示例：

```json
{...
   "address": {
      "$ref": "address_home",
      "$id": ObjectId("534009e4d852427820000002"),
      "$db": "runoob"
   }
}
```

通过指定 $ref 参数来查找指定集合中指定id的用户地址信息：

```javascript
var user = db.users.findOne({"name":"Tom Benzamin"})
var dbRef = user.address
db[dbRef.$ref].findOne({"_id":(dbRef.$id)})
```

> 注意`_id`的值不要漏了`ObjectId()`。

### 覆盖索引查询

覆盖查询是以下的查询：

- 所有的查询字段是索引的一部分
- 所有的查询返回字段在同一个索引中

由于所有出现在查询中的字段是索引的一部分， MongoDB 无需在整个数据文档中检索匹配查询条件返回查询结果，而是从索引中提取数据。因为索引存在于RAM中，从索引中获取数据比通过扫描文档读取数据要快得多。

```javascript
db.users.find({gender:"M"},{user_name:1,_id:0})
```

注意，`_id`会默认返回，如果索引不包括`_id`，需将其排除，否则查询不会被覆盖。

如果是以下的查询，不能使用覆盖索引查询：

- 所有索引字段是一个数组
- 所有索引字段是一个子文档

### 查询分析

查询分析可以确保我们所建立的索引是否有效，是查询语句性能分析的重要工具。

MongoDB 查询分析常用函数有：explain() 和 hint()。

#### explain()

explain 操作提供了查询信息，使用索引及查询统计等。有利于我们对索引的优化。

使用示例：

```javascript
>db.users.find({gender:"M"},{user_name:1,_id:0}).explain()
{
   "cursor" : "BtreeCursor gender_1_user_name_1",
   "isMultiKey" : false,
   "n" : 1,
   "nscannedObjects" : 0,
   "nscanned" : 1,
   "nscannedObjectsAllPlans" : 0,
   "nscannedAllPlans" : 1,
   "scanAndOrder" : false,
   "indexOnly" : true,
   "nYields" : 0,
   "nChunkSkips" : 0,
   "millis" : 0,
   "indexBounds" : {
      "gender" : [
         [
            "M",
            "M"
         ]
      ],
      "user_name" : [
         [
            {
               "$minElement" : 1
            },
            {
               "$maxElement" : 1
            }
         ]
      ]
   }
}
```

- **indexOnly**：字段为 true ，表示我们使用了索引。
- **cursor**：因为这个查询使用了索引，MongoDB 中索引存储在B树结构中，所以这是也使用了 BtreeCursor 类型的游标。如果没有使用索引，游标的类型是 BasicCursor。这个键还会给出你所使用的索引的名称，你通过这个名称可以查看当前数据库下的system.indexes集合（系统自动创建，由于存储索引信息，这个稍微会提到）来得到索引的详细信息。
- **n**：当前查询返回的文档数量。
- **nscanned/nscannedObjects**：表明当前这次查询一共扫描了集合中多少个文档，我们的目的是，让这个数值和返回文档的数量越接近越好。
- **millis**：当前查询所需时间，毫秒数。
- **indexBounds**：当前查询具体使用的索引。

#### hint()

虽然MongoDB查询优化器一般工作的很不错，但是也可以使用 hint 来强制 MongoDB 使用一个指定的索引。

这种方法某些情形下会提升性能。 一个有索引的 collection 并且执行一个多字段的查询(一些字段已经索引了)。

使用示例：

```javascript
db.users.find({gender:"M"},{user_name:1,_id:0}).hint({gender:1,user_name:1}).explain()
```

### 原子操作

mongodb不支持事务，但是mongodb提供了许多原子操作，比如文档的保存，修改，删除等，都是原子操作。

#### 原子操作数据模型

`db.collection.findAndModify()`命令可以返回指定条件的数据并对其执行更新操作。

示例：

```javascript
db.books.findAndModify ( {
   query: {
            _id: 123456789,
            available: { $gt: 0 }
          },
   update: {
             $inc: { available: -1 },
             $push: { checkout: { by: "abc", date: new Date() } }
           }
} )
```

与 update 区别

- findAndModify是有返回值的

  输出中的value字段即返回修改之前的文档，使用 new:true 选项返回修改后的文档。

  update是更新操作，是没有返回值的。

- findAndModify强调操作的原子性（atomically）。

- 属于 get-and-set 式的操作，一般来讲，findAndModify 比update操作稍慢，因为需要等待数据库的响应。

- 另外findAndModify ，其中modify可以是update，还可以是remove。

#### 原子操作常用命令

##### $set

用来指定一个键并更新键值，若键不存在并创建。

```
{ $set : { field : value } }
```

##### $unset

用来删除一个键。

```
{ $unset : { field : 1} }
```

##### $inc

$inc可以对文档的某个值为数字型（只能为满足要求的数字）的键进行增减的操作。

```
{ $inc : { field : value } }
```

##### $push

```
{ $push : { field : value } }
```

把value追加到field里面去，field一定要是数组类型才行，如果field不存在，会新增一个数组类型加进去。

##### $pushAll

同$push，只是一次可以追加多个值到一个数组字段内。

```
{ $pushAll : { field : value_array } }
```

##### $pull

从数组field内删除一个等于value值。

```
{ $pull : { field : _value } }
```

##### $addToSet

增加一个值到数组内，而且只有当这个值不在数组内才增加。

##### $pop

删除数组的第一个或最后一个元素

```
{ $pop : { field : 1 } }
```

##### $rename

修改字段名称

```
{ $rename : { old_field_name : new_field_name } }
```

##### $bit

位操作，integer类型

```
{$bit : { field : {and : 5}}}
```

### 高级索引

```json
{
   "address": {
      "city": "Los Angeles",
      "state": "California",
      "pincode": "123"
   },
   "tags": [
      "music",
      "cricket",
      "blogs"
   ],
   "name": "Tom Benzamin"
}
```

#### 索引数组字段

在数组中创建索引，需要对数组中的每个字段依次建立索引。

如前示例，为数组 tags 创建索引时，会为 music、cricket、blogs三个值建立单独的索引。

```javascript
db.users.ensureIndex({"tags":1})
```

创建索引后，我们可以这样检索集合的 tags 字段：

```javascript
db.users.find({tags:"cricket"})
```

为了验证我们使用使用了索引，可以使用 explain 命令：

```javascript
db.users.find({tags:"cricket"}).explain()
```

以上命令执行结果中会显示 "cursor" : "BtreeCursor tags_1" ，则表示已经使用了索引。

#### 索引子文档字段

假设我们需要通过city、state、pincode字段来检索文档，由于这些字段是子文档的字段，所以我们需要对子文档建立索引。

为子文档的三个字段创建索引，命令如下：

```javascript
db.users.ensureIndex({"address.city":1,"address.state":1,"address.pincode":1})
```

一旦创建索引，我们可以使用子文档的字段来检索数据：

```javascript
db.users.find({"address.city":"Los Angeles"})   
```

查询表达不一定遵循指定的索引的顺序，mongodb 会自动优化。所以上面创建的索引将支持以下查询：

```javascript
db.users.find({"address.state":"California","address.city":"Los Angeles"}) 
db.users.find({"address.city":"Los Angeles","address.state":"California","address.pincode":"123"})
```


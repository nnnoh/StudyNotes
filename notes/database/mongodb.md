## MongoDB

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

### 命令

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

需指定主键 _id，不指定则插入该文档数据。

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

and 条件：

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


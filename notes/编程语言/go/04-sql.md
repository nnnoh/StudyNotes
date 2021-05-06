# database/sql

**database/sql** 包用于对 SQL 的数据库的访问。

其中，**sql.DB** 是一个已存在的数据库的抽象访问接口。

- sql.DB 通过数据库驱动为我们管理底层数据库连接的打开和关闭操作.
- sql.DB 为我们管理数据库连接池

sql.DB 是以连接池的方式管理数据库连接，我们每次进行数据库操作时，都需要从连接池中取出一个连接。当操作任务完成时，我们需要将此连接返回到连接池中。

因此如果我们没有正确地将连接返回给连接池，那么会造成 db.SQL 打开过多的数据库连接，使数据库连接资源耗尽。

## 基本操作

### 数据库驱动的导入

 我们需要借助于一个数据库驱动才能和具体的数据库进行连接。这在 Golang 中也不例外。例如以 MySQL 数据库为例：

```go
import (
    "database/sql"
    _ "github.com/go-sql-driver/mysql"
)
```

需要注意的是，通常来说，我们不应该直接使用驱动所提供的方法，而是应该使用 sql.DB，因此在导入 mysql 驱动时，我们使用了匿名导入的方式。
当导入了一个数据库驱动后，此驱动会自行初始化并注册自己到 Golang 的 database/sql 上下文中，因此我们就可以通过 database/sql 包提供的方法访问数据库了。

### 数据库的连接

```go
func main() {
    db，err := sql.Open("mysql"，
        "user:password@tcp(127.0.0.1:3306)/test")
    if err != nil {
        log.Fatal(err)
    }
    defer db.Close()
}
```

通过 sql.Open 函数，可以创建一个数据库抽象操作接口，如果打开成功的话，它会返回一个 sql.DB 指针。

```
func Open(driverName，dataSourceName string) (*DB，error)
```

 sql.Open 接收两个参数:

- driverName，使用的驱动名。这个名字其实就是数据库驱动注册到 database/sql 时所使用的名字。

- dataSourceName，驱动程序特定的语法，告诉驱动程序如何访问底层数据存储。这个链接包含了数据库的用户名，密码，数据库主机以及需要连接的数据库名等信息。

  mysql链接示例：`用户名:密码@tcp(IP:端口)/数据库?charset=utf8`

需要注意的是，golang 对数据库的连接是延时初始化的(lazy init)，即 sql.Open 并不会立即建立一个数据库的网络连接，也不会对数据库链接参数的合法性做检验，它仅仅是初始化一个 sql.DB 对象。当我们进行第一次数据库查询操作时，此时才会真正建立网络连接。

如果我们想立即检查数据库连接是否可用，那么可以利用 sql.DB 的 Ping 方法，例如:

```go
err = db.Ping()
if err != nil {
    log.Fatal(err)
}
```

sql.DB 对象是作为长期生存的对象来使用的，我们应当避免频繁地调用 Open() 和 Close()。即一般来说，我们要对一个数据库进行操作时，创建一个 sql.DB 并将其保存起来，每次操作此数据库时，传递此 sql.DB 对象。

### 数据库的查询

数据库查询的一般步骤如下:

- 调用 db.Query 执行 SQL 语句，此方法会返回一个 Rows 作为查询的结果
- 通过 rows.Next() 迭代查询数据.
- 通过 rows.Scan() 读取每一行的值
- 调用 db.Close() 关闭查询

Insert 示例：

```go
func insertData(db *sql.DB) {
    rows，err := db.Query(`INSERT INTO user (id，name，age) VALUES (1，"xys"，20)`)
    defer rows.Close()
    if err != nil {
        log.Fatalf("insert data error: %v\n"，err)
    }

    var result int
    rows.Scan(&result)
    log.Printf("insert result %v\n"，result)
}
```

Select 示例：

```go
func selectData(db *sql.DB) {
    var id int
    var name string
    var age int
    rows，err := db.Query(`SELECT * From user where id = 1`)
    if err != nil {
        log.Fatalf("insert data error: %v\n"，err)
        return
    }
    for rows.Next() {
        rows.Scan(&id，&age，&name)
        if err != nil {
            log.Fatal(err)
        }
        log.Printf("get data，id: %d，name: %s，age: %d"，id，name，age)
    }

    err = rows.Err()
    if err != nil {
        log.Fatal(err)
    }
}
```

**rows.Scan** 参数的顺序很重要，需要和查询的结果的 column 对应。例如 "SELECT * From user where id = 1" 查询的行的 column 顺序是 "id，name，age"，因此 rows.Scan 也需要按照此顺序 rows.Scan(&id，&name，&age)，不然会造成数据读取的错位。

注意:

1. 对于每个数据库操作都需要检查是否有错误返回。
2. 每次 db.Query 操作后，都需要调用 rows.Close()。因为 db.Query() 会从数据库连接池中获取一个连接，如果我们没有调用 rows.Close()，则此连接会一直被占用。因此通常我们使用 **defer rows.Close()** 来确保数据库连接可以正确放回到连接池中。
3. 多次调用 rows.Close() 不会有副作用。

#### 常用方法

- `result, err := db.Exec("INSERT INTO userinfo (username, departname, created) VALUES (?, ?, ?)","lily","销售","2016-06-21")`

  执行数据修改 SQL。

- `err := db.QueryRow("SELECT username,departname,created FROM userinfo WHERE uid=?", 3).Scan(&username, &departname, &created)`

  查询单条数据。

- `id, err := res.LastInsertId()`

  获得刚刚添加数据的自增 ID。

### 预编译语句(Prepared Statement)

- PreparedStatement 可以实现自定义参数的查询
- PreparedStatement 通常来说，比手动拼接字符串 SQL 语句高效.
- PreparedStatement 可以防止SQL注入攻击

```go
func insertData(db *sql.DB) {
    stmt，_ := db.Prepare(`INSERT INTO user (id，name，age) VALUES (?，?，?)`)

    rows，err := stmt.Query(1，"xys"，20)
    defer stmt.Close()

    rows.Close()
    if err != nil {
        log.Fatalf("insert data error: %v\n"，err)
    }

    rows，err = stmt.Query(2，"test"，19)
    var result int
    rows.Scan(&result)
    log.Printf("insert result %v\n"，result)
    rows.Close()
}
```

### 事务

- 在操作数据库之前执行，db.Begin()

  例：tx, err := db.Begin()
  
- 保存到数据库：err := tx.Commit()
  
- 回滚：err := tx.Rollback()
  

注意

- 设置事务以后操作数据库就不是 db，而是 tx。
- 在创建数据库中要注意对应引擎。Innodb 支持事务安全，而 MyISAM 不支持事务操作。
- 一个 `tx` 正常情况下只能执行一次的 `Commit` 或 `Rollback`。在第一次正常执行过后，再次调用执行，会检查到已经 `done`，返回一个 `error`。

**事务执行示例**：

- 参考：[go - database/sql Tx - detecting Commit or Rollback - Stack Overflow](https://stackoverflow.com/questions/16184238/database-sql-tx-detecting-commit-or-rollback)

```go
func Transact(db *sql.DB, txFunc func(*sql.Tx) error) (err error) {
    tx, err := db.Begin()
    if err != nil {
        return
    }
    defer func() {
        if p := recover(); p != nil {
            tx.Rollback()
            panic(p) // re-throw panic after Rollback
        } else if err != nil {
            tx.Rollback() // err is non-nil; don't change it
        } else {
            err = tx.Commit() // err is nil; if Commit returns error update err
        }
    }()
    err = txFunc(tx)
    return err
}

func (s Service) DoSomething() error {
    return Transact(s.db, func (tx *sql.Tx) error {
        if _, err := tx.Exec(...); err != nil {
            return err
        }
        if _, err := tx.Exec(...); err != nil {
            return err
        }
        return nil
    })
}
```

另一种写法：

`defer tx.Rollback()` 使得事务回滚始终得到执行。 

- 当 `tx.Commit()` 执行后，`tx.Rollback()` 什么也不做。 

- 当程序因为某个错误中止，`tx.Rollback()` 起到回滚事务。

```go
func DoSomething() (err error) {
  tx, _ := db.Begin()
  defer tx.Rollback()

  if _, err = tx.Exec(...); err != nil {
      return
  }
  if _, err = tx.Exec(...); err != nil {
      return
  }
  // ...


  err = tx.Commit()
  return
}
```

#### 事务与连接

- 事务中使用 `prepare` 时注意 `stmt.Close()` 执行的位置。

  `tx.Commit` 就已经释放了连接。当函数退出的时候，再执行 `stmt.Close` 的时候，连接可能又被使用了。

- 一旦创建了 `tx` 对象，事务处理都依赖与 `tx` 对象，这个对象会从连接池中取出一个空闲的连接，接下来的sql执行都基于这个连接，直到 `commit` 或者 `rollback` 调用之后，才会把连接释放到连接池。

- 事务中也可以使用 `db` 进行查询，但是 `db` 查询的过程会新建连接，这个连接的操作不属于该事务。不建议 `db` 和 `tx` 穿插使用。

#### 事务并发

对于 `sql.Tx` 对象，因为事务过程只有一个连接，事务内的操作都是顺序执行的，在开始下一个数据库交互之前，必须先完成上一个数据库交互。

```go
rows, _ := tx.Query("SELECT id FROM user")
for rows.Next() {
   var mid, did int
   rows.Scan(&mid)
   tx.QueryRow("SELECT id FROM detail_user WHERE master = ?", mid).Scan(&did)
}
```

上面的例子中，`tx` 执行了 `Query` 方法后，连接转移到 `rows` 上，在 `Next` 方法中，`tx.QueryRow` 将尝试获取该连接进行数据库操作。因为还没有调用`rows.Close` ，因此底层的连接属于busy状态，`tx` 是无法再进行查询的。

 将 `tx` 换成 `db` 是没问题的，`rows` 和 `db` 的连接两者可以并存，并且相互不影响。（实际中上面的例子应使用 `query` 的 `join`语句）。
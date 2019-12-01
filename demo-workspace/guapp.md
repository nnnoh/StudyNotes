## Gu

### 前端

> 博客 参考： https://github.com/BranSummer/branroom 
>
> 后台管理 模板： https://github.com/fanjyy/nepadmin 

首页

公开页

### 后台

#### 用户权限管理

##### 数据表

###### 用户信息表

```sql
CREATE TABLE `ums_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `status` int(1) DEFAULT '1' COMMENT '状态 0:禁用，1:启用',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `creat_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表'
```

###### 角色表



###### 权限表



###### 用户角色关系表

```sql
CREATE TABLE `ums_user_role_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='用户角色关系表'
```

###### 角色权限关系表

##### API

###### login

###### register

#### 博客系统

##### 数据表

###### 文章分类表

```sql
CREATE TABLE `bams_article_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主题',
  `name` varchar(20) NOT NULL COMMENT '分类名称',
  `creat_time` datetime NOT NULL COMMENT '分类创建时间',
  `update_time` datetime NOT NULL COMMENT '分类修改时间',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态 0:禁用, 1:启用',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章分类表'
```

###### 文章信息表

```sql
CREATE TABLE `bams_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `user_id` bigint(20) NOT NULL COMMENT '作者id',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类id',
  `cover` varchar(100) DEFAULT NULL COMMENT '封面图片',
  `state` tinyint(2) DEFAULT '1' COMMENT '状态 0:禁用, 1:暂存, 2:发布',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `edit_time` datetime NOT NULL COMMENT '上次修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `content_id` bigint(20) DEFAULT NULL COMMENT '内容id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章信息表'
```

> todo 后续增加 username字段 

###### 文章内容表

```sql
CREATE TABLE `bams_article_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主题',
  `content` text NOT NULL COMMENT '文章内容',
  `creat_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `article_id` bigint(20) DEFAULT NULL COMMENT '文章id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `article_id` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章内容表'
```

> create_time, updatetime 由数据库设置

##### API

> 更新操作只对非空更新字段有效

#### 文件管理系统

##### 数据表

```sql
CREATE TABLE `fms_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `filename` varchar(50) DEFAULT NULL COMMENT '文件名',
  `url` varchar(100) DEFAULT NULL COMMENT 'url',
  `business` varchar(20) DEFAULT NULL COMMENT '业务名',
  `location_path` varchar(100) DEFAULT NULL COMMENT '本地路径',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态 0:禁用，1:启用',
  `creat_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件信息表'
```

> url 目前为静态资源映射的路径












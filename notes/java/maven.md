## Maven

[Maven-易百教程](https://www.yiibai.com/maven)

[Maven-菜鸟教程](https://www.runoob.com/maven/maven-tutorial.html)

### Maven环境配置

### 创建Java项目

**mvn archetype:generate**

```powershell
mvn archetype:generate -DgroupId={project-packaging} -DartifactId={project-name}-DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

> e.g. mvn archetype:generate -DgroupId=com.mycompany.bigproject -DartifactId=myapp -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
>
> powershell会error，CMD没问题。

参数说明：

- **-DgourpId**: 组织标识（包名）
- **-DartifactId**: 项目名称
- **-DarchetypeArtifactId**: 指定 ArchetypeId；
  - maven-archetype-quickstart 创建一个简单的 Java 应用
  - maven-archetype-webapp 创建一个Web Project
- **-DinteractiveMode**: 是否使用交互模式

Maven 创建好项目后还需要手动创建 src/main/resources (存放项目开发中用到的配置文件，如存放 log4j.properties 等)和 src/test/resources (存放测试时用到的配置文件)。

[Standard Directory Layout](http://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)

**编译**	mvn [clean] compile

**测试**	mvn [clean] test

**打包**	mvn package

​	生成结果存放在target目录。

**安装**	mvn clean install

​	执行安装命令前，会先执行编译、测试、打包命令；构建成功，就会将项目的jar包安装到**本地仓库**(maven)

**运行**	java -cp target\myapp-1.0-SNAPSHOT.jar com.mycompany.app.App

> q: Maven [ERROR] 不再支持源选项 5。请使用 6 或更高版本tml)
>
> a: 在settings.xml文件或项目的pom.xml文件中填加下列代码指定jdk版本
>
> ```xml
> <properties>
> <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
> <maven.compiler.source>10</maven.compiler.source> 
> <maven.compiler.target>10</maven.compiler.target> 
> </properties> 
> ```
>
> 1. settings.xml	全局(%MAVEN_HOME%\\conf\\settings.xml)/用户
>
> \<profile> 在里面指定jdk版本
>
> 2. pom.xml
>
> \<properties>元素是根元素\<project>的子元素

### 生命周期

Maven 构建生命周期定义了一个项目构建跟发布的过程。

一个典型的 Maven 构建（build）生命周期是由以下几个阶段的序列组成的

| 阶段          | 处理     | 描述                                                     |
| :------------ | :------- | :------------------------------------------------------- |
| 验证 validate | 验证项目 | 验证项目是否正确且所有必须信息是可用的                   |
| 编译 compile  | 执行编译 | 源代码编译在此阶段完成                                   |
| 测试 Test     | 测试     | 使用适当的单元测试框架（例如JUnit）运行测试。            |
| 包装 package  | 打包     | 创建JAR/WAR包如在 pom.xml 中定义提及的包                 |
| 检查 verify   | 检查     | 对集成测试的结果进行检查，以保证质量达标                 |
| 安装 install  | 安装     | 安装打包的项目到本地仓库，以供其他项目使用               |
| 部署 deploy   | 部署     | 拷贝最终的工程包到远程仓库中，以共享给其他开发人员和工程 |

Maven 有以下三个标准的生命周期：

- **clean**：项目清理的处理
- **default(或 build)**：项目部署的处理
- **site**：项目站点文档创建的处理

在一个生命周期中，运行某个阶段的时候，它之前的所有阶段都会被运行。

#### Clean 生命周期

当我们执行 mvn post-clean 命令时，Maven 调用 clean 生命周期，它包含以下阶段：

- pre-clean：执行一些需要在clean之前完成的工作
- clean：移除所有上一次构建生成的文件
- post-clean：执行一些需要在clean之后立刻完成的工作

#### Site 生命周期

Maven Site 插件一般用来创建新的报告文档、部署站点等。

- pre-site：执行一些需要在生成站点文档之前完成的工作
- site：生成项目的站点文档
- post-site： 执行一些需要在生成站点文档之后完成的工作，并且为部署做准备
- site-deploy：将生成的站点文档部署到特定的服务器上

### POM

pom.xml文件的基本节点元素说明：

- \<project>  pom文件的顶级节点
- \<modelVersion>  object model版本，对Maven2和Maven3来说，只能是4.0.0　
- \<groupId>  项目创建组织的标识符，一般是域名的倒写
- \<artifactId>   定义了项目在所属组织的标识符下的唯一标识，一个组织下可以有多个项目
- \<version>  当前项目的版本，SNAPSHOT，表示是快照版本，在开发中
- \<packaging>  打包的方式，有jar、war、ear等
- \<name>  项目的名称
- \<url>  项目的地址
- \<properties>  属性配置，比如：<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
- \<dependencies>  构建项目依赖的jar

　　其中**由groupId、artifactId和version唯一的确定了一个项目坐标**

#### scope

[maven scope属性说明](https://www.cnblogs.com/kingsonfu/p/10342892.html)

scope定义了类包在项目的使用阶段。项目阶段包括： 编译，运行，测试和发布。

- compile
  默认scope为compile，表示为当前依赖参与项目的编译、测试和运行阶段，属于强依赖。打包之时，会达到包里去。
- test
  该依赖仅仅参与测试相关的内容，包括测试用例的编译和执行，比如定性的Junit。
- runtime
  依赖仅参与运行周期中的使用。一般这种类库都是接口与实现相分离的类库，比如JDBC类库，在编译之时仅依赖相关的接口，在具体的运行之时，才需要具体的mysql、oracle等等数据的驱动程序。
  此类的驱动都是为runtime的类库。
- provided
  该依赖在打包过程中，不需要打进去，这个由运行的环境来提供，比如tomcat或者基础类库等等，事实上，该依赖可以参与编译、测试和运行等周期，与compile等同。区别在于打包阶段进行了exclude操作。
- system
  使用上与provided相同，不同之处在于该依赖不从maven仓库中提取，而是从本地文件系统中提取，其会参照systemPath的属性进行提取依赖。
- import
  这个是maven2.0.9版本后出的属性，import只能在dependencyManagement的中使用，能解决maven单继承问题，import依赖关系实际上并不参与限制依赖关系的传递性。

#### model

多个模块联合编译，即聚合。只需在父项目使用model声明模块名（artifactId）。编译父项目时便会去找子模块并编译子模块引入的依赖。

```xml
<models>
    <model>childA</model>
</models>
```

#### parent

为了子pom可以引用到父pom中引入的依赖，使用parent元素。

```xml
<parent>
   <groupId>com.sang.main</groupId>
   <artifactId>Parent-Moduel</artifactId>
   <version>1.0.2</version>
   <relativePath>../pom.xml</relativePath>  <!--可选-->
</parent>
```

relativePath 指定父pom位置，默认值为../pom.xml。

查找顺序：relativePath元素中的地址–本地仓库–远程仓库。

设定一个空值将始终从仓库中获取，不从本地路径获取，如`<relativePath />`。

子pom：

```xml
<modelVersion>4.0.0</modelVersion>  
<groupId>com.group</groupId>     <!--可选-->
<artifactId>ChildA-module</artifactId>
```

创建一个parent项目，打包类型为pom，parent项目中不存放任何代码，只是管理多个项目之间公共的依赖。对于所有项目都需要的依赖声明在dependencies中，而部分项目需要的依赖的声明在dependencyManagement中，plugin同理。

#### dependencyManagement

通过 dependencyManagement 元素来管理jar包的版本，让子项目中引用一个依赖而不用显示的列出版本号。Maven会沿着父子层次向上走，直到找到一个拥有dependencyManagement元素的项目，然后就会使用在该元素中指定的版本号。

```xml
<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>xxx</groupId>
                <artifactId>xxx</artifactId>
                <version>xxx</version>
            </dependency>
    </dependencies>
</dependencyManagement>
```

当某个子项目需要另外一个版本号时，只需要在dependency中声明一个版本号即可。

##### 与 dependencies 区别

- dependencies即使在子项目中不写该依赖项，那么子项目仍然会从父项目中继承该依赖项（全部继承）

- dependencyManagement里只是声明依赖，并不实现引入，因此子项目需要显示的声明需要用的依赖。

https://www.jellythink.com/archives/510

gradle 区别

项目编译 目录



### 自定义 Maven Archetype

Maven自身提供了许多Archetype来方便用户创建Project，但是每个团队都可能会有一些常用的文件或配置，为了避免在创建project时重复的拷贝和修改，可以自定义自己的Archetype。

Archetype项目：

- `pom.xml`用于定义archetype项目的坐标等信息。

- `src/main/resources/archetype-resources`下的所有内容定义了待生成项目的所有文件（原型文件）
- `src/main/resources/META-INF/maven/archetype.xml`中定义骨架的描述符（元数据），这个文件列出了包含在archetype中的所有文件并将这些文件分类。

除了直接创建Archetype项目，还可以基于已有项目创建脚手架，在当前项目的pom文件中增加插件：

```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-archetype-plugin</artifactId>
    <version>3.1.1</version>
</plugin>
```

### 重复版本依赖

查看依赖，可以通过mvn dependency:tree 打印依赖树，或使用 ide 功能查看。

#### 重复版本依赖原则

- 最短路径原则

  项目中就出现了两个版本的依赖时，maven会采用声明路径最短的版本声明。

- 声明优先原则

  当两个版本的依赖声明路径都是一样长，按照依赖包在pom.xml中声明的先后顺序，优先选择先声明的包。

#### 解决版本冲突

- 排除依赖

  在`dependency`中使用`exclusions`声明排除的依赖`exclusion`。

- 版本锁定

  在`dependencyManagement`声明依赖版本，需要使用时不必考虑依赖版本，以统一版本号。

### pom示例

#### 常用插件

##### 指定编译的工具和jdk版本

```xml
 	<properties>
        <java.version>1.8</java.version>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

### Tips

#### 修改仓库

修改 Maven 根目录下 conf/setting.xml 文件。在 `<mirrors>` 标签中添加下列内容。然后 update  setting。

```xml
	<mirror>
	  <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
	</mirror>
```

#### Gradle 项目转 Maven

1. 项目根目录执行 gradle init；

2. 在 build.gradle 添加如下内容：

   ```
   apply plugin: 'java'
   apply plugin: 'maven'
     
   group = 'com.bazlur.app'
   // artifactId is taken by default, from folder name
   version = '0.1-SNAPSHOT'
     
   dependencies {
   compile 'commons-lang:commons-lang:2.3'
   }
   ```

3. gradle install

4. 在 build/poms 下生成了 pom-default.xml，把它修改为 pom.xml，放到项目根目录下。

#### 修改 eclipse 建立 maven 项目时 JDK 的版本

在 settings.xml 文件的 profiles 标签里增加 profile 节点。

```xml
<profile>  
      <id>jdk-1.8</id>  
	  <activation>  
	    <activeByDefault>true</activeByDefault>  
	    <jdk>1.8</jdk>  
	  </activation>  
	  <properties>  
	    <maven.compiler.source>1.8</maven.compiler.source>
		<maven.compiler.target>1.8</maven.compiler.target>  
		<maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>  
	  </properties>  
	</profile>
```

#### setting.xml

eclipse -> Preferences -> Maven -> User Setting 确认路径。

#### pom.xml Errors/Warnings

##### web.xml is missing and \<failOnMissingWebXml> is set to true

因为 `<packaging>war</packaging>`，老版本 Maven 认为 web 应用需要 web.xml。而如今在 web 应用中 web.xml 配置文件不是必须。

**不创建 web.xml**

在 pom.xml 文件中手动添加如下配置。

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-war-plugin</artifactId>
            <version>2.6</version>
            <configuration>
                <failOnMissingWebXml>false</failOnMissingWebXml>
            </configuration>
        </plugin>
    </plugins>
</build>
```

在更新的版本的Maven中不存在web.xml文件缺失的问题，只需要处理 `<failOnMissingWebXml>`。

```xml
<properties>
    <failOnMissingWebXml>false</failOnMissingWebXml>
</properties>
```

**创建 web.xml**

Eclipse 中，项目右键菜单 javaee tools -> generator deployment descriptor stub，生成 web.xml。

在创建 Dynamic Web Project 时勾选 “Generate web.xml deployment descriptor ”，也可在项目的 WEB-INF 下创建web.xml。

如果报错，则需要对 src/main/webapp 文件进行运行时的路径部署。作用是在运行时将相应的 resource 复制到 web 服务器的相应目录下（通过Deploy Path指定），保证web应用正常运行。

##### Build path specifies execution environment J2SE-1.5. There are no JREs installed in the workspace that are strictly compatible with this environment. 

update Maven 后修改 jdk 版本操作无效，可在 pom.xml 中添加下列内容。

```xml
<build>
    <finalName>xxx</finalName> 
    <plugins>  
      <plugin>    
      <groupId>org.apache.maven.plugins</groupId>    
      <artifactId>maven-compiler-plugin</artifactId>    
      <configuration>    
          <source>1.8</source>    
          <target>1.8</target>    
      </configuration>    
      </plugin>  
    </plugins>  
  </build>
```
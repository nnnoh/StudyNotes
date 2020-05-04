## mybatis generator

### pom.xml

```xml
<build>
        <plugins>
            <!--生成mapper相关信息插件-->
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
                <configuration>
                    <configurationFile>
                        src\main\resources\generatorConfig.xml
                    </configurationFile>
                    <overwrite>true</overwrite>
                    <verbose>true</verbose>
                </configuration>
                <dependencies>
                    <!--对应数据库驱动-->
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>${mysql-connector-java.version}</version>
                    </dependency>
                    <dependency>
                        <groupId>com.itfsw</groupId>
                        <artifactId>mybatis-generator-plugin</artifactId>
                        <version>1.3.2</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
```

### generatorConfig.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <!--defaultModelType:
            flat:该模型为每一张表只生成一个实体类。这个实体类包含表中的所有字段。这种模型最简单，推荐使用。另外onditional和hierarchical-->
    <context id="mysqlgenerator" targetRuntime="MyBatis3" defaultModelType="flat">
        <!--autoDelimitKeywords:当表名或者字段名为SQL关键字的时候，可以设置该属性为true，MBG会自动给表名或字段名添加分隔符。-->
        <property name="autoDelimitKeywords" value="true"/>
        <!--使用``包括字段名，避免字段名与sql保留字冲突报错-->
        <!--        <property name="beginningDelimiter" value="`"/>-->
        <!--        <property name="endingDelimiter" value="`"/>-->

        <!-- 自动生成toString方法 -->
        <plugin type="org.mybatis.generator.plugins.ToStringPlugin"/>
        <!-- 自动生成equals方法和hashcode方法 -->
        <plugin type="org.mybatis.generator.plugins.EqualsHashCodePlugin"/>

        <!-- 非官方插件 https://github.com/itfsw/mybatis-generator-plugin -->
        <!-- 查询单条数据插件 -->
        <plugin type="com.itfsw.mybatis.generator.plugins.SelectOneByExamplePlugin"/>
        <!-- 查询结果选择性返回插件 -->
        <plugin type="com.itfsw.mybatis.generator.plugins.SelectSelectivePlugin"/>
        <!-- Example Criteria 增强插件 -->
        <plugin type="com.itfsw.mybatis.generator.plugins.ExampleEnhancedPlugin"/>
        <!-- 数据Model属性对应Column获取插件 -->
        <plugin type="com.itfsw.mybatis.generator.plugins.ModelColumnPlugin"/>
        <!-- 逻辑删除插件 -->
        <plugin type="com.itfsw.mybatis.generator.plugins.LogicalDeletePlugin">
            <!-- 这里配置的是全局逻辑删除列和逻辑删除值，当然在table中配置的值会覆盖该全局配置 -->
            <!-- 逻辑删除列类型只能为数字、字符串或者布尔类型 -->
            <property name="logicalDeleteColumn" value="status"/>
            <!-- 逻辑删除-已删除值 -->
            <property name="logicalDeleteValue" value="1"/>
            <!-- 逻辑删除-未删除值 -->
            <property name="logicalUnDeleteValue" value="0"/>
        </plugin>

        <!--覆盖生成XML文件-->
        <plugin type="org.mybatis.generator.plugins.UnmergeableXmlMappersPlugin"/>

        <!--由于MBG生成的注释信息没有任何价值，而且有时间戳的情况下每次生成的注释都不一样，使用版本控制的时候每次都会提交，因而一般情况下屏蔽注释信息-->
        <commentGenerator>
            <!--是否阻止生成的注释包含时间戳,默认false-->
            <property name="suppressDate" value="true"/>
            <!--是否将数据库中表的字段描述信息添加到注释-->
            <!--<property name="addRemarkComments" value="true"/>-->
            <!--是否阻止生成注释，默认为false-->
            <property name="suppressAllComments" value="true"/>
        </commentGenerator>

        <!--数据库连接信息-->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://127.0.0.1:3306/guapp?characterEncoding=UTF-8&amp;serverTimezone=GMT%2B8"
                        userId="root"
                        password="123456">
        </jdbcConnection>

        <!--用来指定JDBC类型和Java类型如何转换-->
        <javaTypeResolver>
            <!--forceBigDecimals:该属性可以控制是否强制DECIMAL和NUMERIC类型的字段转换为Java类型的
            java.math.BigDecimal,默认值为false，一般不需要配置。-->
            <property name="forceBigDecimals" value="false"/>
            <!--当useJSR310Types为true时，就会jdbc对应的日期类型会转成java8中的LocateDateTime类型，
            如果useJSR310Types为false，则还是转成java.util.Date类型-->
            <property name="useJSR310Types" value="false"/>
        </javaTypeResolver>

        <!--javaModelGenerator:该元素用来控制生成的实体类-->
        <!--    targetPackage:生成实体类存放的包名，一般就是放在该包下-->
        <!--    targetProject:指定目标项目路径，使用的是文件系统的绝对路径。-->
        <javaModelGenerator targetPackage="com.mine.guauth.system.entity" targetProject="src/main/java">
            <!-- enableSubPackages：如果true，MBG会根据catalog和schema来生成子包。如果false就会直接用targetPackage属性。默认为false。 -->
            <property name="enableSubPackages" value="false"/>
            <!--constructorBased:该属性只对MyBatis3有效，如果true就会使用构造方法入参，如果false就会使用setter方式。默认为false。-->
            <property name="constructorBased" value="false"/>
            <!--trimStrings:是否对数据库查询结果进行trim操作，如果设置为true就会生成类似这样-->
            <!--    public void setUsername(String username) {this.username = username == null ? null : username.trim();}的setter方法。-->
            <!--默认值为false。-->
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>

        <!--sqlMapGenerator-->
        <!--如果targetRuntime目标是**iBATIS2**，该元素必须配置一个。-->
        <!--如果targetRuntime目标是**MyBatis3**，只有当<javaClientGenerator>需要XML时，该元素必须配置一个。
        如果没有配置<javaClientGenerator>，则使用以下的规则：
            如果指定了一个<sqlMapGenerator>，那么MBG将只生成XML的SQL映射文件和实体类。
            如果没有指定<sqlMapGenerator>，那么MBG将只生成实体类。-->
        <!--mapper.xml尽量放到resource目录下,默认maven会无法打包-->
        <sqlMapGenerator targetPackage="com.mine.guauth.system.entity" targetProject="src/main/resources">
            <!--enableSubPackages:如果true，MBG会根据catalog和schema来生成子包。
            如果false就会直接用targetPackage属性。默认为false。-->
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>

        <!-- javaClientGenerator
             type:首先根据<context>的targetRuntime分成三类：
                 MyBatis3:
                        ANNOTATEDMAPPER:基于注解的Mapper接口，不会有对应的XML映射文件
                        MIXEDMAPPER:XML和注解的混合形式，(上面这种情况中的)SqlProvider注解方法会被XML替代。
                        XMLMAPPER:所有的方法都在XML中，接口调用依赖XML文件。(推荐：代码和sql分离，便于维护).-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.mine.guauth.system.dao"
                             targetProject="src/main/java"/>

        <!--表名-->
        <table tableName="gu_%">
            <!--javaType:此列的属性的标准Java类型。 如果需要，可以使用它覆盖JavaTypeResolver计算的类型。（例如MySql的unsigned bigint类型需要映射为java.lang.Object）-->
            <!--jdbcType:列的JDBC类型（INTEGER，DECIMAL，NUMERIC，VARCHAR等）。 如果需要，可以使用它覆盖JavaTypeResolver计算的类型。（例如DB2的LONGVARCHAR类型需要为iBATIS 映射为VARCHAR）-->
            <!--delimitedColumnName:指定是否应在生成的SQL的列名称上增加分隔符。如果列的名称中包含空格，MGB会自动添加分隔符，所以这个重写只有当列名需要强制为一个合适的名字或者列名是数据库中的保留字时是必要的。-->
            <columnOverride column="CREATE_TIME" javaType="java.sql.Timestamp" jdbcType="TIMESTAMP" typeHandler=""
                            delimitedColumnName=""/>
            <columnOverride column="UPDATE_TIME" javaType="java.sql.Timestamp" jdbcType="TIMESTAMP" typeHandler=""
                            delimitedColumnName=""/>
        </table>

    </context>
</generatorConfiguration>
```


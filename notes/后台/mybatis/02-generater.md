# Mybatis Generater

- [MyBatis Generator Core – Introduction to MyBatis Generator](http://mybatis.org/generator/index.html)

pom：

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
                <configuration>
                    <configurationFile>
                        mybatis-generator/generatorConfig.xml
                    </configurationFile>
                    <overwrite>true</overwrite>
                    <verbose>true</verbose>
                </configuration>
                <dependencies>
                    <!- 数据库依赖及config中使用的包的依赖 ->
                </dependencies>
            </plugin>
		</plugins>
    </build>
```

## mybatis-generator-plugin

mybatis-generator-plugin 通过该插件机制来强化Mybatis Generator本身，方便和减少我们平时的代码开发量。

### 扩展

- [GitHub - itfsw/mybatis-generator-plugin at V1.3.6.4](https://github.com/itfsw/mybatis-generator-plugin/tree/V1.3.6.4)
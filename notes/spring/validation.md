## Hibernate Validator

hibernate validator（[官方文档](http://hibernate.org/validator/documentation/)）提供了一套比较完善、便捷的验证实现方式。

`spring-boot-starter-web`包里面有`hibernate-validator`包，不需要引用hibernate validator依赖。

### 校验模式

1. 普通模式（默认）

   校验所有的的属性，然后返回所有的验证失败信息

2. 快速失败返回模式

   只要有一个验证失败，则返回

failFast：true  快速失败返回模式    false 普通模式 

配置方式

```java
ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)   
	.configure()
	.failFast(true)
    // or .addProperty("hibernate.validator.fail_fast", "true")
	.buildValidatorFactory();
Validator validator = validatorFactory.getValidator();
```

### 校验标签

Validation 中内置的 constraint。

Hibernate Validator 附加的 constraint（不再建议使用）。

注意：

- 这个字段的访问级别( private, protected 或者 public) 对此没有影响。

- 静态字段或者属性是不会被校验的。
- 如果要定义约束在属性级别上的话，那么只能定义在访问器(getter)上面,不能定义在修改器(setter)上。（ 不建议对字段和随附的getter方法进行注释，因为这将导致对该字段进行两次验证。）

### 参数校验

#### javaBean校验

在参数前加注解 @Valid，然后在其后加BindindResult即可；多个参数的，可以加多个@Valid和BindingResult。

```java
    @RequestMapping("/val")
    public void val(@RequestBody @Valid DemoModel demo, BindingResult result){
        if(result.hasErrors()){
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
        }
    }
```

若方法入参不写`BindingResult result`这个参数，请求得到的直接是400错误，因为若有校验失败的服务端会抛出异常`org.springframework.web.bind.MethodArgumentNotValidException`（RequestBody）。若写了，那就调用者自己处理。

而对于RequestParam，Spring将`application / x-www-form-urlencoded`数据解释为Web表单数据， Spring使用FormHttpMessageConverter将POST主体转换为域对象，并导致`BindException`。

#### 非javaBean校验

@Valid 要求待校验的入参是JavaBean，如请求body体。对于入参不是JavaBean，@Valid就无法校验。

MethodValidationPostProcessor是Spring提供的来实现基于方法的JSR校验的核心处理器，它能让约束作用在方法入参、返回值上。如：

```java
public @NotNull Object myValidMethod(@NotNull String arg1, @Max(10) int arg2){}
```

方法里写的JSR校验注解要想其生效的话，要求类型级别上必须使用@Validated标注。

1. 配置 MethodValidationPostProcessor 的Bean

   ```java
   	@Bean
       public MethodValidationPostProcessor methodValidationPostProcessor() {
           /**默认是普通模式，会返回所有的验证不通过信息集合*/
           return new MethodValidationPostProcessor();
       }
   ```

   对MethodValidationPostProcessor 进行设置Validator（因为此时不是用的Validator进行验证，Validator的配置不起作用）

   ```java
   	@Bean
       public MethodValidationPostProcessor methodValidationPostProcessor() {
           MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();　　　　　/**设置validator模式为快速失败返回*/
           postProcessor.setValidator(validator());
           return postProcessor;
       }
   
       @Bean
       public Validator validator(){
           ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                   .configure()
                   .addProperty( "hibernate.validator.fail_fast", "true" )
                   .buildValidatorFactory();
           Validator validator = validatorFactory.getValidator();
   
           return validator;
       }
   ```

2. 在Controller上加@Validated注解

3. 验证不通过时，抛出了ConstraintViolationException异常

### 对象校验

```java
		Set<ConstraintViolation<Obj>> violationSet = validator.validate(Obj);
        for (ConstraintViolation<Obj> model : violationSet) {
            System.out.println(model.getMessage());
        }
```

#### 对象级联校验

对象内部包含另一个对象作为属性，属性上加@Valid，可以验证作为属性的对象内部的验证。

如果对象为null值则不校验，可加@NotNull验证不能为空。

### 分组校验

1. 新建验证组：

   ```java
   public interface GroupA{}
   ```
   
2. 校验标签设置所属组（Default是Validator自带的默认分组）

   ```java
   	@NotBlank
       @Range(min = 1,max = Integer.MAX_VALUE,message = "必须大于0",groups = {GroupA.class})
       private Integer userId;
   ```

3. 设置校验组

   ```java
   Set<ConstraintViolation<Person>> validate = validator.validate(p, GroupA.class, GroupB.class);
   ```

   或

   ```java
   @RequestMapping("/valg")
       public void valg(@Validated({GroupA.class, GroupB.class}) Person p, BindingResult result){
       }
   ```

#### 组序列

可以指定组的验证顺序，前面组验证不通过的，后面的分组就不进行验证。

```java
@GroupSequence({GroupA.class, GroupB.class, Default.class})
public interface GroupOrder {
}
```

使用时指定GroupOrder为校验组。

```java
Set<ConstraintViolation<Person>> validate = validator.validate(p, GroupOrder.class);
```

### 自定义校验

示例：

```java
public enum CaseMode {
    UPPER,
    LOWER;
}


@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckCaseValidator.class)
@Documented
public @interface CheckCase {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    CaseMode value();
}


public class CheckCaseValidator implements ConstraintValidator<CheckCase, String> {
    private CaseMode caseMode;
    @Override
    public void initialize(CheckCase checkCase) {
        this.caseMode = checkCase.value();
    }
	@Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }

        if (caseMode == CaseMode.UPPER) {
            return s.equals(s.toUpperCase());
        } else {
            return s.equals(s.toLowerCase());
        }
    }
}
```

@Valid 和 @Validated 区别

Spring Validation验证框架对参数的验证机制提供了@Validated（Spring's JSR-303规范，是标准JSR-303的一个变种），javax提供了@Valid（标准JSR-303规范）。

在检验Controller的入参是否符合规范时，使用@Validated或者@Valid在基本验证功能上没有太多区别。但是在分组、注解地方、嵌套验证等功能上两个有所不同：

1. 分组

   @Validated：提供了一个分组功能。@Valid：作为标准JSR-303规范，还没有吸收分组的功能。

2. 注解位置

   @Validated：可以用在类型、方法和方法参数上。但是不能用在成员属性（字段）上

   @Valid：可以用在方法、构造函数、方法参数和成员属性（字段）上

   两者是否能用于成员属性（字段）上直接影响能否提供嵌套验证的功能。

3. 嵌套验证

   @Validated：用在方法入参上无法单独提供嵌套验证功能。不能用在成员属性（字段）上，也无法提示框架进行嵌套验证。能配合嵌套验证注解@Valid进行嵌套验证。

   @Valid：用在方法入参上无法单独提供嵌套验证功能。能够用在成员属性（字段）上，提示验证框架进行嵌套验证。能配合嵌套验证注解@Valid进行嵌套验证。
Dummy: 帮助测试项目编译通过，被用来作为填充参数列表的对象，实际上不会用到它们，对测试结果也没有任何影响。

```java
@Test
public void should_set_default_value_to_0_for_int_option(){
        // dummy
        Function<String, Object> whatever = (it) -> null;
        Object defaultValue = new Object();

        assertSame(defaultValue, OptionParsers.unary(defaultValue,whatever).parse(asList(), option("p")));
}
```

Fake: 提供了一套简易的实现，利用简易实现来测试功能

```java
public class StudentRepository {
    Map<Long, Student> students = new HashMap<>();

    public Student getById(Long id) {
        return students.get(id);
    }
}
```

Stub: 代指那些包含了预定义好的数据并且在测试时返回给调用者的对象。

当某个对象需要从数据库抓取数据时，我们并不需要真实地与数据库进行交互或者像 Fake 那样从内存中抓取数据，而是直接返回预定义好的数据。

如发送邮件的功能，Stub可以总是返回邮件发送成功的标识。

```java
// the stub
makeStub(send email,post,()=>{
        return true;
})
```

Spy:一个间谍对象,它会记录方法被调用的次数和参数值。可以使用它验证测试中方法的调用情况。

```java
public class SpyUserService implements StudentService {
    private int getUserCallCount;

    public User getUser(int id) {
        getUserCallCount++;
        return new User(id);
    }

    public int getGetUserCallCount() {
        return getUserCallCount;
    }
}
```

Mock: 一个模拟对象,我们可以设置其方法的预期输入和输出,用来验证测试中方法的调用情况。

代指那些仅记录它们的调用信息的对象，在测试断言中我们需要验证 Mocks 被进行了符合期望的调用。

邮件发送服务的测试，我们并不希望每次进行测试的时候都发送一封邮件，毕竟我们很难去验证邮件是否真的被发出了或者被接收了。我们更多地关注于邮件服务是否按照我们的预期在合适的业务流中被调用

```java
@Test
public void test(){
        xxxService.doAction(params);

        verify(mail).sent();
}
```

它们的使用场景不同,Stub和Mock更适用于测试,Spy可以在测试或实现中使用,Fake和Dummy更适用于实现。

# chapter07

> 集成测试和单元测试

```java
//ArgsTest

@Test
public void should_set_boolean_option_to_true_if_flag_present() {
  BoolOption options = Args.parse(BooleanOption.class, "-l");
  assertTrue(options.logging());
}

static record BooleanOption(@Option("l") boolean logging);

集成
---------------------------------------------------------
单元

//OptionParsersTest.BooleanOptionParser

@Test
public void should_set_value_to_true_if_option_present() {
  assertTrue(OptionParsers.bool().parse(asList("-l"), option("l")));
}

```

> 单元测试

“单元测试”指的是能提供==快速反馈的低成本的研发测试（Developer Test）==。在不做任何强调的情况下，它会指针对==不涉及进程外组件的单一软件单元的测试==。

为了让测试能够聚焦到单一的单元，就需要拆分单元间的依赖，那么最终会得到一组彼此间没有直接耦合关系的小粒度对象。

> ！坏味道

将所有`直接耦合`都视为坏味道的设计取向，会将功能需求的上下文打散到一组==细碎的对象群落中，增加理解的难度。==最终滑向`过度设计`（Over Design）的深渊。

坏味道通常源自过==高认知负载（Cognitive Load==）或不易修改，俗称“看不懂改不动”。比如，坏味道过长的方法（Long Method）不是以==绝对代码长度==来衡量的，而是以“==是否超出认知负载或难以改变其中的行为==”来衡量的。而将功能上下文切得过于细碎，也会增加认知负载。因而==不能单纯地认为，这种风格就一定是好的设计==。

> TDD时难道一定要保证时单元测试？

在测试驱动开发中，从来没有强调必须是“单元测试”。反而在==大多数情况==下，都是针对==不同单元粒度==的功能测试。并通过这==一系列不同单元粒度的功能测试，驱动软件的开发==

> 「单元测试」是一个极具误导的叫法

`不同单元粒度的功能测试`才是TDD中单元测试的含义----“单元级别功能测试（Unit Level Functional Test）”来指代 TDD 中需要的那种测试。

- TDD 中的测试是由不同粒度的功能测试构成的
- 每一个测试都兼具`功能验证`和`错误定位`的功效
- 要从`发现问题和定位问题`的角度，去思考测试的效用与成本
- ==单元粒度要以独立的功能上下文或变化点为粒度==



# chapter08



> 对于相同的功能，如果我们划分的功能上下文不同，会有什么结果呢？

1. 从给定的参数列表中寻找对应选项，并根据选项类型读取参数
2. 将参数列表按照选项，分割成由选项名称和参数组成的数组 
3. 将参数列表按照选项，分解成由选项名称和参数组成的映射

不同的实现会产生==不同的功能上下文==，针对不同的功能上下文，也需要编写对应的单元级别功能测试来验证其功能

功能上下文的划分，指引我们编写测试；在测试的驱动下，我们逐步完成功能上下文的实现

> 测试驱动开发的核心要点

单元级别功能测试能够驱动其对应单元（功能上下文或变化点）的==外在功能需求==。而对于对应==单元之内功能的实现==，测试就没有办法了。

单元级别功能测试无法驱动小于其测试单元的功能需求，也无法驱动单元内的实现方式，需要进一步拆分功能上下文才可以

> 如何让TDD丧失驱动力

TDD无法指明某个单元内的实现细节

>测试驱动开发的主要关注点

测试驱动开发的主要关注点在于功能在单元（模块）间的分配，而对于模块内怎么实现，需要==你有自己的想法==。



思考题

> 请结合课程内容，反驳 David Heinemeier Hansson（DHH）的“TDD 已死”。

本质上就是「单元测试」这个极具误导性的叫法导致

>请从架构的角度出发，思考红 / 绿 / 重构循环，分别发挥了什么作用？

红：根据分解的上下文，产生的测试case和大体框架

绿：简单实现，通过测试，只关心当前测试case的上下文，不关心实现的好不好

重构：消除掉以上几个流程中产生的坏味道





快捷键

⌥⌘c: 抽取常量

⌥⌘f:  抽取field

⌥⌘v：抽取变量

⌥⌘p:  抽取参数

⌘F6:  方法签名变更

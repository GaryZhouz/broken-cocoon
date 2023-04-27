# Part1 任务分解法与整体工作流程

> 🚀TDD的基本原则
 1. 当且仅当存在失败的自动化测试时，才开始编写生产代码；
 2. 消除重复。「消除坏味道」

> 🤔️啥是后世广为流传的测试驱动开发咒语?

红 / 绿 / 重构（Red/Green/Refactoring）

红：编写一个失败的小测试，甚至可以是无法编译的测试； 

绿：让这个测试快速通过，甚至不惜犯下任何罪恶；

重构：消除上一步中产生的所有重复（坏味道）。

> 👀那这个「红」从哪里来产生呢？

使用任务分解法拆分：

- 大致把握对外接口的方向；

- 大致构思功能的实现方式，划分所需的组件（Component）以及组件间的关系（所谓的架构）。如果没思路，也可以不划分； 

- 根据需求的功能拆分功能点，功能点要考虑正确路径（`Happy Path`）和边界条件（`Sad Path`）； 

- 依照组件以及组件间的关系，将功能拆分到对应组件；
- 针对拆分的结果编写测试，进入红 / 绿 / 重构循环。

> 🏃实践TDD最重要的前提

理解需求，并通过测试构成高效的节奏，是有效实施 TDD 的前提

# Part2: 识别坏味道与代码重构

> 1⃣️重构的先决条件是啥呢？

- 测试都是绿的，也就是当前功能正常。
- 第二是坏味道足够明显。

> 代码的坏味道有哪些呢？

1. 过长函数
2. `重复代码`
3. `长参数列表`
4. 全局变量
5. 魔法数
6. 深度嵌套
7. `过多的条件逻辑`
8. 未处理异常
9. 未遵循代码约定
10. 过长的类

> TDD 的红 / 绿 / 重构循环

其分离了关注点。在红 / 绿阶段，我们不关心代码结构，只关注功能的累积

在重构的过程中，因为测试的存在，我们可以时刻检查功能是否依旧正确，同时将关注点转移到“怎么让代码变得更好”上去。

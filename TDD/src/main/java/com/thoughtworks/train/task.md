Part1-任务拆解:

> 解析输入并生成图模型

- Happy Path
  - 输入包含城市名称和城市间距离「两座」
  - 输入包含城市名称和城市间距离「多座」

- Sad Path
  - 单个字符串输入大于等于3

    - 输入字符串第二位后的内容非数字
    - 单个字符串输入长度小于3「缺少城市名称 ｜ 城市间距离」


> 计算指定路线的距离

- 输入指定路线「两个节点」
  - Happy Path: 不可达，输出NO SUCH ROUTE
  - Sad Path: 可达，输出距离
- 输入指定路线「多节点」
  - Happy Path: 不可达，输出NO SUCH ROUTE
  - Sad path: 可达，输出距离

> 计算StartCity-EndCity的所有路线

- 起始站和终点站不一样「不考虑环」
  - Happy Path：有可达路线，输出所有路线
  - Sad Path：无可达路线
- 起始站和终点站一样「考虑环，需要追加限制防止StackOverflow」
  - Happy Path：
    - 有可达路线
      - 距离限制
      - 停留站数限制
  - Sad Path：无可达路线

> 计算StartCity-EndCity的最短距离

- 起点-终点是否可达
  - Happy Path:  路线可达，返回最短距离
  - Sad Path: 路线不可达，返回NO SUCH ROUTE




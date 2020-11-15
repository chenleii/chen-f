Feature: 用户级别规则
  等级0为超级用户,数值越小级别越大。

  @transaction
  Scenario Outline: 等级高的可以修改等级低的用户信息
    Given 创建测试系统用户:
      | id | name  | level |
      | 1  | chen1 | 0     |
      | 2  | chen2 | 1     |
      | 3  | chen3 | 2     |
      | 4  | chen4 | 3     |
      | 5  | chen5 | 4     |
      | 6  | chen6 | 5     |
    When 系统用户 "<operatedSysUserId>" 修改系统用户 "<updatedId>" 的等级为 <updatedLevel>
    Then 修改结果是 "<result>"
    Examples:
      | operatedSysUserId | updatedId | updatedLevel | result     |
      | 1                 | 2         | 0            | successful |
      | 3                 | 4         | 1            | failure    |
      | 5                 | 6         | 4            | successful |
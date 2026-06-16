# 图书馆管理系统（前后端实现）
## 开发技术与工具
MySQL + Navicat + IDEA + VSCode

## 数据库表总览
项目共13张业务数据表，完整支撑图书馆借阅、学生、管理员、罚款、公告、日志等全业务流程，使用Navicat可视化管理MySQL数据库。

### 1. cardrec 借阅卡操作记录表
| 字段名 | 说明 |
| ---- | ---- |
| id | 主键ID |
| serNum | 业务流水号 |
| sno | 学生学号 |
| originCardNo | 原借阅卡号 |
| newCardNo | 新借阅卡号 |
| opType | 操作类型 |

### 2. ruleconfig 系统规则配置表
| 字段名 | 说明 |
| ---- | ---- |
| id | 主键ID |
| ruleName | 规则名称 |
| ruleValue | 规则数值 |
| intro | 规则描述 |

### 3. book 图书信息表
| 字段名 | 说明 |
| ---- | ---- |
| id | 主键ID |
| ISBN | 图书ISBN编号 |
| bname | 图书名称 |
| author | 作者 |
| publisher | 出版社 |
| introduction | 图书简介 |
| pubDate | 出版日期 |
| clcNum | 图书分类号 |
| bookStatus | 图书总状态 |

### 4. bookcopy 图书副本馆藏表
| 字段名 | 说明 |
| ---- | ---- |
| barCode | 图书条码（主键） |
| ISBN | 关联图书ISBN |
| place | 馆藏存放位置 |
| status | 当前副本状态 |
| oldStatus | 副本历史状态 |
| location | 馆藏地 |
| nowLocation | 当前所在地 |

### 5. booktype 图书类型表
| 字段名 | 说明 |
| ---- | ---- |
| id | 主键ID |
| typeName | 图书分类名称 |
| intro | 分类介绍 |

### 6. borrowrec 借阅记录表
| 字段名 | 说明 |
| ---- | ---- |
| id | 主键ID |
| serNum | 借阅流水号 |
| sno | 学生学号 |
| barCode | 图书副本条码 |
| borDate | 借出日期 |
| retDate | 应归还日期 |
| realRetDate | 实际归还日期 |
| retStatus | 归还状态 |
| oddDays | 逾期天数 |
| fineMoney | 罚款金额 |
| fineStatus | 罚款结清状态 |
| paySerNum | 关联缴费流水号 |

### 7. notice 公告通知表
| 字段名 | 说明 |
| ---- | ---- |
| id | 主键ID |
| topic | 公告标题 |
| content | 公告内容 |
| author | 发布人 |
| createDate | 发布时间 |

### 8. student 学生信息表
| 字段名 | 说明 |
| ---- | ---- |
| id | 主键ID |
| sno | 学生学号 |
| username | 学生姓名 |
| type | 学生类型 |
| collage | 学院 |
| major | 专业 |
| birth | 出生日期 |
| originPlace | 籍贯 |
| password | 登录密码 |

### 9. admin 管理员信息表
| 字段名 | 说明 |
| ---- | ---- |
| id | 主键ID |
| username | 管理员账号 |
| password | 登录密码 |
| adminType | 管理员权限类型 |

### 10. operationlog 操作日志表
| 字段名 | 说明 |
| ---- | ---- |
| id | 主键ID |
| adminId | 操作管理员ID |
| operateTime | 操作时间 |
| operateType | 操作类型 |
| content | 操作详情 |
| ip | 操作IP地址 |

### 11. payrec 缴费记录表
| 字段名 | 说明 |
| ---- | ---- |
| id | 主键ID |
| serNum | 缴费流水号 |
| sno | 学生学号 |
| payAmount | 缴费金额 |
| payDate | 缴费时间 |

### 12. role 角色信息表
| 字段名 | 说明 |
| ---- | ---- |
| id | 主键ID |
| role_name | 角色名称 |
| remark | 角色备注 |

### 13. libcard 借阅卡信息表
| 字段名 | 说明 |
| ---- | ---- |
| id | 主键ID |
| cardNo | 借阅卡号 |
| sno | 关联学生学号 |
| sname | 学生姓名 |
| type | 借阅卡类型 |
| collage | 学院 |
| major | 专业 |
| birth | 出生日期 |
| originPlace | 籍贯 |
| cardStatus | 借阅卡状态 |
| times | 剩余可借阅次数 |

## 工具分工说明
- MySQL：数据库底层存储，维护数据表、业务关联关系
- Navicat：数据库可视化工具，用于建表、SQL调试、数据管理
- IDEA：后端项目开发（Java/SpringBoot）
- VSCode：前端页面开发（Vue/HTML/CSS/JS）

# 图书馆管理系统（前后端实现）
## 开发技术与工具
MySQL + Navicat + IDEA + VSCode

# 图书管理系统后端项目结构
# 后端项目完整结构说明（更新版）
## 一、项目启动类
`LibraryApplication`：SpringBoot项目启动入口，项目主程序类

## 二、核心包分层（根包：com.library）
### 1. config 配置层
存放项目全局配置类
- `CorsConfig`：全局跨域请求配置，解决前后端跨域访问问题

### 2. controller 控制层
所有接口请求入口，接收前端HTTP请求，统一通过`util.Result`封装返回数据
1. 账号权限登录相关
    - AdminController：管理员管理接口
    - LoginController：登录基础接口
    - AuthSimpleController：简易权限认证接口
    - RoleController：角色权限管理接口
2. 学生业务管理
    - StudentController：学生信息CRUD接口
    - SimpleProfileController：学生个人信息中心接口
3. 图书分类&馆藏管理
    - BookController：图书书目基础信息接口
    - BookcopyController：图书副本（馆藏实体）接口
    - BooktypeController：图书分类类型接口
    - CatalogueController：图书目录相关接口
4. 借阅业务&记录
    - BorrowrecController：图书借阅记录管理接口
5. 借书证全生命周期业务
    - LibcardController：借书证基础信息接口
    - CardOperateController：借书证业务操作总入口（办卡/挂失/补卡/销卡）
    - CardrecController：借书证操作流水记录接口
6. 公告模块
    - NoticeController：系统公告发布、查询接口
7. 系统操作日志
    - OperationlogController：用户操作日志记录、查询接口
8. 缴费&借阅规则配置
    - PayrecController：逾期罚款缴费记录接口
    - RuleconfigController：图书馆借阅规则配置接口

### 3. dto 入参实体包
专门接收前端提交的新增、编辑、业务操作请求参数，用于接口入参绑定
- BookDTO：图书书目新增/编辑入参
- BookCopyDTO：图书副本新增/编辑入参
- CardApplyDTO：办理借书证入参
- CardCancelDTO：注销借书证入参
- CardLossDTO：借书证挂失入参
- CardReissueDTO：借书证补办入参
- PasswordDTO：密码修改入参
- StudentUpdateDTO：学生信息修改入参

### 4. entity 数据库实体包
与MySQL数据库表一一映射的持久化实体类，对应每张数据表
Admin、Student、Book、Bookcopy、Booktype、Borrowrec、Libcard、Cardrec、Notice、Operationlog、Payrec、Role、Ruleconfig

### 5. mapper 持久层（MyBatis）
数据库操作接口层，每个实体对应1个Mapper接口，配套xml映射文件实现SQL
- 接口文件（Mapper）：
AdminMapper、StudentMapper、BookMapper、BookcopyMapper、BooktypeMapper、BorrowrecMapper、LibcardMapper、CardrecMapper、NoticeMapper、OperationlogMapper、PayrecMapper、RoleMapper、RuleconfigMapper
- SQL映射文件：
BookMapper.xml（图书自定义复杂查询SQL）

### 6. service 业务逻辑层
分为**顶层业务接口**与**impl接口实现类**，封装系统核心业务逻辑
#### （1）顶层业务接口（service目录下）
AdminService、StudentService、BookService、BookcopyService、BooktypeService、BorrowrecService、LibcardService、CardOperateService、CardrecService、NoticeService、OperationlogService、PayrecService、RoleService、RuleconfigService、SimpleProfileService

#### （2）impl 接口实现子包（service.impl）
实现对应Service接口，编写数据库交互、业务校验、事务逻辑
- StudentServiceImpl：学生相关业务实现
- LibcardServiceImpl：借书证基础信息业务实现
- CardOperateServiceImpl：借书证操作（办卡/挂失/补卡/销卡）核心业务实现
- BorrowrecServiceImpl：图书借阅、归还业务实现
- PayrecServiceImpl：罚款缴费业务实现
- SimpleProfileServiceImpl：学生个人中心业务实现

### 7. util 工具类包
存放全局通用工具、拦截器、统一返回封装
- Result：全局统一返回结果封装类，标准化接口返回格式（状态码、提示信息、业务数据）
- LoginInterceptor：登录拦截器，全局校验请求登录状态，未登录接口拦截

### 8. vo 出参视图实体包
封装后端查询完成后返回给前端的页面展示数据，用于多表关联、组合数据返回
- BookQueryVO：图书列表分页查询返回视图
- BookBorrowVo：图书借阅详情组合出参，整合图书基础信息+借阅关联数据，用于借阅详情页
- BorrowCheckVo：借书资格校验出参，封装借书限制、校验结果、前端提示文案
- BorrowVo：基础借阅记录列表返回对象
- FineVo：逾期罚款缴费信息返回对象
- StudentProfileVO：学生个人中心页面展示数据封装

## 三、resources 资源目录
1. static：静态资源存放目录（图片、静态文件等）
2. templates：页面模板目录
3. application.yml：SpringBoot全局配置文件（数据库、端口、MyBatis、跨域等配置）
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
| opTime | 操作时间 |

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

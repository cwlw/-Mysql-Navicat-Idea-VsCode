# 图书管理系统后端项目结构说明书
## 一、项目整体说明
本项目基于 SpringBoot + MyBatis 开发图书管理后端系统，采用标准MVC分层架构，通过DTO入参、VO出参实现前后端数据解耦，封装统一返回结果与全局登录拦截，模块化程度高，结构清晰易维护。

## 二、项目启动类
### `LibraryApplication`
SpringBoot项目唯一启动入口主类，负责项目容器初始化、自动装配、服务启动加载，是整个后端服务运行入口。

## 三、核心包分层结构（根包：com.library）
### 1. config 配置层
存放项目全局框架配置类，仅做基础功能配置，无业务逻辑
- `CorsConfig`：全局跨域请求配置类，配置允许访问域名、请求头、请求方式，解决前后端分离跨域问题

### 2. controller 控制层
所有前端HTTP接口请求入口，仅接收参数、调用业务层、统一返回`util.Result`格式数据，不编写核心业务逻辑。按业务模块划分：
#### 2.1 账号权限登录相关
- AdminController：管理员账号CRUD、管理员信息管理接口
- LoginController：系统登录、登出基础接口
- AuthSimpleController：简易权限认证、登录状态校验接口
- RoleController：角色管理、角色权限分配接口

#### 2.2 学生业务管理
- StudentController：学生基础信息增删改查、分页查询接口
- SimpleProfileController：学生个人中心、资料修改、密码修改接口

#### 2.3 图书分类&馆藏管理
- BookController：图书书目基础信息管理接口
- BookcopyController：图书馆藏副本入库、状态管理接口
- BooktypeController：图书分类类型管理接口
- CatalogueController：图书目录关联查询接口

#### 2.4 借阅业务&记录
- BorrowrecController：图书借阅、归还、借阅记录查询接口

#### 2.5 借书证全生命周期业务
- LibcardController：借书证基础信息列表查询接口
- CardOperateController：借书证业务统一操作入口（办卡/挂失/补卡/销卡）
- CardrecController：借书证操作流水记录查询接口

#### 2.6 公告模块
- NoticeController：系统公告发布、编辑、分页查询、详情接口

#### 2.7 系统操作日志
- OperationlogController：用户操作日志查询、行为追溯接口

#### 2.8 缴费&借阅规则配置
- PayrecController：逾期罚款生成、缴费操作、缴费记录查询接口
- RuleconfigController：借阅规则参数配置、规则查询接口

### 3. dto 入参实体包
数据传输入参对象，接收前端提交的业务操作参数，结构化封装请求参数，与数据库实体解耦
- BookDTO：图书书目新增/编辑接口入参
- BookCopyDTO：图书馆藏副本新增/编辑接口入参
- CardApplyDTO：办理借书证业务入参
- CardCancelDTO：注销借书证业务入参
- CardLossDTO：借书证挂失操作入参
- CardReissueDTO：补办借书证业务入参
- PasswordDTO：管理员/学生修改密码入参
- StudentUpdateDTO：学生个人信息更新入参

### 4. entity 数据库实体包
与MySQL数据表一一映射的持久化实体类，字段和数据库表字段完全对应，用于MyBatis数据库映射操作
实体列表：
Admin、Student、Book、Bookcopy、Booktype、Borrowrec、Libcard、Cardrec、Notice、Operationlog、Payrec、Role、Ruleconfig

### 5. mapper 持久层（MyBatis）
数据库交互层，每张数据表对应一个Mapper接口，搭配XML文件编写自定义复杂SQL，直接操作数据库
#### 5.1 Mapper接口文件
AdminMapper、StudentMapper、BookMapper、BookcopyMapper、BooktypeMapper、BorrowrecMapper、LibcardMapper、CardrecMapper、NoticeMapper、OperationlogMapper、PayrecMapper、RoleMapper、RuleconfigMapper

#### 5.2 SQL映射XML文件
- BookMapper.xml：图书模块多条件分页、模糊查询、多表联查自定义SQL
- AdminMapper.xml：管理员角色关联查询、分页筛选、账号权限查询复杂SQL（新增）

### 6. service 业务逻辑层
项目核心业务处理层，分为顶层业务接口与impl实现类，封装数据校验、事务控制、多表关联业务逻辑
#### 6.1 顶层业务接口（service根目录）
定义各模块标准业务方法，提供统一调用规范
AdminService、StudentService、BookService、BookcopyService、BooktypeService、BorrowrecService、LibcardService、CardOperateService、CardrecService、NoticeService、OperationlogService、PayrecService、RoleService、RuleconfigService、SimpleProfileService

#### 6.2 impl 接口实现子包（service.impl）
实现对应Service接口，编写完整业务逻辑
- StudentServiceImpl：学生信息增删改查、数据校验业务实现
- LibcardServiceImpl：借书证基础信息查询、状态校验业务实现
- CardOperateServiceImpl：办卡、挂失、补卡、销卡全流程核心业务实现
- BorrowrecServiceImpl：图书借阅、归还、逾期判定、借阅记录生成业务实现
- PayrecServiceImpl：逾期罚款计算、缴费状态更新、缴费记录保存业务实现
- SimpleProfileServiceImpl：学生个人中心资料查询、密码修改业务实现

### 7. util 工具类包
全局公共工具、拦截器组件，全项目通用，无业务耦合
- Result：全局统一返回结果封装类，标准化接口返回格式（响应码、提示信息、业务数据、分页参数）
- LoginInterceptor：全局登录拦截器，拦截未登录请求，校验用户登录状态

### 8. vo 出参视图实体包
页面视图返回对象，封装多表组合数据给前端，适配页面展示需求，不与数据库表强绑定
- BookQueryVO：图书列表分页查询返回视图，封装图书基础信息、分类名称、馆藏数量
- BookBorrowVo：图书借阅详情组合出参，整合图书信息+借阅关联数据，用于借阅详情页
- BorrowCheckVo：借书资格校验出参，封装借书限制、校验结果、前端提示文案
- BorrowVo：基础借阅记录列表展示对象
- FineVo：逾期罚款缴费信息返回对象，包含罚款金额、逾期天数、缴费状态
- StudentProfileVO：学生个人中心页面数据封装
- AdminProfileVO：管理员个人中心专属出参，封装管理员账号、绑定角色、操作统计、权限信息（新增）

## 四、resources 资源配置目录
存放项目配置文件、静态资源、页面模板
1. static：静态资源存放目录（图片、静态文件等）
2. templates：服务端页面模板目录
3. application.yml：SpringBoot全局配置文件，包含数据库连接、服务端口、MyBatis映射、日志、跨域、项目全局参数配置

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

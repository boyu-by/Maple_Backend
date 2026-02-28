# Task 2 – 项目启动与 SQLite 配置

## 1. 任务目标

Task 2 的目标是验证后端 Spring Boot 项目是否能够：

- 正常启动并运行
- 正确加载 `application.yml` 配置文件
- 成功连接本地 SQLite 数据库
- 在 **未定义任何 Entity 和 Controller** 的情况下正常运行

本任务用于确保项目的基础环境和运行配置正确，为后续核心功能开发打下基础。

---

## 2. 项目结构说明

本任务中新增或确认的关键目录结构如下：
src/
└── main/
└── resources/
├── application.yml
└── db/
└── mindmap.db
docs/
└── Task2.md


说明：

- `application.yml` 位于 `src/main/resources` 目录下，由 Spring Boot 自动加载
- `mindmap.db` 为 SQLite 数据库文件，在首次访问数据库时自动创建
- `docs/` 目录用于存放项目阶段性文档

---

## 3. SQLite 数据库配置

### 3.1 Maven 依赖配置

在 `pom.xml` 中引入 SQLite JDBC 驱动依赖：

```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.45.1.0</version>
</dependency>
```

### 3.2 数据源配置（application.yml）

SQLite 数据源配置如下：

```YAML
spring:
  datasource:
    driver-class-name: org.sqlite.JDBC
    url: jdbc:sqlite:./db/mindmap.db

  jpa:
    hibernate:
      ddl-auto: none
    show-sql: true
```
配置说明：

- 数据库文件路径为 `resources/db/mindmap.db`

- SQLite 在数据库文件不存在时会自动创建

- `ddl-auto` 设置为 `none`，避免 Hibernate 在当前阶段自动建表

- 当前阶段仅验证连接，不涉及表结构生成

## 4. 项目启动验证
### 4.1 启动方式

项目可通过以下任一方式启动：

- IDE（如 IntelliJ IDEA）
直接运行 Spring Boot 主启动类

- 命令行方式：
```Bash
mvn spring-boot:run
```
### 4.2 启动验证结果

- 项目能够正常启动，无异常报错

- 内嵌 Tomcat 成功监听 `8080` 端口

- 在未定义 Entity 和 Controller 的情况下仍可正常运行

- SQLite 数据库连接初始化成功

典型启动日志如下：
```
Tomcat started on port(s): 8080
Started MindMapBackendApplication in X.XXX seconds
```
## 5. 设计说明与阶段性决策

- 本项目选择 SQLite 作为初期数据库，便于快速开发和本地调试

- 数据库表结构将在后续任务中统一设计并通过 Entity 映射实现

- 本阶段重点关注项目可运行性和配置正确性，不涉及业务逻辑实现

## 6. 任务完成情况

✅ Task 2 已完成

项目已具备继续进行以下任务的基础条件：

**Task 3 – 数据库结构设计与 JPA Entity 映射**

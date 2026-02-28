# Task3：数据库结构与 JPA Entity 映射说明

## 1. 任务目标

本任务旨在将 **Task1 中设计的数据库结构**，完整、准确地映射为 **Spring Boot + JPA Entity 模型**，确保：

* 数据表结构与实体类一一对应
* 表之间的关系在 Entity 层得到清晰表达
* 模型设计具备良好的可维护性与扩展性
* 为后续 Repository 与 API 层开发奠定稳定基础

---

## 2. 数据表与实体总览

| 数据表名     | 对应 Entity     | 说明           |
| -------- | ------------- | ------------ |
| mind_map | MindMapEntity | 思维导图元数据（聚合根） |
| node     | NodeEntity    | 思维导图中的节点     |
| edge     | EdgeEntity    | 节点之间的连接关系    |

整体结构遵循 **“一个导图 + 多节点 + 多连接”** 的建模方式。

---

## 3. MindMapEntity（思维导图实体）

### 3.1 表结构映射

**表名：** `mind_map`

| 字段名              | 类型       | 是否为空 | 说明                          |
| ---------------- | -------- | ---- | --------------------------- |
| id               | INTEGER  | NO   | 主键，自增                       |
| title            | TEXT     | NO   | 导图标题                        |
| description      | TEXT     | YES  | 导图描述                        |
| layout_direction | TEXT     | NO   | 布局方向（LEFT_RIGHT / TOP_DOWN） |
| created_at       | DATETIME | NO   | 创建时间                        |
| updated_at       | DATETIME | NO   | 更新时间                        |

---

### 3.2 Entity 设计说明

* `MindMapEntity` 是**聚合根（Aggregate Root）**
* 所有 `NodeEntity`、`EdgeEntity` 都必须归属于某一个 `MindMapEntity`
* 布局方向 `layoutDirection` 使用枚举类型，保证取值安全
* 使用 `@PrePersist` / `@PreUpdate` 自动维护时间字段
* 与 NodeEntity 建立一对多关系（`OneToMany`）

---

## 4. NodeEntity（节点实体）

### 4.1 表结构映射

**表名：** `node`

| 字段名            | 类型       | 是否为空 | 说明                |
| -------------- | -------- | ---- | ----------------- |
| id             | INTEGER  | NO   | 主键，自增             |
| mind_map_id    | INTEGER  | NO   | 所属导图 ID           |
| parent_node_id | INTEGER  | YES  | 父节点 ID（根节点为 NULL） |
| content        | TEXT     | NO   | 节点内容              |
| x              | REAL     | NO   | X 坐标              |
| y              | REAL     | NO   | Y 坐标              |
| collapsed      | BOOLEAN  | NO   | 是否折叠              |
| created_at     | DATETIME | NO   | 创建时间              |
| updated_at     | DATETIME | NO   | 更新时间              |

---

### 4.2 Entity 设计说明

* 每个节点**只能属于一张思维导图**
* 使用 `parent_node_id` 表达节点的树形父子结构
* 节点位置信息（x, y）由前端计算，后端仅负责存储
* 同时保留：

  * `mindMapId`（基础字段，便于查询）
  * `mindMap`（JPA 关系映射）
* 通过 `@JsonIgnore` 避免序列化时的循环引用问题
* 使用索引提升以下查询性能：

  * 按导图查询节点
  * 按父节点查询子节点

---

## 5. EdgeEntity（连接关系实体）

### 5.1 表结构映射

**表名：** `edge`

| 字段名            | 类型       | 是否为空 | 说明      |
| -------------- | -------- | ---- | ------- |
| id             | INTEGER  | NO   | 主键，自增   |
| mind_map_id    | INTEGER  | NO   | 所属导图 ID |
| source_node_id | INTEGER  | NO   | 起始节点 ID |
| target_node_id | INTEGER  | NO   | 目标节点 ID |
| label          | TEXT     | YES  | 连接说明文本  |
| created_at     | DATETIME | NO   | 创建时间    |

---

### 5.2 Entity 设计说明

* EdgeEntity 表示节点之间的**连接关系（图结构）**
* 不参与节点树形结构建模
* 使用 `ManyToOne` 分别指向：

  * MindMapEntity
  * source NodeEntity
  * target NodeEntity
* 不在 Entity 层校验 source / target 是否属于同一导图
  （该逻辑由 Service 层保证）
* 使用 `@PrePersist` 自动维护创建时间

---

## 6. 实体关系总览

```text
MindMapEntity
 ├── NodeEntity (1 : N)
 └── EdgeEntity (1 : N)

NodeEntity
 └── parent_node_id → NodeEntity.id（树结构）

EdgeEntity
 ├── source_node → NodeEntity
 └── target_node → NodeEntity
```

---

## 7. 设计取舍说明

### 7.1 树结构与图结构分离

* 树结构：`NodeEntity.parent_node_id`
* 图结构：`EdgeEntity`

该设计：

* 保证层级结构稳定
* 支持非树状的跨节点连接
* 不破坏原有父子关系模型

---

### 7.2 布局与业务解耦

* 布局方向是导图级属性（MindMap）
* 节点坐标由前端控制
* 后端不关心具体布局算法

---

## 8. Task3 总结

* 数据库结构与 Entity 映射**完全一致**
* 模型职责清晰、无循环依赖
* 为 Task4（Repository + API）提供稳定基础
* 支持后续功能扩展而无需重构表结构

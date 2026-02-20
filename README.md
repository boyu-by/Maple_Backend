## 项目概述

### 1. 项目结构
- **controller/**: 控制器层，处理 HTTP 请求
  - FileController.java: 文件操作相关接口
  - MindMapController.java: 思维导图数据管理接口
  - ImageController.java: 图像处理接口
- **service/**: 服务层，实现核心业务逻辑
  - FileService.java: 文件导入/导出服务
  - FileSystemService.java: 文件系统操作服务
  - ImageGeneratorService.java: 图像生成服务
- **model/**: 数据模型层
  - MindMapData.java: 思维导图数据结构
- **logger/**: 日志工具
  - ErrorLogger.java: 错误日志记录

### 2. 核心功能
- **思维导图管理**:
  - 数据导入/导出
  - 布局方向设置
  - 画布清空
- **文件系统操作**:
  - 文件列表查询
  - 目录创建
  - 文件删除/重命名
- **图像处理**:
  - 图像生成服务

### 3. 技术特点
- **无状态设计**: Controller 不维护请求状态，线程安全
- **RESTful API**: 符合 RESTful 设计原则
- **Spring Boot 3**: 基于最新版本的 Spring Boot
- **多线程安全**: 支持并发请求处理
- **水平扩展**: 可部署多个实例
- **完整测试**: 包含单元测试和集成测试


### 4. 验证结果
- 所有测试用例通过
- 构建成功，代码正常运行
- 服务可正常启动和响应请求

## 技术栈
- **后端**: Spring Boot 3, Java 17
- **构建工具**: Maven
- **测试框架**: JUnit 5
- **API 文档**: OpenAPI 3.0
- **前端集成**: 支持 localStorage, IndexedDB, SQLite 等

## 快速开始
1. 克隆仓库: `git clone <仓库地址>`
2. 构建项目: `mvn clean package`
3. 运行服务: `mvn spring-boot:run`
4. 访问 API: http://localhost:8080/api/mindmap

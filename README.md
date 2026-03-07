# Maple Backend

Maple思维导图后端项目，基于Spring Boot开发，提供思维导图数据管理、历史版本控制和AI智能体辅助功能的API服务。

## 技术栈

- **框架**：Spring Boot
- **数据持久化**：JPA + H2内存数据库
- **API设计**：RESTful风格
- **AI集成**：Dify AI平台
- **构建工具**：Maven

## 功能特性

- **思维导图管理**：支持思维导图的创建、读取、更新和删除
- **版本控制**：自动保存思维导图的历史版本，支持版本的查看、恢复和删除
- **AI智能体**：集成Dify AI平台，提供思维导图相关的智能建议和优化方案
- **CORS配置**：支持跨域请求，确保前后端安全通信

## 安装步骤

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd Maple_Backend
   ```

2. **配置环境变量**
   设置Dify AI API密钥：
   - Windows: `set DIFY_API_KEY=<your-api-key>`
   - Linux/Mac: `export DIFY_API_KEY=<your-api-key>`

3. **构建项目**
   ```bash
   ./mvnw clean package
   ```

## 运行项目

### 开发环境
```bash
./mvnw spring-boot:run
```

项目将在 `http://localhost:8080` 启动。

### 生产环境
```bash
java -jar target/maple-0.0.1-SNAPSHOT.jar
```

## 项目结构

```
Maple_Backend/
├── src/main/java/com/example/maple/
│   ├── config/
│   │   └── CorsConfig.java      # CORS配置
│   ├── controller/
│   │   ├── MindMapController.java
│   │   ├── MindMapVersionController.java
│   │   └── AgentController.java
│   ├── service/
│   │   ├── MindMapService.java
│   │   ├── MindMapVersionService.java
│   │   └── DifyService.java     # AI服务
│   ├── model/
│   │   ├── MindMap.java
│   │   └── MindMapVersion.java
│   ├── repository/
│   │   ├── MindMapRepository.java
│   │   └── MindMapVersionRepository.java
│   └── MapleApplication.java    # 应用入口
├── pom.xml                      # Maven配置
└── README.md                    # 项目说明
```

## API文档

### 思维导图相关API

#### GET /api/mindmap/{id}
- **描述**：获取指定ID的思维导图
- **参数**：
  - `id`：思维导图ID
- **返回**：思维导图对象

#### POST /api/mindmap
- **描述**：创建新的思维导图
- **请求体**：思维导图对象
- **返回**：创建的思维导图对象

#### PUT /api/mindmap/{id}
- **描述**：更新指定ID的思维导图
- **参数**：
  - `id`：思维导图ID
- **请求体**：思维导图对象
- **返回**：更新后的思维导图对象

#### DELETE /api/mindmap/{id}
- **描述**：删除指定ID的思维导图
- **参数**：
  - `id`：思维导图ID
- **返回**：成功消息

### 版本控制相关API

#### GET /api/version/mindmap/{mindMapId}
- **描述**：获取指定思维导图的所有版本
- **参数**：
  - `mindMapId`：思维导图ID
- **返回**：版本列表

#### POST /api/version
- **描述**：创建新的版本
- **请求体**：
  - `mindMapId`：思维导图ID
  - `versionName`：版本名称
- **返回**：创建的版本对象

#### GET /api/version/{id}
- **描述**：获取指定ID的版本
- **参数**：
  - `id`：版本ID
- **返回**：版本对象

#### DELETE /api/version/{id}
- **描述**：删除指定ID的版本
- **参数**：
  - `id`：版本ID
- **返回**：成功消息

### AI智能体相关API

#### POST /api/agent/suggest
- **描述**：获取AI对思维导图的建议
- **参数**：
  - `topic`：思维导图主题或问题
- **返回**：AI的建议内容

## 配置说明

### 数据库配置
- 使用H2内存数据库，无需额外配置
- 应用启动时自动初始化数据库结构

### CORS配置
- 已配置允许所有跨域请求
- 可在 `CorsConfig.java` 中修改配置

### AI集成配置
- 使用Dify AI平台
- 需要设置 `DIFY_API_KEY` 环境变量

## 日志

- 错误日志会记录到控制台
- 可在 `ErrorLogger.java` 中修改日志配置

## 许可证

MIT License

## 贡献

欢迎提交Issue和Pull Request！
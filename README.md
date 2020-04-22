# flink-api-spring-boot-starter

- ### 基于flink rest api开发的spring boot starter，可上传/运行jar等一系列操作

# Quick Start

1. 执行```src/main/resources/sql/flink_explore.sql```
2. jar已发布到中仓仓库，直接依赖即可
```pom
<dependency>
    <groupId>com.github.codingdebugallday</groupId>
    <artifactId>flink-api-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```
3. spring boot配置文件如示例，```src/main/resources/application.yml```
4. 创建自己的flink集群, 已内置```com/github/codingdebugallday/client/api/controller/v1/ClusterController.java```许多接口
> url: http://localhost:9527/v1/{tenantId}/cluster

> method: post
```json
{
    "clusterCode": "hdsp-demo",
    "clusterDesc": "hdsp-demo",
    "jobManagerUrl": "http://hdsp003:50100",
    "username": "root",
    "password": "HandHdspo0!",
    "jobManagerStandbyUrl": "http://hdsp002:50100",
    "enabledFlag": 1,
    "tenantId": 0,
    "nodeDTOList": [
        {
            "nodeCode": "flink_hdsp001",
            "nodeDesc": "hdsp001",
            "settingInfo": "{\"host\":\"hdsp001\",\"username\":\"root\",\"password\":\"HandHdspo0!\"}"
        },
        {
            "nodeCode": "flink_hdsp002",
            "nodeDesc": "hdsp002",
            "settingInfo": "{\"host\":\"hdsp002\",\"username\":\"root\",\"password\":\"HandHdspo0!!\"}"
        },
        {
            "nodeCode": "flink_hdsp003",
            "nodeDesc": "hdsp003",
            "settingInfo": "{\"host\":\"hdsp003\",\"username\":\"root\",\"password\":\"HandHdspo0!!!\"}"
        }
    ]
}
```
4. 使用
> 首先获取FlinkApiContext，然后通过clusterCode以及tenantId获取flinkApi, 
>flinkApi即可调用api，如uploadJar/runJar/jobList等

```java
@Autowired
private FlinkApiContext flinkApiContext;

FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
flinkApi.uploadJar(file)
```

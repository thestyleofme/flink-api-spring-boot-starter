# flink-api-spring-boot-starter

- ### 基于flink rest api开发的spring boot starter，可上传/运行jar等一系列操作

# Quick Start

1. 执行```src/main/resources/sql/flink_explore.sql```
2. jar已发布到中仓仓库，直接依赖即可
```pom
<dependency>
    <groupId>com.github.codingdebugallday</groupId>
    <artifactId>flink-api-spring-boot-starter</artifactId>
    <version>1.0.3.RELEASE</version>
</dependency>
```
3. spring boot配置文件如示例，```src/main/resources/application.yml```
4. 创建自己的flink集群, 已内置```com/github/codingdebugallday/client/api/controller/v1/ClusterController.java```许多接口
> url: http://localhost:9527/v1/{tenantId}/cluster

> method: post
```json
{
    "clusterCode": "hdspdev",
    "clusterDesc": "hdspdev",
    "jobManagerUrl": "http://hdspdev002:50100",
    "username": "root",
    "password": "m8rW2EQ0iDCcWlbH",
    "jobManagerStandbyUrl": "http://hdspdev001:50100",
    "enabledFlag": 1,
    "tenantId": 0,
    "nodeDTOList": [
    	 {
            "nodeCode": "flink_hdspdev001",
            "nodeDesc": "hdspdev001",
            "nodeType":"MARSTER",
            "settingInfo": "{\"host\":\"hdspdev001\",\"username\":\"root\",\"password\":\"m8rW2EQ0iDCcWlbH\"}"
        },
        {
            "nodeCode": "flink_hdspdev002",
            "nodeDesc": "hdspdev002",
            "nodeType":"SLAVE",
            "settingInfo": "{\"host\":\"hdspdev002\",\"username\":\"root\",\"password\":\"m8rW2EQ0iDCcWlbH\"}"
        },
        {
            "nodeCode": "flink_hdspdev003",
            "nodeDesc": "hdspdev003",
            "nodeType":"SLAVE",
            "settingInfo": "{\"host\":\"hdspdev003\",\"username\":\"root\",\"password\":\"m8rW2EQ0iDCcWlbH\"}"
        },
        {
            "nodeCode": "flink_hdspdev004",
            "nodeDesc": "hdspdev004",
            "nodeType":"SLAVE",
            "settingInfo": "{\"host\":\"hdspdev004\",\"username\":\"root\",\"password\":\"m8rW2EQ0iDCcWlbH\"}"
        },
        {
            "nodeCode": "flink_hdspdev005",
            "nodeDesc": "hdspdev005",
            "nodeType":"SLAVE",
            "settingInfo": "{\"host\":\"hdspdev005\",\"username\":\"root\",\"password\":\"m8rW2EQ0iDCcWlbH\"}"
        },
        {
            "nodeCode": "flink_hdspdev006",
            "nodeDesc": "hdspdev006",
            "nodeType":"SLAVE",
            "settingInfo": "{\"host\":\"hdspdev006\",\"username\":\"root\",\"password\":\"m8rW2EQ0iDCcWlbH\"}"
        }
    ]
}
```
4. 使用
> 首先获取FlinkApiContext，然后通过clusterCode以及tenantId获取flinkApi, 
>flinkApi即可调用api，如uploadJar/runJar/jobList等
>
> 会自动重试3次，如jm master挂了，会切换到备用节点进行访问

```java
@Autowired
private FlinkApiContext flinkApiContext;

FlinkApi flinkApi = flinkApiContext.get(clusterCode, tenantId);
flinkApi.uploadJar(file)
```

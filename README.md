#### HBase 发送数据测试

##### 1. 下载JAR

```shell
# 下载jar
wget https://dxs9dnjebzm6y.cloudfront.net/tmp/hbase-demo-1.0.1-SNAPSHOT.jar
```

2. 配置文件

```yml
# 注意修改zk地址
cat -> application.yml << EOF
server:
  port: 9099
hbase:
  zookeeper:
    quorum: 10.1.138.167
    property:
      clientPort: 2181
EOF
```

##### 3. 启动

```
java -jar ./hbase-demo-1.0.1-SNAPSHOT.jar --spring.config.location=./application.yml
```

##### 4. 发送请求

```shell
ip=ip-172-0-6-207
# 创建表 test_tb
curl -XPOST ${ip}:9099/hbase/create_table/test_tb
# 发送数据，可以指定row_key, 放到最后即可，比如下面的row_key为1
curl -XPOST ${ip}:9099/hbase/send_msg/test_tb/1
# get数据，指定row_key, 放到最后即可，比如下面的row_key为1
curl -XPOST ${ip}:9099/hbase/get_msg/test_tb/1
# 删除表
curl -XPOST ${ip}:9099/hbase/drop_table/test_tb
```

![](https://pcmyp.oss-cn-beijing.aliyuncs.com/markdown/202307142344850.png)

![](https://pcmyp.oss-cn-beijing.aliyuncs.com/markdown/202307142343685.png)

![](https://pcmyp.oss-cn-beijing.aliyuncs.com/markdown/202307150001730.png)%

package com.example.demo;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

/**
 * hbase连接
 *
 * @create: 2022-11-01 11:48
 **/

@Component
public class HBaseConn {

//    @Value("${hbase.zookeeper.quorum}")
//    private   String ZOOKEEPER_QUORUM ;
//
//    @Value("${hbase.zookeeper.property.clientPort}")
//    private  String ZOOKEEPER_CLIENT_PORT;

    @Autowired
    private HBaseConfig hbaseConfig;
//    private static final HBaseConn INSTANCE = new HBaseConn();
    private static Configuration configuration;
    private static Connection connection;
    private static HBaseConn instance = null;

   @Bean
    public  HBaseConn getInstance() {
        if (configuration == null) {
            configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.quorum", hbaseConfig.getZookeeperQuorum());
            configuration.set("hbase.zookeeper.property.clientPort", hbaseConfig.getZookeeperClientPort());
        }
        if (instance == null) {
            instance = new HBaseConn();
        }
        return instance;
    }

//    private HBaseConn() {
//        try {
//            if (configuration == null) {
//                configuration = HBaseConfiguration.create();
//                configuration.set("hbase.zookeeper.quorum", hbaseConfig.getZookeeperQuorum());
//                configuration.set("hbase.zookeeper.property.clientPort", hbaseConfig.getZookeeperClientPort());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private Connection getConnection() {
        if (connection == null || connection.isClosed()) {
            try {
                connection = ConnectionFactory.createConnection(configuration);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static Connection getHbaseConn() {
        return instance.getConnection();
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static void closeConn() {
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public static Table getTable(String tableName) throws IOException {
        return instance.getConnection().getTable(TableName.valueOf(tableName));
    }

}

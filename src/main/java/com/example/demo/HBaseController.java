package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hbase",method = RequestMethod.GET)
public class HBaseController {

    @PostMapping("/send_msg/{table_name}/{row_key}")
    public String sendMessageToHBase(@PathVariable("table_name") String table_name,@PathVariable("row_key") String row_key) {
        long stime = System.currentTimeMillis();
        HBaseUtil.putRow(table_name,row_key,"f1","col1","pc1");
        long etime = System.currentTimeMillis();
        return "msg send elapsed time(ms): " + (etime - stime);
    }

    @PostMapping("/get_msg/{table_name}/{row_key}")
    public String getMessageToHBase(@PathVariable("table_name") String table_name,@PathVariable("row_key") String row_key) {
        long stime = System.currentTimeMillis();
        HBaseUtil.getRow(table_name,row_key);
        long etime = System.currentTimeMillis();
        return "get msg elapsed time(ms): " + (etime - stime);
    }
    @PostMapping("/create_table/{table_name}")
    public String createTable(@PathVariable("table_name") String table_name) {
        // create 'test_tb', 'f1'
        System.out.println(table_name);
        long stime = System.currentTimeMillis();
        HBaseUtil.createTable(table_name, new String[]{"f1"});
        long etime = System.currentTimeMillis();
        return "create table elapsed time(ms): " + (etime - stime);
    }

    @PostMapping("/drop_table/{table_name}")
    public String dropTable(@PathVariable("table_name") String table_name) {
        // create 'test_tb', 'f1'
        System.out.println(table_name);
        long stime = System.currentTimeMillis();
        HBaseUtil.deleteTable(table_name);
        long etime = System.currentTimeMillis();
        return "drop table elapsed time(ms): " + (etime - stime);
    }
}

package com.northcastle.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author: northcastle
 * @CreateTime: 2023-03-18  10:56
 * @Description: fastdfs 文件操作的工具类
 *
 */
public class FastDFSUtile {


    /**
     * 类加载的时候，直接就初始化
     * 使用 纯Java的方式进行初始化
     */
    static {
        try {
            Properties fdfsProperties = new Properties();
            // tracker 服务的IP 和 端口号
            fdfsProperties.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.88.126:22122");
            // tracker 服务的 http.server_port
            fdfsProperties.put(ClientGlobal.PROP_KEY_HTTP_TRACKER_HTTP_PORT,"6789");
            ClientGlobal.initByProperties(fdfsProperties);
            System.out.println(ClientGlobal.configInfo());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args)  {

    }
}

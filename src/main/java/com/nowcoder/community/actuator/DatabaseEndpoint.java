package com.nowcoder.community.actuator;

import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Endpoint(id = "database")
public class DatabaseEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseEndpoint.class);

    @Autowired
    private DataSource dataSource;//连接池是由spring容器来管理的直接注入进来就可以了

    //我们尝试获取连接有多个方式：1.访问连接池把dataSource注入进来然后getConnection()
    // 2.更原始的方式直接把连接参数传进来，然后用manager访问，这里用1
    @ReadOperation//表示是通过GET请求来访问的（这个端点只能通过GET请求来访问）
    public String checkConnection() {
        try (//写在这里编译时自动加finally关闭资源
                Connection conn = dataSource.getConnection();
        ) {
            return CommunityUtil.getJSONString(0, "获取连接成功!");
        } catch (SQLException e) {
            logger.error("获取连接失败:" + e.getMessage());
            return CommunityUtil.getJSONString(1, "获取连接失败!");
        }
    }

}
package com.nowcoder.community.dao.elasticsearch;

import com.nowcoder.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository//是spring的数据访问层注解，Mapper是mybatis的注解
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {
    //这个接口已经实现了对es服务器的增删改查

}
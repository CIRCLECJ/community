package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    //可以搜所有帖子也可以搜我的帖子(通过if判断实现)
    // 后两个参数是分页用的，offset是起始行号，limit是每一页最多显示多少行数据
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    //查询帖子的行数
    // @Param注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.如果不止一个，如selectDiscussPosts也需要用if但有三个参数就可以不写
    // 如果我需要动态的拼一个条件，并且这个方法有且只有这一个条件，这时参数必须取别名
    int selectDiscussPostRows(@Param("userId") int userId);


    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(int id, int commentCount);
}

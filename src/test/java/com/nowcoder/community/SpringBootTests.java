package com.nowcoder.community;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.service.DiscussPostService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SpringBootTests {

    @Autowired
    private DiscussPostService discussPostService;

    private DiscussPost data;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("beforeClass");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("afterClass");
    }

    @Before
    public void before() {
        System.out.println("before");

        // 初始化测试数据
        data = new DiscussPost();
        data.setUserId(111);
        data.setTitle("Test Title");
        data.setContent("Test Content");
        data.setCreateTime(new Date());
        discussPostService.addDiscussPost(data);
    }

    @After
    public void after() {
        System.out.println("after");

        // 删除测试数据(这样的话每一个测试方法（如test1）的执行都创建一个新的data然后这个方法执行完就销毁，
        // 下一次执行test2再创建一次data，这样保证数据的独立性)
        discussPostService.updateStatus(data.getId(), 2);
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

    @Test
    public void testFindById() {
        DiscussPost post = discussPostService.findDiscussPostById(data.getId());
        //一般我们测试不是拿到结果然后sout到后台查看而是通过看是否一致，
        // 一致就在test结果打勾，不一致就叉然后们再去打叉的地方具体看哪里出错了
        Assert.assertNotNull(post);//判断查询结果是否为空
        Assert.assertEquals(data.getTitle(), post.getTitle());//判断你期待的数据和得到的数据是否相等
        Assert.assertEquals(data.getContent(), post.getContent());
    }

    @Test
    public void testUpdateScore() {
        int rows = discussPostService.updateScore(data.getId(), 2000.00);
        Assert.assertEquals(1, rows);

        DiscussPost post = discussPostService.findDiscussPostById(data.getId());
        Assert.assertEquals(2000.00, post.getScore(), 2);//最后一个参数是精度，2位小数
    }

}

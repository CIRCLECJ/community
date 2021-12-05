package com.nowcoder.community.config;

import com.nowcoder.community.quartz.AlphaJob;
//import com.nowcoder.community.quartz.PostScoreRefreshJob;
import com.nowcoder.community.quartz.PostScoreRefreshJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

// 配置 -> 数据库 -> 调用(仅仅是在第一次被读取到，然后初始化到数据库里，以后quartz是访问数据库去调用这个任务不是访问配置文件
@Configuration
public class QuartzConfig {

    //FactoryBean与BeanFactory不一样，BeanFactory是容器的顶层接口（IOC时讲的）
    // FactoryBean可简化Bean的实例化过程:（主要目的）（可以认为JobDetailFactoryBean它的底层封装了JobDetail详细的实例化过程）
    // 1.通过FactoryBean封装Bean的实例化过程.（某一些Bean）
    // 2.将FactoryBean装配到Spring容器里.
    // 3.将FactoryBean注入给其他的Bean.
    // 4.该Bean得到的是FactoryBean所管理的对象实例.

    // 配置JobDetail
    //@Bean//注释掉才不会再被执行了
    public JobDetailFactoryBean alphaJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(AlphaJob.class);
        factoryBean.setName("alphaJob");
        factoryBean.setGroup("alphaJobGroup");
        factoryBean.setDurability(true);//是持久的保存吗
        factoryBean.setRequestsRecovery(true);//是否可恢复
        return factoryBean;
    }

    // 配置Trigger(SimpleTriggerFactoryBean, CronTriggerFactoryBean)
    // 前为简单的Trigger，比如我想每几分钟执行一次，后为复杂的trigger，比如我想每个月的月底的晚上执行任务就可以用它
    //@Bean
    public SimpleTriggerFactoryBean alphaTrigger(JobDetail alphaJobDetail) {//在初始化Trigger的时候它是依赖于JobDetail的,它需要注入jobDetail
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(alphaJobDetail);
        factoryBean.setName("alphaTrigger");
        factoryBean.setGroup("alphaTriggerGroup");
        factoryBean.setRepeatInterval(3000);//执行频率
        factoryBean.setJobDataMap(new JobDataMap());//trigger的底层需要存储job的一些状态，这些状态需要指定用什么对象来存
        return factoryBean;
    }

    // 刷新帖子分数任务
    @Bean
    public JobDetailFactoryBean postScoreRefreshJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(PostScoreRefreshJob.class);
        factoryBean.setName("postScoreRefreshJob");
        factoryBean.setGroup("communityJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean postScoreRefreshTrigger(JobDetail postScoreRefreshJobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(postScoreRefreshJobDetail);
        factoryBean.setName("postScoreRefreshTrigger");
        factoryBean.setGroup("communityTriggerGroup");
        factoryBean.setRepeatInterval(1000 * 60 * 5);//5min
        factoryBean.setJobDataMap(new JobDataMap());
        return factoryBean;
    }

}

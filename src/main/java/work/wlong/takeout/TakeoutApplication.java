package work.wlong.takeout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//日志
@Slf4j
//配置类，等同于：@Configuration ，@EnableAutoConfiguration 和 @ComponentScan 三个配置
/*
@ComponentScan	用来自动扫描被这些注解标识的类，最终生成ioc容器里的bean，默认扫描范围是@ComponentScan注解所在配置类包及子包的类
@SpringBootConfiguration	与@Configuration作用相同，都是用来声明当前类是一个配置类，这里表明是springboot主类使用的配置类
@EnableAutoConfiguration	是springboot实现自动化配置的核心注解，通过这个注解把spring应用所需的bean注入容器中
*/
@SpringBootApplication
//Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册
@ServletComponentScan
@EnableTransactionManagement
@EnableCaching
public class TakeoutApplication {
    public static void main(String[] args) {
        SpringApplication.run(TakeoutApplication.class);
        log.info("项目启动成功!");
    }
}

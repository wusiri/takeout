package work.wlong.takeout.filter;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import work.wlong.takeout.common.BaseContext;
import work.wlong.takeout.common.R;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否登录
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    ////路径匹配器,支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //请求信息
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //响应信息
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取本次请求url
        String requestURI = request.getRequestURI();
        //不需要处理的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        //判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        //如果不需要处理，直接放行
        if(check){
            log.info("拦截到请求:{}", request.getRequestURI());
            //将请求转发给过滤器链下一个filter , 如果没有filter那就是你请求的资源
            filterChain.doFilter(request, response);
            return;
        }
        //判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("employee")!=null) {
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            //设置empId
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request,response);
            return;
        }
        if (request.getSession().getAttribute("user")!=null) {
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            //设置empId
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request,response);
            return;
        }
        //如果未登录则返回登录结果,通过输出流方式向客户端响应数据
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls 直接放行的url
     * @param requestUrl 本次请求url
     * @return 匹配信息，true、false
     */
    public boolean check(String[] urls,String requestUrl){
        for (String url : urls) {
            //进行比对
            boolean match = PATH_MATCHER.match(url, requestUrl);
            //判断比对信息
            if(match){
                return true;
            }
        }
        return false;
    }
}

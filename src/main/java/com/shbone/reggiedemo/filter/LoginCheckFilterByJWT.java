package com.shbone.reggiedemo.filter;

import com.alibaba.fastjson.JSON;
import com.shbone.reggiedemo.common.R;
import com.shbone.reggiedemo.dto.UserTokenDTO;
import com.shbone.reggiedemo.service.impl.RedisServiceImpl;
import com.shbone.reggiedemo.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: SunHB
 * @createTime: 2023/12/17 下午4:06
 * @description: jwt checker
 */
@WebFilter(filterName = "loginCheckFilterByJWT", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilterByJWT implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Autowired
    private RedisServiceImpl redisService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        //1、获取本次请求的URI
        String requestURI = request.getRequestURI();// /backend/index.html


        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        //2、判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //3、如果不需要处理，则直接放行
        if (check) {
            //log.info("本次请求{}不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        if(token != null && !token.equals("")){
            //4-1 JWT判断登录状态，如果JWT token 在有效期内，则放行，否则重新登录
            log.info("拦截到请求：{}，需要进行验证处理", requestURI);
            UserTokenDTO userTokenDTO = JWTUtil.parseToken(token);
            if (redisService.get(userTokenDTO.getUserName()) == null || !redisService.get(userTokenDTO.getUserName()).equals(token)) {
                log.error("用户未登录");
                //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
                response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
                return;
                // 4-2 JWT token 30分钟内过期，则动重置token有效期
            } else if (redisService.getExpireTime(userTokenDTO.getUserName()) <= 1 * 60 * 30) {
                log.info("用户{}即将过期，token自动重置有效期60分钟", userTokenDTO.getUserName());
                redisService.set(userTokenDTO.getUserName(), token);
                filterChain.doFilter(request, response);
                return ;
            } else {
                filterChain.doFilter(request,response);
                return ;
            }
        }
        log.error("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     *
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}

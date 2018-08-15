package com.baihoomuch.cloud.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baihoomuch.cloud.service.UserService;
import com.baihoomuch.cloud.vo.UserVo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

/**
 * Description: microservice-simple-provider-user
 * auther Administrator on 2018/7/5
 * User 操作控制
 */
@RestController //注解启动加载, 表示该类控制返回的是json格式
@RequestMapping("/userContro")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     *
     * @param objectid 通过占位名称（占位名称得与形参名称一致）直接在访问的Url后面带参数值 例如：
     * @return
     */
    @GetMapping("/findUser/{objectid}")
    public UserVo findUser(@PathVariable Long objectid){
        UserVo userVo= new UserVo();
        BeanUtils.copyProperties(userService.findByObjectid(objectid),userVo);
         return  userVo;
    }
    @Autowired
    private DiscoveryClient discoveryClient;
    /**
     * 您不需要使用原始Netflix EurekaClient。而且，通常在某种包装器后面使用它更方便。SpringCloud通过逻辑Eureka服务标识符(VIP)，而不是物理URL，
     * 支持假象(REST客户端构建器)和SpringRestTemplate。要用物理服务器的固定列表配置Ribbon，可以将<Client>.ribbon.listOfServers设置为以逗号
     * 分隔的物理地址(或主机名)列表，其中<Client>是客户机的ID。
     * 
     * 您还可以使用org.springframework.cloud.client.discovery.DiscoveryClient，它为发现客户端提供了一个简单的API(不特定于Netflix)，如下面的示例所示：
     * @return
     */
    @GetMapping("/serviceUrl")
    public URI serviceUrlByDiscovery() {
        List<ServiceInstance> list = discoveryClient.getInstances("MICROSERVICE-PROVIDER-USER");
        if (list != null && list.size() > 0 ) {
            return list.get(0).getUri();
        }
        return null;
    }
    @Autowired
    private EurekaClient eurekaClient;
    /**
     * 一旦您拥有了一个作为发现客户端的应用程序，您就可以使用它从Eureka Server中发现服务实例。这样做的一种方法是使用本机
     * @return
     */
    @GetMapping("/serviceUrlByEureka")
    public String serviceUrlByEurekaClient() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("MICROSERVICE-PROVIDER-USER", false);
        return instance.getHomePageUrl();
    }
    
    /**
     * 微服务，get请求方法传对象的方式
     * @param userVo
     * @return
     */
    @GetMapping("/getTest") //“提供者微服务”远程get方式测试，测试源---microservice-consumer-movie-feign
    public UserVo getTest(@RequestBody UserVo userVo) {
    	System.out.println(userVo);
		return userVo;
    }
    /**
     * 微服务，get请求方法传参的方式
     * @param age
     * @param username
     * @return
     */
    @GetMapping("/getTest2") //“提供者微服务”远程get方式测试，测试源---microservice-consumer-movie-feign
    private UserVo getTest2(Integer  age, String username) {
    	UserVo user = new UserVo();
    	user.setAge(age);
    	user.setUsername(username);
    	System.out.println(user);
		return user;
    }
    /**
     *  微服务，post请求方法传对象的方式
     * @param userVo
     * @return
     */
    @PostMapping("/postTest") //“提供者微服务”远程post方式测试，测试源---microservice-consumer-movie-feign
    public  UserVo postTest(@RequestBody UserVo userVo) {
    		System.out.println(userVo);
    		return userVo;
    }
}

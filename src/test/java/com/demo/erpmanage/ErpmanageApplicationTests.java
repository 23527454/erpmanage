package com.demo.erpmanage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.erpmanage.entity.SysUser;
import com.demo.erpmanage.service.SysUserService;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ErpmanageApplicationTests {
    @Resource
    private SysUserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void write() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname", "system");
        SysUser user = userService.login(queryWrapper);
        System.out.println(user);
        SysUser user2 = userService.login(queryWrapper);
        System.out.println(user2);
    }

    @Test
    void write2() {
        Map<String, String> map = new HashMap<>();
        map.put("loginname", "ls");
        map.put("pwd", "123456");
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname", "system");
        SysUser user = userService.login(queryWrapper);

        JSONObject jsonObject = JSONObject.fromObject(user);

        stringRedisTemplate.opsForValue().set("loginUser", jsonObject.toString());

        System.out.println(stringRedisTemplate.opsForValue().get("loginUser"));

        SysUser user2 = (SysUser) JSONObject.toBean(JSONObject.fromObject(stringRedisTemplate.opsForValue().get("loginUser")), SysUser.class);
        System.out.println("user:" + user);
        System.out.println("user2:" + user2);
    }
}

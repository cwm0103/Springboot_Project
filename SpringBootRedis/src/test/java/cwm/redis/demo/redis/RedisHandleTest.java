package cwm.redis.demo.redis;

import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisHandleTest {

    @Autowired
    private RedisHandle redisHandle;
    @Test
    public void getCreateTimeScore() {
        double createTimeScore = redisHandle.getCreateTimeScore(1000);
        System.out.println(createTimeScore);
    }

    @Test
    public void getAllKeys() {
        Set<String> allKeys = redisHandle.getAllKeys();
        System.out.println(allKeys);

    }

    @Test
    public void getAllString() {
        Map<String, Object> allString = redisHandle.getAllString();
        System.out.println(allString);

    }

    @Test
    public void getAllSet() {

    }

    @Test
    public void getAllZSetRange() {
    }

    @Test
    public void getAllZSetReverseRange() {
    }

    @Test
    public void getAllList() {
    }

    @Test
    public void getAllMap() {
    }

    @Test
    public void addList() {
        List<String> list=new LinkedList<>();
        list.add("小学");
        list.add("大学");
        list.add("中学");
        redisHandle.addList("cwm",list);
    }
}
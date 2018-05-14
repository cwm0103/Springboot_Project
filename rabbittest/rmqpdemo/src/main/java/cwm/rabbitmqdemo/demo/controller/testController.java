package cwm.rabbitmqdemo.demo.controller;

import cwm.rabbitmqdemo.demo.entity.Dog;
import cwm.rabbitmqdemo.demo.rabbimq.fanout.FanoutSender;
import cwm.rabbitmqdemo.demo.rabbimq.hello.HelloSender;
import cwm.rabbitmqdemo.demo.rabbimq.more.MoreSender1;
import cwm.rabbitmqdemo.demo.rabbimq.more.MoreSender2;
import cwm.rabbitmqdemo.demo.rabbimq.object.ObjectSender;
import cwm.rabbitmqdemo.demo.rabbimq.topic.TopicSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("rbmq")
public class testController {

    @Autowired
    private  HelloSender helloSender;

    @Autowired
    private MoreSender1 moreSender1;
    @Autowired
    private MoreSender2 moreSender2;

    @Autowired
    private ObjectSender objectSender;

    @Autowired
    private TopicSender topicSender;

    @Autowired
    private FanoutSender fanoutSender;

    @RequestMapping("hello")
    @ResponseBody
    public String test()
    {
        for (int i=0;i<10;i++)
        {
            helloSender.send1(String.valueOf(i));
            helloSender.send2(String.valueOf(i));
        }
        return "消息已经发送！";
    }
    @RequestMapping("more")
    @ResponseBody
    public String more()
    {

        moreSender1.Send1("sender1");
        moreSender2.Sender2("sender2");
        return "多对多发送消息";
    }

    @RequestMapping("object")
    @ResponseBody
    public String object()
    {
        Dog dog=new Dog();
        dog.setAge(4);
        dog.setArea("中国");
        dog.setId(1);
        dog.setName("大累");
        dog.setType("斗狗");
        objectSender.SendObj(dog);
        return "发送实体对象";
    }
    @RequestMapping("topic")
    @ResponseBody
    public String topic()
    {
        topicSender.Sender();
        //topicSender.Sender1();
        //topicSender.Sender2();
        return "topic 方式发送";
    }

    @RequestMapping("fanout")
    @ResponseBody
    public String fanout()
    {
        fanoutSender.Send("fanout 消息发送！");
        return "fanout 发送成功！";
    }
}

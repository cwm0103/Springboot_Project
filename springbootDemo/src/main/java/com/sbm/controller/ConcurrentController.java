package com.sbm.controller;

import com.sbm.synchronizedclass.Counter;
import com.sbm.synchronizedclass.SyncThread;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 并发控制器
 * cwm
 * 2018-2-6
 */

@Controller
@RequestMapping("/cwm")
public class ConcurrentController {

    /**
     * 1. synchronized关键字修饰的方法。
     */
    @RequestMapping("/abc")
    @ResponseBody
    public synchronized   String getName()
    {
//        SyncThread syncThread=new SyncThread();
//        Thread thread1=new Thread(syncThread,"SyncThread1");
//        Thread thread2=new Thread(syncThread,"SyncThread2");
//        thread1.start();
//        thread2.start();

        /**
         * 不是说一个线程执行synchronized代码块时其它的线程受阻塞吗？
         * 为什么上面的例子中thread1和thread2同时在执行。
         * 这是因为synchronized只锁定对象，每个对象只有一个锁（lock）与之相关联
         */
        Thread thread1 = new Thread(new SyncThread(), "SyncThread1");
        Thread thread2 = new Thread(new SyncThread(), "SyncThread2");
        thread1.start();
        thread2.start();
        /**
         * 这时创建了两个SyncThread的对象syncThread1和syncThread2，
         * 线程thread1执行的是syncThread1对象中的synchronized代码(run)，
         * 而线程thread2执行的是syncThread2对象中的synchronized代码(run)；
         * 我们知道synchronized锁定的是对象，这时会有两把锁分别锁定syncThread1对象和syncThread2对象，
         * 而这两把锁是互不干扰的，不形成互斥，所以两个线程可以同时执行。
         */
        return "陈王明";
    }

    @RequestMapping("/bbs")
    @ResponseBody
    public String getCounter()
    {
        Counter counter = new Counter();
        Thread thread1 = new Thread(counter, "A");
        Thread thread2 = new Thread(counter, "B");
        thread1.start();
        thread2.start();
        return "非线程阻塞";
    }
}

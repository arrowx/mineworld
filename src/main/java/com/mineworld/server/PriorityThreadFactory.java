
package com.mineworld.server;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class PriorityThreadFactory implements ThreadFactory {
    
    private final int _prio;
    
    private final String _name;
    
    private final AtomicInteger _threadNumber = new AtomicInteger(1);
    
    private final ThreadGroup _group;
    
    /** * * @param name 线程池名 * @param priority 线程池优先级 */
    public PriorityThreadFactory(String name, int priority) {
        _prio = priority;
        _name = name;
        _group = new ThreadGroup(_name);
    }
    
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(_group, r);
        t.setName(_name + "-" + "#-" + _threadNumber.getAndIncrement());
        t.setPriority(_prio);
        return t;
    }
    
    public ThreadGroup getGroup() {
        return _group;
    }
}

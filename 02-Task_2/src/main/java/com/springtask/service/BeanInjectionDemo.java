package com.springtask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanInjectionDemo {

    /*
     * TRY THIS TO SEE ERROR
     *
     * @Autowired
     * private final Foo foo;
     *
     * Spring cannot inject into final field directly.
     */

    private final Foo foo;

    /*
     * Since multiple Foo beans exist,
     * Spring uses @Primary bean automatically.
     */

    @Autowired
    public BeanInjectionDemo(Foo foo) {

        this.foo = foo;
    }

    public void testInjection() {

        System.out.println("Injected Bean Type:");
        System.out.println(foo.getClass().getName());

        foo.printMessage();
    }
}
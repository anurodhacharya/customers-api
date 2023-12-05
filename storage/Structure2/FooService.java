package com.aurickcode;

import org.springframework.stereotype.Service;

@Service
public class FooService {
    
    private final Main.Foo foo;

    public FooService(Main.Foo foo) { // Here, we get foo from the application context and then we assign foo to that foo and then we can use that foo in those methods too.
        this.foo = foo;
    }

    String getFooName() {
        return foo.name();
    }
}

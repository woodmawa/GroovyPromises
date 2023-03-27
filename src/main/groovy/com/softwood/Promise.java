package com.softwood;

import groovy.lang.Closure;
import groovy.transform.CompileStatic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@CompileStatic
interface Promise<T>   {

    static Promise factory () {
        //default factory is std GroovyPromise
        //try and detect other libraries substitute
        enum Detected  {
                STANDARD;
                //other impl types based on detecting some library
        };

        Detected factoryType = Detected.STANDARD;

        return switch (factoryType) {
            case STANDARD -> new GroovyPromise();
            //case other
            default -> new GroovyPromise(); //use this as last resort
        };
    };

    static Promise task (Closure closure) {
        Promise p = factory();
        p.assign (closure);
        return p ;
    };

    public Promise<T> then(Closure closure);
    public Promise<T> assign(Closure closure);
    public T get();
    public T get(long timeout, TimeUnit unit);
    public T get(T valueIfAbsent);
    public T leftShift(Closure closure);
    public boolean isDone();
    public boolean isCompletedExceptionally();
    public boolean isCancelled();
    public T onComplete(Closure action);
    public T onError(Closure action);

    //static Promise<T> task (Closure task) {
        //Promise<T> promise = new Promise<T>();
    //}


}
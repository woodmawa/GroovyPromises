package com.softwood;

import groovy.lang.Closure;

import java.util.concurrent.TimeUnit;


//@CompileStatic
interface PromiseUnresolved<T>   {

    /*
    public static Promise factory () {
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

    public static Promise task (Closure closure) {
        Promise p = factory();
        p.assign (closure);
        return p ;
    };

     */

    public PromiseUnresolved<T> then(Closure closure);
    public PromiseUnresolved<T> assign(Closure closure);
    public T get();
    public T get(long timeout, TimeUnit unit);
    public T get(T valueIfAbsent);
    public T leftShift(Closure closure);
    public boolean isDone();
    public boolean isCompletedExceptionally();
    public boolean isCancelled();
    public T onComplete(Closure action);
    public T onError(Closure action);

}
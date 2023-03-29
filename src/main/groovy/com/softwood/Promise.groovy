package com.softwood


import java.util.concurrent.TimeUnit;


//@CompileStatic
interface Promise<T>   {

    Promise<T> then(Closure closure)
    Promise<T> assign(Closure closure)
    T get()
    T get(long timeout, TimeUnit unit)
    T get(T valueIfAbsent)
    T leftShift(Closure closure)
    boolean isDone()
    boolean isCompletedExceptionally()
    boolean isCancelled()
    T onComplete(Closure action)
    T onError(Closure action)

}
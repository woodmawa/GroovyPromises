package com.softwood

import groovy.transform.CompileStatic

import java.util.concurrent.TimeUnit

@CompileStatic
interface Promise<T>   {
    Promise<T> then (Closure closure)
    Promise<T> assign (Closure closure)
    T get()
    T get(long timeout, TimeUnit unit)
    T get (T valueIfAbsent)
    T leftShift (Closure closure)
    boolean isDone()
    boolean isCompletedExceptionally()
    boolean isCancelled()
    onComplete (Closure action)
    onError (Closure action)

}
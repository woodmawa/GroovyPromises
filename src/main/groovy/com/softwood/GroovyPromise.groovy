package com.softwood

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.TimeUnit

class GroovyPromise implements Promise {
    CompletableFuture future //concrete class

    static Promise task(Closure closure) {
        Promise p = new GroovyPromise()
        p.future = CompletableFuture.supplyAsync (closure)
        return p
    }

    static Promise task(value, long timeout, TimeUnit unit, Closure closure) {
        Promise p = new GroovyPromise()
        p.future = CompletableFuture.supplyAsync (closure)
        p.future.completeOnTimeout(value, timeout, unit)
        return p
    }

    @Override
    Promise then(Closure closure) {
        future = future.thenApply (closure)
        return this
    }

    @Override
    Promise assign (Closure closure) {
        future = future.completeAsync (closure)
        return this
    }


    @Override
    Object get() {
        future.get()
    }

    @Override
    Object get(long timeout, TimeUnit unit) {
        future.get(timeout, unit)
    }

    @Override
    Object get(valueIfAbsent) {
        future.getNow(valueIfAbsent)
    }

    @Override
    Object leftShift(Closure closure) {
        task (closure)
    }

    @Override
    boolean isDone() {
        future.isDone ()
    }

    @Override
    boolean isCompletedExceptionally() {
        future.isCompletedExceptionally ()
    }

    @Override
    boolean isCancelled() {
        future.isCancelled ()
    }

    @Override
    def onComplete(Closure action) {
        // action (result, exception)
        def result
        //expects bifunction so provide wrapper to get the value , and just pass that to users action
        (future as CompletionStage).whenComplete (result = {value, ex-> action (value)})
        return result
    }

    @Override
    def onError(Closure action) {
        // action (result, exception)
        def result
        (future as CompletionStage).whenComplete (result = action)
        return result

    }
}

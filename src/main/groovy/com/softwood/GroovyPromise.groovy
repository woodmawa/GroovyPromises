package com.softwood

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.TimeUnit

class GroovyPromise<T> implements Promise {
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
    Promise assign (Closure answer) {
        assert answer
        if (!future)
            future = new CompletableFuture().completeAsync (answer)
        else
            throw new ReadOnlyPropertyException("Promise has already been assigned, create a new promise", GroovyPromise)
        return this
    }


    @Override
    T get() {
        future.get()
    }

    @Override
    T get(long timeout, TimeUnit unit) {
        future.get(timeout, unit)
    }

    @Override
    T get(valueIfAbsent) {
        future.getNow(valueIfAbsent)
    }

    @Override
    GroovyPromise leftShift(Closure closure) {
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
        //exception should be null in this case, so ignore it
        (future as CompletionStage).whenComplete (result = {value, exc -> action (value)})
        return result
    }

    @Override
    def onError(Closure action) {
        //expects bifunction so provide wrapper to get the value , and just pass that to users action
        def result
        (future as CompletionStage).whenComplete (result = {value, exc -> action (value, exc)})
        return result

    }
}

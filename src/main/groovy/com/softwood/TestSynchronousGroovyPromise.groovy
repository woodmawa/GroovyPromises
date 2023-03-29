package com.softwood

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.TimeUnit

class TestSynchronousGroovyPromise<T> implements Promise {
    T result //concrete class
    boolean isDone = false
    boolean isCompletedExceptionally = false
    boolean isCancelled = false

    static Promise task(Closure closure) {
        Promise p = new TestSynchronousGroovyPromise()
        p.result = closure()
        p.isDone = true
        return p
    }

    static Promise task(value, long timeout, TimeUnit unit, Closure closure) {
        Promise p = new TestSynchronousGroovyPromise()
        p.result = closure()
        return p
    }

    @Override
    Promise then(Closure closure) {
        result = closure(result)
        isDone = true
        return this
    }

    @Override
    Promise assign (Closure answer) {
        assert answer
        if (!result) {
            result = answer()
            isDone = true
        }
        else
            throw new ReadOnlyPropertyException("Promise has already been assigned, create a new promise", TestSynchronousGroovyPromise)
        return this
    }


    @Override
    T get() {
        result
    }

    @Override
    T get(long timeout, TimeUnit unit) {
        result
    }

    @Override
    T get(valueIfAbsent) {
        result ?: valueIfAbsent
    }

    @Override
    TestSynchronousGroovyPromise leftShift(Closure closure) {
        task (closure)
    }

    @Override
    boolean isDone() {
        isDone ()
    }

    @Override
    boolean isCompletedExceptionally() {
        isCompletedExceptionally ()
    }

    @Override
    boolean isCancelled() {
        isCancelled ()
    }

    @Override
    def onComplete(Closure action) {
        // action (result, exception)
        def resultCompletion
        //expects bifunction so provide wrapper to get the value , and just pass that to users action
        //exception should be null in this case, so ignore it
        resultCompletion = action (result)
        return resultCompletion
    }

    @Override
    def onError(Closure action) {
        //expects bifunction so provide wrapper to get the value , and just pass that to users action
        def resultCompletion
        resultCompletion = action (result)
      return resultCompletion

    }
}

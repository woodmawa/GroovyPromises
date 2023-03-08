package com.softwood

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit

class GroovyPromiseList implements PromiseList {
    Collection<Promise> futureList = new ConcurrentLinkedQueue()

    /*static Promise task(Closure closure) {
        Promise p = new GroovyPromiseList()
        p.future = CompletableFuture.supplyAsync (closure)
        return p


    static Promise task(value, long timeout, TimeUnit unit, Closure closure) {
        Promise p = new GroovyPromiseList()
        p.future = CompletableFuture.supplyAsync (closure)
        p.future.completeOnTimeout(value, timeout, unit)
        return p
    }*/

    /*@Override
    Promise then(Closure closure) {
        future = future.thenApply (closure)
        return this
    }*/

    @Override
    List get() {
        futureList as List
    }

    @Override
    Object leftShift(Closure closure) {
        futureList .add(GroovyPromise.task (closure))
    }

    @Override
    def onComplete(Closure outcomeListProcessor ) {
        // action (result, exception)
        def result
        List<CompletableFuture> futures = futureList.collect {GroovyPromise p -> p.future}
        CompletableFuture allDone = CompletableFuture.allOf(*futures)
        //expects bifunction so provide wrapper to get the value , and just pass that to users action
        allDone.whenComplete { value, exception ->
            result = futureList.collect { GroovyPromise p -> p.future.get() }
            outcomeListProcessor (result)
            result
        }
        return allDone
    }

    @Override
    def onAnyComplete (Closure outcomeProcessor) {
        // action (result, exception)
        List<CompletableFuture> futures = futureList.collect {GroovyPromise p -> p.future}
        CompletableFuture anyDone = CompletableFuture.anyOf(*futures)
        //expects bifunction so provide wrapper to get the value , and just pass that to users action
        anyDone.whenComplete { value, exception ->
            outcomeProcessor (value)
        }
        return anyDone
    }
/*
    @Override
    def onError(Closure action) {
        // action (result, exception)
        def result
        (future as CompletionStage).whenComplete (result = action)
        return result

    }*/

    static onAnyComplete (List<Promise> promises, Closure outcomeProcessor) {
        // action (result, exception)
        def result
        List<CompletableFuture> futures = promises.collect {GroovyPromise p -> p.future}
        CompletableFuture anyDone = CompletableFuture.anyOf(*futures)
        //expects bifunction so provide wrapper to get the value , and just pass that to users action
        anyDone.whenComplete { value, exception ->
            outcomeProcessor (value)
        }
        return anyDone

    }

    //onError (Closure action)

    static onComplete (List<Promise> promises, Closure outcomeListProcessor ) {
        // action (result, exception)
        def result
        List<CompletableFuture> futures = promises.collect {GroovyPromise p -> p.future}
        CompletableFuture allDone = CompletableFuture.allOf(*futures)
        //expects bifunction so provide wrapper to get the value , and just pass that to users action
        allDone.whenComplete { value, exception ->
            result = promises.collect { GroovyPromise p -> p.future.get() }
            outcomeListProcessor (result)
        }
        return allDone

    }
}

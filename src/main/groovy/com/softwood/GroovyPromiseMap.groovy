package com.softwood

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

class GroovyPromiseMap implements PromiseMap {
    Map<String, Promise> futureMap = new ConcurrentHashMap<>()

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
    Map get() {
        futureMap as Map
    }

    @Override
    Promise get(String key) {
        futureMap.get (key)
    }

    @Override
    def onComplete(Closure outcomeMapProcessor ) {
        // action (result, exception)
        def result
        List<CompletableFuture> futures = futureMap.collect { key, GroovyPromise p -> p.future}
        CompletableFuture allDone = CompletableFuture.allOf(*futures)
        //expects bifunction so provide wrapper to get the value , and just pass that to users action
        allDone.whenComplete { value, exception ->
            result = futureMap.collect {key,  GroovyPromise p -> p.future.get() }
            outcomeMapProcessor (result)
            result
        }
        return allDone
    }

    @Override
    def onAnyComplete (Closure outcomeProcessor) {
        // action (result, exception)
        List<CompletableFuture> futures = futureMap.collect { GroovyPromise p -> p.future}
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

    static onAnyComplete (Map<String, Promise> promisesMap, Closure outcomeProcessor) {
        // action (result, exception)
        def result
        List<CompletableFuture> futures = promisesMap.collect {GroovyPromise p -> p.future}
        CompletableFuture anyDone = CompletableFuture.anyOf(*futures)
        //expects bifunction so provide wrapper to get the value , and just pass that to users action
        anyDone.whenComplete { value, exception ->
            outcomeProcessor (value)
        }
        return anyDone

    }

    static onComplete (Map<String, Promise> promisesMap, Closure outcomeListProcessor ) {
        // action (result, exception)
        def result
        List<CompletableFuture> futures = promisesMap.collect {GroovyPromise p -> p.future}
        CompletableFuture allDone = CompletableFuture.allOf(*futures)
        //expects bifunction so provide wrapper to get the value , and just pass that to users action
        allDone.whenComplete { value, exception ->
            result = promisesMap.collect { GroovyPromise p -> p.future.get() }
            outcomeListProcessor (result)
        }
        return allDone

    }
}

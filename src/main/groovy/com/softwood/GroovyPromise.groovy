package com.softwood

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class GroovyPromise implements Promise {
    CompletableFuture future //concrete class

    static Promise task(Closure closure) {
        Promise p = new GroovyPromise()
        p.future = CompletableFuture.supplyAsync (closure)
        return p
    }

    @Override
    Promise then(Closure closure) {
        future = future.thenApply (closure)
        return this
    }

    @Override
    Object get() {
        future.get()
    }

    @Override
    Object get(long timeout, TimeUnit unit) {
        future.get(timeout, unit )
    }

    @Override
    Object leftShift(Closure closure) {
        task (closure)
    }
}

package com.softwood

import java.util.concurrent.CompletableFuture

class GroovyPromise implements Promise {
    CompletableFuture future

    @Override
    Promise task(Closure closure) {
        future = CompletableFuture.supplyAsync (closure)
        return this
    }

    @Override
    Promise then(Closure closure) {
        return future.thenApply (closure)
    }

    @Override
    Object get() {
        future.get()
    }
}

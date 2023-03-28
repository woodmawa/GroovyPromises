package com.softwood

import com.softwood.factory.PromiseFactory
import com.softwood.factory.PromiseFactoryBuilder

class Promises {
    static PromiseFactory promiseFactory = new PromiseFactoryBuilder().build()

    private Promises () {

    }

    static PromiseFactory getPromiseFactory() {
        return promiseFactory
    }

    //overwrite the default if you need to
    static void setPromiseFactory(PromiseFactory promiseFactory) {
        Promises.@promiseFactory = promiseFactory
    }

    static Promise createPromise () {
        promiseFactory.createPromise()
    }

    static Promise task (Closure work) {
        promiseFactory.task (work)
    }
}

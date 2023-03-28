package com.softwood.factory

import com.softwood.GroovyPromise
import com.softwood.Promise
import com.softwood.TestSynchronousGroovyPromise

class SynchronousPromiseFactory extends AbstractPromiseFactory {
    Promise createPromise() {

        new TestSynchronousGroovyPromise()
    }

    Promise task (Closure work) {
        Promise p = new TestSynchronousGroovyPromise()
        p.assign (work)
        return p
    }
}

package com.softwood.factory

import com.softwood.GroovyPromise
import com.softwood.Promise
import com.softwood.TestSynchronousGroovyPromise


class GroovyPromiseFactory extends AbstractPromiseFactory {
    Promise createPromise() {

        new GroovyPromise()
    }

    Promise task(Closure work) {
        Promise p = new GroovyPromise()
        p.assign (work)
        return p
    }
}

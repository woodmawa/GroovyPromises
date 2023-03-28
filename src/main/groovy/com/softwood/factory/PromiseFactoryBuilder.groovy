package com.softwood.factory

import com.softwood.GroovyPromise
import com.softwood.TestSynchronousGroovyPromise


class PromiseFactoryBuilder {
    static PromiseFactory build() {
        //default factory is std GroovyPromise
        //try and detect other libraries substitute
        Detected factoryType = Detected.STANDARD

        StackTraceElement[] stack = Thread.currentThread().getStackTrace()
        String dump = stack.toString()

        boolean testRunner = stack.any {StackTraceElement elem -> elem.toString().contains ("Runner")}
        if (testRunner)
            factoryType = Detected.TEST

        return switch (factoryType) {
            case Detected.STANDARD -> new GroovyPromiseFactory()
            case Detected.TEST -> new TestSynchronousGroovyPromise()

                //case other
            default -> new GroovyPromise() //use this as last resort
        }
    }
}


enum Detected  {
    STANDARD,
    TEST
    //other impl types based on detecting some library
}


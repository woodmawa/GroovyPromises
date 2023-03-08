package com.softwood

import groovy.transform.CompileStatic

import java.util.concurrent.TimeUnit

@CompileStatic
interface PromiseList<T>   {
    T get()
    T leftShift (Closure closure)
    onAnyComplete (Closure processList)
    //onError (Closure action)
    onComplete (Closure closure)

}
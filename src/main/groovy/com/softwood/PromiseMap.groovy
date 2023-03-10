package com.softwood

import groovy.transform.CompileStatic

@CompileStatic
interface PromiseMap<String, Promise>   {
    Promise get(String key)
    onAnyComplete (Closure processMap)
    //onError (Closure action)
    onComplete (Closure closure)

}
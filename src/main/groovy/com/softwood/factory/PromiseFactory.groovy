package com.softwood.factory

import com.softwood.Promise

//defines a set of public static methods to create a promise
//implemented in various concrete classes
interface PromiseFactory<T> {
    Promise<T> createPromise ()
    Promise<T> task (Closure work)
}

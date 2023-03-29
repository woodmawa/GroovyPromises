package com.softwood.factory

import com.softwood.Promise

//defines a set of public static methods to create a promise
//implemented in various concrete classes  
abstract class AbstractPromiseFactory implements PromiseFactory {
    //static Promise createPromise () {}
    //static Promise task (Closure work) {}  //empty implementation
}

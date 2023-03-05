package com.softwood

interface Promise<T>   {
    Promise<T> task (Closure closure)
    Promise<T> then (Closure closure)
    T get()
}
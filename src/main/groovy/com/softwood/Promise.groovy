package com.softwood

interface Promise<T>   {
    Promise<T> then (Closure closure)
    T get()
}
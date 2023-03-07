package com.softwood

import java.util.concurrent.TimeUnit

interface Promise<T>   {
    Promise<T> then (Closure closure)
    T get()
    T get(long timeout, TimeUnit unit)
    T get (T valueIfAbsent)
    T leftShift (Closure closure)
}
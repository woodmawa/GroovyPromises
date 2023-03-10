import com.softwood.GroovyPromise
import com.softwood.GroovyPromiseList
import com.softwood.Promise
import com.softwood.PromiseList

import java.nio.channels.ScatteringByteChannel

import static com.softwood.GroovyPromise.*
import static com.softwood.GroovyPromiseList.*

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

Future<String> async() throws InterruptedException {
  CompletableFuture<String> completableFuture = new CompletableFuture<>()

  ExecutorService executor =  Executors.newCachedThreadPool()
  executor.submit(() -> {
    //Thread.sleep(100)
    completableFuture.complete("Hello")
    return null
  })

  executor.shutdownNow()


  return completableFuture
}

static void main(String[] args) {

  println "Hello world!"

  //Promise p = task {2*2}

  //println p.get()


  /*def p1 = task { 2 * 2 }
  def p2 = task { 4 * 4 }
  def p3 = task { 8 * 8 }
  assert [4,16,64] == waitAll(p1, p2, p3)*/

  //def promiseList = tasks([{ 2 * 2 }, { 4 * 4}, { 8 * 8 }])

  //println promiseList
  //assert [4,16,64] == promiseList.get()

  //CompletableFuture fut = async()
  //println fut.get()

  //CompletableFuture f = CompletableFuture.supplyAsync  {"groovy hello"}
  //println f.get()

  Promise p = task { "groovy hello" }.then { it + " more text" }
  p.onComplete { value -> println "promise all done saw : $value" }

  println p.get()
  assert p.isDone()

  //put some promises into promise list
  PromiseList pl = new GroovyPromiseList()
  pl << { 2 * 2 }
  pl << { 2 * 4 }
  pl << { 2 * 5 }

  onAnyComplete(pl.get()) { println "\t>static onAny $it" }

  pl.onAnyComplete { results ->
    println "\t>any results method : $results"
  }

  pl.onComplete { List results ->
    println "promiseList results method : $results"
  }

  onComplete(pl.get()) { println "static promiseList results $it" }

  Promise p1 = new GroovyPromise().assign {10*10}
  println "test assign " + p1.get()

  //println pl.get()

//sleep 200
}

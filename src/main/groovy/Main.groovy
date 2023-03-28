import com.softwood.Promise
import com.softwood.PromiseFactory

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

//import static grails.async.Promises.*
//import static com.softwood.Promise.*

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

  Promise p3 = PromiseFactory.create()
  println p3.getClass().name
  //Promise p2 = Promise.task {2*20}
  //println "p2: ${p2.get()}"

  /*
  Promise p = new GroovyPromise()
  p.task {"groovy hello"}.then {it + "more text" }

  println p.get()

  */

}

import kotlinx.coroutines.*
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext


fun main() {


    val job1: Job = Job()

    val scope = CoroutineScope(Dispatchers.Default + job1)

    val job2 = scope.launch(start = CoroutineStart.LAZY) {

        logMessage("Hello")
        delay(100)

    }

    Thread.sleep(2000)
    logMessage("world")

}


fun Job.logStates() {
    logMessage(">>>")
    logMessage("$this is isActive? $isActive")
    logMessage("$this is isCancelled? $isCancelled")
    logMessage("$this is isCompleted? $isCompleted")
    logMessage("<<<")
}


fun logMessage(msg: String) {
    println("Running on: [${Thread.currentThread().name}] | $msg")
}


fun CoroutineScope.logContext(id: String) {
    coroutineContext.logDetails(id)
}


fun CoroutineContext.logDetails(id: String) {
    sequenceOf(
        Job,
        ContinuationInterceptor,
        CoroutineExceptionHandler,
        CoroutineName
    )
        .mapNotNull { key -> this[key] }
        .forEach { logMessage("id: $id ${it.key} = ${it}") }
}
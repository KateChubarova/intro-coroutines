package kate_tests

import contributors.User
import contributors.log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel


class KTests {
    val actual = listOf(
        User("Alice", 1), User("Bob", 3),
        User("Alice", 2), User("Bob", 7),
        User("Charlie", 3), User("Alice", 5)
    )

    fun test() {
        println("group_by test")
        println(actual)
        println("-------")
        val actual_group_by = actual.groupBy { it.login }
        print(actual_group_by)

        println("test map")
        val actual_map =
            actual_group_by.map { (login, users_contributions) ->
                User(
                    login,
                    users_contributions.sumBy { it.contributions })
            }
        println(actual_map)

        println(actual_map.sortedByDescending { it.contributions })
    }

    fun main() = runBlocking {
        val deferred: Deferred<Int> = async {
            loadData()
        }
        log("waiting...")
        log(deferred.await().toString())
        log("world Hello")
    }

    suspend fun loadData(): Int {
        log("loading...")
        delay(1000L)
        log("loaded!")
        return 42
    }

    fun global_scope_test() {
        println("hello")
        GlobalScope.launch {
            delay(1000L)
            println("finished")
        }
        println("hello")
    }

    suspend fun kate_fun() = coroutineScope {}

    fun channel_main() = runBlocking {
        val channel = Channel<String>()
        launch {
            channel.send("A1")
            channel.send("A2")
            log("A done")
        }
        launch {
            channel.send("B1")
            log("B done")
        }
        launch {
            repeat(3) {
                val x = channel.receive()
                log(x)
            }
        }
    }

    fun log(message: Any?) {
        println("[${Thread.currentThread().name}] $message")
    }
}
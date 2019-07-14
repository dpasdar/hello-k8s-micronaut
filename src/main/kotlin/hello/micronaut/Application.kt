package hello.micronaut

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("hello.micronaut")
                .mainClass(Application.javaClass)
                .start()
    }
}
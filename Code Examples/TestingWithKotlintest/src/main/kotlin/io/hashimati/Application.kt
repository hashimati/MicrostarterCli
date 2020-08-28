package io.hashimati

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("io.hashimati")
		.start()
}


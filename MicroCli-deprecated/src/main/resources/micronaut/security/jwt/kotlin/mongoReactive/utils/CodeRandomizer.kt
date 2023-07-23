package ${securityPackage}.utils

import jakarta.inject.Singleton
import java.util.*
import javax.annotation.PostConstruct


@Singleton
class CodeRandomizer {
    // A= 65- Z=90  , a= 97 z =122, 0 =48, 9 = 57
    var rndSet = ArrayList<Int>()
    @PostConstruct
    private fun init() {
        for (i in 65..90) {
            rndSet.add(i)
        }
        for (i in 48..57) {
            rndSet.add(i)
        }
    }

    fun getRandomString(length: Int): String {
        val r = Random()
        val random = StringBuilder("")
        for (i in 1..length) {
            random.append(r.nextInt(10))
        }
        return random.toString()
    }
}


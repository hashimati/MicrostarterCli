package ${securityPackage}.services

import ${securityPackage}.PasswordEncoderService
import ${securityPackage}.domains.LoginStatus
import ${securityPackage}.domains.Roles
import ${securityPackage}.domains.User
import ${securityPackage}.repository.UserRepository
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Inject
import jakarta.inject.Singleton
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.*

@Singleton
class UserService (private val userRepository: UserRepository?, private val passwordEncoderService: PasswordEncoderService){

    fun save(user: User): Mono<User> {
        user.password = passwordEncoderService!!.encode(user.password)
        user.id = user.username
        return userRepository!!.save(user)
    }

    fun findByUsername(username: String?): Mono<User> {
        return userRepository!!.findByUsername(username)
    }

    @EventListener
    fun init(startupEvent: StartupEvent?) {
        val c = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.YEAR, 1000)
        val admin = User()
        admin.username = "admin"
        admin.password = "admin"
        admin.active = true
        admin.roles.add(Roles.ADMIN)
        admin.email = "Hello@gmail.com"
        admin.lastTimeLogin = Instant.now()
        admin.activationCode = "0000"
        admin.lastTimeTryToLogin = Instant.now()
        admin.lastLoginStatus = LoginStatus.SUCCEED
        println(
            admin
        )
        if (!userRepository!!.existsByUsername(admin.username)) save(admin).block()
        //        if(!userRepository.existsByUsername(admin.getUsername()))
        println(findByUsername("admin"))
    }
}
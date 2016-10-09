package server

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    private lateinit var userRepository: UserRepository

    @RequestMapping
    fun users(principal: Principal): List<User> {
        val username= principal.name
        this.validateUser(username)
        return userRepository.findAll()
    }

    @RequestMapping(value="{userId}")
    fun readUser(@PathVariable userId: String): User {
        return userRepository.findOne(userId)
    }

    @RequestMapping("insert")
    fun usersInsert(@RequestParam(value = "username") username: String,
                    @RequestParam(value = "fullName") fullName: String,
                    @RequestParam(value = "password") password: String): User {

        val user = User(username, fullName, password)
        userRepository.insert(arrayListOf(user))
        return user
    }
    private fun validateUser(username: String?) {
        userRepository.findByUsername(username) ?: throw Exception("username $username is not found")
    }
}

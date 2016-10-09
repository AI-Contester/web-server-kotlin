package server

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Configuration
open class AuthenticationConfiguration : GlobalAuthenticationConfigurerAdapter() {

    @Autowired
    lateinit var userRepository: UserRepository

    @Throws(Exception::class)
    override fun init(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService())
    }

    @Bean
    open fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            val user = userRepository.findByUsername(username)?:throw UsernameNotFoundException("$username not found")
            User(user.username, user.password, true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER", "write"))
        }
    }
}

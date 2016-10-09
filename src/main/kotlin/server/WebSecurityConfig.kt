package server

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
open class WebSecurityConfig: WebSecurityConfigurerAdapter() {
    override fun configure(web: WebSecurity?) {
        if (web == null) {
            return
        }
        web.ignoring().antMatchers("/greeting")
    }
}

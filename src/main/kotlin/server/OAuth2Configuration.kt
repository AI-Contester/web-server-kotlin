package server

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer

@Configuration
@EnableResourceServer
@EnableAuthorizationServer
open class OAuth2Configuration : AuthorizationServerConfigurerAdapter() {

    @Autowired
    lateinit var authenticationManager: AuthenticationManagerBuilder

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {

        endpoints?.authenticationManager({ authentication -> authenticationManager.orBuild.authenticate(authentication) })
    }

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        if (clients == null) {
            return
        }
        clients.inMemory().withClient("trusted")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("read", "write")
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer?) {
        security?.allowFormAuthenticationForClients() // чтобы не требовал Authorization header
    }
}

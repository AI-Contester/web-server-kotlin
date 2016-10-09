package server

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.embedded.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.HttpStatus
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@SpringBootApplication
open class Application {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

    @Bean
    open fun corsFilter(
            @Value("\${tagit.origin:http://localhost:9000}") origin: String): FilterRegistrationBean {
        return FilterRegistrationBean(object : Filter {
            @Throws(IOException::class, ServletException::class)
            override fun doFilter(req: ServletRequest, res: ServletResponse,
                         chain: FilterChain) {
                val request = req as HttpServletRequest
                val response = res as HttpServletResponse
                val method = request.method
                // this origin value could just as easily have come from a database
                response.setHeader("Access-Control-Allow-Origin", origin)
                response.setHeader("Access-Control-Allow-Methods",
                        "POST,GET,OPTIONS,DELETE")
                response.setHeader("Access-Control-Max-Age", java.lang.Long.toString((60 * 60).toLong()))
                response.setHeader("Access-Control-Allow-Credentials", "true")
                response.setHeader(
                        "Access-Control-Allow-Headers",
                        "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization")
                if ("OPTIONS" == method) {
                    response.status = HttpStatus.OK.value()
                } else {
                    chain.doFilter(req, res)
                }
            }

            override fun init(filterConfig: FilterConfig) {
            }

            override fun destroy() {
            }
        })
    }
}

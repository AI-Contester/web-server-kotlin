package server

import org.springframework.data.annotation.Id

class User(var username: String, var fullName: String, var password: String?) {
    @Id
    lateinit var id: String

    override fun toString():String = String.format(
            "Customer[id=%s, username'%s', fullname='%s']",
            id, this.username, fullName
    )
}

package server

import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository:MongoRepository<User, String> {
    fun findByUsername(username: String?): User?

    fun findByFullName(fullName: String): List<User>
}

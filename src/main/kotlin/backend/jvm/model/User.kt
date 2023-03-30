package backend.jvm.model

import jakarta.persistence.*
import java.util.*

/**
 *
 * User entity
 *
 * @property id the User primary key
 * @property token the User token
 * @property email the User email
 * @property password the User password
 * @property username the User username
 * @property clientName the User name
 * @property birthday the User birthday
 * @property companyId null if the user has the role of 'guest' or 'client'. the company where the user works
 */
/*
@Entity
@Table(name = "user")
class User {
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        val id: Int? = null

        @Column(name = "token")
        val token: String? = null

        @Column(name = "email")
        val email: String

        @Column(name = "password")
        val password: String

        @Column(name = "name")
        val clientName: String

        @Column(name = "birthday")
        val birthday: Date?

        @ManyToMany()
        @JoinTable(
                name = "user_role",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")]
        )
        val role: List<Role>

        constructor(email:String,
                    password:String,
                    clientName:String,
                    birth:Date,
                    role: List<Role>
        ){
                this.email = email
                this.password = password
                this.clientName = clientName
                this.birthday = birth
                this.role = role
        }

        constructor(){
                this.email = ""
                this.password = ""
                this.clientName = ""
                this.birthday = null
                this.role = listOf()
        }*/
//}
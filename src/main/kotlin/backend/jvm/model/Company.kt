package backend.jvm.model

import jakarta.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "company")
@org.hibernate.annotations.NamedQuery(
        name = "getCompanyById",
        query = "from Company where id = :id",
        cacheable = true,
        timeout = 1
)
class Company {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0

        @Column(name = "nif", unique = true)
        val nif: String

        @Column(name = "address")
        val address: String

        @Column(name = "name")
        val name: String

        @Column(name = "type")
        val type : String

        @Column(name = "description")
        val description:String

        @OneToMany(mappedBy = "company")
        val serviceDBS: List<ServiceDB>?

        @OneToOne(mappedBy = "company")
        val schedule: Schedule?

        /*@ManyToMany
        @JoinTable(
                name = "user_company",
                joinColumns = [JoinColumn(name = "company_id")],
                inverseJoinColumns = [JoinColumn(name = "user_id")]
        )
        val usersDB: List<UserDB>?*/

        @OneToMany(mappedBy = "company")
        val userCompany: List<UserCompany>?

        constructor() {
                this.nif = ""
                this.address = ""
                this.name = ""
                this.type = ""
                this.description = ""
                this.serviceDBS = null
                this.schedule = null
                this.userCompany = listOf()
        }

        constructor(nif: String, address: String, compName: String, compType: String, description: String, serviceDB: List<ServiceDB>?, schedule: Schedule?, usersDB: List<UserCompany>?){
                this.nif = nif
                this.address = address
                this.name = compName
                this.type = compType
                this.description = description
                this.serviceDBS = serviceDB
                this.schedule = schedule
                this.userCompany = usersDB
        }

}


class Node(val data: Int){
        var previous: Node? = null
        var next: Node? = null
}

class BinaryTree{

        var root: Node?  = null


        fun insert (data: Int){
                root = insertBinary(root, data)
        }


        fun insertBinary(node: Node?, data: Int): Node?{
                if(node == null){
                        return Node(data)
                }
                if(data < node.data)
                        node.previous = insertBinary(node.previous, data)
                else if(data > node.data){
                        node.next = insertBinary(node.next, data)
                }

                return node
        }

        fun search(data: Int): Boolean{
                return searchBinary(root, data)
        }

        fun searchBinary(node: Node?, data: Int): Boolean{
                if(node == null) return false
                if(node.data == data) return true

                return if(node.data > data)
                        searchBinary(node.previous, data)
                else
                        searchBinary(node.next, data)
        }


}







fun main() {


        val tree = BinaryTree()

        tree.insert(4)
        tree.insert(2)
        tree.insert(6)
        tree.insert(1)
        tree.insert(3)
        tree.insert(5)
        tree.insert(7)

        println(tree.search(5))  // true
        println(tree.search(8))  // false










        /*val a = "abggtztf"

        val b = "ftgtgba"

        val list = hashMapOf<Char, Int>()
        val list1 = hashMapOf<Char, Int>()

        for (i in a.indices) {
                if (list.keys.contains(a[i])) {
                        list[a[i]] = list[a[i]]!! + 1
                }
                else list.put(a[i], 1)
        }

        for (i in b.indices) {
                if (list1.keys.contains(b[i])) {
                        list1[b[i]] = list1[b[i]]!! + 1
                }
                else list1.put(b[i], 1)
        }

        println(list.equals(list1))*/



}
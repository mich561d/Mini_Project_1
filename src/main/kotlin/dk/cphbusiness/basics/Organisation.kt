package dk.cphbusiness.basics

open class Person (var firstName: String, var lastName: String, val dateOfBirth: String)

class Employee(
        firstName: String,
        lastName: String,
        dateOfBirth: String,
        var dateOfEmployee: String,
        var salary: Double,
        department: Department
        ) : Person(firstName, lastName, dateOfBirth) {

    val relatives = mutableListOf<Relative>()

    private var note = ""
    var department: Department = department
        get() = field
        set(value) {
            println("removing from department")
            field.remove(this)
            field = value
            println("adding to department")
            value.add(this)
            }

    var managedDepartment: Department? = null

    init {
        department.add(this)
        }

    fun print() {
        println("$firstName $lastName")
        }
    }

class Department(
        var code: String,
        var name: String
        ) {
    lateinit var manager: Employee
    private val employeeStore = mutableListOf<Employee>()
    val employees: List<Employee>
        get() = employeeStore

    fun add(employee: Employee) {
        employeeStore.add(employee)
        }

    fun remove(employee: Employee) {
        employeeStore.remove(employee)
        }

    override fun toString() = "[$code] $name ${manager.firstName} has ${employees.size} employees"
    }

class Relative(
        firstName: String,
        lastName: String,
        dateOfBirth: String,
        var role: String,
        employee: Employee
        ): Person(firstName, lastName, dateOfBirth)


fun main() {
    val adm = Department("ADM", "Administration")
    val e1 = Employee(
        "Sonja",
        "Jensen",
        "1999-10-02",
        "2019-10-02",
        10_000.00,
        adm
        )
    adm.manager = e1
    val itd = Department("IT", "IT Department")
    itd.manager = e1
    println(adm)
    val e2 = Employee(
        "Kurt",
        "Hansen",
        "1999-10-02",
        "2019-10-02",
        9_000.00,
        adm
    )
    println(adm)
    e2.department = itd
    println(adm)
    println(itd)
    // adm.employees.add(e2)
    println(adm)
    println(itd)
    }
package com.example.application.backend

import java.util.*


object BackendService {

    private val employees = mutableSetOf<Employee>()

    init {
        employees.add(Employee(UUID.fromString("e87dc893-f6cf-478e-8d7b-30639ed70436"), "Rowena", "Leeming", "rleeming0@bbc.co.uk", "Food Chemist"))
        employees.add(Employee(UUID.fromString("7489fdcd-a056-4447-9a3a-c6199c89288c"), "Alvinia", "Delong", "adelong1@altervista.org", "Recruiting Manager"))
        employees.add(Employee(UUID.fromString("bb18583e-af29-4f9c-94c6-037a08a47ee9"), "Leodora", "Burry", "lburry2@example.com", "Food Chemist"))
        employees.add(Employee(UUID.fromString("1fd2671d-c02f-4576-8200-0414c9d30ad9"), "Karen", "Oaten", "koaten3@ihg.com", "VP Sales"))
    }

    fun getEmployees(): Set<Employee> {
        return employees
    }

    fun save(employee: Employee) {
        employees.add(employee)
    }

    fun load(id: UUID): Employee {
        return employees.firstOrNull { id == it.id } ?:  throw RuntimeException("No employee with id: $id")
    }
}

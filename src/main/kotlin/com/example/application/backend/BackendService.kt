package com.example.application.backend

import java.util.*


class BackendService private constructor() {

    private var employees: MutableList<Employee>? = null

    init {
        // Init dummy data

        employees = ArrayList()
        employees!!.add(Employee(UUID.fromString("e87dc893-f6cf-478e-8d7b-30639ed70436"), "Rowena", "Leeming", "rleeming0@bbc.co.uk", "Food Chemist"))
        employees!!.add(Employee(UUID.fromString("7489fdcd-a056-4447-9a3a-c6199c89288c"), "Alvinia", "Delong", "adelong1@altervista.org", "Recruiting Manager"))
        employees!!.add(Employee(UUID.fromString("bb18583e-af29-4f9c-94c6-037a08a47ee9"), "Leodora", "Burry", "lburry2@example.com", "Food Chemist"))
        employees!!.add(Employee(UUID.fromString("1fd2671d-c02f-4576-8200-0414c9d30ad9"), "Karen", "Oaten", "koaten3@ihg.com", "VP Sales"))
        /*employees.add(new Employee("Mariele", "Huke", "mhuke4@washingtonpost.com", "Research Assistant IV"));
        employees.add(new Employee("Grata", "Widdowes", "gwiddowes5@cargocollective.com", "Actuary"));
        employees.add(new Employee("Donna", "Roadknight", "droadknight6@apache.org", "Mechanical Systems Engineer"));
        employees.add(new Employee("Tommi", "Nowland", "tnowland7@biblegateway.com", "Senior Developer"));
        employees.add(new Employee("Tonya", "Teresia", "tteresia8@boston.com", "Assistant Manager"));
        employees.add(new Employee("Steffen", "Yon", "syon9@ocn.ne.jp", "Senior Sales Associate"));
        employees.add(new Employee("Consalve", "Willes", "cwillesa@linkedin.com", "Programmer I"));
        employees.add(new Employee("Jeanelle", "Lambertz", "jlambertzb@nymag.com", "Operator"));
        employees.add(new Employee("Odelia", "Loker", "olokerc@gov.uk", "Developer I"));
        employees.add(new Employee("Briano", "Shawell", "bshawelld@posterous.com", "Research Assistant IV"));
        employees.add(new Employee("Tarrance", "Mainston", "tmainstone@cmu.edu", "Research Nurse"));
        employees.add(new Employee("Torrence", "Gehring", "tgehringf@a8.net", "Geological Engineer"));
        employees.add(new Employee("Augie", "Pionter", "apionterg@ehow.com", "Senior Financial Analyst"));
        employees.add(new Employee("Marillin", "Aveson", "mavesonh@shop-pro.jp", "Technical Writer"));
        employees.add(new Employee("Jacquelyn", "Moreby", "jmorebyi@slashdot.org", "Executive Secretary"));
        employees.add(new Employee("Glenn", "Bangley", "gbangleyj@prlog.org", "Account Executive"));
        employees.add(new Employee("Isidoro", "Glave", "iglavek@tamu.edu", "Compensation Analyst"));
        employees.add(new Employee("Cchaddie", "Spatarul", "cspatarull@sun.com", "Business Systems Development Analyst"));*/
    }

    fun getEmployees(): List<Employee>? {
        return employees
    }

    fun save(employee: Employee) {
        employees!!.removeIf { emp -> employee.id == emp.id }
        employees!!.add(employee)
    }

    fun load(id: UUID): Employee {
        return employees!!.stream()
                .filter { employee -> id == employee.id }
                .findAny()
                .orElseThrow { RuntimeException("No employee with id: $id") }
    }

    companion object {
        var INSTANCE = BackendService()
    }
}

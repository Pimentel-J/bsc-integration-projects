# Additional Requirements - FURPS

## Functionality
*Specifies functionalities that are not related with the use cases, such as: Audit, Reporting and Security.*

-  Languages:
    * All the artifacts, code, documentation, etc, must be in english.

## Usability
*Evaluates the user's interface. Has subcategories, namely: error prevention; aesthetics and design; help (Help) and documentation; consistency and patterns.*

N/A

## Reliability
*Refers to software integrity, compliance, and interoperability. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, mean time between failures.*

- Backup the execution orders in a binary file to have possibility of recovery.

## Performance
*Evaluates software performance requirements, namely: response time, memory use, CPU utilization, load capacity and application availability.*

N/A

## Supportability
*Supportability requirements group several features, such as: testability, adaptability, maintainability, compatibility, configurability, installability, scalability and more.*

- Configurability:
	* The company's information (e.g. title and tax number) should be specified by configuration.
	* The system must use an external service _defined by configuration_ [to obtain zip-codes to be covered in the geographic area].
	* The system must use a job scheduling algorithm _defined by configuration_ [to schedule service providers to do services].


- Testability:
	* Code coverage and mutation coverage is based on unit testing and is performed using quality assurance tools.


## +
### Design constraints
*Specifies or restricts the system design process. Examples may include: software process, use of development tools, libraries, etc.*

-  The software development process should be iterative and incremental while adopting good design practices and coding standards. The students should use the software development process introduced in ESOFT.
-  In this project all members of the team should use Jenkins and Sonar Cube for continuous integration and code quality assessment, respectively.
-  Allow import of files with different extensions [for import of execution orders]

### Implementation constraints
*Specifies or restricts the code or construction of a system. Examples: mandatory patterns, implementation languages, database integrity policies, resource limits, operating system.*

-  All the algorithms implemented in this work should be highly efficient.
-  Both applications should be implemented in Java. 
-  The user interface should be implemented using JavaFX.
-  The implementation process must follow a TDD (Test Driven Development) approach. Unit tests should be developed to validate all domain classes. Code changes must follow the same criteria, i.e. when changing existing components, unit tests must be developed or updated. When developing Input/Output (IO) methods for files, unit tests are recommended but not mandatory under the project.


### Interface constraints
*Specifies or restricts functionalities related to the system's interaction with other external systems.*

(fill here)

### Physical constraints
*Specifies a limitation or physical requirement of the used hardware, such as: material, shape, size or weight.*

(fill here)
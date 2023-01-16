# Simple NEM12 Parser
This project is a simple implementation of a parser for a simplified version of the NEM12 file format used in the Australian energy market. The parser reads the file and converts it into a collection of MeterRead objects.

# Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
 * Java 8 or later
 * Maven

### Installing

Clone the repository:

`git clone https://github.com/yourusername/SimpleNEM12Parser.git`

Build the project:

`mvn clean install`

Run the Unit Tests:

`mvn test`

Run the application:

`mvn spring-boot:run`

This will output the test cases given in `TestHarness.java` this could be commented out if not needed.

### Project Structure
- **entity:** Package that contains all the entities used in the project such as MeterRead, MeterVolume which were provided.
- **exception**: Package that contains all the custom exceptions used in the project such as SimpleNem12FileFormatException, SimpleNem12FileParsingException, etc.
- **mapper**: Package that contains all the classes to map the values from the file to the entity objects.
- **service**: Package that contains the main implementation of the SimpleNem12Parser interface.
- **util**: Package that contains the utility classes used in the project such as CSVFileReader. This was seperated from the main method for reusability. 
- **validation**: Package that contains the custom validations used in the project. 
I separated the validations from the mapper classes to make the code more modular. This will make it easier to add further validations in the future.

### Error Handling
The parseSimpleNem12 method throws a SimpleNem12FileParsingException in case of any errors while parsing the NEM12 file. This exception can be caught and handled in the calling code.

### Extending the project
* Given more time and depending on the project requirements the SimpleNem12Parser could be extended to instead accept types other than File. 
* Additional validation checks and alternate validation handling could potentially be added depending on the output required.
* The Mapper and entity classes could be easily modified to handle additional fields in the NEM12 file.

### Additional Questions
- How will the parser be used? This could dictate the implementation and also the technologies used.
- What should happen to the files after they have been processed?
- How should the parser handle validations? I've assumed for this purpuse that the processing is stopped and the exception is raised.
- Is it possible that the file can change?
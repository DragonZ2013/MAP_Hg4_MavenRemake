package Main.Repository;

import Main.Model.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.*;
import java.util.List;

public class StudentRepository extends InMemoryRepository<Student> implements FileRepository{

    /**
     * Constructor for StudentRepository objects
     */
    public StudentRepository() {
        super();
    }

    /**
     * Updates a Student object from the Memory Repository array given the ID of the parameter object
     * @param obj
     * @return Student -modified object
     */
    @Override
    public Student update(Student obj) {
        Student studentToUpdate = this.repoList.stream()
                .filter(student -> student.getStudentId() == obj.getStudentId())
                .findFirst()
                .orElseThrow();
        studentToUpdate.setEnrolledCourses(obj.getEnrolledCourses());
        studentToUpdate.setTotalCredits(obj.getTotalCredits());
        studentToUpdate.setFirstName(obj.getFirstName());
        studentToUpdate.setLastName(obj.getLastName());

        return studentToUpdate;
    }

    @Override
    public List readFromFile() throws FileNotFoundException {
        return null;
    }

    @Override
    public void writeToFile() {

    }

    @Override
    public Object findOne(int Id) {
        return null;
    }

    @Override
    public void close() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

        String serializedStudent = "";

        for (Student s : repoList){
            objectMapper.registerModule(new SimpleModule().addSerializer(Student.class, new StudentSerializer()));

            serializedStudent += objectMapper.writeValueAsString(s);

            serializedStudent += ",";

            writer.writeValue(new File("StudentData.json"),serializedStudent);

        }

    }
}

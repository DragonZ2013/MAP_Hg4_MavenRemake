package Main.Repository;

import Main.Model.Course;
import Main.Model.Student;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentRepository extends InMemoryRepository<Student> implements FileRepository{

    /**
     * Constructor for StudentRepository objects
     */
    public StudentRepository(CourseRepository courseRepository) throws IOException {
        super();

        BufferedReader fixReader = new BufferedReader(new FileReader("studentData.json"));

        String line = fixReader.readLine().replace("\\","");

        fixReader.close();

        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.replace(0,1,"[");
        stringBuilder.replace(line.length()-2,line.length(),"]");

        BufferedWriter fixWriter = new BufferedWriter(new FileWriter("studentData.json"));

        fixWriter.write(stringBuilder.toString());
        fixWriter.close();
        Reader studentReader = new BufferedReader(new FileReader("studentData.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(studentReader);

        for (JsonNode n: parser ){
            List<Course> tempCourses = new ArrayList<>();
            String courses = n.path("enrolledCourses").asText();
            String[] splitCourses = courses.replace("[","").replace("]","").replace(" ","").split(",");
            List<Integer> coursesId = new ArrayList<>(Arrays.asList(splitCourses)).stream().map(Integer::valueOf).toList();
            for (Course c: courseRepository.getAll())
                for(Integer cId: coursesId)
                    if(cId==c.getCourseId())
                        tempCourses.add(c);
            Student s = new Student(n.path("firstName").asText(),n.path("lastName").asText(),n.path("studentId").asInt(),n.path("totalCredits").asInt(),tempCourses);
            this.create(s);

        }
        this.close();
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

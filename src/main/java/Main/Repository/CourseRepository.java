package Main.Repository;

import Main.Model.Course;
import Main.Model.Teacher;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository extends InMemoryRepository<Course> implements FileRepository{

    /**
     * Constructor for CourseRepository objects
     */
    public CourseRepository(TeacherRepository teacherRepository) throws IOException {
        super();
        /*
        BufferedReader fixReader = new BufferedReader(new FileReader("courseData.json"));

        String line = fixReader.readLine().replace("\\","");

        fixReader.close();

        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.replace(0,1,"[");
        stringBuilder.replace(line.length()-2,line.length(),"]");

        BufferedWriter fixWriter = new BufferedWriter(new FileWriter("courseData.json"));

        fixWriter.write(stringBuilder.toString());
        fixWriter.close();
        */
        Reader courseReader = new BufferedReader(new FileReader("courseData.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(courseReader);

        for (JsonNode n: parser ){
            Teacher tempTeacher = null;
            int teacherId = n.path("teacherId").asInt();
            for(Teacher t: teacherRepository.getAll())
                if(t.getTeacherId()==teacherId)
                    tempTeacher=t;
            Course c = new Course(n.path("name").asText(),tempTeacher,n.path("maxEnrollment").asInt(),new ArrayList(),n.path("credits").asInt(),n.path("courseId").asInt());

        }
    }

    /**
     * Updates a Course object from the Memory Repository array given the ID of the parameter object
     * @param obj
     * @return Course -modified object
     */
    @Override
    public Course update(Course obj) {
        Course courseToUpdate = this.repoList.stream()
                .filter(course -> course.getCourseId() == obj.getCourseId())
                .findFirst()
                .orElseThrow();
        courseToUpdate.setCredits(obj.getCredits());
        courseToUpdate.setMaxEnrollment(obj.getMaxEnrollment());
        courseToUpdate.setName(obj.getName());
        courseToUpdate.setTeacher(obj.getTeacher());
        courseToUpdate.setStudentsEnrolled(obj.getStudentsEnrolled());

        return courseToUpdate;
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

        String serializedCourse = "";

        for (Course c : repoList){
            objectMapper.registerModule(new SimpleModule().addSerializer(Course.class, new CourseSerializer()));

            serializedCourse += objectMapper.writeValueAsString(c);

            serializedCourse += ",";

            writer.writeValue(new File("CourseData.json"),serializedCourse);

        }

    }
}

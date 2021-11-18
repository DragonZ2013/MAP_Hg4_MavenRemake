package Main.Repository;

import Main.Model.Course;
import Main.Model.Teacher;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class CourseRepository extends InMemoryRepository<Course> implements FileRepository{

    /**
     * Constructor for CourseRepository objects
     */
    public CourseRepository() {
        super();
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

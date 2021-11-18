package Main.Repository;

import Main.Model.Student;
import Main.Model.Teacher;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class TeacherRepository extends InMemoryRepository<Teacher> implements FileRepository{


    /**
     * Constructor for TeacherRepository Objects
     */
    public TeacherRepository() {
        super();
    }

    /**
     * Updates a Teacher object from the Memory Repository array given the ID of the parameter object
     * @param obj
     * @return Teacher -modified object
     */
    @Override
    public Teacher update(Teacher obj) {
        Teacher teacherToUpdate = this.repoList.stream()
                .filter(teacher -> teacher.getTeacherId() == obj.getTeacherId())
                .findFirst()
                .orElseThrow();
        teacherToUpdate.setCourses(obj.getCourses());
        teacherToUpdate.setFirstName(obj.getFirstName());
        teacherToUpdate.setLastName(obj.getLastName());

        return teacherToUpdate;
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

        String serializedTeacher = "";

        for (Teacher t : repoList){
            objectMapper.registerModule(new SimpleModule().addSerializer(Teacher.class, new TeacherSerializer()));

            serializedTeacher += objectMapper.writeValueAsString(t);

            serializedTeacher += ",";

            writer.writeValue(new File("StudentData.json"),serializedTeacher);

        }
    }
}

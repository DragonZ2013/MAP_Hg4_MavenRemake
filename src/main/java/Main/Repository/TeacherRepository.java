package Main.Repository;

import Main.Model.Course;
import Main.Model.Student;
import Main.Model.Teacher;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepository extends InMemoryRepository<Teacher> implements FileRepository{


    /**
     * Constructor for TeacherRepository Objects
     */
    public TeacherRepository() throws IOException {
        super();

        BufferedReader fixReader = new BufferedReader(new FileReader("teacherData.json"));

        String line = fixReader.readLine().replace("\\","");

        fixReader.close();

        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.replace(0,1,"[");
        stringBuilder.replace(line.length()-2,line.length(),"]");

        BufferedWriter fixWriter = new BufferedWriter(new FileWriter("teacherData.json"));

        fixWriter.write(stringBuilder.toString());
        fixWriter.close();

        Reader teacherReader = new BufferedReader(new FileReader("teacherData.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(teacherReader);

        for (JsonNode n: parser ){
            Teacher t = new Teacher(n.path("firstName").asText(),n.path("lastName").asText(),new ArrayList(),n.path("teacherId").asInt());
            this.create(t);
        }
        this.close();

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

            writer.writeValue(new File("TeacherData.json"),serializedTeacher);

        }
    }
}

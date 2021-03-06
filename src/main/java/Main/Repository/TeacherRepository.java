package Main.Repository;

import Main.Model.Teacher;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.*;
import java.util.ArrayList;

public class TeacherRepository extends InMemoryRepository<Teacher> implements FileRepository{


    /**
     * Constructor for TeacherRepository Objects + File initializer
     * @param filename
     * @throws IOException
     */
    public TeacherRepository(String filename) throws IOException {
        super();

        BufferedReader fixReader = new BufferedReader(new FileReader(filename));

        String line = fixReader.readLine().replace("\\","");

        fixReader.close();

        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.replace(0,1,"[");
        stringBuilder.replace(line.length()-2,line.length(),"]");

        BufferedWriter fixWriter = new BufferedWriter(new FileWriter(filename));

        fixWriter.write(stringBuilder.toString());
        fixWriter.close();

        Reader teacherReader = new BufferedReader(new FileReader(filename));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(teacherReader);

        for (JsonNode n: parser ){
            Teacher t = new Teacher(n.path("firstName").asText(),n.path("lastName").asText(),new ArrayList(),n.path("teacherId").asInt());
            this.create(t);
        }
        this.close(filename);

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

    /**
     * Saves the TeacherRepository at given path
     * @param filename
     * @throws IOException
     */
    @Override
    public void close(String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

        String serializedTeacher = "";

        for (Teacher t : repoList){
            objectMapper.registerModule(new SimpleModule().addSerializer(Teacher.class, new TeacherSerializer()));

            serializedTeacher += objectMapper.writeValueAsString(t);

            serializedTeacher += ",";

            writer.writeValue(new File(filename),serializedTeacher);

        }
    }
}

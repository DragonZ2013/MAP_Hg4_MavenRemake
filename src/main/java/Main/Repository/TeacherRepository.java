package Main.Repository;

import Main.Model.Student;
import Main.Model.Teacher;

public class TeacherRepository extends InMemoryRepository<Teacher>{


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
}

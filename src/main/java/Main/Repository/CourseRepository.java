package Main.Repository;

import Main.Model.Course;

public class CourseRepository extends InMemoryRepository<Course>{

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
}

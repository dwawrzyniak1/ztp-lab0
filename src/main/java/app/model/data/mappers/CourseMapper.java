package app.model.data.mappers;

import app.model.entities.Course;

public class CourseMapper implements Mapper<Course> {

    private static int fullnamePosition = 0;
    private static int shortnamePosition = 1;
    private static int codePosition = 2;

    @Override
    public Course map(String source) {
        String[] mapping = source.split(separator);

        Course course = new Course();
        course.setFullname(mapping[fullnamePosition]);
        course.setShortname(mapping[shortnamePosition]);
        course.setCode(mapping[codePosition]);

        return course;
    }

    @Override
    public String unmap(Course entity) {
        return entity.record(separator);
    }
}

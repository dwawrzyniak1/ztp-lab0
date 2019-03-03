package app.model.data.mappers;

import app.model.entities.Enrollment;

public class EnrollmentMapper implements Mapper<Enrollment> {

    private static int studentIdPosition = 0;
    private static int courseCodePosition = 1;

    private static String separator = ",";

    @Override
    public Enrollment map(String source) {
        String[] elements = source.split(separator);
        return new Enrollment(
                Long.valueOf(elements[studentIdPosition]),
                elements[courseCodePosition]);
    }

    @Override
    public String unmap(Enrollment entity) {
        return entity.record(separator);
    }
}

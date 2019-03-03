package app.model.data.mappers;

public interface Mapper <T> {

    String separator = ",";

    T map(String source);

    String unmap(T entity);

}

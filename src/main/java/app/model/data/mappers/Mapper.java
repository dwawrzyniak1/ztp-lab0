package app.model.data.mappers;

public interface Mapper <T> {

    String separator = ",";

    T map(String source);

    String unmap(T entity);

    default String[] trimAll(String[] source){
        for(String element : source){
            element.trim();
        }
        return source;
    }

}

package app.model.data;

import app.model.data.mappers.Mapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Repository<T> {

    protected Path resource;

    protected Mapper<T> mapper;

    protected Repository(String filename) {
        try {
            resource = initialize(filename);
        } catch (URISyntaxException e) {
            handleError(e);
        }
    }

    protected Path initialize(String filename) throws URISyntaxException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URI filepath = classLoader.getResource(filename).toURI();
        return Paths.get(filepath);
    }

    public List<T> getAll(){
        try (Stream<String> stream = Files.lines(resource)) {

            return stream
                    .filter(record -> !"".equals(record.trim()))
                    .map(record -> mapper.map(record))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            handleError(e);
        }
        return Collections.emptyList();
    }

    public void add(T entity){
        try {
            Files.write(resource, mapper.unmap(entity).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            handleError(e);
        }
    }

    public void delete(T entity){
        try (Stream<String> stream = Files.lines(resource)) {

            List<String> deleted = stream
                    .filter(record -> !"".equals(record.trim()))
                    .filter(record -> !mapper.map(record).equals(entity))
                    .collect(Collectors.toList());

            Files.write(resource, deleted);

        } catch (IOException e) {
            handleError(e);
        }
    }

    public void update(T entity){
        try (Stream<String> stream = Files.lines(resource)) {

            List<String> deleted = stream
                    .filter(record -> !"".equals(record.trim()))
                    .map(record -> mapper.map(record).equals(entity) ? mapper.unmap(entity) : record)
                    .collect(Collectors.toList());

            Files.write(resource, deleted);

        } catch (IOException e) {
            handleError(e);
        }
    }

    protected void handleError(Exception e) {
        if(e instanceof IOException){
            System.err.println("Błąd dostępu do pliku.");
        }else if(e instanceof URISyntaxException){
            System.err.println("Blad parsowania sciezki nie powiodlo się");
        }
    }

}

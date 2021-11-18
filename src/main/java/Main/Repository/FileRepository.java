package Main.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileRepository<T> {

    List<T> readFromFile() throws FileNotFoundException;

    void writeToFile();

    T findOne(int Id);

    void close() throws IOException;
}

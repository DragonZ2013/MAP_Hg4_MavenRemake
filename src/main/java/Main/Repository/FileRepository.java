package Main.Repository;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileRepository<T> {

    List<T> readFromFile() throws FileNotFoundException;

    void writeToFile();

    T findOne(int Id);

    void close();
}

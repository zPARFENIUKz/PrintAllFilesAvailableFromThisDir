import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        final Path path = Path.of("c:\\windows").getRoot();
        final List<Path> allFiles = getAllFiles(path);
        if (allFiles != null){
            for (final Path filepath : allFiles){
                System.out.println(filepath);
            }
            System.out.printf("There was %d available files", allFiles.size());

        }
    }

    private static List<Path> getAllFiles(final Path startPath){
        List<Path> lst;
        try (final DirectoryStream<Path> directoryStream = Files.newDirectoryStream(startPath)){
            lst = new ArrayList<>();
            for (final Path path : directoryStream){
                if (Files.isRegularFile(path)){
                    lst.add(path);
                } else {
                    final List<Path> tempList = getAllFiles(path);
                    if (tempList != null){
                        lst.addAll(tempList);
                    }
                }
            }
            return lst;
        } catch (AccessDeniedException e) {
        } catch (IOException e) {
            System.out.println("Path isn't correct");
        }
        return null;
    }
}
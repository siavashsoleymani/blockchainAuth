package ir.uid.util;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class StreamFileUtil {
    public static void streamFileIntoResponse(File file, HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = new FileInputStream(file);
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }
}

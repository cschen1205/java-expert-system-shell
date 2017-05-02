package com.cschen.works.ess.js;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by cschen on 28/4/17.
 */
public class IOUtil {

    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    public static InputStreamReader getResource(String filename) throws IOException {
        ClassLoader classLoader = IOUtil.class.getClassLoader();
        URL dataFile = classLoader.getResource(filename);
        return new InputStreamReader(dataFile.openStream());
    }

    public static void lines(String filename, Consumer<Stream<String>> callback) {
        if (callback == null) {
            logger.error("There is not callback for lines");
            return;
        }

        try (BufferedReader reader = new BufferedReader(getResource(filename))) {
            callback.accept(reader.lines());
        } catch (IOException e) {
            logger.error("Failed to read the file " + filename, e);
        }
    }


    public static String readToEnd(String filename) {
        StringBuilder sb = new StringBuilder();
        lines(filename, stringStream -> sb.append(stringStream.collect(Collectors.joining("\n"))));
        return sb.toString();
    }
}


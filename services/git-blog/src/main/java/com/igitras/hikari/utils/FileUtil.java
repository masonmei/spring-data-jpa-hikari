package com.igitras.hikari.utils;

import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author mason
 */
public class FileUtil {
    public static final String FILE_PREFIX = "file:";
    public static final String CLASSPATH_PREFIX = "classpath:";
    private static final String[] SEARCH_PATH = new String[]{FILE_PREFIX, CLASSPATH_PREFIX};

    /**
     * Resolve folder with file Path.
     *
     * @param filePath file path to resolved as file.
     * @return resolved file
     */
    public static File resolveFolder(String filePath) throws FileNotFoundException {
        Assert.hasLength(filePath, "File Path must not be null while resolving file.");
        if (filePath.startsWith(FILE_PREFIX) || filePath.startsWith(CLASSPATH_PREFIX)) {
            File configFile;
            try {
                configFile = ResourceUtils.getFile(filePath);
                if (configFile.exists()) {
                    return configFile.getCanonicalFile();
                }
            } catch (IOException e) {
                throw new FileNotFoundException("Cannot found file : " + filePath);
            }
        } else {
            for (String path : SEARCH_PATH) {
                File configFile;
                try {
                    configFile = ResourceUtils.getFile(String.format("%s%s", path, filePath));
                    if (configFile.exists()) {
                        return configFile.getCanonicalFile();
                    }
                } catch (IOException e) {
                    throw new FileNotFoundException("Cannot found file : " + filePath);
                }
            }
        }
        throw new FileNotFoundException("Cannot found file : " + filePath);
    }
}

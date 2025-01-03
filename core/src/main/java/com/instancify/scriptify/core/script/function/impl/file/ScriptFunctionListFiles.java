package com.instancify.scriptify.core.script.function.impl.file;

import com.instancify.scriptify.api.exception.ScriptFunctionArgTypeException;
import com.instancify.scriptify.api.exception.ScriptFunctionArgsCountException;
import com.instancify.scriptify.api.exception.ScriptFunctionException;
import com.instancify.scriptify.api.script.Script;
import com.instancify.scriptify.api.script.function.ScriptFunction;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Represents a function to get all files in a folder
 */
public class ScriptFunctionListFiles implements ScriptFunction {

    @Override
    public String getName() {
        return "listFiles";
    }

    @Override
    public Object invoke(Script script, Object[] args) throws ScriptFunctionException {
        if (args.length == 1) {
            if (args[0] instanceof String filePath) {
                File folder = Paths.get(filePath).toAbsolutePath().toFile();
                if (folder.isDirectory()) {
                    return Arrays.stream(folder.listFiles()).map(File::getAbsolutePath).toList();
                } else {
                    throw new ScriptFunctionException("File is not a folder");
                }
            } else {
                throw new ScriptFunctionArgTypeException(String.class, args[0].getClass());
            }
        } else {
            throw new ScriptFunctionArgsCountException(1, args.length);
        }
    }
}
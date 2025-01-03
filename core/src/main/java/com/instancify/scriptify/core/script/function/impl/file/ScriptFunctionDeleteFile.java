package com.instancify.scriptify.core.script.function.impl.file;

import com.instancify.scriptify.api.exception.ScriptFunctionArgTypeException;
import com.instancify.scriptify.api.exception.ScriptFunctionArgsCountException;
import com.instancify.scriptify.api.exception.ScriptFunctionException;
import com.instancify.scriptify.api.script.Script;
import com.instancify.scriptify.api.script.function.ScriptFunction;

import java.io.File;
import java.util.List;

/**
 * Represents a function to delete a file in the normal or recursive way
 */
public class ScriptFunctionDeleteFile implements ScriptFunction {

    @Override
    public String getName() {
        return "deleteFile";
    }

    @Override
    public Object invoke(Script script, Object[] args) throws ScriptFunctionException {
        if (args.length > 2 || args.length < 1) {
            throw new ScriptFunctionArgsCountException(1, args.length);
        }
        if (!(args[0] instanceof String filePath)) {
            throw new ScriptFunctionArgTypeException(String.class, args[0].getClass());
        }

        if (args.length == 1) {
            try {
                return new File(filePath).delete();
            } catch (Exception e) {
                return null;
            }
        }

        if (!(args[1] instanceof Boolean recursive)) {
            throw new ScriptFunctionArgTypeException(Boolean.class, args[1].getClass());
        }

        try {
            File file = new File(filePath);
            if (recursive) {
                deleteDirectoryRecursively(file);
                return null;
            } else {
                return file.delete();
            }
        } catch (Exception e) {
            return null;
        }
    }

    private void deleteDirectoryRecursively(File file) throws ScriptFunctionException {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteDirectoryRecursively(entry);
                }
            }
        }
        if (!file.delete()) {
            throw new ScriptFunctionException("Failed to delete " + file);
        }
    }
}

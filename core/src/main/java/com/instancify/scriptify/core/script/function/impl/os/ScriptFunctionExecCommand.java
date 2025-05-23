package com.instancify.scriptify.core.script.function.impl.os;

import com.instancify.scriptify.api.exception.ScriptFunctionArgTypeException;
import com.instancify.scriptify.api.exception.ScriptFunctionArgsCountException;
import com.instancify.scriptify.api.exception.ScriptFunctionException;
import com.instancify.scriptify.api.script.Script;
import com.instancify.scriptify.api.script.function.ScriptFunction;
import com.instancify.scriptify.api.script.function.argument.ScriptFunctionArgument;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Represents a function to execute command
 */
public class ScriptFunctionExecCommand implements ScriptFunction {

    @Override
    public @NotNull String getName() {
        return "execCommand";
    }

    @Override
    public Object invoke(Script<?> script, ScriptFunctionArgument[] args) throws ScriptFunctionException {
        if (args.length != 1) {
            throw new ScriptFunctionArgsCountException(1, args.length);
        }

        if (!(args[0].getValue() instanceof String input)) {
            throw new ScriptFunctionArgTypeException(String.class, args[0].getType());
        }

        try {
            Process process = Runtime.getRuntime().exec(input);

            StringBuilder message = new StringBuilder();
            try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

                String line;
                while ((line = stdInput.readLine()) != null) {
                    message.append(line).append("\n");
                }
                while ((line = stdError.readLine()) != null) {
                    message.append(line).append("\n");
                }
            }
            return message.toString();
        } catch (IOException e) {
            throw new ScriptFunctionException(e);
        }
    }
}

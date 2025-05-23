package com.instancify.scriptify.core.script.function.impl.os;

import com.instancify.scriptify.api.exception.ScriptFunctionArgTypeException;
import com.instancify.scriptify.api.exception.ScriptFunctionArgsCountException;
import com.instancify.scriptify.api.exception.ScriptFunctionException;
import com.instancify.scriptify.api.script.Script;
import com.instancify.scriptify.api.script.function.ScriptFunction;
import com.instancify.scriptify.api.script.function.argument.ScriptFunctionArgument;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a function to get environment variable value
 */
public class ScriptFunctionEnv implements ScriptFunction {

    @Override
    public @NotNull String getName() {
        return "env";
    }

    @Override
    public Object invoke(Script<?> script, ScriptFunctionArgument[] args) throws ScriptFunctionException {
        if (args.length != 1) {
            throw new ScriptFunctionArgsCountException(1, args.length);
        }

        if (!(args[0].getValue() instanceof String name)) {
            throw new ScriptFunctionArgTypeException(String.class, args[0].getType());
        }

        return System.getenv(name);
    }
}

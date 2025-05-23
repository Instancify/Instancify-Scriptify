package com.instancify.scriptify.core.script.function.impl.crypto;

import com.instancify.scriptify.api.exception.ScriptFunctionArgTypeException;
import com.instancify.scriptify.api.exception.ScriptFunctionArgsCountException;
import com.instancify.scriptify.api.exception.ScriptFunctionException;
import com.instancify.scriptify.api.script.Script;
import com.instancify.scriptify.api.script.function.ScriptFunction;
import com.instancify.scriptify.api.script.function.argument.ScriptFunctionArgument;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Represents a function to decode base64 to string
 */
public class ScriptFunctionBase64Decode implements ScriptFunction {
    @Override
    public @NotNull String getName() {
        return "base64decode";
    }

    @Override
    public Object invoke(Script<?> script, ScriptFunctionArgument[] args) throws ScriptFunctionException {
        if (args.length != 1) {
            throw new ScriptFunctionArgsCountException(1, args.length);
        }

        if (!(args[0].getValue() instanceof String str)) {
            throw new ScriptFunctionArgTypeException(String.class, args[0].getClass());
        }

        return new String(Base64.getDecoder().decode(str), StandardCharsets.UTF_8);
    }
}

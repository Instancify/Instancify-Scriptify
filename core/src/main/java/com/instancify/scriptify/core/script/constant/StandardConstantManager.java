package com.instancify.scriptify.core.script.constant;

import com.instancify.scriptify.api.script.constant.ScriptConstant;
import com.instancify.scriptify.api.script.constant.ScriptConstantManager;
import com.instancify.scriptify.core.script.constant.impl.ScriptConstantBaseDir;
import com.instancify.scriptify.core.script.constant.impl.ScriptConstantOsName;

import java.util.HashMap;
import java.util.Map;

public class StandardConstantManager implements ScriptConstantManager {

    private final Map<String, ScriptConstant> constants = new HashMap<>();

    public StandardConstantManager() {
        this.register(new ScriptConstantOsName());
        this.register(new ScriptConstantBaseDir());
    }

    @Override
    public Map<String, ScriptConstant> getConstants() {
        return constants;
    }

    @Override
    public void register(ScriptConstant constant) {
        if (!constants.containsKey(constant.getName())) {
            constants.put(constant.getName(), constant);
        } else {
            throw new IllegalStateException("The constant with this name already exists");
        }
    }

    @Override
    public void remove(String name) {
        if (constants.containsKey(name)) {
            constants.remove(name);
        } else {
            throw new IllegalArgumentException("The constant with this name does not exist");
        }
    }
}
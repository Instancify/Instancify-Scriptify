package com.instancify.scriptify.script;

import com.instancify.scriptify.api.exception.ScriptFunctionException;
import com.instancify.scriptify.api.script.Script;
import com.instancify.scriptify.api.script.function.ScriptFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

public class JsFunction implements Function {

    private final Script script;
    private final ScriptFunction function;

    public JsFunction(Script script, final ScriptFunction function) {
        this.script = script;
        this.function = function;
    }

    @Override
    public Object call(Context context, Scriptable scriptable, Scriptable scriptable1, Object[] objects) {
        try {
            return function.invoke(script, objects);
        } catch (ScriptFunctionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Scriptable construct(Context context, Scriptable scriptable, Object[] objects) {
        return null;
    }

    @Override
    public String getClassName() {
        return "";
    }

    @Override
    public Object get(String s, Scriptable scriptable) {
        return null;
    }

    @Override
    public Object get(int i, Scriptable scriptable) {
        return null;
    }

    @Override
    public boolean has(String s, Scriptable scriptable) {
        return false;
    }

    @Override
    public boolean has(int i, Scriptable scriptable) {
        return false;
    }

    @Override
    public void put(String s, Scriptable scriptable, Object o) {

    }

    @Override
    public void put(int i, Scriptable scriptable, Object o) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void delete(int i) {

    }

    @Override
    public Scriptable getPrototype() {
        return null;
    }

    @Override
    public void setPrototype(Scriptable scriptable) {

    }

    @Override
    public Scriptable getParentScope() {
        return null;
    }

    @Override
    public void setParentScope(Scriptable scriptable) {

    }

    @Override
    public Object[] getIds() {
        return new Object[0];
    }

    @Override
    public Object getDefaultValue(Class<?> aClass) {
        return null;
    }

    @Override
    public boolean hasInstance(Scriptable scriptable) {
        return false;
    }
}

package com.instancify.scriptify.script;

import com.instancify.scriptify.api.exception.ScriptException;
import com.instancify.scriptify.api.script.Script;
import com.instancify.scriptify.api.script.constant.ScriptConstant;
import com.instancify.scriptify.api.script.constant.ScriptConstantManager;
import com.instancify.scriptify.api.script.function.ScriptFunction;
import com.instancify.scriptify.api.script.function.ScriptFunctionManager;
import com.instancify.scriptify.api.script.security.ScriptSecurityManager;
import com.instancify.scriptify.api.script.security.exclude.ClassSecurityExclude;
import com.instancify.scriptify.api.script.security.exclude.SecurityExclude;
import com.instancify.scriptify.core.script.security.StandardSecurityManager;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaClass;
import org.mozilla.javascript.ScriptableObject;

public class JsScript implements Script<Object> {

    private final Context context = Context.enter();
    private final ScriptSecurityManager securityManager = new StandardSecurityManager();
    private ScriptFunctionManager functionManager;
    private ScriptConstantManager constantManager;

    @Override
    public ScriptSecurityManager getSecurityManager() {
        return securityManager;
    }

    @Override
    public ScriptFunctionManager getFunctionManager() {
        return functionManager;
    }

    @Override
    public void setFunctionManager(ScriptFunctionManager functionManager) {
        this.functionManager = functionManager;
    }

    @Override
    public ScriptConstantManager getConstantManager() {
        return constantManager;
    }

    @Override
    public void setConstantManager(ScriptConstantManager constantManager) {
        this.constantManager = constantManager;
    }

    @Override
    public Object eval(String script) throws ScriptException {
        ScriptableObject scope;

        if(securityManager.getSecurityMode()){
            SafeClassShutter safeClassShutter = new SafeClassShutter();
            context.setClassShutter(safeClassShutter);

            for(SecurityExclude exclude : securityManager.getExcludes()) {
                if(exclude instanceof ClassSecurityExclude classExclude) {
                    safeClassShutter.allowedClasses.add(classExclude.getValue());
                }
            }
        }

        scope = context.initStandardObjects();

        if (functionManager != null) {
            for (ScriptFunction function : functionManager.getFunctions().values()) {
                scope.put(function.getName(), scope, new JsFunction(this, function));
            }
        }

        if (constantManager != null) {
            for (ScriptConstant constant : constantManager.getConstants().values()) {
                ScriptableObject.putConstProperty(scope, constant.getName(), constant.getValue());
            }
        }

        try {
            Object value = context.evaluateString(scope, script, null, 1, null);
            context.close();
            return value;
        } catch (Exception e) {
            context.close();
            throw new ScriptException(e);
        }
    }
}

package com.instancify.scriptify.api.script.security.exclude;

public interface SecurityExclude {

    /**
     * Value to be added to the exclusion.
     *
     * @return The exclusion value
     */
    String getValue();

    /**
     * Checks that the value of path or packet is excluded.
     *
     * @param value Path or package
     * @return True if excluded, otherwise false
     */
    default boolean isExcluded(String value) {
        // Check that the path starts from the path specified in the exclusion
        return value.startsWith(this.getValue());
    }

    /**
     * Creates a new exclusion instance for the package.
     *
     * @param value A package that will be excluded
     * @return A new exclusion instance for the package
     */
    /*static ClassSecurityExclude ofPackage(String value) {
        return new ClassSecurityExclude(value);
    }*/

    /**
     * Creates a new exclusion instance for the path.
     *
     * @param value A path that will be excluded
     * @return A new exclusion instance for the path
     */
    static PathSecurityExclude ofPath(String value) {
        return new PathSecurityExclude(value);
    }
}

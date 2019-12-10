package org.test.annotations;

import org.test.di.Context;

public class DependenciesResolver {
    public static void inject() {
        Context.getAllInjectables();
    }
}

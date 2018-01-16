package servlets;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import entities.Salary;

public class GsonExclusionStrategyForSalaryClass implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getDeclaringClass() == Salary.class && f.getName().equalsIgnoreCase("employee");
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }

}

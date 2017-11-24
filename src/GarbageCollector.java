import Variables.Variable;

public abstract class GarbageCollector {

    public abstract Cell allocate(Variable var);

    public abstract void free(Variable var);
}

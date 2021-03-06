package me.tomassetti.turin.parser.ast.expressions;

import com.google.common.collect.ImmutableList;
import me.tomassetti.turin.compiler.errorhandling.ErrorCollector;
import me.tomassetti.jvm.JvmConstructorDefinition;
import me.tomassetti.turin.definitions.TypeDefinition;
import me.tomassetti.turin.resolvers.SymbolResolver;
import me.tomassetti.turin.parser.ast.Node;
import me.tomassetti.turin.parser.ast.typeusage.ReferenceTypeUsageNode;
import me.tomassetti.turin.parser.ast.typeusage.TypeUsageNode;
import me.tomassetti.turin.symbols.FormalParameter;

import java.util.*;


public class Creation extends InvokableExpr {

    private TypeUsageNode type;

    public TypeUsageNode getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Creation{" +
                "type='" + type + '\'' +
                ", actualParams=" + actualParams +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Creation creation = (Creation) o;

        if (!actualParams.equals(creation.actualParams)) return false;
        if (!type.equals(creation.type)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + actualParams.hashCode();
        return result;
    }

    @Override
    public boolean isOnOverloaded(SymbolResolver resolver) {
        return getTypeDefinition(resolver).hasManyConstructors();
    }

    private TypeDefinition getTypeDefinition(SymbolResolver resolver) {
        return type.asReferenceTypeUsage().getTypeDefinition();
    }

    @Override
    protected List<? extends FormalParameter> formalParameters(SymbolResolver resolver) {
        return getTypeDefinition(resolver).getConstructorParams(actualParams);
    }

    public Creation(TypeUsageNode type, List<ActualParam> actualParams) {
        super(actualParams);
        this.type = type;
        this.type.setParent(this);
    }

    public Creation(String typeName, List<ActualParam> actualParams) {
        this(new ReferenceTypeUsageNode(typeName), actualParams);
    }

    @Override
    protected boolean specificValidate(SymbolResolver resolver, ErrorCollector errorCollector) {
        return super.specificValidate(resolver, errorCollector);
    }

    @Override
    public Iterable<Node> getChildren() {
        return ImmutableList.copyOf(actualParams);
    }

    @Override
    public TypeUsageNode calcType() {
        return type;
    }

    public JvmConstructorDefinition jvmDefinition(SymbolResolver resolver) {
        return getTypeDefinition(resolver).resolveConstructorCall(originalParams);
    }

}

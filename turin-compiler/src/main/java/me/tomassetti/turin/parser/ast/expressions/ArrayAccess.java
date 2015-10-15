package me.tomassetti.turin.parser.ast.expressions;

import com.google.common.collect.ImmutableList;
import me.tomassetti.turin.parser.analysis.resolvers.SymbolResolver;
import me.tomassetti.turin.parser.ast.Node;
import me.tomassetti.turin.parser.ast.typeusage.TypeUsageNode;

public class ArrayAccess extends Expression {

    private Expression array;
    private Expression index;

    public Expression getArray() {
        return array;
    }

    public Expression getIndex() {
        return index;
    }

    public ArrayAccess(Expression array, Expression index) {
        this.array = array;
        this.array.setParent(this);
        this.index = index;

        this.index.setParent(this);
    }

    @Override
    public Iterable<Node> getChildren() {
        return ImmutableList.of(array, index);
    }

    @Override
    public TypeUsageNode calcType(SymbolResolver resolver) {
        TypeUsageNode arrayType = array.calcType(resolver);
        if (arrayType.isArray()) {
            return (TypeUsageNode)arrayType.asArrayTypeUsage().getComponentType();
        } else {
            throw new UnsupportedOperationException();
        }
    }
}

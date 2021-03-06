package me.tomassetti.turin.parser.ast.invokables;

import com.google.common.collect.ImmutableList;
import me.tomassetti.turin.resolvers.SymbolResolver;
import me.tomassetti.turin.parser.ast.FormalParameterNode;
import me.tomassetti.turin.parser.ast.Node;
import me.tomassetti.turin.parser.ast.statements.Statement;
import me.tomassetti.turin.parser.ast.typeusage.TypeUsageNode;
import me.tomassetti.turin.symbols.FormalParameter;
import me.tomassetti.turin.symbols.InvokableDefinition;
import me.tomassetti.turin.symbols.Symbol;

import java.util.List;
import java.util.Optional;

/**
 * Either a function or a method.
 */
public abstract class InvokableDefinitionNode extends Node implements InvokableDefinition {
    protected String name;
    protected TypeUsageNode returnType;
    protected List<FormalParameterNode> parameters;
    protected Statement body;

    public InvokableDefinitionNode(List<FormalParameterNode> parameters, Statement body, String name, TypeUsageNode returnType) {
        this.parameters = parameters;
        this.parameters.forEach((p) -> p.setParent(InvokableDefinitionNode.this) );
        this.body = body;
        this.body.setParent(this);
        this.name = name;
        this.returnType = returnType;
        this.returnType.setParent(this);
    }

    @Override
    public TypeUsageNode getReturnType() {
        return returnType;
    }

    @Override
    public List<? extends FormalParameter> getParameters() {
        return parameters;
    }

    public Statement getBody() {
        return body;
    }

    @Override
    public Optional<Symbol> findSymbol(String name, SymbolResolver resolver) {
        for (FormalParameterNode param : parameters) {
            if (param.getName().equals(name)) {
                return Optional.of(param);
            }
        }
        return super.findSymbol(name, resolver);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Iterable<Node> getChildren() {
        return ImmutableList.<Node>builder().add(returnType).addAll(parameters).add(body).build();
    }
}

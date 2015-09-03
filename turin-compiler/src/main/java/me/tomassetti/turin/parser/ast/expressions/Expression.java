package me.tomassetti.turin.parser.ast.expressions;

import me.tomassetti.turin.parser.analysis.InFileResolver;
import me.tomassetti.turin.parser.analysis.JvmMethodDefinition;
import me.tomassetti.turin.parser.analysis.JvmType;
import me.tomassetti.turin.parser.analysis.Resolver;
import me.tomassetti.turin.parser.ast.Node;
import me.tomassetti.turin.parser.ast.TypeUsage;

import java.util.List;

/**
 * Created by federico on 29/08/15.
 */
public abstract class Expression extends Node {
    public abstract TypeUsage calcType(Resolver resolver);

    /**
     * When the expression corresponds to something invokable this method find which Jvm method corresponds to the call
     * with the given parameters.
     */
    public JvmMethodDefinition findMethodFor(List<JvmType> argsTypes, Resolver resolver, boolean staticContext) {
        throw new UnsupportedOperationException(this.getClass().getCanonicalName());
    }

    /**
     * This expression represents a type?
     */
    public boolean isType(Resolver resolver) {
        return false;
    }
}
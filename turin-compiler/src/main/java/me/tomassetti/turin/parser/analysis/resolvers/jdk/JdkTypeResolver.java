package me.tomassetti.turin.parser.analysis.resolvers.jdk;

import me.tomassetti.turin.jvm.JvmNameUtils;
import me.tomassetti.turin.parser.analysis.resolvers.TypeResolver;
import me.tomassetti.turin.parser.ast.TypeDefinition;

import java.util.Optional;

public class JdkTypeResolver implements TypeResolver {

    private static JdkTypeResolver INSTANCE = new JdkTypeResolver();

    private JdkTypeResolver() {

    }

    public static JdkTypeResolver getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<TypeDefinition> resolveAbsoluteTypeName(String typeName) {
        if (!JvmNameUtils.isValidQualifiedName(typeName)) {
            throw new IllegalArgumentException(typeName);
        }
        return ReflectionTypeDefinitionFactory.getInstance().findTypeDefinition(typeName);
    }
}
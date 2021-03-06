package me.tomassetti.turin.typesystem;

import com.google.common.collect.ImmutableList;
import me.tomassetti.jvm.JvmType;
import me.tomassetti.turin.symbols.Symbol;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * NOTE: Being a Node we could need to have separate instances for each occurrence, so that each one can have a proper
 *       parent.
 */
public class UnsignedPrimitiveTypeUsage implements TypeUsage {

    public static UnsignedPrimitiveTypeUsage ULONG = new UnsignedPrimitiveTypeUsage("ulong", PrimitiveTypeUsage.LONG);
    public static UnsignedPrimitiveTypeUsage UINT = new UnsignedPrimitiveTypeUsage("uint", PrimitiveTypeUsage.INT,
            ImmutableList.of(ULONG));
    public static UnsignedPrimitiveTypeUsage USHORT = new UnsignedPrimitiveTypeUsage("ushort", PrimitiveTypeUsage.SHORT,
            ImmutableList.of(UINT, ULONG));
    public static UnsignedPrimitiveTypeUsage UBYTE = new UnsignedPrimitiveTypeUsage("ubyte", PrimitiveTypeUsage.BYTE,
            ImmutableList.of(USHORT, UINT, ULONG));
    public static UnsignedPrimitiveTypeUsage UDOUBLE = new UnsignedPrimitiveTypeUsage("udouble", PrimitiveTypeUsage.DOUBLE);
    public static UnsignedPrimitiveTypeUsage UFLOAT = new UnsignedPrimitiveTypeUsage("ufloat", PrimitiveTypeUsage.FLOAT,
            ImmutableList.of(UDOUBLE));

    public static ImmutableList<UnsignedPrimitiveTypeUsage> ALL = ImmutableList.of(UBYTE, USHORT, UINT, ULONG, UFLOAT, UDOUBLE);

    private String name;
    private PrimitiveTypeUsage correspondingPrimitiveTypeUsage;
    private List<UnsignedPrimitiveTypeUsage> promotionTypes;

    private UnsignedPrimitiveTypeUsage(String name, PrimitiveTypeUsage correspondingPrimitiveTypeUsage) {
        this(name, correspondingPrimitiveTypeUsage, Collections.emptyList());
    }

    private UnsignedPrimitiveTypeUsage(String name, PrimitiveTypeUsage correspondingPrimitiveTypeUsage,
                                       List<UnsignedPrimitiveTypeUsage> promotionTypes) {
        this.name = name;
        this.correspondingPrimitiveTypeUsage = correspondingPrimitiveTypeUsage;
        this.promotionTypes = promotionTypes;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public PrimitiveTypeUsage asPrimitiveTypeUsage() {
        return correspondingPrimitiveTypeUsage.asPrimitiveTypeUsage();
    }

    @Override
    public <T extends TypeUsage> TypeUsage replaceTypeVariables(Map<String, T> typeParams) {
        return this;
    }

    @Override
    public boolean sameType(TypeUsage other) {
        return other == this;
    }

    @Override
    public JvmType jvmType() {
        return correspondingPrimitiveTypeUsage.jvmType();
    }

    @Override
    public boolean canBeAssignedTo(TypeUsage type) {
        return asPrimitiveTypeUsage().canBeAssignedTo(type)
                || this == type
                || promotionTypes.contains(type);
    }

    @Override
    public String toString() {
        return "BasicTypeUsage{" +
                "name='" + name + '\'' +
                ", correspondingPrimitiveTypeUsage=" + correspondingPrimitiveTypeUsage +
                '}';
    }

    public static Optional<UnsignedPrimitiveTypeUsage> findByName(String typeName) {
        for (UnsignedPrimitiveTypeUsage basicTypeUsage : ALL) {
            if (basicTypeUsage.name.equals(typeName)) {
                return Optional.of(basicTypeUsage);
            }
        }
        return Optional.empty();
    }

    public static UnsignedPrimitiveTypeUsage getByName(String typeName) {
        for (UnsignedPrimitiveTypeUsage basicTypeUsage : ALL) {
            if (basicTypeUsage.name.equals(typeName)) {
                return basicTypeUsage;
            }
        }
        throw new IllegalArgumentException(typeName);
    }

    ///
    /// Fields
    ///

    @Override
    public boolean hasInstanceField(String fieldName, Symbol instance) {
        return false;
    }

    @Override
    public Symbol getInstanceField(String fieldName, Symbol instance) {
        throw new IllegalArgumentException("A " + describe() + " has no field named " + fieldName);
    }

    @Override
    public String describe() {
        return name;
    }

    ///
    /// Methods
    ///

    @Override
    public Optional<Invokable> getMethod(String method, boolean staticContext) {
        return Optional.empty();
    }
}

package me.tomassetti.turin.compiler.bytecode;

import me.tomassetti.turin.parser.analysis.JvmMethodDefinition;
import org.objectweb.asm.MethodVisitor;

import java.util.List;

import static org.objectweb.asm.Opcodes.*;

/**
 * Created by federico on 29/08/15.
 */
public class MethodInvocation extends BytecodeSequence {

    private JvmMethodDefinition jvmMethodDefinition;

    public MethodInvocation(JvmMethodDefinition jvmMethodDefinition) {
        this.jvmMethodDefinition = jvmMethodDefinition;
    }

    @Override
    public void operate(MethodVisitor mv) {
        //mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        //mv.visitLdcInsn("foo");
        mv.visitMethodInsn(INVOKEVIRTUAL, jvmMethodDefinition.getJvmType(), jvmMethodDefinition.getName(), jvmMethodDefinition.getSignature(), jvmMethodDefinition.isOnInterface());
    }

}
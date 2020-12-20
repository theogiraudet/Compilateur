package TP2.asd.type;

import TP2.llvm.Llvm;

public class Array extends Type {

    private final Type type;
    private final int size;

    public Array(Type type, int size) {
        this.type = type;
        this.size = size;
    }

    /**
     * Crée un nouveau type tableau avec une taille indéterminée
     *
     * @param type
     */
    public Array(Type type) {
        this(type, -1);
    }

    public Type getType() {
        return type;
    }

    @Override
    public String pp() {
        return type.pp() + '[' + (size == -1 ? "" : size) + ']';
    }

    /**
     * @return un pointeur vers le tableau
     */
    @Override
    public Llvm.Type toLlvmType() {
        return new Llvm.Pointer(type.toLlvmType());
    }

    public Llvm.Array toLlvmArray() {
        return new Llvm.Array(type.toLlvmType(), size);
    }

    @Override
    /**
     * @paran obj un objet
     * @return true si les objets sont identiques, c'est-à-dire si <i>obj</i> est de type Array, que le type
     * aggrégé est le même et que taille est identique. Dans le cas où le tableau a une taille indéterminée, la taille
     * est alors ignorée dans la comparaison
     */
    public boolean equals(Object obj) {
        return obj instanceof Array
                && (((Array) obj).size == size || size == -1 || ((Array) obj).size == -1)
                && ((Array) obj).type.equals(type);
    }
}

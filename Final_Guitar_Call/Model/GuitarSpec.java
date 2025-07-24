package com.swabhav.Final_Guitar_Call.Model;

public class GuitarSpec {
    private Builder builder;
    private String model;
    private Type type;
    private Wood frontWood;
    private Wood backWood;
    private String stringCount;

    public GuitarSpec(Builder builder, String model, Type type, Wood frontWood, Wood backWood, String stringCount) {
        this.builder = builder;
        this.model = model;
        this.type = type;
        this.frontWood = frontWood;
        this.backWood = backWood;
        this.stringCount = stringCount;
    }

    public Builder getBuilder() {
        return builder;
    }

    public String getModel() {
        return model;
    }

    public Type getType() {
        return type;
    }

    public Wood getFrontWood() {
        return frontWood;
    }

    public Wood getBackWood() {
        return backWood;
    }

    public String getStringCount() {
        return stringCount;
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setFrontWood(Wood frontWood) {
        this.frontWood = frontWood;
    }

    public void setBackWood(Wood backWood) {
        this.backWood = backWood;
    }

    public void setStringCount(String stringCount) {
        this.stringCount = stringCount;
    }

    public boolean matches(GuitarSpec otherSpec) {
        if (builder != otherSpec.builder) return false;
        if ((model != null && !model.equals(otherSpec.model)) || (model == null && otherSpec.model != null)) return false;
        if (type != otherSpec.type) return false;
        if (frontWood != otherSpec.frontWood) return false;
        if (backWood != otherSpec.backWood) return false;
        if (stringCount != null && !stringCount.equals(otherSpec.stringCount)) return false;
        return true;
    }
}

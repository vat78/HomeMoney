package ru.vat78.homeMoney.model.tools;


public class ColumnDefinition {

    private String name;
    private boolean editing;
    private boolean shown;
    private String caption;
    private boolean required;
    private String regexp;

    public ColumnDefinition setName(String name) {
        this.name = name;
        return this;
    }

    public ColumnDefinition setEditing(boolean editing) {
        this.editing = editing;
        return this;
    }

    public ColumnDefinition setShown(boolean shown) {
        this.shown = shown;
        return this;
    }

    public ColumnDefinition setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public ColumnDefinition setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public ColumnDefinition setRegexp(String regexp) {
        this.regexp = regexp;
        return this;
    }

    public String getName() {
        return name;
    }

    public boolean isEditing() {
        return editing;
    }

    public boolean isShown() {
        return shown;
    }

    public String getCaption() {
        return caption;
    }

    public boolean isRequired() {
        return required;
    }

    public String getRegexp() {
        return regexp;
    }
}

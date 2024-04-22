package org.ibs.api;

public class GoodsData {
    private String name;
    private String type;
    private Boolean exotic;

    public GoodsData(String name, String type, Boolean exotic) {
        this.name = name;
        this.type = type;
        this.exotic = exotic;
    }

    public GoodsData() {
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Boolean getExotic() {
        return exotic;
    }
}

package de.shop.modules.users.domain.dto;

import java.util.HashMap;
import java.util.List;

public class OutputCartDto {
    private HashMap<String, Object> data = new HashMap<>();
    private List<CartDto> products;

    public void addData(String key, Object value) {
        data.put(key, value);
    }

    public HashMap getData() {
        return data;
    }

    public void addProducts(List<CartDto> dto) {
        this.products = dto;
    }

    public List<CartDto> getProducts() {
        return products;
    }
}

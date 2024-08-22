package de.shop.modules.users.domain.dto;

import java.util.List;

/**
 * Класс, который выводит полное представление о заказе.
 */
public class OutputOrderDto {
    private OutputOrderDataDto data;
    private List<OutputOrderItemDto> products;

    public void setData(OutputOrderDataDto data) {
        this.data = data;
    }

    public OutputOrderDataDto getData() {
        return data;
    }

    public void setProducts(List<OutputOrderItemDto> items) {
        this.products = items;
    }

    public List<OutputOrderItemDto> getProducts() {
        return products;
    }

}
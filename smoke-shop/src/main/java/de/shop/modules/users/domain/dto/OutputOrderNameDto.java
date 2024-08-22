package de.shop.modules.users.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Класс, dto который выводит информацию о заказе, как обложку в списке заказов.
 */
public class OutputOrderNameDto {
    private Long id; // id заказа
    private LocalDateTime date; // дата заказа
    private BigDecimal total; // сумма заказа

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }
}

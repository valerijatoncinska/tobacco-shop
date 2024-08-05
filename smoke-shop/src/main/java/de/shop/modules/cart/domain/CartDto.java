//package de.shop.modules.cart.domain;
//
//import de.shop.modules.product.domain.dto.ProductDto;
//
//import java.util.List;
//import java.util.Objects;
//
//public class CartDto {
//
//    private Long id;
//
//    private List<ProductDto> products;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public List<ProductDto> getProducts() {
//        return products;
//    }
//
//    public void setProducts(List<ProductDto> products) {
//        this.products = products;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        CartDto cartDto = (CartDto) o;
//        return Objects.equals(id, cartDto.id) && Objects.equals(products, cartDto.products);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, products);
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Cart with id - %d, has %d products", id, products != null ? products.size() : 0);
//    }
//}
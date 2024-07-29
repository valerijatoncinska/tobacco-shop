//package de.shop.modules.cart.mapping;

//import de.shop.modules.cart.domain.CartDto;
//import de.shop.modules.cart.domain.CartEntity;
//import de.shop.modules.cart.mapping.CartMappingService;
//import de.shop.modules.product.domain.dto.ProductDto;
//import de.shop.modules.product.domain.entity.ProductEntity;
//import de.shop.modules.product.service.mapping.ProductMappingService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CartMappingServiceImpl implements CartMappingService {
////    @Autowired
//    private ProductMappingService productMappingService;
//
////    @Override
//    public CartDto mapEntityToDto(CartEntity entity) {
//
//        if (entity == null) {
//            return null;
//        }
//
//        CartDto dto = new CartDto();
//
//        dto.setId(entity.getId());
//        dto.setProducts(productEntityListToProductDtoList(entity.getProducts()));
//
//        return dto;
//
//
//    }
//
//    protected List<ProductDto> productEntityListToProductDtoList(List<ProductEntity> productEntityList) {
//
//        if (productEntityList == null) {
//
//            return null;
//        }
//
//        List<ProductDto> productDtoList = new ArrayList<ProductDto>(productEntityList.size());
//        for (ProductEntity productEntity : productEntityList) {
//            productDtoList.add(productMappingService.mapEntityToDto(productEntity));
//        }
//
//        return productDtoList;
//
////    }
//}

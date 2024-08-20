package de.shop.modules.product.controller;

import de.shop.core.components.LanguageResolver;
import de.shop.core.components.Validate;
import de.shop.core.exceptions.ValidateException;
import de.shop.modules.product.domain.dto.InputProductDto;
import de.shop.modules.product.domain.dto.OutputDto;
import de.shop.modules.product.domain.dto.OutputProductAdminDto;
import de.shop.modules.product.service.ProductService;
import de.shop.modules.users.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Properties;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService service;
    private LanguageResolver lang;
    private Validate validate;
    private CartService cartService;

    public ProductController(ProductService service, LanguageResolver lang, Validate validate, CartService cartService) {
        this.service = service;
        this.lang = lang;
        this.validate = validate;
        this.cartService = cartService;
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<OutputProductAdminDto> updateProduct(@PathVariable Long id, @RequestBody InputProductDto dto) throws ValidateException {
        Properties p = lang.load("products", "messages");
        validate.notBlank(dto.getTitle(), ((String) p.get("not.blank")));
        validate.price(dto.getPrice(), BigDecimal.ZERO, new BigDecimal(5000), ((String) p.get("price.error")).replace("[min]", "0").replace("[max]", "5000"));
        validate.minMax(dto.getQuantity(), 0, 5000, ((String) p.get("stock.error")).replace("[min]", "0").replace("[max]", "5000"));
        validate.notBlank(dto.getDescription(), ((String) p.get("desc.not.blank")));
        validate.notBlank(dto.getCharacteristics(), ((String) p.get("char.not.blank")));
        return ResponseEntity.ok(service.updateProduct(id, dto));
    }


    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<OutputProductAdminDto> createNewProduct(@RequestBody InputProductDto dto) throws ValidateException {
        Properties p = lang.load("products", "messages");
        validate.notBlank(dto.getTitle(), ((String) p.get("not.blank")));
        validate.price(dto.getPrice(), BigDecimal.ZERO, new BigDecimal(5000), ((String) p.get("price.error")).replace("[min]", "0").replace("[max]", "5000"));
        validate.minMax(dto.getQuantity(), 0, 5000, ((String) p.get("stock.error")).replace("[min]", "0").replace("[max]", "5000"));
        validate.notBlank(dto.getDescription(), ((String) p.get("desc.not.blank")));
        validate.notBlank(dto.getCharacteristics(), ((String) p.get("char.not.blank")));
        return ResponseEntity.ok(service.save(dto));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> dropProduct(@PathVariable Long id) {
        if (service.dropProduct(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Добавляем в корзину продукт.
    @PostMapping("/{id}/addition-to-cart")
    @Secured("ROLE_USER")
    public Object addToCart(@PathVariable Long id) {
        System.out.println("Работает контроллер добавления в корзину");
        boolean n = service.addItemCart(id);
        if (n == true) {
            return cartService.findByProductId(id);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{id}")
    public OutputDto productInfo(@PathVariable Long id) {
        return service.productInfo(id);
    }

    @GetMapping
    public ResponseEntity<OutputDto> productsAll() {
        return ResponseEntity.ok(service.productsAll());
    }


}

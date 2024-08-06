package de.shop.modules.product.controller;

import de.shop.core.components.LanguageResolver;
import de.shop.core.components.Validate;
import de.shop.modules.product.domain.dto.InputProductDto;
import de.shop.modules.product.domain.dto.OutputProductAdminDto;
import de.shop.modules.product.domain.entity.ProductEntity;
import de.shop.modules.product.service.ProductService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;


@RestController
@RequestMapping("/admin/products")
//@Secured("ROLE_ADMIN")
public class ProductControllerAdmin {

    private final ProductService service;
    private LanguageResolver lang;
    private Validate validate;

    public ProductControllerAdmin(ProductService service, LanguageResolver lang, Validate validate) {
        {
            this.service = service;
            this.lang = lang;
            this.validate = validate;
        }
    }

    @PostMapping
    public OutputProductAdminDto save(@RequestBody InputProductDto product) {
        {
            Properties p = lang.load("product", "messages");
            validate.notBlank(product.getTitle(), ((String) p.get("notblank")));
            validate.price(product.getPrice(), new BigDecimal(0), new BigDecimal(100000), ((String) p.get("price.validate")).replace("[min]", "0").replace("[max]", "100000"));
            validate.minMax(product.getQuantity(), 0, 100000, ((String) p.get("quantity.validate")).replace("[min]", "0").replace("[max]", "100000"));

            return service.save(product);
        }
    }

    @PutMapping("/{id}")
    public void archiveById(@PathVariable Long id) {
        service.archiveById(id);
    }

    @PutMapping("/{id}/restorations")
    public ProductEntity restoreById(@PathVariable Long id) {
        return service.restoreById(id);
    }

    @PutMapping("/modifications")
    public ProductEntity update(ProductEntity product) {
        return service.update(product);
    }

    @GetMapping("/{id}")
    public ProductEntity findById(@PathVariable Long id) {
        return service.findByIdForAdmin(id);
    }

    @GetMapping
    public List<OutputProductAdminDto> findAllActiveProductsForAdmin() {
        return service.findAllActiveProductsForAdmin();
    }

}

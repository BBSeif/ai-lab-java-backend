package aiproject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import aiproject.entity.Product;
import aiproject.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.net.URI;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@AllArgsConstructor 
@RestController 
@RequestMapping ("/api/products")
@Tag (name = "Product Controller", description = "API for managing products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation (summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponse (responseCode = "200", description = "Successfully retrieved list of products")
    public ResponseEntity<?> getAllProducts(
            @ParameterObject @PageableDefault(size = 10) org.springframework.data.domain.Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @GetMapping("/{id}")
    @Operation (summary = "Get product by ID", description = "Retrieve a product by its ID")
    @ApiResponse (responseCode = "200", description = "Successfully retrieved product") 
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    @Operation (summary = "Create a new product", description = "Create a new product with the provided details")
    @ApiResponse (responseCode = "201", description = "Successfully created product")  
    public ResponseEntity<?> createProduct(@Valid @RequestParam String name,
                                           @RequestParam(required = false) String description,
                                           @RequestParam BigDecimal price) {
        var createdProduct = productService.createProduct(name, description, price);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(((Product) createdProduct).getId())
                .toUri();
        return ResponseEntity.created(location).body(createdProduct);
    }

    @PutMapping("/{id}")
    @Operation (summary = "Update an existing product", description = "Update the details of an existing product by its ID")
    @ApiResponse (responseCode = "200", description = "Successfully updated product")   
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @Valid @RequestParam String name,
                                           @RequestParam(required = false) String description,
                                           @RequestParam BigDecimal price) {
        return ResponseEntity.ok(productService.updateProduct(id, name, description, price));
    }

    @DeleteMapping("/{id}")
    @Operation (summary = "Delete a product", description = "Delete a product by its ID")
    @ApiResponse (responseCode = "204", description = "Successfully deleted product")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }   
  


}

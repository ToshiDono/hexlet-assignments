package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findAllByPrice(Integer price);

    List<Product> findAllByPriceBetweenOrderByPriceAsc(Integer minPrice, Integer maxPrice);
    // END
}

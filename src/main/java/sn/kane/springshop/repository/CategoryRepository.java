package sn.kane.springshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.kane.springshop.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}

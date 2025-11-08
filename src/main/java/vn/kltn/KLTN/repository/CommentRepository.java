package vn.kltn.KLTN.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Comment;

@RepositoryRestResource
public interface CommentRepository extends JpaRepository<Comment, String> {

	@EntityGraph(attributePaths = { "product" })
	@Transactional
	@Query("SELECT c FROM Comment c where c.product.name=:productName")
	public List<Comment> findCommentsByProductId(@Param("productName") String productId);
}

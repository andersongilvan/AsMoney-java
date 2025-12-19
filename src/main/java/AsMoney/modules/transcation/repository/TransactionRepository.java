package AsMoney.modules.transcation.repository;

import AsMoney.modules.transcation.entiry.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(
            """
            SELECT t 
            FROM Transaction t
            JOIN t.user u
            WHERE u.id = :userId    
                    """
    )
    List<Transaction>findByUser(@Param("userId") UUID userId);
}

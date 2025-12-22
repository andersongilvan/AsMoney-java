package AsMoney.modules.transcation.repository;

import AsMoney.modules.transcation.entiry.Transaction;
import AsMoney.modules.transcation.enums.AmountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("""
            SELECT t 
            FROM Transaction t
            JOIN t.user u
            WHERE u.id = :userId    
            """)
    List<Transaction> findByUser(@Param("userId") UUID userId);

    @Query("""
            SELECT COALESCE(SUM(t.amount), 0.0)
            FROM Transaction t
            WHERE t.type = :type
            AND t.user.id = :userId
            """)
    BigDecimal findByType(
            @Param("userId") UUID userId,
            @Param("type") AmountType type
    );

    @Query("""
            SELECT COALESCE(SUM(t.amount), 0.0) 
            FROM Transaction t
            WHERE t.user.id = :userId
            """)
    BigDecimal sumAmount(@Param("userId") UUID userId);

    @Query("""
            DELETE FROM Transaction t
            WHERE t.id = :transactionId
            AND t.user.id = :userId
            """)
    void deleteById(@Param("transactionId") UUID transactionId, @Param("userId") UUID userId);


    @Query("""
            SELECT t FROM Transaction t
                WHERE t.createdAt >=  :startDate
                    AND t.createdAt < :endDate
                    AND t.user.id = :userId
            """)
    List<Transaction> findBetweenDates(
            @Param("startDate")
            LocalDateTime startDate,
            @Param("endDate")
            LocalDateTime endDate,
            @Param("userId")
            UUID userId);
}



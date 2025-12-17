package AsMoney.modules.transcation.entiry;


import AsMoney.modules.transcation.enums.AmountType;
import AsMoney.modules.user.entity.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private double amount;

    @Enumerated(EnumType.STRING)
    private AmountType type;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}

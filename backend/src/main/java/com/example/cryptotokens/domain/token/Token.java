package com.example.cryptotokens.domain.token;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "tokens",
    uniqueConstraints = @UniqueConstraint(columnNames = {"token", "symbol"})
)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false, unique = true)
    private String symbol;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Token token1)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(getToken(), token1.getToken()) &&
            Objects.equals(getSymbol(), token1.getSymbol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getToken(), getSymbol());
    }
}

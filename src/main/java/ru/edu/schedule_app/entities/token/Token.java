package ru.edu.schedule_app.entities.token;

import ru.edu.schedule_app.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    private User user;

    private String refreshToken;

    public Token(User user, String refreshToken) {
        this.user = user;
        this.refreshToken = refreshToken;
    }
}

package Philately.user.model;

import Philately.stamp.model.Stamp;
import Philately.stamp.model.WishedStamp;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Stamp> stamps = new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<WishedStamp> wishedStamps = new ArrayList<>();
}

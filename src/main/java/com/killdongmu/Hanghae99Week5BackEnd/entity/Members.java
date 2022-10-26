package com.killdongmu.Hanghae99Week5BackEnd.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.killdongmu.Hanghae99Week5BackEnd.util.Authority;
import com.killdongmu.Hanghae99Week5BackEnd.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity(name = "member")
@NoArgsConstructor
@AllArgsConstructor
public class Members extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public Members(String username, String password, String email, Authority authority) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authority = authority;
    }

}

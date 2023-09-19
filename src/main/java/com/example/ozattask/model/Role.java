package com.example.ozattask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String name;

    @Transient
    @ManyToMany(mappedBy = "role")
    private Set<User> users;

    public Role(long roleId, String name){
        this.roleId = roleId;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
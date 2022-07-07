package com.ob11to.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import net.bytebuddy.build.ToStringPlugin;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"company", "profile","userChats"})
@EqualsAndHashCode(of = "username")
//@Builder
@Entity
@Table(name = "users", schema = "public")
@TypeDef(name = "json", typeClass = JsonBinaryType.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User implements Comparable<User>, BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Embedded
    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Column(unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Type(type = "json")
    private String info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @OneToMany(mappedBy = "user")
//    @Builder.Default
    private List<UserChat> userChats = new ArrayList<>();

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }
}

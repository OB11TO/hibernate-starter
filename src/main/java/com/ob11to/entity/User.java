package com.ob11to.entity;

import com.ob11to.util.StringUtils;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import net.bytebuddy.build.ToStringPlugin;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ob11to.util.StringUtils.SPACE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"company", "profile","userChats", "payments"})
@EqualsAndHashCode(of = "username")
@Builder
@Entity
@Table(name = "users", schema = "public")
@TypeDef(name = "json", typeClass = JsonBinaryType.class)
public class User implements Comparable<User>, BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(fetch = FetchType. LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Profile profile;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserChat> userChats = new ArrayList<>();

    @BatchSize(size = 3)
    @OneToMany(mappedBy = "receiver")
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }

    public String fullName(){
        return getPersonalInfo().getFirstname() + SPACE + getPersonalInfo().getLastname();
    }
}

package com.chat.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * USERS DOMAIN
 * 
 * UserId를 PK로 갖는 USERS 테이블. UserName = 로그인 할 때 사용할 아이디로, Unique 부여. 회원가입, 비밀번호
 * 찾기 등의 기능으로 사용하기 위하여 email을 not-null 데이터 분석에 사용하기 위하여 gender, age, address,
 * time(BaseTimeEntity) 값을 받음
 * 
 * @author 이존규
 *  
 */
@Entity(name = "USERS")
@SequenceGenerator(name = "USERS_SEQ_GEN", sequenceName = "USERS_SEQ", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter 
@ToString
public class Users extends BaseTimeEntity {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERSA_SEQ_GEN")
    private Integer userId; // 개인마다 고유한 번호 부여. => CRUD에 사용

    @Column(unique = true, nullable = false)
    private String userName; // 로그인에 사용할 이름(아이디)

    @Column(nullable = false)
    private String userPassword; // 로그인에 사용할 비밀번호


    /**
     * Spring security, 관리자 기능을 사용하기 위한 roles settring
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRole> roles = new HashSet<>();

    /**
     * 회원가입 한 유저들에게 Role 을 부여. Default = USER
     * 
     * @param role (USER or ADMIN)
     * @return default = USER
     * @author 이존규
     */
    public Users addRole(UserRole role) {
        roles.add(role);

        return this;
    }
    
}

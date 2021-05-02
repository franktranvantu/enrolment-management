package com.franktran.enrolmentmanagement.config.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.franktran.enrolmentmanagement.config.security.UserPermission.*;

public enum UserRole {

    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, ENROLMENT_READ, ENROLMENT_WRITE, COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(ENROLMENT_READ, ENROLMENT_WRITE, COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
    ENROLMENT(Sets.newHashSet(ENROLMENT_READ, ENROLMENT_WRITE)),
    COURSE(Sets.newHashSet(COURSE_READ, COURSE_WRITE)),
    STUDENT(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + name()));
        return authorities;
    }

    public static Set<GrantedAuthority> getAuthoritiesByRole(String role) {
        return Stream.of(values())
            .filter(value -> UserRole.valueOf(role) == value)
            .map(UserRole::getAuthorities)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }

    public static boolean isEditable(UserRole[] roles, Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
            .filter(authority -> Arrays.stream(roles).anyMatch(role -> String.format("ROLE_%s", role).equals(authority.getAuthority())))
            .findAny()
            .isPresent();
    }
}

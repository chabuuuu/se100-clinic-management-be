package com.se100.clinic_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Jacksonized
@Builder
@FieldDefaults(level = PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtTokenVo {
    Integer userId;
    String username;
    List<String> roles;

    public List<GrantedAuthority> getAuthorities() {
        if (roles == null) return new ArrayList<>();
        return roles.stream().map(s -> (GrantedAuthority) () -> s).toList();
    }
}

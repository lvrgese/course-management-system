package com.lvargese.courseapi.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
@AttributeOverrides({
        @AttributeOverride(name = "name",   column = @Column(name = "guardian_name")),
        @AttributeOverride(name = "email",  column = @Column(name = "guardian_email")),
        @AttributeOverride(name = "mobile", column = @Column(name = "guardian_mobile"))
})
public class Guardian {
    @Column(nullable = false,length = 50)
    private String name;
    @Column(unique = true)
    private String email;
    private String mobile;
}

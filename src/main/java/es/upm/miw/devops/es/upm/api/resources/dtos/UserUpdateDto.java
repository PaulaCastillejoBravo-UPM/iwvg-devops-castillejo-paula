package es.upm.miw.devops.es.upm.api.resources.dtos;

import es.upm.miw.devops.es.upm.api.infrastructure.data.models.Role;

public record UserUpdateDto(
        String firstName,
        String familyName,
        String email,
        String mobile,
        String identity,
        String address,
        String city,
        Integer postalCode,
        String province,
        String password,
        Boolean active,
        Role role
) {
}
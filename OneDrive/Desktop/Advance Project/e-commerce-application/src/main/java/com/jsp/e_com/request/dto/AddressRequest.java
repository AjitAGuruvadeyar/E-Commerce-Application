package com.jsp.e_com.request.dto;

import com.jsp.e_com.enums.AddressType;
import com.jsp.e_com.responce.dto.AddressResponce;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AddressRequest {

//	@NotBlank
//    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Street address must be alphanumeric")
    private String streetAddress;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Street address additional must be alphanumeric or blank")
    private String streetAddressAdditional;

//    @NotBlank
//    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "City must contain only alphabets and spaces")
    private String city;

//    @NotBlank
//    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "State must contain only alphabets and spaces")
    private String state;

//    @NotBlank
//    @Pattern(regexp = "^\\d+$", message = "Pincode must be numeric")
    private int pincode;

//    @NotNull(message = "Address type is required")
    private AddressType addressType;

}

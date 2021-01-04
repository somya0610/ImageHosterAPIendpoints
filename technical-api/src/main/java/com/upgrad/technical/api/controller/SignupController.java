package com.upgrad.technical.api.controller;

import com.upgrad.technical.api.model.SignupUserRequest;
import com.upgrad.technical.api.model.SignupUserResponse;
import com.upgrad.technical.service.business.SignupBusinessService;
import com.upgrad.technical.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.UUID;

//Write the annotation which adds both the @Controller and @ResponseBody annotations.You no longer need to add @ResponseBody annotation on all the request mapping methods
@RestController
//Write the annotation to map the url request of type '/'
@RequestMapping("/")
public class SignupController {

    @Autowired
    private SignupBusinessService signupBusinessService;


    //This method is for user signup
    //This method receives the object of SignupUserRequest type with its attributes being set.
    //This method is listening for a HTTP POST request as indicated by method= RequestMethod.POST ,
    // maps to a URL request of type '/usersignup' and consumes and produces Json.
    @RequestMapping(method= RequestMethod.POST, path="/usersignup",
            consumes= MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> userSignup(final SignupUserRequest signupUserRequest) {
        //Now declaring the object of UserEntity class and set its corresponding attributes using signupUserRequest
        final UserEntity userEntity = new UserEntity();

        userEntity.setUuid(UUID.randomUUID().toString());
        //set the FirstName of userEntity using signupUserRequest
        userEntity.setFirstName(signupUserRequest.getFirstName());
        //set the LastName of userEntity using signupUserRequest
        userEntity.setLastName(signupUserRequest.getLastName());
        //set the Email of userEntity using signupUserRequest
        userEntity.setEmail(signupUserRequest.getEmailAddress());
        //set the Password of userEntity using signupUserRequest
        userEntity.setPassword(signupUserRequest.getPassword());
        //set the MobilePhone of userEntity using signupUserRequest
        userEntity.setMobilePhone(signupUserRequest.getMobileNumber());
        //Salt is actually used to encrypt and will be explained in next segment.As of now we are hard coding it
        userEntity.setSalt("1234abc");
        //Since this is user sign up so we are setting the role as "nonadmin"
        userEntity.setRole("nonadmin");



        //After setting the attributes of userEntity, pass it to signup() method, to add this object to a persistent context
        final UserEntity createdUserEntity = signupBusinessService.signup(userEntity);

        //Declaring an object of SignupUserResponse type and set its attributes using createdUserEntity.
        SignupUserResponse userResponse = new SignupUserResponse().id(createdUserEntity.getUuid())
                .status("USER SUCCESSFULLY REGISTERED");

        return new ResponseEntity<SignupUserResponse>(userResponse,HttpStatus.CREATED);
    }

}

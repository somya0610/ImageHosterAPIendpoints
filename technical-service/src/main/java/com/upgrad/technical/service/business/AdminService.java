package com.upgrad.technical.service.business;


import com.upgrad.technical.service.dao.ImageDao;
import com.upgrad.technical.service.dao.UserDao;
import com.upgrad.technical.service.entity.ImageEntity;
import com.upgrad.technical.service.entity.UserAuthTokenEntity;
import com.upgrad.technical.service.exception.ImageNotFoundException;
import com.upgrad.technical.service.exception.UnauthorizedException;
import com.upgrad.technical.service.exception.UserNotSignedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private ImageDao imageDao;

    public ImageEntity getImage(final String imageUuid, final String authorization) throws ImageNotFoundException, UnauthorizedException, UserNotSignedInException {

        //Get the record from user_auth_tokens table in UserAuthTokenEntity type object with the received accesstoken
        //Call getUserAuthToken() method
        UserAuthTokenEntity userAuthTokenEntity = imageDao.getUserAuthToken(authorization);

        //If you get no record, throw user defined exception(UserNotSignedInException, the implementation of UserNotSignedInException has been done)
        //Pass two String type arguments
        //First argument is Exception code(e.g. USR-001)
        //Second argument is Exception message(e.g. You are not Signed in, sign in first to get the image details)
        if (userAuthTokenEntity == null) {
            throw  new UserNotSignedInException("USR-001", "You are not Signed in, sign in first to get the image details");
        }


        //Now we need to check whether the user with corresponding access token is an admin or not
        //Get user from UserAuthTokenEntity type object and check
        //If he is an admin then get the image details using getImage() method in ImageDao class else throw UnauthorizedException(user-defined)(UnauthorizedException has been implemented and accepts two arguments, both are Strings, first is Exception code and second is Exception message)
        //Also throw an ImageNotFoundException if the image uuid is incorrect(ImageNotException has been implemented and accepts two arguments, both are Strings, first is Exception code and second is Exception message)
        if (userAuthTokenEntity.getUser().getRole().equals("admin")) {
            ImageEntity imageEntity = imageDao.getImage(imageUuid);
            if (imageEntity == null) {
                throw new ImageNotFoundException("IMG-001", "Image with given uuid not found");
            }
            return imageEntity;
        } else {
            throw new UnauthorizedException("ATH-001", "UNAUTHORISED Access, Entered user is not an admin");
        }
    }
}

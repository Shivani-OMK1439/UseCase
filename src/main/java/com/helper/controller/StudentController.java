package com.helper.controller;

import com.helper.dto.response.ViewListResponse;
import com.helper.dto.request.StudentCourseCred;
import com.helper.dto.request.StudentCred;
import com.helper.dto.response.*;
import com.helper.entity.UserInfo;
import com.helper.service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@Controller
public class StudentController {

    @Autowired
    private ServiceClass serviceClass;


    /****************************************** User Onboard *****************************************/

    @RequestMapping(value = "/user/onboard", method = RequestMethod.POST)
    @ResponseBody
    public OnboardResponse OnboardUser(@RequestParam() String email, @RequestParam() String firstName, @RequestParam() String lastName,
                                       @RequestParam() String password) throws Exception{
        return serviceClass.saveDetails(new UserInfo(email, firstName, lastName, password,0));
    }




    /*************************************** User Login *********************************************/


    @RequestMapping(value = "/user/login", method = RequestMethod.POST) //by default choose get
    @ResponseBody
    public LoginResponse loginUser(@RequestParam() int id, @RequestParam() String password) throws Exception {
        return serviceClass.isLogin(id, password);
    }





    /********************************** User View All course List  ************************************/


    @RequestMapping(value = "/courses/view", method = RequestMethod.POST)
    public @ResponseBody
    CourseViewResponse viewCourses(@RequestHeader(value = "userToken") String userToken, @RequestBody StudentCred studentCred) throws Exception {


        // get validUpto using user token
        LocalDateTime validUpto = serviceClass.validUptoBasedOnUserToken(userToken);

        LocalDateTime currentDateTime = LocalDateTime.now();

        //if validUpto is less than currentDate Time
        if (validUpto.isAfter(currentDateTime)) {
            return serviceClass.coursesViewAfterLogin(studentCred);
        }

        else {
            List<StudentCourseDetail> data = new ArrayList<>();
            return new CourseViewResponse("User Token expired! Login again", false, data);
        }

    }




    /********************************** User Course Register  *****************************************/


    @RequestMapping(value = "user/course/register",method = RequestMethod.POST)
    public @ResponseBody
    CourseRegisterResponse CoursesForRegistration(@RequestBody StudentCourseCred studentCourseCred) {
        return serviceClass.saveCoursesOfEachStudent(studentCourseCred);
    }



    /********************************* User All Registered Courses List ********************************/

    @RequestMapping(value = "user/course/list",method = RequestMethod.POST)
    public @ResponseBody
    ViewListResponse CoursesListDetails(@RequestBody StudentCred studentCred) throws Exception
    {
         return serviceClass.CourseListDetails(studentCred);
    }






}




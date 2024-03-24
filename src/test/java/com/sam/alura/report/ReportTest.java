package com.sam.alura.report;

import com.sam.alura.course.*;
import com.sam.alura.user.ApplicationUser;
import com.sam.alura.user.CleanPassword;
import com.sam.alura.user.Role;
import com.sam.alura.user.RoleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class ReportTest {


    @Test
    void shouldCalculateNPS() {
        CourseRatingRepository courseRatingRepository = Mockito.mock(CourseRatingRepository.class);

        NPSService npsService = new NPSService(courseRatingRepository);

        Role role = new Role();
        role.setType(RoleType.ROLE_INSTRUCTOR);

        Role roleStudent = new Role();
        roleStudent.setType(RoleType.ROLE_STUDENT);


        CleanPassword password = new CleanPassword("123");

        ApplicationUser instructor = new ApplicationUser("Samuel", "samcamargo", "sam-camargo@live.com", password, role);
        Course course = new Course("java", "code", instructor, "123");

        ApplicationUser applicationUser = new ApplicationUser("Samuel", "samcamargo", "sam-camargo@live.com", password, roleStudent);
        ApplicationUser applicationUser1 = new ApplicationUser("camargo", "samcamargo", "sam-camargo@live.com", password, roleStudent);
        ApplicationUser applicationUser2 = new ApplicationUser("camargo", "samcamargo", "sam-camargo@live.com", password, roleStudent);

        List<CourseRating> courseRatings = Arrays.asList(
                new CourseRating(course, applicationUser, "bad", 9),
                new CourseRating(course, applicationUser1, "bad", 5),
                new CourseRating(course, applicationUser2, "bad", 10)
        );

        Mockito.when(courseRatingRepository.findAllByCourseId(course.getId())).thenReturn(courseRatings);
        NPSResponse result = npsService.calculateNPS(course);
        Assertions.assertEquals(33, result.nps());
        Assertions.assertEquals(result.promoters(), 2);
        Assertions.assertEquals(result.detractors(), 1);
    }
}


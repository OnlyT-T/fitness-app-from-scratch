package com.progressfit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progressfit.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // save()
    // findAll()
    // findById()
    // deleteById()
    // existsById()
    // count()

    /* ==================================================== */
    /* PARSE METHOD NAMES IS THE FEATURE OF SPRING DATA JPA */
    /* ==================================================== */
    // find...By...And...
    // find...By...GreaterThan...
    // find...By...LessThan...
    // find...By...Containing... ==> WHERE username LIKE '%trung%'
    // find...By...EndingWith... ==> WHERE username LIKE '%gmail.com'
    // find...By...IgnoreCase... ==> Không phân biệt viết hoa/thường
    // find...By...Between(x, y)
    // find...By...OrderBy...Asc(x) ==> sorting
    // read...By
    // get...By
    // exists...By
    // count...By
    // delete...By

    // CUSTOM METHODS
    /*
    @Query("""
        SELECT u
        FROM User u
        WHERE u.age > :age
    """)
    List<User> findAdultUsers(@Param("age") Integer age);
    */

    // Check whether email already exists
    boolean existsByEmail(String email);
}
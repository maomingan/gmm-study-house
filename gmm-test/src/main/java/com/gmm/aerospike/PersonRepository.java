//package com.gmm.aerospike;
//
//import org.springframework.data.aerospike.repository.AerospikeRepository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * @author Gmm
// * @date 2023/9/8
// */
//public interface PersonRepository extends AerospikeRepository<Person, Long> {
//    List<Person> findByLastName(String lastName);
//    List<Person> findByLastNameContaining(String lastNameLike);
//    List<Person> findByFirstNameAndLastNameContaining(String firstName, String lastNameLike);
//    List<Person> findByDateOfBirthBetween(long startDate, long endDate);
//}

//package com.gmm.aerospike;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.List;
//
///**
// * @author Gmm
// * @date 2023/9/8
// */
//@RestController
//public class AerospikeDemoController {
//
//    @Autowired
//    private PersonRepository personRepsitory;
//
//    @GetMapping("/seed")
//    public int seedData() {
//        Person person = new Person(1, "Bob", "Jones", new GregorianCalendar(1971, 12, 19).getTime());
//        Person person2 = new Person(2, "Gmm", "gump", new GregorianCalendar(1990, 12, 19).getTime());
//        Person person3 = new Person(3, "Lsy", "sylvia", new GregorianCalendar(1992, 12, 19).getTime());
//        personRepsitory.save(person);
//        personRepsitory.save(person2);
//        personRepsitory.save(person3);
//        return 1;
//    }
//
//    @GetMapping("/findByLastName/{lastName}")
//    public List<Person> findByLastName(@PathVariable(name = "lastName", required=true) String lastName) {
//        List<Person> list = personRepsitory.findByLastName(lastName);
//        return list;
//    }
//
//    @GetMapping("/findByLastNameLike/{lastNameLike}")
//    public List<Person> findByLastNameLike(@PathVariable(name = "lastNameLike", required=true) String lastNameLike) {
//        List<Person> list = personRepsitory.findByLastNameContaining(lastNameLike);
//        return list;
//    }
//
//    @GetMapping("/findByFirstNameAndLastNameContaining/{firstName}/{lastNameLike}")
//    public List<Person> findByFirstNameAndLastNameContaining(@PathVariable(name = "firstName", required=true) String firstName,
//                                           @PathVariable(name = "lastNameLike", required=true) String lastNameLike) {
//        List<Person> list = personRepsitory.findByFirstNameAndLastNameContaining(firstName, lastNameLike);
//        return list;
//    }
//
//    @GetMapping("/findByDateOfBirthBetween")
//    public List<Person> findByDateOfBirthBetween(Date startDate, Date endDate) {
//        List<Person> list = personRepsitory.findByDateOfBirthBetween(startDate.getTime(), endDate.getTime());
//        return list;
//    }
//
//    @GetMapping("/pageQuery")
//    public Page<Person> pageQuery(int page, int size){
//        PageRequest pr = PageRequest.of(page, size, Sort.Direction.DESC, "dateOfBirth");
//        Page<Person> pageList = personRepsitory.findAll(pr);
//        return pageList;
//    }
//
//}

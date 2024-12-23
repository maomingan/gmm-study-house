//package com.gmm.aerospike;
//
//import com.aerospike.client.Host;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.aerospike.config.AbstractAerospikeDataConfiguration;
//import org.springframework.data.aerospike.config.AerospikeDataSettings;
//import org.springframework.data.aerospike.repository.config.EnableAerospikeRepositories;
//
//import java.util.Collection;
//import java.util.Collections;
//
///**
// * @author Gmm
// * @date 2023/9/8
// */
//@Configuration
//@EnableAerospikeRepositories(basePackageClasses = { PersonRepository.class})
//public class AerospikeConfiguration extends AbstractAerospikeDataConfiguration {
//
//    @Override
//    protected Collection<Host> getHosts() {
//        return Collections.singleton(new Host("172.20.81.146", 3000));
//    }
//
//    @Override
//    protected String nameSpace() {
//        return "test";
//    }
//
//    @Bean
//    public AerospikeDataSettings aerospikeDataSettings() {
//        return AerospikeDataSettings.builder().scansEnabled(true).build();
//    }
//
//}

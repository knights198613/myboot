package com.jiangwei.springboottest.myboot;

import com.xiaoju.am.mesh.sdk.http.EnableJaguarClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableJaguarClients(basePackages = {"com.jiangwei.springboottest.myboot.facade"})
@SpringBootApplication
public class MybootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybootApplication.class, args);

        //UUUUUUUUUUUUUU
    }

}

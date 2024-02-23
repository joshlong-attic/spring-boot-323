package bootiful.service;

import org.springframework.ai.chat.ChatClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;

@SpringBootApplication
public class ServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    // Tony Hoare

    // José Paumard
    @Bean
    ApplicationRunner applicationRunner() {

        return args -> {


            var es = Executors.newVirtualThreadPerTaskExecutor();


            var names = new ConcurrentSkipListSet<String>();  // Set<String>
            var threads = new ArrayList<Thread>();
            for (var i = 0; i < 1000; i++) {
                var first = i == 0;
                threads.add(
                        Thread.ofVirtual().unstarted(new Runnable() {
                            @Override
                            public void run() {
                                // tbd todo

                                if (first) names.add(Thread.currentThread().toString());
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }


                                if (first) names.add(Thread.currentThread().toString());
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                                if (first) names.add(Thread.currentThread().toString());
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                                if (first) names.add(Thread.currentThread().toString());
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        })
                );
            }

            for (var t : threads) t.start();

            for (var t : threads) t.join();

            // ..
            System.out.println( names);


        };

    }


}

@Controller
@ResponseBody
class StoryTimeController {

    private final ChatClient singularity;

    StoryTimeController(ChatClient singularity) {
        this.singularity = singularity;
    }

    @GetMapping("/story")
    Map<String, String> story() {

        var prompt = """
                            
                Dear Singularity, 
                            
                Please write a story about the amazing Java and Spring developers at
                American Airlines, please.
                            
                And, would you write it in the style of famed children's author Dr. Seuss.
                            
                Cordially,
                Josh
                """;

        var response = this.singularity.call(prompt);

        return Map.of("story", response);
    }

}

@Controller
@ResponseBody
class CustomerHttpController {

    private final CustomerRepository repository;

    CustomerHttpController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/customers")
    Collection<Customer> customers() {
        return this.repository.findAll();
    }
}

interface CustomerRepository extends ListCrudRepository<Customer, Integer> {
}

// look mom, no Lombok!
record Customer(@Id Integer id, String name) {
}






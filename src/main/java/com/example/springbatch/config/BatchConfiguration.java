package com.example.springbatch.config;

import com.example.springbatch.JobCompletionNotificationListener;
import com.example.springbatch.Person;
import com.example.springbatch.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    // reader, processor, writer
    @Bean
    public FlatFileItemReader<Person> reader() {
        // create itemReader
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>(){{
                    setTargetType(Person.class);
                }}).build();
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        // creat itemWriter (@EnableBatchProcessing 의해 만들어진 datasource 의 복사본을 자동으로 가져온다.
        // JDBC의 Batch 기능을 사용하여 한번에 Database로 전달하여 Database 내부에서 쿼리들이 실행되도록한다.
        return new JdbcBatchItemWriterBuilder<Person>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
                .dataSource(dataSource)
                .build();
    }

    // job 을 정의합니다.
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer()) // ? 왜 정의하는 거지?
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    // job 을 구성하고 있는 step 을 정의한다. 각각의 step 은 reader, processor, writer 을 포함할 수 있다.
    @Bean
    public Step step1(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("step1")  // step 의 이름을 정의합니다.
                .<Person, Person> chunk(10)
                .reader(reader())               // csv 로 부터 파일을 읽고
                .processor(processor())         // 읽은 csv 파일의 사람 이름을 대문자로 바꾸는 작업을 한 후
                .writer(writer)                 // database 에 넣는다. 라고 하는 step 을 정의합니다.
                .build();
    }
}

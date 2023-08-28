package com.ap4j.bma.batch;

import com.ap4j.bma.model.entity.apt.AptDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableBatchProcessing
public class ChunkJobConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final ApartmentApiService apartmentApiService;
	private final DataSource dataSource;

	@Bean
	public JdbcTemplate jdbcTemplate(){
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public Job job(){
		return jobBuilderFactory.get("chunkJob2")
				.start(step())
				.build();
	}

	@Bean
	public Step step(){
		return stepBuilderFactory.get("chunkStep")
				.<List<AptDTO>, List<AptDTO>>chunk(1)
				.reader(aptApiReader())
				.writer(aptApiWriter())
				.allowStartIfComplete(true)
				.build();
	}

	@Bean
	@StepScope
	public ItemReader<List<AptDTO>> aptApiReader(){
		return new ApartmentApiReader(apartmentApiService);
	}

	@Bean
	@StepScope
	public ItemWriter<List<AptDTO>> aptApiWriter(){
		return new ApartmentApiWriter(apartmentApiService, jdbcTemplate());
	}

}

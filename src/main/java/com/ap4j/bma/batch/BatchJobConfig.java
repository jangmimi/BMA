package com.ap4j.bma.batch;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.MethodInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchJobConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job testJob(){
		return jobBuilderFactory.get("testJob1")
				.start(testStep())
				.next(testStep2())
				.next(testStep3())
				.next(taskStep())
				.next(BeforeAfterTaskletTest())
				.build();
	}

	@Bean
	public Step testStep(){
		return stepBuilderFactory.get("testStep")
				.tasklet(((contribution, chunkContext) -> {
					log.info("Step 실행");
					// 비즈니스 로직
					for(int i = 0; i < 10; i++) {
						log.info("{}번 실행", i);
					}
					return RepeatStatus.FINISHED;
				}))
				.allowStartIfComplete(true)
				.build();
	}

	@Bean
	public Step testStep2(){
		return stepBuilderFactory.get("testStep2")
				.tasklet(((contribution, chunkContext) -> {
					log.info("Step2 실행");
					log.info("{} 컨트리 + 청크 {}", contribution,chunkContext);
					return RepeatStatus.FINISHED;
				}))
				.allowStartIfComplete(true)
				.build();
	}

	@Bean
	public Step testStep3(){
		return stepBuilderFactory.get("testStep3")
				.tasklet(((contribution, chunkContext) -> {
					log.info("Step3 실행");
					return RepeatStatus.FINISHED;
				}))
				.allowStartIfComplete(true)
				.build();
	}


	// 외부 태스크 가져오기
	@Bean
	public Step BeforeAfterTaskletTest(){
		return stepBuilderFactory.get("beforeTaskTest")
				.tasklet(new BeforeAfterTasklet())
				.build();
	}


	@Bean
	public Step taskStep(){
		return stepBuilderFactory.get("taskStep")
				.tasklet(myTasklet())
				.allowStartIfComplete(true)
				.build();
	}

	@Bean
	public CustomService service(){
		return new CustomService();
	}

	@Bean
	public MethodInvokingTaskletAdapter myTasklet(){
		MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

		adapter.setTargetObject(service());
		adapter.setTargetMethod("businessLogic");

		return adapter;
	}







}

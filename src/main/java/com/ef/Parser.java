package com.ef;

import static java.util.Arrays.stream;

import java.util.EnumMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ef.enums.Parameter;
import com.ef.util.DateUtil;

@EnableBatchProcessing
public class Parser {

	private static String[] config = { "spring/job/spring-parser.xml" };

	public static void main(String[] args) {

		try {

			Map<Parameter, String> parameters = readCommandLineParameters(args);
			JobParameters jobParameters = createJobParameters(parameters);
			
			ApplicationContext context = new ClassPathXmlApplicationContext(config);		
			
			JobLauncher jobLauncher = context.getBean(JobLauncher.class);
			
			System.out.println("parameters "+parameters);
			
			if(parameters.get(Parameter.accesslog) != null){
				System.out.println("running log");
				jobLauncher.run((Job)context.getBean("logParserJob"), jobParameters);
			}
			if(parameters.get(Parameter.startDate) != null) {
				jobLauncher.run((Job)context.getBean("findBlockedIp"), jobParameters);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Map<Parameter, String> readCommandLineParameters(String[] args) throws ParseException {

		CommandLineParser parser = new DefaultParser();
		CommandLine line = parser.parse(createCommandLineOptions(), args);

		Map<Parameter, String> params = new EnumMap<>(Parameter.class);
		stream(Parameter.values()).forEach(param -> params.put(param, line.getOptionValue(param.name())));

		return params;
	}

	private static Options createCommandLineOptions() {

		Options options = new Options();

		stream(Parameter.values())
				.forEach(param -> options.addOption(Option.builder().longOpt(param.name()).hasArg().build()));

		return options;
	}
	
	private static JobParameters createJobParameters(Map<Parameter, String> params) throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		
		jobParametersBuilder.addString(Parameter.startDate.name(), DateUtil.formatDate(params.get(Parameter.startDate)));
		jobParametersBuilder.addString(Parameter.endDate.name(), DateUtil.getEndDate(params.get(Parameter.startDate), params.get(Parameter.duration)));
		jobParametersBuilder.addLong(Parameter.threshold.name(), Long.valueOf(params.get(Parameter.threshold)));
		jobParametersBuilder.addString(Parameter.accesslog.name(), params.get(Parameter.accesslog));
		
		return jobParametersBuilder.toJobParameters();
	}
	
	
}

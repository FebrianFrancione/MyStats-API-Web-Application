package com.quickChart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuickChartApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickChartApplication.class, args);
	}
//
}

/* web app ??Secure?
	not be a wrapper nor extend public services. HAs its own domain of business
	RESTful

	Needs:
	access to external service, inclusing service addres and params must be stored in config file
	Wep app secure - only an aithroized user may use system.
	Business exposes functions via secure web service API. MAy be called by any web client, but must be authenticated

	External service may not consist of simple et reuqwets.At leas t3 types of web service consumption : ex: auth, different verbs, http headers
 */
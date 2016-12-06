# spring-quartz-cluster

This project is used to demonstrates on how to configure Spring Quartz integration in Clustering mode. 
We will use MariaDB as persistent machenism.

## Download DDL from Quartz official website: http://www.quartz-scheduler.org/downloads/
1. Unzip Quartz 2.2.3.tar.gz
2. Create a database on MariaDB database: quartz2
2. Execute DDL from quartz-2.2.3-distribution.tar\quartz-2.2.3\docs\dbTables\tables_mysql.sql

## Add Required libraries to Gradle (build.gradle)
buildscript {
	repositories {
        mavenCentral()
        maven { url 'http://repo.spring.io/plugins-release' }
    }
    dependencies {
        classpath('org.springframework.boot:spring-boot-gradle-plugin:1.4.2.RELEASE')
    }
}

apply plugin: 'war'
apply plugin: 'eclipse-wtp'
apply plugin: 'spring-boot'

sourceCompatibility = 1.8
targetCompatibility = 1.8

repositories {
    mavenCentral()
}
ext {
	mybatisSpringVersion = '1.1.1'
	quartzVersion = '2.2.3'
	mariadbClientVersion = '1.5.5'
}
dependencies {
	compile 'org.slf4j:jcl-over-slf4j'
	compile 'ch.qos.logback:logback-classic'
	compile 'org.logback-extensions:logback-ext-spring:0.1.4'
	compile 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.springframework.boot:spring-boot-starter-tomcat'
	compile "org.mybatis.spring.boot:mybatis-spring-boot-starter:$mybatisSpringVersion"
	compile "org.quartz-scheduler:quartz:$quartzVersion"
	compile 'org.springframework:spring-context-support'
	compile 'org.springframework.boot:spring-boot-starter-security'
	compile "org.mariadb.jdbc:mariadb-java-client:$mariadbClientVersion"
	testCompile 'org.springframework.boot:spring-boot-starter-test'
}

war {
	archiveName 'quartz.war'
}

eclipse {
  wtp {
    component {
      contextPath = 'quartz'
    }
  }
}
bootRepackage {
    enabled = false
}


## Configure datasource (appcontext-datasource.xml)
   <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
		<property name="driverClassName" value="org.mariadb.jdbc.MariaDbDataSource" />
		<property name="url" value="jdbc:mysql://127.0.0.1:3306/quartz2" />
		<property name="username" value="root" />
		<property name="password" value="123" />
  </bean>


## Configure transactionManager (appcontext-transaction.xml)
   <bean id="transactionManager"
		class="org.springframework.jdbc.datasource.DataSourceTransactionManager"
		p:dataSource-ref="dataSource" />
   <tx:annotation-driven transaction-manager="transactionManager"/>

## Configure property (quartz.properties)
org.quartz.scheduler.instanceId = AUTO
org.quartz.scheduler.skipUpdateCheck = true
org.quartz.scheduler.makeSchedulerThreadDaemon = true

org.quartz.threadPool.class = org.quartz.simpl.SimpleThreadPool
org.quartz.threadPool.threadCount = 5
org.quartz.threadPool.makeThreadsDaemons = true

org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreTX
org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
org.quartz.jobStore.isClustered = true

## Configure quartz (appcontext-quartz.xml)
  <bean name="runMeJob" class="org.springframework.scheduling.quartz.JobDetailFactoryBean">
	    <property name="jobClass" value="com.kdemo.spring.quartz.job.Job1" />
	    <property name="group" value="MY_JOBS_GROUP" />
	    <property name="description" value="Just run for test"/>
	    <property name="durability" value="true" />
	</bean>
	

	<!-- Cron Trigger, run every 5 seconds -->
	<bean id="cronTrigger"
                class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
		<property name="jobDetail" ref="runMeJob" />
		<property name="description" value="Run every day on 11PM" />
		<property name="cronExpression" value="0 0 23 ? * *" />

	</bean>

	<bean id="scheduler" class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
		<property name="quartzProperties" ref="quartzProperties"/>
		<property name="dataSource" ref="dataSource"/>
		<property name="transactionManager" ref="transactionManager"/>
		<property name="overwriteExistingJobs" value="true"/>
		<property name="jobFactory">
			<bean class="com.kdemo.spring.quartz.factory.AutowiringSpringBeanJobFactory"/>
		</property>
		
		<property name="jobDetails">
			<list>
				<ref bean="runMeJob" />
			</list>
		</property>

		<property name="triggers">
			<list>
				<ref bean="cronTrigger" />
			</list>
		</property>
	</bean>
	
	<util:properties id="quartzProperties" location="classpath:/quartz.properties"/>



package com.baihoo.blog.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * springboot 之 注解式声明事务,使用聲明式事務配置，可以有效規範代碼
 * @author Administrator
 */
@Configuration
public class ApplicationContextTransactional {
	//事务方法超时时间设置
	private static final int TX_METHOD_TIMEOUT=5;
	//AOP切面的切点表达式
	private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.baihoo.blog.service.impl.*.*(..))";
	//注入事务管理器
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	/**
	 * 增强(事务)的属性的配置 
	 * 		isolation:DEFAULT	:事务的隔离级别.
	 * 		propagation			:事务的传播行为.
	 * 		read-only					:false.不是只读
	 * 		timeout					:-1
	 * 		no-rollback-for		:发生哪些异常不回滚
	 * 		rollback-for				:发生哪些异常回滚事务
	 * @return
	 */
	@Bean
	public TransactionInterceptor txAdvice() {
		/*增强(事务)的属性的配置
		 * 	<tx:attributes>
		 * */
		NameMatchTransactionAttributeSource txAttributeS = new NameMatchTransactionAttributeSource();
		/*propagation="REQUIRED" , timeout=5 ;rollback-for=".. , .."配置*/
		RuleBasedTransactionAttribute requiredAttr = new RuleBasedTransactionAttribute();
		requiredAttr.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		requiredAttr.setTimeout(TX_METHOD_TIMEOUT);
		requiredAttr.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		/*propagation="SUPPORTS" , readOnly="true"配置*/
		RuleBasedTransactionAttribute supportsAttr = new RuleBasedTransactionAttribute();
		supportsAttr.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
		supportsAttr.setReadOnly(true);
		/*
		 注意：方法名称来自类匹配的到方法 
		 	【save*, “*”表示匹配任意個字符】
		 	<tx:method .../>
		 */	
		Map<String , TransactionAttribute>   txMethod = new HashMap<String , TransactionAttribute>();
		txMethod.put("save*", requiredAttr);
		txMethod.put("add*", requiredAttr);
		txMethod.put("insert*", requiredAttr);
		txMethod.put("update*", requiredAttr);
		
		txMethod.put("register*", requiredAttr); //對service層的轉賬業務方法開啓事務增强通知
		
		txMethod.put("delete*", supportsAttr);
		txMethod.put("find*", supportsAttr);
		txMethod.put("select*", supportsAttr);
		txMethod.put("get*", supportsAttr);
		
		txAttributeS.setNameMap(txMethod);
		TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager , txAttributeS);
		return txAdvice;
	}
	/**
	 * AOP配置定义切面和切点的信息
	 * @return
	 */
	@Bean
	public Advisor txAdviceAdvisor() {
		AspectJExpressionPointcut pointcut= new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
		
		return new DefaultPointcutAdvisor(pointcut , txAdvice());
	}
}

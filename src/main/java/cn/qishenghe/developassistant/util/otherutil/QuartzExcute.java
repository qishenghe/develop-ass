package cn.qishenghe.developassistant.util.otherutil;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzExcute implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap jobDataMap = context.getMergedJobDataMap();
        Class clazz = (Class) jobDataMap.get("class");
        String methodName = (String) jobDataMap.get("method");
        Object[] params = (Object[]) jobDataMap.get("params");

        QuartzUtil.runFunction(clazz, methodName, params);

    }
}

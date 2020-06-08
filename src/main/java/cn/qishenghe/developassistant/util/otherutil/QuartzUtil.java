package cn.qishenghe.developassistant.util.otherutil;

import cn.qishenghe.developassistant.util.dataprocessingutil.Md5Util;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class QuartzUtil {

    // 调度器
    private Scheduler scheduler;

    // 周期
    private String cron;
    // 类
    private Class clazz;
    // 函数名称（注：被调用函数所有参数应使用类类型，禁止使用基础数据类型及接口）
    private String methodName;
    // 参数集
    private Object[] params;

    // job Id
    private String jobId;

    // 不指定ID进行构造
    public QuartzUtil(String cron, Class clazz, String methodName, Object... params) {
        this.cron = cron;
        this.clazz = clazz;
        this.methodName = methodName;
        this.params = params;
    }

    public static void runFunction (Class clazz, String methodName, Object... params) {
        try {
            Object o = clazz.newInstance();

            Class[] classes = new Class[params.length];
            int num = 0;
            for (Object singleParam : params) {
                classes[num ++] = singleParam.getClass();
            }

            Method method = clazz.getDeclaredMethod(methodName, classes);
            method.invoke(o, params);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void run () {

        try {
            // 如果未定义jobId，则自动生成一个（默认为job描述的MD5值）
            if (jobId == null) {
                jobId = Md5Util.getMd5(getComment());
            }

            if (this.scheduler == null) {
//                scheduler = StdSchedulerFactory.getDefaultScheduler();
                // 根据不同nme获取不同调度器
                Properties props = new Properties();
                props.put("org.quartz.scheduler.instanceName", getJobId() + "");// 不同name
                props.put("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool");
                props.put("org.quartz.threadPool.threadCount","10");
                StdSchedulerFactory schf = new StdSchedulerFactory();
                schf.initialize(props);
                scheduler = schf.getScheduler();

                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("class", clazz);
                jobDataMap.put("method", methodName);
                jobDataMap.put("params", params);

                JobDetail jobDetail = JobBuilder.newJob(QuartzExcute.class)
                        .setJobData(jobDataMap)
                        .withIdentity(jobId)
                        .build();

                Trigger triggerWeatherStationWeatherDataRt = TriggerBuilder.newTrigger()
                        .withIdentity(jobId)
                        .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                        .forJob(jobDetail)
                        .build();

                scheduler.scheduleJob(jobDetail, triggerWeatherStationWeatherDataRt);
            }

            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void end () {
        try {
            if (scheduler != null) {
                scheduler.shutdown();
                scheduler = null;
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void restart () {
        end();
        run();
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Class<Object> getClazz() {
        return clazz;
    }

    public void setClazz(Class<Object> clazz) {
        this.clazz = clazz;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getComment () {
        return "[" + "class : " + clazz + "|" + "method : " + methodName + "|" + "cron" + cron + "]";
    }

//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        System.out.println("abc");
//        runFunction(this.clazz, this.methodName, this.params);
//    }
}

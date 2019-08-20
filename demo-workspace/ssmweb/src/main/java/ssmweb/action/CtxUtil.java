package ssmweb.action;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring���������Ĺ����࣬���ڻ�ȡ��ǰ��Spring����
 * ʵ���˽ӿ�ApplicationContextAware�Ҹ��౻Spring����
 * ����Զ�����setApplicationContext������ȡSpring��������
 */
@Component
public class CtxUtil implements ApplicationContextAware {

    public static ApplicationContext ctx;
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx=applicationContext;
    }
    /**
     * �������ͻ��bean
      */
    public static <T> T getBean(Class<T> clazz){
        return ctx.getBean(clazz);
    }
    /**
     * �����������ƻ��bean
      */
    public static Object getBean(String name){
        return ctx.getBean(name);
    }

}

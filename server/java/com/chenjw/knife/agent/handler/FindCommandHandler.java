package com.chenjw.knife.agent.handler;

import java.util.ArrayList;
import java.util.List;

import com.chenjw.knife.agent.Agent;
import com.chenjw.knife.agent.constants.Constants;
import com.chenjw.knife.agent.core.CommandDispatcher;
import com.chenjw.knife.agent.core.CommandHandler;
import com.chenjw.knife.agent.core.ServiceRegistry;
import com.chenjw.knife.agent.service.ContextService;
import com.chenjw.knife.agent.service.ObjectHolderService;
import com.chenjw.knife.agent.utils.NativeHelper;
import com.chenjw.knife.agent.utils.ResultHelper;
import com.chenjw.knife.agent.utils.ToStringHelper;
import com.chenjw.knife.core.args.ArgDef;
import com.chenjw.knife.core.args.Args;
import com.chenjw.knife.core.model.result.ClassInfo;
import com.chenjw.knife.core.model.result.ClassListInfo;
import com.chenjw.knife.core.model.result.InstanceListInfo;
import com.chenjw.knife.core.model.result.ObjectInfo;
import com.chenjw.knife.utils.StringHelper;

public class FindCommandHandler implements CommandHandler {

    private Class<?> findClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private Class<?>[] findLikeClass(String className) {
        List<Class<?>> likeClass = new ArrayList<Class<?>>();
        for (Class<?> clazz : Agent.getAllLoadedClasses()) {
            if (StringHelper.matchIgnoreCase(className, clazz.getName())) {
                likeClass.add(clazz);
            }
        }
        return likeClass.toArray(new Class<?>[likeClass.size()]);
    }

    private boolean isNumeric(String str) {
        return StringHelper.isNumeric(str);
    }

    public void handle(Args args, CommandDispatcher dispatcher) {
        Class<?> clazz = null;
        String className = args.arg("find-expression");
        if (isNumeric(className)) {
            Class<?>[] likeClazz = (Class<?>[]) ServiceRegistry.getService(ContextService.class).get(Constants.CLASS_LIST);
            clazz = likeClazz[Integer.parseInt(className)];
        } else {
            clazz = findClass(className);
            if (clazz == null) {
                Class<?>[] likeClazz = findLikeClass(className);
                if (likeClazz.length > 1) {

                    ServiceRegistry.getService(ContextService.class).put(Constants.CLASS_LIST, likeClazz);

                    ClassListInfo info = new ClassListInfo();
                    List<ClassInfo> cInfoList = new ArrayList<ClassInfo>();
                    for (Class<?> cc : likeClazz) {
                        ClassInfo cInfo = new ClassInfo();
                        cInfo.setInterface(cc.isInterface());
                        cInfo.setName(cc.getName());
                        cInfo.setClassLoader(ToStringHelper.toClassLoaderString(cc));
                        cInfoList.add(cInfo);

                    }
                    info.setClasses(cInfoList.toArray(new ClassInfo[cInfoList.size()]));
                    info.setExpression(className);
                    Agent.sendResult(ResultHelper.newResult(info));
                    return;
                } else if (likeClazz.length == 1) {
                    clazz = likeClazz[0];
                }
            }
            if (clazz == null) {
                Agent.sendResult(ResultHelper.newErrorResult("not found!"));
                return;
            }
        }
        // //
        Object[] objs = NativeHelper.findInstancesByClass(clazz);

        InstanceListInfo info = new InstanceListInfo();
        List<ObjectInfo> cInfoList = new ArrayList<ObjectInfo>();
        for (Object obj : objs) {
            ObjectInfo cInfo = new ObjectInfo();
            cInfo.setObjectId(ServiceRegistry.getService(ObjectHolderService.class).toId(obj));
            cInfo.setValueString(ToStringHelper.toDetailString(obj));
            cInfoList.add(cInfo);

        }
        info.setInstances(cInfoList.toArray(new ObjectInfo[cInfoList.size()]));
        info.setClassName(clazz.getName());
        Agent.sendResult(ResultHelper.newResult(info));
    }

    public void declareArgs(ArgDef argDef) {

        argDef.setDefinition("find <find-expression>");

    }
}

package com.as1124.spring.remote.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.springframework.remoting.rmi.RmiServiceExporter;

import com.as1124.spring.web.model.UserInfo;

/**
 * 和标准 RMI 实现不同，这里可以不用实现 {@link Remote} 接口，接口方法也不用抛出 {@link RemoteException}，
 * 在{@linkplain RmiServiceExporter 远程服务导出}时进行了Wrapper包装；参考{@linkplain RmiServiceExporter#prepare()}，
 * {@linkplain RmiInvocationWrapper}
 * <br/>
 * 如果 <code>extends Remote </code>也没有问题；
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IUserRMIService {

	public String getName(int uid);

	public List<UserInfo> getAllUsers();

	public void saveUser(UserInfo user);

	public default void deleteUser(int uid) {
		// default implementation
	}
}
